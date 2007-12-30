/*
 * $Id:  $
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

package com.lowagie.rups.view;

import com.lowagie.rups.view.itext.treenodes.PdfObjectTreeNode;
import com.lowagie.rups.view.itext.treenodes.PdfPagesTreeNode;

/**
 * Interface implemented by objects that can show a PDF as a JTree.
 */
public interface TreeNavigationListener {
	/**
	 * Select a specific node in the tree.
	 * Typically this method will be called from a different tree,
	 * such as the pages, outlines or form tree.
	 * @param	node	the node that has to be selected
	 */
	public void selectNode(PdfObjectTreeNode node);
	/**
	 * Gets the root of the page tree
	 * @return	the top level PdfPagesTreeNode
	 */
	public PdfPagesTreeNode getPages();

	/**
	 * Gets the root of the outline tree.
	 * @return	the top level Outline TreeNode
	 */
	public PdfObjectTreeNode getOutlines();

	/**
	 * Gets the root of the form tree.
	 * @return	the top level Outline TreeNode
	 */
	public PdfObjectTreeNode getForm();
}
