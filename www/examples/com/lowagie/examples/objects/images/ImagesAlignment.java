/*
 * $Id$
 * $Name$
 *
 * This code is free software. It may only be copied or modified
 * if you include the following copyright notice:
 *
 * --> Copyright 2005 by Marc Campforts <--
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
package com.lowagie.examples.objects.images;

import java.io.FileOutputStream;
import java.io.IOException;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Image;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfWriter;

/**
 * Testing Image alignment.
 */
public class ImagesAlignment {

	/**
	 * Generates a PDF with Images that are aligned.
	 * @param args no arguments needed
	 */
	public static void main(java.lang.String[] args) {
	    Document doc = new Document();
	    try {
	        PdfWriter.getInstance(doc, new FileOutputStream("imagesAlignment.pdf"));
	        doc.open();
	        doc.add(new Phrase("The leading of this paragraph is calculated	automagically. "));
	        Image gif = Image.getInstance("otsoe.jpg");
	        gif.setAlignment(Image.RIGHT | Image.TEXTWRAP);
	        doc.add(gif);
	        doc.add(new Phrase("The leading of this paragraph is calculated	automagically. "));
	        doc.add(new Phrase("The leading of this paragraph is calculated	automagically. "));
	        doc.add(new Phrase("The default leading is 1.5 times the fontsize. "));
	        doc.add(new Phrase("You can add chunks "));
	        doc.add(new Phrase("You can add chunks ", FontFactory.getFont(FontFactory.HELVETICA, 24)));
	        doc.add(new Phrase("or you can add phrases. "));
	        doc.add(new Phrase("Unless you change the leading with the method setLeading, the leading doesn't change if you add text with another leading. This can lead to some problems.", FontFactory.getFont(FontFactory.HELVETICA, 24)));
	        doc.add(new Phrase("Unless you change the leading with the method setLeading, the leading doesn't change if you add text with another leading. This can lead to some problems."));
	    }
	    catch(DocumentException de) {
	        System.err.println(de.getMessage());
	    }
	    catch(IOException ioe) {
	        System.err.println(ioe.getMessage());
	    }
	    doc.close();
	}
} 
