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
package com.lowagie.examples.directcontent.colors;

import java.awt.Color;
import java.io.FileOutputStream;

import com.lowagie.text.Document;
import com.lowagie.text.PageSize;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfShading;
import com.lowagie.text.pdf.PdfShadingPattern;
import com.lowagie.text.pdf.PdfWriter;

/**
 * Shading example
 */
public class ShadingPattern {
    /**
     * Shading example.
     * @param args no arguments needed
     */
    public static void main(String[] args) {
        System.out.println("Shading pattern");
        Document document = new Document(PageSize.A4, 50, 50, 50, 50);
        try {
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("shading_pattern.pdf"));
            document.open();

            PdfShading shading = PdfShading.simpleAxial(writer, 100, 100, 400, 100, Color.red, Color.cyan);
            PdfShadingPattern shadingPattern = new PdfShadingPattern(shading);
            PdfContentByte cb = writer.getDirectContent();
            BaseFont bf = BaseFont.createFont(BaseFont.TIMES_BOLD, BaseFont.WINANSI, false);
            cb.setShadingFill(shadingPattern);
            cb.beginText();
            cb.setTextMatrix(100, 100);
            cb.setFontAndSize(bf, 40);
            cb.showText("Look at this text!");
            cb.endText();
            PdfShading shadingR = PdfShading.simpleRadial(writer, 200, 500, 50, 300, 500, 100, new Color(255, 247, 148), new Color(247, 138, 107), false, false);
            cb.paintShading(shadingR);
            cb.sanityCheck();
            document.close();
        }
        catch (Exception de) {
            de.printStackTrace();
        }
    }
}
