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

public class Chap0608 {

	public static void main(String[] args) {

		System.out.println("Chapter 6 example 8: Rotating an Image");

		// step 1: creation of a document-object
		Document document = new Document();

		try {

			// step 2:
			// we create a writer that listens to the document
			// and directs a PDF-stream to a file

			PdfWriter.getInstance(document, new FileOutputStream("Chap0608.pdf"));

			// step 3: we open the document
			document.open();

			// step 4: we add content
			Image jpg = Image.getInstance("myKids.jpg");
			jpg.setAlignment(Image.MIDDLE);

			jpg.setRotation((float)Math.PI / 6);
			document.add(new Paragraph("rotate 30 degrees"));
			document.add(jpg);
			document.newPage();

			jpg.setRotation((float)Math.PI / 4);
			document.add(new Paragraph("rotate 45 degrees"));
			document.add(jpg);
			document.newPage();

			jpg.setRotation((float)Math.PI / 2);
			document.add(new Paragraph("rotate pi/2 radians"));
			document.add(jpg);
			document.newPage();

			jpg.setRotation((float)(Math.PI * 0.75));
			document.add(new Paragraph("rotate 135 degrees"));
			document.add(jpg);
			document.newPage();

			jpg.setRotation((float)Math.PI);
			document.add(new Paragraph("rotate pi radians"));
			document.add(jpg);
			document.newPage();

			jpg.setRotation((float)(2.0 * Math.PI));
			document.add(new Paragraph("rotate 2 x pi radians"));
			document.add(jpg);
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
