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

import java.awt.Color;
import java.io.*;

import com.lowagie.text.*;
import com.lowagie.text.pdf.*;

public class Chap0907 {
    public static void main(String[] args) {
        
        System.out.println("Chapter 9 example 7: Barcodes without ttf");
        
        // step 1: creation of a document-object
        Document document = new Document(PageSize.A4, 50, 50, 50, 50);
        
        try {
            
            // step 2:
            // we create a writer that listens to the document
            // and directs a PDF-stream to a file
            
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("Chap0907.pdf"));
            
            // step 3: we open the document
            document.open();
            
            // step 4: we add content to the document
            PdfContentByte cb = writer.getDirectContent();
            Barcode39 code39 = new Barcode39();
            code39.setCode("CODE39-1234567890");
            code39.setStartStopText(false);
            Image image39 = code39.createImageWithBarcode(cb, null, null);
            Barcode39 code39ext = new Barcode39();
            code39ext.setCode("The willows.");
            code39ext.setStartStopText(false);
            code39ext.setExtended(true);
            Image image39ext = code39ext.createImageWithBarcode(cb, null, null);
            BarcodeEAN codeEAN = new BarcodeEAN();
            codeEAN.setCodeType(codeEAN.EAN13);
            codeEAN.setCode("9780201615883");
            Image imageEAN = codeEAN.createImageWithBarcode(cb, null, null);
            BarcodeInter25 code25 = new BarcodeInter25();
            code25.setGenerateChecksum(true);
            code25.setCode("41-1200076041-001");
            Image image25 = code25.createImageWithBarcode(cb, null, null);
            BarcodePostnet codePost = new BarcodePostnet();
            codePost.setCode("12345");
            Image imagePost = codePost.createImageWithBarcode(cb, null, null);
            BarcodePostnet codePlanet = new BarcodePostnet();
            codePlanet.setCode("50201402356");
            codePlanet.setCodeType(codePlanet.PLANET);
            Image imagePlanet = codePlanet.createImageWithBarcode(cb, null, null);
            PdfTemplate tp = cb.createTemplate(0, 0);
            PdfTemplate ean = codeEAN.createTemplateWithBarcode(cb, null, Color.blue);
            BarcodeEAN codeSUPP = new BarcodeEAN();
            codeSUPP.setCodeType(codeEAN.SUPP5);
            codeSUPP.setCode("54995");
            codeSUPP.setBaseline(-2);
            BarcodeEANSUPP eanSupp = new BarcodeEANSUPP(codeEAN, codeSUPP);
            Image imageEANSUPP = eanSupp.createImageWithBarcode(cb, null, Color.blue);
            PdfPTable table = new PdfPTable(2);
            table.setWidthPercentage(100);
            table.getDefaultCell().setBorder(Rectangle.NO_BORDER);
            table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
            table.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
            table.getDefaultCell().setFixedHeight(70);
            table.addCell("CODE 39");
            table.addCell(new Phrase(new Chunk(image39, 0, 0)));
            table.addCell("CODE 39 EXTENDED");
            table.addCell(new Phrase(new Chunk(image39ext, 0, 0)));
            table.addCell("CODE EAN");
            table.addCell(new Phrase(new Chunk(imageEAN, 0, 0)));
            table.addCell("CODE EAN\nWITH\nSUPPLEMENTAL 5");
            table.addCell(new Phrase(new Chunk(imageEANSUPP, 0, 0)));
            table.addCell("CODE INTERLEAVED");
            table.addCell(new Phrase(new Chunk(image25, 0, 0)));
            table.addCell("CODE POSTNET");
            table.addCell(new Phrase(new Chunk(imagePost, 0, 0)));
            table.addCell("CODE PLANET");
            table.addCell(new Phrase(new Chunk(imagePlanet, 0, 0)));
            document.add(table);
        }
        catch (Exception de) {
            de.printStackTrace();
        }
        
        // step 5: we close the document
        document.close();
    }
}
