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
class OutPage implements PdfPageEvent
{
    boolean active = false;
    
    public void setActive(boolean b)
    {
        active = b;
    }
    
    public void onOpenDocument(PdfWriter writer, Document document)
    {
        System.out.println("onOpenDocument " + document.getPageNumber());
    }

    public void onStartPage(PdfWriter writer, Document document)
    {
        System.out.println("onStartPage " + document.getPageNumber());
    }

    public void onEndPage(PdfWriter writer, Document document)
    {
        System.out.println("onEndPage " + document.getPageNumber());
    }

    public void onCloseDocument(PdfWriter writer, Document document)
    {
        System.out.println("onCloseDocument " + document.getPageNumber());
    }
 
    public void onParagraph(PdfWriter writer, Document document, float position)
    {
        System.out.println("onParagraph " + document.getPageNumber() + " " + position);
        // add a line to check the correct position
        PdfContentByte cb = writer.getDirectContent();
        cb.saveState();
        cb.setRGBColorStrokeF(1, 1, 0);
        cb.moveTo(0, position);
        cb.lineTo(500, position);
        cb.stroke();
        cb.restoreState();
        if (active) {
            PdfDestination destination = new PdfDestination(PdfDestination.FITH, position);
            PdfOutline outline = new PdfOutline(cb.getRootOutline(), destination, "Go to page " + document.getPageNumber());
            cb.addOutline(outline);
        }
    }

    public void onGenericTag(PdfWriter writer,Document document,Rectangle rect,String text)
    {
    }
    
}


public class Formtest {
    
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
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("c:\\pdform.pdf"));
            OutPage pageEvent = new OutPage();
            writer.setPageEvent(pageEvent);
            BaseFont bfc = BaseFont.createFont("STSong-Light","UniGB-UCS2-H",false);
            
            document.open();


            Table table = new Table(3);
            table.setBorderWidth(1);
            table.setBorderColor(new Color(0, 0, 255));
            table.setCellpadding(0);
            table.setCellspacing(5);
            Cell cell = new Cell("header");
            cell.setHeader(true);
            cell.setColspan(3);
            table.addCell(cell);
            table.endHeaders();
            Phrase phc = new Phrase("example cell with colspan 1 and rowspan 2 and an ", new Font(Font.HELVETICA, 12, Font.UNDERLINE));
            Anchor an = new Anchor("anchor", new Font(Font.HELVETICA, 12, Font.UNDERLINE));
            an.setReference("http://www.lowagie.com/iText/index.html");
            phc.add(an);
            phc.add(".");
            phc.add(new Chunk(" And something", new Font(Font.HELVETICA, 12)));
            phc.add(new Chunk("2", new Font(Font.HELVETICA, 6)).setTextRise(6));
            phc.add(new Chunk(" else more.", new Font(Font.HELVETICA, 12)));
            cell = new Cell(phc);
            cell.setRowspan(2);
            cell.setBorderColor(new Color(255, 0, 0));
            cell.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
            table.addCell(cell);
            table.addCell("1.1");
            table.addCell("2.1");
            table.addCell("1.2");
            table.addCell("2.2");
            table.addCell("cell test1");
            cell = new Cell("big cell");
            cell.setRowspan(2);
            cell.setColspan(2);
            table.addCell(cell);
            table.addCell("cell test2");
 
            
            Paragraph pg = new Paragraph();
            pg.add(new Chunk("This table", new Font(Font.HELVETICA, 12, Font.UNDERLINE, new Color(255, 0, 0))));
            pg.add(new Chunk(" ", new Font(Font.HELVETICA, 12)));
            Chunk ck = new Chunk("\u6e96\u53d7\u4fdd\u4eba \u6e96\u53d7\u4fdd\u4eba\u6e96\u53d7\u4fdd\u4eba\u6e96\u53d7\u4fdd\u4eba\u6e96\u53d7\u4fdd\u4eba\u6e96\u53d7\u4fdd\u4eba \u6e96\u53d7 \u4fdd\u4eba\u6e96 \u53d7\u4fdd\u4eba\u6e96\u53d7\u4fdd\u4eba", new Font(bfc, 12, Font.UNDERLINE));
            pg.add(ck);
            pg.add(new Chunk(" defines the mapping of character codes to the glyph index values used in the font. It may contain more than one subtable, in order to support more than one character encoding scheme.\nCharacter codes that do not correspond to any glyph in the font should be mapped to glyph index 0. The glyph at this location must be a special glyph representing a missing character.", new Font(Font.HELVETICA, 12)));
            pg.setAlignment(Element.ALIGN_JUSTIFIED);
            pg.setIndentationLeft(300);
            Paragraph pg2 = new Paragraph("Another string in another line.", new Font(Font.HELVETICA, 12, Font.UNDERLINE));
            pg2.add(an);
            Paragraph pg3 = new Paragraph("Short line.");
            pg3.setIndentationLeft(300);
            Image img = Image.getInstance("pngnow.png");
            BaseFont bf = BaseFont.createFont("Helvetica", "winansi", false);
            PdfContentByte cbm = writer.getDirectContent();
            PdfTemplate cb = cbm.createTemplate(1000, 1000);
            PdfTemplate cbt = cbm.createTemplate(100, 100);
            cb.addImage(img, img.width(), 0, 0, img.height(), 144, 144);
            cb.setLineWidth(0);
            float x1 = 100;
            float y1 = 100;
            float x2 = 370;
            float y2 = 200;
            cb.moveTo(x1, y1);
            cb.lineTo(x1, y2);
            cb.lineTo(x2, y2);
            cb.lineTo(x2, y1);
            cb.lineTo(x1, y1);
            cb.stroke();
            cb.arc(x1, y1, x2, y2, 45f, -90f);
            cb.lineTo(x1, y1);
            cb.stroke();
            cb.addTemplate(cbt, 50, 60);
            cbm.addTemplate(cb, 0, 0);
            cbm.addTemplate(cb, 10, 10);
            cbm.addTemplate(cb, .5f, 0, 0, .5f, 20, 20);
            int k;
            for (k = 2; k < 12; ++k) {
                document.newPage();
                document.add(pg2);
                document.add(table);
                pageEvent.setActive(true);
                document.add(pg);
                pageEvent.setActive(false);
                document.add(pg3);
                document.add(pg2);
                cbm.beginText();
                cbm.setFontAndSize(bf, 12);
                String text = "Page " + k + " of ";
                float len = bf.getWidthPoint(text, 12);
                cbm.setTextMatrix(280, 40);
                cbm.showText(text);
                cbm.endText();
                cbm.addTemplate(cbt, 280 + len, 40);
                cbm.setRGBColorStrokeF(0, 1, 0);
                cbm.setLineWidth(0);
                cbm.moveTo(350, 800);
                cbm.lineTo(350, 0);
                cbm.stroke();
                cbm.moveTo(595 - 50, 800);
                cbm.lineTo(595 - 50, 0);
                cbm.stroke();
            }
            cbt.beginText();
            cbt.setFontAndSize(bf, 12);
            cbt.setTextMatrix(0, 0);
            cbt.showText("" + (k - 1));
            cbt.endText();
            document.close();
            System.out.println("FIM.");
        }
        catch (Exception de)
        {
            System.err.println(de.getMessage());
        }
    }
}
