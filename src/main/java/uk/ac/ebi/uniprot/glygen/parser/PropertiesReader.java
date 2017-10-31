package uk.ac.ebi.uniprot.glygen.parser;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class PropertiesReader {
    private static ResourceBundle res = ResourceBundle.getBundle("glygen");

    public static String getResourcesFolder() {
        try {
            return res.getString("resourcesFolder");
        } catch (NullPointerException | MissingResourceException | ClassCastException e) {
            return ("./src/main/resources/");
        }
    }
}
