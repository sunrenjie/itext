import java.awt.Color;
import java.io.*;

import com.lowagie.text.*;
import com.lowagie.text.pdf.*;

public class align2 {


public static void main(String[] args)
{
	// creation of the document with a certain size and certain margins
	Document document = new Document(PageSize.A4, 50, 50, 50, 50);
	try
	{
		// creation of the different writers
		PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("pdfalign2.pdf"));

		document.open();
        BaseFont bf = BaseFont.createFont("Helvetica", "Cp1252", false);
        PdfContentByte cb = writer.getDirectContent();
        cb.setLineWidth(0f);
        cb.moveTo(250, 500);
        cb.lineTo(250, 800);
        cb.moveTo(50, 700);
        cb.lineTo(400, 700);
        cb.moveTo(50, 650);
        cb.lineTo(400, 650);
        cb.moveTo(50, 600);
        cb.lineTo(400, 600);
        cb.stroke();
        cb.beginText();
        cb.setFontAndSize(bf, 12);
        String text = "Sample text for alignment";
        cb.showTextAligned(PdfContentByte.ALIGN_CENTER, text + " Center", 250, 700, 0);
        cb.showTextAligned(PdfContentByte.ALIGN_RIGHT, text + " Right", 250, 650, 0);
        cb.showTextAligned(PdfContentByte.ALIGN_LEFT, text + " Left", 250, 600, 0);
        cb.setTextMatrix(100, 400);
        cb.setFontAndSize(bf, 14);
        cb.showText("Text at position 100,400.");
        cb.endText();
		document.close();
		System.out.println("FIM.");
	}
	catch (Exception de)
	{
		System.err.println(de.getMessage());
	}
}
}
