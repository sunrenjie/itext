/*
 * Copyright 2002 by Paulo Soares.
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
 * the Initial Developer are Copyright (C) 1999, 2000, 2001, 2002 by Bruno Lowagie.
 * All Rights Reserved.
 * Co-Developer of the code is Paulo Soares. Portions created by the Co-Developer
 * are Copyright (C) 2000, 2001, 2002 by Paulo Soares. All Rights Reserved.
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

package com.lowagie.text.pdf;
import com.lowagie.text.Rectangle;
import java.util.ArrayList;
import java.util.Iterator;
import java.awt.Color;

/** Implements form fields.
 *
 * @author Paulo Soares (psoares@consiste.pt)
 */
public class PdfFormField extends PdfAnnotation {

    public static final int FF_READ_ONLY = 1;
    public static final int FF_REQUIRED = 2;
    public static final int FF_NO_EXPORT = 4;
    public static final int FF_NO_TOGGLE_TO_OFF = 16384;
    public static final int FF_RADIO = 32768;
    public static final int FF_PUSHBUTTON = 65536;
    public static final int FF_MULTILINE = 4096;
    public static final int FF_PASSWORD = 8192;
    public static final int FF_COMBO = 131072;
    public static final int FF_EDIT = 262144;
    public static final int Q_LEFT = 0;
    public static final int Q_CENTER = 1;
    public static final int Q_RIGHT = 2;
    public static final int MK_NO_ICON = 0;
    public static final int MK_NO_CAPTION = 1;
    public static final int MK_CAPTION_BELOW = 2;
    public static final int MK_CAPTION_ABOVE = 3;
    public static final int MK_CAPTION_RIGHT = 4;
    public static final int MK_CAPTION_LEFT = 5;
    public static final int MK_CAPTION_OVERLAID = 6;
    public static final PdfName IF_SCALE_ALWAYS = PdfName.A;
    public static final PdfName IF_SCALE_BIGGER = PdfName.B;
    public static final PdfName IF_SCALE_SMALLER = PdfName.S;
    public static final PdfName IF_SCALE_NEVER = PdfName.N;
    public static final PdfName IF_SCALE_ANAMORPHIC = PdfName.A;
    public static final PdfName IF_SCALE_PROPORTIONAL = PdfName.P;
    public static final boolean MULTILINE = true;
    public static final boolean SINGLELINE = false;
    public static final boolean PLAINTEXT = false;
    public static final boolean PASSWORD = true;
    static PdfName mergeTarget[] = {PdfName.FONT, PdfName.XOBJECT, PdfName.COLORSPACE, PdfName.PATTERN};
    
    /** Holds value of property parent. */
    protected PdfFormField parent;
    
    protected ArrayList kids;
    
/**
 * Constructs a new <CODE>PdfAnnotation</CODE> of subtype link (Action).
 */
    
    public PdfFormField(PdfWriter writer, float llx, float lly, float urx, float ury, PdfAction action) {
        super(writer, llx, lly, urx, ury, action);
        put(PdfName.TYPE, PdfName.ANNOT);
        put(PdfName.SUBTYPE, PdfName.WIDGET);
        annotation = true;
    }

    /** Creates new PdfFormField */
    protected PdfFormField(PdfWriter writer) {
        super(writer, null);
        form = true;
        annotation = false;
    }
    
    public void setWidget(Rectangle rect, PdfName highlight) {
        put(PdfName.TYPE, PdfName.ANNOT);
        put(PdfName.SUBTYPE, PdfName.WIDGET);
        put(PdfName.RECT, new PdfRectangle(rect));
        annotation = true;
        if (!highlight.equals(HIGHLIGHT_INVERT))
            put(PdfName.H, highlight);
    }
    
    public static PdfFormField createEmpty(PdfWriter writer) {
        PdfFormField field = new PdfFormField(writer);
        return field;
    }
    
    public void setButton(int flags) {
        put(PdfName.FT, PdfName.BTN);
        if (flags != 0)
            put(PdfName.FF, new PdfNumber(flags));
    }
    
