/*
 * $Id$
 *
 * This code is part of the 'iText Tutorial'.
 * You can find the complete tutorial at the following address:
 * http://itextdocs.lowagie.com/tutorial/
 *
 * This code is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 *
 * itext-questions@lists.sourceforge.net
 */
package com.lowagie.examples.fonts.styles;

import java.io.FileOutputStream;
import java.io.IOException;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfWriter;

/**
 * Asking a font for the width and height of a textstring.
 */
public class WidthHeight {
    /**
     * Width and height of a textstring
     * @param args no arguments needed
     */
    public static void main(String[] args) {
        
        System.out.println("Width and height of a textstring");
        
        // step 1: creation of a document-object
        Document document = new Document();
        
        try {
            
            // step 2: creation of the writer-object
            PdfWriter.getInstance(document, new FileOutputStream("widthheight.pdf"));
            
            // step 3: we open the document
            document.open();
            
            // step 4: we add content to the document
            BaseFont bfComic = BaseFont.createFont("c:\\windows\\fonts\\comic.ttf", BaseFont.WINANSI, BaseFont.EMBEDDED);
            Font font = new Font(bfComic, 12);
            String text1 = "quick brown fox jumps";
            String text2 = " over ";
            String text3 = "the lazy dog";
            document.add(new Paragraph(text1, font));
            document.add(new Paragraph("width: " + bfComic.getWidthPoint(text1, 12)));
            document.add(new Paragraph("ascent: " + bfComic.getAscentPoint(text1, 12)));
            document.add(new Paragraph("descent: " + bfComic.getDescentPoint(text1, 12)));
            document.add(new Paragraph("height: " + (bfComic.getAscentPoint(text1, 12) - bfComic.getDescentPoint(text1, 12))));
            document.add(new Paragraph(text2, font));
            document.add(new Paragraph("width: " + bfComic.getWidthPoint(text2, 12)));
            document.add(new Paragraph("ascent: " + bfComic.getAscentPoint(text2, 12)));
            document.add(new Paragraph("descent: " + bfComic.getDescentPoint(text2, 12)));
            document.add(new Paragraph("height: " + (bfComic.getAscentPoint(text2, 12) - bfComic.getDescentPoint(text2, 12))));
            document.add(new Paragraph(text3, font));
            document.add(new Paragraph("width: " + bfComic.getWidthPoint(text3, 12)));
            document.add(new Paragraph("ascent: " + bfComic.getAscentPoint(text3, 12)));
            document.add(new Paragraph("descent: " + bfComic.getDescentPoint(text3, 12)));
            document.add(new Paragraph("height: " + (bfComic.getAscentPoint(text3, 12) - bfComic.getDescentPoint(text3, 12))));
            document.add(new Paragraph(text1 + text2 + text3, font));
            document.add(new Paragraph("width: " + bfComic.getWidthPoint(text1 + text2 + text3, 12)));
            document.add(new Paragraph("ascent: " + bfComic.getAscentPoint(text1 + text2 + text3, 12)));
            document.add(new Paragraph("descent: " + bfComic.getDescentPoint(text1 + text2 + text3, 12)));
            document.add(new Paragraph("height: " + (bfComic.getAscentPoint(text1 + text2 + text3, 12) - bfComic.getDescentPoint(text1 + text2 + text3, 12))));
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
