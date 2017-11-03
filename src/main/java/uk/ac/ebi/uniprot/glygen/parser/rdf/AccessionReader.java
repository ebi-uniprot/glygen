package uk.ac.ebi.uniprot.glygen.parser.rdf;

import org.apache.jena.rdf.model.Model;

public class AccessionReader {
    private String entryEndpoint = "http://www.uniprot.org/uniprot/";
    private Model model;

    public AccessionReader(Model model) {
        this.model = model;
    }

    public void getEntry(String entry) {
        model.read(entryEndpoint + entry + ".rdf", "RDFXML");

    }
}
