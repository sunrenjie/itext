/*
 * $Id$
 * $Name$
 *
 * This code is free software. It may only be copied or modified
 * if you include the following copyright notice:
 *
 * --> Copyright 2000, 2001 by Paulo Soares, Bruno Lowagie <--
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
import java.io.*;
import java.net.*;
import java.util.*;
import java.awt.Color;
import com.lowagie.text.*;
import com.lowagie.text.pdf.*;
public class Chap0615 {
    
    public static void main(String[] args) {
        System.out.println("Chapter 6 example 15: images in tables");
        // step 1: creation of a document-object
        Document document = new Document();
        try {
            // step 2:
            // we create a writer that listens to the document
            // and directs a PDF-stream to a file
            PdfWriter.getInstance(document, new FileOutputStream("Chap0615.pdf"));
            // step 3: we open the document
            document.open();
            // step 4: we create a table and add it to the document
            Image img0 = Image.getInstance("myKids.jpg");
			img0.setAlignment(Image.MIDDLE);
            Image img1 = Image.getInstance("pngnow.png");
			img1.setAlignment(Image.LEFT | Image.UNDERLYING);
            Image img2 = Image.getInstance("pngnow.png");
			img2.setAlignment(Image.RIGHT | Image.TEXTWRAP);
            Image img3 = Image.getInstance("pngnow.png");
			img3.setAlignment(Image.LEFT);
            Image img4 = Image.getInstance("pngnow.png");
			img4.setAlignment(Image.MIDDLE);
            Image img5 = Image.getInstance("pngnow.png");
			img5.setAlignment(Image.RIGHT);
            Table table = new Table(3);
            table.setPadding(2);
            table.setDefaultHorizontalAlignment(Element.ALIGN_CENTER);
			// row 1
            table.addCell("I see an image\non my right");
            Cell cell = new Cell("This is the image (aligned in the middle):");
            cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
			cell.add(img0);
			cell.add(new Phrase("This was the image"));
            table.addCell(cell);
            table.addCell("I see an image\non my left");
			// row 2
			cell = new Cell("This is the image (left aligned):");
			cell.add(img1);
			cell.add(new Phrase("This was the image"));
            table.addCell(cell);
            table.addCell("I see images\neverywhere");
			cell = new Cell("This is the image (right aligned):");
			cell.add(img2);
			cell.add(new Phrase("This was the image"));
            table.addCell(cell);
			// row 3
            table.addCell("I see an image\non my right");
			cell = new Cell(img3);
			cell.add(img4);
			cell.add(img5);
            table.addCell(cell);
            table.addCell("I see an image\non my left");
			document.add(table);
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