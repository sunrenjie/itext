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

import java.io.*;

import com.lowagie.text.*;
import com.lowagie.text.pdf.*;

public class Chap1110 {
    
    public static void main(String[] args) {
        
        System.out.println("Chapter 11 example 10: page labels");
        
        // step 1: creation of a document-object
        Document document = new Document(PageSize.A4, 50, 50, 50, 50);
        try {
            // step 2:
            // we create a writer that listens to the document
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("Chap1110.pdf"));
            // step 3: we open the document
            document.open();
            // step 4:
            // we add some content
            for (int k = 1; k <= 10; ++k) {
                document.add(new Paragraph("This document has the logical page numbers: i,ii,iii,iv,1,2,3,A-8,A-9,A-10\nReal page " + k));
                document.newPage();
            }
            PdfPageLabels pageLabels = new PdfPageLabels();
            pageLabels.addPageLabel(1, PdfPageLabels.LOWERCASE_ROMAN_NUMERALS);
            pageLabels.addPageLabel(5, PdfPageLabels.DECIMAL_ARABIC_NUMERALS);
            pageLabels.addPageLabel(8, PdfPageLabels.DECIMAL_ARABIC_NUMERALS, "A-", 8);
            writer.setPageLabels(pageLabels);
        }
        catch (Exception de) {
            de.printStackTrace();
        }
        
        // step 5: we close the document
        document.close();
    }
}
