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

import java.io.FileOutputStream;
import java.io.IOException;
import java.awt.Color;

import com.lowagie.text.Cell;
import com.lowagie.text.Chapter;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Rectangle;
import com.lowagie.text.Table;
import com.lowagie.text.rtf.RtfWriter2;
import com.lowagie.text.rtf.field.RtfTableOfContents;
import com.lowagie.text.rtf.field.RtfTOCEntry;
import com.lowagie.text.rtf.table.RtfCell;
import com.lowagie.text.rtf.table.RtfBorder;
import com.lowagie.text.rtf.table.RtfBorderGroup;

public class Chap0804 {
    public static void main(String[] args) {
        System.out.println("Chapter 8 example 4: Table of contents and Cell borders");
        Document document = new Document();
        try {
            RtfWriter2 writer2 = RtfWriter2.getInstance(document, new FileOutputStream("Chap0804.rtf"));
            
            writer2.setAutogenerateTOCEntries(true);
            
            document.open();
            
            Paragraph para = new Paragraph();
            para.add(new RtfTableOfContents("RIGHT CLICK AND HERE AND SELECT \"UPDATE FIELD\" TO UPDATE.", new Font()));
            document.add(para);

            Paragraph par = new Paragraph("This is some sample content.");
            Chapter chap1 = new Chapter("Chapter 1", 1);
            chap1.add(par);
            Chapter chap2 = new Chapter("Chapter 2", 2);
            chap2.add(par);
            document.add(chap1);
            document.add(chap2);
            
            for(int i = 0; i < 300; i++) {
                if(i == 158) {
                    document.add(new RtfTOCEntry("This is line 158.", new Font()));
                }
                document.add(new Paragraph("Line " + i));
            }
            
            document.add(new RtfTOCEntry("Cell border demonstration", new Font()));

            Table table = new Table(3);

            RtfCell cellDotted = new RtfCell("Dotted border");
            cellDotted.setBorders(new RtfBorderGroup(Rectangle.BOX, RtfBorder.BORDER_DOTTED, 1, new Color(0, 0, 0)));
            RtfCell cellEmbossed = new RtfCell("Embossed border");
            cellEmbossed.setBorders(new RtfBorderGroup(Rectangle.BOX, RtfBorder.BORDER_EMBOSS, 1, new Color(0, 0, 0)));
            RtfCell cellNoBorder = new RtfCell("No border");
            cellNoBorder.setBorders(new RtfBorderGroup());
            
            table.addCell(cellDotted);
            table.addCell(cellEmbossed);
            table.addCell(cellNoBorder);
            
            document.add(table);
        }
        catch(DocumentException de) {
            System.err.println(de.getMessage());
        }
        catch(IOException ioe) {
            System.err.println(ioe.getMessage());
        }
        document.close();
    }
}