/*
 * This class is part of the book "iText in Action - 2nd Edition"
 * written by Bruno Lowagie (ISBN: 9781935182610)
 * For more info, go to: http://itextpdf.com/examples/
 * This example only works with the AGPL version of iText.
 */

package part2.chapter08;

import com.itextpdf.text.ExceptionConverter;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfAnnotation;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfFormField;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPCellEvent;

public class ChildFieldEvent implements PdfPCellEvent {

    protected PdfFormField parent;
    protected PdfFormField kid;
    protected float padding;
    
    public ChildFieldEvent(PdfFormField parent, PdfFormField kid, float padding) {
        this.parent = parent;
        this.kid = kid;
        this.padding = padding;
    }
    
    /**
     * @see com.lowagie.text.pdf.PdfPCellEvent#cellLayout(com.lowagie.text.pdf.PdfPCell,
     *      com.lowagie.text.Rectangle, com.lowagie.text.pdf.PdfContentByte[])
     */
    public void cellLayout(PdfPCell cell, Rectangle rect, PdfContentByte[] cb) {
        try {
            parent.addKid(kid);
            kid.setWidget(new Rectangle(rect.getLeft(padding), rect.getBottom(padding),
                    rect.getRight(padding), rect.getTop(padding)),
                    PdfAnnotation.HIGHLIGHT_INVERT);
        } catch (Exception e) {
            throw new ExceptionConverter(e);
        }
    }
}
