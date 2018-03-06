package uk.ac.ebi.uniprot.glygen.temp;

/**
 * Constants related to protein RDF reader
 */
public class ReaderConstants {
    public static String UP_NS = "http://purl.uniprot.org/core/";
    public static String UP_URI = "http://purl.uniprot.org/uniprot/";

    public enum FF_LINE_IDS {
        ID("mnemonic"),
        AC("replaces"),
        DT_CR("created"),
        DT_MD("modified"),
        DT_VR("version");

        private String key;

        FF_LINE_IDS(String str){
            this.key = str;
        }

        public String getKey(){
            return key;
        }
    }
}
