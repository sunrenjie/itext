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

import java.io.FileOutputStream;
import java.io.IOException;
import java.awt.Color;
import java.net.URL;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;

public class Chap1102 {
    
    public static void main(String[] args) {
        
        System.out.println("Chapter 11 example 2: anchor and remote goto");
        
        // step 1: creation of a document-object
        Document document = new Document();
        
        try {
            
            // step 2:
            // we create a writer that listens to the document
            // and directs a PDF-stream to a file
            PdfWriter writerA = PdfWriter.getInstance(document, new FileOutputStream("Chap1102a.pdf"));
            PdfWriter writerB = PdfWriter.getInstance(document, new FileOutputStream("Chap1102b.pdf"));
            
            // step 3: we open the document
            document.open();
            
            // step 4: we add some content
            
            Paragraph p1 = new Paragraph("We discussed anchors in chapter 3, but you can add an URL to a chunk to to make it an ", FontFactory.getFont(FontFactory.HELVETICA, 12));
            p1.add(new Chunk("anchor", FontFactory.getFont(FontFactory.HELVETICA, 12, Font.UNDERLINE, new Color(0, 0, 255))).setAnchor(new URL("http://www.lowagie.com/iText/")));
            p1.add(" you will automatically jump to another location in this document.");
            Paragraph p2 = new Paragraph("blah, blah, blah");
            Paragraph p3a = new Paragraph("This paragraph contains a ");
            p3a.add(new Chunk("local destination in document A", FontFactory.getFont(FontFactory.HELVETICA, 12, Font.NORMAL, new Color(0, 255, 0))).setLocalDestination("test"));
            Paragraph p3b = new Paragraph("This paragraph contains a local ");
            p3b.add(new Chunk("local destination in document B", FontFactory.getFont(FontFactory.HELVETICA, 12, Font.NORMAL, new Color(0, 255, 0))).setLocalDestination("test"));
            Paragraph p4a = new Paragraph(new Chunk("Click this paragraph to go to a certain destination on document B").setRemoteGoto("Chap1102b.pdf", "test"));
            Paragraph p4b = new Paragraph(new Chunk("Click this paragraph to go to a certain destination on document A").setRemoteGoto("Chap1102a.pdf", "test"));
            Paragraph p5a = new Paragraph("you can also jump to a ");
            p5a.add(new Chunk("specific page on another document", FontFactory.getFont(FontFactory.HELVETICA, 12, Font.ITALIC)).setRemoteGoto("Chap1102b.pdf", 3));
            document.add(p1);
            document.add(p2);
            document.add(p2);
            document.add(p2);
            document.add(p2);
            document.add(p2);
            document.add(p2);
            document.add(p2);
            writerA.pause();
            document.add(p4b);
            writerA.resume();
            writerB.pause();
            document.add(p4a);
            document.add(p5a);
            writerB.resume();
            document.add(p2);
            document.add(p2);
            document.add(p2);
            document.add(p2);
            writerA.pause();
            document.add(p3b);
            document.add(p2);
            document.add(p2);
            document.newPage();
            document.add(p2);
            document.add(p2);
            document.newPage();
            writerA.resume();
            writerB.pause();
            document.add(p3a);
            writerB.resume();
            document.add(p2);
            document.add(p2);
        }
        catch(DocumentException de) {
            System.err.println(de.getMessage());
        }
        catch(IOException ioe) {
            System.err.println(ioe.getMessage());
        }
        catch(Exception e) {
            System.err.println(e.getMessage());
        }
        
        // step 5: we close the document
        document.close();
    }
}
