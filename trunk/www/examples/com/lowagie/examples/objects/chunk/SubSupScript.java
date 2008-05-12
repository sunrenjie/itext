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

package com.lowagie.examples.objects.chunk;

import java.awt.Color;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.StringTokenizer;

import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfWriter;

/**
 * Demonstrates how sub- and superscript works.
 * 
 * @author blowagie
 */

public class SubSupScript {

	/**
	 * Demonstrates the use of Subscript and superscript.
	 * 
	 * @param args no arguments needed here
	 */
	public static void main(String[] args) {

		System.out.println("Sub- and Superscript");

		// step 1: creation of a document-object
		Document document = new Document();
		try {
			// step 2:
			// we create a writer that listens to the document
			PdfWriter.getInstance(document,
					new FileOutputStream("SubSupScript.pdf"));

			// step 3: we open the document
			document.open();
			// step 4:
			String s = "quick brown fox jumps over the lazy dog";
			StringTokenizer st = new StringTokenizer(s, " ");
			float textrise = 6.0f;
			Chunk c;
			while (st.hasMoreTokens()) {
				c = new Chunk(st.nextToken());
				c.setTextRise(textrise);
				c.setUnderline(new Color(0xC0, 0xC0, 0xC0), 0.2f, 0.0f, 0.0f, 0.0f, PdfContentByte.LINE_CAP_BUTT);
				document.add(c);
				textrise -= 2.0f;
			}
		} catch (DocumentException de) {
			System.err.println(de.getMessage());
		} catch (IOException ioe) {
			System.err.println(ioe.getMessage());
		}

		// step 5: we close the document
		document.close();
	}
}