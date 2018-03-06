package uk.ac.ebi.uniprot.glygen.temp;

import org.apache.jena.rdf.model.*;
import org.apache.jena.riot.RDFDataMgr;
import static uk.ac.ebi.uniprot.glygen.temp.ReaderConstants.UP_NS;
import static uk.ac.ebi.uniprot.glygen.temp.ReaderConstants.UP_URI;
import static uk.ac.ebi.uniprot.glygen.temp.ReaderConstants.FF_LINE_IDS;

/**
 * Util class, reads a reviewed protein from http://purl.uniprot.org/uniprot/, in RDF format using Jena
 */
public class ReviewedProteinRDFReader {

    private static void listAllNodes(Model m) {
        int count = 0;
        NodeIterator iter = m.listObjects();
        while (iter.hasNext()) {
            RDFNode node = iter.next();
            ++count;
            if (node instanceof Literal) {
                Literal lit = (Literal)node;
                System.out.println("Literal -> " + lit.getDatatype() + " : " + lit.getString());
            } else if (node instanceof Resource) {
                Resource res = (Resource)node;
                System.out.println("Resource -> " + res);
                for (StmtIterator i = res.listProperties(); i.hasNext(); ) {
                    Statement s = i.next();
                    System.out.println( "\t" + s.getPredicate() + " with value " + s.getObject() );
                }
            } else {
                System.out.println(node.getClass() + " => " + node.toString());
            }
        }
        System.out.println("RDF node count: " + count);
    }

    private static void readIDLine(Model m, String accId) {
        Resource res = m.getResource( UP_URI + accId);
        if (res == null) {
            System.out.println(accId + " not found!");
        } else {
            Property mnemonic = m.getProperty(UP_NS + FF_LINE_IDS.ID.getKey());
            System.out.println("ID (mnemonic) = " + res.getProperty(mnemonic).getLiteral());
        }
    }

    private static void readACLine(Model m, String accId) {
        Resource res = m.getResource( UP_URI + accId);
        if (res == null) {
            System.out.println(accId + " not found!");
        } else {
            Property secAcc = m.getProperty(UP_NS + FF_LINE_IDS.AC.getKey());
            StringBuilder sb = new StringBuilder();
            for (NodeIterator i = m.listObjectsOfProperty(res, secAcc); i.hasNext(); ) {
                sb.append(",");
                String s = i.next().toString();
                sb.append(s.substring(s.lastIndexOf('/') + 1));
            }
            System.out.println("AC (sec accession number) = " + sb.toString().substring(1));
        }
    }

    private static void readDTLines(Model m, String accId) {
        Resource res = m.getResource( UP_URI + accId);
        if (res == null) {
            System.out.println(accId + " not found!");
        } else {
            Property dtCreated = m.getProperty(UP_NS + FF_LINE_IDS.DT_CR.getKey());
            System.out.println("DT (created) = " + res.getProperty(dtCreated).getLiteral().getValue());
            Property dtModified = m.getProperty(UP_NS + FF_LINE_IDS.DT_MD.getKey());
            System.out.println("DT (modified) = " + res.getProperty(dtModified).getLiteral().getValue());
            Property version = m.getProperty(UP_NS + FF_LINE_IDS.DT_VR.getKey());
            System.out.println("DT (version) = " + res.getProperty(version).getLiteral().getValue());
        }
    }

    public static void main(String[] args) {
        // P62805 - H4_HUMAN
        String accId = "P62805";
        Model m = RDFDataMgr.loadModel( UP_URI + accId + ".rdf" );

        ReviewedProteinRDFReader.listAllNodes(m);

        ReviewedProteinRDFReader.readIDLine(m, accId);
        ReviewedProteinRDFReader.readACLine(m, accId);
        ReviewedProteinRDFReader.readDTLines(m, accId);
    }
}
