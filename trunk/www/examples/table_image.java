import java.io.*;
import com.lowagie.text.*;
import com.lowagie.text.pdf.*;

public class table_image {
    
    public static void main(String[] args)
    {
        Document document = new Document(PageSize.A4, 50, 50, 50, 50);
        try {
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("table_image.pdf"));
            document.open();
            Image img = Image.getInstance("pngnow.png");
            Chunk ck = new Chunk(img, 0, 0);
            Table table = new Table(3);
            table.setWidth(100);
            table.setCellspacing(2);
            table.setDefaultHorizontalAlignment(Element.ALIGN_CENTER);
            Cell cell = new Cell(new Chunk(img, 0, -13));
            cell.setLeading(img.scaledHeight());
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell("I see an image on my right");
            table.addCell(cell);
            table.addCell("I see an image on my left");
            table.addCell(cell);
            table.addCell("I see images everywhere");
            table.addCell(cell);
            table.addCell("I see an image on my right");
            table.addCell(cell);
            table.addCell("I see an image on my left");
            
            Paragraph p1 = new Paragraph("This is an image ");
            p1.add(ck);
            p1.add(" just here.");
            document.add(p1);
            document.add(table);
            Paragraph p2 = new Paragraph("More Images ");
            p2.setLeading(img.scaledHeight() * 1.1f);
            p2.setAlignment(Element.ALIGN_JUSTIFIED);
            img.scalePercent(70);
            for (int k = 0; k < 20; ++k)
                p2.add(ck);
            document.add(p2);
            document.close();
        }
        catch (Exception de) {
            System.err.println(de.getMessage());
        }
        System.out.println("Finished.");
    }
}
