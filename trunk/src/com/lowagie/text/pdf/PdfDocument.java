/*
 * $Name$
 *
 * Copyright 1999, 2000, 2001 by Bruno Lowagie.
 *
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Library General Public License as published
 * by the Free Software Foundation; either version 2 of the License, or any
 * later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Library general Public License for more
 * details.
 *
 * You should have received a copy of the GNU Library General Public License along
 * with this library; if not, write to the Free Foundation, Inc., 59 Temple Place,
 * Suite 330, Boston, MA 02111-1307 USA.
 *
 * If you didn't download this code from the following link, you should check if
 * you aren't using an obsolete version:
 * http://www.lowagie.com/iText/
 *
 * ir-arch Bruno Lowagie,
 * Adolf Baeyensstraat 121
 * 9040 Sint-Amandsberg
 * BELGIUM
 * tel. +32 (0)9 228.10.97
 * bruno@lowagie.com
 *
 */

package com.lowagie.text.pdf;

import com.lowagie.text.StringCompare;
import com.lowagie.text.Anchor;
import com.lowagie.text.Annotation;
import java.util.Comparator;
import com.lowagie.text.DocListener;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.DocWriter;
import com.lowagie.text.Element;
import com.lowagie.text.Chunk;
import com.lowagie.text.Graphic;
import com.lowagie.text.Header;
import com.lowagie.text.HeaderFooter;
import com.lowagie.text.Image;
import com.lowagie.text.ImgWMF;
import com.lowagie.text.List;
import com.lowagie.text.ListItem;
import com.lowagie.text.Meta;
import com.lowagie.text.Phrase;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Rectangle;
import com.lowagie.text.Section;
import com.lowagie.text.Table;
import com.lowagie.text.Watermark;

import java.awt.Color;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.Iterator;

/**
 * <CODE>PdfDocument</CODE> is the class that is used by <CODE>PdfWriter</CODE>
 * to translate a <CODE>Document</CODE> into a PDF with different pages.
 * <P>
 * A <CODE>PdfDocument</CODE> always listens to a <CODE>Document</CODE>
 * and adds the Pdf representation of every <CODE>Element</CODE> that is
 * added to the <CODE>Document</CODE>.
 *
 * @see		com.lowagie.text.Document
 * @see		com.lowagie.text.DocListener
 * @see		PdfWriter
 *
 * @author  bruno@lowagie.com
 */

class PdfDocument extends Document implements DocListener {
    
/**
 * <CODE>PdfInfo</CODE> is the PDF InfoDictionary.
 * <P>
 * A document's trailer may contain a reference to an Info dictionary that provides information
 * about the document. This optional dictionary may contain one or more keys, whose values
 * should be strings.<BR>
 * This object is described in the 'Portable Document Format Reference Manual version 1.3'
 * section 6.10 (page 120-121)
 *
 * @author  bruno@lowagie.com
 */
    
    public class PdfInfo extends PdfDictionary {
        
        // constructors
        
/**
 * Construct a <CODE>PdfInfo</CODE>-object.
 */
        
        PdfInfo() {
            super();
            addProducer();
            addCreationDate();
        }
        
/**
 * Constructs a <CODE>PdfInfo</CODE>-object.
 *
 * @param		author		name of the author of the document
 * @param		title		title of the document
 * @param		subject		subject of the document
 */
        
        PdfInfo(String author, String title, String subject) {
            this();
            addTitle(title);
            addSubject(subject);
            addAuthor(author);
        }
        
/**
 * Adds the title of the document.
 *
 * @param	title		the title of the document
 */
        
        void addTitle(String title) {
            put(PdfName.TITLE, new PdfString(title, PdfObject.TEXT_UNICODE));
        }
        
/**
 * Adds the subject to the document.
 *
 * @param	subject		the subject of the document
 */
        
        void addSubject(String subject) {
            put(PdfName.SUBJECT, new PdfString(subject, PdfObject.TEXT_UNICODE));
        }
        
/**
 * Adds some keywords to the document.
 *
 * @param	keywords		the keywords of the document
 */
        
        void addKeywords(String keywords) {
            put(PdfName.KEYWORDS, new PdfString(keywords, PdfObject.TEXT_UNICODE));
        }
        
/**
 * Adds the name of the author to the document.
 *
 * @param	author		the name of the author
 */
        
        void addAuthor(String author) {
            put(PdfName.AUTHOR, new PdfString(author, PdfObject.TEXT_UNICODE));
        }
        
/**
 * Adds the name of the creator to the document.
 *
 * @param	creator		the name of the creator
 */
        
        void addCreator(String creator) {
            put(PdfName.CREATOR, new PdfString(creator, PdfObject.TEXT_UNICODE));
        }
        
/**
 * Adds the name of the producer to the document.
 */
        
        void addProducer() {
            // This line may only be changed by Bruno Lowagie or Paulo Soares
            put(PdfName.PRODUCER, new PdfString("itext-paulo (lowagie.com) - build 86", PdfObject.TEXT_UNICODE));
            // Do not edit the line above!
        }
        
/**
 * Adds the date of creation to the document.
 */
        
        void addCreationDate() {
            put(PdfName.CREATIONDATE, new PdfDate());
        }
    }
    
/**
 * <CODE>PdfCatalog</CODE> is the PDF Catalog-object.
 * <P>
 * The Catalog is a dictionary that is the root node of the document. It contains a reference
 * to the tree of pages contained in the document, a reference to the tree of objects representing
 * the document's outline, a reference to the document's article threads, and the list of named
 * destinations. In addition, the Catalog indicates whether the document's outline or thumbnail
 * page images should be displayed automatically when the document is viewed and wether some location
 * other than the first page should be shown when the document is opened.<BR>
 * In this class however, only the reference to the tree of pages is implemented.<BR>
 * This object is described in the 'Portable Document Format Reference Manual version 1.3'
 * section 6.2 (page 67-71)
 *
 * @author  bruno@lowagie.com
 */
    
    class PdfCatalog extends PdfDictionary {
        
        // constructors
        
/**
 * Constructs a <CODE>PdfCatalog</CODE>.
 *
 * @param		pages		an indirect reference to the root of the document's Pages tree.
 */
        
        PdfCatalog(PdfIndirectReference pages) {
            super(CATALOG);
            put(PdfName.PAGES, pages);
        }
        
/**
 * Constructs a <CODE>PdfCatalog</CODE>.
 *
 * @param		pages		an indirect reference to the root of the document's Pages tree.
 * @param		outlines	an indirect reference to the outline tree.
 */
        
        PdfCatalog(PdfIndirectReference pages, PdfIndirectReference outlines) {
            super(CATALOG);
            put(PdfName.PAGES, pages);
            put(PdfName.PAGEMODE, PdfName.USEOUTLINES);
            put(PdfName.OUTLINES, outlines);
        }
        
/**
 * Adds the names of the named destinations to the catalog.
 * @param localDestinations the local destinations
 */
        void addNames(TreeMap localDestinations) {
            if (localDestinations.size() == 0)
                return;
            PdfArray ar = new PdfArray();
            for (Iterator i = localDestinations.keySet().iterator(); i.hasNext();) {
                String name = (String)i.next();
                Object obj[] = (Object[])localDestinations.get(name);
                PdfIndirectReference ref = (PdfIndirectReference)obj[1];
                ar.add(new PdfString(name));
                ar.add(ref);
            }
            PdfArray limits = new PdfArray();
            limits.add((PdfString)ar.getArrayList().get(0));
            limits.add((PdfString)ar.getArrayList().get(ar.size() - 2));
            PdfDictionary dests = new PdfDictionary();
            PdfDictionary names = new PdfDictionary();
            dests.put(PdfName.LIMITS, limits);
            dests.put(PdfName.NAMES, ar);
            names.put(PdfName.DESTS, dests);
            put(PdfName.NAMES, names);
        }
        
/** Sets the viewer preferences as the sum of several constants.
 * @param preferences the viewer preferences
 * @see PdfWriter#setViewerPreferences
 */
        
