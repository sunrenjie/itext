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

import com.lowagie.text.*;
import com.lowagie.text.pdf.*;

public class Chap0908 {
    
    public static void main(String[] args) {
        
        System.out.println("Chapter 9 example 8: changing fontwidths");
        
        // step 1: creation of a document-object
        Document document = new Document(PageSize.A4, 50, 50, 50, 50);
        try {
            
            // step 2:
            // we create a writer that listens to the document
            // and directs a PDF-stream to a file
            
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("font_widths.pdf"));
            
            // step 3: we open the document
            document.open();
            
            // step 4: we add content to the document
            BaseFont bf = BaseFont.createFont("Helvetica", "winansi", false, false, null, null);
            int widths[] = bf.getWidths();
            for (int k = 0; k < widths.length; ++k) {
                if (widths[k] != 0)
                    widths[k] = 1000;
            }
            bf.setForceWidthsOutput(true);
            document.add(new Paragraph("A big text to show Helvetica with fixed width.", new Font(bf)));
        
        }
        catch (Exception de) {
            de.printStackTrace();
        }
        // step 5: we close the document
        document.close();
    }
}
