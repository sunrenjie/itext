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

package com.lowagie.rups.controller;

import java.util.Observable;
import java.util.Observer;

import javax.swing.JTabbedPane;
import javax.swing.event.TreeSelectionListener;

import com.lowagie.rups.model.ObjectLoader;
import com.lowagie.rups.model.PdfFile;
import com.lowagie.rups.model.TreeNodeFactory;
import com.lowagie.rups.view.PageNavigationListener;
import com.lowagie.rups.view.PdfObjectListener;
import com.lowagie.rups.view.RupsMenuBar;
import com.lowagie.rups.view.TreeNavigationListener;
import com.lowagie.rups.view.Utilities;
import com.lowagie.rups.view.itext.FormTree;
import com.lowagie.rups.view.itext.OutlineTree;
import com.lowagie.rups.view.itext.PagesTable;
import com.lowagie.rups.view.itext.PdfObjectPanel;
import com.lowagie.rups.view.itext.PdfTree;
import com.lowagie.rups.view.itext.StreamTextArea;
import com.lowagie.rups.view.itext.XRefTable;
import com.lowagie.rups.view.itext.treenodes.PdfObjectTreeNode;
import com.lowagie.rups.view.itext.treenodes.PdfPagesTreeNode;
import com.lowagie.rups.view.itext.treenodes.PdfTrailerTreeNode;
import com.lowagie.text.pdf.PRStream;
import com.lowagie.text.pdf.PdfName;
import com.lowagie.text.pdf.PdfObject;

/**
 * Controls the GUI components that get their content from iText's PdfReader.
 */
public class PdfReaderController extends Observable
	implements Observer, PdfObjectListener, TreeNavigationListener {

	/** Treeview of the PDF file. */
	protected PdfTree pdfTree;
	/** Tabbed Pane containing other components. */
	protected JTabbedPane navigationTabs;
	/** JTable with all the pages and their labels. */
	protected PagesTable pages;
	/** Treeview of the outlines. */
	protected OutlineTree outlines;
	/** Treeview of the form. */
	protected FormTree form;
	/** JTable corresponding with the CrossReference table. */
	protected XRefTable xref;
	/** A panel that will show PdfObjects. */
	protected PdfObjectPanel objectPanel;
	/** Tabbed Pane containing other components. */
	protected JTabbedPane editorTabs;
	/** A panel that will show a stream. */
	protected StreamTextArea streamArea;
	
	/** The factory producing tree nodes. */
	protected TreeNodeFactory nodes;
	/** The root of the page tree. */
	protected PdfPagesTreeNode pageTreeNode;
	/** The root of the Outline tree. */
	protected PdfObjectTreeNode outlineTreeNode;
	/** The root of the Outline tree. */
	protected PdfObjectTreeNode formTreeNode;
	
	public PdfReaderController(TreeSelectionListener treeSelectionListener,
			PageNavigationListener pageNavigationListener) {
		pdfTree = new PdfTree();
		pdfTree.addTreeSelectionListener(treeSelectionListener);
		addObserver(pdfTree);
		pages = new PagesTable(pageNavigationListener);
		addObserver(pages);
		outlines = new OutlineTree();
		addObserver(outlines);
		form = new FormTree();
		addObserver(form);
		xref = new XRefTable();
		addObserver(xref);
		navigationTabs = new JTabbedPane();
		navigationTabs.addTab("Pages", null, Utilities.getScrollPane(pages), "Pages");
		navigationTabs.addTab("Outlines", null, Utilities.getScrollPane(outlines), "Outlines (Bookmarks)");
		navigationTabs.addTab("Form", null, Utilities.getScrollPane(form), "Interactive Form");
		navigationTabs.addTab("XRef", null, Utilities.getScrollPane(xref), "Cross-reference table");
		objectPanel = new PdfObjectPanel();
		addObserver(objectPanel);
		streamArea = new StreamTextArea();
		addObserver(streamArea);
		editorTabs = new JTabbedPane();
		editorTabs.addTab("Stream", null, streamArea, "Stream");
	}

	public PdfTree getPdfTree() {
		return pdfTree;
	}
	
	public void startObjectLoader(PdfFile file) {
		setChanged();
		notifyObservers();
		setChanged();
		new ObjectLoader(this, file.getPdfReader());
	}

	/**
	 * @see java.util.Observable#notifyObservers(java.lang.Object)
	 */
	@Override
	public void notifyObservers(Object obj) {
		if (obj instanceof ObjectLoader) {
			ObjectLoader loader = (ObjectLoader)obj;
			nodes = loader.getNodes();
			PdfTrailerTreeNode root = pdfTree.getRoot();
			root.setTrailer(loader.getReader().getTrailer());
			root.setUserObject("PDF Object Tree");
			nodes.expandNode(root);
			PdfObjectTreeNode catalog = nodes.getChildNode(pdfTree.getRoot(), PdfName.ROOT);
			pageTreeNode = (PdfPagesTreeNode)nodes.getChildNode(catalog, PdfName.PAGES);
			outlineTreeNode = nodes.getChildNode(catalog, PdfName.OUTLINES);
			formTreeNode = nodes.getChildNode(catalog, PdfName.ACROFORM);
		}
		super.notifyObservers(obj);
		if (obj == null) {
			pageTreeNode = null;
			outlineTreeNode = null;
			formTreeNode = null;
		}
	}

	public PdfObjectTreeNode getForm() {
		return formTreeNode;
	}

	public PdfObjectTreeNode getOutlines() {
		return outlineTreeNode;
	}

	public PdfPagesTreeNode getPages() {
		return pageTreeNode;
	}

	public void selectNode(PdfObjectTreeNode node) {
		pdfTree.selectNode(node);
	}

	public void show(PdfObject object) {
		objectPanel.render(object);
		streamArea.render(object);
		if (object instanceof PRStream) {
			editorTabs.setSelectedComponent(streamArea);
		}
	}

	public JTabbedPane getNavigationTabs() {
		return navigationTabs;
	}

	public TreeNodeFactory getNodes() {
		return nodes;
	}

	public XRefTable getXref() {
		return xref;
	}

	public void update(Observable observable, Object obj) {
		if (RupsMenuBar.CLOSE.equals(obj)) {
			setChanged();
			notifyObservers(null);
		}
	}

	public void setPageTableRow(int pageNumber) {
		pageNumber--;
		if (pages == null || pages.getSelectedRow() == pageNumber)
			return;
		if (pageNumber < pages.getRowCount())
			pages.setRowSelectionInterval(pageNumber, pageNumber);
	}

	public PdfObjectPanel getObjectPanel() {
		return objectPanel;
	}

	public StreamTextArea getStreamArea() {
		return streamArea;
	}

	public JTabbedPane getEditorTabs() {
		return editorTabs;
	}
}
