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

import com.lowagie.text.Document;
import com.lowagie.text.PageSize;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.html.HtmlParser;

public class Chap0707 {
        
	public static void main(String[] args) {

		System.out.println("Chapter 7 example 7: parsing the HTML from example 2");

		// step 1: creation of a document-object
		Document document = new Document(PageSize.A4, 80, 50, 30, 65);

		try {

			// step 2:
			// we create a writer that listens to the document
			// and directs a XML-stream to a file
 			PdfWriter.getInstance(document, new FileOutputStream("Chap0707.pdf"));

			// step 3: we parse the document
            HtmlParser.parse(document, "Chap0702.html");         
            
		}
		catch(Exception e) {
            e.printStackTrace();
			System.err.println(e.getMessage());
		}
	}
}