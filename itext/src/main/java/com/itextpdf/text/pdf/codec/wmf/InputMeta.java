/*
 * $Id$
 *
 * This file is part of the iText (R) project.
 * Copyright (c) 1998-2009 1T3XT BVBA
 * Authors: Bruno Lowagie, Paulo Soares, et al.
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License version 3
 * as published by the Free Software Foundation with the addition of the
 * following permission added to Section 15 as permitted in Section 7(a):
 * FOR ANY PART OF THE COVERED WORK IN WHICH THE COPYRIGHT IS OWNED BY 1T3XT,
 * 1T3XT DISCLAIMS THE WARRANTY OF NON INFRINGEMENT OF THIRD PARTY RIGHTS.
 *
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Affero General Public License for more details.
 * You should have received a copy of the GNU Affero General Public License
 * along with this program; if not, see http://www.gnu.org/licenses or write to
 * the Free Software Foundation, Inc., 51 Franklin Street, Fifth Floor,
 * Boston, MA, 02110-1301 USA, or download the license from the following URL:
 * http://itextpdf.com/terms-of-use/
 *
 * The interactive user interfaces in modified source and object code versions
 * of this program must display Appropriate Legal Notices, as required under
 * Section 5 of the GNU Affero General Public License.
 *
 * In accordance with Section 7(b) of the GNU Affero General Public License,
 * you must retain the producer line in every PDF that is created or manipulated
 * using iText.
 *
 * You can be released from the requirements of the license by purchasing
 * a commercial license. Buying such a license is mandatory as soon as you
 * develop commercial activities involving the iText software without
 * disclosing the source code of your own applications.
 * These activities include: offering paid services to customers as an ASP,
 * serving PDFs on the fly in a web application, shipping iText with a closed
 * source product.
 *
 * For more information, please contact iText Software Corp. at this
 * address: sales@itextpdf.com
 */
package com.itextpdf.text.pdf.codec.wmf;

import com.itextpdf.text.BaseColor;
import java.io.IOException;
import java.io.InputStream;

import com.itextpdf.text.Utilities;

public class InputMeta {
    
    InputStream in;
    int length;
    
    public InputMeta(InputStream in) {
        this.in = in;
    }

    public int readWord() throws IOException{
        length += 2;
        int k1 = in.read();
        if (k1 < 0)
            return 0;
        return (k1 + (in.read() << 8)) & 0xffff;
    }

    public int readShort() throws IOException{
        int k = readWord();
        if (k > 0x7fff)
            k -= 0x10000;
        return k;
    }

    public int readInt() throws IOException{
        length += 4;
        int k1 = in.read();
        if (k1 < 0)
            return 0;
        int k2 = in.read() << 8;
        int k3 = in.read() << 16;
        return k1 + k2 + k3 + (in.read() << 24);
    }
    
    public int readByte() throws IOException{
        ++length;
        return in.read() & 0xff;
    }
    
    public void skip(int len) throws IOException{
        length += len;
        Utilities.skip(in, len);
    }
    
    public int getLength() {
        return length;
    }
    
    public BaseColor readColor() throws IOException{
        int red = readByte();
        int green = readByte();
        int blue = readByte();
        readByte();
        return new BaseColor(red, green, blue);
    }
}
