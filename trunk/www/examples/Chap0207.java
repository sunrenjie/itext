/*
 * $Id$
 * $Name$
 *
 * This code is free software. It may only be copied or modified
 * if you include the following copyright notice:
 *
 * --> Copyright 2001 by Bruno Lowagie, Vincent Bouvelle <--
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

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.pdf.PdfPageEventHelper;

public class Chap0207 {
    
    public static void main(String[] args) {
        
        System.out.println("Chapter 2 example 7: font propagation");
        
        // step 1: creation of a document-object
        Document document = new Document();
        
        try {
            
            // step 2:
            // we create a writer that listens to the document
            // and directs a PDF-stream to a file
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("Chap0207.pdf"));
            
            // step 3: we open the document
            document.open();
            
            // step 4:
            // we add some content
            Paragraph myParagraph = new Paragraph("Hello 1! ", FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.BOLD));
            myParagraph.add(new Paragraph("This is the end of the sentence.",FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.ITALIC)));
            document.add(myParagraph);
            
            Phrase myPhrase = new Phrase("Hello 2! ", FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.BOLD));
            myPhrase.add(new Phrase("This is the end of the sentence.\n", FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.ITALIC)));
            document.add(myPhrase);
            
            myParagraph = new Paragraph(12);
            myParagraph.add(new Paragraph("Hello 3! ", FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.BOLD)));
            myParagraph.add(new Paragraph("This is the end of the sentence.",FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.ITALIC)));
            document.add(myParagraph);
            
            myParagraph = new Paragraph(12);
            myParagraph.add(new Phrase("Hello 4! ", FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.BOLD)));
            myParagraph.add(new Phrase("This is the end of the sentence.",FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.ITALIC)));
            document.add(myParagraph);
            
            myPhrase = new Phrase(12);
            myPhrase.add(new Phrase("Hello 5! ", FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.BOLD)));
            myPhrase.add(new Phrase("This is the end of the sentence.\n",FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.ITALIC)));
            document.add(myPhrase);
            
            myPhrase = new Phrase("Hello 6! ", FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.BOLD));
            document.add(myPhrase);
            myPhrase = new Phrase("This is the end of the sentence.", FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.ITALIC));
            document.add(myPhrase);
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