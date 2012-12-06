package com.itextpdf.text.pdf;

import com.itextpdf.text.pdf.interfaces.IAccessibleElement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class PdfPTableBody extends PdfPTableBlock {

    protected PdfName role = PdfName.TBODY;

    public PdfPTableBody() {

    }

    public PdfName getRole() {
        return role;
    }

    public void setRole(final PdfName role) {
        this.role = role;
    }

}
