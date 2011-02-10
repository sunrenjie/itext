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

package com.itextpdf.rups.view.itext;

import java.io.IOException;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.itextpdf.rups.io.OutputStreamResource;
import com.itextpdf.rups.io.TextAreaOutputStream;

/**
 * TextArea that visualizes the XFA XML file.
 */
public class XfaTextArea extends JScrollPane {
	
	/** The text area with the content stream. */
	protected JTextArea text;
	
	/**
	 * Constructs a StreamTextArea.
	 */
	public XfaTextArea() {
		super();
		text = new JTextArea();
		setViewportView(text);
	}
	
	public void clear() {
		text.setText("");
	}
	
	public void load(OutputStreamResource xml) throws IOException {
		TextAreaOutputStream stream = new TextAreaOutputStream(text);
		xml.writeTo(stream);
	}

	/** A Serial Version UID. */
	private static final long serialVersionUID = -8275229961781669457L;
}
