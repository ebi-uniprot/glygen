package uk.ac.ebi.uniprot.glygen.parser.rdf;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.riot.WriterDatasetRIOT;
import uk.ac.ebi.uniprot.glygen.util.SyntaxRDF;

public class AccessionReader {
    private String entryEndpoint = "http://www.uniprot.org/uniprot/";
    private Model model;
    private SyntaxRDF syntax;

    public AccessionReader(Model model, SyntaxRDF syntax) {
        this.model = model;
        this.syntax = syntax;
    }

    public void getEntry(String entry) {
        model.read(entryEndpoint + entry + this.syntax.getExtension(), this.syntax.getFormat());
    }
}
