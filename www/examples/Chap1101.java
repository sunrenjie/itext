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
 * itext@lowagie.com
 */

import java.io.FileOutputStream;
import java.io.IOException;
import java.awt.Color;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.pdf.PdfContentByte;

public class Chap1101 {
    
    public static void main(String[] args) {
        
        System.out.println("Chapter 11 example 1: local goto");
        
        // step 1: creation of a document-object
        Document document = new Document();
        
        try {
            
            // step 2:
            // we create a writer that listens to the document
            // and directs a PDF-stream to a file
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("Chap1101.pdf"));
            
            // step 3: we open the document
            document.open();
            
            // step 4: we add some content
            
            Paragraph p1 = new Paragraph("We will do something special with this paragraph. If you click on ", FontFactory.getFont(FontFactory.HELVETICA, 12));
            p1.add(new Chunk("this word", FontFactory.getFont(FontFactory.HELVETICA, 12, Font.NORMAL, new Color(0, 0, 255))).setLocalGoto("test"));
            p1.add(" you will automatically jump to another location in this document.");
            Paragraph p2 = new Paragraph("blah, blah, blah");
            Paragraph p3 = new Paragraph("This paragraph contains a local ");
            p3.add(new Chunk("local destination", FontFactory.getFont(FontFactory.HELVETICA, 12, Font.NORMAL, new Color(0, 255, 0))).setLocalDestination("test"));
            document.add(p1);
            document.add(p2);
            document.add(p2);
            document.add(p2);
            document.add(p2);
            document.add(p2);
            document.add(p2);
            document.add(p2);
            document.add(p3);
        }
        catch(DocumentException de) {
            System.err.println(de.getMessage());
        }
        catch(IOException ioe) {
            System.err.println(ioe.getMessage());
        }
        
        // step 5: we close the document
        document.close();
    }
}
