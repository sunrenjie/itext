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

import java.awt.Color;
import java.io.FileOutputStream;
import java.io.IOException;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;

public class Chap0201 {

	public static void main(String[] args) {

		System.out.println("Chapter 2 example 1: Chunks and fonts");

		// step 1: creation of a document-object
		Document document = new Document();

		try {

			// step 2:
			// we create a writer that listens to the document
			// and directs a PDF-stream to a file
 			PdfWriter.getInstance(document, new FileOutputStream("Chap0201.pdf"));

			// step 3: we open the document
			document.open();

			// step 4: we add content to the document
			Font[] fonts = new Font[14];
			fonts[0] = new Font(Font.COURIER, 12, Font.NORMAL);
			fonts[1] = new Font(Font.COURIER, 12, Font.BOLD);
			fonts[2] = new Font(Font.COURIER, 12, Font.ITALIC);
			fonts[3] = new Font(Font.COURIER, 12, Font.BOLD | Font.ITALIC);
			fonts[4] = new Font(Font.HELVETICA, 12, Font.NORMAL);
			fonts[5] = new Font(Font.HELVETICA, 12, Font.BOLD);
			fonts[6] = new Font(Font.HELVETICA, 12, Font.ITALIC);
			fonts[7] = new Font(Font.HELVETICA, 12, Font.BOLD | Font.ITALIC);
			fonts[8] = new Font(Font.TIMES_NEW_ROMAN, 12, Font.NORMAL);
			fonts[9] = new Font(Font.TIMES_NEW_ROMAN, 12, Font.BOLD);
			fonts[10] = new Font(Font.TIMES_NEW_ROMAN, 12, Font.ITALIC);
			fonts[11] = new Font(Font.TIMES_NEW_ROMAN, 12, Font.BOLD | Font.ITALIC);
			fonts[12] = new Font(Font.SYMBOL, 12, Font.NORMAL);
			fonts[13] = new Font(Font.ZAPFDINGBATS, 12, Font.NORMAL);
			for (int i = 0; i < 14; i++) {
				Chunk chunk = new Chunk("This is some", fonts[i]);
				document.add(new Phrase(chunk));
				document.add(new Phrase(new Chunk(" font. ", fonts[i]).setTextRise((i % 2 == 0) ? -6 : 6)));
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