        void setViewerPreferences(int preferences) {
            if ((preferences & PdfWriter.PageLayoutSinglePage) != 0)
                put(PdfName.PAGELAYOUT, PdfName.SINGLEPAGE);
            else if ((preferences & PdfWriter.PageLayoutOneColumn) != 0)
                put(PdfName.PAGELAYOUT, PdfName.ONECOLUMN);
            else if ((preferences & PdfWriter.PageLayoutTwoColumnLeft) != 0)
                put(PdfName.PAGELAYOUT, PdfName.TWOCOLUMNLEFT);
            else if ((preferences & PdfWriter.PageLayoutTwoColumnRight) != 0)
                put(PdfName.PAGELAYOUT, PdfName.TWOCOLUMNRIGHT);
            if ((preferences & PdfWriter.PageModeUseNone) != 0)
                put(PdfName.PAGEMODE, PdfName.USENONE);
            else if ((preferences & PdfWriter.PageModeUseOutlines) != 0)
                put(PdfName.PAGEMODE, PdfName.USEOUTLINES);
            else if ((preferences & PdfWriter.PageModeUseThumbs) != 0)
                put(PdfName.PAGEMODE, PdfName.USETHUMBS);
            else if ((preferences & PdfWriter.PageModeFullScreen) != 0)
                put(PdfName.PAGEMODE, PdfName.FULLSCREEN);
            if ((preferences & PdfWriter.ViewerPreferencesMask) == 0)
                return;
            PdfDictionary vp = new PdfDictionary();
            if ((preferences & PdfWriter.HideToolbar) != 0)
                vp.put(PdfName.HIDETOOLBAR, PdfBoolean.PDFTRUE);
            if ((preferences & PdfWriter.HideMenubar) != 0)
                vp.put(PdfName.HIDEMENUBAR, PdfBoolean.PDFTRUE);
            if ((preferences & PdfWriter.HideWindowUI) != 0)
                vp.put(PdfName.HIDEWINDOWUI, PdfBoolean.PDFTRUE);
            if ((preferences & PdfWriter.FitWindow) != 0)
                vp.put(PdfName.FITWINDOW, PdfBoolean.PDFTRUE);
            if ((preferences & PdfWriter.CenterWindow) != 0)
                vp.put(PdfName.CENTERWINDOW, PdfBoolean.PDFTRUE);
            if ((preferences & PdfWriter.NonFullScreenPageModeUseNone) != 0)
                vp.put(PdfName.NONFULLSCREENPAGEMODE, PdfName.USENONE);
            else if ((preferences & PdfWriter.NonFullScreenPageModeUseOutlines) != 0)
                vp.put(PdfName.NONFULLSCREENPAGEMODE, PdfName.USEOUTLINES);
            else if ((preferences & PdfWriter.NonFullScreenPageModeUseThumbs) != 0)
                vp.put(PdfName.NONFULLSCREENPAGEMODE, PdfName.USETHUMBS);
            if ((preferences & PdfWriter.DirectionL2R) != 0)
                vp.put(PdfName.DIRECTION, PdfName.L2R);
            else if ((preferences & PdfWriter.DirectionR2L) != 0)
                vp.put(PdfName.DIRECTION, PdfName.R2L);
            put(PdfName.VIEWERPREFERENCES, vp);
        }
        
        void setOpenAction(PdfAction action) {
            put(PdfName.OPENACTION, action);
        }
        
        void setPageLabels(PdfPageLabels pageLabels) {
            put(PdfName.PAGELABELS, pageLabels.getDictionary());
        }
    }
    
    // membervariables
    
/**
 * The ratio between the extra word spacing and the extra character spacing.<BR>
 * Extra word spacing will grow <CODE>ratio</CODE> times more than extra character spacing.
 */
    static final float ratio = 2.5f;
    
/** The characters to be applied the hanging ponctuation. */
    static final String hangingPunctuation = ".,;:'";
    
/** The <CODE>PdfWriter</CODE>. */
    private PdfWriter writer;
    
/** some meta information about the Document. */
    private PdfInfo info = new PdfInfo();
    
/** Signals that OnOpenDocument should be called. */
    private boolean firstPageEvent = true;
    
/** Signals that onParagraph is valid. */
    private boolean isParagraph = true;
    
    // Horizontal line
    
/** The line that is currently being written. */
    private PdfLine line = null;
    
/** This represents the current indentation of the PDF Elements on the left side. */
    private float indentLeft = 0;
    
/** This represents the current indentation of the PDF Elements on the right side. */
    private float indentRight = 0;
    
/** This represents the current indentation of the PDF Elements on the left side. */
    private float listIndentLeft = 0;
    
/** This represents the current alignment of the PDF Elements. */
    private int alignment = Element.ALIGN_LEFT;
    
    // Vertical lines
    
/** This is the PdfContentByte object, containing the text. */
    private PdfContentByte text;
    
/** This is the PdfContentByte object, containing the borders and other Graphics. */
    private PdfContentByte graphics;
    
/** The lines that are written until now. */
    private ArrayList lines = new ArrayList();
    
/** This represents the leading of the lines. */
    private float leading = 0;
    
/** This is the current height of the document. */
    private float currentHeight = 0;
    
/** This represents the current indentation of the PDF Elements on the top side. */
    private float indentTop = 0;
    
/** This represents the current indentation of the PDF Elements on the bottom side. */
    private float indentBottom = 0;
    
/** This checks if the page is empty. */
    private boolean pageEmpty = true;
    
    // resources
    
/** This is the size of the current Page. */
    protected Rectangle thisPageSize = null;
    
/** This is the FontDictionary of the current Page. */
    protected PdfFontDictionary fontDictionary;
    
/** This is the XObjectDictionary of the current Page. */
    protected PdfXObjectDictionary xObjectDictionary;
    
    // images
    
/** This is the list with all the images in the document. */
    private HashMap images = new HashMap();
    
/** This is the image that could not be shown on a previous page. */
    private Image imageWait = null;
    
/** This is the position where the image ends. */
    private float imageEnd = -1;
    
/** This is the indentation caused by an image on the left. */
    private float imageIndentLeft = 0;
    
/** This is the indentation caused by an image on the right. */
    private float imageIndentRight = 0;
    
    // annotations and outlines
    
/** This is the array containing the references to the annotations. */
    private PdfArray annotations;
    
/** This is the <CODE>ArrayList</CODE> with the outlines of the document. */
    private ArrayList outlines;
   
/** This is the current <CODE>PdfOutline</CODE> in the hierarchy of outlines. */
    private PdfOutline currentOutline;
    
/** The current active <CODE>PdfAction</CODE> when processing an <CODE>Anchor</CODE>. */
    private PdfAction currentAction = null;
    
/**
 * Stores the destinations keyed by name. Value is
 * <CODE>Object[]{PdfAction,PdfIndirectReference,PdfDestintion}</CODE>.
 */
    private TreeMap localDestinations = new TreeMap(new StringCompare());
    
/** Stores the destinations for the current page. */
    private HashMap localPageDestinations = new HashMap();
    
/** these are the viewerpreferences of the document */
    private int viewerPreferences = 0;
    
    private String openActionName;
    private PdfAction openActionAction;
    private PdfPageLabels pageLabels;

    //add by Jin-Hsia Yang
    private boolean isNewpage = false;
    private boolean isParagraphE = false;
    private float paraIndent = 0;
    //end add by Jin-Hsia Yang
    
    // constructors
    
/**
 * Constructs a new PDF document.
 * @throws DocumentException on error
 */
    
    public PdfDocument() throws DocumentException {
        super();
        addProducer();
        addCreationDate();
    }
    
    // listener methods
    
/**
 * Adds a <CODE>PdfWriter</CODE> to the <CODE>PdfDocument</CODE>.
 *
 * @param writer the <CODE>PdfWriter</CODE> that writes everything
 *                     what is added to this document to an outputstream.
 * @throws DocumentException on error
 */
    
    public final void addWriter(PdfWriter writer) throws DocumentException {
        if (this.writer == null) {
            this.writer = writer;
            return;
        }
        throw new DocumentException("You can only add a writer to a PdfDocument once.");
    }
    
/**
 * Sets the pagesize.
 *
 * @param pageSize the new pagesize
 * @return <CODE>true</CODE> if the page size was set
 */
    
    public boolean setPageSize(Rectangle pageSize) {
        if (writer != null && writer.isPaused()) {
            return false;
        }
        this.pageSize = pageSize;
        return true;
    }
    
/**
 * Changes the header of this document.
 *
 * @param header the new header
 */
    
    public void setHeader(HeaderFooter header) {
        if (writer != null && writer.isPaused()) {
            return;
        }
        super.setHeader(header);
    }
    
/**
 * Resets the header of this document.
 */
    
    public void resetHeader() {
        if (writer != null && writer.isPaused()) {
            return;
        }
        super.resetHeader();
    }
    
/**
 * Changes the footer of this document.
 *
 * @param	footer		the new footer
 */
    
    public void setFooter(HeaderFooter footer) {
        if (writer != null && writer.isPaused()) {
            return;
        }
        super.setFooter(footer);
    }
    
/**
 * Resets the footer of this document.
 */
    
    public void resetFooter() {
        if (writer != null && writer.isPaused()) {
            return;
        }
        super.resetFooter();
    }
    
/**
 * Sets the page number to 0.
 */
    
    public void resetPageCount() {
        if (writer != null && writer.isPaused()) {
            return;
        }
        super.resetPageCount();
    }
    
/**
 * Sets the page number.
 *
 * @param	pageN		the new page number
 */
    
    public void setPageCount(int pageN) {
        if (writer != null && writer.isPaused()) {
            return;
        }
        super.setPageCount(pageN);
    }
    
/**
 * Sets the <CODE>Watermark</CODE>.
 *
 * @param watermark the watermark to add
 * @return <CODE>true</CODE> if the element was added, <CODE>false</CODE> if not.
 */
    
    public boolean add(Watermark watermark) {
        if (writer != null && writer.isPaused()) {
            return false;
        }
        this.watermark = watermark;
        return true;
    }
    
/**
 * Removes the <CODE>Watermark</CODE>.
 */
    
    public void removeWatermark() {
        if (writer != null && writer.isPaused()) {
            return;
        }
        this.watermark = null;
    }
    
/**
 * Sets the margins.
 *
 * @param	marginLeft		the margin on the left
 * @param	marginRight		the margin on the right
 * @param	marginTop		the margin on the top
 * @param	marginBottom	the margin on the bottom
 * @return	a <CODE>boolean</CODE>
 */
    
