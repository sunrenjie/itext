/*
 * Copyright 2002 Paulo Soares
 *
 * The contents of this file are subject to the Mozilla Public License Version 1.1
 * (the "License"); you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at http://www.mozilla.org/MPL/
 *
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 * for the specific language governing rights and limitations under the License.
 *
 * The Original Code is 'iText, a free JAVA-PDF library'.
 *
 * The Initial Developer of the Original Code is Bruno Lowagie. Portions created by
 * the Initial Developer are Copyright (C) 1999, 2000, 2001, 2002 by Bruno Lowagie.
 * All Rights Reserved.
 * Co-Developer of the code is Paulo Soares. Portions created by the Co-Developer
 * are Copyright (C) 2000, 2001, 2002 by Paulo Soares. All Rights Reserved.
 *
 * Contributor(s): all the names of the contributors are added in the source code
 * where applicable.
 *
 * Alternatively, the contents of this file may be used under the terms of the
 * LGPL license (the "GNU LIBRARY GENERAL PUBLIC LICENSE"), in which case the
 * provisions of LGPL are applicable instead of those above.  If you wish to
 * allow use of your version of this file only under the terms of the LGPL
 * License and not to allow others to use your version of this file under
 * the MPL, indicate your decision by deleting the provisions above and
 * replace them with the notice and other provisions required by the LGPL.
 * If you do not delete the provisions above, a recipient may use your version
 * of this file under either the MPL or the GNU LIBRARY GENERAL PUBLIC LICENSE.
 *
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the MPL as stated above or under the terms of the GNU
 * Library General Public License as published by the Free Software Foundation;
 * either version 2 of the License, or any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Library general Public License for more
 * details.
 *
 * If you didn't download this code from the following link, you should check if
 * you aren't using an obsolete version:
 * http://www.lowagie.com/iText/
 */

package com.lowagie.text.pdf;
import com.lowagie.text.ExceptionConverter;
import java.io.UnsupportedEncodingException;
/** Supports fast encodings for winansi and PDFDocEncoding.
 *
 * @author Paulo Soares (psoares@consiste.pt)
 */
public class PdfEncodings {
    
    static final char winansiByteToChar[] = {
        0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 
        16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 
        32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 
        48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63, 
        64, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 
        80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 91, 92, 93, 94, 95, 
        96, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 
        112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 123, 124, 125, 126, 127, 
        8364, 65533, 8218, 402, 8222, 8230, 8224, 8225, 710, 8240, 352, 8249, 338, 65533, 381, 65533, 
        65533, 8216, 8217, 8220, 8221, 8226, 8211, 8212, 732, 8482, 353, 8250, 339, 65533, 382, 376, 
        160, 161, 162, 163, 164, 165, 166, 167, 168, 169, 170, 171, 172, 173, 174, 175, 
        176, 177, 178, 179, 180, 181, 182, 183, 184, 185, 186, 187, 188, 189, 190, 191, 
        192, 193, 194, 195, 196, 197, 198, 199, 200, 201, 202, 203, 204, 205, 206, 207, 
        208, 209, 210, 211, 212, 213, 214, 215, 216, 217, 218, 219, 220, 221, 222, 223, 
        224, 225, 226, 227, 228, 229, 230, 231, 232, 233, 234, 235, 236, 237, 238, 239, 
        240, 241, 242, 243, 244, 245, 246, 247, 248, 249, 250, 251, 252, 253, 254, 255};
        
    static final char pdfEncodingByteToChar[] = {
        0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 
        16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 
        32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 
        48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63, 
        64, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 
        80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 91, 92, 93, 94, 95, 
        96, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 
        112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 123, 124, 125, 126, 127, 
        0x2022, 0x2020, 0x2021, 0x2026, 0x2014, 0x2013, 0x0192, 0x2044, 65533, 65533, 0x2212, 65533, 65533, 65533, 65533, 65533,
        0x2019, 0x201a, 0x2122, 0xfb01, 0xfb02, 0x0141, 0x0152, 0x0160, 0x0178, 0x017d, 0x0131, 0x0142, 0x0153, 0x0161, 0x017e, 65533,
        0x20ac, 161, 162, 163, 164, 165, 166, 167, 168, 169, 170, 171, 172, 173, 174, 175, 
        176, 177, 178, 179, 180, 181, 182, 183, 184, 185, 186, 187, 188, 189, 190, 191, 
        192, 193, 194, 195, 196, 197, 198, 199, 200, 201, 202, 203, 204, 205, 206, 207, 
        208, 209, 210, 211, 212, 213, 214, 215, 216, 217, 218, 219, 220, 221, 222, 223, 
        224, 225, 226, 227, 228, 229, 230, 231, 232, 233, 234, 235, 236, 237, 238, 239, 
        240, 241, 242, 243, 244, 245, 246, 247, 248, 249, 250, 251, 252, 253, 254, 255};
        
