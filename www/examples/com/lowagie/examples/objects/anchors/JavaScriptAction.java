/*
 * $Id$
 * $Name$
 *
 * This code is free software. It may only be copied or modified
 * if you include the following copyright notice:
 *
 * --> Copyright 2001-2005 by Paulo Soares and Bruno Lowagie <--
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

package com.lowagie.examples.objects.anchors;

import java.io.FileOutputStream;

import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfAction;
import com.lowagie.text.pdf.PdfWriter;

/**
 * Creates a documents with different named actions.
 * 
 * @author blowagie
 */

public class JavaScriptAction {

	/**
	 * Creates a document with Named Actions.
	 * 
	 * @param args
	 *            no arguments needed
	 */
	public static void main(String[] args) {

		System.out.println("JavaScript");

		// step 1: creation of a document-object
		Document document = new Document();

		try {

			// step 2:
			PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(
					"JavaScriptAction.pdf"));
			// step 3: we add Javascript as Metadata and we open the document        
			document.open();
			// step 4: we add some content
			Paragraph p = new Paragraph(new Chunk("Click to say Hello")
					.setAction(PdfAction.javaScript("app.alert('Hello');\r", writer)));
			document.add(p);
		} catch (Exception de) {
			de.printStackTrace();
		}

		// step 5: we close the document
		document.close();

	}
}