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

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.util.HashMap;
import java.util.StringTokenizer;

import com.lowagie.text.Document;
import com.lowagie.text.ElementTags;
import com.lowagie.text.PageSize;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.xml.XmlParser;
import com.lowagie.text.xml.XmlPeer;

public class Chap0705 {
    
    public static void main(String[] args) {
        
        System.out.println("Chapter 7 example 5: simple database example");
        
        int i = 0;
        try {
            BufferedReader reader = new BufferedReader(new FileReader("simpleDB0705.txt"));
            
            String line;
            while ((line = reader.readLine()) != null) {
                HashMap tagmap = new HashMap();
                StringTokenizer tokenizer = new StringTokenizer(line, "|");
                
                XmlPeer peer = new XmlPeer(ElementTags.ITEXT, "letter");
                tagmap.put(peer.getAlias(), peer);
                if (tokenizer.hasMoreTokens()) {
                    peer = new XmlPeer(ElementTags.CHUNK, "givenname");
                    peer.setContent(tokenizer.nextToken());
                    tagmap.put(peer.getAlias(), peer);
                    
                }
                if (tokenizer.hasMoreTokens()) {
                    peer = new XmlPeer(ElementTags.CHUNK, "name");
                    peer.setContent(tokenizer.nextToken());
                    tagmap.put(peer.getAlias(), peer);
                    
                }
                if (tokenizer.hasMoreTokens()) {
                    peer = new XmlPeer(ElementTags.CHUNK, "mail");
                    peer.setContent(tokenizer.nextToken());
                    tagmap.put(peer.getAlias(), peer);
                    
                }
                if (tokenizer.hasMoreTokens()) {
                    peer = new XmlPeer(ElementTags.ANCHOR, "website");
                    String reference = tokenizer.nextToken();
                    peer.setContent(reference);
                    peer.addValue(ElementTags.REFERENCE, reference);
                    peer.addValue(ElementTags.COLOR, "#0000FF");
                    tagmap.put(peer.getAlias(), peer);
                    
                }
                
                // step 1: creation of a document-object
                Document document = new Document(PageSize.A4, 80, 50, 30, 65);
                
                // step 2:
                // we create a writer that listens to the document
                PdfWriter.getInstance(document, new FileOutputStream("Chap0705_" + (++i) + ".pdf"));
                
                // step 3: we parse the document
                XmlParser.parse(document, "simpleLetter0705.xml", tagmap);
            }
        }
        catch(Exception e) {
            e.printStackTrace();
            System.err.println(e.getMessage());
        }
    }
}