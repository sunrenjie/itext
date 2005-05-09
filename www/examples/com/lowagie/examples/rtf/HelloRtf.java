/*
 * $Id$
 * $Name$
 *
 * This code is free software. It may only be copied or modified
 * if you include the following copyright notice:
 *
 * --> Copyright 2001 by Bruno Lowagie <--
 * --> Copyright 2004 by Mark Hall <--
 *
 * This code is part of the 'iText Tutorial'.
 * You can find the complete tutorial at the following address:
 * http://itextdocs.lowagie.com/tutorial/
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

/**
 * Generates an RTF document with the text 'Hello'
 * 
 * @author Mark Hall
 */
public class HelloRtf {
    
    /**
     * Creates an RTF file.
     * 
     * @param args some text. For instance: if you add 'World' as argument, the RTF will contain the text 'Hello World'.
     */
    public static void main(String[] args) {
        
        System.out.println("Hello World example in RTF");
        
        // step 1: creation of a document-object
        Document document = new Document();
        
        try {
            
            // step 2:
            // we create a writer that listens to the document
            // and directs a RTF-stream to a file
            
            RtfWriter2.getInstance(document, new FileOutputStream("HelloWorld.rtf"));
            
            // step 3: we open the document
            document.open();
            
            // step 4: we add a paragraph to the document
			Paragraph p = new Paragraph("Hello");
			for (int i = 0; i < args.length; i++) {
				p.add(" ");
				p.add(args[i]);
			}
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
}