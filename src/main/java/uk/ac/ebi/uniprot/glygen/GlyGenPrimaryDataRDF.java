package uk.ac.ebi.uniprot.glygen;

import org.apache.jena.riot.RDFFormat;
import org.apache.jena.vocabulary.OWL;
import uk.ac.ebi.uniprot.glygen.parser.protAPI.AccessionListReader;
import uk.ac.ebi.uniprot.glygen.parser.rdf.AccessionListParser;

import java.io.IOException;
import java.util.Date;

public class GlyGenPrimaryDataRDF {
    public static void main(String[] args) throws IOException {
        Date now = new Date();
        AccessionListParser parser = new AccessionListParser("RDFXML");
        parser.parseModel("./src/main/resources/test.acc");
        parser.writeModel("./src/main/resources/test.acc.out");
        System.out.println(parser.getOmittedAccessions());
        Date end = new Date();
        System.out.println("Running time: " + (end.getTime() - now.getTime()));
    }
}
