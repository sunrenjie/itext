import java.io.*;
import com.lowagie.text.*;
import com.lowagie.text.pdf.*;

public class remote_goto_1 {
    
    public static void main(String[] args)
    {
        Document document = new Document(PageSize.A4, 50, 50, 50, 50);
        try {
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("c:\\remote_goto_1.pdf"));
            document.open();
            Paragraph p1 = new Paragraph(new Chunk("Hit me to go to document 2!").setRemoteGoto("remote_goto_2.pdf", "p1").setLocalDestination("p1"));
            document.add(p1);
            document.close();
        }
        catch (Exception de) {
            System.err.println(de.getMessage());
        }
        System.out.println("Finished.");
    }
}
