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

public class Chap09_fontfactory_styles {
    
    public static void main(String[] args) {
        
        System.out.println("Chapter 9: class FontFactory and Styles");
        
        // step 1: creation of a document-object
        Document document = new Document();
        
        try {
            
            // step 2:
            // we create a writer that listens to the document
            // and directs a PDF-stream to a file
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("Chap09_fontfactory_styles.pdf"));
            
            // step 3: we open the document
            document.open();
            
            // step 4:
            // we add some content
            FontFactory.register("c:\\winnt\\fonts\\arial.ttf");
            FontFactory.register("c:\\winnt\\fonts\\arialbd.ttf");
            FontFactory.register("c:\\winnt\\fonts\\ariali.ttf");
            FontFactory.register("c:\\winnt\\fonts\\arialbi.ttf");
            Phrase myPhrase = new Phrase("This is font family Arial ", FontFactory.getFont("Arial", 8));
            myPhrase.add(new Phrase("italic ", FontFactory.getFont("Arial", 8, Font.ITALIC)));
            myPhrase.add(new Phrase("bold ", FontFactory.getFont("Arial", 8, Font.BOLD)));
            myPhrase.add(new Phrase("bolditalic", FontFactory.getFont("Arial", 8, Font.BOLDITALIC)));
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