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

import java.awt.Color;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.TreeMap;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.pdf.PdfPageEventHelper;

public class Chap0208 implements SplitCharacter {
    
    public static void main(String[] args) {
        
        System.out.println("Chapter 2 example 8: split character");
        
        // step 1: creation of a document-object
        Document document = new Document();
        
        try {
            
            // step 2:
            // we create a writer that listens to the document
            // and directs a PDF-stream to a file
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("Chap0208.pdf"));
            
            // step 3: we open the document
            document.open();
            
            // step 4:
            // we add some content
            String text = "Some.text.to.show.the.splitting.action.of.the.interface.";
            Chap0208 split = new Chap0208();
            Chunk ck = new Chunk(text, new Font(Font.HELVETICA, 24));
            Paragraph p = new Paragraph(24, ck);
            document.add(new Paragraph("Normal split."));
            document.add(p);
            ck = new Chunk(text, new Font(Font.HELVETICA, 24));
            ck.setSplitCharacter(split);
            p = new Paragraph(24, ck);
            document.add(new Paragraph("The dot '.' is the split character."));
            document.add(p);
            
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
    
    /**
     * Returns <CODE>true</CODE> if the character can split a line.
     * @param c the character
     * @return <CODE>true</CODE> if the character can split a line
     */
    public boolean isSplitCharacter(char c) {
        return (c == '.');
    }
}