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
 * itext@lowagie.com
 */

import java.io.FileOutputStream;
import java.io.IOException;

import com.lowagie.text.*;
import com.lowagie.text.pdf.*;

public class Chap03_hyphenation {
    
    public static void main(String[] args) {
        
        System.out.println("Chapter 3 example 4: annotations at absolute positions");
        
        // step 1: creation of a document-object
        Document document = new Document(PageSize.A4, 100, 300, 100, 100);
        try {
            // step 2: we create a writer that listens to the document
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("Chap03_hyphenation.pdf"));
            // step 3: we open the document
            document.open();
            // step 4: we add some content
            String text = "It was the best of times, it was the worst of times, " + 
                "it was the age of wisdom, it was the age of foolishness, " +
                "it was the epoch of belief, it was the epoch of incredulity, " +
                "it was the season of Light, it was the season of Darkness, " +
                "it was the spring of hope, it was the winter of despair, " +
                "we had everything before us, we had nothing before us, " +
                "we were all going direct to Heaven, we were all going direct " +
                "the other way\u2014in short, the period was so far like the present " +
                "period, that some of its noisiest authorities insisted on its " +
                "being received, for good or for evil, in the superlative degree " +
                "of comparison only.";
            document.add(new Paragraph("GB"));
            Chunk ckEN = new Chunk(text);
            HyphenationAuto autoEN = new HyphenationAuto("en", "GB", 2, 2);
            ckEN.setHyphenation(autoEN);
            Paragraph pEN = new Paragraph(ckEN);
            pEN.setAlignment(Paragraph.ALIGN_JUSTIFIED);
            document.add(pEN);
            document.add(new Paragraph("US"));
            Chunk ckUS = new Chunk(text);
            HyphenationAuto autoUS = new HyphenationAuto("en", "US", 2, 2);
            ckUS.setHyphenation(autoUS);
            Paragraph pUS = new Paragraph(ckUS);
            pUS.setAlignment(Paragraph.ALIGN_JUSTIFIED);
            document.add(pUS);
        }
        catch(Exception de) {
            de.printStackTrace();
        }
        // step 5: we close the document
        document.close();
    }
}
