/*
 * $Id$
 * $Name$
 *
 * This code is free software. It may only be copied or modified
 * if you include the following copyright notice:
 *
 * --> Copyright 2001 by Paulo Soares <--
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
import java.io.*;
import com.lowagie.text.*;
import com.lowagie.text.pdf.*;
public class Chap0519 {

    
    public static void main(String[] args) {
        System.out.println("Chapter 5 example 19: PdfPTable");
        // step 1: creation of a document-object
        Document document = new Document(PageSize.A4.rotate(), 10, 10, 10, 10);
        try {
            // step 2: we create a writer that listens to the document
            PdfWriter.getInstance(document, new FileOutputStream("Chap0519.pdf"));
            // step 3: we open the document
            document.open();
            // step 4: we add content to the document (this happens in a seperate method)
            loadDocument(document);
        }
        catch (Exception e2) {
            System.out.println(e2);
        }
        // step 5: we close the document
        document.close();
    }
    
    public static void loadDocument(Document document) {
        String[] bogusData = { "M0065920",
        "SL",
        "FR86000P",
        "PCGOLD",
        "119000",
        "96 06",
        "2001-08-13",
        "4350",
        "6011648299",
        "FLFLMTGP",
        "153",
        "119000.00"
        };
        int NumColumns = 12;
        try {
            // we add some meta information to the document
            
            PdfPTable datatable = new PdfPTable(NumColumns);
            
            datatable.getDefaultCell().setPadding(3);
            int headerwidths[] = {9, 4, 8, 10, 8, 11, 9, 7, 9, 10, 4, 10}; // percentage
            datatable.setWidths(headerwidths);
            datatable.setWidthPercentage(100); // percentage
            
            datatable.getDefaultCell().setBorderWidth(2);
            datatable.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
            datatable.addCell("Clock #");
            datatable.addCell("Trans Type");
            datatable.addCell("Cusip");
            datatable.addCell("Long Name");
            datatable.addCell("Quantity");
            datatable.addCell("Fraction Price");
            datatable.addCell("Settle Date");
            datatable.addCell("Portfolio");
            datatable.addCell("ADP Number");
            datatable.addCell("Account ID");
            datatable.addCell("Reg Rep ID");
            datatable.addCell("Amt To Go ");
            
            datatable.setHeaderRows(1);  // this is the end of the table header
            
            datatable.getDefaultCell().setBorderWidth(1);
            
            int max = 666;
            for (int i = 1; i < max; i++) {
                if (i % 2 == 1) {
                    datatable.getDefaultCell().setGrayFill(0.9f);
                }
                for (int x = 0; x < NumColumns; x++) {
                    datatable.addCell(bogusData[x]);
                }
                if (i % 2 == 1) {
                    datatable.getDefaultCell().setGrayFill(0.0f);
                }
            }
            document.add(datatable);
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
}
