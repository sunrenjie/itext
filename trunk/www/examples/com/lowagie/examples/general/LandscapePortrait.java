/*
 * $Id$
 * $Name$
 *
 * This code is free software. It may only be copied or modified
 * if you include the following copyright notice:
 *
 * --> Copyright 2001-2004 by Bruno Lowagie <--
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

package com.lowagie.examples.general;


import java.io.FileOutputStream;
import java.io.IOException;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;

/**
 * Demonstrates the use of PageSize.
 * @author blowagie
 */
public class LandscapePortrait {
    /**
     * Creates a PDF document with a certain pagesize
     * @param args no arguments needed here
     */
    public static void main(String[] args) {
        
        System.out.println("Documents in Landscape and Portrait format");
        // step 1: creation of a document-object
        Document document = new Document(PageSize.A4.rotate());
        
        try {
            
            // step 2:
            // we create a writer that listens to the document
            // and directs a PDF-stream to a file
            
            PdfWriter.getInstance(document, new FileOutputStream("LandscapePortrait.pdf"));
            
            // step 3: we open the document
            document.open();
            
            // step 4: we add some content
            document.add(new Paragraph("To create a document in landscape format, just make the height smaller than the width. For instance by rotating the PageSize Rectangle: PageSize.A4.rotate()"));
            document.setPageSize(PageSize.A4);
            document.newPage();
            document.add(new Paragraph("This is portrait again"));
            
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
