/*
 * $Id$
 * $Name$
 *
 * This code is free software. It may only be copied or modified
 * if you include the following copyright notice:
 *
 * --> Copyright 2001 by Bruno Lowagie, Geert Poels, Chris Zachary <--
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
import java.awt.Point;
import java.io.FileOutputStream;
import java.io.IOException;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;

public class Chap0516 {
    
    public static void main(String[] args) {
        System.out.println("Chapter 5 example 16: nested tables");
        // step 1: creation of a document-object
        Document document = new Document(PageSize.A4.rotate());
        try {
            // step 2:
            // we create a writer that listens to the document
            // and directs a PDF-stream to a file
            PdfWriter.getInstance(document, new FileOutputStream("Chap0516.pdf"));
            // step 3: we open the document
            document.open();
            // step 4: we create a table and add it to the document
            Table secondTable = new Table(2);
            secondTable.addCell("2nd table 0.0");
            secondTable.addCell("2nd table 0.1");
            secondTable.addCell("2nd table 1.0");
            secondTable.addCell("2nd table 1.1");
            Cell tableCell = new Cell("This is a nested table");
            tableCell.add(secondTable);
            
            Table aTable = new Table(3,3);    // 3 rows, 3 columns
            aTable.addCell("0.0", new Point(0,0));
            aTable.addCell("0.1", new Point(0,1));
            aTable.addCell("0.2", new Point(0,2));
            aTable.addCell("0.0", new Point(1,0));
            aTable.addCell(tableCell, new Point(1,1));
            aTable.addCell("2.2", new Point(1,2));
            aTable.addCell("2.0", new Point(2,0));
            aTable.addCell("2.1", new Point(2,1));
            aTable.addCell("2.2", new Point(2,2));
            document.add(aTable);
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