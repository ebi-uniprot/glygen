package uk.ac.ebi.uniprot.glygen.parser.rdf;

import org.apache.jena.query.Dataset;
import org.apache.jena.rdf.model.RDFWriter;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.riot.Lang;
import org.apache.jena.riot.RDFDataMgr;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.riot.RDFFormat;
import uk.ac.ebi.uniprot.glygen.util.SyntaxRDF;

public class AccessionListParser {
    private List<String> omittedAccessions;
    private AccessionReader accessionReader;
    private Model model;
    private Charset charset;
    private SyntaxRDF syntax;

    public AccessionListParser(String rdfSyntax) {
        this.charset = Charset.forName("UTF-8");
        this.syntax = rdfSyntax.equalsIgnoreCase("JSONLD") ? SyntaxRDF.JSONLD : SyntaxRDF.RDFXML;
    }
    /**
     *
     * @param inputFileName
     */
    public void parseModel(String inputFileName) {
        this.initModel();
        this.populateModel(inputFileName);
    }

    public void writeModel(String outpuFileName) {
        try (OutputStream out = new FileOutputStream(outpuFileName + this.syntax.getExtension())) {
            RDFWriter writer = this.model.getWriter(this.syntax.getFormat());
            Resource proteinType = model.createResource("http://purl.uniprot.org/core/Protein");
            writer.setProperty("prettyTypes", new Resource[]{proteinType});
            writer.write(this.model, out, this.syntax.getFormat());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initModel() {
        this.model = ModelFactory.createDefaultModel();
        this.accessionReader = new AccessionReader(this.model, this.syntax);
    }

    private void populateModel(String inputFileName) {
        this.omittedAccessions = new ArrayList<String>();
        Path pathIn = FileSystems.getDefault().getPath(inputFileName);
        try (BufferedReader reader = Files.newBufferedReader(pathIn, charset)) {
            String accession = null;
            while ((accession = reader.readLine()) != null) {
                try {
                    accessionReader.getEntry(accession);
                } catch (Exception e) {
                    this.omittedAccessions.add(accession);
                }
            }
        } catch (IOException x) {
            System.err.format("IOException: %s%n", x);
        }
    }

    public List<String> getOmittedAccessions() {
        return omittedAccessions;
    }
}
