/*
 * $Id$
 * $Name$
 *
 * This code is free software. It may only be copied or modified
 * if you include the following copyright notice:
 *
 * --> Copyright 2000, 2001 by Bruno Lowagie <--
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
public class Chap0508 {
    public static void main(String[] args) {
        System.out.println("Chapter 5 example 8: table splitting");
        // step 1: creation of a document-object
        Document document = new Document();
        try {
            // step 2:
            // we create a writer that listens to the document
            // and directs a PDF-stream to a file
            PdfWriter.getInstance(document, new FileOutputStream("Chap0508.pdf"));
            // step 3: we open the document
            document.open();
            // step 4: we create a table and add it to the document
            Table table = new Table(3);
            table.setBorderWidth(1);
            table.setBorderColor(new Color(0, 0, 255));
            table.setPadding(5);
            table.setSpacing(5);
            Cell cell = new Cell("header");
            cell.setHeader(true);
            cell.setColspan(3);
            table.addCell(cell);
            cell = new Cell("example cell with colspan 1 and rowspan 2");
            cell.setRowspan(2);
            cell.setBorderColor(new Color(255, 0, 0));
            table.addCell(cell);
            table.addCell("1.1");
            table.addCell("2.1");
            table.addCell("1.2");
            table.addCell("2.2");
            table.addCell("cell test1");
            cell = new Cell("big cell");
            cell.setRowspan(2);
            cell.setColspan(2);
            cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
            table.addCell(cell);
            table.addCell("cell test2");
            document.add(new Paragraph("repeating the same table 10 times:"));
            for (int i = 0; i < 10; i++) {
                document.add(table);
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
