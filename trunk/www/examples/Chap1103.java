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

public class Chap1103 {
    
    
    public static void main(String[] args) {
        
        System.out.println("Chapter 11 example 3: named actions");
        
        // step 1: creation of a document-object
        Document document = new Document(PageSize.A4, 50, 50, 50, 50);
        
        try {
            
            // step 2: we create a writer that listens to the document
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("Chap1103.pdf"));
            // step 3: we open the document
            document.open();
            // step 4: we add some content
            String application = "c:/winnt/notepad.exe";
            Paragraph p = new Paragraph(new Chunk("Click to open " + application).setAction(new PdfAction(application, null, null, null)));
            PdfPTable table = new PdfPTable(4);
            table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(new Phrase(new Chunk("First Page").setAction(new PdfAction(PdfAction.FIRSTPAGE))));
            table.addCell(new Phrase(new Chunk("Prev Page").setAction(new PdfAction(PdfAction.PREVPAGE))));
            table.addCell(new Phrase(new Chunk("Next Page").setAction(new PdfAction(PdfAction.NEXTPAGE))));
            table.addCell(new Phrase(new Chunk("Last Page").setAction(new PdfAction(PdfAction.LASTPAGE))));
            for (int k = 1; k <= 10; ++k) {
                document.add(new Paragraph("This is page " + k));
                document.add(table);
                document.add(p);
                document.newPage();
            }
        }
        catch (Exception de) {
            de.printStackTrace();
        }
        
        // step 5: we close the document
        document.close();
    }
}
