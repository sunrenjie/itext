/*
 * $Id: ContentOperator.java 4242 2010-01-02 23:22:20Z xlv $
 *
 * This file is part of the iText project.
 * Copyright (c) 1998-2009 1T3XT BVBA
 * Authors: Bruno Lowagie, et al.
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License version 3
 * as published by the Free Software Foundation with the addition of the
 * following permission added to Section 15 as permitted in Section 7(a):
 * FOR ANY PART OF THE COVERED WORK IN WHICH THE COPYRIGHT IS OWNED BY 1T3XT,
 * 1T3XT DISCLAIMS THE WARRANTY OF NON INFRINGEMENT OF THIRD PARTY RIGHTS.
 *
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Affero General Public License for more details.
 * You should have received a copy of the GNU Affero General Public License
 * along with this program; if not, see http://www.gnu.org/licenses or write to
 * the Free Software Foundation, Inc., 51 Franklin Street, Fifth Floor,
 * Boston, MA, 02110-1301 USA, or download the license from the following URL:
 * http://itextpdf.com/terms-of-use/
 *
 * The interactive user interfaces in modified source and object code versions
 * of this program must display Appropriate Legal Notices, as required under
 * Section 5 of the GNU Affero General Public License.
 *
 * In accordance with Section 7(b) of the GNU Affero General Public License,
 * you must retain the producer line in every PDF that is created or manipulated
 * using iText.
 *
 * You can be released from the requirements of the license by purchasing
 * a commercial license. Buying such a license is mandatory as soon as you
 * develop commercial activities involving the iText software without
 * disclosing the source code of your own applications.
 * These activities include: offering paid services to customers as an ASP,
 * serving PDFs on the fly in a web application, shipping iText with a closed
 * source product.
 *
 * For more information, please contact iText Software Corp. at this
 * address: sales@itextpdf.com
 */
package com.itextpdf.text.pdf.parser;

/**
 * Allows you to find the rectangle that contains all the text in a page.
 * @since 5.0.2
 */
public class TextMarginFinder implements RenderListener {
	/** The left margin. */
	protected float llx = Float.MAX_VALUE;
	/** The bottom margin. */
	protected float lly = Float.MAX_VALUE;
	/** The right margin. */
	protected float urx = Float.MIN_VALUE;
	/** The top margin. */
	protected float ury = Float.MIN_VALUE;

	/**
	 * Resets all margin values.
	 * @see com.itextpdf.text.pdf.parser.RenderListener#reset()
	 */
	public void reset() {
		llx = Float.MAX_VALUE;
		lly = Float.MAX_VALUE;
		urx = Float.MIN_VALUE;
		ury = Float.MIN_VALUE;
	}

	/**
	 * Method invokes by the PdfContentStreamProcessor.
	 * Passes a TextRenderInfo for every text chunk that is encountered.
	 * We'll use this object to obtain coordinates.
	 * @return TextRenderInfo object containing information about the text.
	 * @see com.itextpdf.text.pdf.parser.RenderListener#renderText(com.itextpdf.text.pdf.parser.TextRenderInfo)
	 */
	public void renderText(TextRenderInfo renderInfo) {
		LineSegment lower = renderInfo.getLineSegment(TextRenderInfo.LinePos.DESCENT);
		LineSegment upper = renderInfo.getLineSegment(TextRenderInfo.LinePos.ASCENT);
		float tmp1 = Math.min(lower.getStartPoint().get(Vector.I1), lower.getEndPoint().get(Vector.I1));
		float tmp2 = Math.min(upper.getStartPoint().get(Vector.I1), upper.getEndPoint().get(Vector.I1));
		llx = Math.min(llx, Math.min(tmp1, tmp2));
		tmp1 = Math.min(lower.getStartPoint().get(Vector.I2), lower.getEndPoint().get(Vector.I2));
		tmp2 = Math.min(upper.getStartPoint().get(Vector.I2), upper.getEndPoint().get(Vector.I2));
		lly = Math.min(lly, Math.min(tmp1, tmp2));
		tmp1 = Math.max(lower.getStartPoint().get(Vector.I1), lower.getEndPoint().get(Vector.I1));
		tmp2 = Math.max(upper.getStartPoint().get(Vector.I1), upper.getEndPoint().get(Vector.I1));
		urx = Math.max(urx, Math.max(tmp1, tmp2));
		tmp1 = Math.max(lower.getStartPoint().get(Vector.I2), lower.getEndPoint().get(Vector.I2));
		tmp2 = Math.max(upper.getStartPoint().get(Vector.I2), upper.getEndPoint().get(Vector.I2));
		ury = Math.max(ury, Math.max(tmp1, tmp2));
	}

	/**
	 * Getter for the left margin.
	 * @return the left margin
	 */
	public float getLlx() {
		return llx;
	}

	/**
	 * Getter for the bottom margin.
	 * @return the bottom margin
	 */
	public float getLly() {
		return lly;
	}

	/**
	 * Getter for the right margin.
	 * @return the right margin
	 */
	public float getUrx() {
		return urx;
	}

	/**
	 * Getter for the top margin.
	 * @return the top margin
	 */
	public float getUry() {
		return ury;
	}

	/**
	 * Gets the width of the text block.
	 * @return a width
	 */
	public float getWidth() {
		return urx - llx;
	}
	
	/**
	 * Gets the height of the text block.
	 * @return a height
	 */
	public float getHeight() {
		return ury - lly;
	}
	
	/**
	 * @see com.itextpdf.text.pdf.parser.RenderListener#beginTextBlock()
	 */
	public void beginTextBlock() {
	}

	/**
	 * @see com.itextpdf.text.pdf.parser.RenderListener#endTextBlock()
	 */
	public void endTextBlock() {
	}

	/**
	 * @see com.itextpdf.text.pdf.parser.RenderListener#renderImage(com.itextpdf.text.pdf.parser.ImageRenderInfo)
	 */
	public void renderImage(ImageRenderInfo renderInfo) {
	}
}
