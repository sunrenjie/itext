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
 * ir-arch Bruno Lowagie,
 * Adolf Baeyensstraat 121
 * 9040 Sint-Amandsberg
 * BELGIUM
 * tel. +32 (0)9 228.10.97
 * bruno@lowagie.com
 */

import java.io.PrintWriter;
import java.io.IOException;
import java.io.File;

import java.util.Date;

import javax.servlet.http.*;
import javax.servlet.*;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.html.HtmlWriter;

public class Chap0105 extends HttpServlet {
    
    public void doGet (HttpServletRequest request, HttpServletResponse response)
    throws IOException, ServletException {
        
        // we retrieve the presentationtype
        String presentationtype = request.getParameter("presentationtype");
        
        // step 1
        Document document = new Document();
        try {
            // step 2: we set the ContentType and create an instance of the corresponding Writer
            if ("pdf".equals(presentationtype)) {
                response.setContentType("application/pdf");
                PdfWriter.getInstance(document, response.getOutputStream());
            }
            else if ("html".equals(presentationtype)) {
                response.setContentType("text/html");
                HtmlWriter.getInstance(document, response.getOutputStream());
            }
            else {
                response.sendRedirect("http://www.lowagie.com/iText/tutorial/ch01.html#step2");
            }
            
            // step 3
            document.open();
            
            // step 4
            document.add(new Paragraph(new Date().toString()));
        }
        catch(DocumentException de) {
            de.printStackTrace();
            System.err.println("document: " + de.getMessage());
        }
        
        // step 5: we close the document (the outputstream is also closed internally)
        document.close();
    }
}