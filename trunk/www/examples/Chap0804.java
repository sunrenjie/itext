/*
 * $Id$
 * $Name$
 *
 * This code is free software. It may only be copied or modified
 * if you include the following copyright notice:
 *
 * --> Copyright 2000, 2001 by Bruno Lowagie <--
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

import java.io.*;
import java.awt.Color;
import com.lowagie.text.*;
import com.lowagie.text.rtf.RtfWriter2;
import com.lowagie.text.rtf.field.RtfTableOfContents;
import com.lowagie.text.rtf.field.RtfTOCEntry;

public class Chap0804 {
    public static void main(String[] args) {
        System.out.println("Chapter 8 example 4: Table of contents");
        // step 1: creation of a document-object
        Document document = new Document();
        try {
            // step 2:
            // we create a writer that listens to the document
            // and directs a PDF-stream to a file
            RtfWriter2 writer2 = RtfWriter2.getInstance(document, new FileOutputStream("Chap0804.rtf"));
            
            // Set the writer to automagically generate TOC entries
            writer2.setAutogenerateTOCEntries(true);
            
            // step 3: we open the document
            document.open();
            
            // step 4: we create a table of contents and add it to the document
            Paragraph p = new Paragraph();
            p.add(new RtfTableOfContents("RIGHT CLICK AND HERE AND SELECT \"UPDATE FIELD\" TO UPDATE.", new Font()));
            document.add(p);

            // step 4: we create two chapters and add the same content to both.
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
            // step 5: we close the document
            document.close();
        }
        catch(DocumentException de) {
            System.err.println(de.getMessage());
        }
        catch(IOException ioe) {
            System.err.println(ioe.getMessage());
        }
    }
}