    protected static PdfFormField createButton(PdfWriter writer, int flags) {
        PdfFormField field = new PdfFormField(writer);
        field.setButton(flags);
        return field;
    }
    
    public static PdfFormField createPushButton(PdfWriter writer) {
        return createButton(writer, FF_PUSHBUTTON);
    }

    public static PdfFormField createCheckBox(PdfWriter writer) {
        return createButton(writer, 0);
    }

    public static PdfFormField createRadioButton(PdfWriter writer, boolean noToggleToOff) {
        return createButton(writer, FF_RADIO + (noToggleToOff ? FF_NO_TOGGLE_TO_OFF : 0));
    }
    
    public static PdfFormField createTextField(PdfWriter writer, boolean multiline, boolean password, int maxLen) {
        PdfFormField field = new PdfFormField(writer);
        field.put(PdfName.FT, PdfName.TX);
        int flags = (multiline ? FF_MULTILINE : 0);
        flags += (password ? FF_PASSWORD : 0);
        field.put(PdfName.FF, new PdfNumber(flags));
        if (maxLen > 0)
            field.put(PdfName.MAXLEN, new PdfNumber(maxLen));
        return field;
    }
    
    protected static PdfFormField createChoice(PdfWriter writer, int flags, PdfArray options, int topIndex) {
        PdfFormField field = new PdfFormField(writer);
        field.put(PdfName.FT, PdfName.CH);
        field.put(PdfName.FF, new PdfNumber(flags));
        field.put(PdfName.OPT, options);
        if (topIndex > 0)
            field.put(PdfName.TI, new PdfNumber(topIndex));
        return field;
    }
    
    public static PdfFormField createList(PdfWriter writer, String options[], int topIndex, boolean unicode) {
        return createChoice(writer, 0, processOptions(options, unicode), topIndex);
    }

    public static PdfFormField createList(PdfWriter writer, String options[], int topIndex) {
        return createList(writer, options, topIndex, false);
    }

    public static PdfFormField createList(PdfWriter writer, String options[][], int topIndex, boolean unicode) {
        return createChoice(writer, 0, processOptions(options, unicode), topIndex);
    }

    public static PdfFormField createList(PdfWriter writer, String options[][], int topIndex) {
        return createList(writer, options, topIndex, false);
    }

    public static PdfFormField createCombo(PdfWriter writer, boolean edit, String options[], int topIndex, boolean unicode) {
        return createChoice(writer, FF_COMBO + (edit ? FF_EDIT : 0), processOptions(options, unicode), topIndex);
    }
    
    public static PdfFormField createCombo(PdfWriter writer, boolean edit, String options[], int topIndex) {
        return createCombo(writer, edit, options, topIndex, false);
    }
    
    public static PdfFormField createCombo(PdfWriter writer, boolean edit, String options[][], int topIndex, boolean unicode) {
        return createChoice(writer, FF_COMBO + (edit ? FF_EDIT : 0), processOptions(options, unicode), topIndex);
    }
    
    public static PdfFormField createCombo(PdfWriter writer, boolean edit, String options[][], int topIndex) {
        return createCombo(writer, edit, options, topIndex, false);
    }
    
    protected static PdfArray processOptions(String options[], boolean unicode) {
        String encoding = (unicode ? PdfObject.TEXT_UNICODE : PdfObject.ENCODING);
        PdfArray array = new PdfArray();
        for (int k = 0; k < options.length; ++k) {
            array.add(new PdfString(options[k], encoding));
        }
        return array;
    }
    
    protected static PdfArray processOptions(String options[][], boolean unicode) {
        String encoding = (unicode ? PdfObject.TEXT_UNICODE : PdfObject.ENCODING);
        PdfArray array = new PdfArray();
        for (int k = 0; k < options.length; ++k) {
            String subOption[] = options[k];
            PdfArray ar2 = new PdfArray(new PdfString(subOption[0], encoding));
            ar2.add(new PdfString(subOption[1], encoding));
            array.add(ar2);
        }
        return array;
    }
    