    public boolean setMargins(float marginLeft, float marginRight, float marginTop, float marginBottom) {
        if (writer != null && writer.isPaused()) {
            return false;
        }
        this.marginLeft = marginLeft;
        this.marginRight = marginRight;
        this.marginTop = marginTop;
        this.marginBottom = marginBottom;
        return true;
    }
    
/**
 * Makes a new page and sends it to the <CODE>PdfWriter</CODE>.
 *
 * @return a <CODE>boolean</CODE>
 * @throws DocumentException on error
 */
    
    public boolean newPage() throws DocumentException {
        //add by Jin-Hsia Yang
        isNewpage = true;
        //end add by Jin-Hsia Yang
        if (writer.getDirectContent().size() == 0 && writer.getDirectContentUnder().size() == 0 && (pageEmpty || (writer != null && writer.isPaused()))) {
            return false;
        }
        PdfPageEvent pageEvent = writer.getPageEvent();
        if (pageEvent != null)
            pageEvent.onEndPage(writer, this);
        
        //Added to inform any listeners that we are moving to a new page (added by David Freels)
        super.newPage();
        
        // we flush the arraylist with recently written lines
        flushLines();
        // we assemble the resources of this pages
        PdfResources resources = new PdfResources();
        int procset = PdfProcSet.PDF;
        if (fontDictionary.containsFont()) {
            resources.add(fontDictionary);
            procset |= PdfProcSet.TEXT;
        }
        if (xObjectDictionary.containsXObject()) {
            resources.add(xObjectDictionary);
            procset |= PdfProcSet.IMAGEC;
        }
        resources.add(new PdfProcSet(procset));
        // we make a new page and add it to the document
        PdfPage page = new PdfPage(new PdfRectangle(thisPageSize), resources);
        // we add the annotations
        if (annotations.size() > 0) {
            page.put(PdfName.ANNOTS, annotations);
        }
        if (!open || close) {
            throw new PdfException("The document isn't open.");
        }
        text.endText();
        PdfIndirectReference pageReference = writer.add(page, new PdfContents(writer.getDirectContentUnder(), graphics, text, writer.getDirectContent()));
        // we update the outlines
        for (Iterator i = outlines.iterator(); i.hasNext(); ) {
            PdfOutline outline = (PdfOutline) i.next();
            outline.setDestinationPage(pageReference);
        }
        // we update the local destinations
        for (Iterator i = localPageDestinations.keySet().iterator(); i.hasNext();) {
            PdfDestination destination = (PdfDestination)i.next();
            destination.addPage(pageReference);
        }
        localPageDestinations.clear();
        // we initialize the new page
        initPage();

        //add by Jin-Hsia Yang
        isNewpage = false;
        //end add by Jin-Hsia Yang

        return true;
    }
    
    // methods to open and close a document
    
/**
 * Opens the document.
 * <P>
 * You have to open the document before you can begin to add content
 * to the body of the document.
 */
    
    public void open() {
        if (!open) {
            super.open();
            writer.open();
            outlines = new ArrayList();
            PdfOutline outline = new PdfOutline();
            outlines.add(outline);
            currentOutline = outline;
        }
        try {
            initPage();
        }
        catch(DocumentException de) {
        }
    }
    
/**
 * Closes the document.
 * <B>
 * Once all the content has been written in the body, you have to close
 * the body. After that nothing can be written to the body anymore.
 */
    
    public void close() {
        if (close) {
            return;
        }
        try {
            newPage();
            newPage();
        }
        catch(DocumentException pe) {
        }
        PdfPageEvent pageEvent = writer.getPageEvent();
        if (pageEvent != null)
            pageEvent.onCloseDocument(writer, this);
        super.close();
        
        try {
            writer.addLocalDestinations(localDestinations);
        }
        catch(Exception pe) {
        }
        
        if (outlines.size() > 1) {
            int objectNumber = writer.size();
            int level = 0;
            // telling each outline what its indirect reference is
            for (Iterator i = outlines.iterator(); i.hasNext(); ) {
                PdfOutline o = (PdfOutline) i.next();
                if (o.level() > level) {
                    level = o.level();
                }
                PdfIndirectReference reference = new PdfIndirectReference(PdfObject.DICTIONARY, objectNumber);
                o.setIndirectReference(reference);
                objectNumber++;
            }
            // setting al the navigation keys (First, Last, Next, Previous)
            for (int l = 0; l <= level; l++) {
                PdfOutline levelOutline = null;
                for (Iterator i = outlines.iterator(); i.hasNext(); ) {
                    PdfOutline o = (PdfOutline) i.next();
                    if (o.level() == l) {
                        if (levelOutline == null) {
                            levelOutline = o;
                        }
                        else {
                            if (o.parent().indirectReference().getNumber() == levelOutline.parent().indirectReference().getNumber()) {
                                levelOutline.put(PdfName.NEXT, o.indirectReference());
                                o.put(PdfName.PREV, levelOutline.indirectReference());
                            }
                            levelOutline = o;
                        }
                    }
                    else if (o.level() == (l + 1)) {
                        if (o.parent().get(PdfName.FIRST) == null) {
                            o.parent().put(PdfName.FIRST, o.indirectReference());
                        }
                        o.parent().put(PdfName.LAST, o.indirectReference());
                    }
                }
            }
            
            // write everything to the PdfWriter
            try {
                for (Iterator i = outlines.iterator(); i.hasNext(); ) {
                    writer.add((PdfOutline) i.next());
                }
            }
            catch(Exception pe) {
                System.err.println("Error: " + pe.getMessage());
            }
            
        }
        
        writer.close();
    }
    
/** Adds a font to the current page.
 * @param name the name of the font
 * @param ref the indirect reference to this font
 */
    public void addFont(PdfName name, PdfIndirectReference ref) {
        fontDictionary.put(name, ref);
    }
    
/** Adds a <CODE>PdfPTable</CODE> to the document.
 * @param ptable the <CODE>PdfPTable</CODE> to be added to the document.
 * @param xWidth the width the <CODE>PdfPTable</CODE> occupies in the page
 * @throws DocumentException on error
 */
    
    void addPTable(PdfPTable ptable, float xWidth) throws DocumentException {
        if (ptable.getHeaderRows() >= ptable.size())
            return;
        float headerHeight = ptable.getHeaderHeight();
        float bottom = indentBottom();
        float baseY = indentTop() - currentHeight;
        float currentY = baseY;
        int startRow = ptable.getHeaderRows();
        int currentRow = startRow;
        PdfContentByte cv[] = null;
        float eventY = 0;
        int eventRow = 0;
        int eventHeader = 0;
        float absoluteWidths[] = ptable.getAbsoluteWidths();
        PdfPTableEvent event = ptable.getTableEvent();
        ptable.setTableEvent(null);
        float heights[] = new float[ptable.size()];
        int heightsIdx = 0;
        for (currentRow = startRow; currentRow < ptable.size(); ++currentRow) {
            if (currentRow == startRow && currentY - ptable.getRowHeight(currentRow) - headerHeight < bottom) {
                if (currentHeight == 0)
                    ++startRow;
                else {
                    newPage();
                    startRow = currentRow;
                    --currentRow;
                    bottom = indentBottom();
                    baseY = indentTop() - currentHeight;
                    currentY = baseY;
                }
                continue;
            }
            if (currentY - ptable.getRowHeight(currentRow) < bottom) {
                if (cv != null) {
                    if (event != null) {
                        float finalHeights[] = new float[heightsIdx + 1];
                        finalHeights[0] = eventY;
                        for (int k = 0; k < heightsIdx; ++k)
                            finalHeights[k + 1] = finalHeights[k] - heights[k];
                        float widths[] = new float[absoluteWidths.length + 1];
                        widths[0] = xWidth;
                        for (int k = 0; k < absoluteWidths.length; ++k)
                            widths[k + 1] = widths[k] + absoluteWidths[k];
                        event.tableLayout(ptable, widths, finalHeights, eventHeader, eventRow, cv);
                    }
                    PdfPTable.endWritingRows(cv);
                    cv = null;
                }
                newPage();
                startRow = currentRow;
                --currentRow;
                bottom = indentBottom();
                baseY = indentTop() - currentHeight;
                currentY = baseY;
            }
            else {
                if (cv == null) {
                    cv = PdfPTable.beginWritingRows(writer.getDirectContent());
                    if (event != null) {
                        heightsIdx = 0;
                        eventHeader = ptable.getHeaderRows();
                        for (int k = 0; k < eventHeader; ++k)
                            heights[heightsIdx++] = ptable.getRowHeight(k);
                        eventY = currentY;
                        eventRow = currentRow;
                    }
                    currentY = ptable.writeSelectedRows(0, ptable.getHeaderRows(), xWidth, currentY, cv);
                }
                if (event != null) {
                    heights[heightsIdx++] = ptable.getRowHeight(currentRow);
                }
                currentY = ptable.writeSelectedRows(currentRow, currentRow + 1, xWidth, currentY, cv);
            }
        }
        if (cv != null) {
            if (event != null) {
                float finalHeights[] = new float[heightsIdx + 1];
                finalHeights[0] = eventY;
                for (int k = 0; k < heightsIdx; ++k)
                    finalHeights[k + 1] = finalHeights[k] - heights[k];
                float widths[] = new float[absoluteWidths.length + 1];
                widths[0] = xWidth;
                for (int k = 0; k < absoluteWidths.length; ++k)
                    widths[k + 1] = widths[k] + absoluteWidths[k];
                event.tableLayout(ptable, widths, finalHeights, eventHeader, eventRow, cv);
            }
            PdfPTable.endWritingRows(cv);
            text.moveText(0, currentY - baseY);
            currentHeight = indentTop() - currentY;
        }
        ptable.setTableEvent(event);

    }
    
/**
 * Signals that an <CODE>Element</CODE> was added to the <CODE>Document</CODE>.
 *
 * @param element the element to add
 * @return <CODE>true</CODE> if the element was added, <CODE>false</CODE> if not.
 * @throws DocumentException when a document isn't open yet, or has been closed
 */
    
