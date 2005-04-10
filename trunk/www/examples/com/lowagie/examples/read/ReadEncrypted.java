/*
 * $Id$
 * $Name$
 *
 * This code is part of the 'iText Tutorial'.
 * You can find the complete tutorial at the following address:
 * http://itext.sourceforge.net/tutorial/
 *
 * This code is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 *
 * itext-questions@lists.sourceforge.net
 */
package com.lowagie.examples.read;

import java.io.BufferedWriter;
import java.io.FileWriter;

import com.lowagie.text.pdf.PdfReader;

/**
 * Reading an encrypted PDF file (you need the owner password to do this).
 */
public class ReadEncrypted {

	/**
	 * Reads an encrypted PDF document.
	 * 
	 * @param args
	 *            no arguments needed
	 */
	public static void main(String[] args) {
		try {
			BufferedWriter out = new BufferedWriter(new FileWriter(
					"info_encrypted.txt"));
			PdfReader r = new PdfReader("HelloEncrypted.pdf", "Hello"
						.getBytes());
			out.write(r.getInfo().toString());
			out.write("\r\n");
			out.write(String.valueOf(r.getPermissions()));
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}