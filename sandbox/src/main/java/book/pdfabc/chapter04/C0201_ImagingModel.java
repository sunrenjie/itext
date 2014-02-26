package book.pdfabc.chapter04;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;

public class C0201_ImagingModel {

    public static final String DEST = "results/pdfabc/c04/hello_world.pdf";
    
    public static void main(String[] args) throws IOException, DocumentException {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new C0201_ImagingModel().createPdf(DEST);
    }
    
    public void createPdf(String dest) throws IOException, DocumentException {
        // step 1
        Document document = new Document();
        // step 2
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(dest));
        // step 3
        document.open();
        // step 4
        PdfContentByte canvas = writer.getDirectContent();
        // THIS IS NOT THE USUAL WAY TO ADD TEXT; THIS IS THE HARD WAY!!!
        canvas.beginText();
        canvas.moveText(36, 788);
        canvas.setFontAndSize(BaseFont.createFont(), 12);
        canvas.showText("Hello World ");
        canvas.endText();
        // This creates a path for a line and strokes it
        canvas.saveState();
        canvas.moveTo(0, 0);
        canvas.lineTo(595, 842);
        canvas.stroke();
        canvas.restoreState();
        // step 5
        document.close();
    }
}