    public boolean add(Element element) throws DocumentException {
        if (writer != null && writer.isPaused()) {
            return false;
        }
        try {
            
            switch(element.type()) {
                
                // Information (headers)
                case Element.HEADER:
                    // other headers than those below are not supported
                    return false;
                case Element.TITLE:
                    info.addTitle(((Meta)element).content());
                    break;
                case Element.SUBJECT:
                    info.addSubject(((Meta)element).content());
                    break;
                case Element.KEYWORDS:
                    info.addKeywords(((Meta)element).content());
                    break;
                case Element.AUTHOR:
                    info.addAuthor(((Meta)element).content());
                    break;
                case Element.CREATOR:
                    info.addCreator(((Meta)element).content());
                    break;
                case Element.PRODUCER:
                    // you can not change the name of the producer
                    info.addProducer();
                    break;
                case Element.CREATIONDATE:
                    // you can not set the creation date, only reset it
                    info.addCreationDate();
                    break;
                    
                    // content (text)
                case Element.CHUNK:
                {
                    // if there isn't a current line available, we make one
                    if (line == null) {
                        carriageReturn();
                    }
                    
                    // we cast the element to a chunk
                    PdfChunk chunk = new PdfChunk((Chunk) element, currentAction);
                    // we try to add the chunk to the line, until we succeed
                    {
                        PdfChunk overflow;
                        while ((overflow = line.add(chunk)) != null) {
                            carriageReturn();
                            chunk = overflow;
                        }
                    }
                    pageEmpty = false;
                    if (chunk.isAttribute(Chunk.NEWPAGE)) {
                        newPage();
                    }
                    break;
                }
                case Element.ANCHOR:
                {
                    Anchor anchor = (Anchor) element;
                    String url = anchor.reference();
                    leading = anchor.leading();
                    if (url != null) {
                        currentAction = new PdfAction(url);
                    }
                    
                    // we process the element
                    element.process(this);
                    currentAction = null;
                    break;
                }
                case Element.ANNOTATION:
                {
                    if (line == null) {
                        carriageReturn();
                    }
                    Annotation annot = (Annotation) element;
                    annotations.add(new PdfAnnotation(indentRight() - line.widthLeft(), indentTop() - currentHeight, indentRight() - line.widthLeft() + 20, indentTop() - currentHeight - 20, new PdfString(annot.title()), new PdfString(annot.content())));
                    pageEmpty = false;
                    break;
                }
                case Element.PHRASE:
                {
                    // we cast the element to a phrase and set the leading of the document
                    leading = ((Phrase) element).leading();
                    // we process the element
                    element.process(this);
                    break;
                }
                case Element.PARAGRAPH:
                {
                    // we cast the element to a paragraph
                    Paragraph paragraph = (Paragraph) element;
                    
                    //add by Jin-Hsia Yang
                    isParagraphE = true;
                    paraIndent = paragraph.indentationLeft();
                    //end add by Jin-Hsia Yang
                    
                    // we adjust the parameters of the document
                    alignment = paragraph.alignment();
                    indentLeft += paragraph.indentationLeft();
                    indentRight += paragraph.indentationRight();
                    leading = paragraph.leading();
                    carriageReturn();
                    // we don't want to make orphans/widows
                    if (currentHeight + line.height() + leading > indentTop() - indentBottom()) {
                        newPage();
                    }
                    PdfPageEvent pageEvent = writer.getPageEvent();
                    if (pageEvent != null && isParagraph)
                        pageEvent.onParagraph(writer, this, indentTop() - currentHeight);
                    
                    // we process the paragraph
                    element.process(this);
                    // if the last line is justified, it should be aligned to the left
                    //				if (line.hasToBeJustified()) {
                    //					line.resetAlignment();
                    //				}
                    // some parameters are set back to normal again
                    carriageReturn();
                    
                    if (pageEvent != null && isParagraph)
                        pageEvent.onParagraphEnd(writer, this, indentTop() - currentHeight);
                    
                    alignment = Element.ALIGN_LEFT;
                    indentLeft -= paragraph.indentationLeft();
                    indentRight -= paragraph.indentationRight();
                    
                    //add by Jin-Hsia Yang
                    isParagraphE = false;
                    //end add by Jin-Hsia Yang
                    
                    break;
                }
                case Element.SECTION:
                case Element.CHAPTER:
                {
                    // Chapters and Sections only differ in their constructor
                    // so we cast both to a Section
                    Section section = (Section) element;
                    
                    PdfDestination destination = new PdfDestination(PdfDestination.FITH, indentTop() - currentHeight);
                    while (currentOutline.level() >= section.depth()) {
                        currentOutline = currentOutline.parent();
                    }
                    PdfOutline outline = new PdfOutline(currentOutline, destination, section.title(), section.isBookmarkOpen());
                    outlines.add(outline);
                    currentOutline = outline;
                    
                    // some values are set
                    carriageReturn();
                    indentLeft += section.indentationLeft();
                    indentRight += section.indentationRight();
                    
                    PdfPageEvent pageEvent = writer.getPageEvent();
                    if (pageEvent != null)
                        if (element.type() == Element.CHAPTER)
                            pageEvent.onChapter(writer, this, indentTop() - currentHeight);
                        else
                            pageEvent.onSection(writer, this, indentTop() - currentHeight, section.depth());
                    
                    // the title of the section (if any has to be printed)
                    if (section.title() != null) {
                        isParagraph = false;
                        add(section.title());
                        isParagraph = true;
                    }
                    indentLeft += section.indentation();
                    // we process the section
                    element.process(this);
                    // some parameters are set back to normal again
                    indentLeft -= section.indentationLeft() + section.indentation();
                    indentRight -= section.indentationRight();
                    
                    if (pageEvent != null)
                        if (element.type() == Element.CHAPTER)
                            pageEvent.onChapterEnd(writer, this, indentTop() - currentHeight);
                        else
                            pageEvent.onSectionEnd(writer, this, indentTop() - currentHeight);
                    
                    // if the section is a chapter, we begin a new page
                    if (section.isChapter()) {
                        newPage();
                    }
                    // otherwise, we begin a new line
                    else {
                        newLine();
                    }
                    break;
                }
                case Element.LIST:
                {
                    // we cast the element to a List
                    List list = (List) element;
                    // we adjust the document
                    listIndentLeft += list.indentationLeft();
                    indentRight += list.indentationRight();
                    // we process the items in the list
                    element.process(this);
                    // some parameters are set back to normal again
                    listIndentLeft -= list.indentationLeft();
                    indentRight -= list.indentationRight();
                    break;
                }
                case Element.LISTITEM:
                {
                    // we cast the element to a ListItem
                    ListItem listItem = (ListItem) element;
                    // we adjust the document
                    alignment = listItem.alignment();
                    listIndentLeft += listItem.indentationLeft();
                    indentRight += listItem.indentationRight();
                    leading = listItem.leading();
                    carriageReturn();
                    // we prepare the current line to be able to show us the listsymbol
                    line.setListItem(listItem);
                    // we process the item
                    element.process(this);
                    // if the last line is justified, it should be aligned to the left
                    //				if (line.hasToBeJustified()) {
                    //					line.resetAlignment();
                    //				}
                    // some parameters are set back to normal again
                    carriageReturn();
                    listIndentLeft -= listItem.indentationLeft();
                    indentRight -= listItem.indentationRight();
                    break;
                }
                case Element.RECTANGLE:
                {
                    Rectangle rectangle = (Rectangle) element;
                    graphics.rectangle(rectangle);
                    pageEmpty = false;
                    break;
                }
                case Element.PTABLE:
                {
                    // before every table, we add a new line and flush all lines
                    newLine();
                    flushLines();
                    PdfPTable ptable = (PdfPTable)element;
                    float totalWidth = (indentRight() - indentLeft()) * ptable.getWidthPercentage() / 100;
                    float xWidth = 0;
                    switch (ptable.getHorizontalAlignment()) {
                        case Element.ALIGN_LEFT:
                            xWidth = indentLeft();
                            break;
                        case Element.ALIGN_RIGHT:
                            xWidth = indentRight() - totalWidth;
                            break;
                            default:
                                xWidth = (indentRight() + indentLeft() - totalWidth) / 2;
                    }
                    ptable.setTotalWidth(totalWidth);
                    addPTable(ptable, xWidth);
                    
                    break;
                }
                case Element.TABLE:
                {
                    
                    /**
                     * This is a list of people who worked on the Table functionality.
                     * To see who did what, please check the CVS repository:
                     *
                     * Leslie Baski
                     * Matt Benson
                     * Francesco De Milato
                     * David Freels
                     * Bruno Lowagie
                     * Veerendra Namineni
                     * Geert Poels
                     * Tom Ring
                     * Paulo Soares
                     */
                    
                    // before every table, we add a new line and flush all lines
                    newLine();
                    flushLines();
                    
                    // initialisation of parameters
                    float pagetop = indentTop();
                    float oldHeight = currentHeight;
                    float cellDisplacement;
                    PdfCell cell;
                    Color color;
                    
                    // correct table : fill empty cells/ parse table in table
                    ((Table) element).complete();
                    
                    // constructing the PdfTable
                    PdfTable table = new PdfTable((Table) element,
                    indentLeft(), indentRight(),
                    currentHeight > 0 ? (pagetop - currentHeight) - 6 : pagetop);
                    
                    // drawing the table
                    ArrayList cells = table.getCells();
                    ArrayList headercells = null;
                    while (! cells.isEmpty()) {
                        
                        // initialisation of some extra parameters;
                        float lostTableBottom = 0;
                        float lostTableTop = 0;
                        
                        // loop over the cells
                        boolean cellsShown = false;
                        for (Iterator iterator = cells.iterator(); iterator.hasNext(); ) {
                            cell = (PdfCell) iterator.next();
                            lines = cell.getLines(pagetop, indentBottom());
                            // if there are lines to add, add them
                            if (lines != null && lines.size() > 0) {
                                // we paint the borders of the cells
                                cellsShown = true;
                                graphics.rectangle(cell.rectangle(pagetop, indentBottom()));
                                lostTableBottom = Math.max(cell.bottom(), indentBottom());
                                lostTableTop = cell.top();
                                
                                // we write the text
                                float cellTop = cell.top(pagetop - oldHeight);
                                text.moveText(0, cellTop);
                                cellDisplacement = flushLines() - cellTop;
                                text.moveText(0, cellDisplacement);
                                if (oldHeight + cellDisplacement > currentHeight) {
                                    currentHeight = oldHeight + cellDisplacement;
                                }
                            }
                            // if a cell is allready added completely, remove it
                            if (cell.mayBeRemoved()) {
                                iterator.remove();
                            }
                        }
                        
                        // we paint the graphics of the table after looping through all the cells
                        if (cellsShown) {
                            Rectangle tablerec = new Rectangle(table);
                            tablerec.setBorder(table.border());
                            tablerec.setBorderWidth(table.borderWidth());
                            tablerec.setBorderColor(table.borderColor());
                            tablerec.setBackgroundColor(table.backgroundColor());
                            tablerec.setGrayFill(table.grayFill());
                            graphics.rectangle(tablerec.rectangle(top(), indentBottom()));
                        }
                        
                        // if the table continues on the next page
                        if (! cells.isEmpty()) {
                            
                            graphics.setLineWidth(table.borderWidth());
                            if (cellsShown && (table.border() & Rectangle.BOTTOM) == Rectangle.BOTTOM)
                            {
                                // Draw the bottom line
                                
                                // the color is set to the color of the element
                                Color tColor = table.borderColor();
                                if (tColor != null) {
                                    graphics.setRGBColorStroke(tColor.getRed(), tColor.getGreen(), tColor.getBlue());
                                }
                                graphics.moveTo(table.left(), Math.max(table.bottom(), indentBottom()));
                                graphics.lineTo(table.right(),  Math.max(table.bottom(), indentBottom()));
                                graphics.stroke();
                                if (tColor != null) {
                                    graphics.resetRGBColorStroke();
                                }
                            }
                            
                            // old page
                            pageEmpty = false;
                            float difference = lostTableBottom;
                            
                            // new page
                            newPage();
                            flushLines();
                            
                            // this part repeats the table headers (if any)
                            headercells = table.getHeaderCells();
                            int size = headercells.size();
                            if (size > 0) {
                                // this is the top of the headersection
                                cell = (PdfCell) headercells.get(0);
                                float oldTop = cell.top();
                                // loop over all the cells of the table header
                                for (int i = 0; i < size; i++) {
                                    cell = (PdfCell) headercells.get(i);
                                    // calculation of the new cellpositions
                                    cell.setTop(indentTop() - oldTop + cell.top());
                                    cell.setBottom(indentTop() - oldTop + cell.bottom() - 2f * table.cellspacing());
                                    pagetop = cell.bottom();
                                    // we paint the borders of the cell
                                    graphics.rectangle(cell.rectangle(indentTop(), indentBottom()));
                                    // we write the text of the cell
                                    lines = cell.getLines(indentTop(), indentBottom());
                                    float cellTop = cell.top(indentTop());
                                    text.moveText(0, cellTop);
                                    cellDisplacement = flushLines() - cellTop;
                                    text.moveText(0, cellDisplacement);
                                }
                                
                                currentHeight =  indentTop() - pagetop - table.cellspacing();
                                text.moveText(0, pagetop - indentTop() + table.cellspacing() - currentHeight);
                            }
                            oldHeight = currentHeight;
                            
                            // calculating the new positions of the table and the cells
                            size = Math.min(cells.size(), table.columns());
                            int i = 0;
                            while (i < size ) {
                                cell = (PdfCell) cells.get(i);
                                if (cell.top(-table.cellspacing()) > lostTableBottom) {
                                    float newBottom = pagetop - difference + cell.bottom();
                                    float neededHeight = cell.remainingHeight();
                                    if (newBottom > pagetop - neededHeight) {
                                        difference += newBottom - (pagetop - neededHeight);
                                    }
                                }
                                i++;
                            }
                            size = cells.size();
                            table.setTop(indentTop());
                            table.setBottom(pagetop - difference + table.bottom(table.cellspacing()));
                            for (i = 0; i < size; i++) {
                                cell = (PdfCell) cells.get(i);
                                float newBottom = pagetop - difference + cell.bottom();
                                float newTop = pagetop - difference + cell.top(-table.cellspacing());
                                if (newTop > indentTop() - currentHeight - table.cellspacing()) {
                                    newTop = indentTop() - currentHeight - table.cellspacing();
                                    //newTop = newBottom + cell.remainingHeight();
                                }
                                cell.setTop(newTop);
                                cell.setBottom(newBottom);
                            }
                        }
                    }
                    
                    text.moveText(0, oldHeight - currentHeight);
                    lines.add(line);
                    currentHeight += line.height() - pagetop + indentTop();
                    line = new PdfLine(indentLeft(), indentRight(), alignment, leading);
                    pageEmpty = false;
                    break;
                }
                case Element.GIF:
                case Element.JPEG:
                case Element.PNG:
                case Element.IMGRAW:
                case Element.IMGTEMPLATE:
                {
                    carriageReturn();
                    add((Image) element);
                    pageEmpty = false;
                    break;
                }
                case Element.GRAPHIC:
                {
                    Graphic graphic = (Graphic) element;
                    graphic.processAttributes(indentLeft(), indentBottom(), indentRight(), indentTop(), indentTop() - currentHeight);
                    graphics.add(graphic);
                    pageEmpty = false;
                    break;
                }
                default:
                    return false;
            }
            return true;
        }
        catch(Exception e) {
            throw new DocumentException(e.getMessage());
        }
    }
    
