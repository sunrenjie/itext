/*
 * $Id$
 * $Name$
 *
 * This code is free software. It may only be copied or modified
 * if you include the following copyright notice
 *
 * --> Copyright 2002 by Bruno Lowagie <--
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

import java.awt.*;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Enumeration;
import java.util.Vector;

import com.lowagie.text.*;
import com.lowagie.text.pdf.*;

public class Chap13_gis extends Vector {
    private int province;
    
    public Chap13_gis(int i) {
        province = i;
        DataInputStream datainputstream = null;
        try {
            URL url = new URL("http://www.lowagie.com/kaart/prov" + i);
            datainputstream = new DataInputStream(url.openStream());
            do
                addElement(new Point(datainputstream.readInt(), datainputstream.readInt()));
            while(true);
        }
        catch(MalformedURLException mue) {
            System.err.println("Error (mue): " + mue.getMessage());
        }
        catch(IOException ioe) {
            try {
                datainputstream.close();
            }
            catch(IOException e) { }
        }
        catch(Exception e) {
            System.err.println(e.getMessage());
        }
    }
    
    final void draw(Graphics g, int offsetX, int offsetY, int scale) {
        Polygon polygon;
        for(Enumeration enumeration = getPolygons(offsetX, offsetY, scale); enumeration.hasMoreElements(); g.drawPolygon(polygon)) {
            polygon = (Polygon)enumeration.nextElement();
            switch(province) {
                case 0:
                    g.setColor(Color.pink);
                    break;
                case 1:
                    g.setColor(Color.yellow);
                    break;
                case 2:
                    g.setColor(Color.green);
                    break;
                case 3:
                    g.setColor(Color.cyan);
                    break;
                case 4:
                    g.setColor(Color.orange);
                    break;
            }
            g.fillPolygon(polygon);
            g.setColor(Color.darkGray);
        }
    }
    
    private final Enumeration getPolygons(int offsetX, int offsetY, int scale) {
        Vector vector = new Vector(5);
        Polygon polygon;
        for(Enumeration enumeration = elements(); enumeration.hasMoreElements(); vector.addElement(polygon)) {
            Point point = (Point)enumeration.nextElement();
            polygon = new Polygon();
            for(; point.x > 0 && point.y > 0 && enumeration.hasMoreElements(); point = (Point)enumeration.nextElement())
                polygon.addPoint(x(point.x, offsetX, scale), y(point.y, offsetY, scale));
        }
        return vector.elements();
    }
    
    private static final void drawCities(Graphics g, int offsetX, int offsetY, int scale) {
        g.setColor(Color.black);
        DataInputStream datainputstream = null;
        try {
            URL url = new URL("http://www.lowagie.com/kaart/steden");
            datainputstream = new DataInputStream(url.openStream());
            do {
                int zip = datainputstream.readInt();
                int x = datainputstream.readInt();
                int y = datainputstream.readInt();
                if((scale < 7) || (scale < 8 && ((zip % 10) == 0)) || (scale < 10 && ((zip % 100) == 0)) || ((zip % 1000) == 0))
                    g.drawOval(x(x, offsetX, scale) - 2, y(y, offsetY, scale) - 2, 4, 4);
            }
            while(true);
        }
        catch(MalformedURLException mue) {
            System.err.println("Error (mue): " + mue.getMessage());
        }
        catch(IOException ioe) {
            try {
                datainputstream.close();
            }
            catch(IOException e) { }
        }
        catch(Exception e) {
            System.err.println(e.getMessage());
        }
    }

    private final static int x(int x, int offsetX, int scale) {
        return x + offsetX >> scale;
    }

    private final static int y(int y, int offsetY, int scale) {
        return y + offsetY >> scale;
    }
    
    
    /**
     * @param args the command line arguments
     */
    public static void main (String args[]) {
        
        System.out.println("Chapter 13: a clickable map of Flanders");
        
        // step 1: creation of a document-object
        Document document = new Document(PageSize.A4, 50, 50, 50, 50);
        try {
            // step 2: we create a writer that listens to the document
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("Chap13_gis.pdf"));
            PdfAcroForm acroForm = writer.getAcroForm();
            // step 3: we open the document
            document.open();
            document.add(new Phrase("This is the map of Flanders"));
            // we create a fontMapper and read all the fonts in the font directory
            DefaultFontMapper mapper = new DefaultFontMapper();
            mapper.insertDirectory("c:\\winnt\\fonts");
            // step 4: we add some content
            int w = 520;
            int h = 300;
            int scale = 9;
            int offsetX = 0;
            int offsetY = 0;
            PdfContentByte cb = new PdfContentByte(null);
            Graphics2D g2 = cb.createGraphics(w, h, mapper);
            for(int i = 0; i < 5; i++) {
                Chap13_gis p = new Chap13_gis(i);
                p.draw(g2, offsetX, offsetY, scale);
            }
            drawCities(g2, offsetX, offsetY, scale);
            g2.dispose();
            acroForm.addHiddenField("scale", String.valueOf(scale));
            acroForm.addHiddenField("offsetX", String.valueOf(offsetX));
            acroForm.addHiddenField("offsetY", String.valueOf(offsetY));
            acroForm.addHiddenField("width", String.valueOf(w));
            acroForm.addHiddenField("height", String.valueOf(h));
            acroForm.addMap("map", null, "city.php", cb, 40, 780 - h, 40 + w, 780);
        }
        catch (Exception de) {
            de.printStackTrace();
        }
        
        // step 5: close the document
        document.close();
    }
    
}