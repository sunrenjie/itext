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
import com.lowagie.text.rtf.*;

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
            RtfWriter rtf = RtfWriter.getInstance(document, new FileOutputStream("Chap0803.rtf"));

            /* We specify that the RTF file has a Title Page */
            rtf.setHasTitlePage(true);

            /* We create headers and footers for the RTF file */
            RtfHeaderFooters header = new RtfHeaderFooters();
            RtfHeaderFooters footer = new RtfHeaderFooters();

            /* We add a header that will only appear on the first page */
            header.set(RtfHeaderFooters.FIRST_PAGE, new HeaderFooter(new Phrase("This header is only on the first page"), false));
            /* We add a header that will only appear on left-side pages */
            header.set(RtfHeaderFooters.LEFT_PAGES, new HeaderFooter(new Phrase("This header is only on left pages"), false));
            /* We add a header that will only appear on right-side pages */
            header.set(RtfHeaderFooters.RIGHT_PAGES, new HeaderFooter(new Phrase("This header is only on right pages. "), false));
            /* We add a footer that will appear on all pages except the first (because of the title page)
               Because the header has different left and right page footers, we have to add the footer to
               both the left and right pages. */
            footer.set(RtfHeaderFooters.LEFT_PAGES, new HeaderFooter(new Phrase("This footer is on all pages except the first. Page: "), true));
            footer.set(RtfHeaderFooters.RIGHT_PAGES, new HeaderFooter(new Phrase("This footer is on all pages except the first. Page: "), true));

            /* Open the document */
            document.open();


            /* We add the header and footer */
            document.setHeader(header);
            document.setFooter(footer);


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
