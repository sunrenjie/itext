/*
 * $Id$
 * $Name$
 *
 * This code is free software. It may only be copied or modified
 * if you include the following copyright notice:
 *
 * --> Copyright 2001 by Bruno Lowagie <--
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

import java.io.FileOutputStream;
import java.io.IOException;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;

import com.lowagie.text.html.HtmlWriter;

public class Chap0106 {

	public static void main(String[] args) {

		System.out.println("Chapter 1 example 6: Meta Information");

		// step 1: creation of a document-object
		Document document = new Document();

		try {

			// step 2:
			// we create a writer that listens to the document
			// and directs a PDF-stream to a file

			PdfWriter.getInstance(document, new FileOutputStream("Chap0106.pdf"));

			HtmlWriter.getInstance(document, System.out);

			// step 3: we add some metadata and open the document

			document.addTitle("Hello World example");

			document.addSubject("This example explains step 3 in Chapter 1");

			document.addKeywords("Metadata, iText, step 3, tutorial");

			document.addAuthor("Bruno Lowagie");

			document.addHeader("Expires", "0");
			document.open();

			// step 4: we add a paragraph to the document
			document.add(new Paragraph("Hello World")); 

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