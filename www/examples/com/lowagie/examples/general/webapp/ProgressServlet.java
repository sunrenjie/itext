/*
 * $Id$
 * $Name$
 *
 * This code is free software. It may only be copied or modified
 * if you include the following copyright notice:
 *
 * --> Copyright 2001-2005 by Bruno Lowagie <--
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

package com.lowagie.examples.general.webapp;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Paragraph;
import com.lowagie.text.html.HtmlWriter;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.rtf.RtfWriter;

/**
 * If you want to avoid that your Servlet times out, you should use this ProgressServlet.
 * 
 * @author blowagie
 */
public class ProgressServlet extends HttpServlet {
	   
    /**
     * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
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
            else if ("rtf".equals(presentationtype)) {
                response.setContentType("text/rtf");
                RtfWriter.getInstance(document, response.getOutputStream());
            }
            else {
                response.sendRedirect("http://itext.sourceforge.net/tutorial/general/webapp/index.html#HelloWorld");
            }
            
            // step 3
            document.open();
            
            // step 4
            document.add(new Paragraph("Hello World"));
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