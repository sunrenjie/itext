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
        try {
            // step 2: we create a writer that listens to the document
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("Chap13_form.pdf"));
            PdfAcroForm acroForm = writer.getAcroForm();
            // step 3: we open the document
            document.open();
            // step 4: we add some content
            BaseFont helv = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.WINANSI, BaseFont.NOT_EMBEDDED);
            float fontSize = 12;
            acroForm.addSingleLineTextField("name", "your name", helv, fontSize, 171, 800, 350, 820);
            acroForm.addMultiLineTextField("msg", "Hello iText!\nThis is a Test\nThere are multiple lines in this textfield", helv, fontSize, 171, 730, 350, 790);
            acroForm.addSingleLinePasswordField("password", "", helv, fontSize, 171, 700, 350, 720);
            acroForm.addHtmlPostButton("btn", "SUBMIT", "noValue", "Chap13.php", helv, fontSize, 355, 700, 420, 725);
            acroForm.addResetButton("reset", "RESET", null, helv, fontSize, 430, 700, 495, 725);
            acroForm.addCheckBox("CB1", "Food", true, 180, 685, 190, 695);
            acroForm.addCheckBox("CB2", "Drinks", false, 180, 645, 210, 675);
            PdfFormField radiogroup = acroForm.getRadioGroup("CreditCard", "Visa", true);
            acroForm.addRadioButton(radiogroup, "Visa", 180, 625, 195, 640);
            acroForm.addRadioButton(radiogroup, "MasterCard", 200, 625, 215, 640);
            acroForm.addRadioButton(radiogroup, "American", 220, 625, 235, 640);
            acroForm.addRadioGroup(radiogroup);
            String[] colors = {"Red", "Green", "Blue"};
            String[][] colorvalues = {{"#FF0000", "Red"}, {"#00FF00", "Green"}, {"#0000FF", "Blue"}};
            acroForm.addSelectList("list1", colors, "Green", helv, fontSize, 171, 550, 300, 600);
            acroForm.addSelectList("list2", colorvalues, "#0000FF", helv, fontSize, 315, 550, 450, 600);
            acroForm.addComboBox("combo1", colors, "Green", true, helv, fontSize, 171, 440, 300, 490);
            acroForm.addComboBox("combo2", colorvalues, "#0000FF", false, helv, fontSize, 315, 440, 450, 490);
            PdfContentByte cb = new PdfContentByte(null);
            cb.setRGBColorStroke(0x00, 0x00, 0x00);
            cb.setRGBColorFill(0xFF, 0x00, 0x00);
            cb.arc(0, 0, 100, 100, 0, 360);
            cb.fillStroke();
            cb.setRGBColorStroke(0x00, 0x00, 0x00);
            cb.setRGBColorFill(0xFF, 0xFF, 0xFF);
            cb.arc(20, 20, 80, 80, 0, 360);
            cb.fillStroke();
            cb.setRGBColorStroke(0x00, 0x00, 0x00);
            cb.setRGBColorFill(0xFF, 0x00, 0x00);
            cb.arc(40, 40, 60, 60, 0, 360);
            cb.fillStroke();
            cb.setRGBColorFill(0x00, 0x00, 0x00);
            cb.arc(48, 48, 52, 52, 0, 360);
            cb.fill();
            acroForm.addMap("map", null, "Chap13.php", cb, 171, 300, 271, 400);
            acroForm.addHiddenField("hidden", "secret");
        }
        catch (Exception de) {
            de.printStackTrace();
        }

        // step 5: close the document
        document.close();
    }

}