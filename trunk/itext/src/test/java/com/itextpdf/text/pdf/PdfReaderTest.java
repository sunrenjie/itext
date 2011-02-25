/*
 * $Id:  $
 *
 * This file is part of the iText (R) project.
 * Copyright (c) 1998-2011 1T3XT BVBA
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

package com.itextpdf.text.pdf;

import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

import com.itextpdf.testutils.TestResourceUtils;
import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;

public class PdfReaderTest {

    @Test
    public void testPartialReadOpenFile() throws Exception {
        /* commit 3265 incorrectly closed the input stream, make sure
         * the constructor contract is kept, i.e. file is still open
         */
        File testFile = TestResourceUtils.getResourceAsTempFile(this, "RomeoJuliet.pdf");
        RandomAccessFileOrArray f = new RandomAccessFileOrArray(testFile.getAbsolutePath());
        PdfReader r = new PdfReader(f, null);

        assertTrue("kept open", f.isOpen());
    }

    @Ignore("validity of test needs to be resolved")
    @Test
    public void testGetLink() throws Exception {
        File testFile = TestResourceUtils.getResourceAsTempFile(this, "getLinkTest1.pdf");
        PdfReader currentReader = new PdfReader(testFile.getAbsolutePath());
        Document document = new Document(PageSize.A4, 0, 0, 0, 0);
        PdfWriter writer = PdfWriter.getInstance(document, new
                ByteArrayOutputStream());
        document.open();
        document.newPage();
        List<PdfAnnotation.PdfImportedLink> links = currentReader.getLinks(1);
        PdfAnnotation.PdfImportedLink link = links.get(0);
        writer.addAnnotation(link.createAnnotation(writer));
        document.close();
    }

    @Test
    public void testGetLink2() throws Exception {
        File testFile = TestResourceUtils.getResourceAsTempFile(this, "getLinkTest2.pdf");
        String filename = testFile.getAbsolutePath();
        PdfReader rdr = new PdfReader(new RandomAccessFileOrArray(filename), new byte[0]);
        // this one works: PdfReader rdr = new PdfReader(filename);
        rdr.consolidateNamedDestinations(); // does not help
        rdr.getLinks(1);
    }

}
