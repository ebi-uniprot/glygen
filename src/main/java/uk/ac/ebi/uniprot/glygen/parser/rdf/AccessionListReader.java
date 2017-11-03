package uk.ac.ebi.uniprot.glygen.parser.rdf;

import uk.ac.ebi.uniprot.glygen.parser.PropertiesReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;

public class AccessionListReader {
    private List<String> omittedAccessions;
    private AccessionReader accessionReader;
    private Model model;
    private Charset charset;

    public AccessionListReader() {
        this.charset = Charset.forName("UTF-8");
    }
    /**
     *
     * @param inputFileName
     */
    public void writeModel(String inputFileName) {
        this.initModel();
        this.populateModel(inputFileName);

    }

    private void initModel() {
        this.model = ModelFactory.createDefaultModel();
        this.accessionReader = new AccessionReader(this.model);
    }
    private void populateModel(String inputFileName) {
        this.omittedAccessions = new ArrayList<String>();
        Path pathIn = FileSystems.getDefault().getPath(PropertiesReader.getResourcesFolder(), inputFileName);
        int counter = 1;
        try (BufferedReader reader = Files.newBufferedReader(pathIn, charset)) {
            String accession = null;
            while ((accession = reader.readLine()) != null) {
                try {
                    accessionReader.getEntry(accession);
                    counter++;
                } catch (Exception e) {
                    this.omittedAccessions.add(accession);
                }
            }
        } catch (IOException x) {
            System.err.format("IOException: %s%n", x);
        }
    }
}
