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
 * ir-arch Bruno Lowagie,
 * Adolf Baeyensstraat 121
 * 9040 Sint-Amandsberg
 * BELGIUM
 * tel. +32 (0)9 228.10.97
 * bruno@lowagie.com
 */

import java.awt.Color;
import java.io.FileOutputStream;
import java.io.IOException;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.html.HtmlWriter;

public class Chap0207 {
    
    public static void main(String[] args) {
        
        System.out.println("Chapter 2 example 7: Anchors");
        
        // step 1: creation of a document-object
        Document document = new Document();
        
        try {
            
            // step 2:
            // we create a writer that listens to the document
            // and directs a PDF-stream to a file
            PdfWriter.getInstance(document, new FileOutputStream("Chap0207.pdf"));
            HtmlWriter.getInstance(document, new FileOutputStream("Chap0207.html"));
            
            // step 3: we open the document
            document.open();
            
            // step 4:
            Paragraph paragraph = new Paragraph("Please visit my ");
            Anchor anchor1 = new Anchor("website (external reference)",
            new Font(Font.HELVETICA, 12, Font.UNDERLINE, new Color(0, 0, 255)));
            anchor1.setReference("http://www.lowagie.com/iText/");
            anchor1.setName("top");
            paragraph.add(anchor1);
            paragraph.add(new Chunk(".\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n"));
            document.add(paragraph);
            Anchor anchor2 = new Anchor("please jump to a local destination",
            new Font(Font.HELVETICA, 12, Font.NORMAL, new Color(0, 0, 255)));
            anchor2.setReference("#top");
            document.add(anchor2);
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