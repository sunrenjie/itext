/*
 * $Id$
 * $Name$
 *
 * This code is free software. It may only be copied or modified
 * if you include the following copyright notice:
 *
 * --> Copyright 2000, 2001 by Bruno Lowagie <--
 *
 * This code is part of the 'iText Tutorial'.
 * You can find the complete tutorial at the following address:
 * http://www.lowagie.com/iText/tutorial/
 *
 * This code is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 */

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

import javax.servlet.http.*;
import javax.servlet.*;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;

/**
 * With this class you can test the iText library
 * and make a nice Calendar.
 *
 * @author  bruno@lowagie.com
 */

public class Calendar extends HttpServlet {
    
    // public static final membervariables
    
        /** This is a language value. */
    public static final int DUTCH = 0;
    
        /** This is a language value. */
    public static final int ENGLISH = 1;
    
        /** These are the months of the year. */
    public static final String[][] MONTH = new String[2][12];
    
    static {
        MONTH[DUTCH][0]                 = "januari";
        MONTH[DUTCH][1]                 = "februari";
        MONTH[DUTCH][2]                 = "maart";
        MONTH[DUTCH][3]                 = "april";
        MONTH[DUTCH][4]                 = "mei";
        MONTH[DUTCH][5]                 = "juni";
        MONTH[DUTCH][6]                 = "juli";
        MONTH[DUTCH][7]                 = "augustus";
        MONTH[DUTCH][8]                 = "september";
        MONTH[DUTCH][9]                 = "oktober";
        MONTH[DUTCH][10]                = "november";
        MONTH[DUTCH][11]                = "december";
        
        MONTH[ENGLISH][0]               = "january";
        MONTH[ENGLISH][1]               = "february";
        MONTH[ENGLISH][2]               = "march";
        MONTH[ENGLISH][3]               = "april";
        MONTH[ENGLISH][4]               = "may";
        MONTH[ENGLISH][5]               = "june";
        MONTH[ENGLISH][6]               = "july";
        MONTH[ENGLISH][7]               = "august";
        MONTH[ENGLISH][8]               = "september";
        MONTH[ENGLISH][9]               = "october";
        MONTH[ENGLISH][10]              = "november";
        MONTH[ENGLISH][11]              = "december";
    }
    
        /** These are the days of the week. */
    public static final String[][] DAY = new String[2][7];
    
    static {
        DAY[DUTCH][0]   = "MA";
        DAY[DUTCH][1]   = "DI";
        DAY[DUTCH][2]   = "WO";
        DAY[DUTCH][3]   = "DO";
        DAY[DUTCH][4]   = "VR";
        DAY[DUTCH][5]   = "ZA";
        DAY[DUTCH][6]   = "ZO";
        
        DAY[ENGLISH][0] = "MO";
        DAY[ENGLISH][1] = "TU";
        DAY[ENGLISH][2] = "WE";
        DAY[ENGLISH][3] = "TH";
        DAY[ENGLISH][4] = "FR";
        DAY[ENGLISH][5] = "SA";
        DAY[ENGLISH][6] = "SU";
    }
    
    /**
     * Overriding the init method
     */
    
    public void init(ServletConfig config)
    throws ServletException {
        
        super.init(config);
    }
    
    /**
     * This method generates a PDF Calender.
     */
    
