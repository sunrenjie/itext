/*
 * $Id$
 * $Name$
 *
 * This code is free software. It may only be copied or modified
 * if you include the following copyright notice:
 *
 * --> Copyright 2001 by Paulo Soares <--
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

public class Chap1009 {
    
    public static void main(String[] args) {
        
        System.out.println("Chapter 10 example 9: a PdfPTable at an absolute position");
        
        // step 1: creation of a document-object
        Document document = new Document(PageSize.A4, 50, 50, 50, 50);
        try {
            // step 2:  we create a writer that listens to the document
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("Chap1009.pdf"));
            // step 3: we open the document
            document.open();
            // step 4: we add some content
            PdfPTable table = new PdfPTable(4);
            table.getDefaultCell().setBorder(Rectangle.LEFT | Rectangle.RIGHT);
            for (int k = 0; k < 24; ++k) {
                table.addCell("cell " + k);
            }
            table.setTotalWidth(300);
            table.writeSelectedRows(0, -1, 100, 600, writer.getDirectContent());
            // step 5: we close the document
            document.close();
        }
        catch (Exception de) {
            de.printStackTrace();
        }
    }
}
