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

import java.awt.Color;
import java.io.FileOutputStream;
import java.io.IOException;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;

public class Chap0201 {
    
    public static void main(String[] args) {
        
        System.out.println("Chapter 2 example 1: Chunks and fonts");
        
        // step 1: creation of a document-object
        Document document = new Document();
        
        try {
            
            // step 2:
            // we create a writer that listens to the document
            // and directs a PDF-stream to a file
            PdfWriter.getInstance(document, new FileOutputStream("Chap0201.pdf"));
            
            // step 3: we open the document
            document.open();
            
            // step 4: we add content to the document
            Font[] fonts = new Font[14];
            fonts[0] = FontFactory.getFont(FontFactory.COURIER, 12, Font.NORMAL);
            fonts[1] = FontFactory.getFont(FontFactory.COURIER, 12, Font.BOLD);
            fonts[2] = FontFactory.getFont(FontFactory.COURIER, 12, Font.ITALIC);
            fonts[3] = FontFactory.getFont(FontFactory.COURIER, 12, Font.BOLD | Font.ITALIC);
            fonts[4] = FontFactory.getFont(FontFactory.HELVETICA, 12, Font.NORMAL);
            fonts[5] = FontFactory.getFont(FontFactory.HELVETICA, 12, Font.BOLD);
            fonts[6] = FontFactory.getFont(FontFactory.HELVETICA, 12, Font.ITALIC);
            fonts[7] = FontFactory.getFont(FontFactory.HELVETICA, 12, Font.BOLD | Font.ITALIC);
            fonts[8] = FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.NORMAL);
            fonts[9] = FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.BOLD);
            fonts[10] = FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.ITALIC);
            fonts[11] = FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.BOLD | Font.ITALIC);
            fonts[12] = FontFactory.getFont(FontFactory.SYMBOL, 12, Font.NORMAL);
            fonts[13] = FontFactory.getFont(FontFactory.ZAPFDINGBATS, 12, Font.NORMAL);
            for (int i = 0; i < 14; i++) {
                Chunk chunk = new Chunk("This is some", fonts[i]);
                document.add(new Phrase(chunk));
                document.add(new Phrase(new Chunk(" font. ",
                fonts[i]).setTextRise((i % 2 == 0) ? -6 : 6)));
            }
            document.add(new Phrase(new Chunk("This text is underlined",
            FontFactory.getFont(FontFactory.HELVETICA, 12, Font.UNDERLINE))));
            document.add(new Phrase(new Chunk("This font is of type ITALIC | STRIKETHRU",
            FontFactory.getFont(FontFactory.HELVETICA, 12, Font.ITALIC | Font.STRIKETHRU))));
            Chunk ck = new Chunk("This text has a yellow background color", FontFactory.getFont(FontFactory.HELVETICA, 12));
            ck.setBackground(new Color(0xFF, 0xFF, 0x00));
            document.add(new Phrase(ck));
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