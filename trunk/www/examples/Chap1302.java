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

import java.io.*;

import com.lowagie.text.*;
import com.lowagie.text.pdf.*;

public class Chap1302 {
    
    public static void main(String[] args)
    {
        Document document = new Document(PageSize.A4, 50, 50, 50, 50);
        try
        {
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("Chap1302.pdf"));
            writer.setEncryption(PdfWriter.STRENGTH128BITS, "userpass", "ownerpass", PdfWriter.AllowCopy | PdfWriter.AllowPrinting);
            document.open();
            document.add(new Paragraph("This document is Top Secret!"));
            document.close();
        }
        catch (Exception de)
        {
            de.printStackTrace();
        }
    }
}
