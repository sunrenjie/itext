/**
 * This example was written by Jason Clapham
 * for iText users who want a very simple example
 * of how to code a servlet to output a PDF document.
 */

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;

public class OutSimplePDF extends HttpServlet {
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }
    
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
    /*
     *  This outputs a given message as PDF to illustrate the workaround
     *  for the MS Internet Explorer bug discussed at:
     *  http://www.lowagie.com/iText/faq.html#msie
     *
     */
        
        try {
            
            // take the message from the URL or create default message
            String msg = (String)request.getParameter("msg");
            if (msg == null || msg.trim().length() <= 0)
                msg = "[ specify a message in the 'msg' argument on the URL ]";
            
            // create simple doc and write to a ByteArrayOutputStream
            Document document = new Document();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            PdfWriter.getInstance(document, baos);
            document.open();
            document.add(new Paragraph(msg));
            document.close();
            
            // write ByteArrayOutputStream to the ServletOutputStream
            response.setContentType("application/pdf");
            response.setContentLength(baos.size());
            ServletOutputStream out = response.getOutputStream();
            baos.writeTo(out);
            out.flush();
            
            // this is a workaround for a bug in MSIE
            // see http://www.lowagie.com/iText/faq.html#msie
            
        }
        catch (Exception e2) {
            System.out.println("Error in "+getClass().getName()+"\n"+e2);
        }
    }
    
    public void destroy() {
    }
}