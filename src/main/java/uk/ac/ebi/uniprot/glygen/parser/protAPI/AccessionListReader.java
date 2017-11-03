package uk.ac.ebi.uniprot.glygen.parser.protAPI;

import uk.ac.ebi.uniprot.glygen.parser.PropertiesReader;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import javax.json.JsonObject;

public class AccessionListReader {
    private List<String> omittedAccessions;
    private Charset charset = Charset.forName("UTF-8");
    private AccessionReader accessionReader = new AccessionReader();

    /**
     *
     * @param fileName
     */
    public void readList(String fileName) {
        this.omittedAccessions = new ArrayList<String>();

        Path pathOut = FileSystems.getDefault().getPath(PropertiesReader.getResourcesFolder(), fileName + ".out.json");
        try (BufferedWriter writer = Files.newBufferedWriter(pathOut, charset)) {
            writer.write('[');
            writer.newLine();
            Path pathIn = FileSystems.getDefault().getPath(PropertiesReader.getResourcesFolder(), fileName);
            int counter = 1;
            try (BufferedReader reader = Files.newBufferedReader(pathIn, charset)) { String accession = null;
                while ((accession = reader.readLine()) != null) {
                    JsonObject entry = accessionReader.getEntry(accession);
                    try {
                        if (counter != 1) {
                            writer.write(',');
                            writer.newLine();
                        }
                        String entryAccession = entry.get("accession").toString();
                        writer.write(entry.toString());
                        System.out.println(accession + " written " + counter);
                        counter++;
                    } catch (Exception e) {
                        this.omittedAccessions.add(accession);
                    }
                }
            } catch (IOException x) {
                System.err.format("IOException: %s%n", x);
            }
            writer.write(']');
        } catch (IOException x) {
            System.err.format("IOException: %s%n", x);
        }
    }

    public List<String> getOmittedAccessions() {
        return omittedAccessions;
    }


}
