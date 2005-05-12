/*
 * $Id$
 * $Name$
 *
 * This code is free software. It may only be copied or modified
 * if you include the following copyright notice:
 *
 * --> Copyright 2004 by Mark Hall <--
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

package com.lowagie.examples.rtf;

import java.io.FileOutputStream;
import java.io.IOException;

import com.lowagie.text.Cell;
import com.lowagie.text.Chapter;
import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.HeaderFooter;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Table;
import com.lowagie.text.rtf.RtfWriter2;
import com.lowagie.text.rtf.headerfooter.RtfHeaderFooter;
import com.lowagie.text.rtf.headerfooter.RtfHeaderFooterGroup;
import com.lowagie.text.rtf.field.RtfPageNumber;
import com.lowagie.text.rtf.field.RtfTotalPageNumber;

/**
 * This example creates a RTF document with more complex headers and footers
 * using the RtfHeaderFooters class.
 * 
 * @author <a href="mailto:mhall@myrealbox.com">Mark.Hall@myrealbox.com </a>
 */
public class RtfWithHeadersFooters {

    /**
     * Creates an RTF document with complex headers and footers.
     * 
     * @param args no arguments needed
     */
    public static void main(String[] args) {

        System.out
                .println("Extended headers / footers in RTF");

        Document document = new Document(PageSize.A4);
        try {
            RtfWriter2 rtf = RtfWriter2.getInstance(document, new FileOutputStream("headerfooter.rtf"));

            /* Create a table based header */
            Table headerTable = new Table(3);
            headerTable.addCell("Document header");
            Cell pageNumberCell = new Cell();
            pageNumberCell.add(new RtfPageNumber());
            pageNumberCell.add(new Chunk(" of "));
            pageNumberCell.add(new RtfTotalPageNumber());
            headerTable.addCell(pageNumberCell);
            headerTable.addCell("Company Name");
            HeaderFooter header = new RtfHeaderFooter(headerTable);

            /*
             * Create different headers for the title page and all following
             * pages
             */
            RtfHeaderFooterGroup footer = new RtfHeaderFooterGroup();
            footer.setHeaderFooter(new RtfHeaderFooter(new Phrase("This is the footer on the title page")), com.lowagie.text.rtf.headerfooter.RtfHeaderFooter.DISPLAY_FIRST_PAGE);
            footer.setHeaderFooter(new RtfHeaderFooter(new Phrase("This is a left side page")), com.lowagie.text.rtf.headerfooter.RtfHeaderFooter.DISPLAY_LEFT_PAGES);
            footer.setHeaderFooter(new RtfHeaderFooter(new Phrase("This is a right side page")), com.lowagie.text.rtf.headerfooter.RtfHeaderFooter.DISPLAY_RIGHT_PAGES);

            document.setHeader(header);
            document.setFooter(footer);

            document.open();

            document.add(new RtfPageNumber());
            Chapter chapter = new Chapter(new Paragraph("Advanced RTF headers and footers", new Font(Font.HELVETICA, 16, Font.BOLD)), 1);

            chapter.add(new Paragraph("This document demonstrates the use of advanced RTF headers and footers."));

            for (int i = 0; i < 300; i++) {
                chapter.add(new Paragraph("Line " + i));
            }
            document.add(chapter);
        } catch (DocumentException de) {
            System.err.println(de.getMessage());
        } catch (IOException ioe) {
            System.err.println(ioe.getMessage());
        }
        document.close();
    }
}