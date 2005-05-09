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

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Paragraph;
import com.lowagie.text.rtf.RtfWriter2;
import com.lowagie.text.rtf.style.RtfFont;

/**
 * This example creates a RTF document with the font 'Comic'.
 *
 * @author <a href="mailto:mhall@myrealbox.com">Mark.Hall@myrealbox.com</a>
 */
public class RtfSpecialFont {

    /**
     * Creates an RTF with a special font (Comic).
     * 
     * @param args no arguments needed
     */
    public static void main(String[] args) {

        System.out.println("RTF Special Fonts");

        Document document = new Document();
        try {

            RtfWriter2.getInstance(document, new FileOutputStream("specialfont.rtf"));
            document.open();

            /* Create the font. The font name must exactly match the font name on the client system. */
            RtfFont comicSansFont = new RtfFont("Comic Sans MS");
            Paragraph para = new Paragraph("This was written in Comic Sans MS", comicSansFont);
            document.add(para);
            
            RtfFont doubleStrikethroughFont = new RtfFont("Times New Roman", 12, RtfFont.STYLE_DOUBLE_STRIKETHROUGH);
            para = new Paragraph("This paragraph uses the double strikethrough style", doubleStrikethroughFont);
            document.add(para);
            
            RtfFont hiddenFont = new RtfFont("Times New Roman", 12, RtfFont.STYLE_HIDDEN);
            para = new Paragraph("This paragraph uses the hidden style", hiddenFont);
            document.add(para);

            RtfFont embossedFont = new RtfFont("Times New Roman", 12, RtfFont.STYLE_EMBOSSED);
            para = new Paragraph("This paragraph uses the embossed style", embossedFont);
            document.add(para);
        } catch (DocumentException de) {
            System.err.println(de.getMessage());
        } catch (IOException ioe) {
            System.err.println(ioe.getMessage());
        }
        document.close();
    }
}