package uk.ac.ebi.uniprot.glygen.util;

import org.apache.jena.riot.Lang;
import org.apache.jena.riot.RDFFormat;

/**
 * Created by Leyla on 06/11/2017.
 */
public enum SyntaxRDF {
    RDFXML("RDFXML")
    ,JSONLD("JSONLD")
    ;

    private final Lang lang;
    private final String format;
    private final String extension;
    SyntaxRDF(String rdfSyntax) {
        this.lang = rdfSyntax.equalsIgnoreCase("JSONLD") ? Lang.JSONLD : Lang.RDFXML;
        this.format = this.lang.equals(Lang.JSONLD) ? "JSONLD" : "RDF/XML-ABBREV";
        this.extension = this.lang.equals(Lang.JSONLD) ? ".jsonld" : ".rdf";
    }

    public Lang getLang() {
        return this.lang;
    }

    public String getExtension() {
        return this.extension;
    }

    public String getFormat() {
        return this.format;
    }

}