    // methods to add Content
    
/**
 * Adds an image to the document and to the page resources.
 * @param image the <CODE>Image</CODE> to add
 * @return the name of the image added
 * @throws PdfException on error
 * @throws DocumentException on error
 */
    
    PdfName addDirectImage(Image image) throws PdfException, DocumentException {
        PdfName name = addDirectImageSimple(image);
        xObjectDictionary.put(name, writer.getImageReference(name));
        return name;
    }
    
/** Adds an image to the document but not to the page resources. It is used with
 * templates and <CODE>Document.add(Image)</CODE>.
 * @param image the <CODE>Image</CODE> to add
 * @return the name of the image added
 * @throws PdfException on error
 * @throws DocumentException on error
 */
    PdfName addDirectImageSimple(Image image) throws PdfException, DocumentException {
        PdfName name;
        // if the images is already added, just retrieve the name
        if (images.containsKey(image.getMySerialId())) {
            name = (PdfName) images.get(image.getMySerialId());
        }
        // if it's a new image, add it to the document
        else {
            if (image.isImgTemplate()) {
                name = new PdfName("img" + images.size());
                if (image.templateData() == null) {
                    try {
                        ImgWMF wmf = (ImgWMF)image;
                        wmf.readWMF(writer.getDirectContent().createTemplate(0, 0));
                    }
                    catch (Exception e) {
                        throw new DocumentException(e.getMessage());
                    }
                }
            }
            else {
                PdfImage i = new PdfImage(image, "img" + images.size());
                writer.add(i);
                name = i.name();
            }
            images.put(image.getMySerialId(), name);
        }        
        return name;
    }
    
    
    
/**
 * Adds an image to the document.
 * @param image the <CODE>Image</CODE> to add
 * @throws PdfException on error
 * @throws DocumentException on error
 */
    
