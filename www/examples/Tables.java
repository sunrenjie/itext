// this is a test class written by Alan Soukup and Bruno Lowagie

import java.io.*;
import java.awt.Color;
// imports for PDF
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;

/**
 * Test for iText 30
 */

public class Tables {

	public static void main(String[] args) {
		// creation of the document with a certain size and certain margins
		Document document = new Document(PageSize.A4.rotate(), 50, 50, 50, 50);

		try {
			// creation of the different writers
			PdfWriter.getInstance(document, new FileOutputStream("tables.pdf"));

			// we add some meta information to the document
            document.addAuthor("Alan Soukup");
            document.addSubject("This is the result of a Test.");

            document.open();

            Table datatable = new Table(10);

            datatable.setCellpadding(0);
			datatable.setCellspacing(3);
            datatable.setBorder(Rectangle.NO_BORDER);
            int headerwidths[] = {10, 30, 15, 15, 5, 5, 5, 5, 5, 5};
            datatable.setWidths(headerwidths);
            datatable.setWidth(100);

			// the first cell spans 10 columns
            Cell cell = new Cell(new Phrase("Administration -System Users Report", new Font(Font.HELVETICA, 24, Font.BOLD)));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setLeading(30);
            cell.setColspan(10);
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
            datatable.addCell(cell);

			// These cells span 2 rows
            datatable.setDefaultCellBorderWidth(2);
            datatable.setDefaultHorizontalAlignment(1);
            datatable.setDefaultRowspan(2);
            datatable.addCell("User Id");
			datatable.addCell(new Phrase("Name(last, first, middle)", new Font(Font.HELVETICA, 14, Font.BOLD)));
            datatable.addCell("Company");
			datatable.addCell("Department");

			// This cell spans the remaining 6 columns in 1 row
            datatable.setDefaultRowspan(1);
            datatable.setDefaultColspan(6);
            datatable.addCell("Permissions");

			// These cells span 1 row and 1 column
            datatable.setDefaultColspan(1);
            datatable.addCell("Admin");
            datatable.addCell("Data");
            datatable.addCell("Expl");
            datatable.addCell("Prod");
            datatable.addCell("Proj");
            datatable.addCell("Online");

			// this is the end of the table header
			datatable.endHeaders();

            datatable.setDefaultCellBorderWidth(1);
            datatable.setDefaultRowspan(1);

            for (int i = 1; i < 30; i++) {
				
				if (i % 2 == 1) {
					datatable.setDefaultCellGrayFill(0.9f);
				}

				datatable.addCell("myUserId");
				datatable.addCell("Somebody with a very, very, very, very, very, very, very, very, very long long name");
				datatable.addCell("No Name Company");
				datatable.addCell("Departmentless");

				datatable.setDefaultHorizontalAlignment(Element.ALIGN_CENTER);
				datatable.addCell("No");
				datatable.addCell("Yes");
				datatable.addCell("No");
				datatable.addCell("Yes");
				datatable.addCell("No");
				datatable.addCell("Yes");
				datatable.setDefaultHorizontalAlignment(Element.ALIGN_LEFT);

				if (i % 2 == 1) {
					datatable.setDefaultCellGrayFill(0.0f);
				}
			}


			document.add(datatable);
		}
		catch(Exception e) {
			e.printStackTrace();
		}

		// we close the document
		document.close();
	}
}