    static final IntHashtable winansi = new IntHashtable();
    
    static final IntHashtable pdfEncoding = new IntHashtable();
    
    static {        
        winansi.put(8364, 128);
        winansi.put(8218, 130);
        winansi.put(402, 131);
        winansi.put(8222, 132);
        winansi.put(8230, 133);
        winansi.put(8224, 134);
        winansi.put(8225, 135);
        winansi.put(710, 136);
        winansi.put(8240, 137);
        winansi.put(352, 138);
        winansi.put(8249, 139);
        winansi.put(338, 140);
        winansi.put(381, 142);
        winansi.put(8216, 145);
        winansi.put(8217, 146);
        winansi.put(8220, 147);
        winansi.put(8221, 148);
        winansi.put(8226, 149);
        winansi.put(8211, 150);
        winansi.put(8212, 151);
        winansi.put(732, 152);
        winansi.put(8482, 153);
        winansi.put(353, 154);
        winansi.put(8250, 155);
        winansi.put(339, 156);
        winansi.put(382, 158);
        winansi.put(376, 159);
        
        pdfEncoding.put(0x2022, 128);
        pdfEncoding.put(0x2020, 129);
        pdfEncoding.put(0x2021, 130);
        pdfEncoding.put(0x2026, 131);
        pdfEncoding.put(0x2014, 132);
        pdfEncoding.put(0x2013, 133);
        pdfEncoding.put(0x0192, 134);
        pdfEncoding.put(0x2044, 135);
        pdfEncoding.put(0x2212, 138);
        pdfEncoding.put(0x2019, 144);
        pdfEncoding.put(0x201a, 145);
        pdfEncoding.put(0x2122, 146);
        pdfEncoding.put(0xfb01, 147);
        pdfEncoding.put(0xfb02, 148);
        pdfEncoding.put(0x0141, 149);
        pdfEncoding.put(0x0152, 150);
        pdfEncoding.put(0x0160, 151);
        pdfEncoding.put(0x0178, 152);
        pdfEncoding.put(0x017d, 153);
        pdfEncoding.put(0x0131, 154);
        pdfEncoding.put(0x0142, 155);
        pdfEncoding.put(0x0153, 156);
        pdfEncoding.put(0x0161, 157);
        pdfEncoding.put(0x017e, 158);
        pdfEncoding.put(0x20ac, 160);
    }

    /**
     * Converts a <CODE>String</CODE> to a </CODE>byte</CODE> array according
     * to the font's encoding.
     * @param text the <CODE>String</CODE> to be converted
     * @return an array of <CODE>byte</CODE> representing the conversion according to the font's encoding
     */
    public static final byte[] convertToBytes(String text, String encoding) {
        if (encoding == null || encoding.length() == 0) {
            int len = text.length();
            byte b[] = new byte[len];
            for (int k = 0; k < len; ++k)
                b[k] = (byte)text.charAt(k);
            return b;
        }
        IntHashtable hash = null;
        if (encoding.equals(BaseFont.WINANSI))
            hash = winansi;
        else if (encoding.equals(PdfObject.ENCODING))
            hash = pdfEncoding;
        if (hash != null) {
            int len = text.length();
            byte b[] = new byte[len];
            int c = 0;
            for (int k = 0; k < len; ++k) {
                char char1 = text.charAt(k);
                if (char1 < 128 || (char1 >= 160 && char1 <= 255))
                    c = char1;
                else
                    c = hash.get(char1);
                b[k] = (byte)c;
            }
            return b;
        }
        try {
            return text.getBytes(encoding);
        }
        catch (UnsupportedEncodingException e) {
            throw new ExceptionConverter(e);
        }
    }
    
    public static final String convertToString(byte bytes[], String encoding) {
        if (encoding == null || encoding.length() == 0) {
            char c[] = new char[bytes.length];
            for (int k = 0; k < bytes.length; ++k)
                c[k] = (char)(bytes[k] & 0xff);
            return new String(c);
        }
        char ch[] = null;
        if (encoding.equals(BaseFont.WINANSI))
            ch = winansiByteToChar;
        else if (encoding.equals(PdfObject.ENCODING))
            ch = pdfEncodingByteToChar;
        if (ch != null) {
            int len = bytes.length;
            char c[] = new char[len];
            for (int k = 0; k < len; ++k) {
                c[k] = ch[bytes[k] & 0xff];
            }
            return new String(c);
        }
        try {
            return new String(bytes, encoding);
        }
        catch (UnsupportedEncodingException e) {
            throw new ExceptionConverter(e);
        }
    }
}
