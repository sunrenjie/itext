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
public class Chap0614 {
    
    public static void main(String[] args) {
        Document.compress = false;
        System.out.println("Chapter 6 example 14: images wrapped in a Chunk");
        // step 1: creation of a document-object
        Document document = new Document();
        try {
            // step 2:
            // we create a writer that listens to the document
            // and directs a PDF-stream to a file
            PdfWriter.getInstance(document, new FileOutputStream("Chap0614.pdf"));
            // step 3: we open the document
            document.open();
            // step 4: we create a table and add it to the document
            Image img = Image.getInstance("pngnow.png");
            img.scalePercent(70);
            Chunk ck = new Chunk(img, 0, -5);
            Table table = new Table(3);
            table.setWidth(100);
            table.setPadding(2);
            table.setDefaultHorizontalAlignment(Element.ALIGN_CENTER);
            Cell cell = new Cell(new Chunk(img, 0, -13));
            cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell("I see an image\non my right");
            table.addCell(cell);
            table.addCell("I see an image\non my left");
            table.addCell(cell);
            table.addCell("I see images\neverywhere");
            table.addCell(cell);
            table.addCell("I see an image\non my right");
            table.addCell(cell);
            table.addCell("I see an image\non my left");
            
            Phrase p1 = new Phrase("This is an image ");
            p1.add(ck);
            p1.add(" just here.");
            document.add(p1);
            document.add(p1);
            document.add(p1);
            document.add(p1);
            document.add(p1);
            document.add(p1);
            document.add(p1);
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
