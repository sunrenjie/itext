/*
 * $Id$
 *
 * This code is part of the 'iText Tutorial'.
 * You can find the complete tutorial at the following address:
 * http://itextdocs.lowagie.com/tutorial/
 *
 * This code is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 *
 * itext-questions@lists.sourceforge.net
 */

package com.lowagie.examples.forms.create;


import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.*;
import com.lowagie.text.pdf.TextField;

import java.awt.*;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Generates a StudentCard as a form
 * @author blowagie
 */
public class StudentCardFormStructure implements PdfPCellEvent {

	/** the writer with the acroform */
	private PdfFormField field;

	/**
	 * Construct an implementation of PdfPCellEvent.
	 *
	 * @param field
	 *            a form field
	 *
	 */
	public StudentCardFormStructure(PdfFormField field) {
		this.field = field;
	}

	/**
	 * @see com.lowagie.text.pdf.PdfPCellEvent#cellLayout(com.lowagie.text.pdf.PdfPCell,
	 *      com.lowagie.text.Rectangle, com.lowagie.text.pdf.PdfContentByte[])
	 */
	public void cellLayout(PdfPCell cell, Rectangle position,
			PdfContentByte[] canvases) {
		field.setWidget(position, null);
	}

    /**
     * Generates a StudentCard as a form
     * @param args no arguments needed here
     */
    public static void main(String[] args) {

        System.out.println("StudentCard as a form");

        // step 1: creation of a document-object
        Rectangle rect = new Rectangle(243, 153);
        rect.setBackgroundColor(new Color(0xFF, 0xFF, 0xCC));
        Document document = new Document(rect, 10, 10, 10, 10);

        try {

            // step 2:
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("studentcardformStructure.pdf"));

            // step 2.5: set the writer to use structure tags.  This must be done
            // BEFORE we call document.open().
            writer.setTagged();

            writer.setTabs( PdfName.S ); // *S*tructure order

            // step 3: we open the document
            document.open();

            // step 4: Set up structure (AKA Marked Content)

            PdfStructureTreeRoot root = writer.getStructureTreeRoot();

            PdfStructureElement page = new PdfStructureElement( root, new PdfName( "Part") );
            page.put( PdfName.T, new PdfString("Page 1" ) );
            // calling page.getMCID() will assign it one as needed.
            root.setPagesMCID(1, page.getMCID() );
            PdfStructureElement curFldStruct = null;

            // step 5:
            Font font = FontFactory.getFont(FontFactory.HELVETICA, 14, Font.BOLD, Color.BLUE);
            Paragraph p = new Paragraph("Ghent University", font);
            p.setAlignment(Element.ALIGN_CENTER);
            document.add(p);
            Font f = FontFactory.getFont(FontFactory.HELVETICA, 8);
            PdfPTable outertable = new PdfPTable(3);
            outertable.setTotalWidth(200);
            outertable.getDefaultCell().setBorder(Rectangle.NO_BORDER);
            float[] outer = { 60, 25, 15 };
            outertable.setWidths(outer);
            PdfPTable innertable = new PdfPTable(2);
            float[] inner = {35, 65};
            innertable.setWidths(inner);
            PdfPCell cell;
            TextField text;
            innertable.addCell(new Paragraph("name:", f));
            cell = new PdfPCell();
            text = new TextField(writer, new Rectangle(0, 0), "name");

            text.setOptions(TextField.MULTILINE);
            text.setFontSize(8);

            PdfFormField name = text.getTextField();

            // reading order is defined by the order in which children are added to parents.
            // it has nothing to to with the MCIDs.
            curFldStruct = new PdfStructureElement(page, PdfName.FORM);
            curFldStruct.put( PdfName.T, new PdfString("name" ) );
            curFldStruct.getMCID();
            name.setMarkedObject( curFldStruct );

            cell.setCellEvent(new StudentCardForm(name));
            innertable.addCell(cell);
            innertable.addCell(new Paragraph("date of birth:", f));
            cell = new PdfPCell();
            text = new TextField(writer, new Rectangle(0, 0), "birthday");
            text.setOptions(TextField.MULTILINE);
            text.setFontSize(8);
            PdfFormField birthdate = text.getTextField();

            curFldStruct = new PdfStructureElement(page, PdfName.FORM);
            curFldStruct.put( PdfName.T, new PdfString( "date of birth" ) );
            curFldStruct.getMCID();
            birthdate.setMarkedObject( curFldStruct );
            curFldStruct.setMarkedObject(birthdate.getIndRef(), null);

            cell.setCellEvent(new StudentCardForm(birthdate));
            innertable.addCell(cell);
            innertable.addCell(new Paragraph("Study Program:", f));
            cell = new PdfPCell();
            text = new TextField(writer, new Rectangle(0, 0), "studyprogram");
            text.setOptions(TextField.MULTILINE);
            text.setFontSize(8);
            PdfFormField studyprogram = text.getTextField();

            curFldStruct = new PdfStructureElement(page, PdfName.FORM);
            curFldStruct.put(PdfName.T, new PdfString("Study Program:"));
            curFldStruct.getMCID();
            studyprogram.setMarkedObject( curFldStruct );

            cell.setCellEvent(new StudentCardForm(studyprogram));
            innertable.addCell(cell);
            innertable.addCell(new Paragraph("option:", f));
            cell = new PdfPCell();
            text = new TextField(writer, new Rectangle(0, 0), "option");
            text.setOptions(TextField.MULTILINE);
            text.setFontSize(8);
            PdfFormField option = text.getTextField();

            curFldStruct = new PdfStructureElement( page, PdfName.FORM );
            curFldStruct.put(PdfName.T, new PdfString("option"));
            curFldStruct.getMCID();
            option.setMarkedObject( curFldStruct );

            cell.setCellEvent(new StudentCardForm(option));
            innertable.addCell(cell);
            outertable.addCell(innertable);
            cell = new PdfPCell();
			cell.setBackgroundColor(new Color(0xFF, 0xDE, 0xAD));
            PdfFormField picture = PdfFormField.createPushButton(writer);
            picture.setFieldName("picture");
            cell.setCellEvent(new StudentCardForm(picture));
            outertable.addCell(cell);
            cell = new PdfPCell();
			cell.setBackgroundColor(Color.WHITE);
            PdfFormField barcode = PdfFormField.createPushButton(writer);
            barcode.setFieldName("barcode");

            curFldStruct = new PdfStructureElement( page, PdfName.FORM );
            curFldStruct.put(PdfName.T, new PdfString("barcode"));
            curFldStruct.getMCID();
            barcode.setMarkedObject( curFldStruct );

            cell.setCellEvent(new StudentCardForm(barcode));
            outertable.addCell(cell);
            outertable.writeSelectedRows(0, -1, 20, 100, writer.getDirectContent());
            writer.addAnnotation(name);
            writer.addAnnotation(birthdate);
            writer.addAnnotation(studyprogram);
            writer.addAnnotation(option);
            writer.addAnnotation(picture);
            writer.addAnnotation(barcode);
        }
        catch(DocumentException de) {
            System.err.println(de.getMessage());
        }
        catch(IOException ioe) {
            System.err.println(ioe.getMessage());
        }

        // step 5: we close the document
        document.close();
    }
}