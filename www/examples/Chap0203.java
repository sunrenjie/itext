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

public class Chap0203 {

	public static void main(String[] args) {

		System.out.println("Chapter 2 example 3: Greek Characters");

		// step 1: creation of a document-object
		Document document = new Document();

		try {

			// step 2:
			// we create a writer that listens to the document
			// and directs a PDF-stream to a file
			PdfWriter.getInstance(document, new FileOutputStream("Chap0203.pdf"));

			// step 3: we open the document
			document.open();

			// step 4: we add a paragraph to the document
			document.add(new Phrase("What is the " + (char) 945 + "-coefficient of the " + (char) 946 + "-factor in the " + (char) 947 + "-equation?\n")); 
			for (int i = 913; i < 970; i++) {
				document.add(new Phrase(" " + String.valueOf(i) + ": " + (char) i));
			}
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