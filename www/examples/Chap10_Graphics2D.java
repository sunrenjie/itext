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

import java.awt.*;
import java.awt.geom.*;
import java.io.FileOutputStream;
import java.io.IOException;

import com.lowagie.text.*;
import com.lowagie.text.pdf.*;

public class Chap10_Graphics2D {
    
    public static void main(String[] args) {
        
        System.out.println("Chapter 10: Using the java.awt.Graphics2D-object");
        
        // step 1: creation of a document-object
        Document document = new Document();
        
        try {
            
            // step 2:
            // we create a writer that listens to the document
            // and directs a PDF-stream to a file
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("Chap10_Graphics2D.pdf"));
            
            // step 3: we open the document
            document.open();
            
            // step 4: we grab the ContentByte and do some stuff with it
            
            // we create a fontMapper and read all the fonts in the font directory
            DefaultFontMapper mapper = new DefaultFontMapper();
            mapper.insertDirectory("c:\\winnt\\fonts");
            // we create a template and a Graphics2D object that corresponds with it
            int w = 150;
            int h = 150;
            PdfContentByte cb = writer.getDirectContent();
            PdfTemplate tp = cb.createTemplate(w, h);
            Graphics2D g2 = tp.createGraphics(w, h, mapper);
            tp.setWidth(w);
            tp.setHeight(h);
            double ew = w/2;
            double eh = h/2;
            Ellipse2D.Double circle, oval, leaf, stem;
            Area circ, ov, leaf1, leaf2, st1, st2;
            circle = new Ellipse2D.Double();
            oval = new Ellipse2D.Double();
            leaf = new Ellipse2D.Double();
            stem = new Ellipse2D.Double();
            circ = new Area(circle);
            ov = new Area(oval);
            leaf1 = new Area(leaf);
            leaf2 = new Area(leaf);
            st1 = new Area(stem);
            st2 = new Area(stem);
            g2.setColor(Color.green);
            
            // Creates the first leaf by filling the intersection of two Area objects created from an ellipse.
            leaf.setFrame(ew-16, eh-29, 15.0, 15.0);
            leaf1 = new Area(leaf);
            leaf.setFrame(ew-14, eh-47, 30.0, 30.0);
            leaf2 = new Area(leaf);
            leaf1.intersect(leaf2);
            g2.fill(leaf1);
            
            // Creates the second leaf.
            leaf.setFrame(ew+1, eh-29, 15.0, 15.0);
            leaf1 = new Area(leaf);
            leaf2.intersect(leaf1);
            g2.fill(leaf2);
            
            g2.setColor(Color.black);
            
            // Creates the stem by filling the Area resulting from the subtraction of two Area objects created from an ellipse.
            stem.setFrame(ew, eh-42, 40.0, 40.0);
            st1 = new Area(stem);
            stem.setFrame(ew+3, eh-47, 50.0, 50.0);
            st2 = new Area(stem);
            st1.subtract(st2);
            g2.fill(st1);
            
            g2.setColor(Color.yellow);
            
            // Creates the pear itself by filling the Area resulting from the union of two Area objects created by two different ellipses.
            circle.setFrame(ew-25, eh, 50.0, 50.0);
            oval.setFrame(ew-19, eh-20, 40.0, 70.0);
            circ = new Area(circle);
            ov = new Area(oval);
            circ.add(ov);
            g2.fill(circ);
            
            g2.setColor(Color.black);
            java.awt.Font thisFont = new java.awt.Font("Arial", java.awt.Font.PLAIN, 18);
            g2.setFont(thisFont);
            String pear = "Pear";
            FontMetrics metrics = g2.getFontMetrics();
            int width = metrics.stringWidth(pear);
            g2.drawString(pear, (w - width) / 2, 20);
            g2.dispose();
            cb.addTemplate(tp, 50, 400);
            
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
