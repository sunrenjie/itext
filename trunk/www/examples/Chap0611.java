/*
 * $Id$
 * $Name$
 *
 * This code is free software. It may only be copied or modified
 * if you include the following copyright notice:
 *
 * --> Copyright 2001 by Paulo Soares <--
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

import com.lowagie.text.*;
import com.lowagie.text.pdf.*;
import java.awt.image.RenderedImage;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.util.Hashtable;
import com.sun.media.jai.codec.SeekableStream;
import com.sun.media.jai.codec.FileSeekableStream;
import com.sun.media.jai.codec.TIFFDecodeParam;
import com.sun.media.jai.codec.ImageDecoder;
import com.sun.media.jai.codec.ImageCodec;

public class Chap0611 {
        
    public static void main(String[] args) {
        // creation of the document with a certain size and certain margins
        Document document = new Document(PageSize.A4, 50, 50, 50, 50);
        //Document.compress = false;
        try {
            // creation of the different writers
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("Chap0611.pdf"));
            
            File file = new File("338814-00.tif");
            SeekableStream s = new FileSeekableStream(file);
            
            TIFFDecodeParam param = null;
            
            ImageDecoder dec = ImageCodec.createImageDecoder("tiff", s, param);
            
            System.out.println("Number of images in this TIFF: " + dec.getNumPages());
            
            // Which of the multiple images in the TIFF file do we want to load
            // 0 refers to the first, 1 to the second and so on.
            int total = dec.getNumPages();
            document.open();
            PdfContentByte cb = writer.getDirectContent();
            for (int k = 0; k < total; ++k) {
                RenderedImage ri = dec.decodeAsRenderedImage(k);
                Raster ra = ri.getData();
                BufferedImage bi = new BufferedImage(ri.getColorModel(),
                Raster.createWritableRaster(ri.getSampleModel(), ra.getDataBuffer(), null), false, new Hashtable());
                Image img = Image.getInstance(bi, null, true);
                img.scalePercent(72f / 200f * 100);
                img.setAbsolutePosition(0, 0);
                System.out.println("Image: " + k);
                cb.addImage(img);
                document.newPage();
            }
            document.close();
        }
        catch (Exception de) {
            de.printStackTrace();
            //System.err.println(de.getMessage());
        }
    }
}
