package part3.chapter09;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.GrayColor;
import com.itextpdf.text.pdf.PdfAction;
import com.itextpdf.text.pdf.PdfFormField;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PushbuttonField;

public class XFDFServlet extends HttpServlet {

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {
        response.setContentType("application/pdf");
        try {
            // We get a resource from our web app
            InputStream is
                = getServletContext().getResourceAsStream("/resources/subscribe.pdf");
            // We create a reader with the InputStream
            PdfReader reader = new PdfReader(is, null);
            // We create an OutputStream for the new PDF
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            // Now we create the PDF
            PdfStamper stamper = new PdfStamper(reader, baos);
            // We add a submit button to the existing form
            PushbuttonField button = new PushbuttonField(
                stamper.getWriter(), new Rectangle(90, 660, 140, 690), "submit");
            button.setText("POST");
            button.setBackgroundColor(new GrayColor(0.7f));
            button.setVisibility(PushbuttonField.VISIBLE_BUT_DOES_NOT_PRINT);
            PdfFormField submit = button.getField();
            submit.setAction(PdfAction.createSubmitForm(
                "/book/xfdf", null, PdfAction.SUBMIT_XFDF));
            stamper.addAnnotation(submit, 1);
            stamper.close();
            // We write the PDF bytes to the OutputStream
            OutputStream os = response.getOutputStream();
            baos.writeTo(os);
            os.flush();
        } catch (DocumentException e) {
            throw new IOException(e.getMessage());
        }
	}

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        response.setContentType("application/vnd.adobe.xfdf");
        response.setHeader("Content-Disposition",
		    "attachment; filename=\"subscribe.xfdf\"");
        OutputStream os = response.getOutputStream();
        InputStream is = request.getInputStream();
        byte[] b = new byte[256];  
        int read;  
        while ((read = is.read(b)) != -1) {  
            os.write(b, 0, read);  
        }
    }

    /**
     * Serial version UID.
     */
    private static final long serialVersionUID = 7582490560292953774L;

}
