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

package com.lowagie.examples.html;

import java.io.FileOutputStream;
import java.io.IOException;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Paragraph;
import com.lowagie.text.html.HtmlWriter;

/**
 * Generates a simple PDF file with metadata.
 * 
 * @author blowagie
 */

public class HelloWorldMeta {

	/**
	 * Generates a PDF file with metadata
	 * 
	 * @param args no arguments needed here
	 */
	public static void main(String[] args) {

		System.out.println("Metadata");

		// step 1: creation of a document-object
		Document document = new Document();
		try {
			// step 2:
			// we create a writer that listens to the document
			// and directs a PDF-stream to a file
			HtmlWriter.getInstance(document,
					new FileOutputStream("HelloWorldMeta.html"));

			// step 3: we add some metadata open the document
			// standard meta information
			document.addTitle("Hello World example");
            document.addAuthor("Bruno Lowagie");
            document.addSubject("This example explains step 3 in Chapter 1");
            document.addKeywords("Metadata, iText, step 3, tutorial");
            // custom (HTML) meta information
            document.addHeader("Expires", "0");
            // meta information that will be in a comment section in HTML
            document.addCreator("My program using iText");
			document.open();
			// step 4: we add a paragraph to the document
			document.add(new Paragraph("Hello World"));
		} catch (DocumentException de) {
			System.err.println(de.getMessage());
		} catch (IOException ioe) {
			System.err.println(ioe.getMessage());
		}

		// step 5: we close the document
		document.close();
	}
}