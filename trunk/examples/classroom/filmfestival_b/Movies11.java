package classroom.filmfestival_b;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Set;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

import classroom.database.MySessionFactory;
import classroom.festivaldatabase.DirectorName;
import classroom.festivaldatabase.FilmTitle;

import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.List;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.ColumnText;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfPageEventHelper;
import com.lowagie.text.pdf.PdfWriter;

public class Movies11 {


	public static final String RESULT = "results/classroom/filmfestival/movies11.pdf";
	public static final Logger LOGGER = Logger.getLogger(Movies11.class.getName());
	
	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
	
		// step 1
		Document document = new Document();
		document.setMargins(36, 36, 48, 48);
		float middle = (document.right() + document.left()) / 2;
		float columns[][] = {
				{ document.left(), document.bottom(), middle - 12, document.top() },
				{ middle + 12, document.bottom(), document.right(), document.top()}
		};
		try {
			// step 2
			OutputStream os = new FileOutputStream(RESULT);
			PdfWriter writer = PdfWriter.getInstance(document, os);
			writer.setPageEvent(new Movies11().new MoviePageEvents(middle, document.top(), document.bottom()));
			// step 3
			document.open();
			// step 4
			Session session = (Session) MySessionFactory.currentSession();
			Query q = session.createQuery("from FilmTitle order by title");
			java.util.List<FilmTitle> results = q.list();
			
			ColumnText column = new ColumnText(writer.getDirectContent());
			column.setSimpleColumn(columns[0][0], columns[0][1], columns[0][2], columns[0][3]);
			
			float pos;
			int status;
			int ccount = 0;
			File f;
			Image img;
			Paragraph p;
			Chunk c;
			Font bold = new Font(Font.HELVETICA, 12, Font.BOLD);
			Font italic = new Font(Font.HELVETICA, 12, Font.ITALIC);
			for (FilmTitle movie : results) {
				f = new File("resources/classroom/filmposters/" + movie.getFilmId() + ".jpg");
				if (f.exists()) {
					img = Image.getInstance(f.getPath());
					img.setWidthPercentage(0);
					img.scaleToFit(72, 144);
				}
				else {
					img = null;
				}
				p = new Paragraph(20);
				c = new Chunk(movie.getTitle(), bold);
				c.setAnchor("http://cinema.lowagie.com/titel.php?id=" + movie.getFilmId());
				p.add(c);
				c = new Chunk(" (" + movie.getYear() + ") ", italic);
				p.add(c);
				c = new Chunk("IMDB");
				c.setAnchor("http://www.imdb.com/title/tt" + movie.getImdb());
				p.add(c);
				Set<DirectorName> directors = movie.getDirectorNames();
				List list = new List();
				for (DirectorName director : directors) {
					list.add(director.getName());
				}
				
				if (img != null) column.addElement(img);
				column.addElement(p);
				column.addElement(list);
				pos = column.getYLine();
				status = column.go(true);
				if (ColumnText.hasMoreText(status)) {
					column.setText(null);
					ccount++;
					if (ccount > 1) {
						ccount = 0;
						document.newPage();
						column.setSimpleColumn(columns[0][0], columns[0][1], columns[0][2], columns[0][3]);
					}
					else {
						column.setSimpleColumn(columns[1][0], columns[1][1], columns[1][2], columns[1][3]);
					}
				}
				else {
					column.setYLine(pos);
				}
				if (img != null) column.addElement(img);
				column.addElement(p);
				column.addElement(list);
				column.addElement(Chunk.NEWLINE);
				column.go();
			}
			// step 5
			document.close();
		} catch (IOException e) {
			LOGGER.error("IOException: ", e);
		} catch (DocumentException e) {
			LOGGER.error("DocumentException: ", e);
		}
	}
	
	class MoviePageEvents extends PdfPageEventHelper {
		private float x;
		private float top;
		private float bottom;
		private BaseFont bf;
		
		public MoviePageEvents(float x, float top, float bottom) throws DocumentException, IOException {
			this.x = x;
			this.top = top;
			this.bottom = bottom;
			bf = BaseFont.createFont(BaseFont.HELVETICA_BOLD, BaseFont.WINANSI, BaseFont.NOT_EMBEDDED);
		}

		@Override
		public void onEndPage(PdfWriter writer, Document document) {
			PdfContentByte canvas = writer.getDirectContent();
			canvas.saveState();
			canvas.beginText();
			canvas.setFontAndSize(bf, 14);
			canvas.showTextAligned(Element.ALIGN_CENTER, "FILMFESTIVAL", x, top + 12, 0);
			canvas.showTextAligned(Element.ALIGN_CENTER, "page " + writer.getPageNumber(), x, bottom - 18, 0);
			canvas.endText();
			canvas.moveTo(x, top);
			canvas.lineTo(x, bottom);
			canvas.stroke();
			canvas.restoreState();
		}

	}
}
