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

public class Chap1002 {
    
    public static void main(String[] args) {
        
        System.out.println("Chapter 10 example 2: Text at absolute positions");
        
        // step 1: creation of a document-object
        Document document = new Document();
        
        try {
            
            // step 2:
            // we create a writer that listens to the document
            // and directs a PDF-stream to a file
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("Chap1002.pdf"));
            
            // step 3: we open the document
            document.open();
            
            // step 4: we grab the ContentByte and do some stuff with it
            PdfContentByte cb = writer.getDirectContent();
            
            // first we draw some lines to be able to visualize the text alignment functions
            cb.setLineWidth(0f);
            cb.moveTo(250, 500);
            cb.lineTo(250, 800);
            cb.moveTo(50, 700);
            cb.lineTo(400, 700);
            cb.moveTo(50, 650);
            cb.lineTo(400, 650);
            cb.moveTo(50, 600);
            cb.lineTo(400, 600);
            cb.stroke();
            
            // we tell the ContentByte we're ready to draw text
            cb.beginText();
            
            BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
            cb.setFontAndSize(bf, 12);
            String text = "Sample text for alignment";
            // we show some text starting on some absolute position with a given alignment
            cb.showTextAligned(PdfContentByte.ALIGN_CENTER, text + " Center", 250, 700, 0);
            cb.showTextAligned(PdfContentByte.ALIGN_RIGHT, text + " Right", 250, 650, 0);
            cb.showTextAligned(PdfContentByte.ALIGN_LEFT, text + " Left", 250, 600, 0);
            
            // we draw some text on a certain position
            cb.setTextMatrix(100, 400);
            cb.showText("Text at position 100,400.");
            
            // we draw some rotated text on a certain position
            cb.setTextMatrix(0, 1, -1, 0, 100, 300);
            cb.showText("Text at position 100,300, rotated 90 degrees.");
            
            // we draw some mirrored, rotated text on a certain position
            cb.setTextMatrix(0, 1, 1, 0, 200, 200);
            cb.showText("Text at position 200,200, mirrored and rotated 90 degrees.");
            
            // we tell the contentByte, we've finished drawing text
            cb.endText();
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
