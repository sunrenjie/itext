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
import java.net.MalformedURLException;
import java.net.URL;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.html.HtmlWriter;

public class Chap0601 {

	public static void main(String[] args) {

		System.out.println("Chapter 6 example 1: Adding a Gif, Jpeg and Png-file using urls");

		// step 1: creation of a document-object
		Document document = new Document();

		try {

			// step 2:
			// we create a writer that listens to the document
			// and directs a PDF-stream to a file
			PdfWriter.getInstance(document, new FileOutputStream("Chap0601.pdf"));
			HtmlWriter.getInstance(document, new FileOutputStream("Chap0601.html"));

			// step 3: we open the document
			document.open();

			Image gif = Image.getInstance(new URL("http://www.lowagie.com/iText/tutorial/vonnegut.gif"));
			Image jpeg = Image.getInstance(new URL("http://www.lowagie.com/iText/tutorial/myKids.jpg"));
			Image png = Image.getInstance(new URL("http://www.lowagie.com/iText/tutorial/hitchcock.png"));

			document.add(gif);
			document.add(jpeg);
			document.add(png);

		}
		catch(MalformedURLException mue) {
			System.err.println(mue.getMessage());
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