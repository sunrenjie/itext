/*
 * TrueTypeFontUnicode.java
 *
 * Created on November 19, 2001, 5:05 PM
 */

package com.lowagie.text.pdf;

import java.io.*;
import java.util.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Arrays;
import java.util.Comparator;
import com.lowagie.text.DocumentException;
/** Represents a True Type font with Unicode encoding. All the character
 * in the font can be used directly by using the encoding Identity-H or
 * Identity-V. This is the only way to represent some character sets such
 * as Thai.
 * @author  Paulo Soares (psoares@consiste.pt)
 */
class TrueTypeFontUnicode extends TrueTypeFont implements Comparator{
    
    /** <CODE>true</CODE> if the encoding is vertical.
     */    
    boolean vertical = false;

    /** Creates a new TrueType font addressed by Unicode characters. The font
     * will always be embedded. 
     * @param ttFile the location of the font on file. The file must end in '.ttf'.
     * The modifiers after the name are ignored.
     * @param enc the encoding to be applied to this font
     * @param emb true if the font is to be embedded in the PDF
     * @throws DocumentException the font is invalid
     * @throws IOException the font file could not be read
     */
    TrueTypeFontUnicode(String ttFile, String enc, boolean emb) throws DocumentException, IOException {
        String nameBase = getBaseName(ttFile);
        if (nameBase.length() < ttFile.length()) {
            ttFile = nameBase;
        }
        encoding = enc;
        embedded = emb;
        fileName = ttFile;
        fontType = FONT_TYPE_TTUNI;
        if (fileName.toLowerCase().endsWith(".ttf") && ((enc.equals(IDENTITY_H) || enc.equals(IDENTITY_V)) && emb)) {
            process();
            if ((cmap31 == null && !fontSpecific) || (cmap10 == null && fontSpecific))
                throw new DocumentException(fileName + " " + style + " does not contain an usable cmap.");
            if (fontSpecific) {
                fontSpecific = false;
                String tempEncoding = encoding;
                encoding = PdfObject.ENCODING;
                try {
                    createEncoding();
                }
                catch (UnsupportedEncodingException e) {
                    throw new DocumentException(e.getMessage());
                }
                encoding = tempEncoding;
                fontSpecific = true;
            }
        }
        else
            throw new DocumentException(fileName + " " + style + " is not a TTF font file.");
        vertical = enc.endsWith("V");
    }
    
/**
 * Gets the width of a <CODE>String</CODE> in normalized 1000 units.
 * @param text the <CODE>String</CODE> to get the witdth of
 * @return the width in normalized 1000 units
 */
    public int getWidth(String text)
    {
        if (vertical)
            return text.length() * 1000;
        int total = 0;
        if (fontSpecific) {
            byte b[] = null;
            try {
                b = text.getBytes(PdfObject.ENCODING);
            }
            catch (Exception e) {
                b = text.getBytes();
            }
            int len = b.length;
            for (int k = 0; k < len; ++k)
                total += getRawWidth(b[k] & 0xff, null);
        }
        else {
            int len = text.length();
            for (int k = 0; k < len; ++k)
                total += getRawWidth(text.charAt(k), encoding);
        }
        return total;
    }

    /** Creates a ToUnicode CMap to allow copy and paste from Acrobat.
     * @param metrics metrics[0] contains the glyph index and metrics[2]
     * contains the Unicode code
     * @throws DocumentException on error
     * @return the stream representing this CMap or <CODE>null</CODE>
     */    
    private PdfStream getToUnicode(Object metrics[]) throws DocumentException {
        if (metrics.length == 0)
            return null;
        StringBuffer buf = new StringBuffer(
        "/CIDInit /ProcSet findresource begin\n" +
        "12 dict begin\n" +
        "begincmap\n" +
        "/CIDSystemInfo\n" +
        "<< /Registry (Adobe)\n" +
        "/Ordering (UCS)\n" +
        "/Supplement 0\n" +
        ">> def\n" +
        "/CMapName /Adobe-Identity-UCS def\n" +
        "/CMapType 2 def\n" +
        "1 begincodespacerange\n" +
        toHex(((int[])metrics[0])[0]) + toHex(((int[])metrics[metrics.length - 1])[0]) + "\n" +
        "endcodespacerange\n");
        int size = 0;
        for (int k = 0; k < metrics.length; ++k) {
            if (size == 0) {
                if (k != 0) {
                    buf.append("endbfrange\n");
                }
                size = Math.min(100, metrics.length - k);
                buf.append(size).append(" beginbfrange\n");
            }
            --size;
            int metric[] = (int[])metrics[k];
            String fromTo = toHex(metric[0]);
            buf.append(fromTo).append(fromTo).append(toHex(metric[2])).append("\n");
        }
        buf.append(
        "endbfrange\n" +
        "endcmap\n" +
        "CMapName currentdict /CMap defineresource pop\n" +
        "end end\n");
        String s = buf.toString();
        byte b[] = null;
        try {
            b = s.getBytes(PdfObject.ENCODING);
        }
        catch (Exception e) {
            b = s.getBytes();
        }
        PdfStream stream = new PdfStream(b);
        stream.flateCompress();
        return stream;
    }
    
    /** Gets an hex string in the format "&lt;HHHH&gt;".
     * @param n the number
     * @return the hex string
     */    
    static String toHex(int n) {
        String s = Integer.toHexString(n);
        return "<0000".substring(0, 5 - s.length()) + s + ">";
    }
    
