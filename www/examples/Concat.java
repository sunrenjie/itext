/*
 * $Id$
 * $Name$
 *
 * This code is free software. It may only be copied or modified
 * if you include the following copyright notice:
 *
 * --> Copyright 2002 by Bruno Lowagie <--
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

public class Concat extends java.lang.Object {
    
    /**
     * @param args the command line arguments
     */
    public static void main (String args[]) {
        if (args.length < 3) {
            System.err.println("This tools needs at least 3 parameters:\njava Concat destfile file1 file2 [file3 ...]");
        }
        else {
            try {
                int f = 1;
                // we create a reader for a certain document
                PdfReader reader = new PdfReader(args[f]);
                // we retrieve the total number of pages
                int n = reader.getNumberOfPages();
                System.out.println("There are " + n + " pages in the original file.");
                
                // step 1: creation of a document-object
                Document document = new Document(reader.getPageSize(1));
                // step 2: we create a writer that listens to the document
                PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(args[0]));
                // step 3: we open the document
                document.open();
                PdfContentByte cb = writer.getDirectContent();
                PdfImportedPage page;
                // step 4: we add content
                while (f < args.length) {
                    int i = 0;
                    while (i < n) {
                        i++;
                        document.setPageSize(reader.getPageSize(i));
                        document.newPage();
                        page = writer.getImportedPage(reader, i);
                        cb.addTemplate(page, 1f, 0, 0, 1f, 0, 0);
                        System.out.println("Processed page " + i);
                    }
                    f++;
                    if (f < args.length) {
                        reader = new PdfReader(args[f]);
                        // we retrieve the total number of pages
                        n = reader.getNumberOfPages();
                        System.out.println("There are " + n + " pages in the original file.");
                    }
                }
                // step 5: we close the document
                document.close();
            }
            catch(Exception e) {
                System.err.println(e.getClass().getName() + ": " + e.getMessage());
            }
        }
    }
    
}
