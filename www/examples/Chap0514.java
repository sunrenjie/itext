/*
 * $Id$
 * $Name$
 *
 * This code is free software. It may only be copied or modified
 * if you include the following copyright notice:
 *
 * --> Copyright 2001 by Bruno Lowagie, Geert Poels <--
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

import java.awt.Point;
import java.io.FileOutputStream;
import java.io.IOException;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;

public class Chap0514 {
    
    public static void main(String[] args) {
        System.out.println("Chapter 5 example 14: nested tables");
        // step 1: creation of a document-object
        Document document = new Document();
        try {
            // step 2:
            // we create a writer that listens to the document
            // and directs a PDF-stream to a file
            PdfWriter.getInstance(document, new FileOutputStream("Chap0514.pdf"));
            // step 3: we open the document
            document.open();
            // step 4: we create a table and add it to the document
            Table secondTable = new Table(2);
            secondTable.addCell("2nd table 0.0");
            secondTable.addCell("2nd table 0.1");
            secondTable.addCell("2nd table 1.0");
            secondTable.addCell("2nd table 1.1");
            
            Table thirdTable = new Table(2);
            thirdTable.addCell("3rd table 0.0");
            thirdTable.addCell("3rd table 0.1");
            thirdTable.addCell("3rd table 1.0");
            thirdTable.addCell("3rd table 1.1");
            
            Table aTable = new Table(5,5);
            aTable.addCell("2.2", new Point(2,2));
            aTable.addCell("3.3", new Point(3,3));
            aTable.addCell("2.1", new Point(2,1));
            aTable.insertTable(secondTable, new Point(1,3));
            aTable.insertTable(thirdTable, new Point(6,2));
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