    public static PdfFormField createSignature(PdfWriter writer) {
        PdfFormField field = new PdfFormField(writer);
        field.put(PdfName.FT, PdfName.SIG);
        field.put(PdfName.FF, new PdfNumber(0));
        writer.setSigFlags(PdfWriter.SIGNATURE_EXISTS);
        return field;
    }
    
    /** Getter for property parent.
     * @return Value of property parent.
     */
    public PdfFormField getParent() {
        return parent;
    }
    
    public void addKid(PdfFormField field) {
        field.parent = this;
        if (kids == null)
            kids = new ArrayList();
        kids.add(field);
    }
    
    ArrayList getKids() {
        return kids;
    }
    
    public int setFieldFlags(int flags) {
        PdfNumber obj = (PdfNumber)get(PdfName.FF);
        int old;
        if (obj == null)
            old = 0;
        else
            old = obj.intValue();
        int v = old | flags;
        put(PdfName.FF, new PdfNumber(v));
        return old;
    }
    
    public void setValueAsStringUnicode(String s) {
        put(PdfName.V, new PdfString(s, PdfObject.TEXT_UNICODE));
    }

    public void setValueAsString(String s) {
        put(PdfName.V, new PdfString(s));
    }

    public void setValueAsName(String s) {
        put(PdfName.V, new PdfName(s));
    }

    public void setValue(PdfSignature sig) {
        put(PdfName.V, sig);
    }

    public void setDefaultValueAsString(String s) {
        put(PdfName.DV, new PdfString(s));
    }

    public void setDefaultValueAsStringUnicode(String s) {
        put(PdfName.DV, new PdfString(s, PdfObject.TEXT_UNICODE));
    }

    public void setDefaultValueAsName(String s) {
        put(PdfName.DV, new PdfName(s));
    }
    
    public void setFieldName(String s) {
        put(PdfName.T, new PdfString(s));
    }
    
    public void setUserName(String s) {
        put(PdfName.TU, new PdfString(s));
    }
    
    public void setMappingName(String s) {
        put(PdfName.TM, new PdfString(s));
    }
    
    public void setQuadding(int v) {
        put(PdfName.Q, new PdfNumber(v));
    }
    
    static void mergeResources(PdfDictionary result, PdfDictionary source) {
        PdfDictionary dic = null;
        PdfDictionary res = null;
        PdfName target = null;
        for (int k = 0; k < mergeTarget.length; ++k) {
            target = mergeTarget[k];
            if ((dic = (PdfDictionary)source.get(target)) != null) {
                if ((res = (PdfDictionary)result.get(target)) == null) {
                    res = new PdfDictionary();
                }
                res.merge(dic);
                result.put(target, res);
            }
        }
    }
    
    void setUsed() {
        used = true;
        if (parent != null)
            put(PdfName.PARENT, parent.getIndirectReference());
        if (kids != null) {
            PdfArray array = new PdfArray();
            for (int k = 0; k < kids.size(); ++k)
                array.add(((PdfFormField)kids.get(k)).getIndirectReference());
            put(PdfName.KIDS, array);
        }
        if (templates == null)
            return;
        PdfDictionary dic = new PdfDictionary();
        for (Iterator it = templates.keySet().iterator(); it.hasNext();) {
            PdfTemplate template = (PdfTemplate)it.next();
            mergeResources(dic, (PdfDictionary)template.getResources());
        }
        put(PdfName.DR, dic);
    }

    PdfDictionary getMK() {
        PdfDictionary mk = (PdfDictionary)get(PdfName.MK);
        if (mk == null) {
            mk = new PdfDictionary();
            put(PdfName.MK, mk);
        }
        return mk;
    }
    
    public void setMKRotation(int rotation) {
        getMK().put(PdfName.R, new PdfNumber(rotation));
    }
    
