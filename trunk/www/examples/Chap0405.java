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

import java.awt.Color;
import java.io.FileOutputStream;
import java.io.IOException;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;

public class Chap0405 {
    
    public static void main(String[] args) {
        
        System.out.println("Chapter 4 example 5: page borders and horizontal lines");
        
        // step 1: creation of a document-object
        Document document = new Document();
        
        try {
            
            // step 2: we create a writer that listens to the document
            PdfWriter.getInstance(document, new FileOutputStream("Chap0405.pdf"));
            
            // step 3: we open the document
            document.open();
            
            // step 4: we add a paragraph to the document
            Graphic g = new Graphic();
            g.setBorder(3f, 5f);
            document.add(g);
            document.add(new Paragraph("Hello World"));
            document.add(new Paragraph("Hello World\n\n"));
            g = new Graphic();
            g.setHorizontalLine(5f, 100f);
            document.add(g);
            document.add(new Paragraph("Hello World"));
            document.add(new Paragraph("Hello World\n\n"));
            g = new Graphic();
            g.setHorizontalLine(2f, 80f, new Color(0xFF, 0x00, 0x00));
            document.add(g);
            document.add(new Paragraph("Hello World"));
            
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