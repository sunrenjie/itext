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
 * ir-arch Bruno Lowagie,
 * Adolf Baeyensstraat 121
 * 9040 Sint-Amandsberg
 * BELGIUM
 * tel. +32 (0)9 228.10.97
 * bruno@lowagie.com
 */

import java.awt.Color;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

import com.lowagie.text.*;
import com.lowagie.text.xml.*;

public class Chap0701 {
    
    public static void main(String[] args) {
        
        System.out.println("Chapter 7 example 1: my first XML");
        
        // step 1: creation of a document-object
        Document document = new Document();
        
        try {
            
            // step 2:
            // we create a writer that listens to the document
            // and directs a XML-stream to a file
            XmlWriter.getInstance(document, new FileOutputStream("Chap0701.xml"), "http://www.lowagie.com/iText/itext.dtd");
            
            // step 3: we open the document
            document.open();
            
            // step 4: we add content to the document
            Paragraph paragraph = new Paragraph("Please visit my ");
            Anchor anchor1 = new Anchor("website (external reference)", new Font(Font.HELVETICA, 12, Font.UNDERLINE, new Color(0, 0, 255)));
            anchor1.setReference("http://www.lowagie.com/iText/");
            anchor1.setName("top");
            paragraph.add(anchor1);
            document.add(paragraph);
            
            Paragraph entities = new Paragraph("These are some special characters: <, >, &, \" and '");
            document.add(entities);
            
            document.add(new Paragraph("some books I really like:"));
            List list;
            ListItem listItem;
            list = new List(true, 15);
            listItem = new ListItem("When Harlie was one", new Font(Font.TIMES_NEW_ROMAN, 12));
            listItem.add(new Chunk(" by David Gerrold", new Font(Font.TIMES_NEW_ROMAN, 11, Font.ITALIC)).setTextRise(8.0f));
            list.add(listItem);
            listItem = new ListItem("The World according to Garp", new Font(Font.TIMES_NEW_ROMAN, 12));
            listItem.add(new Chunk(" by John Irving", new Font(Font.TIMES_NEW_ROMAN, 11, Font.ITALIC)).setTextRise(-8.0f));
            list.add(listItem);
            listItem = new ListItem("Decamerone", new Font(Font.TIMES_NEW_ROMAN, 12));
            listItem.add(new Chunk(" by Giovanni Boccaccio", new Font(Font.TIMES_NEW_ROMAN, 11, Font.ITALIC)));
            list.add(listItem);
            document.add(list);
            
            paragraph = new Paragraph("some movies I really like:");
            list = new List(false, 10);
            list.add("Wild At Heart");
            list.add("Casablanca");
            list.add("When Harry met Sally");
            list.add("True Romance");
            list.add("Le mari de la coiffeuse");
            paragraph.add(list);
            document.add(paragraph);
            
            document.add(new Paragraph("Some authors I really like:"));
            list = new List(false, 20);
            list.setListSymbol(new Chunk("*", new Font(Font.HELVETICA, 20, Font.BOLD)));
            listItem = new ListItem("Isaac Asimov");
            list.add(listItem);
            List sublist;
            sublist = new List(true, 10);
            sublist.setListSymbol(new Chunk("", new Font(Font.HELVETICA, 8)));
            sublist.add("The Foundation Trilogy");
            sublist.add("The Complete Robot");
            sublist.add("Caves of Steel");
            sublist.add("The Naked Sun");
            list.add(sublist);
            listItem = new ListItem("John Irving");
            list.add(listItem);
            sublist = new List(true, 10);
            sublist.setListSymbol(new Chunk("", new Font(Font.HELVETICA, 8)));
            sublist.add("The World according to Garp");
            sublist.add("Hotel New Hampshire");
            sublist.add("A prayer for Owen Meany");
            sublist.add("Widow for a year");
            list.add(sublist);
            listItem = new ListItem("Kurt Vonnegut");
            list.add(listItem);
            sublist = new List(true, 10);
            sublist.setListSymbol(new Chunk("", new Font(Font.HELVETICA, 8)));
            sublist.add("Slaughterhouse 5");
            sublist.add("Welcome to the Monkey House");
            sublist.add("The great pianola");
            sublist.add("Galapagos");
            list.add(sublist);
            document.add(list);
            
            paragraph = new Paragraph("\n\n");
            document.add(paragraph);
            
            Table table = new Table(3);
            table.setBorderWidth(1);
            table.setBorderColor(new Color(0, 0, 255));
            table.setCellpadding(5);
            table.setCellspacing(5);
            Cell cell = new Cell("header");
            cell.setHeader(true);
            cell.setColspan(3);
            table.addCell(cell);
            table.endHeaders();
            cell = new Cell("example cell with colspan 1 and rowspan 2");
            cell.setRowspan(2);
            cell.setBorderColor(new Color(255, 0, 0));
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
            document.add(table);
            
            Image jpeg = Image.getInstance("myKids.jpg");
            document.add(jpeg);
            Image png = Image.getInstance(new URL("http://www.lowagie.com/iText/examples/hitchcock.png"));
            document.add(png);
            Anchor anchor2 = new Anchor("please jump to a local destination", new Font(Font.HELVETICA, 12, Font.NORMAL, new Color(0, 0, 255)));
            anchor2.setReference("#top");
            document.add(anchor2);
            
            document.add(paragraph);
            
            // we define some fonts
            Font chapterFont = new Font(Font.HELVETICA, 24, Font.NORMAL, new Color(255, 0, 0));
            Font sectionFont = new Font(Font.HELVETICA, 20, Font.NORMAL, new Color(0, 0, 255));
            Font subsectionFont = new Font(Font.HELVETICA, 18, Font.BOLD, new Color(0, 64, 64));
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
                    chapter.add(table);
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
                            subsection.add(table);
                        }
                        subsection.add(blahblah);
                    }
                    if (j == 2 && i > 2) {
                        section.add(blahblahblah);
                        section.add(table);
                    }
                }
                document.add(chapter);
            }
            
        }
        catch(DocumentException de) {
            System.err.println(de.getMessage());
        }
        catch(IOException ioe) {
            System.err.println(ioe.getMessage());
        }
        
        // step 5: we close the document
        document.close();
    }
}