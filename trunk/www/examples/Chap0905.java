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
 * ir-arch Bruno Lowagie,
 * Adolf Baeyensstraat 121
 * 9040 Sint-Amandsberg
 * BELGIUM
 * tel. +32 (0)9 228.10.97
 * bruno@lowagie.com
 */

import java.io.FileOutputStream;
import java.io.IOException;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.pdf.PdfBarcode;
import com.lowagie.text.pdf.BaseFont;

public class Chap0905 {
    
    public static void main(String[] args) {
        
        System.out.println("Chapter 9 example 5: Barcodes");
        
        // step 1: creation of a document-object
        Document document = new Document();
        
        try {
            
            // step 2:
            // we create a writer that listens to the document
            // and directs a PDF-stream to a file
            
            PdfWriter.getInstance(document, new FileOutputStream("Chap0905.pdf"));
            
            // step 3: we open the document
            document.open();
            
            // step 4: we add a paragraph to the document
            document.add(new Paragraph(new PdfBarcode("c:\\winnt\\fonts\\CODE39.TTF", PdfBarcode.CODE39, 36, "0123456789")));
            document.add(new Paragraph(new PdfBarcode("c:\\winnt\\fonts\\UPC-A.TTF", PdfBarcode.UPCA, 36, "203489343822")));
            document.add(new Paragraph(new PdfBarcode("c:\\winnt\\fonts\\UPC-AHH.TTF", PdfBarcode.UPCA, 36, "203489343822")));
            document.add(new Paragraph(new PdfBarcode("c:\\winnt\\fonts\\EAN-13.TTF", PdfBarcode.EAN13, 36, "8010012529736")));
            document.add(new Paragraph(new PdfBarcode("c:\\winnt\\fonts\\EAN-13HH.TTF", PdfBarcode.EAN13, 48, "5400111151441")));
            document.add(new Paragraph(new PdfBarcode("c:\\winnt\\fonts\\EAN-13B.TTF", PdfBarcode.EAN13, 60, "8010012529736")));
            document.add(new Paragraph(new PdfBarcode("c:\\winnt\\fonts\\EAN-13BH.TTF", PdfBarcode.EAN13, 72, "5400111151441")));
            document.add(new Paragraph(new PdfBarcode("c:\\winnt\\fonts\\I2OF5.TTF", PdfBarcode.INTERLEAVED_2_OF_5, 48, "12345678900987654321")));
            document.add(new Paragraph(new PdfBarcode("c:\\winnt\\fonts\\I2OF5NT.TTF", PdfBarcode.INTERLEAVED_2_OF_5, 48, "2345678900987654321")));       
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