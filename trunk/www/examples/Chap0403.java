/*
 * $Id$
 * $Name$
 *
 * This code is free software. It may only be copied or modified
 * if you include the following copyright notice:
 *
 * --> Copyright 2001 by Bruno Lowagie and Paulo Soares <--
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

import java.io.FileOutputStream;
import java.io.IOException;
import java.awt.Color;

import com.lowagie.text.*;
import com.lowagie.text.pdf.*;

public class Chap0403 {
    
    public static void main(String[] args) {
        
        System.out.println("Chapter 4 example 3: Chapters and Sections");
        
        // step 1: creation of a document-object
        Document document = new Document(PageSize.A4, 50, 50, 50, 50);
        try {
            // step 2: we create a writer that listens to the document
            PdfWriter writer=PdfWriter.getInstance(document, new FileOutputStream("Chap0403.pdf"));
            // step 3: we open the document
            document.open();
            // step 4: we add content to the document
            Paragraph title1 = new Paragraph("This is Chapter 1", new Font(Font.HELVETICA, 18, Font.BOLDITALIC, new Color(0, 0, 255)));
            Chapter chapter1 = new Chapter(title1, 2);
            chapter1.setNumberDepth(0);
            Paragraph someText = new Paragraph("This is some text");
            chapter1.add(someText);
            Paragraph title11 = new Paragraph("This is Section 1 in Chapter 1", new Font(Font.HELVETICA, 16, Font.BOLD, new Color(255, 0, 0)));
            Section section1 = chapter1.addSection(title11);
            Paragraph someSectionText = new Paragraph("This is some silly paragraph in a chapter and/or section. It contains some text to test the functionality of Chapters and Section.");
            section1.add(someSectionText);
            document.add(chapter1);
            
            Paragraph title2 = new Paragraph("This is Chapter 2", new Font(Font.HELVETICA, 18, Font.BOLDITALIC, new Color(0, 0, 255)));
            Chapter chapter2 = new Chapter(title2, 2);
            chapter2.setNumberDepth(0);
            chapter2.add(someText);
            Paragraph title21 = new Paragraph("This is Section 1 in Chapter 2", new Font(Font.HELVETICA, 16, Font.BOLD, new Color(255, 0, 0)));
            Section section2 = chapter2.addSection(title21);
            section2.add(someSectionText);
            chapter2.setBookmarkOpen(false);
            document.add(chapter2);
        }
        catch(Exception de) {
            de.printStackTrace();
        }
        // step 5: we close the document
        document.close();
    }
}

