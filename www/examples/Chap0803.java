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
 * itext-questions@lists.sourceforge.net
 */

import java.io.FileOutputStream;
import java.io.IOException;

import com.lowagie.text.*;
import com.lowagie.text.rtf.RtfWriter2;
import com.lowagie.text.rtf.headerfooter.RtfHeaderFooter;
import com.lowagie.text.rtf.headerfooter.RtfHeaderFooterGroup;

/**
 * This example creates a RTF document with more complex headers and footers
 * using the RtfHeaderFooters class.
 *
 * @author <a href="mailto:mhall@myrealbox.com">Mark.Hall@myrealbox.com</a>
 */
public class Chap0803 {

    public static void main(String[] args) {

        System.out.println("Chapter 8 example 3: RTF with the RtfHeaderFooters class");

        /* Create a new document */
        Document document = new Document(PageSize.A4);
        try
            {
            /* Create a RtfWriter and a PdfWriter for the document */
            RtfWriter2 rtf = RtfWriter2.getInstance(document, new FileOutputStream("Chap0803.rtf"));

            /* Create a Table to use as header */
            Table headerTable = new Table(3);
            headerTable.addCell("Test Cell 1");
            headerTable.addCell("Test Cell 2");
            headerTable.addCell("Test Cell 3");
            HeaderFooter header = new RtfHeaderFooter(headerTable);
            
            /* Create different headers for the title page and left and right pages */
            RtfHeaderFooterGroup footer = new RtfHeaderFooterGroup();
            footer.setHeaderFooter(new RtfHeaderFooter(new Phrase("This is the footer on the title page")), com.lowagie.text.rtf.headerfooter.RtfHeaderFooter.DISPLAY_FIRST_PAGE);
            footer.setHeaderFooter(new RtfHeaderFooter(new Phrase("This is a left side page")), com.lowagie.text.rtf.headerfooter.RtfHeaderFooter.DISPLAY_LEFT_PAGES);
            footer.setHeaderFooter(new RtfHeaderFooter(new Phrase("This is a right side page")), com.lowagie.text.rtf.headerfooter.RtfHeaderFooter.DISPLAY_RIGHT_PAGES);
            
            document.setHeader(header);
            document.setFooter(footer);
            
            document.open();

            /* We add some content */
            Chapter chapter = new Chapter(new Paragraph("Advanced RTF headers and footers", new Font(Font.HELVETICA, 16, Font.BOLD)), 1);

            chapter.add(new Paragraph("This document demonstrates the use of advanced RTF headers and footers."));

            for(int i = 0; i < 300; i++)
                {
                chapter.add(new Paragraph("Line " + i));
                }
            document.add(chapter);
            }
        catch(DocumentException de)
            { System.err.println(de.getMessage()); }
        catch(IOException ioe)
            { System.err.println(ioe.getMessage()); }

        /* Close the document */
        document.close();
    }
}
