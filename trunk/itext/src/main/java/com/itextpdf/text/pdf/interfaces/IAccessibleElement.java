package com.itextpdf.text.pdf.interfaces;

import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PdfObject;
import com.itextpdf.text.pdf.PdfStructureElement;

import java.util.HashMap;

/**
 * Describes accessible element.
 */
public interface IAccessibleElement {

    /**
     * Get the proeprty of accessible element (i.e. alternate text).
     * @param key
     * @return
     */
    PdfObject getAccessibleProperty(final PdfName key);

    /**
     * Sets the property of accessible element (i.e. alternate text).
     * @param key
     * @param value
     */
    void setAccessibleProperty(final PdfName key, final PdfObject value);

    /**
     * Gets all the properties of accessible element.
     * @return
     */
    HashMap<PdfName, PdfObject> getAccessibleProperties();

    /**
     * Gets the role of the accessible element.
     * @return
     */
    PdfName getRole();

    /**
     * Sets the role of the accessiblee element.
     * Set role to <code>null</code> if you don't want to tag this element.
     * @param role
     */
    void setRole(final PdfName role);

}
