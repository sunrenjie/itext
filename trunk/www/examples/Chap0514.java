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
            
            // simple example
            
            Table secondTable = new Table(2);
            secondTable.addCell("2nd table 0.0");
            secondTable.addCell("2nd table 0.1");
            secondTable.addCell("2nd table 1.0");
            secondTable.addCell("2nd table 1.1");
            
            Table aTable = new Table(4,4);    // 4 rows, 4 columns
            aTable.setAutoFillEmptyCells(true);
            aTable.addCell("2.2", new Point(2,2));
            aTable.addCell("3.3", new Point(3,3));
            aTable.addCell("2.1", new Point(2,1));
            aTable.insertTable(secondTable, new Point(1,3));
            document.add(aTable);
            
            // example with 2 nested tables
            
            Table thirdTable = new Table(2);
            thirdTable.addCell("3rd table 0.0");
            thirdTable.addCell("3rd table 0.1");
            thirdTable.addCell("3rd table 1.0");
            thirdTable.addCell("3rd table 1.1");
            
            aTable = new Table(5,5);
            aTable.setAutoFillEmptyCells(true);
            aTable.addCell("2.2", new Point(2,2));
            aTable.addCell("3.3", new Point(3,3));
            aTable.addCell("2.1", new Point(2,1));
            aTable.insertTable(secondTable, new Point(1,3));
            aTable.insertTable(thirdTable, new Point(6,2));
            document.add(aTable);        
            
            // relative column widths are preserved
            
            Table a = new Table( 2 );
            a.setWidths( new float[] { 85, 15 } );
            a.addCell("a-1");
            a.addCell("a-2");
            
            Table b = new Table(5);
            b.setWidths( new float[] { 15, 7, 7, 7, 7 } );
            b.addCell("b-1");
            b.addCell("b-2");
            b.addCell("b-3");
            b.addCell("b-4");
            b.addCell("b-5");
            
            // now, insert these 2 tables into a third for layout purposes
            Table c = new Table( 3, 1 );
            c.setWidth( 100.0f );
            c.setWidths( new float[] { 20, 2, 78 } );
            c.insertTable(a, new Point(0,0) );
            c.insertTable(b, new Point(0,2) );

            document.add(c);
            
            // adding extra cells after adding a table
            
            Table t1 = new Table(3);
            t1.addCell("1.1");
            t1.addCell("1.2");
            t1.addCell("1.3");
            // nested
            Table t2 = new Table(2);
            t2.addCell("2.1");
            t2.addCell("2.2");
            
            // now insert the nested
            t1.insertTable(t2);
            t1.addCell("new cell");    // correct row/column ?
            document.add(t1);       
            
            // deep nesting
            
            t1=new Table(2,2); 
            for (int i = 0; i < 4; i++) {
                t1.addCell("t1");
            }
            
            t2=new Table(3,3);
            for (int i = 0; i < 9; i++) {
                if (i == 4) t2.insertTable(t1);
                else t2.addCell("t2"); 
            }
            
            Table t3=new Table(4,4);
            for (int i = 0; i < 16; i++) {
                if (i == 10) t3.insertTable(t2);
                else t3.addCell("t3"); 
            }

            document.add(t3); 
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
