/*
 * $Id$
 * $Name$
 *
 * This code is free software. It may only be copied or modified
 * if you include the following copyright notice:
 *
 * --> Copyright 2001 by Bruno Lowagie <--
 * --> Copyright 2002 by Erwin Acherman <--
 *
 * This code is part of the 'iText Tutorial'.
 * You can find the complete tutorial at the following address:
 * http://www.lowagie.com/iText/tutorial/
 *
 * This code is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 *
 * itext@lowagie.com
 */

import java.awt.Color;
import java.io.FileOutputStream;
import java.io.IOException;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;

public class Chap0201a {

    public static void main(String[] args) {

	System.out.println("Chapter 2 example 1: Horizontal space in Paragraphs");
	Document.compress = true;
	// step 1: creation of a document-object
	Document document = new Document();



	try {

	    // step 2:
	    // we create a writer that listens to the document
	    // and directs a PDF-stream to a file
	    PdfWriter.getInstance(document, new FileOutputStream("Chap0201a.pdf"));

	    // step 3: we open the document
	    document.open();

	    // step 4: we add content to the document
	    Font[] fonts = new Font[14];
	    fonts[13] = FontFactory.getFont(FontFactory.COURIER, 7, Font.NORMAL);
	    fonts[12] = FontFactory.getFont(FontFactory.COURIER, 14, Font.BOLD);
	    fonts[11] = FontFactory.getFont(FontFactory.COURIER, 8, Font.ITALIC);
	    fonts[10] = FontFactory.getFont(FontFactory.COURIER, 13, Font.BOLD | Font.ITALIC);
	    fonts[9] = FontFactory.getFont(FontFactory.HELVETICA, 9, Font.NORMAL);
	    fonts[8] = FontFactory.getFont(FontFactory.HELVETICA, 12, Font.BOLD);
	    fonts[7] = FontFactory.getFont(FontFactory.HELVETICA, 12, Font.ITALIC);
	    fonts[6] = FontFactory.getFont(FontFactory.HELVETICA, 11, Font.BOLD | Font.ITALIC);
	    fonts[5] = FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.NORMAL);
	    fonts[4] = FontFactory.getFont(FontFactory.TIMES_ROMAN, 16, Font.BOLD);
	    fonts[3] = FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.ITALIC);
	    fonts[2] = FontFactory.getFont(FontFactory.TIMES_ROMAN, 32, Font.BOLD | Font.ITALIC);
	    fonts[1] = FontFactory.getFont(FontFactory.SYMBOL, 5, Font.NORMAL);
	    fonts[0] = FontFactory.getFont(FontFactory.ZAPFDINGBATS, 5, Font.NORMAL);
	    for (int i = 0; i < 14; i++) {
			Chunk chunk = new Chunk("This is some text in some", fonts[i]);
			document.add(new Phrase(chunk));
			document.add(new Phrase(new Chunk(" font. ",
											  fonts[i]).setTextRise((i % 2 == 0) ? -6 : 6)));
	    }
	    document.add(new Phrase(new Chunk("This text is underlined ",
										  FontFactory.getFont(FontFactory.HELVETICA, 12, Font.UNDERLINE))));
	    document.add(new Phrase(new Chunk("This font is of type ITALIC | STRIKETHRU ",
										  FontFactory.getFont(FontFactory.HELVETICA, 12, Font.ITALIC | Font.STRIKETHRU))));
	    Chunk ck = new Chunk(" This text has a yellow background color ", FontFactory.getFont(FontFactory.HELVETICA, 12));
	    ck.setBackground(new Color(0xFF, 0xFF, 0x00));
	    document.add(new Phrase(ck));

		document.add(new Chunk("text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text"));


	    Image img = Image.getInstance("pngnow.png");
		ck = new Chunk(img, 0, 0);
	    Phrase p1 = new Phrase("This is an image (not) hanging into the above text");
	    p1.add(ck);
	    p1.add(" just here.");
		document.add(p1);

		p1 = new Phrase();
		ck = new Chunk(" As we can see the clashing is fixed for Images and"); ck.setBackground(new Color(0xFF, 0xFF, 0x00));	p1.add(ck);
		ck = new Chunk(" oversized font chunks", fonts[2]); ck.setBackground(new Color(0xFF, 0xFF, 0x00));	p1.add(ck);
		ck = new Chunk(", but no change of leading is introduced by "); ck.setBackground(new Color(0xFF, 0xFF, 0x00));	p1.add(ck);
		ck = new Chunk("risen ", fonts[7]);	ck.setBackground(new Color(0xFF, 0xFF, 0x00));	p1.add(ck);
		ck = new Chunk("(i.e. ");ck.setBackground(new Color(0xFF, 0xFF, 0x00)); p1.add(ck);
		ck = new Chunk("subscript").setTextRise(-9);ck.setBackground(new Color(0xFF, 0xFF, 0x00)); p1.add(ck);
		ck = new Chunk(" or "); ck.setBackground(new Color(0xFF, 0xFF, 0x00)); p1.add(ck);
		ck = new Chunk("superscirpt").setTextRise(9); ck.setBackground(new Color(0xFF, 0xFF, 0x00)); p1.add(ck);
		ck = new Chunk(") text! "); ck.setBackground(new Color(0xFF, 0xFF, 0x00)); p1.add(ck);
	    document.add(p1);

		document.add(new Chunk(" text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text"));

		ck = new Chunk(img, 0, -19);
		p1 = new Phrase("This is an image (not) hanging in the subsequent text");
	    p1.add(ck);
	    p1.add(" just here.");
		document.add(p1);

		document.add(new Chunk("text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text"));

		ck = new Chunk(img, 0, -7);
		p1 = new Phrase("This is an image aligned to 'its' baseline ");
	    p1.add(ck);
	    p1.add(" just here.");
		document.add(p1);

		document.add(new Chunk("text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text"));


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