    PdfArray getMKColor(Color color) {
        PdfArray array = new PdfArray();
        int type = ExtendedColor.getType(color);
        switch (type) {
            case ExtendedColor.TYPE_GRAY: {
                array.add(new PdfNumber(((GrayColor)color).getGray()));
                break;
            }
            case ExtendedColor.TYPE_CMYK: {
                CMYKColor cmyk = (CMYKColor)color;
                array.add(new PdfNumber(cmyk.getCyan()));
                array.add(new PdfNumber(cmyk.getMagenta()));
                array.add(new PdfNumber(cmyk.getYellow()));
                array.add(new PdfNumber(cmyk.getBlack()));
                break;
            }
            case ExtendedColor.TYPE_SEPARATION:
            case ExtendedColor.TYPE_PATTERN:
            case ExtendedColor.TYPE_SHADING:
                throw new RuntimeException("Separations, patterns and shadings are not allowed in MK dictionary.");
            default:
                array.add(new PdfNumber(color.getRed() / 255f));
                array.add(new PdfNumber(color.getGreen() / 255f));
                array.add(new PdfNumber(color.getBlue() / 255f));
        }
        return array;
    }
    
    public void setMKBorderColor(Color color) {
        if (color == null)
            getMK().remove(PdfName.BC);
        else
            getMK().put(PdfName.BC, getMKColor(color));
    }
    
    public void setMKBackgroundColor(Color color) {
        if (color == null)
            getMK().remove(PdfName.BG);
        else
            getMK().put(PdfName.BG, getMKColor(color));
    }
    
    public void setMKNormalCaption(String caption) {
        getMK().put(PdfName.CA, new PdfString(caption));
    }
    
    public void setMKRolloverCaption(String caption) {
        getMK().put(PdfName.RC, new PdfString(caption));
    }
    
    public void setMKAlternateCaption(String caption) {
        getMK().put(PdfName.AC, new PdfString(caption));
    }
    
    public void setMKNormalCaptionUnicode(String caption) {
        getMK().put(PdfName.CA, new PdfString(caption, PdfObject.TEXT_UNICODE));
    }
    
    public void setMKRolloverCaptionUnicode(String caption) {
        getMK().put(PdfName.RC, new PdfString(caption, PdfObject.TEXT_UNICODE));
    }
    
    public void setMKAlternateCaptionUnicode(String caption) {
        getMK().put(PdfName.AC, new PdfString(caption, PdfObject.TEXT_UNICODE));
    }
    
    public void setMKNormalIcon(PdfTemplate template) {
        getMK().put(PdfName.I, template.getIndirectReference());
    }
    
    public void setMKRolloverIcon(PdfTemplate template) {
        getMK().put(PdfName.RI, template.getIndirectReference());
    }
    
    public void setMKAlternateIcon(PdfTemplate template) {
        getMK().put(PdfName.IX, template.getIndirectReference());
    }
    
    public void setMKIconFit(PdfName scale, PdfName scalingType, float leftoverLeft, float leftoverBottom) {
        PdfDictionary dic = new PdfDictionary();
        if (!scale.equals(PdfName.A))
            dic.put(PdfName.SW, scale);
        if (!scalingType.equals(PdfName.P))
            dic.put(PdfName.S, scalingType);
        if (leftoverLeft != 0.5f || leftoverBottom != 0.5f) {
            PdfArray array = new PdfArray(new PdfNumber(leftoverLeft));
            array.add(new PdfNumber(leftoverBottom));
            dic.put(PdfName.A, array);
        }
        getMK().put(PdfName.IF, dic);
    }
    
    public void setMKTextPosition(int tp) {
        getMK().put(PdfName.TP, new PdfNumber(tp));
    }
    
    public static PdfAnnotation shallowDuplicate(PdfAnnotation annot) {
        PdfAnnotation dup;
        if (annot.isForm()) {
            dup = new PdfFormField(annot.writer);
            PdfFormField dupField = (PdfFormField)dup;
            PdfFormField srcField = (PdfFormField)annot;
            dupField.parent = srcField.parent;
            dupField.kids = srcField.kids;
        }
        else
            dup = new PdfAnnotation(annot.writer, null);
        dup.merge(annot);
        dup.form = annot.form;
        dup.annotation = annot.annotation;
        dup.templates = annot.templates;
        return dup;
    }
}
