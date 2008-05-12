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

package com.lowagie.examples.objects;

import java.io.FileOutputStream;
import java.io.IOException;

import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfWriter;

/**
 * Demonstrates some Paragraph functionality.
 * 
 * @author blowagie
 */

public class Paragraphs {

	/**
	 * Demonstrates some Paragraph functionality.
	 * 
	 * @param args
	 *            no arguments needed here
	 */
	public static void main(String[] args) {

		System.out.println("the Paragraph object");

		// step 1: creation of a document-object
		Document document = new Document();
		try {
			// step 2:
			// we create a writer that listens to the document
			PdfWriter.getInstance(document, new FileOutputStream("Paragraphs.pdf"));

			// step 3: we open the document
			document.open();
			// step 4:
			Paragraph p1 = new Paragraph(new Chunk(
					"This is my first paragraph. ",
					FontFactory.getFont(FontFactory.HELVETICA, 10)));
			p1.add("The leading of this paragraph is calculated automagically. ");
			p1.add("The default leading is 1.5 times the fontsize. ");
			p1.add(new Chunk("You can add chunks "));
			p1.add(new Phrase("or you can add phrases. "));
			p1.add(new Phrase(
				"Unless you change the leading with the method setLeading, the leading doesn't change if you add text with another leading. This can lead to some problems.",
				FontFactory.getFont(FontFactory.HELVETICA, 18)));
			document.add(p1);
			Paragraph p2 = new Paragraph(new Phrase(
					"This is my second paragraph. ", FontFactory.getFont(
							FontFactory.HELVETICA, 12)));
			p2.add("As you can see, it started on a new line.");
			document.add(p2);
			Paragraph p3 = new Paragraph("This is my third paragraph.",
					FontFactory.getFont(FontFactory.HELVETICA, 12));
			document.add(p3);
		} catch (DocumentException de) {
			System.err.println(de.getMessage());
		} catch (IOException ioe) {
			System.err.println(ioe.getMessage());
		}

		// step 5: we close the document
		document.close();
	}
}