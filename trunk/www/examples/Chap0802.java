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

import com.lowagie.text.*;
import com.lowagie.text.rtf.RtfWriter;

public class Chap0802 {
    
    public static void main(String[] args) {
        
        System.out.println("Chapter 8 example 2: Lists and RTF");
        
        // step 1: creation of a document-object
        Document document = new Document();
        
        try {
            
            // step 2:
            // we create a writer that listens to the document
            // and directs a PDF-stream to a file
            RtfWriter.getInstance(document, new FileOutputStream("Chap0802.rtf"));
            
            // step 3: we open the document
            document.open();
            
            // step 4:
            
            document.add(new Paragraph("some books I really like:"));
            ListItem listItem;
            List list = new List(true, 15);
            listItem = new ListItem("When Harlie was one", new Font(Font.TIMES_NEW_ROMAN, 12));
            listItem.add(new Chunk(" by David Gerrold", new Font(Font.TIMES_NEW_ROMAN, 11, Font.ITALIC)));
            list.add(listItem);
            listItem = new ListItem("The World according to Garp", new Font(Font.TIMES_NEW_ROMAN, 12));
            listItem.add(new Chunk(" by John Irving", new Font(Font.TIMES_NEW_ROMAN, 11, Font.ITALIC)));
            list.add(listItem);
            listItem = new ListItem("Decamerone", new Font(Font.TIMES_NEW_ROMAN, 12));
            listItem.add(new Chunk(" by Giovanni Boccaccio", new Font(Font.TIMES_NEW_ROMAN, 11, Font.ITALIC)));
            list.add(listItem);
            document.add(list);
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
