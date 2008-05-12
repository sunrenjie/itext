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

package com.lowagie.examples.objects.anchors;

import java.io.FileOutputStream;

import com.lowagie.text.Document;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfDestination;
import com.lowagie.text.pdf.PdfWriter;

/**
 * Creates a document that jumps to a Local Destination upon opening.
 * 
 * @author blowagie
 */

public class LocalDestination {

	/**
	 * Creates a document that jumps to a Local Destination upon opening.
	 * 
	 * @param args no arguments needed here
	 */
	public static void main(String[] args) {
        System.out.println("local destination");
        
        // step 1: creation of a document-object
        Document document = new Document();
        try {
            // step 2:
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("LocalDestination.pdf"));
            // step 3: we open the document
            document.open();
            // step 4: we add some content
            document.add(new Paragraph("Page 1"));
            document.newPage();
            document.add(new Paragraph("This PDF file jumps directly to page 2 when opened"));
            PdfContentByte cb = writer.getDirectContent();
            cb.localDestination("page2", new PdfDestination(PdfDestination.XYZ, -1, 10000, 0));
            writer.setOpenAction("page2");
        }
        catch (Exception de) {
            de.printStackTrace();
        }
        
        // step 5: we close the document
        document.close();
	}
}