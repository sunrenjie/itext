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

import java.io.*;

import java.net.URL;

import com.lowagie.text.*;
import com.lowagie.text.pdf.*;

public class Chap0616 {
    
    public static void main(String[] args) {
        
        System.out.println("Chapter 6 example 16: images and annotations");
        
        // step 1: creation of a document-object
        Document document = new Document(PageSize.A4, 50, 50, 50, 50);
        try {
            // step 2:
            // we create a writer that listens to the document
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("Chap0616.pdf"));
            // step 3: we open the document
            document.open();
            // step 4: we add some content
            Image wmf = Image.getInstance(new URL("http://www.lowagie.com/iText/examples/harbour.wmf"));
            wmf.setAnnotation(new Annotation(0, 0, 0, 0, "http://www.lowagie.com"));
			wmf.setAbsolutePosition(100f, 600f);
			document.add(wmf);
            Image gif = Image.getInstance(new URL("http://www.lowagie.com/iText/examples/vonnegut.gif"));
            gif.setAnnotation(new Annotation(0, 0, 0, 0, "Chap1102b.pdf", 3));
			gif.setAbsolutePosition(100f, 400f);
			document.add(gif);
            Image jpeg = Image.getInstance(new URL("http://www.lowagie.com/iText/examples/myKids.jpg"));
            jpeg.setAnnotation(new Annotation("picture", "These are my children", 0, 0, 0, 0));
			jpeg.setAbsolutePosition(100f, 150f);
			document.add(jpeg);
            Image png = Image.getInstance(new URL("http://www.lowagie.com/iText/examples/hitchcock.png"));
            png.setAnnotation(new Annotation(0, 0, 0, 0, "Chap1102a.pdf", "test"));
			png.setAbsolutePosition(350f, 250f);
			document.add(png);
        }
        catch (Exception de) {
            de.printStackTrace();
        }
        
        // step 5: we close the document
        document.close();
    }
}
