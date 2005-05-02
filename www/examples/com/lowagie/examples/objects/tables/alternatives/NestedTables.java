/*
 * $Id$
 * $Name$
 *
 * This code is part of the 'iText Tutorial'.
 * You can find the complete tutorial at the following address:
 * http://itext.sourceforge.net/tutorial/
 *
 * This code is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 *
 * itext-questions@lists.sourceforge.net
 */
package com.lowagie.examples.objects.tables.alternatives;

import java.awt.Point;
import java.io.FileOutputStream;
import java.io.IOException;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Table;
import com.lowagie.text.pdf.PdfWriter;

/**
 * Nested tables.
 */
public class NestedTables {
    
    /**
     * Nested tables.
     * @param args no arguments needed
     */
    public static void main(String[] args) {
        System.out.println("Nested tables");
        // step 1: creation of a document-object
        Document document = new Document();
        try {
            // step 2: creation of the writer
            PdfWriter.getInstance(document, new FileOutputStream("nestedtables.pdf"));
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
			document.add(new Paragraph("converted to PdfPTable:"));
			aTable.setConvert2pdfptable(true);
			document.add(aTable);
            document.newPage();
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
			document.add(new Paragraph("converted to PdfPTable:"));
			aTable.setConvert2pdfptable(true);
			document.add(aTable);     
            document.newPage();  
            
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
			document.add(new Paragraph("converted to PdfPTable:"));
			t1.setConvert2pdfptable(true);
			document.add(t1);   

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
