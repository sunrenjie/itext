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
 * itext@lowagie.com
 */

import java.awt.Color;
import java.io.*;

import com.lowagie.text.*;
import com.lowagie.text.pdf.*;
import java.awt.image.RenderedImage;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.util.Hashtable;
import javax.media.jai.NullOpImage;
import javax.media.jai.OpImage;
import com.sun.media.jai.codec.*;
import com.sun.media.jai.codecimpl.*;

public class Chap0612 {
    
    
    public static void main(String[] args) {
        // creation of the document with a certain size and certain margins
        Document document = new Document(PageSize.A4, 50, 50, 50, 50);
        //Document.compress = false;
        try {
            // creation of the different writers
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("Chap0612.pdf"));

            File file = new File("12.tif");
            SeekableStream s = new FileSeekableStream(file);
            TIFFDirectory dir = new TIFFDirectory(s, 0);
            long IFDOffset = dir.getIFDOffset();
            document.open();
            PdfContentByte cb = writer.getDirectContent();
            while (IFDOffset != 0L) {
                dir = new TIFFDirectory(s, IFDOffset, 0);
                IFDOffset = dir.getNextIFDOffset();
                long h = dir.getFieldAsLong(TIFFImageDecoder.TIFF_IMAGE_LENGTH);
                long w = dir.getFieldAsLong(TIFFImageDecoder.TIFF_IMAGE_WIDTH);
                long offset = dir.getFieldAsLong(TIFFImageDecoder.TIFF_STRIP_OFFSETS);
                long size = dir.getFieldAsLong(TIFFImageDecoder.TIFF_STRIP_BYTE_COUNTS);
                boolean reverse = false;
                if (dir.isTagPresent(TIFFImageDecoder.TIFF_FILL_ORDER))
                    reverse = (dir.getFieldAsLong(TIFFImageDecoder.TIFF_FILL_ORDER) == 2L);
                int compression = (int)dir.getFieldAsLong(TIFFImageDecoder.TIFF_COMPRESSION);
                switch (compression) {
                    case TIFFImage.COMP_FAX_G3_1D:
                        compression = Image.CCITTG3_1D;
                        break;
                    case TIFFImage.COMP_FAX_G3_2D:
                        compression = Image.CCITTG3_1D;
                        if (dir.isTagPresent(TIFFImageDecoder.TIFF_T4_OPTIONS)) {
                            if (((int)dir.getFieldAsLong(TIFFImageDecoder.TIFF_T4_OPTIONS) & 1) != 0)
                                compression = Image.CCITTG3_2D;
                        }
                        break;
                    case TIFFImage.COMP_FAX_G4_2D:
                        compression = Image.CCITTG4;
                        break;
                    default:
                        throw new Exception("Compression type " + compression + " not supported");
                }
                byte im[] = new byte[(int)size];
                s.seek(offset);
                s.readFully(im);
                Image img = Image.getInstance((int)w, (int)h, reverse, compression, 0, im);
                img.scalePercent(72f / 200f * 100);
                img.setAbsolutePosition(0, 0);
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