    public void doPost(HttpServletRequest request, HttpServletResponse response)
    throws IOException, ServletException {
        
        // getting the color parameters
        String c1red = request.getParameter("c1red");
        String c1green = request.getParameter("c1green");
        String c1blue = request.getParameter("c1blue");
        String c2red = request.getParameter("c2red");
        String c2green = request.getParameter("c2green");
        String c2blue = request.getParameter("c2blue");
        String c3red = request.getParameter("c3red");
        String c3green = request.getParameter("c3green");
        String c3blue = request.getParameter("c3blue");
        
        // setting the color values
        Color border = new Color(0x00, 0x00, 0x00);
        Color bgTable = new Color(0x40, 0x00, 0xFF);
        Color bgCells = new Color(0xFF, 0xFF, 0x00);
        
        try {
            int i;
            i = Integer.parseInt(c1red);
            if (i < 0 || i > 255) {
                throw new Exception();
            }
            i = Integer.parseInt(c1green);
            if (i < 0 || i > 255) {
                throw new Exception();
            }
            i = Integer.parseInt(c1blue);
            if (i < 0 || i > 255) {
                throw new Exception();
            }
            if (c1red != null && c1green != null && c1blue != null) {
                border = new Color(Integer.parseInt(c1red), Integer.parseInt(c1green), Integer.parseInt(c1blue));
            }
        }
        catch(Exception e) {
        }
        
        try {
            int i;
            i = Integer.parseInt(c2red);
            if (i < 0 || i > 255) {
                throw new Exception();
            }
            i = Integer.parseInt(c2green);
            if (i < 0 || i > 255) {
                throw new Exception();
            }
            i = Integer.parseInt(c2blue);
            if (i < 0 || i > 255) {
                throw new Exception();
            }
            if (c2red != null && c2green != null && c2blue != null) {
                bgTable = new Color(Integer.parseInt(c2red), Integer.parseInt(c2green), Integer.parseInt(c2blue));
            }
        }
        catch(Exception e) {
        }
        
        try {
            int i;
            i = Integer.parseInt(c3red);
            if (i < 0 || i > 255) {
                throw new Exception();
            }
            i = Integer.parseInt(c3green);
            if (i < 0 || i > 255) {
                throw new Exception();
            }
            i = Integer.parseInt(c3blue);
            if (i < 0 || i > 255) {
                throw new Exception();
            }
            if (c3red != null && c3green != null && c3blue != null) {
                bgCells = new Color(Integer.parseInt(c3red), Integer.parseInt(c3green), Integer.parseInt(c3blue));
            }
        }
        catch(Exception e) {
        }
        
        // getting the year
        String y = request.getParameter("year");
        int year;
        if (y != null) {
            year = Integer.parseInt(y);
            if (year < 2000 || year > 2034) {
                year = 2001;
            }
        }
        else {
            year = 2001;
        }
        
        // setting some fonts in the color chosen by the user
        Font font1 = new Font(Font.HELVETICA, 14, Font.NORMAL, border);
        Font font2 = new Font(Font.HELVETICA, 24, Font.BOLD, border);
        // creating some content that will be used frequently
        Paragraph newLine = new Paragraph("\n", font1);
        Anchor anchor = new Anchor("visit http://www.lowagie.com/iText/", font1);
        anchor.setReference("http://www.lowagie.com/iText/");
        Paragraph link = new Paragraph(anchor);
        link.setAlignment(Element.ALIGN_CENTER);
        
        // getting the language
        int language;
        String l = request.getParameter("language");
        if (l == null || Integer.parseInt(l) == ENGLISH) {
            language = ENGLISH;
        }
        else {
            language = DUTCH;
        }
        
        // step1: creating the document object
        Document document = new Document(PageSize.A4, 30, 30, 50, 50);
        
        // step2.1: creating an OutputStream
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        
        try {
            // step2.2: creating an instance of the writer
            PdfWriter.getInstance(document, baos);
            
            // step 3.1: adding some metadata to the document
            document.addSubject("This is a Calendar for the year " + year + ".");
            
            // step 3.2: adding a Header
            HeaderFooter header = new HeaderFooter(new Phrase("iText Calendar", font1), false);
            header.setAlignment(Element.ALIGN_CENTER);
            header.setBorder(Rectangle.BOTTOM);
            header.setBorderColor(border);
            document.setHeader(header);
            
            // step 3.3: adding a Footer
            HeaderFooter footer = new HeaderFooter(new Phrase("iText, a JAVA PDF library", font1), false);
            footer.setAlignment(Element.ALIGN_CENTER);
            footer.setBorder(Rectangle.TOP);
            footer.setBorderColor(border);
            document.setFooter(footer);
            
            // step 3.4: opening the document
            document.open();
            
            // step 4: loop over the months: each month is a new page
            for (int i = 0; i < 12; i++) {
                
                // add the name of the current month
                Paragraph name = new Paragraph(MONTH[language][i] + " " + year, font2);
                name.setAlignment(Element.ALIGN_CENTER);
                document.add(name);
                document.add(newLine);
                
                // retrieve and add the image that has to be on this Calendar page
                try {
                    Image image = Image.getInstance(new URL(request.getParameter("img" + i)));
                    image.scaleToFit(450, 250);
                    image.setAlignment(Image.MIDDLE);
                    document.add(image);
                }
                catch(Exception e) {
                    document.add(new Paragraph(e.getMessage()));
                }
                document.add(newLine);
                
                // add a table with all the days of this month
                Month month = new Month(i, year, DAY[language], border, bgTable, bgCells);
                month.setAlignment(Element.ALIGN_CENTER);
                document.add(month);
                
                // adding a link to my page
                document.add(link);
                
                // add a page break
                document.newPage();
                
            }
        }
        catch(DocumentException de) {
            System.err.println(de.getMessage());
            de.printStackTrace();
        }
        
        // step 5: closing the document
        document.close();
        
        // we have written the pdfstream to a ByteArrayOutputStream,
        // now we are going to write this outputStream to the ServletOutputStream
        // after we have set the contentlength (see http://www.lowagie.com/iText/faq.html#msie)
        response.setContentType("application/pdf");
        response.setContentLength(baos.size());
        ServletOutputStream out = response.getOutputStream();
        baos.writeTo(out);
        out.flush();
    }
}