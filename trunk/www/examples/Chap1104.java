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

public class Chap1104 {
    
    public static void main(String[] args) {
        
        System.out.println("Chapter 11 example 4: open action");
        
        // step 1: creation of a document-object
        Document document = new Document(PageSize.A4, 50, 50, 50, 50);
        try {
            // step 2: we create a writer that listens to the document
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("Chap1104.pdf"));
            // step 3: we open the document
            document.open();
            // step 4: we add some content
            document.add(new Paragraph("Page 1"));
            document.newPage();
            document.add(new Paragraph("This PDF file jumps directly to page 2 when opened"));
            PdfContentByte cb = writer.getDirectContent();
            cb.localDestination("page2", new PdfDestination(PdfDestination.XYZ, -1, 10000, 0));
            writer.setOpenAction("page2");
        }
        catch (Exception de) {
            de.printStackTrace();
        }
        
        // step 5: we close the document
        document.close();
    }
}
