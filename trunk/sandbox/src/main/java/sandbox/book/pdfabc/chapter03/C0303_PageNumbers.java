package sandbox.book.pdfabc.chapter03;

import java.io.IOException;

import com.itextpdf.text.pdf.PdfDictionary;
import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PdfReader;

public class C0303_PageNumbers {
	public static final String SRC = "resources/pdfs/primes.pdf";
	
    public static void main(String[] args) throws IOException {
        PdfReader reader = new PdfReader(SRC);
        int n = reader.getNumberOfPages();
        PdfDictionary page;
        for (int i = 1; i <= n; i++) {
            page = reader.getPageN(i);
            System.out.println("The parent of page " + i + " is " + page.get(PdfName.PARENT));
        }
        reader.close();
    }
}
