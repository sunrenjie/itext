import java.io.*;
import com.lowagie.text.*;
import com.lowagie.text.pdf.*;

public class irregular {
    
    public static void main(String[] args)
    {
        Document document = new Document(PageSize.A4, 50, 50, 50, 50);
        try {
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("c:\\irregular.pdf"));
            document.open();            
            PdfContentByte cb = writer.getDirectContent();
            BaseFont bf = BaseFont.createFont("Helvetica", "winansi", false);
            
            ColumnText ct = new ColumnText(cb);
            float left[] = {100,500, 100,100};
            float right[] = {300,500, 200,300, 100,300, 100,200, 300,200, 300,100};
            cb.setRGBColorStrokeF(1, 0, 0);
            cb.moveTo(left[0], left[1]);
            for (int k = 2; k < left.length; k += 2)
                cb.lineTo(left[k], left[k+1]);
            cb.stroke();
            cb.setRGBColorStrokeF(0, 1, 0);
            cb.moveTo(right[0], right[1]);
            for (int k = 2; k < right.length; k += 2)
                cb.lineTo(right[k], right[k+1]);
            cb.stroke();
            cb.resetRGBColorStroke();
            cb.setLineWidth(0);
            ct.setColumns(left, right);
            ct.setLeading(15);
            ct.setYLine(500);
            ct.setAlignment(Element.ALIGN_JUSTIFIED);
            ct.setIndent(10);
            Phrase ph = new Phrase("Intel makes no ");
            ph.add(new Chunk("representation or warranties", new Font(Font.TIMES_NEW_ROMAN, 12, Font.UNDERLINE | Font.ITALIC)));
            ph.add(" with respect to the contents hereof and specifically disclaims any implied warranties of merchantability or fitness for any particular purpose.\nFurther, Intel reserves the right to revise this publication from time to time in the content hereof without obligation of Intel to notify any person of such revision or changes.\nThe publication of this specification should not be construed as a commitment on Intel's part to implement any product.");
            ct.addText(ph);
            ct.go();
            
            ColumnText ct2 = new ColumnText(cb);
            ct2.setSimpleColumn(ph, 40, 800, 250, 100, 16, Element.ALIGN_JUSTIFIED);
            ct2.setExtraParagraphSpace(5);
            ct2.go();

            ct2 = new ColumnText(cb);
            ct2.setSimpleColumn(ph, 280, 800, 500, 100, 16, Element.ALIGN_CENTER);
            ct2.go();
            
            ct2 = new ColumnText(cb);
            ct2.setSimpleColumn(ph, 310, 500, 420, 300, 16, Element.ALIGN_JUSTIFIED);
            int status = ct2.go();
            if (status == ColumnText.NO_MORE_COLUMN) {
                ct2.setSimpleColumn(ph, 440, 500, 550, 300, 16, Element.ALIGN_JUSTIFIED);
                ct2.go();
            }

/*            for (;;) {
                float xx[] = ct.findLimitsTwoLines();
                if (xx == null)
                    break;
                float x1 = Math.max(xx[0], xx[2]);
                float x2 = Math.min(xx[1], xx[3]);
                cb.moveTo(x1, ct.getYLine());
                cb.lineTo(x2, ct.getYLine());
                cb.stroke();
                System.out.println("" + x1 + "," + ct.getYLine() + "-" + x2 + "," + ct.getYLine());
                cb.beginText();
                cb.setFontAndSize(bf, 18);
                cb.setTextMatrix(x1, ct.getYLine());
                cb.showText("SÂome text in this coordinate.");
                cb.endText();
            }
*/            
            document.close();
            System.out.println("Fim.");
        }
        catch (Exception de) {
            System.err.println(de.getMessage());
        }
    }
}
