/*
 * $Id$
 * $Name$
 *
 * This code is free software. It may only be copied or modified
 * if you include the following copyright notice:
 *
 * --> Copyright 2001-2005 by Paulo Soares and Bruno Lowagie <--
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

package com.lowagie.examples.forms.create;


import java.awt.Color;
import java.io.FileOutputStream;
import java.io.IOException;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Image;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.Barcode;
import com.lowagie.text.pdf.BarcodeEAN;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

/**
 * Generates a StudentCard
 * @author blowagie
 */
public class StudentCard {
    /**
     * Generates a StudentCard
     * @param args no arguments needed here
     */
    public static void main(String[] args) {
        
        System.out.println("StudentCard");
        
        // step 1: creation of a document-object
        Rectangle rect = new Rectangle(245, 151);
        rect.setBackgroundColor(new Color(0xFF, 0xFF, 0xCC));
        Document document = new Document(rect);
        
        try {
            
            // step 2:
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("studentcard.pdf"));
            
            // step 3: we open the document
            document.open();
            
            // step 4:
            PdfContentByte cb = writer.getDirectContent();
            Font f = FontFactory.getFont(FontFactory.HELVETICA, 9);
            PdfPTable table = new PdfPTable(2);
            table.setTotalWidth(200);
            float[] widths = {35, 65};
            table.setWidths(widths);
            table.addCell(new Paragraph("name:", f));
            table.addCell(new Paragraph("Bruno Lowagie", f));
            table.addCell(new Paragraph("date of birth:", f));
            table.addCell(new Paragraph("June 10th 1970", f));
            table.addCell(new Paragraph("Study Program:", f));
            table.addCell(new Paragraph("master in civil engineering", f));
            table.addCell(new Paragraph("option:", f));
            table.addCell(new Paragraph("architecture", f));
            table.addCell(new Paragraph("barcode:", f));
            BarcodeEAN codeEAN = new BarcodeEAN();
            codeEAN.setCodeType(Barcode.EAN13);
            codeEAN.setCode("8010012529736");
			Image imageEAN = codeEAN.createImageWithBarcode(cb, null, null);
			table.getDefaultCell().setBackgroundColor(Color.WHITE);
			table.addCell(imageEAN);
            table.writeSelectedRows(0, -1, 20, 140, writer.getDirectContent());
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