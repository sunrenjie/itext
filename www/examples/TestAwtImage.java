import java.io.*;
import com.lowagie.text.*;
import com.lowagie.text.pdf.*;
import java.awt.*;
import java.awt.image.*;

public class TestAwtImage {

    
    public static void main(String[] args) {
        Document document = new Document(PageSize.A4, 50, 50, 50, 50);
        try {
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("test_awt_image.pdf"));
            document.open();
            for (int k = 0; k < 39; ++k) {
                String s = "";
                for (int j = 0; j < 20; ++j)
                    s += k + "-";
                document.add(new Paragraph(s));
            }
            PdfContentByte cb = writer.getDirectContent();
            com.lowagie.text.Image img = com.lowagie.text.Image.getInstance(java.awt.Toolkit.getDefaultToolkit().createImage("surfing.gif"), null);
            img.setAbsolutePosition(100, 200);
            cb.addImage(img);
        }         
        catch(Exception de) {
            System.err.println(de.getMessage());
        }
        document.close();
        System.out.println("Finished!");
        System.exit(0);
    }
}