/*
 * $Id$
 * $Name$
 *
 * This code is free software. It may only be copied or modified
 * if you include the following copyright notice:
 *
 * --> Copyright 2002-2005 by Bruno Lowagie <--
 *
 * This code is part of the 'iText Tutorial'.
 * You can find the complete tutorial at the following address:
 * http://www.lowagie.com/iText/tutorial/
 *
 * This code is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 *
 * itext-questions@lists.sourceforge.net
 */
package com.lowagie.examples.objects.fonts;

import java.io.BufferedWriter;
import java.io.FileWriter;

import com.lowagie.text.pdf.BaseFont;

/**
 * Retrieving the full font name
 */
public class FullFontNames {

	/**
	 * Retrieving the full font name
	 * @param args no arguments needed
	 */
	public static void main(String[] args) {
		try
		{
			BufferedWriter out = new BufferedWriter(new FileWriter("fullfontname_arialbi.txt"));
	        BaseFont bf = BaseFont.createFont("c:\\windows\\fonts\\arialbi.ttf", "winansi", BaseFont.NOT_EMBEDDED);
	        String names[][] = bf.getFullFontName();
	        for (int k = 0; k < names.length; ++k) {
	            if (names[k][0].equals("3") && names[k][1].equals("1")) // Microsoft encoding
	                out.write(names[k][3] + "\r\n");
	        }
	        out.flush();
	        out.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
