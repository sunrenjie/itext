/*
 * @(#)Text.java
 *
 * This class can be used as an example on how to use the iText library
 */

import java.awt.Color;
import java.io.FileOutputStream;
import java.io.IOException;

import com.lowagie.text.*;
import com.lowagie.text.html.HtmlWriter;
//import com.lowagie.text.xml.XmlWriter;
import com.lowagie.text.pdf.PdfWriter;

/**
 * With this class you can test the iText library.
 *
 * @author  bruno@lowagie.com
 *
 * @since   iText0.30
 */

public class Text {

// main method

    /**
     * This method generates all kinds of files. 
     *
     * @since   iText0.30
     */

	public static void main(String[] args) {

		// creation of the document with a certain size and certain margins
		Document document = new Document(PageSize.A4, 50, 50, 50, 50);

		try {
			// creation of the different writers
			HtmlWriter.getInstance(document, new FileOutputStream("text.html"));
			PdfWriter.getInstance(document, new FileOutputStream("text.pdf"));
			//XmlWriter.getInstance(document, new FileOutputStream("text.xml"));
			
			// we add some meta information to the document
			document.addAuthor("Bruno Lowagie");
			document.addSubject("This is the result of a Test.");
	
			// we define a header and a footer
			HeaderFooter header = new HeaderFooter(new Phrase("This is a header."), false);
			HeaderFooter footer = new HeaderFooter(new Phrase("This is page "), new Phrase("."));
			footer.setAlignment(Element.ALIGN_CENTER);

			// we define some fonts
			Font chapterFont = new Font(Font.HELVETICA, 24, Font.NORMAL, new Color(255, 0, 0));
			Font sectionFont = new Font(Font.HELVETICA, 20, Font.NORMAL, new Color(0, 0, 255));
			Font subsectionFont = new Font(Font.HELVETICA, 18, Font.BOLD, new Color(0, 64, 64));

			// we define a table
			Table table = new Table(3);
			table.setBorderWidth(1);
			table.setBorderColor(new Color(0, 0, 255));
			table.setCellpadding(5);
			table.setCellspacing(5);
			Cell cell = new Cell("header");
			cell.setHeader(true);
			cell.setColspan(3);
			table.addCell(cell);
			cell = new Cell("example cell with colspan 1 and rowspan 2");
			cell.setRowspan(2);
			cell.setBorderColor(new Color(255, 0, 0));
			table.addCell(cell);
			table.addCell("1.1");
			table.addCell("2.1");
			table.addCell("1.2");
			table.addCell("2.2");
			table.addCell("cell test1");
			cell = new Cell("big cell");
			cell.setBackgroundColor(new Color(0, 255, 255));
			cell.setRowspan(2);
			cell.setColspan(2);
			table.addCell(cell);
			table.addCell("cell test2");
			// we open the document for writing
			document.open();

			// we create some paragraphs
			Paragraph blahblah = new Paragraph("blah blah blah blah blah blah blaah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah");
			Paragraph blahblahblah = new Paragraph("blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blaah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah");

			// this loop will create 7 chapters
			for (int i = 1; i < 8; i++) {
				Paragraph cTitle = new Paragraph("This is chapter " + i, chapterFont);
				Chapter chapter = new Chapter(cTitle, i);

				// along the way I will change some things

				// set headers/footers in the 3rd chapter
				if (i == 3) {
					Jpeg wm = new Jpeg("watermark.jpg", 200, 200);
					Watermark watermark = new Watermark(wm, (PageSize.A4.width() - 200) / 2, (PageSize.A4.height() - 200) / 2);
					document.add(watermark);
					document.setHeader(header);
					document.setFooter(footer);
				}
				// change the header in the 4th chapter and add some extra text
				if (i == 4) {
					header = new HeaderFooter(new Phrase("This is a long, long, long, long, long, long, long, long, long, long, long, long, long, long, long, long, long, long, long, long, long, long header."), false);
					document.setHeader(header);
					blahblahblah.setAlignment(Element.ALIGN_JUSTIFIED);
					blahblah.setAlignment(Element.ALIGN_JUSTIFIED);
					chapter.add(blahblah);
				}
				// change the footer in the 4th chapter and add some extra text
				if (i == 5) {
					document.removeWatermark();
					footer = new HeaderFooter(new Phrase("This is page "), new Phrase(". Long, long, long, long, long, long, long, long, long, long, long, long, long, long, long, long, long, long, long, long, long footer"));
					document.setFooter(footer);
					blahblahblah.setAlignment(Element.ALIGN_CENTER);
					blahblah.setAlignment(Element.ALIGN_RIGHT);
					chapter.add(blahblah);
				}
				// add a table in the 6th chapter
				if (i == 6) {
					blahblah.setAlignment(Element.ALIGN_JUSTIFIED);
					chapter.add(table);
					document.resetHeader();
				}
				// remove the footer in the 7th chapter
				if (i == 7) {
					document.resetFooter();
				}

				// in every chapter 3 sections will be added
				for (int j = 1; j < 4; j++) {
					Paragraph sTitle = new Paragraph("This is section " + j + " in chapter " + i, sectionFont);
					Section section = chapter.addSection(sTitle, 1);
					// in all chapters except the 1st one, some extra text is added to section 3
					if (j == 3 && i > 1) {
						section.add(blahblah);
					}
					// in every section 3 subsections are added
					for (int k = 1; k < 4; k++) {
						Paragraph subTitle = new Paragraph("This is subsection " + k + " of section " + j, subsectionFont);
						Section subsection = section.addSection(subTitle, 3);
						if (k == 1 && j == 3) {
							subsection.add(blahblahblah);
							subsection.add(table);
						}
						subsection.add(blahblah);
					}
					if (j == 2 && i > 2) {
						section.add(blahblahblah);
						section.add(table);
					}
				}

				document.add(chapter);
			}
			document.add(new Paragraph(45, new Chunk("Hello world")));

			Paragraph paragr1 = new Paragraph(16);
			paragr1.add(new Chunk("This is some text."));
			Phrase ph = new Phrase();
			Anchor link = new Anchor("text!", new Font(Font.COURIER, 20, Font.BOLDITALIC, new Color(0, 0, 255)));
			link.setReference("http://www.lowagie.com");
			ph.add(new Chunk("This is ", new Font(Font.COURIER, 20, Font.ITALIC, new Color(255, 0, 0))));
			ph.add(new Chunk("some more ", new Font(Font.SYMBOL, 20, Font.BOLD, new Color(0, 255, 0))));
			ph.add(link);
			paragr1.add(ph);
			Paragraph paragr2 = new Paragraph(ph);
			paragr1.add(paragr2);
			document.add(new Paragraph(16, new Chunk("01234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789")));
			document.add(paragr1);
			document.add(ph);

			document.add(ph);										   			
			document.add(ph);
			document.add(ph);
			document.add(ph);
			Paragraph p = new Paragraph(new Chunk("This is a very, very, very, very, very, very, very, very, very, very, very, very, very, very, very, very, very, very, very, very, very, very, very, very, very, very, very, very, very, very, very, very, very, very, very, very, very, very, very, very, very, very long line"));
			document.add(p);
			p.setIndentationLeft(30);
			document.add(p);
			p.setAlignment(Element.ALIGN_CENTER);
			document.add(p);		
			
			header = new HeaderFooter(new Phrase("This is a header. The pagenumber is "), new Phrase("."));
			document.setHeader(header);

			document.add(table);

			document.add(p);
			p.setIndentationRight(30);
			document.add(p);
			p.setAlignment(Element.ALIGN_JUSTIFIED);
			document.add(p);
	
			table.setCellspacing(0);
			document.add(table);
			p.setIndentationLeft(0);
			document.add(p);
			document.add(p);
			document.add(p); 
	
			Jpeg jpeg = new Jpeg(new java.net.URL("http://www.lowagie.com/fotoboek/wij2.jpg"), 200, 320);
			document.add(jpeg);

			document.add(p);

			p.setIndentationRight(0);
			document.add(p);
			p.setAlignment(Element.ALIGN_RIGHT);
			document.add(p);	
				
			Image gif2 = new Gif("vonnegut.gif", 198, 149);
			gif2.setAlignment(Image.RIGHT + Image.TEXTWRAP);
			document.add(gif2);

			document.add(p);
			gif2.setAlignment(Image.LEFT + Image.TEXTWRAP);
			document.add(gif2);
			document.add(p); 
			gif2.setAlignment(Image.MIDDLE + Image.TEXTWRAP);
			document.add(gif2);
			document.add(p);

			Image gif1 = new Gif("banner.gif", 300, 60);
			gif1.setAlignment(Image.MIDDLE + Image.UNDERLYING);
			document.add(gif1);

			document.add(p);
			gif1.setAlignment(Image.LEFT + Image.TEXTWRAP);
			document.add(gif1);

			document.add(p);
			jpeg.setAlignment(Image.MIDDLE + Image.TEXTWRAP);
			document.add(jpeg);
			document.add(p);
			jpeg.setAlignment(Image.RIGHT + Image.TEXTWRAP);
			document.add(jpeg);
			jpeg.setAlignment(Image.LEFT + Image.UNDERLYING);
			document.add(jpeg);
			document.add(p);
			document.add(p);
			document.add(p);
			document.add(p); 

			footer = new HeaderFooter(new Phrase("This is a footer with a lot of text in it. Really a lot of text, so that I will be able to test if the footer is being represented nice enough."), false);
			document.setFooter(footer);
			document.add(p);
			document.add(p);
			document.add(p);
			document.add(p);
			document.add(p);
			document.add(new Chunk("test"));
			document.add(p);
			document.add(p);
			document.add(p);
			document.add(p);
			document.add(p);

			p.setAlignment(Element.ALIGN_LEFT);
			document.add(p);
			document.add(new Phrase("thing1\n"));
			document.add(new Phrase("thing2\n"));
			document.add(new Phrase("thing3\n"));
			document.add(new Phrase("thing4\n"));

			int[] widths = {2, 1, 1};
			table.setWidths(widths);
			table.setCellspacing(2);
			table.setWidth(60);
			document.add(table);

			document.add(p);

			document.add(new Paragraph("This is another very, \n\nVERY,\nvery, very, very, very, very, very, very, very, very, very, very, very, very, very, very, very, very, very, very, very, very, very, very, very, very, very, very, very, very, very, very, very, very, very, very, very, very, very, very, very, very, very, very, very, very, very, very, very, very, very, very long line"));
			document.add(new Paragraph("This\n\n is some paragraph."));

			header = new HeaderFooter(new Phrase("This is a long, long, long, long, long, long, long, long, long, long, long, long, long, long, long, long, header. The pagenumber is "), new Phrase("."));
			document.setHeader(header);
  
			document.newPage();

		// new Chapter
			Paragraph title1 = new Paragraph("This is Chapter 1", new Font(Font.HELVETICA, 18, Font.BOLD, new Color(0, 0, 255)));
			Chapter chapter1 = new Chapter(title1, 1);
			chapter1.setNumberDepth(0);

		  // new Section
			Paragraph title11 = new Paragraph("This is Section 1 in Chapter 1", new Font(Font.HELVETICA, 16, Font.BOLD, new Color(255, 0, 0)));
			Section section1 = chapter1.addSection(title11);
			Paragraph someSectionText = new Paragraph("This is some silly paragraph in a chapter and/or section. It contains some text to test the functionality of Chapters and Section.");
			section1.add(someSectionText);

			List list = new List(true, 20);
			list.add(new ListItem("First line"));
			list.add(new ListItem("The second line is longer to see what happens once the end of the line is reached. Will it start on a new line?"));
			list.add(new ListItem("Third line"));
			section1.add(list);

		  // new Section
			Paragraph title12 = new Paragraph("This is Section 2 in Chapter 1", new Font(Font.HELVETICA, 16, Font.BOLD, new Color(255, 0, 0)));
			Section section2 = chapter1.addSection(title12);
			table.setCellpadding(10);
			section2.add(table);
			
			p.setAlignment(Element.ALIGN_JUSTIFIED);
			section2.add(p);
			section2.add(p);
			section2.add(p);
			section2.add(p);
			section2.add(p);
			section2.add(p);

			// new Section
			Paragraph title121 = new Paragraph("This is Section 1 in Section 2 in Chapter 1", new Font(Font.HELVETICA, 14, Font.BOLD, new Color(255, 0, 0)));
			Section section21 = section2.addSection(40, title121, 2);

			List overview = new List(false, 10);
			overview.add("This is an item");
			overview.add("This is another item");
			section21.add(overview);
			
			// new Section
			Paragraph title122 = new Paragraph("This is Section 2 in Section 2 in Chapter 1", new Font(Font.HELVETICA, 14, Font.BOLD, new Color(255, 0, 0)));
			Section section22 = section2.addSection(40, title122, 2);
			section22.add(someSectionText);

			document.add(chapter1);

		// new Chapter
			Paragraph title2 = new Paragraph("This is Chapter 2", new Font(Font.HELVETICA, 18, Font.BOLDITALIC, new Color(0, 0, 255)));
			Chapter chapter2 = new Chapter(title2, 2);
			chapter2.setNumberDepth(0);
			chapter2.add(someSectionText);
			table.setCellpadding(0);
			table.setCellspacing(0);
			chapter2.add(table);
			document.add(chapter2);
 
		}
		catch(DocumentException de) {
			System.err.println(de.getMessage());
		}
		catch(IOException ioe) {
			System.err.println(ioe.getMessage());
		}

		// we close the document
		document.close();
		System.err.println("Finished!");
	}
}