    private void add(Image image) throws PdfException, DocumentException {
        pageEmpty = false;
        PdfName name = addDirectImageSimple(image);
        
        if (image.hasAbsolutePosition()) {
            graphics.addImage(image);
            return;
        }
        
        // if there isn't enough room for the image on this page, save it for the next page
        if (currentHeight != 0 && indentTop() - currentHeight - image.scaledHeight() < indentBottom()) {
            if (imageWait == null) {
                imageWait = image;
                return;
            }
            newPage();
            if (currentHeight != 0 && indentTop() - currentHeight - image.scaledHeight() < indentBottom()) {
                imageWait = image;
                return;
            }
        }
        // avoid endless loops
        imageWait = null;
        boolean textwrap = (image.alignment() & Image.TEXTWRAP) == Image.TEXTWRAP
        && !((image.alignment() & Image.MIDDLE) == Image.MIDDLE);
        boolean underlying = (image.alignment() & Image.UNDERLYING) == Image.UNDERLYING;
        float diff = leading / 2;
        if (textwrap) {
            diff += leading;
        }
        float lowerleft = indentTop() - currentHeight - image.scaledHeight() -diff;
        float mt[] = image.matrix();
        switch(image.alignment() & Image.MIDDLE) {
            case Image.RIGHT:
                graphics.addImage(image, mt[0], mt[1], mt[2], mt[3], indentRight() - image.scaledWidth() - mt[4], lowerleft - mt[5]);
                break;
            case Image.MIDDLE:
                float middle = indentRight() - indentLeft() - image.scaledWidth();
                graphics.addImage(image, mt[0], mt[1], mt[2], mt[3], indentLeft() + (middle / 2) - mt[4], lowerleft - mt[5]);
                break;
            case Image.LEFT:
                default:
                    graphics.addImage(image, mt[0], mt[1], mt[2], mt[3], indentLeft() - mt[4], lowerleft - mt[5]);
        }
        if (textwrap) {
            if (imageEnd < 0 || imageEnd < currentHeight + image.scaledHeight() + diff) {
                imageEnd = currentHeight + image.scaledHeight() + diff;
            }
            if ((image.alignment() & Image.RIGHT) == Image.RIGHT) {
                imageIndentRight += image.scaledWidth();
            }
            else {
                imageIndentLeft += image.scaledWidth();
            }
        }
        if (!(textwrap || underlying)) {
            currentHeight += image.scaledHeight() + diff;
            flushLines();
            text.moveText(0, - (image.scaledHeight() + diff));
            newLine();
        }
    }
    
/**
 * Initializes a page.
 * <P>
 * If the footer/header is set, it is printed.
 * @throws DocumentException on error
 */
    
