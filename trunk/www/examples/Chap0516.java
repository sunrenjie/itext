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
 * ir-arch Bruno Lowagie,
 * Adolf Baeyensstraat 121
 * 9040 Sint-Amandsberg
 * BELGIUM
 * tel. +32 (0)9 228.10.97
 * bruno@lowagie.com
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
            Table a = new Table( 2 );
            a.setWidths( new float[] { 85, 15 } );
            a.addCell("a-1");
            a.addCell("a-2");
            
            Table b = new Table(10);
            b.setWidths( new float[] { 15, 7, 4, 25, 7, 7, 7, 7, 7, 7 } );
            b.addCell("b-1");
            b.addCell("b-2");
            b.addCell("b-3");
            b.addCell("b-4");
            b.addCell("b-5");
            b.addCell("b-6");
            b.addCell("b-7");
            b.addCell("b-8");
            b.addCell("b-9");
            b.addCell("b-10");
            
            // now, insert these 2 tables into a third for layout purposes
            Table c = new Table( 3, 1 );
            c.setWidth( 100.0f );
            c.setWidths( new float[] { 20, 2, 78 } );
            c.insertTable(a, new Point(0,0) );
            c.insertTable(b, new Point(0,2) );

            document.add(c);
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