    /** Generates the CIDFontTyte2 dictionary.
     * @param fontDescriptor the indirect reference to the font descriptor
     * @param subsetPrefix the subset prefix
     * @param metrics the horizontal width metrics
     * @throws DocumentException on error
     * @return a stream
     */    
    private PdfDictionary getCIDFontType2(PdfIndirectReference fontDescriptor, String subsetPrefix, Object metrics[]) throws DocumentException {
        PdfDictionary dic = new PdfDictionary(PdfName.FONT);
        dic.put(PdfName.SUBTYPE, new PdfName("CIDFontType2"));
        dic.put(PdfName.BASEFONT, new PdfName(subsetPrefix + fontName));
        dic.put(PdfName.FONTDESCRIPTOR, fontDescriptor);
        dic.put(new PdfName("CIDToGIDMap"),new PdfName("Identity"));
        PdfDictionary cdic = new PdfDictionary();
        cdic.put(PdfName.REGISTRY, new PdfString("Adobe"));
        cdic.put(PdfName.ORDERING, new PdfString("Identity"));
        cdic.put(PdfName.SUPPLEMENT, new PdfNumber(0));
        dic.put(new PdfName("CIDSystemInfo"), cdic);
        if (!vertical) {
            dic.put(new PdfName("DW"), new PdfNumber(1000));
            StringBuffer buf = new StringBuffer("[");
            int lastNumber = -10;
            boolean firstTime = true;
            for (int k = 0; k < metrics.length; ++k) {
                int metric[] = (int[])metrics[k];
                if (metric[1] == 1000)
                    continue;
                int m = metric[0];
                if (m == lastNumber + 1) {
                    buf.append(" ").append(metric[1]);
                }
                else {
                    if (!firstTime) {
                        buf.append("]");
                    }
                    firstTime = false;
                    buf.append(m).append("[").append(metric[1]);
                }
                lastNumber = m;
            }
            if (buf.length() > 1) {
                buf.append("]]");
                dic.put(PdfName.W, new PdfLiteral(buf.toString()));
            }
        }
        return dic;
    }
    
    /** Generates the font dictionary.
     * @param descendant the descendant dictionary
     * @param subsetPrefix the subset prefix
     * @param toUnicode the ToUnicode stream
     * @throws DocumentException on error
     * @return the stream
     */    
    private PdfDictionary getFontBaseType(PdfIndirectReference descendant, String subsetPrefix, PdfIndirectReference toUnicode) throws DocumentException {
        PdfDictionary dic = new PdfDictionary(PdfName.FONT);
        dic.put(PdfName.SUBTYPE, new PdfName("Type0"));
        dic.put(PdfName.BASEFONT, new PdfName(subsetPrefix + fontName));
        dic.put(PdfName.ENCODING, new PdfName(encoding));
        dic.put(new PdfName("DescendantFonts"), new PdfArray(descendant));
        if (toUnicode != null)
            dic.put(new PdfName("ToUnicode"), toUnicode);
        return dic;
    }

    /** The method used to sort the metrics array.
     * @param o1 the first element
     * @param o2 the second element
     * @return the comparisation
     */    
    public int compare(Object o1, Object o2) {
        int m1 = ((int[])o1)[0];
        int m2 = ((int[])o2)[0];
        if (m1 < m2)
            return -1;
        if (m1 == m2)
            return 0;
        return 1;
    }

    /** Outputs to the writer the font dictionaries and streams.
     * @param writer the writer for this document
     * @param ref the font indirect reference
     * @param params several parameters that depend on the font type
     * @throws IOException on error
     * @throws DocumentException error in generating the object
     */
    void writeFont(PdfWriter writer, PdfIndirectReference ref, Object params[]) throws DocumentException, IOException {
        HashMap longTag = (HashMap)params[0];
        Object metrics[] = longTag.values().toArray();
        Arrays.sort(metrics, this);
        PdfIndirectReference ind_font = null;
        PdfObject pobj = null;
        PdfIndirectObject obj = null;
        TrueTypeFontSubSet sb = new TrueTypeFontSubSet(fileName, longTag, false);
        byte b[] = sb.process();
        int lengths[] = new int[]{b.length};
        pobj = new StreamFont(b, lengths);
        obj = writer.addToBody(pobj);
        ind_font = obj.getIndirectReference();
        String subsetPrefix = createSubsetPrefix();
        PdfDictionary dic = getFontDescriptor(ind_font, subsetPrefix);
        obj = writer.addToBody(dic);
        ind_font = obj.getIndirectReference();

        pobj = getCIDFontType2(ind_font, subsetPrefix, metrics);
        obj = writer.addToBody(pobj);
        ind_font = obj.getIndirectReference();

        pobj = getToUnicode(metrics);
        PdfIndirectReference toUnicodeRef = null;
        if (pobj != null) {
            obj = writer.addToBody(pobj);
            toUnicodeRef = obj.getIndirectReference();
        }

        pobj = getFontBaseType(ind_font, subsetPrefix, toUnicodeRef);
        writer.addToBody(pobj, ref);
    }

    /** A forbidden operation. Will throw a null pointer exception.
     * @param text the text
     * @return always <CODE>null</CODE>
     */    
    byte[] convertToBytes(String text)
    {
        return null;
    }
}
