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
import com.itextpdf.text.Element;
import com.itextpdf.text.ExceptionConverter;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.GrayColor;
import com.itextpdf.text.pdf.PdfAnnotation;
import com.itextpdf.text.pdf.PdfBorderDictionary;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfFormField;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPCellEvent;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.RGBColor;
import com.itextpdf.text.pdf.TextField;

public class TextFields implements PdfPCellEvent {

    public static final String RESULT1 = "results/part2/chapter08/text_fields.pdf";
    public static final String RESULT2 = "results/part2/chapter08/text_filled.pdf";
    
    protected int tf;
    
    public static void main(String[] args) throws DocumentException, IOException {
        TextFields example = new TextFields(0);
        example.createPdf(RESULT1);
        example.manipulatePdf(RESULT1, RESULT2);
    }
    
    public TextFields(int tf) {
        this.tf = tf;
    }
    
    public void manipulatePdf(String src, String dest) throws IOException, DocumentException {
        PdfReader reader = new PdfReader(src);
        PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(dest));
        AcroFields form = stamper.getAcroFields();
        System.out.println(form.getField("text_4"));
        form.setField("text_1", "Bruno Lowagie");
        form.setFieldProperty("text_2", "fflags", 0, null);
        form.setFieldProperty("text_2", "bordercolor", RGBColor.RED, null);
        form.setField("text_2", "bruno");
        form.setFieldProperty("text_3", "clrfflags", TextField.PASSWORD, null);
        form.setFieldProperty("text_3", "setflags", PdfAnnotation.FLAGS_PRINT, null);
        form.setField("text_3", "12345678", "xxxxxxxx");
        form.setFieldProperty("text_4", "textsize", new Float(12), null);
        form.regenerateField("text_4");
        stamper.close();
    }
    
    public void createPdf(String filename) throws DocumentException, IOException {
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(filename));
        document.open();

        PdfPCell cell;
        PdfPTable table = new PdfPTable(2);
        table.setWidths(new int[]{ 1, 2 });

        table.addCell("Name:");
        cell = new PdfPCell();
        cell.setCellEvent(new TextFields(1));
        table.addCell(cell);
        
        table.addCell("Loginname:");
        cell = new PdfPCell();
        cell.setCellEvent(new TextFields(2));
        table.addCell(cell);
        
        table.addCell("Password:");
        cell = new PdfPCell();
        cell.setCellEvent(new TextFields(3));
        table.addCell(cell);
        
        table.addCell("Reason:");
        cell = new PdfPCell();
        cell.setCellEvent(new TextFields(4));
        cell.setFixedHeight(60);
        table.addCell(cell);

        document.add(table);
        
        document.close();

    }

    public void cellLayout(PdfPCell cell, Rectangle rectangle, PdfContentByte[] canvases) {
        PdfWriter writer = canvases[0].getPdfWriter();
        TextField text = new TextField(writer, rectangle,
                String.format("text_%s", tf));
        text.setBackgroundColor(new GrayColor(0.75f));
        switch(tf) {
        case 1:
            text.setBorderStyle(PdfBorderDictionary.STYLE_BEVELED);
            text.setAlignment(Element.ALIGN_RIGHT);
            text.setText("Enter your name here...");
            text.setFontSize(0);
            text.setAlignment(Element.ALIGN_CENTER);
            text.setOptions(TextField.REQUIRED);
            break;
        case 2:
            text.setMaxCharacterLength(8);
            text.setOptions(TextField.COMB);
            text.setBorderStyle(PdfBorderDictionary.STYLE_SOLID);
            text.setBorderColor(RGBColor.BLUE);
            text.setBorderWidth(2);
            break;
        case 3:
            text.setBorderStyle(PdfBorderDictionary.STYLE_INSET);
            text.setOptions(TextField.PASSWORD);
            text.setVisibility(TextField.VISIBLE_BUT_DOES_NOT_PRINT);
            break;
        case 4:
            text.setBorderStyle(PdfBorderDictionary.STYLE_DASHED);
            text.setBorderColor(RGBColor.RED);
            text.setBorderWidth(2);
            text.setFontSize(8);
            text.setText("Enter the reason why you want to win a free accreditation for the Foobar Film Festival");
            text.setOptions(TextField.MULTILINE | TextField.REQUIRED);
            break;
        }
        try {
            PdfFormField field = text.getTextField();
            if (tf == 3) {
                field.setUserName("Choose a password");
            }
            writer.addAnnotation(field);
        }
        catch(IOException ioe) {
            throw new ExceptionConverter(ioe);
        }
        catch(DocumentException de) {
            throw new ExceptionConverter(de);
        }
    }
}
