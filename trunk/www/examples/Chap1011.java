/*
 * $Id$
 * $Name$
 *
 * This code is free software. It may only be copied or modified
 * if you include the following copyright notice:
 *
 * --> Copyright 2001 by Paulo Soares <--
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

import java.io.*;
import java.awt.Color;
import com.lowagie.text.*;
import com.lowagie.text.pdf.*;

public class Chap1011 {
    
    public static void main(String[] args) {
        
        System.out.println("Chapter 10 example 11: a PdfPTable in a template");
            
        // step 1: creation of a document-object
        Rectangle rect = new Rectangle(PageSize.A4);
        rect.setBackgroundColor(new Color(238, 221, 88));
        Document document = new Document(rect, 50, 50, 50, 50);
        try {
            // step 2: we create a writer that listens to the document
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("Chap1011.pdf"));
            // step 3: we open the document
            document.open();
            // step 4:
            PdfTemplate template = writer.getDirectContent().createTemplate(20, 20);
            BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.WINANSI, BaseFont.NOT_EMBEDDED);
            String text = "Vertical";
            float size = 16;
            float width = bf.getWidthPoint(text, size);
            template.beginText();
            template.setRGBColorFillF(1, 1, 1);
            template.setFontAndSize(bf, size);
            template.setTextMatrix(0, 2);
            template.showText(text);
            template.endText();
            template.setWidth(width);
            template.setHeight(size + 2);
            Image img = Image.getInstance(template);
            img.setRotationDegrees(90);
            Chunk ck = new Chunk(img, 0, 0);
            PdfPTable table = new PdfPTable(3);
            table.setWidthPercentage(100);
            table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
            table.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
            PdfPCell cell = new PdfPCell(img);
            cell.setPadding(4);
            cell.setBackgroundColor(new Color(0, 0, 255));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell("I see a template on my right");
            table.addCell(cell);
            table.addCell("I see a template on my left");
            table.addCell(cell);
            table.addCell("I see a template everywhere");
            table.addCell(cell);
            table.addCell("I see a template on my right");
            table.addCell(cell);
            table.addCell("I see a template on my left");
            
            Paragraph p1 = new Paragraph("This is a template ");
            p1.add(ck);
            p1.add(" just here.");
            p1.setLeading(img.scaledHeight() * 1.1f);
            document.add(p1);
            document.add(table);
            Paragraph p2 = new Paragraph("More templates ");
            p2.setLeading(img.scaledHeight() * 1.1f);
            p2.setAlignment(Element.ALIGN_JUSTIFIED);
            img.scalePercent(70);
            for (int k = 0; k < 20; ++k)
                p2.add(ck);
            document.add(p2);
            // step 5: we close the document
            document.close();
        }
        catch (Exception de) {
            System.err.println(de.getMessage());
        }
    }
}
