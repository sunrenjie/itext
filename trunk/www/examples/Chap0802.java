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

import java.awt.Color;
import java.io.FileOutputStream;
import java.io.IOException;

import com.lowagie.text.*;
import com.lowagie.text.rtf.RtfWriter;

/**
 * This example creates a RTF document with two chapters and different headers
 * for both chapters. The same works for footers. Just replace setHeader with
 * setFooter.
 *
 * @author <a href="mailto:mhall@myrealbox.com">Mark.Hall@myrealbox.com</a>
 */
public class Chap0802 {

    public static void main(String[] args) {

        System.out.println("Chapter 8 example 2: Headers in RTF");

        // step 1: creation of a document-object
        Document document = new Document();

        try {

            // step 2:
            // we create a writer that listens to the document
            // and directs a PDF-stream to a file
            RtfWriter.getInstance(document, new FileOutputStream("Chap0802.rtf"));

            // step 3: we open the document
            document.open();

            // step 4: we create two chapters and add the same content to both.
            Paragraph par = new Paragraph("This is some sample content.");
            Chapter chap1 = new Chapter("Chapter 1", 1);
            chap1.add(par);
            Chapter chap2 = new Chapter("Chapter 2", 2);
            chap2.add(par);

            // step 5: we create the header for the first chapter, set the header and
            // then add the first chapter.
            HeaderFooter hf1 = new HeaderFooter(new Phrase("This is chapter 1"), false);
            document.setHeader(hf1);
            document.add(chap1);

            // step 6: we create a second header, set this one and then add the second
            // chapter.
            HeaderFooter hf2 = new HeaderFooter(new Phrase("This is chapter 2"), false);
            document.setHeader(hf2);
            document.add(chap2);
        }
        catch(DocumentException de) {
            System.err.println(de.getMessage());
        }
        catch(IOException ioe) {
            System.err.println(ioe.getMessage());
        }

        // step 7: we close the document
        document.close();
    }
}
