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
 * itext@lowagie.com
 */

import java.io.*;

import com.lowagie.text.*;
import com.lowagie.text.pdf.*;

public class Encrypt extends java.lang.Object {
    
    /**
     * @param args the command line arguments
     */
    public static void main (String args[]) {
        if (args.length != 3) {
            System.err.println("This tools needs 3 parameters:\njava Encrypt srcfile destfile password");
        }
        else {
            try {
                // we create a reader for a certain document
                PdfReader reader = new PdfReader(args[0]);
                // we retrieve the total number of pages
                int n = reader.getNumberOfPages();
                System.out.println("There are " + n + " pages in the original file.");
                
                // step 1: creation of a document-object
                Document document = new Document(reader.getPageSizeWithRotation(1));
                // step 2: we create a writer that listens to the document
                PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(args[1]));
                writer.setEncryption(PdfWriter.STRENGTH128BITS, args[2], null, PdfWriter.AllowPrinting);
                // step 3: we open the document
                document.open();
                PdfContentByte cb = writer.getDirectContent();
                PdfImportedPage page;
                int rotation;
                int i = 0;
                // step 4: we add content
                while (i < n) {
                    i++;
                    document.setPageSize(reader.getPageSizeWithRotation(i));
                    document.newPage();
                    page = writer.getImportedPage(reader, i);
                    rotation = reader.getPageRotation(i);
                    if (rotation == 90 || rotation == 270) {
                        cb.addTemplate(page, 0, -1f, 1f, 0, 0, reader.getPageSizeWithRotation(i).height());
                    }
                    else {
                        cb.addTemplate(page, 1f, 0, 0, 1f, 0, 0);
                    }
                    System.out.println("Processed page " + i);
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
