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
 * itext-questions@lists.sourceforge.net
 */

import java.io.*;

import com.lowagie.text.*;
import com.lowagie.text.pdf.*;

class MyTableEvent implements PdfPTableEvent {
    
    public void tableLayout(PdfPTable table, float[][] width, float[] heights, int headerRows, int rowStart, PdfContentByte[] canvases) {
        float widths[] = width[0];
        PdfContentByte cb = canvases[PdfPTable.TEXTCANVAS];
        cb.saveState();
        cb.setLineWidth(2);
        cb.setRGBColorStroke(255, 0, 0);
        cb.rectangle(widths[0], heights[heights.length - 1], widths[widths.length - 1] - widths[0], heights[0] - heights[heights.length - 1]);
        cb.stroke();
        if (headerRows > 0) {
            float headerHeight = heights[0];
            for (int k = 0; k < headerRows; ++k)
                headerHeight += heights[k];
            cb.setRGBColorStroke(0, 0, 255);
            cb.rectangle(widths[0], heights[headerRows], widths[widths.length - 1] - widths[0], heights[0] - heights[headerRows]);
            cb.stroke();
        }
        cb.restoreState();
        cb = canvases[PdfPTable.BASECANVAS];
        cb.saveState();
        cb.setLineWidth(.5f);
        for (int line = 0; line < heights.length - 1; ++line) {
            widths = width[line];
            for (int col = 0; col < widths.length - 1; ++col) {
                if (line == 0 && col == 0)
                    cb.setAction(new PdfAction("http://www.geocities.com/itextpdf"),
                        widths[col], heights[line + 1], widths[col + 1], heights[line]);
                cb.setRGBColorStrokeF((float)Math.random(), (float)Math.random(), (float)Math.random());
                cb.moveTo(widths[col], heights[line]);
                cb.lineTo(widths[col + 1], heights[line]);
                cb.stroke();
                cb.setRGBColorStrokeF((float)Math.random(), (float)Math.random(), (float)Math.random());
                cb.moveTo(widths[col], heights[line]);
                cb.lineTo(widths[col], heights[line + 1]);
                cb.stroke();
            }
        }
        cb.restoreState();
    }
}

public class Chap1202 {
    
    public static void main(String[] args) {
        
        System.out.println("Chapter 12 example 2: Table events");
        
        // step 1: creation of a document-object
        Document document = new Document(PageSize.A4, 50, 50, 50, 50);
        try {
            // step 2: we create a writer that listens to the document
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("Chap1202.pdf"));
            // step 3: we open the document
            document.open();
            // step 4: we add some content
            BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.WINANSI, BaseFont.NOT_EMBEDDED);
            // table 1
            PdfPTable table = new PdfPTable(4);
            table.getDefaultCell().setBorder(Rectangle.NO_BORDER);
            for (int k = 0; k < 24; ++k) {
                if (k != 0)
                    table.addCell("" + k);
                else
                    table.addCell("This is an URL");
            }
            MyTableEvent event = new MyTableEvent();
            table.setTableEvent(event);
            table.setTotalWidth(300);
            // write table 1 at some position
            table.writeSelectedRows(0, -1, 100, 600, writer.getDirectContent());
            // add table 1 (default position)
            document.add(table);
            document.newPage();
            // table 2
            table = new PdfPTable(4);
            float fontSize = 12;
            table.getDefaultCell().setPaddingTop(bf.getFontDescriptor(BaseFont.ASCENT, fontSize) - fontSize + 2);
            table.getDefaultCell().setBorder(Rectangle.NO_BORDER);
            for (int k = 0; k < 500 * 4; ++k) {
                if (k == 0)
                    table.addCell(new Phrase("This is an URL", new Font(bf, fontSize)));
                else
                    table.addCell(new Phrase("" + k, new Font(bf, fontSize)));
            }
            table.setTableEvent(event);
            table.setHeaderRows(3);
            document.add(table);
        }
        catch (Exception de) {
            de.printStackTrace();
        }
        // step 5: close the document
        document.close();
    }
}
