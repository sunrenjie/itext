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

import com.lowagie.text.Document;
import com.lowagie.text.PageSize;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.xml.XmlParser;

public class Chap0704 {
    
    public static void main(String[] args) {
        
        System.out.println("Chapter 7 example 4: making life easy");
        
        // step 1: creation of a document-object
        Document documentA = new Document(PageSize.A4, 80, 50, 30, 65);
        
        // step 1: creation of a document-object
        Document documentB = new Document(PageSize.A4, 80, 50, 30, 65);
        
        try {
            
            // step 2:
            // we create a writer that listens to the document
            // and directs a XML-stream to a file
            PdfWriter.getInstance(documentA, new FileOutputStream("Chap0704a.pdf"));
            PdfWriter.getInstance(documentB, new FileOutputStream("Chap0704b.pdf"));
            
            // step 3: we parse the document
            XmlParser.parse(documentA, "Chap0701.xml");
            XmlParser.parse(documentB, "Chap0703.xml", "tagmap0703.xml");
            
        }
        catch(Exception e) {
            e.printStackTrace();
            System.err.println(e.getMessage());
        }
    }
}