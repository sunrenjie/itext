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

public class Chap0202 {
    
    public static void main(String[] args) {
        
        System.out.println("Chapter 2 example 2: Phrases");
        
        // step 1: creation of a document-object
        Document document = new Document();
        
        try {
            
            // step 2:
            // we create a writer that listens to the document
            // and directs a PDF-stream to a file
            PdfWriter.getInstance(document, new FileOutputStream("Chap0202.pdf"));
            
            // step 3: we open the document
            document.open();
            
            // step 4: we add a paragraph to the document
            Phrase phrase0 = new Phrase();
            Phrase phrase1 = new Phrase("(1) this is a phrase\n");
            // In this example the leading is passed as a parameter
            Phrase phrase2 = new Phrase(24, "(2) this is a phrase with leading 24. You can only see the difference if the line is long enough. Do you see it? There is more space between this line and the previous one.\n");
            // When a Font is passed (explicitely or embedded in a chunk),
			// the default leading = 1.5 * size of the font
            Phrase phrase3 = new Phrase("(3) this is a phrase with a red, normal font Courier, size 20. As you can see the leading is automatically changed.\n", new Font(Font.COURIER, 20, Font.NORMAL, new Color(255, 0, 0)));
            Phrase phrase4 = new Phrase(new Chunk("(4) this is a phrase\n"));
            Phrase phrase5 = new Phrase(18, new Chunk("(5) this is a phrase in Helvetica, bold, red and size 16 with a given leading of 18 points.\n", new Font(Font.HELVETICA, 16, Font.BOLD, new Color(255, 0, 0))));
            // A Phrase can contains several chunks with different fonts
            Phrase phrase6 = new Phrase("(6)");
            Chunk chunk = new Chunk(" This is a font: ");
            phrase6.add(chunk);
            phrase6.add(new Chunk("Helvetica", new Font(Font.HELVETICA)));
            phrase6.add(chunk);
            phrase6.add(new Chunk("Times New Roman", new Font(Font.TIMES_NEW_ROMAN)));
            phrase6.add(chunk);
            phrase6.add(new Chunk("Courier", new Font(Font.COURIER)));
            phrase6.add(chunk);
            phrase6.add(new Chunk("Symbol", new Font(Font.SYMBOL)));
            phrase6.add(chunk);
            phrase6.add(new Chunk("ZapfDingBats", new Font(Font.ZAPFDINGBATS)));
            Phrase phrase7 = new Phrase("(7) if you don't add a newline yourself, all phrases are glued to eachother!");
            
            document.add(phrase1);
            document.add(phrase2);
            document.add(phrase3);
            document.add(phrase4);
            document.add(phrase5);
            document.add(phrase6);
            document.add(phrase7);
            
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
