/*
 * $Id$
 * $Name$
 *
 * This code is free software. It may only be copied or modified
 * if you include the following copyright notice:
 *
 * --> Copyright 2005 by Bruno Lowagie <--
 *
 * This code is part of the 'iText Tutorial'.
 * You can find the complete tutorial at the following address:
 * http://www.lowagie.com/iText/tutorial/
 *
 * This code is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 *
 * itext-questions@lists.sourceforge.net
 */
package com.lowagie.examples.directcontent;

import java.io.FileOutputStream;
import java.io.IOException;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.pdf.PdfContentByte;

/**
 * Simple use of PdfContentByte: drawing Circles
 */
public class Circles {
    
    /**
     * Draws some concentric circles.
     * @param args no arguments needed
     */
    public static void main(String[] args) {
        
        System.out.println("Drawing circles");
        
        // step 1: creation of a document-object
        Document document = new Document();
        
        try {
            
            // step 2:
            // we create a writer that listens to the document
            // and directs a PDF-stream to a file
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("circles.pdf"));
            
            // step 3: we open the document
            document.open();
            
            // step 4: we grab the ContentByte and do some stuff with it
            PdfContentByte cb = writer.getDirectContent();

            cb.circle(250.0f, 500.0f, 200.0f);
            cb.circle(250.0f, 500.0f, 150.0f);
            cb.circle(250.0f, 500.0f, 100.0f);
            cb.circle(250.0f, 500.0f, 50.0f);
            
            cb.stroke();
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