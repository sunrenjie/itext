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
import java.util.Iterator;
import java.util.TreeMap;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.pdf.PdfPageEventHelper;

class Glossary extends PdfPageEventHelper {
    
    // we will keep a glossary of words and the pages they appear on in a TreeMap
    TreeMap glossary = new TreeMap();
    
    // we override only the onGenericTag method
    public void onGenericTag(PdfWriter writer, Document document, Rectangle rect, String text) {
        glossary.put(text, new Integer(writer.getPageNumber()));
    }
    
    // we add a method to retrieve the glossary
    public TreeMap getGlossary() {
        return glossary;
    }
    
}

public class Chap0206 {
    
    public static void main(String[] args) {
        
        System.out.println("Chapter 2 example 6: generic tags");
        
        // step 1: creation of a document-object
        Document document = new Document();
        
        try {
            
            // step 2:
            // we create a writer that listens to the document
            // and directs a PDF-stream to a file
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("Chap0206.pdf"));
            
            // step 3: we open the document
            document.open();
            
            // step 4:
            // we create an Event and add it to the writer
            Glossary pageEvent = new Glossary();
            writer.setPageEvent(pageEvent);
            
            // we add some content
            String[] f = new String[14];
            f[0] = "Courier";
            f[1] = "Courier Bold";
            f[2] = "Courier Italic";
            f[3] = "Courier Bold Italic";
            f[4] = "Helvetica";
            f[5] = "Helvetica bold";
            f[6] = "Helvetica italic";
            f[7] = "Helvetica bold italic";
            f[8] = "Times New Roman";
            f[9] = "Times New Roman bold";
            f[10] = "Times New Roman italic";
            f[11] = "Times New Roman bold italic";
            f[12] = "Symbol";
            f[13] = "Zapfdingbats";
            Font[] fonts = new Font[14];
            fonts[0] = new Font(Font.COURIER, 12, Font.NORMAL);
            fonts[1] = new Font(Font.COURIER, 12, Font.BOLD);
            fonts[2] = new Font(Font.COURIER, 12, Font.ITALIC);
            fonts[3] = new Font(Font.COURIER, 12, Font.BOLD | Font.ITALIC);
            fonts[4] = new Font(Font.HELVETICA, 12, Font.NORMAL);
            fonts[5] = new Font(Font.HELVETICA, 12, Font.BOLD);
            fonts[6] = new Font(Font.HELVETICA, 12, Font.ITALIC);
            fonts[7] = new Font(Font.HELVETICA, 12, Font.BOLD | Font.ITALIC);
            fonts[8] = new Font(Font.TIMES_NEW_ROMAN, 12, Font.NORMAL);
            fonts[9] = new Font(Font.TIMES_NEW_ROMAN, 12, Font.BOLD);
            fonts[10] = new Font(Font.TIMES_NEW_ROMAN, 12, Font.ITALIC);
            fonts[11] = new Font(Font.TIMES_NEW_ROMAN, 12, Font.BOLD | Font.ITALIC);
            fonts[12] = new Font(Font.SYMBOL, 12, Font.NORMAL);
            fonts[13] = new Font(Font.ZAPFDINGBATS, 12, Font.NORMAL);
            for (int i = 0; i < 14; i++) {
                Chunk chunk = new Chunk("This is font ", fonts[i]);
                Paragraph p = new Paragraph(chunk);
                p.add(new Phrase(new Chunk(f[i], fonts[i]).setGenericTag(f[i])));
                document.add(p);
                if (i % 4 == 3) {
                    document.newPage();
                }
            }
            
            // we add the glossary
            document.newPage();
            TreeMap glossary = pageEvent.getGlossary();
            for (Iterator i = glossary.keySet().iterator(); i.hasNext(); ) {
                String key = (String) i.next();
                int page = ((Integer) glossary.get(key)).intValue();
                Paragraph g = new Paragraph(key);
                g.add(" : page ");
                g.add(String.valueOf(page));
                document.add(g);
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