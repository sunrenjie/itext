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

import java.io.*;

import com.lowagie.text.*;
import com.lowagie.text.pdf.*;

public class Chap0110 {
    
    public static void main(String[] args)
    {        
        System.out.println("Chapter 1 example 10: encryption 128 bits");
        
        Document document = new Document(PageSize.A4, 50, 50, 50, 50);
        try
        {
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("Chap0110.pdf"));
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
