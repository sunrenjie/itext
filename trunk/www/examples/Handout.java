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

public class Handout extends java.lang.Object {
    
    /**
     * @param args the command line arguments
     */
    public static void main (String args[]) {
        if (args.length != 3) {
            System.err.println("This tools needs 3 parameters:\njava Handout srcfile destfile pages");
        }
        else {
            try {       
                int pages = Integer.parseInt(args[2]);         
                if (pages < 2 || pages > 8) {
                    throw new DocumentException("You can't have " + pages + " pages on one page (minimum 2; maximum 8).");
                }
                
                float x1 = 30f;
                float x2 = 280f;
                float x3 = 320f;
                float x4 = 565f;
                
                float[] y1 = new float[pages];
                float[] y2 = new float[pages];
                
                float height = (778f - (20f * (pages - 1))) / pages;
                y1[0] = 812f;
                y2[0] = 812f - height;
                
                for (int i = 1; i < pages; i++) {
                    y1[i] = y2[i - 1] - 20f;
                    y2[i] = y1[i] - height;
                }
                
                // we create a reader for a certain document
                PdfReader reader = new PdfReader(args[0]);
                // we retrieve the total number of pages
                int n = reader.getNumberOfPages();
                System.out.println("There are " + n + " pages in the original file.");
                
                // step 1: creation of a document-object
                Document document = new Document(PageSize.A4);
                // step 2: we create a writer that listens to the document
                PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(args[1]));
                // step 3: we open the document
                document.open();
                PdfContentByte cb = writer.getDirectContent();
                PdfImportedPage page;
                int i = 0;
                int p = 0;
                // step 4: we add content
                while (i < n) {
                    i++;
                    Rectangle rect = reader.getPageSize(i);
                    float factorx = (x2 - x1) / rect.width();
                    float factory = (y1[p] - y2[p]) / rect.height();
                    float factor = (factorx < factory ? factorx : factory);
                    float dx = (factorx == factor ? 0f : ((x2 - x1) - rect.width() * factor) / 2f);
                    float dy = (factory == factor ? 0f : ((y1[p] - y2[p]) - rect.height() * factor) / 2f);
                    page = writer.getImportedPage(reader, i);
                    cb.addTemplate(page, factor, 0, 0, factor, x1 + dx, y2[p] + dy);
                    cb.setRGBColorStroke(0xC0, 0xC0, 0xC0);
                    cb.rectangle(x3 - 5f, y2[p] - 5f, x4 - x3 + 10f, y1[p] - y2[p] + 10f);
                    for (float l = y1[p] - 19; l > y2[p]; l -= 16) {
                        cb.moveTo(x3, l);
                        cb.lineTo(x4, l);
                    }
                    cb.rectangle(x1 + dx, y2[p] + dy, rect.width() * factor, rect.height() * factor);
                    cb.stroke();
                    System.out.println("Processed page " + i);
                    p++;
                    if (p == pages) {
                        p = 0;
                        document.newPage();
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
