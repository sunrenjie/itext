/*
 * $Id$
 * $Name$
 *
 * This code is free software. It may only be copied or modified
 * if you include the following copyright notice:
 *
 * --> Copyright 2001 by Bruno Lowagie <--
 *
 * This code is part of the 'iText Tutorial'.
 * You can find the complete tutorial at the following address:
 * http://www.lowagie.com/iText/tutorial/
 *
 * This code is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 *
 * itext-questions@lists.sourceforge.net
 */

import java.io.*;

import com.lowagie.text.*;
import com.lowagie.text.pdf.*;

public class Chap1109 {
    
    public static void main(String[] args) {
        
        System.out.println("Chapter 11 example 9: outlines with actions");
        
        // step 1: creation of a document-object
        Document document = new Document(PageSize.A4, 50, 50, 50, 50);
        
        try {
            // step 2:
            // we create a writer that listens to the document
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("Chap1109.pdf"));
            // step 3: we open the document
            document.open();
            // step 4:
            // we add some content
            document.add(new Paragraph("Outline action example"));
            // we add the outline
            PdfContentByte cb = writer.getDirectContent();
            cb.addOutline(new PdfOutline(cb.getRootOutline(), new PdfAction("http://www.geocities.com/itextpdf"), "http://www.geocities.com/itextpdf"));
            cb.addOutline(new PdfOutline(cb.getRootOutline(), new PdfAction("http://www.lowagie.com/iText"), "http://www.lowagie.com/iText"));
            cb.addOutline(new PdfOutline(cb.getRootOutline(), new PdfAction("Chap1102b.pdf", 3), "Chap1102b.pdf"));
        }
        catch (Exception de) {
            de.printStackTrace();
        }
        
        // step 5: we close the document
        document.close();
    }
}
