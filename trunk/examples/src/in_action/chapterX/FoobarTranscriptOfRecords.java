package in_action.chapterX;

import java.io.FileOutputStream;
import java.io.IOException;

import in_action.chapter17.FoobarLearningAgreement;

import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfPTableEvent;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.pdf.events.FieldPositioningEvents;

/**
 * This example was written by Bruno Lowagie.
 * It is an extra example for the book 'iText in Action' by Manning Publications.
 * ISBN: 1932394796
 * http://www.1t3xt.com/docs/book.php
 * http://www.manning.com/lowagie/
 */

public class FoobarTranscriptOfRecords implements PdfPTableEvent {

	/**
	 * Creates an instance of an Event to add a field.
	 */
	public FoobarTranscriptOfRecords() {
	}
	
	/**
	 * Generates a learning agreement form.
	 * @param args no arguments needed here
	 */
	public static void main(String[] args) {
		System.out.println("Chapter 10: example Transcript of Records");
		System.out.println("-> Creates a ToR form in PDF;");
		System.out.println("-> jars needed: iText.jar");		
		// step 1: creation of a document-object
		Document document = new Document();
		try {
			// step 2:
			// we create a writer
			PdfWriter writer = PdfWriter.getInstance(
					// that listens to the document
					document,
					// and directs a PDF-stream to a file
					new FileOutputStream("results/in_action/chapterX/FoobarTranscriptOfRecords.pdf"));
			FieldPositioningEvents fpe = new FieldPositioningEvents();
			writer.setPageEvent(fpe);
			// step 3: we open the document
			document.open();
			// step 4: we add a paragraph to the document

			Font font = FontFactory.getFont(BaseFont.HELVETICA, BaseFont.WINANSI, BaseFont.NOT_EMBEDDED, 14);
			Font smallFont = FontFactory.getFont(BaseFont.HELVETICA, BaseFont.WINANSI, BaseFont.NOT_EMBEDDED, 8);
			Paragraph p;
			p = new Paragraph("TRANSCRIPT OF RECORDS", font);
			p.setAlignment(Element.ALIGN_CENTER);
			document.add(p);
			PdfPTable table;
			int[] widths = { 5, 8, 8, 5, 5, 8, 8, 28 };
			table = new PdfPTable(8);
			table.setWidthPercentage(100);
			table.setSpacingBefore(10f);
			table.setSpacingAfter(0f);
			table.setTableEvent(new FoobarTranscriptOfRecords());
			table.setWidths(widths);
			table.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
			
			PdfPCell cell;
			// line 1
			cell = new PdfPCell(new Paragraph("NAME OF SENDING INSTITUTION:"));
			cell.setColspan(5);
			cell.setBorder(PdfPCell.NO_BORDER);
			table.addCell(cell);
			cell = new PdfPCell();
			cell.setBorder(PdfPCell.NO_BORDER);
			cell.setCellEvent(new FieldPositioningEvents(writer, "sending_institution"));
			cell.setColspan(3);
			table.addCell(cell);
			// line 2
			cell = new PdfPCell(new Paragraph("Faculty/Department:"));
			cell.setColspan(5);
			cell.setBorder(PdfPCell.NO_BORDER);
			table.addCell(cell);
			cell = new PdfPCell();
			cell.setBorder(PdfPCell.NO_BORDER);
			cell.setCellEvent(new FieldPositioningEvents(writer, "sending_department"));
			cell.setColspan(3);
			table.addCell(cell);
			// line 3
			cell = new PdfPCell(new Paragraph("ECTS departmental coordinator:"));
			cell.setBorder(PdfPCell.NO_BORDER);
			cell.setColspan(5);
			table.addCell(cell);
			cell = new PdfPCell();
			cell.setBorder(PdfPCell.NO_BORDER);
			cell.setCellEvent(new FieldPositioningEvents(writer, "sending_coordinator"));
			cell.setColspan(3);
			table.addCell(cell);
			// line 4
			cell = new PdfPCell(new Paragraph("Tel:"));
			cell.setBorder(PdfPCell.NO_BORDER);
			table.addCell(cell);
			cell = new PdfPCell();
			cell.setBorder(PdfPCell.NO_BORDER);
			cell.setCellEvent(new FieldPositioningEvents(writer, "sending_tel"));
			cell.setColspan(2);
			table.addCell(cell);
			cell = new PdfPCell(new Paragraph("Fax:"));
			cell.setBorder(PdfPCell.NO_BORDER);
			table.addCell(cell);
			cell = new PdfPCell();
			cell.setBorder(PdfPCell.NO_BORDER);
			cell.setCellEvent(new FieldPositioningEvents(writer, "sending_fax"));
			cell.setColspan(2);
			table.addCell(cell);
			cell = new PdfPCell(new Paragraph("e-mail:"));
			cell.setBorder(PdfPCell.NO_BORDER);
			table.addCell(cell);
			cell = new PdfPCell();
			cell.setBorder(PdfPCell.NO_BORDER);
			cell.setCellEvent(new FieldPositioningEvents(writer, "sending_mail"));
			table.addCell(cell);
			document.add(table);

			table = new PdfPTable(6);
			table.setWidthPercentage(100);
			table.setSpacingBefore(0f);
			table.setSpacingAfter(0f);
			table.setTableEvent(new FoobarTranscriptOfRecords());
			float[] w = { 35, 30, 20, 15, 10, 20};
			table.setWidths(w);
			table.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
			// line 1
			cell = new PdfPCell(new Paragraph("NAME OF STUDENT:"));
			cell.setBorder(PdfPCell.NO_BORDER);
			table.addCell(cell);
			cell = new PdfPCell();
			cell.setBorder(PdfPCell.NO_BORDER);
			cell.setColspan(2);
			cell.setCellEvent(new FieldPositioningEvents(writer, "student_familyname"));
			table.addCell(cell);
			cell = new PdfPCell(new Paragraph("first name:"));
			cell.setBorder(PdfPCell.NO_BORDER);
			table.addCell(cell);
			cell = new PdfPCell();
			cell.setBorder(PdfPCell.NO_BORDER);
			cell.setColspan(2);
			cell.setCellEvent(new FieldPositioningEvents(writer, "student_firstname"));
			table.addCell(cell);
			// line 2
			cell = new PdfPCell(new Paragraph("Date and place of birth:"));
			cell.setBorder(PdfPCell.NO_BORDER);
			table.addCell(cell);
			cell = new PdfPCell();
			cell.setBorder(PdfPCell.NO_BORDER);
			cell.setCellEvent(new FieldPositioningEvents(writer, "student_birth"));
			cell.setColspan(3);
			table.addCell(cell);
			cell = new PdfPCell(new Paragraph("sex:"));
			cell.setBorder(PdfPCell.NO_BORDER);
			table.addCell(cell);
			cell = new PdfPCell();
			cell.setBorder(PdfPCell.NO_BORDER);
			cell.setCellEvent(new FieldPositioningEvents(writer, "student_sex"));
			table.addCell(cell);
			// line 3
			cell = new PdfPCell(new Paragraph("Matriculation date:"));
			cell.setBorder(PdfPCell.NO_BORDER);
			table.addCell(cell);
			cell = new PdfPCell();
			cell.setBorder(PdfPCell.NO_BORDER);
			cell.setCellEvent(new FieldPositioningEvents(writer, "student_matriculation_date"));
			table.addCell(cell);
			cell = new PdfPCell(new Paragraph("Matriculation number:"));
			cell.setColspan(2);
			cell.setBorder(PdfPCell.NO_BORDER);
			table.addCell(cell);
			cell = new PdfPCell();
			cell.setBorder(PdfPCell.NO_BORDER);
			cell.setCellEvent(new FieldPositioningEvents(writer, "student_matriculation_number"));
			cell.setColspan(2);
			table.addCell(cell);
			document.add(table);

			table = new PdfPTable(8);
			table.setWidthPercentage(100);
			table.setSpacingBefore(0f);
			table.setSpacingAfter(0f);
			table.setTableEvent(new FoobarTranscriptOfRecords());
			table.setWidths(widths);
			table.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
			// line 1
			cell = new PdfPCell(new Paragraph("NAME OF RECEIVING INSTITUTION:"));
			cell.setColspan(5);
			cell.setBorder(PdfPCell.NO_BORDER);
			table.addCell(cell);
			cell = new PdfPCell();
			cell.setBorder(PdfPCell.NO_BORDER);
			cell.setCellEvent(new FieldPositioningEvents(writer, "receiving_institution"));
			cell.setColspan(3);
			table.addCell(cell);
			// line 2
			cell = new PdfPCell(new Paragraph("Faculty/Department:"));
			cell.setColspan(5);
			cell.setBorder(PdfPCell.NO_BORDER);
			table.addCell(cell);
			cell = new PdfPCell();
			cell.setBorder(PdfPCell.NO_BORDER);
			cell.setCellEvent(new FieldPositioningEvents(writer, "receiving_department"));
			cell.setColspan(3);
			table.addCell(cell);
			// line 3
			cell = new PdfPCell(new Paragraph("ECTS departmental coordinator:"));
			cell.setBorder(PdfPCell.NO_BORDER);
			cell.setColspan(5);
			table.addCell(cell);
			cell = new PdfPCell();
			cell.setBorder(PdfPCell.NO_BORDER);
			cell.setCellEvent(new FieldPositioningEvents(writer, "receiving_coordinator"));
			cell.setColspan(3);
			table.addCell(cell);
			// line 4
			cell = new PdfPCell(new Paragraph("Tel:"));
			cell.setBorder(PdfPCell.NO_BORDER);
			table.addCell(cell);
			cell = new PdfPCell();
			cell.setBorder(PdfPCell.NO_BORDER);
			cell.setCellEvent(new FieldPositioningEvents(writer, "receiving_tel"));
			cell.setColspan(2);
			table.addCell(cell);
			cell = new PdfPCell(new Paragraph("Fax:"));
			cell.setBorder(PdfPCell.NO_BORDER);
			table.addCell(cell);
			cell = new PdfPCell();
			cell.setBorder(PdfPCell.NO_BORDER);
			cell.setCellEvent(new FieldPositioningEvents(writer, "receiving_fax"));
			cell.setColspan(2);
			table.addCell(cell);
			cell = new PdfPCell(new Paragraph("e-mail:"));
			cell.setBorder(PdfPCell.NO_BORDER);
			table.addCell(cell);
			cell = new PdfPCell();
			cell.setBorder(PdfPCell.NO_BORDER);
			cell.setCellEvent(new FieldPositioningEvents(writer, "receiving_mail"));
			table.addCell(cell);
			document.add(table);
			

			table = new PdfPTable(6);
			table.setWidthPercentage(100);
			float[] width = { 3, 12, 3, 2, 2, 2 };
			table.setWidths(width);
			table.setSpacingBefore(20);
			table.setTableEvent(new FoobarLearningAgreement());
			table.getDefaultCell().setBorder(PdfPCell.BOX);
			table.addCell("Course Unit code (1)");
			table.addCell("Title of the course unit");
			table.addCell("Duration of course unit (2)");
			table.addCell("Local grade (3)");
			table.addCell("ECTS grade (4)");
			table.addCell("ECTS credits (5)");
			for (int i = 0; i < 18; i++) {
				cell = new PdfPCell();
				cell.setFixedHeight(14);
				cell.setBorder(PdfPCell.RIGHT);
				cell.setCellEvent(new FieldPositioningEvents(writer, "course_code_" + i));
				table.addCell(cell);
				cell = new PdfPCell();
				cell.setFixedHeight(14);
				cell.setBorder(PdfPCell.RIGHT);
				cell.setCellEvent(new FieldPositioningEvents(writer, "course_title_" + i));
				table.addCell(cell);
				cell = new PdfPCell();
				cell.setFixedHeight(14);
				cell.setBorder(PdfPCell.RIGHT);
				cell.setCellEvent(new FieldPositioningEvents(writer, "course_duration_" + i));
				table.addCell(cell);
				cell = new PdfPCell();
				cell.setFixedHeight(14);
				cell.setBorder(PdfPCell.RIGHT);
				cell.setCellEvent(new FieldPositioningEvents(writer, "course_localgrade_" + i));
				table.addCell(cell);
				cell = new PdfPCell();
				cell.setFixedHeight(14);
				cell.setBorder(PdfPCell.RIGHT);
				cell.setCellEvent(new FieldPositioningEvents(writer, "course_ectsgrade_" + i));
				table.addCell(cell);
				cell = new PdfPCell();
				cell.setFixedHeight(14);
				cell.setBorder(PdfPCell.NO_BORDER);
				cell.setCellEvent(new FieldPositioningEvents(writer, "course_credits_" + i));
				table.addCell(cell);
			};
			table.getDefaultCell().setBorder(PdfPCell.RIGHT);
			table.addCell("");
			table.addCell("");
			table.addCell("");
			table.addCell("");
			table.addCell("");
			table.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
			table.addCell("Total:");
			table.getDefaultCell().setBorder(PdfPCell.RIGHT);
			table.addCell("");
			cell = new PdfPCell();
			cell.setFixedHeight(14);
			cell.setBorder(PdfPCell.RIGHT);
			cell.setCellEvent(new FieldPositioningEvents(writer, "continued"));
			table.addCell(cell);
			table.addCell("");
			table.addCell("");
			table.addCell("");
			cell = new PdfPCell();
			cell.setFixedHeight(14);
			cell.setBorder(PdfPCell.NO_BORDER);
			cell.setCellEvent(new FieldPositioningEvents(writer, "total_credits"));
			table.addCell(cell);
			document.add(table);
			
			document.add(new Paragraph("(1) (2) (3) (4) (5) see explanation on back page"));
			document.add(Chunk.NEWLINE);
			Chunk degree = new Chunk("                                                                                          ");
			degree.setGenericTag("degree");
			p = new Paragraph("Diploma/Degree awarded: ");
			p.add(degree);
			document.add(p);
			document.add(Chunk.NEWLINE);
			document.add(new Paragraph("Date          Signature of registrar/dean/administration officer           Stamp of institution"));
			document.add(Chunk.NEWLINE);
			document.add(Chunk.NEWLINE);
			document.add(Chunk.NEWLINE);
			document.add(Chunk.NEWLINE);
			document.add(Chunk.NEWLINE);
			document.add(new Paragraph("NB: This document is not valid without the signature of the registrat/dean/administration officer and the official stamp of the institution.", smallFont));
		} catch (DocumentException de) {
			System.err.println(de.getMessage());
		} catch (IOException ioe) {
			System.err.println(ioe.getMessage());
		}

		// step 5: we close the document
		document.close();
	}

	/**
	 * @see com.lowagie.text.pdf.PdfPTableEvent#tableLayout(com.lowagie.text.pdf.PdfPTable, float[][], float[], int, int, com.lowagie.text.pdf.PdfContentByte[])
	 */
	public void tableLayout(PdfPTable table, float[][] width, float[] heights, int headerRows, int rowStart, PdfContentByte[] canvases) {
		float widths[] = width[0];
        PdfContentByte cb = canvases[PdfPTable.TEXTCANVAS];
        cb.rectangle(widths[0], heights[heights.length - 1], widths[widths.length - 1] - widths[0], heights[0] - heights[heights.length - 1]);
        cb.stroke();
	}
}