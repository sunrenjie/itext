/*
 * $Id$
 * $Name$
 *
 * This code is part of the 'iText Tutorial'.
 * You can find the complete tutorial at the following address:
 * http://itextdocs.lowagie.com/tutorial/
 *
 * This code is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 *
 * itext-questions@lists.sourceforge.net
 */

package com.lowagie.examples.objects.bookmarks;

import java.io.File;

import com.lowagie.tools.plugins.HtmlBookmarks;

/**
 * Creates an HTML document with bookmarks of a PDF file.
 * 
 * @author blowagie
 */

public class HtmlIndex {

	/**
	 * Creates a document with outlines.
	 * 
	 * @param args
	 *            no arguments needed
	 */
	public static void main(String[] args) {
		System.out.println("Html Index");
		File file = new File("ChapterSection.pdf");
		if (!file.exists()) ChapterSection.main(args);
		file = new File("ChapterSection.pdf");
		String[] arg = new String[1];
		arg[0] = file.getAbsolutePath();
		HtmlBookmarks.main(arg);
		file = new File("OutlineActions.pdf");
		if (!file.exists()) OutlineActions.main(args);
		file = new File("OutlineActions.pdf");
		arg[0] = file.getAbsolutePath();
		HtmlBookmarks.main(arg);
	}
}