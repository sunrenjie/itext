/*
 * $Id$
 * $Name$
 *
 * This code is free software. It may only be copied or modified
 * if you include the following copyright notice:
 *
 * --> Copyright 2001 by Bruno Lowagie <--
 *
 * This code is part of the 'iText Tutorial'.
 * You can find the complete tutorial at the following address:
 * http://www.lowagie.com/iText/tutorial/
 *
 * This code is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 *
 * itext-questions@lists.sourceforge.net
 */

import java.io.FileOutputStream;
import java.io.IOException;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;

import com.lowagie.text.html.HtmlWriter;

public class Chap0108 {
    
    public static void main(String[] args) {
        
        System.out.println("Chapter 1 example 8: Viewerpreferences");
        
        // step 1: creation of a document-object
        Document document = new Document();
        
        try {
            
            // step 2:
            // we create a writer that listens to the document
            // and directs a PDF-stream to a file
            
            PdfWriter writerA = PdfWriter.getInstance(document, new FileOutputStream("Chap0108a.pdf"));
            writerA.setViewerPreferences(PdfWriter.PageLayoutTwoColumnLeft);
            PdfWriter writerB = PdfWriter.getInstance(document, new FileOutputStream("Chap0108b.pdf"));
            writerB.setViewerPreferences(PdfWriter.HideMenubar | PdfWriter.HideToolbar);
            PdfWriter writerC = PdfWriter.getInstance(document, new FileOutputStream("Chap0108c.pdf"));
            writerC.setViewerPreferences(PdfWriter.PageLayoutTwoColumnLeft | PdfWriter.PageModeFullScreen | PdfWriter.NonFullScreenPageModeUseThumbs);
            
            // step 3:
            
            // we add a Watermark that will show up on PAGE 1
            try {
                Watermark watermark = new Watermark(Image.getInstance("watermark.jpg"), 200, 420);
                document.add(watermark);
            }
            catch(Exception e) {
                System.err.println("Are you sure you have the file 'watermark.jpg' in the right path?");
            }
            
            // we add a Header that will show up on PAGE 1
            HeaderFooter header = new HeaderFooter(new Phrase("This is a header"), false);
            document.setHeader(header);
            
            // we open the document
            document.open();
            
            // we rotate the page, starting from PAGE 2
            document.setPageSize(PageSize.A4.rotate());
            
            // we need to change the position of the Watermark
            try {
                Watermark watermark = new Watermark(Image.getInstance("watermark.jpg"), 320, 200);
                document.add(watermark);
            }
            catch(Exception e) {
                System.err.println("Are you sure you have the file 'watermark.jpg' in the right path?");
            }
            
            // we add a Footer that will show up on PAGE 2
            HeaderFooter footer = new HeaderFooter(new Phrase("This is page: "), true);
            document.setFooter(footer);
            
            // step 4: we add content to the document
            
            // PAGE 1
            
            document.add(new Paragraph("Hello World"));
            
            // we trigger a page break
            document.newPage();
            
            // PAGE 2
            
            // we add some more content
            document.add(new Paragraph("Hello Earth"));
            
            // we remove the header starting from PAGE 3
            document.resetHeader();
            
            // we trigger a page break
            document.newPage();
            
            // PAGE 3
            
            // we add some more content
            document.add(new Paragraph("Hello Sun"));
            document.add(new Paragraph("Remark: the header has vanished!"));
            
            // we reset the page numbering
            document.resetPageCount();
            
            // we trigger a page break
            document.newPage();
            
            // PAGE 4
            
            // we add some more content
            document.add(new Paragraph("Hello Moon"));
            document.add(new Paragraph("Remark: the pagenumber has been reset!"));
            
        }
        catch(DocumentException de) {
            System.err.println(de.getMessage());
        }
        catch(IOException ioe) {
            System.err.println(ioe.getMessage());
        }
        
        // step 5: we close the document
        document.close();
    }
}
