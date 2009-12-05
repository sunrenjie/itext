/*
 * This class is part of the book "iText in Action - 2nd Edition"
 * written by Bruno Lowagie (ISBN: 9781935182610)
 * For more info, go to: http://itextpdf.com/examples/
 * This example only works with the AGPL version of iText.
 */

package part2.chapter08;

import java.io.FileOutputStream;
import java.io.IOException;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.GrayColor;
import com.itextpdf.text.pdf.PdfAction;
import com.itextpdf.text.pdf.PdfFormField;
import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.TextField;

public class TextFieldActions {

    public static final String RESULT = "results/part2/chapter08/field_actions.pdf";
    public static void main(String[] args) throws IOException, DocumentException {
        new TextFieldActions().createPdf(RESULT);
    }
    public void createPdf(String filename) throws IOException, DocumentException {
        Document document = new Document();
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(filename));
        document.open();
        TextField date = new TextField(writer, new Rectangle(36, 806, 126, 780), "date");
        date.setBorderColor(new GrayColor(0.2f));
        PdfFormField datefield = date.getTextField();
        datefield.setAdditionalActions(PdfName.V, PdfAction.javaScript(
                "AFDate_FormatEx( 'dd-mm-yyyy' );", writer));
        writer.addAnnotation(datefield);
        TextField name = new TextField(writer, new Rectangle(130, 806, 256, 780), "name");
        name.setBorderColor(new GrayColor(0.2f));
        PdfFormField namefield = name.getTextField();
        namefield.setAdditionalActions(PdfName.FO, PdfAction.javaScript(
                "app.alert('name field got the focus');", writer));
        namefield.setAdditionalActions(PdfName.BL, PdfAction.javaScript(
                "app.alert('name lost the focus');", writer));
        namefield.setAdditionalActions(PdfName.K, PdfAction.javaScript(
                "event.change = event.change.toUpperCase();", writer));
        writer.addAnnotation(namefield);
        document.close();
    }
}
