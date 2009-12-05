/*
 * This class is part of the book "iText in Action - 2nd Edition"
 * written by Bruno Lowagie (ISBN: 9781935182610)
 * For more info, go to: http://itextpdf.com/examples/
 * This example only works with the AGPL version of iText.
 */

package part1.chapter03;

import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.lowagie.database.DatabaseConnection;
import com.lowagie.database.HsqldbConnection;
import com.lowagie.filmfestival.Movie;
import com.lowagie.filmfestival.PojoFactory;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;

public class MovieColumns4 extends MovieColumns1 {

    /** The resulting PDF file. */
    public static final String RESULT = "results/part1/chapter03/movie_columns4.pdf";
    
    public static final float[][] LEFT =
        { { 36,806, 36,670, 108,670, 108,596, 36,596, 36,36 }, 
        { 299,806, 299,484, 336,484, 336,410, 299,410, 299,36 } };
    public static final float[][] RIGHT =
        { { 296,806, 296,484, 259,484, 259,410, 296,410, 296,36 },
        { 559,806, 559,246, 487,246, 487,172, 559,172, 559,36 } };
    
    /**
     * Creates a PDF with information about the movies
     * @param    filename the name of the PDF file that will be created.
     * @throws    DocumentException 
     * @throws    IOException 
     * @throws    SQLException
     */
    public void createPdf(String filename)
        throws IOException, DocumentException, SQLException {

        DatabaseConnection connection = new HsqldbConnection("filmfestival");    
        Document document = new Document();
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(filename));
        document.open();
        
        PdfContentByte canvas = writer.getDirectContent();
        drawRectangles(canvas);
        List<Movie> movies = PojoFactory.getMovies(connection);
        ColumnText ct = new ColumnText(canvas);
        ct.setAlignment(Element.ALIGN_JUSTIFIED);
        ct.setLeading(14);
        int column = 0;
        ct.setColumns(LEFT[column], RIGHT[column]);
        int status = ColumnText.START_COLUMN;
        Phrase p;
        float y;
        for (Movie movie : movies) {
            y = ct.getYLine();
            p = createMovieInformation(movie);
            ct.addText(p);
            status = ct.go(true);
            if (ColumnText.hasMoreText(status)) {
                column = Math.abs(column - 1);
                if (column == 0) {
                    document.newPage();
                    drawRectangles(canvas);
                }
                ct.setColumns(LEFT[column], RIGHT[column]);
                y = 806;
            }
            ct.setYLine(y);
            ct.setText(p);
            status = ct.go();
        }
        
        document.close();
        connection.close();
    }
    
    public void drawRectangles(PdfContentByte canvas) {
        canvas.saveState();
        canvas.setGrayFill(0.9f);
        canvas.rectangle(33, 592, 72, 72);
        canvas.rectangle(263, 406, 72, 72);
        canvas.rectangle(491, 168, 72, 72);
        canvas.fillStroke();
        canvas.restoreState();
    }
    
    public static void main(String[] args) throws IOException, DocumentException, SQLException {
        new MovieColumns4().createPdf(RESULT);
    }
}
