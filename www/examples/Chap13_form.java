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
 * itext@lowagie.com
 */
 
import java.io.*;
import java.awt.Color;

import com.lowagie.text.*;
import com.lowagie.text.pdf.*;

public class Chap13_form extends java.lang.Object {

    /**
    * @param args the command line arguments
    */
    public static void main (String args[]) {
        
        System.out.println("Chapter 13: a form with some text");

        // step 1: creation of a document-object
        Document document = new Document(PageSize.A4, 50, 50, 50, 50);

        Document.compress = false;
        try {
            // step 2: we create a writer that listens to the document
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("Chap13_form.pdf"));
            PdfAcroForm acroForm = writer.getAcroForm();
            // step 3: we open the document
            document.open();
            // step 4: we add some content
            BaseFont helv = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.WINANSI, BaseFont.NOT_EMBEDDED);
            float fontSize = 12;
            acroForm.addSingleLineTextField("Hello iText!", helv, fontSize, "msg", 171, 730, 350, 750);
            acroForm.addHtmlPostButton("SUBMIT", helv, fontSize, "btn", "Chap13.php", 171, 700, 250, 725);
        }
        catch (Exception de) {
            de.printStackTrace();
        }

        // step 5: close the document
        document.close();
    }

}