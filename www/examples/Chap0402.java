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
 * itext-questions@lists.sourceforge.net
 */

import java.io.FileOutputStream;
import java.awt.Color;

import com.lowagie.text.*;
import com.lowagie.text.pdf.*;

public class Chap0402 {
    
    public static void main(String[] args) {
        
        System.out.println("Chapter 4 example 2: Chapters and Sections");
        
        // step 1: creation of a document-object
        Document document = new Document(PageSize.A4, 50, 50, 50, 50);
        try {
            // step 2: we create a writer that listens to the document
            PdfWriter writer=PdfWriter.getInstance(document, new FileOutputStream("Chap0402.pdf"));
            // step 3: we open the document
            document.open();
            // step 4: we add content to the document
            // we define some fonts
            Font chapterFont = FontFactory.getFont(FontFactory.HELVETICA, 24, Font.NORMAL, new Color(255, 0, 0));
            Font sectionFont = FontFactory.getFont(FontFactory.HELVETICA, 20, Font.NORMAL, new Color(0, 0, 255));
            Font subsectionFont = FontFactory.getFont(FontFactory.HELVETICA, 18, Font.BOLD, new Color(0, 64, 64));
            // we create some paragraphs
            Paragraph blahblah = new Paragraph("blah blah blah blah blah blah blaah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah");
            Paragraph blahblahblah = new Paragraph("blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blaah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah");
            // this loop will create 7 chapters
            for (int i = 1; i < 8; i++) {
                Paragraph cTitle = new Paragraph("This is chapter " + i, chapterFont);
                Chapter chapter = new Chapter(cTitle, i);
                
                if (i == 4) {
                    blahblahblah.setAlignment(Element.ALIGN_JUSTIFIED);
                    blahblah.setAlignment(Element.ALIGN_JUSTIFIED);
                    chapter.add(blahblah);
                }
                if (i == 5) {
                    blahblahblah.setAlignment(Element.ALIGN_CENTER);
                    blahblah.setAlignment(Element.ALIGN_RIGHT);
                    chapter.add(blahblah);
                }
                // add a table in the 6th chapter
                if (i == 6) {
                    blahblah.setAlignment(Element.ALIGN_JUSTIFIED);
                }
                // in every chapter 3 sections will be added
                for (int j = 1; j < 4; j++) {
                    Paragraph sTitle = new Paragraph("This is section " + j + " in chapter " + i, sectionFont);
                    Section section = chapter.addSection(sTitle, 1);
                    // in all chapters except the 1st one, some extra text is added to section 3
                    if (j == 3 && i > 1) {
                        section.add(blahblah);
                    }
                    // in every section 3 subsections are added
                    for (int k = 1; k < 4; k++) {
                        Paragraph subTitle = new Paragraph("This is subsection " + k + " of section " + j, subsectionFont);
                        Section subsection = section.addSection(subTitle, 3);
                        if (k == 1 && j == 3) {
                            subsection.add(blahblahblah);
                        }
                        subsection.add(blahblah);
                    }
                    if (j == 2 && i > 2) {
                        section.add(blahblahblah);
                    }
                }
                document.add(chapter);
            }
        }
        catch(Exception de) {
            de.printStackTrace();
        }
        // step 5: we close the document
        document.close();
    }
}

