/*
 * $Id$
 * $Name$
 *
 * This code is free software. It may only be copied or modified
 * if you include the following copyright notice:
 *
 * --> Copyright 2002-2005 by Paulo Soares and Bruno Lowagie <--
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
package com.lowagie.examples.objects.fonts;

import java.io.FileOutputStream;

import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfWriter;

/**
 * Using an Open Type Font (CFF only).
 */
public class OpenTypeFont {
	/**
	 * Using oth
	 * @param args no arguments needed
	 */
	public static void main(String[] args) {
		// step 1
        Document document = new Document(PageSize.A4, 50, 50, 50, 50);
        try {
        	// step 2
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("opentypefont.pdf"));
            // step 3
            document.open();
            // step 4
            BaseFont bf = BaseFont.createFont("liz.otf", BaseFont.CP1252, true);
            String text = "Some text with the otf font LIZ.";
            document.add(new Paragraph(text, new Font(bf, 14)));
        }
        catch (Exception de) {
            de.printStackTrace();
        }
        // step 5
        document.close();
	}
}
