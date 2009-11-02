/*
 * Copyright 2009 by Kevin Day.
 *
 * The contents of this file are subject to the Mozilla Public License Version 1.1
 * (the "License"); you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at http://www.mozilla.org/MPL/
 *
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 * for the specific language governing rights and limitations under the License.
 *
 * The Original Code is 'iText, a free JAVA-PDF library'.
 *
 * The Initial Developer of the Original Code is Bruno Lowagie. Portions created by
 * the Initial Developer are Copyright (C) 1999-2008 by Bruno Lowagie.
 * All Rights Reserved.
 * Co-Developer of the code is Paulo Soares. Portions created by the Co-Developer
 * are Copyright (C) 2000-2008 by Paulo Soares. All Rights Reserved.
 *
 * Contributor(s): all the names of the contributors are added in the source code
 * where applicable.
 *
 * Alternatively, the contents of this file may be used under the terms of the
 * LGPL license (the "GNU LIBRARY GENERAL PUBLIC LICENSE"), in which case the
 * provisions of LGPL are applicable instead of those above.  If you wish to
 * allow use of your version of this file only under the terms of the LGPL
 * License and not to allow others to use your version of this file under
 * the MPL, indicate your decision by deleting the provisions above and
 * replace them with the notice and other provisions required by the LGPL.
 * If you do not delete the provisions above, a recipient may use your version
 * of this file under either the MPL or the GNU LIBRARY GENERAL PUBLIC LICENSE.
 *
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the MPL as stated above or under the terms of the GNU
 * Library General Public License as published by the Free Software Foundation;
 * either version 2 of the License, or any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Library general Public License for more
 * details.
 *
 * If you didn't download this code from the following link, you should check if
 * you aren't using an obsolete version:
 * http://www.lowagie.com/iText/
 */
package com.lowagie.text.pdf.parser;


/**
 * A simple text extraction renderer.
 * 
 * This renderer keeps track of the current Y position of each string.  If it detects
 * that the y position has changed, it inserts a line break into the output.  If the
 * PDF renders text in a non-top-to-bottom fashion, this will result in the text not
 * being a true representation of how it appears in the PDF.
 * 
 * This renderer also uses a simple strategy based on the font metrics to determine if
 * a blank space should be inserted into the output.
 * 
 * @since	2.1.5
 */
public class SimpleTextExtractingPdfContentRenderListener implements TextProvidingRenderListener {

    /** keeps track of the Y position of the last rendered text */
    private float lastYPos;
    /** keeps track of the X position of the end of the last rendered text */
    private float lastEndingXPos;

    /** used to store the resulting String. */
    private StringBuffer result;

    /**
     * Creates a new text extraction renderer.
     */
    public SimpleTextExtractingPdfContentRenderListener() {
        reset();
    }

    public void reset() {
        lastYPos = 0f;
        lastEndingXPos = 0f;
        result = new StringBuffer();
    }
    
    /**
     * Returns the result so far.
     * @return	a String with the resulting text.
     */
    public String getResultantText(){
        return result.toString();
    }
    /**
     * Writes text to the result.
     * @param text  The text that needs to be displayed
     * @param gs    The current graphics state, including the current font and various spacings needed to compute glyph widths
     * @param renderInto The rectangle that the result will be rendered into
     * @param spaceWidth The scaled width of a space character in the current font
     * @see com.lowagie.text.pdf.parser.RenderListener#renderText(String, GraphicsState, Rectangle)
     */

    /**
     * Captures text using a simplified algorithm for inserting hard returns and spaces
     * @see com.lowagie.text.pdf.parser.AbstractRenderListener#renderText(java.lang.String, com.lowagie.text.pdf.parser.GraphicsState, com.lowagie.text.pdf.parser.Matrix, com.lowagie.text.pdf.parser.Matrix)
     */
    public void renderText(String text, GraphicsState gs, Matrix textMatrix, Matrix endingTextMatrix) {
        boolean firstRender = result.length() == 0;
        boolean hardReturn = false;

        float x1 = textMatrix.get(Matrix.I31);
        float x2 = endingTextMatrix.get(Matrix.I31);
        int y1 = (int)textMatrix.get(Matrix.I32);
        
        float sameLineThreshold = 0.1f; // technically, we should base this on the current font metrics
        if (Math.abs(y1 - lastYPos) > sameLineThreshold && !firstRender)
            hardReturn = true;

        if (hardReturn){
            //System.out.println("<Hard Return>");
            result.append('\n');
        } else if (!firstRender){
            
            float spaceGlyphWidth = gs.font.getWidth(' ')/1000f;
            float spaceWidth = (spaceGlyphWidth * gs.fontSize + gs.characterSpacing + gs.wordSpacing) * gs.horizontalScaling; // this is unscaled!!
            Matrix scaled = new Matrix(spaceWidth, 0).multiply(textMatrix);
            float scaledSpaceWidth = scaled.get(Matrix.I31) - textMatrix.get(Matrix.I31);
            
            if (x1 - lastEndingXPos > scaledSpaceWidth/2f ){
                //System.out.println("<Implied space on text '" + text + "'> lastEndingXPos=" + lastEndingXPos + ", x1=" + x1 + ", scaledSpaceWidth=" + scaledSpaceWidth);
                result.append(' ');
            }
        } else {
            //System.out.println("Displaying first string of content '" + text + "' :: currentX = " + currentX);
        }
        
        // System.out.println("After displaying '" + text + "' :: Start at " + x1 + " end at " + x2);
        
        result.append(text);

        lastYPos = y1;
        lastEndingXPos = x2;
        
    }

}