    private void initPage() throws DocumentException {
        
        // initialisation of some page objects
        annotations = new PdfArray();
        fontDictionary = new PdfFontDictionary();
        xObjectDictionary = new PdfXObjectDictionary();
        writer.resetContent();
        
        // the pagenumber is incremented
        pageN++;
        
        // graphics and text are initialized
        float oldleading = leading;
        int oldAlignment = alignment;
        
        imageEnd = -1;
        imageIndentRight = 0;
        imageIndentLeft = 0;
        graphics = new PdfContentByte(writer);
        text = new PdfContentByte(writer);
        text.beginText();
        leading = 16;
        indentBottom = 0;
        indentTop = 0;
        currentHeight = 0;
        
        // backgroundcolors, etc...
        thisPageSize = pageSize;
        if (pageSize.backgroundColor() != null
        || pageSize.hasBorders()
        || pageSize.borderColor() != null
        || pageSize.grayFill() > 0) {
            add(pageSize);
        }
        
        // if there is a watermark, the watermark is added
        if (watermark != null) {            
            float mt[] = watermark.matrix();
            graphics.addImage(watermark, mt[0], mt[1], mt[2], mt[3], watermark.offsetX() - mt[4], watermark.offsetY() - mt[5]);
        }
        
        // if there is a footer, the footer is added
        if (footer != null) {
            footer.setPageNumber(pageN);
            leading = footer.paragraph().leading();
            add(footer.paragraph());
            // adding the footer limits the height
            indentBottom = currentHeight;
            text.moveText(left(), indentBottom());
            flushLines();
            text.moveText(-left(), -bottom());
            footer.setTop(bottom(currentHeight));
            footer.setBottom(bottom() - (0.75f * leading));
            footer.setLeft(left());
            footer.setRight(right());
            graphics.rectangle(footer);
            indentBottom = currentHeight + leading * 3 / 2;
            currentHeight = 0;
        }
        
        // we move to the left/top position of the page
        text.moveText(left(), top());
        
        // if there is a header, the header = added
        if (header != null) {
            header.setPageNumber(pageN);
            leading = header.paragraph().leading();
            text.moveText(0, leading);
            add(header.paragraph());
            newLine();
            indentTop = currentHeight - leading;
            header.setTop(top() + leading);
            header.setBottom(indentTop() + leading * 2 / 3);
            header.setLeft(left());
            header.setRight(right());
            graphics.rectangle(header);
            flushLines();
            currentHeight = 0;
        }
        
        pageEmpty = true;
        
        // if there is an image waiting to be drawn, draw it
        try {
            if (imageWait != null) {
                add(imageWait);
                imageWait = null;
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        
        leading = oldleading;
        alignment = oldAlignment;
        carriageReturn();
        PdfPageEvent pageEvent = writer.getPageEvent();
        if (pageEvent != null) {
            if (firstPageEvent) {
                pageEvent.onOpenDocument(writer, this);
            }
            pageEvent.onStartPage(writer, this);
        }
        firstPageEvent = false;
    }
    
/**
 * If the current line is not empty or null, it is added to the arraylist
 * of lines and a new empty line is added.
 * @throws DocumentException on error
 */
    
    private void carriageReturn() throws DocumentException {
        // the arraylist with lines may not be null
        if (lines == null) {
            lines = new ArrayList();
        }
        // If the current line is not null
        if (line != null) {
            // we check if the end of the page is reached
            if (currentHeight + line.height() < indentTop() - indentBottom()) {
                // if so nonempty lines are added and the heigt is augmented
                if (line.size() > 0) {
                    currentHeight += line.height();
                    lines.add(line);
                    pageEmpty = false;
                }
            }
            // if the end of the line is reached, we start a new page
            else {
                newPage();
            }
        }
        if (imageEnd > -1 && currentHeight > imageEnd) {
            imageEnd = -1;
            imageIndentRight = 0;
            imageIndentLeft = 0;
        }
        // a new current line is constructed
        line = new PdfLine(indentLeft(), indentRight(), alignment, leading);
    }
    
/**
 * Adds the current line to the list of lines and also adds an empty line.
 * @throws DocumentException on error
 */
    
    private void newLine() throws DocumentException {
        carriageReturn();
        if (lines != null && lines.size() > 0) {
            lines.add(line);
            currentHeight += line.height();
        }
        line = new PdfLine(indentLeft(), indentRight(), alignment, leading);
    }
    
/**
 * Writes all the lines to the text-object.
 *
 * @return the displacement that was caused
 * @throws DocumentException on error
 */
    
    private float flushLines() throws DocumentException {
        
        // checks if the ArrayList with the lines is not null
        if (lines == null) {
            return 0;
        }
        
        //add by Jin-Hsia Yang
        boolean newline=false;
        //end add by Jin-Hsia Yang
        
        // checks if a new Line has to be made.
        if (line != null && line.size() > 0) {
            lines.add(line);
            line = new PdfLine(indentLeft(), indentRight(), alignment, leading);
            
            //add by Jin-Hsia Yang
            newline=true;
            //end add by Jin-Hsia Yang
            
        }
        
        // checks if the ArrayList with the lines is empty
        if (lines.size() == 0) {
            return 0;
        }
        
        // initialisation of some parameters
        Object currentValues[] = new Object[2];
        PdfFont currentFont = null;
        int currentLeading = 0;
        float displacement = 0;
        PdfLine l;
        PdfChunk chunk;
        Float lastBaseFactor = new Float(0);
        currentValues[1] = lastBaseFactor;
        // looping over all the lines
        for (Iterator i = lines.iterator(); i.hasNext(); ) {
            
            // this is a line in the loop
            l = (PdfLine) i.next();
            
            //add by Jin-Hsia Yang
            if(isParagraphE && isNewpage && newline) {
	        newline=false;
	        text.moveText(listIndentLeft + paraIndent,-l.height());
            } else
            //end add by Jin-Hsia Yang

            // aligning the line
            text.moveText(l.indentLeft() - indentLeft() + listIndentLeft, -l.height());
            
            // is the line preceeded by a symbol?
            if (l.listSymbol() != null) {
                chunk = l.listSymbol();
                text.moveText(- l.listIndent(), 0);
                if (chunk.font().compareTo(currentFont) != 0) {
                    currentFont = chunk.font();
                    text.setFontAndSize(currentFont.getFont(), currentFont.size());
                }
                if (chunk.color() != null) {
                    Color color = chunk.color();
                    text.setRGBColorFill(color.getRed(), color.getGreen(), color.getBlue());
                    text.showText(chunk);
                    text.resetRGBColorFill();
                }
                else {
                    text.showText(chunk);
                }
                text.moveText(l.listIndent(), 0);
            }
            
            currentValues[0] = currentFont;
            
            writeLineToContent(l, text, graphics, currentValues);
            
            currentFont = (PdfFont)currentValues[0];
            
            displacement += l.height();
            if (indentLeft() - listIndentLeft != l.indentLeft()) {
                text.moveText(indentLeft() - l.indentLeft() - listIndentLeft, 0);
            }
            
        }
        lines = new ArrayList();
        return displacement;
    }
    
    // methods to retrieve information
    
/**
 * Gets the <CODE>PdfInfo</CODE>-object.
 *
 * @return	<CODE>PdfInfo</COPE>
 */
    
    PdfInfo getInfo() {
        return info;
    }
    
/**
 * Gets the <CODE>PdfCatalog</CODE>-object.
 *
 * @param pages an indirect reference to this document pages
 * @return <CODE>PdfCatalog</CODE>
 */
    
    PdfCatalog getCatalog(PdfIndirectReference pages) {
        PdfCatalog catalog;
        if (outlines.size() > 1) {
            PdfOutline outline = (PdfOutline) outlines.get(0);
            catalog = new PdfCatalog(pages, outline.indirectReference());
        }
        else
            catalog = new PdfCatalog(pages);
        if (openActionName != null) {
            PdfAction action = getLocalGotoAction(openActionName);
            catalog.setOpenAction(action);
        }
        else if (openActionAction != null)
            catalog.setOpenAction(openActionAction);
        if (pageLabels != null)
            catalog.setPageLabels(pageLabels);
        catalog.addNames(localDestinations);
        catalog.setViewerPreferences(viewerPreferences);
        return catalog;
    }
    
    // methods concerning the layout
    
/**
 * Returns the bottomvalue of a <CODE>Table</CODE> if it were added to this document.
 *
 * @param	table	the table that may or may not be added to this document
 * @return	a bottom value
 */
    
    float bottom(Table table) {
        // where will the table begin?
        float h = (currentHeight > 0) ? indentTop() - currentHeight - 2f * leading : indentTop();
        // constructing a PdfTable
        PdfTable tmp = new PdfTable(table, indentLeft(), indentRight(), h);
        return tmp.bottom();
    }
    
/**
 * Checks if a <CODE>PdfPTable</CODE> fits the current page of the <CODE>PdfDocument</CODE>.
 *
 * @param	table	the table that has to be checked
 * @param	margin	a certain margin
 * @return	<CODE>true</CODE> if the <CODE>PdfPTable</CODE> fits the page, <CODE>false</CODE> otherwise.
 */
    
    boolean fitsPage(PdfPTable table, float margin) {
        float totalWidth = (indentRight() - indentLeft()) * table.getWidthPercentage() / 100;
        table.setTotalWidth(totalWidth);
        return table.getTotalHeight() <= indentTop() - currentHeight - indentBottom() - margin;
    }
    
/**
 * Gets the indentation on the left side.
 *
 * @return	a margin
 */
    
    private float indentLeft() {
        return left(indentLeft + listIndentLeft + imageIndentLeft);
    }
    
/**
 * Gets the indentation on the right side.
 *
 * @return	a margin
 */
    
    private float indentRight() {
        return right(indentRight + imageIndentRight);
    }
    
/**
 * Gets the indentation on the top side.
 *
 * @return	a margin
 */
    
    private float indentTop() {
        return top(indentTop);
    }
    
/**
 * Gets the indentation on the bottom side.
 *
 * @return	a margin
 */
    
    float indentBottom() {
        return bottom(indentBottom);
    }
    
/**
 * Adds an outline to the document.
 * @param outline the outline to be added
 */
    void addOutline(PdfOutline outline) {
        outlines.add(outline);
    }
    
/**
 * Adds a named outline to the document .
 * @param outline the outline to be added
 * @param name the name of this local destination
 */
    void addOutline(PdfOutline outline, String name) {
        outlines.add(outline);
        localDestination(name, outline.getPdfDestination());
    }
    
/**
 * Gets the root outline. All the outlines must be created with a parent.
 * The first level is created with this outline.
 * @return the root outline
 */
    public PdfOutline getRootOutline() {
        return (PdfOutline)outlines.get(0);
    }
    
/**
 * Adds a template to the page dictionary.
 * @param template the template to be added
 * @return the name by which this template is identified
 */
    PdfName addTemplateToPage(PdfTemplate template) {
        PdfName name = writer.addDirectTemplateSimple(template);
        xObjectDictionary.put(name, template.getIndirectReference());
        return name;
    }
    
/**
 * Writes a text line to the document. It takes care of all the attributes.
 * <P>
 * Before entering the line position must have been established and the
 * <CODE>text</CODE> argument must be in text object scope (<CODE>beginText()</CODE>).
 * @param line the line to be written
 * @param text the <CODE>PdfContentByte</CODE> where the text will be written to
 * @param graphics the <CODE>PdfContentByte</CODE> where the graphics will be written to
 * @param currentValues the current font and extra spacing values
 * @throws DocumentException on error
 */
    void writeLineToContent(PdfLine line, PdfContentByte text, PdfContentByte graphics, Object currentValues[])  throws DocumentException {
        PdfFont currentFont = (PdfFont)(currentValues[0]);
        float lastBaseFactor = ((Float)(currentValues[1])).floatValue();
        PdfChunk chunk;
        int numberOfSpaces;
        int lineLen;
        boolean isJustified;
        float hangingCorrection = 0;
        
        numberOfSpaces = line.numberOfSpaces();
        lineLen = line.toString().length();
        // does the line need to be justified?
        isJustified = line.hasToBeJustified() && (numberOfSpaces != 0 || lineLen > 1);
        if (isJustified) {
            if (line.isNewlineSplit() && line.widthLeft() >= (lastBaseFactor * (ratio * numberOfSpaces + lineLen - 1))) {
                text.setWordSpacing(ratio * lastBaseFactor);
                text.setCharacterSpacing(lastBaseFactor);
            }
            else {
                float width = line.widthLeft();
                PdfChunk last = line.getChunk(line.size() - 1);
                if (last != null) {
                    String s = last.toString();
                    char c;
                    if (s.length() > 0 && hangingPunctuation.indexOf((c = s.charAt(s.length() - 1))) >= 0) {
                        float oldWidth = width;
                        width += last.font().width(c) * 0.4f;
                        hangingCorrection = width - oldWidth;
                    }
                }
                float baseFactor = width / (ratio * numberOfSpaces + lineLen - 1);
                text.setWordSpacing(ratio * baseFactor);
                text.setCharacterSpacing(baseFactor);
                lastBaseFactor = baseFactor;
            }
        }
        
        int lastChunkStroke = line.getLastStrokeChunk();
        int chunkStrokeIdx = 0;
        float xMarker = text.getXTLM();
        float baseXMarker = xMarker;
        float yMarker = text.getYTLM();
        boolean imageWasPresent = false;
        
        // looping over all the chunks in 1 line
        for (Iterator j = line.iterator(); j.hasNext(); ) {
            chunk = (PdfChunk) j.next();
            
            if (chunk.font().compareTo(currentFont) != 0) {
                currentFont = chunk.font();
                text.setFontAndSize(currentFont.getFont(), currentFont.size());
            }
            Color color = chunk.color();
            float rise = 0;
            Float fr = (Float)chunk.getAttribute(Chunk.SUBSUPSCRIPT);
            if (fr != null)
                rise = fr.floatValue();
            if (color != null)
                text.setRGBColorFill(color.getRed(), color.getGreen(), color.getBlue());
            if (rise != 0)
                text.setTextRise(rise);
            
            if (chunk.isImage()) {
                imageWasPresent = true;
            }
            // If it is a CJK chunk we will have to simulate the
            // space adjustment.
            else if (isJustified && numberOfSpaces > 0 && chunk.isCJKEncoding()) {
                String s = chunk.toString();
                int idx = s.indexOf(' ');
                if (idx < 0)
                    text.showText(chunk);
                else {
                    float spaceCorrection = - ratio * lastBaseFactor * 1000f / chunk.font.size();
                    PdfTextArray textArray = new PdfTextArray(new PdfString(s.substring(0, idx), chunk.getEncoding()));
                    int lastIdx = idx;
                    while ((idx = s.indexOf(' ', lastIdx + 1)) >= 0) {
                        textArray.add(new PdfNumber(spaceCorrection));
                        textArray.add(new PdfString(s.substring(lastIdx, idx), chunk.getEncoding()));
                        lastIdx = idx;
                    }
                    textArray.add(new PdfNumber(spaceCorrection));
                    textArray.add(new PdfString(s.substring(lastIdx), chunk.getEncoding()));
                    text.showText(textArray);
                }
            }
            else
                text.showText(chunk);
            
            if (rise != 0)
                text.setTextRise(0);
            if (color != null)
                text.resetRGBColorFill();
            
            if (chunkStrokeIdx <= lastChunkStroke) {
                boolean isStroked = (chunk.isAttribute(Chunk.STRIKETHRU) || chunk.isAttribute(Chunk.UNDERLINE));
                float width;
                float endSpacing = 0;
                if (isJustified) {
                    width = chunk.getWidthCorrected(lastBaseFactor, ratio * lastBaseFactor);
                }
                else
                    width = chunk.width();
                if (chunk.isStroked()) {
                    PdfChunk nextChunk = line.getChunk(chunkStrokeIdx + 1);
                    if (isStroked) {
                        graphics.setLineWidth(chunk.font().size() / 15);
                        if (color != null)
                            graphics.setRGBColorStroke(color.getRed(), color.getGreen(), color.getBlue());
                    }
                    float shift = chunk.font().size() / 3;
                    if (chunk.isAttribute(Chunk.STRIKETHRU)) {
                        float subtract = lastBaseFactor;
                        if (nextChunk != null && nextChunk.isAttribute(Chunk.STRIKETHRU))
                            subtract = 0;
                        if (nextChunk == null)
                            subtract += hangingCorrection;
                        graphics.moveTo(xMarker, yMarker + shift);
                        graphics.lineTo(xMarker + width - subtract, yMarker + shift);
                        graphics.stroke();
                    }
                    if (chunk.isAttribute(Chunk.UNDERLINE)) {
                        float subtract = lastBaseFactor;
                        if (nextChunk != null && nextChunk.isAttribute(Chunk.UNDERLINE))
                            subtract = 0;
                        if (nextChunk == null)
                            subtract += hangingCorrection;
                        graphics.moveTo(xMarker, yMarker - shift);
                        graphics.lineTo(xMarker + width - subtract, yMarker - shift);
                        graphics.stroke();
                    }
                    if (chunk.isAttribute(Chunk.LINK)) {
                        float subtract = lastBaseFactor;
                        if (nextChunk != null && nextChunk.isAttribute(Chunk.LINK))
                            subtract = 0;
                        if (nextChunk == null)
                            subtract += hangingCorrection;
                        annotations.add(new PdfAnnotation(xMarker, yMarker, xMarker + width - subtract, yMarker + chunk.font().size(), (PdfAction)chunk.getAttribute(Chunk.LINK)));
                    }
                    if (chunk.isAttribute(Chunk.REMOTEGOTO)) {
                        float subtract = lastBaseFactor;
                        if (nextChunk != null && nextChunk.isAttribute(Chunk.REMOTEGOTO))
                            subtract = 0;
                        if (nextChunk == null)
                            subtract += hangingCorrection;
                        Object obj[] = (Object[])chunk.getAttribute(Chunk.REMOTEGOTO);
                        String filename = (String)obj[0];
                        if (obj[1] instanceof String)
                            remoteGoto(filename, (String)obj[1], xMarker, yMarker, xMarker + width - subtract, yMarker + chunk.font().size());
                        else
                            remoteGoto(filename, ((Integer)obj[1]).intValue(), xMarker, yMarker, xMarker + width - subtract, yMarker + chunk.font().size());
                    }
                    if (chunk.isAttribute(Chunk.LOCALGOTO)) {
                        float subtract = lastBaseFactor;
                        if (nextChunk != null && nextChunk.isAttribute(Chunk.LOCALGOTO))
                            subtract = 0;
                        if (nextChunk == null)
                            subtract += hangingCorrection;
                        localGoto((String)chunk.getAttribute(Chunk.LOCALGOTO), xMarker, yMarker, xMarker + width - subtract, yMarker + chunk.font().size());
                    }
                    if (chunk.isAttribute(Chunk.LOCALDESTINATION)) {
                        float subtract = lastBaseFactor;
                        if (nextChunk != null && nextChunk.isAttribute(Chunk.LOCALDESTINATION))
                            subtract = 0;
                        if (nextChunk == null)
                            subtract += hangingCorrection;
                        localDestination((String)chunk.getAttribute(Chunk.LOCALDESTINATION), new PdfDestination(PdfDestination.XYZ, xMarker, yMarker + chunk.font().size(), 0));
                    }
                    if (chunk.isAttribute(Chunk.GENERICTAG)) {
                        float subtract = lastBaseFactor;
                        if (nextChunk != null && nextChunk.isAttribute(Chunk.GENERICTAG))
                            subtract = 0;
                        if (nextChunk == null)
                            subtract += hangingCorrection;
                        Rectangle rect = new Rectangle(xMarker, yMarker, xMarker + width - subtract, yMarker + chunk.font().size());
                        PdfPageEvent pev = writer.getPageEvent();
                        if (pev != null)
                            pev.onGenericTag(writer, this, rect, (String)chunk.getAttribute(Chunk.GENERICTAG));
                    }
                    if (chunk.isImage()) {
                        Image image = chunk.getImage();
                        float matrix[] = image.matrix();
                        matrix[Image.CX] = xMarker + chunk.getImageOffsetX() - matrix[Image.CX];
                        matrix[Image.CY] = yMarker + chunk.getImageOffsetY() - matrix[Image.CY];
                        graphics.addImage(image, matrix[0], matrix[1], matrix[2], matrix[3], matrix[4], matrix[5]);
                        text.setTextMatrix(xMarker + lastBaseFactor + image.scaledWidth(), yMarker);
                    }
                    if (isStroked) {
                        graphics.setLineWidth(chunk.font().size() / 15);
                        if (color != null)
                            graphics.resetRGBColorStroke();
                    }
                }
                xMarker += width;
                ++chunkStrokeIdx;
            }
        }
        if (isJustified) {
            text.setWordSpacing(0);
            text.setCharacterSpacing(0);
        }
        if (imageWasPresent)
            text.setTextMatrix(baseXMarker, yMarker);
        currentValues[0] = currentFont;
        currentValues[1] = new Float(lastBaseFactor);
    }
    
/**
 * Implements a link to other part of the document. The jump will
 * be made to a local destination with the same name, that must exist.
 * @param name the name for this link
 * @param llx the lower left x corner of the activation area
 * @param lly the lower left y corner of the activation area
 * @param urx the upper right x corner of the activation area
 * @param ury the upper right y corner of the activation area
 */
    void localGoto(String name, float llx, float lly, float urx, float ury) {
        PdfAction action = getLocalGotoAction(name);
        annotations.add(new PdfAnnotation(llx, lly, urx, ury, action));
    }

    PdfAction getLocalGotoAction(String name) {
        PdfAction action;
        Object obj[] = (Object[])localDestinations.get(name);
        if (obj == null)
            obj = new Object[3];
        if (obj[0] == null) {
            if (obj[1] == null) {
                obj[1] = writer.getPdfIndirectReference();
            }
            action = new PdfAction((PdfIndirectReference)obj[1]);
            obj[0] = action;
            localDestinations.put(name, obj);
        }
        else {
            action = (PdfAction)obj[0];
        }
        return action;
    }
    
/**
 * The local destination to where a local goto with the same
 * name will jump.
 * @param name the name of this local destination
 * @param destination the <CODE>PdfDestination</CODE> with the jump coordinates
 * @return <CODE>true</CODE> if the local destination was added,
 * <CODE>false</CODE> if a local destination with the same name
 * already existed
 */
    boolean localDestination(String name, PdfDestination destination) {
        Object obj[] = (Object[])localDestinations.get(name);
        if (obj == null)
            obj = new Object[3];
        if (obj[2] != null)
            return false;
        obj[2] = destination;
        localDestinations.put(name, obj);
        localPageDestinations.put(destination, null);
        return true;
    }
    
/**
 * Implements a link to another document.
 * @param filename the filename for the remote document
 * @param name the name to jump to
 * @param llx the lower left x corner of the activation area
 * @param lly the lower left y corner of the activation area
 * @param urx the upper right x corner of the activation area
 * @param ury the upper right y corner of the activation area
 */
    void remoteGoto(String filename, String name, float llx, float lly, float urx, float ury) {
        annotations.add(new PdfAnnotation(llx, lly, urx, ury, new PdfAction(filename, name)));
    }
    
/**
 * Implements a link to another document.
 * @param filename the filename for the remote document
 * @param page the page to jump to
 * @param llx the lower left x corner of the activation area
 * @param lly the lower left y corner of the activation area
 * @param urx the upper right x corner of the activation area
 * @param ury the upper right y corner of the activation area
 */
    void remoteGoto(String filename, int page, float llx, float lly, float urx, float ury) {
        annotations.add(new PdfAnnotation(llx, lly, urx, ury, new PdfAction(filename, page)));
    }
    
/** Sets the viewer preferences as the sum of several constants.
 * @param preferences the viewer preferences
 * @see PdfWriter#setViewerPreferences
 */
    
    public void setViewerPreferences(int preferences) {
        viewerPreferences |= preferences;
    }
    
/** Implements an action in an area.
 * @param action the <CODE>PdfAction</CODE>
 * @param llx the lower left x corner of the activation area
 * @param lly the lower left y corner of the activation area
 * @param urx the upper right x corner of the activation area
 * @param ury the upper right y corner of the activation area
 */
    void setAction(PdfAction action, float llx, float lly, float urx, float ury) {
        annotations.add(new PdfAnnotation(llx, lly, urx, ury, action));
    }

    void setOpenAction(String name) {
        openActionName = name;
        openActionAction = null;
    }

    void setOpenAction(PdfAction action) {
        openActionAction = action;
        openActionName = null;
    }
    
    void setPageLabels(PdfPageLabels pageLabels) {
        this.pageLabels = pageLabels;
    }
}
