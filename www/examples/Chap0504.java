/*
 * $Id$
 * $Name$
 *
 * This code is free software. It may only be copied or modified
 * if you include the following copyright notice:
 *
 * --> Copyright 2001 by Bruno Lowagie <--
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

import java.io.FileOutputStream;
import java.io.IOException;
import java.awt.Point;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;

public class Chap0504 {
    
    public static void main(String[] args) {
        System.out.println("Chapter 5 example 4: adding columns");
        // step 1: creation of a document-object
        Document document = new Document();
        try {
            // step 2:
            // we create a writer that listens to the document
            // and directs a PDF-stream to a file
            PdfWriter.getInstance(document, new FileOutputStream("Chap0504.pdf"));
            // step 3: we open the document
            document.open();
            // step 4: we create a table and add it to the document
            Table aTable = new Table(2,2);    // 2 rows, 2 columns
            aTable.setAutoFillEmptyCells(true);
            aTable.addCell("0.0");
            aTable.addCell("0.1");
            aTable.addCell("1.0");
            aTable.addCell("1.1");
            aTable.addColumns(2);
            float[] f = {1f, 1f, 1f, 1f};
            aTable.setWidths(f);
            aTable.addCell("2.2", new Point(2,2));
            aTable.addCell("3.3", new Point(3,3));
            aTable.addCell("2.1", new Point(2,1));
            aTable.addCell("1.3", new Point(1,3));
            aTable.addCell("5.2", new Point(5,2));
            aTable.addCell("6.1", new Point(6,1));
            aTable.addCell("5.0", new Point(5,0)); 
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