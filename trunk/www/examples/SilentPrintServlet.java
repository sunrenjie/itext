/**
 * Title:        SilentPrintServlet
 * Description:  Explain to print silently via Servlet/Browser
 * @author     Heiner Jostkleigrewe, Heiner.Jostkleigrewe@gt-net.de
 * @since      iText paolo 0.89a 
 */
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

import com.lowagie.text.*;
import com.lowagie.text.pdf.*;

public class SilentPrintServlet extends HttpServlet
{
  public static final int ACT_INIT = 0;
  public static final int ACT_REPORT_1 = 1;
  
  public SilentPrintServlet()
  {
  }

  public void doGet(HttpServletRequest requ, HttpServletResponse resp) throws ServletException, IOException
  {
  	doWork(requ, resp);	
  }
  
  public void doPost(HttpServletRequest requ, HttpServletResponse resp) throws ServletException, IOException
  {
  	doWork(requ, resp);	
  }
  
  public void doWork(HttpServletRequest requ, HttpServletResponse resp) throws ServletException, IOException
  {
    ServletOutputStream out = resp.getOutputStream();
    int action = ACT_INIT;
    int sub = ACT_INIT;
    try
    {
      action = Integer.parseInt(requ.getParameter("action"));
      sub = Integer.parseInt(requ.getParameter("sub"));
    }
    catch (Exception e)
    {}

    switch (action)
    {
      case ACT_INIT:
      {
        htmlHeader(out, requ, resp);
        formular(out, requ, resp, sub);
        break;
      }

      case ACT_REPORT_1:
      {
        Document document = new Document(PageSize.A4, 10, 10, 40, 10);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        
       try
        {
          PdfWriter writer = PdfWriter.getInstance(document, baos);
          document.open();
       	  if (requ.getParameter("preview") == null)
            writer.addJavaScript("this.print(false);",false);
	  document.add(new Chunk("Silent Auto Print"));        
    	  document.close();
        }
       catch (DocumentException e)
        {
        	e.printStackTrace();
        }
        resp.setContentType("application/pdf");

        resp.setContentLength(baos.size());
        baos.writeTo(out);
        out.flush();
        break;
      }
    }
  }

  private void htmlHeader(ServletOutputStream out,
                          HttpServletRequest requ,
                          HttpServletResponse resp
                          )
                          throws IOException
  {

    resp.setContentType("text/html; charset=ISO-8859-1");
    resp.setHeader("Cache-Control", "no-cache");
    out.println("<html>");
    out.println("<head>");
    out.println("<meta http-equiv='Content-Type' content='text/html;charset=iso-8859-1'>");
    out.println("<meta http-equiv='expires' content='0'>");
    out.println("<meta http-equiv='cache-control' content='no-cache'>");
    out.println("<meta http-equiv='pragma' content='no-cache'>");
    out.println("</head>");
    out.println("<body>");
  }
  
  private void formular(ServletOutputStream out,
                        HttpServletRequest requ,
                        HttpServletResponse resp,
                        int sub
                        )
                        throws IOException
  {
    out.print("<form method='post' action='");
    out.print(requ.getRequestURI());
    out.print("?action=");
    out.print(ACT_INIT);
    out.print("&sub=");
    out.println(ACT_REPORT_1);
    out.println("'>");
    out.print("<input type='checkbox' name='preview' value='Y'");
    if (requ.getParameter("preview") != null)
	out.print(" checked ");
    out.println(">preview<br>");

    out.println("<input type=submit value='Report 1'>");
    out.println("</form>");
    if (sub != ACT_INIT)
    {
    	if (requ.getParameter("preview") != null)
    	{
    		out.println("<script languate='JavaScript'>");
		out.print("w = window.open(\"");
		out.print(requ.getRequestURI());
		out.print("?action=");
		out.print(sub);
		out.print("&preview=Y\", \"Printing\", \"width=800,height=450,scrollbars,menubar=yes,resizable=yes\");");
    		out.println("</script>");
    	}
    	else
    	{
    		out.print("<iframe src='");
		out.print(requ.getRequestURI());
		out.print("?action=");
		out.print(sub);
    		out.println("' width='10' height='10' name='pdf_box'>");
    	}
    }
    out.println("</body>");
    out.println("</html>");
  }
}
