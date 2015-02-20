/**
 * Example written by Bruno Lowagie in answer to the following question:
 * http://stackoverflow.com/questions/28610545/get-page-number-in-itext
 */
package sandbox.tables;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class SimpleTable5 {
    public static final String DEST = "results/tables/simple_table5.pdf";

    public static void main(String[] args) throws IOException,
            DocumentException {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new SimpleTable5().createPdf(DEST);
    }
    public void createPdf(String dest) throws IOException, DocumentException {
        Document document = new Document(PageSize.A4.rotate());
        PdfWriter.getInstance(document, new FileOutputStream(dest));
        document.open();
        PdfPTable table = new PdfPTable(5);
    	table.setWidthPercentage(100);
    	PdfPCell cell = new PdfPCell(new Phrase("Table XYZ (Continued)"));
        cell.setColspan(5);
    	table.addCell(cell);
    	cell = new PdfPCell(new Phrase("Continue on next page"));
        cell.setColspan(5);
    	table.addCell(cell);
        table.setHeaderRows(2);
        table.setFooterRows(1);
        table.setSkipFirstHeader(true);
        table.setSkipLastFooter(true);
    	for (int i = 0; i < 350; i++) {
            table.addCell(String.valueOf(i+1));
    	}
    	document.add(table);
    	document.close();
    }

}
