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

public class Chap13_pdfreader {

    public static void main(String[] args) {
        System.out.println("Chapter 13 example pdfreader: reading an existing PDF file");
        try {
            // we create a reader for a certain document
            PdfReader reader = new PdfReader("Chap0703.pdf");
            // we retrieve the total number of pages
            int n = reader.getNumberOfPages();
            // we retrieve the size of the first page
            Rectangle psize = reader.getPageSize(1);
            float width = psize.width();
            float height = psize.height();
            
            // step 1: creation of a document-object
            Document document = new Document(psize, 50, 50, 50, 50);
            // step 2: we create a writer that listens to the document
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("Chap13_pdfreader.pdf"));
            // step 3: we open the document
            try {
                Watermark watermark = new Watermark(Image.getInstance("watermark.jpg"), 200, 320);
                document.add(watermark);
            }
            catch(Exception e) {
                System.err.println("Are you sure you have the file 'watermark.jpg' in the right path?");
            }
            document.open();
            // step 4: we add content
            PdfContentByte cb = writer.getDirectContent();
            int i = 0;
            int p = 0;
            System.out.println("There are " + n + " pages in the document.");
            while (i < n) {
                document.newPage();
                p++;
                i++;
                PdfImportedPage page1 = writer.getImportedPage(reader, i);
                cb.addTemplate(page1, .5f, 0, 0, .5f, 0, height / 2);
                System.err.println("processed page " + i);
                if (i < n) {
                    i++;
                    PdfImportedPage page2 = writer.getImportedPage(reader, i);
                    cb.addTemplate(page2, .5f, 0, 0, .5f, width / 2, height / 2);
                    System.err.println("processed page " + i);
                }
                if (i < n) {
                    i++;
                    PdfImportedPage page3 = writer.getImportedPage(reader, i);
                    cb.addTemplate(page3, .5f, 0, 0, .5f, 0, 0);
                    System.err.println("processed page " + i);
                }
                if (i < n) {
                    i++;
                    PdfImportedPage page4 = writer.getImportedPage(reader, i);
                    cb.addTemplate(page4, .5f, 0, 0, .5f, width / 2, 0);
                    System.err.println("processed page " + i);
                }
                cb.setRGBColorStroke(255, 0, 0);
                cb.moveTo(0, height / 2);
                cb.lineTo(width, height / 2);
                cb.stroke();
                cb.moveTo(width / 2, height);
                cb.lineTo(width / 2, 0);
                cb.stroke();
                BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
                cb.beginText();
                cb.setFontAndSize(bf, 14);
                cb.showTextAligned(PdfContentByte.ALIGN_CENTER, "page " + p + " of " + ((n / 4) + (n % 4 > 0? 1 : 0)), width / 2, 40, 0);
                cb.endText();
            }
            // step 5: we close the document
            document.close();
        }
        catch (Exception de) {
            de.printStackTrace();
        }
    }
}
