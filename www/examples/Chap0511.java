/*
 * $Id$
 * $Name$
 *
 * This code is free software. It may only be copied or modified
 * if you include the following copyright notice:
 *
 * --> Copyright 1999, 2000, 2001 by Bruno Lowagie, Alan Soukup <--
 *
 * This code is part of the 'iText Tutorial'.
 * You can find the complete tutorial at the following address:
 * http://www.lowagie.com/iText/tutorial/
 *
 * This code is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 *
 * ir-arch Bruno Lowagie,
 * Adolf Baeyensstraat 121
 * 9040 Sint-Amandsberg
 * BELGIUM
 * tel. +32 (0)9 228.10.97
 * bruno@lowagie.com
 */

import java.awt.Color;
import java.io.FileOutputStream;
import java.io.IOException;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;

public class Chap0511 {
    
    public static void main(String[] args) {
        System.out.println("Chapter 5 example 11: large tables with cellspacing");
        // creation of the document with a certain size and certain margins
        Document document = new Document(PageSize.A4.rotate(), 50, 50, 50, 50);
        
        try {
            // creation of the different writers
            PdfWriter.getInstance(document, new FileOutputStream("Chap0511.pdf"));
            
            // we add some meta information to the document
            document.addAuthor("Alan Soukup");
            document.addSubject("This is the result of a Test.");
            
            document.open();
            Table datatable = new Table(10);
            
            datatable.setPadding(0);
            datatable.setSpacing(4);
            //datatable.setBorder(Rectangle.NO_BORDER);
            int headerwidths[] = {10, 24, 12, 12, 7, 7, 7, 7, 7, 7};
            datatable.setWidths(headerwidths);
            datatable.setWidth(100);
            
            // the first cell spans 10 columns
            Cell cell = new Cell(new Phrase("Administration -System Users Report", new Font(Font.HELVETICA, 24, Font.BOLD)));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setLeading(30);
            cell.setColspan(10);
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
            datatable.addCell(cell);
            
            // These cells span 2 rows
            datatable.setDefaultCellBorderWidth(2);
            datatable.setDefaultHorizontalAlignment(1);
            datatable.setDefaultRowspan(2);
            datatable.addCell("User Id");
            datatable.addCell(new Phrase("Name", new Font(Font.HELVETICA, 14, Font.BOLD)));
            datatable.addCell("Company");
            datatable.addCell("Department");
            
            // This cell spans the remaining 6 columns in 1 row
            datatable.setDefaultRowspan(1);
            datatable.setDefaultColspan(6);
            datatable.addCell("Permissions");
            
            // These cells span 1 row and 1 column
            datatable.setDefaultColspan(1);
            datatable.addCell("Admin");
            datatable.addCell("Data");
            datatable.addCell("Expl");
            datatable.addCell("Prod");
            datatable.addCell("Proj");
            datatable.addCell("Online");
            
            // this is the end of the table header
            datatable.endHeaders();
            
            datatable.setDefaultCellBorderWidth(1);
            datatable.setDefaultRowspan(1);
            
            for (int i = 1; i < 30; i++) {
                
                datatable.setDefaultHorizontalAlignment(Element.ALIGN_LEFT);
                
                datatable.addCell("myUserId");
                datatable.addCell("Somebody with a very, very, very, very, very, very, very, very, very, very, very, very, very, very, very, very, very, very, very, very, very long long name");
                datatable.addCell("No Name Company");
                datatable.addCell("D" + i);
                
                datatable.setDefaultHorizontalAlignment(Element.ALIGN_CENTER);
                datatable.addCell("No");
                datatable.addCell("Yes");
                datatable.addCell("No");
                datatable.addCell("Yes");
                datatable.addCell("No");
                datatable.addCell("Yes");
                
            }
            
            document.add(datatable);
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        
        // we close the document
        document.close();
    }
}
