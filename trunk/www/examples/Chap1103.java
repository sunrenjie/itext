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
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfDestination;
import com.lowagie.text.pdf.PdfOutline;
import com.lowagie.text.pdf.PdfTemplate;

public class Chap1103 {
    
    public static void main(String[] args) {
        
        System.out.println("Chapter 11 example 3: Outlines and Destinations");
        
        // step 1: creation of a document-object
        Document document = new Document();
        
        try {
            
            // step 2:
            // we create a writer that listens to the document
            // and directs a PDF-stream to a file
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("Chap1103.pdf"));
            
            // step 3: we open the document
            document.open();
            
            // step 4: we grab the ContentByte and do some stuff with it
            PdfContentByte cb = writer.getDirectContent();
            
            // we create a PdfTemplate
            PdfTemplate template = cb.createTemplate(25, 25);
            
            // we add some crosses to visualize the destinations
            template.moveTo(13, 0);
            template.lineTo(13, 25);
            template.moveTo(0, 13);
            template.lineTo(50, 13);
            template.stroke();
            
            // we add the template on different positions
            cb.addTemplate(template, 287, 787);
            cb.addTemplate(template, 187, 487);
            cb.addTemplate(template, 487, 287);
            cb.addTemplate(template, 87, 87);
            
            // we define the destinations
            PdfDestination d1 = new PdfDestination(PdfDestination.XYZ, 300, 800, 0);
            PdfDestination d2 = new PdfDestination(PdfDestination.FITH, 500);
            PdfDestination d3 = new PdfDestination(PdfDestination.FITR, 200, 300, 400, 500);
            PdfDestination d4 = new PdfDestination(PdfDestination.FITBV, 100);
            PdfDestination d5 = new PdfDestination(PdfDestination.FIT);
            
            // we define the outlines
            PdfOutline out1 = new PdfOutline(cb.getRootOutline(), d1, "root");
            PdfOutline out2 = new PdfOutline(out1, d2, "sub 1");
            PdfOutline out3 = new PdfOutline(out1, d3, "sub 2");
            PdfOutline out4 = new PdfOutline(out2, d4, "sub 2.1");
            PdfOutline out5 = new PdfOutline(out2, d5, "sub 2.2");
            
            cb.addOutline(out1);
            cb.addOutline(out2);
            cb.addOutline(out3);
            cb.addOutline(out4);
            cb.addOutline(out5);
            
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
