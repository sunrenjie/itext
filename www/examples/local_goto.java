import java.io.*;
import com.lowagie.text.*;
import com.lowagie.text.pdf.*;

class OutPageGoto extends PdfPageEventHelper
{
    public void onGenericTag(PdfWriter writer,Document document,Rectangle rect,String text)
    {
        PdfContentByte cb = writer.getDirectContent();
        PdfDestination destination = new PdfDestination(PdfDestination.XYZ, rect.left(), rect.top(), 0);
        PdfOutline outline = new PdfOutline(cb.getRootOutline(), destination, text);
        cb.addOutline(outline);
    }
}

public class local_goto {
    
    public static void main(String[] args)
    {
        Document document = new Document(PageSize.A4, 50, 50, 50, 50);
        try {
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("c:\\local_goto.pdf"));
            OutPageGoto pageEvent = new OutPageGoto();
            writer.setPageEvent(pageEvent);
            document.open();
            PdfContentByte cb = writer.getDirectContent();
            BaseFont bf = BaseFont.createFont("Helvetica", "winansi", false);
            cb.beginText();
            cb.setTextMatrix(300,600);
            cb.setFontAndSize(bf, 12);
            cb.showText("Local goto");
            cb.endText();
            cb.localGoto("d1", 300, 600, 350, 620);
            Paragraph p1 = new Paragraph(new Chunk("Hit me to go to page 2!").setLocalGoto("p2").setLocalDestination("p1").setGenericTag("This is page 1"));
            document.add(p1);
            document.newPage();
            Paragraph p2 = new Paragraph(new Chunk("Hit me to return to page 1!").setLocalGoto("p1").setLocalDestination("p2").setGenericTag("This is page 2"));
            document.add(p2);
            cb.beginText();
            cb.setTextMatrix(100, 500);
            cb.setFontAndSize(bf, 12);
            cb.showText("Local destination");
            cb.endText();
            PdfDestination dest = new PdfDestination(PdfDestination.XYZ, 100, 512, 0);
            cb.localDestination("d1", dest);
            document.close();
            System.out.println("Fim.");
        }
        catch (Exception de) {
            System.err.println(de.getMessage());
        }
    }
}
