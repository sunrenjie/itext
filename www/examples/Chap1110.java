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

import java.io.*;

import com.lowagie.text.*;
import com.lowagie.text.pdf.*;

public class Chap1110 {
    
    public static void main(String[] args) {
        
        System.out.println("Chapter 11 example 10: annotations");
        
        // step 1: creation of a document-object
        Document document = new Document(PageSize.A4, 50, 50, 50, 50);
        try {
            // step 2:
            // we create a writer that listens to the document
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("Chap1110.pdf"));
            // step 3: we open the document
            document.open();
            // step 4: we add some content
            
            PdfContentByte cb = writer.getDirectContent();
            // draw a rectangle
            cb.setRGBColorStroke(0x00, 0x00, 0xFF);
            cb.rectangle(100, 700, 100, 100);
            cb.stroke();
            Annotation annot = new Annotation(100f, 700f, 200f, 800f, "http://www.lowagie.com");
            document.add(annot);
            cb.setRGBColorStroke(0xFF, 0x00, 0x00);
            cb.rectangle(200, 700, 100, 100);
            cb.stroke();
            try {
                document.add(new Annotation(200f, 700f, 300f, 800f, new java.net.URL("http://www.lowagie.com")));
            }
            catch(Exception e) {
            }
            cb.setRGBColorStroke(0x00, 0xFF, 0x00);
            cb.rectangle(300, 700, 100, 100);
            cb.stroke();
            document.add(new Annotation(300f, 700f, 400f, 800f, "C://winnt/notepad.exe", null, null, null));
            cb.setRGBColorStroke(0x00, 0x00, 0xFF);
            cb.rectangle(100, 500, 100, 100);
            cb.stroke();
            document.add(new Annotation("annotation", "This annotation is placed on an absolute position", 100f, 500f, 200f, 600f));
            cb.setRGBColorStroke(0xFF, 0x00, 0x00);
            cb.rectangle(200, 500, 100, 100);
            cb.stroke();
            document.add(new Annotation(200f, 500f, 300f, 600f, "Chap1102a.pdf", "test"));
            cb.setRGBColorStroke(0x00, 0xFF, 0x00);
            cb.rectangle(300, 500, 100, 100);
            cb.stroke();
            document.add(new Annotation(300f, 500f, 400f, 600f, "Chap1102b.pdf", 3));
        }
        catch (Exception de) {
            de.printStackTrace();
        }
        
        // step 5: we close the document
        document.close();
    }
}
