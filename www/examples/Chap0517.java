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
 * itext@lowagie.com
 */
import java.awt.Point;
import java.io.FileOutputStream;
import java.io.IOException;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;

public class Chap0517 {
    
    public static void main(String[] args) {
        System.out.println("Chapter 5 example 17: nested tables");
        // step 1: creation of a document-object
        Document document = new Document(PageSize.A4.rotate());
        try {
            // step 2:
            // we create a writer that listens to the document
            // and directs a PDF-stream to a file
            PdfWriter.getInstance(document, new FileOutputStream("Chap0517.pdf"));
            // step 3: we open the document
            document.open();
            // step 4:
            Table t1=new Table(2,2); 
            Table t2=new Table(3,3); 
            Table t3=new Table(4,4); 

            t2.insertTable(t1,new Point(2,2));
            t3.insertTable(t2,new Point(2,2)); 

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