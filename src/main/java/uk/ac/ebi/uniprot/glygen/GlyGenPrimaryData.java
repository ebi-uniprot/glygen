package uk.ac.ebi.uniprot.glygen;

import uk.ac.ebi.uniprot.glygen.parser.protAPI.AccessionListReader;

import java.io.IOException;
import java.util.Date;

public class GlyGenPrimaryData {
    public static void main(String[] args) throws IOException {
        Date now = new Date();
        AccessionListReader listReader = new AccessionListReader();
        listReader.readList("test.acc");
        System.out.println(listReader.getOmittedAccessions());
        Date end = new Date();
        System.out.println("Running time: " + (end.getTime() - now.getTime()));
    }
}
