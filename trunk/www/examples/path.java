/*
 * @(#)Text.java
 *
 * This class can be used as an example on how to use the iText library
 */

import java.awt.Color;
import java.io.*;

import com.lowagie.text.*;
import com.lowagie.text.pdf.*;

/**
 * With this class you can test the iText library.
 *
 * @author  bruno@lowagie.com
 *
 * @since   iText0.30
 */

public class path {
    
    // main method
    
/**
 * This method generates all kinds of files.
 *
 * @since   iText0.30
 */
    
    public static void main(String[] args)
    {
        // creation of the document with a certain size and certain margins
        Document document = new Document(PageSize.A4, 50, 50, 50, 50);
        try
        {
            // creation of the different writers
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("c:\\pdfnstroke.pdf"));
            
            document.open();
            PdfContentByte cb = writer.getDirectContent();
            cb.setLineWidth(3);
            cb.setLineCap(1);
            cb.circle(100, 100, 30);
            cb.moveTo(100, 100);
            cb.lineTo(200, 320);
            cb.stroke();
            PdfDestination d1 = new PdfDestination(PdfDestination.XYZ, 0, 0, 0);
            PdfOutline out1 = new PdfOutline(cb.getRootOutline(), d1, "Level 0");
            cb.addOutline(out1);
            PdfOutline out2 = new PdfOutline(out1, d1, "Level 11", false);
            cb.addOutline(out2);
            PdfOutline out21 = new PdfOutline(out1, d1, "Level 12");
            cb.addOutline(out21);
            PdfOutline out3 = new PdfOutline(out2, d1, "Level 2");
            cb.addOutline(out3);
            PdfOutline out4 = new PdfOutline(out3, d1, "Level 3");
            cb.addOutline(out4);
            document.close();
            System.out.println("FIM.");
        }
        catch (Exception de)
        {
            System.err.println(de.getMessage());
        }
    }
}
