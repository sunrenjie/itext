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

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.TreeSet;
import java.util.Iterator;
import java.util.HashMap;
import java.util.EmptyStackException;

import org.xml.sax.Parser;
import org.xml.sax.helpers.ParserFactory;

import com.lowagie.text.*;
import com.lowagie.text.pdf.*;
import com.lowagie.text.xml.*;

/**
 * We want to change the behaviour of the handler in some cases
 */

class MyHandler extends SAXmyHandler {
    
/**
 * We have to override the constructor
 */
    
    public MyHandler(Document document, HashMap tagmap) {
        super(document, tagmap);
    }
    
/**
 * We only alter the handling of some endtags.
 */
    
    public void endElement(String name) {
        if (myTags.containsKey(name)) {
            XmlPeer peer = (XmlPeer) myTags.get(name);
            // we don't want the document to be close
            // because we are going to add a page after the xml is parsed
            if (isDocumentRoot(peer.getTag())) {
                return;
            }
            handleEndingTags(peer.getTag());
            // we want to add a paragraph after the speaker chunk
            if ("SPEAKER".equals(name)) {
                try {
                    TextElementArray previous = (TextElementArray) stack.pop();
                    previous.add(new Paragraph(16));
                    stack.push(previous);
                }
                catch(EmptyStackException ese) {
                }
            }
        }
        else {
            handleEndingTags(name);
        }
    }
}

/**
 * We use the tagfile from chapter 7, but we want to change some tag definitions.
 */

class MyMap extends TagMap {
    
    public MyMap(String tagfile) {
        super(tagfile);
        XmlPeer peer = new XmlPeer(ElementTags.CHUNK, "SPEAKER");
        peer.addValue(ElementTags.SIZE, "10");
        peer.addValue(ElementTags.STYLE, "bold");
        peer.addValue(ElementTags.GENERICTAG, "");
        put(peer.getAlias(), peer);
    }
}

/**
 * This object contains a speaker and a number of occurrances in the play
 */

class Speaker implements Comparable {
    
    // name of the speaker
    private String name;
    
    // number of occurrances
    private int occurrance = 1;
    
    public Speaker(String name) {
        this.name = name;
    }
    
    public String name() {
        return name;
    }
    
    public int occurrance() {
        return occurrance;
    }
    
    public int compareTo(Object o) {
        Speaker otherSpeaker = (Speaker) o;
        if (otherSpeaker.name().equals(name)) {
            this.occurrance += otherSpeaker.occurrance();
            otherSpeaker.occurrance = this.occurrance;
            return 0;
        }
        return name.compareTo(otherSpeaker.name());
    }
}

/**
 * Your own page events.
 */

class MyPageEvents extends PdfPageEventHelper {
    
    // we will keep a list of speakers
    TreeSet speakers = new TreeSet();
    
    // This is the contentbyte object of the writer
    PdfContentByte cb;
    
    // we will put the final number of pages in a template
    PdfTemplate template;
    
    // this is the BaseFont we are going to use for the header / footer
    BaseFont bf = null;
    
    // this is the current act of the play
    String act = "";
    
    // we override the onGenericTag method
    public void onGenericTag(PdfWriter writer, Document document, Rectangle rect, String text) {
        speakers.add(new Speaker(text));
    }
    
    // we override the onOpenDocument method
    public void onOpenDocument(PdfWriter writer, Document document) {
        try {
            bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
            cb = writer.getDirectContent();
            template = cb.createTemplate(50, 50);
        }
        catch(DocumentException de) {
        }
        catch(IOException ioe) {
        }
    }
    
    // we override the onChapter method
    public void onChapter(PdfWriter writer,Document document,float paragraphPosition,Paragraph title) {
        StringBuffer buf = new StringBuffer();
        for (Iterator i = title.getChunks().iterator(); i.hasNext(); ) {
            Chunk chunk = (Chunk) i.next();
            buf.append(chunk.content());
        }
        act = buf.toString();
    }
    
    // we override the onEndPage method
    public void onEndPage(PdfWriter writer, Document document) {
        int pageN = writer.getPageNumber();
        String text = "Page " + pageN + " of ";
        float len = bf.getWidthPoint(text, 8);
        cb.beginText();
        cb.setFontAndSize(bf, 8);
        cb.setTextMatrix(280, 30);
        cb.showText(text);
        cb.endText();
        cb.addTemplate(template, 280 + len, 30);
        cb.beginText();
        cb.setFontAndSize(bf, 8);
        cb.setTextMatrix(280, 820);
        if (pageN % 2 == 1) {
            cb.showText("Romeo and Juliet");
        }
        else {
            cb.showText(act);
        }
        cb.endText();
    }
    
    // we override the onCloseDocument method
    public void onCloseDocument(PdfWriter writer, Document document) {
        template.beginText();
        template.setFontAndSize(bf, 8);
        template.showText(String.valueOf(writer.getPageNumber() - 1));
        template.endText();
    }
    
    // we add a method to retrieve the glossary
    public TreeSet getSpeakers() {
        return speakers;
    }
}

public class Chap1201 {
    
    private static final String PARSER = "org.apache.xerces.parsers.SAXParser";
    
    public static void main(String[] args) {
        
        System.out.println("Chapter 12 example 1: page events");
        
        // step 1: creation of a document-object
        Document document = new Document(PageSize.A4, 80, 50, 30, 65);
        
        try {
            // step 2:
            // we create a writer that listens to the document
            // and directs a XML-stream to a file
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("Chap1201.pdf"));
            
            // create add the event handler
            MyPageEvents events = new MyPageEvents();
            writer.setPageEvent(events);
            
            // step 3: we create a parser and set the document handler
            Parser parser = ParserFactory.makeParser(PARSER);
            parser.setDocumentHandler(new MyHandler(document, new MyMap("tagmap0703.xml")));
            
            // step 4: we parse the document
            parser.parse("Chap0703.xml");
            
            document.newPage();
            Speaker speaker;
            for (Iterator i = events.getSpeakers().iterator(); i.hasNext(); ) {
                speaker = (Speaker) i.next();
                document.add(new Paragraph(speaker.name() + ": " + speaker.occurrance() + " speech blocks"));
            }
            document.close();
            
        }
        catch(Exception e) {
            e.printStackTrace();
            System.err.println(e.getMessage());
        }
    }
}