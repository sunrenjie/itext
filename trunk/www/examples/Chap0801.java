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
import com.lowagie.text.rtf.RtfWriter;

public class Chap0801 {
    
    public static void main(String[] args) {
        
        System.out.println("Chapter 8 example 1: RTF");
        
        // step 1: creation of a document-object
        Document document = new Document();
        
        try {
            
            // step 2:
            // we create a writer that listens to the document
            // and directs a PDF-stream to a file
            RtfWriter.getInstance(document, new FileOutputStream("Chap0801.rtf"));
            
            // step 3: we open the document
            document.open();
            
            // step 4: we add a paragraph to the document
            Paragraph paragraph0 = new Paragraph();
            Paragraph paragraph1 = new Paragraph("(1) this is a Paragraph");
            // In this example the leading is passed as a parameter
            Paragraph paragraph2 = new Paragraph(24, "(2) this is a Paragraph with leading 24. You can only see the difference if the line is long enough. Do you see it? There is more space between this line and the previous one.");
            // When a Font is passed (explicitely or embedded in a chunk),
            // the default leading = 1.5 * size of the font
            Paragraph paragraph3 = new Paragraph("(3) this is a Paragraph with a red, normal font Courier, size 20. As you can see the leading is automatically changed.", new Font(Font.COURIER, 20, Font.NORMAL, new Color(255, 0, 0)));
            Paragraph paragraph4 = new Paragraph(new Chunk("(4) this is a Paragraph"));
            Paragraph paragraph5 = new Paragraph(18, new Chunk("(5) this is a Paragraph in Helvetica, bold, red and size 16 with a given leading of 18 points.", new Font(Font.HELVETICA, 16, Font.BOLD, new Color(255, 0, 0))));
            // A Paragraph can contains several chunks with different fonts
            Paragraph paragraph6 = new Paragraph("(6)");
            Chunk chunk = new Chunk(" This is a font: ");
            paragraph6.add(chunk);
            paragraph6.add(new Chunk("Helvetica", new Font(Font.HELVETICA)));
            paragraph6.add(chunk);
            paragraph6.add(new Chunk("Times New Roman", new Font(Font.TIMES_NEW_ROMAN)));
            paragraph6.add(chunk);
            paragraph6.add(new Chunk("Courier", new Font(Font.COURIER)));
            paragraph6.add(chunk);
            paragraph6.add(new Chunk("Symbol", new Font(Font.SYMBOL)));
            paragraph6.add(chunk);
            paragraph6.add(new Chunk("ZapfDingBats", new Font(Font.ZAPFDINGBATS)));
            Anchor anchor1 = new Anchor("website (external reference)", new Font(Font.HELVETICA, 12, Font.UNDERLINE, new Color(0, 0, 255)));
            anchor1.setReference("http://www.lowagie.com/iText/");
            document.add(paragraph1);
            document.add(paragraph2);
            document.add(paragraph3);
            document.add(paragraph4);
            document.add(paragraph5);
            document.add(paragraph6);
            document.add(anchor1);
            
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
