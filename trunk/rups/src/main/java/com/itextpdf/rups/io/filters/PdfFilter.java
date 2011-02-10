/*
 * $Id$
 *
 * Copyright 2007 Bruno Lowagie.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 */

package com.itextpdf.rups.io.filters;

import java.io.File;

import javax.swing.filechooser.FileFilter;


/**
 * Filters PDF files in a JFileChooser.
 */
public class PdfFilter extends FileFilter {

	/** A public instance of the PdfFilter. */
	public static final PdfFilter INSTANCE = new PdfFilter();
	
    /**
     *
     * @see javax.swing.filechooser.FileFilter#accept(java.io.File)
     * @param f File
     * @return boolean
     */
    public boolean accept(File f) {
		if (f.isDirectory()) return true;
		if (f.getName().toLowerCase().endsWith(".pdf")) return true;
		return false;
	}

    /**
     *
     * @see javax.swing.filechooser.FileFilter#getDescription()
     * @return String
     */
    public String getDescription() {
		return "*.pdf PDF files";
	}

}
