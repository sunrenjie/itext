/*
 * $Id$
 * $Name$
 *
 * Copyright 2001, 2002 by Bruno Lowagie.
 *
 * The contents of this file are subject to the Mozilla Public License Version 1.1
 * (the "License"); you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at http://www.mozilla.org/MPL/
 *
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 * for the specific language governing rights and limitations under the License.
 *
 * The Original Code is 'iText, a free JAVA-PDF library'.
 *
 * The Initial Developer of the Original Code is Bruno Lowagie. Portions created by
 * the Initial Developer are Copyright (C) 1999, 2000, 2001, 2002 by Bruno Lowagie.
 * All Rights Reserved.
 * Co-Developer of the code is Paulo Soares. Portions created by the Co-Developer
 * are Copyright (C) 2000, 2001, 2002 by Paulo Soares. All Rights Reserved.
 *
 * Contributor(s): all the names of the contributors are added in the source code
 * where applicable.
 *
 * Alternatively, the contents of this file may be used under the terms of the
 * LGPL license (the "GNU LIBRARY GENERAL PUBLIC LICENSE"), in which case the
 * provisions of LGPL are applicable instead of those above.  If you wish to
 * allow use of your version of this file only under the terms of the LGPL
 * License and not to allow others to use your version of this file under
 * the MPL, indicate your decision by deleting the provisions above and
 * replace them with the notice and other provisions required by the LGPL.
 * If you do not delete the provisions above, a recipient may use your version
 * of this file under either the MPL or the GNU LIBRARY GENERAL PUBLIC LICENSE.
 *
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the MPL as stated above or under the terms of the GNU
 * Library General Public License as published by the Free Software Foundation;
 * either version 2 of the License, or any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Library general Public License for more
 * details.
 *
 * If you didn't download this code from the following link, you should check if
 * you aren't using an obsolete version:
 * http://www.lowagie.com/iText/
 */

package com.lowagie.text.html;

import java.util.HashMap;
import java.util.Properties;
import java.util.StringTokenizer;

import org.xml.sax.AttributeList;

import com.lowagie.text.markup.MarkupTags;
import com.lowagie.text.markup.MarkupParser;
import com.lowagie.text.xml.*;
import com.lowagie.text.*;

/**
 * The <CODE>Tags</CODE>-class maps several XHTML-tags to iText-objects.
 */

public class SAXmyHtmlHandler extends SAXmyHandler {
    
/** These are the properties of the body section. */
    private Properties bodyAttributes = new Properties();
    
/** This is the status of the table border. */
    private boolean tableBorder = false;
    
/**
 * Constructs a new SAXiTextHandler that will translate all the events
 * triggered by the parser to actions on the <CODE>Document</CODE>-object.
 *
 * @param	document	this is the document on which events must be triggered
 */
    
    public SAXmyHtmlHandler(DocListener document) {
        super(document, new HtmlTagMap());
    }
    
/**
 * Constructs a new SAXiTextHandler that will translate all the events
 * triggered by the parser to actions on the <CODE>Document</CODE>-object.
 *
 * @param	document	this is the document on which events must be triggered
 */
    
    public SAXmyHtmlHandler(DocListener document, HashMap htmlTags) {
        super(document, htmlTags);
    }
    
/**
 * This method gets called when a start tag is encountered.
 *
 * @param	name		the name of the tag that is encountered
 * @param	attrs		the list of attributes
 */
    
    public void startElement(String name, AttributeList attrs) {
        //System.err.println(name);
        
        if (((HtmlTagMap)myTags).isHtml(name)) {
            // we do nothing
            return;
        }
        if (((HtmlTagMap)myTags).isHead(name)) {
            // we do nothing
            return;
        }
        if (((HtmlTagMap)myTags).isTitle(name)) {
            // we do nothing
            return;
        }
        if (((HtmlTagMap)myTags).isMeta(name)) {
            // we look if we can change the body attributes
            String meta = null;
            String content = null;
            if (attrs != null) {
                for (int i = 0; i < attrs.getLength(); i++) {
                    String attribute = attrs.getName(i);
                    if (attribute.equalsIgnoreCase(HtmlTags.CONTENT))
                        content = attrs.getValue(i);
                    else if (attribute.equalsIgnoreCase(HtmlTags.NAME))
                        meta = attrs.getValue(i);
                }
            }
            if (meta != null && content != null) {
                bodyAttributes.put(meta, content);
            }
            return;
        }
        if (((HtmlTagMap)myTags).isLink(name)) {
            // we do nothing for the moment, in a later version we could extract the style sheet
            return;
        }
        if (((HtmlTagMap)myTags).isBody(name)) {
            // maybe we could extract some info about the document: color, margins,...
            // but that's for a later version...
            XmlPeer peer = new XmlPeer(ElementTags.ITEXT, name);
            super.handleStartingTags(peer.getTag(), bodyAttributes);
            return;
        }
        if (myTags.containsKey(name)) {
            XmlPeer peer = (XmlPeer) myTags.get(name);
            if (Table.isTag(peer.getTag()) || Cell.isTag(peer.getTag())) {
                Properties p = peer.getAttributes(attrs);
                String value;
                if (Table.isTag(peer.getTag()) && (value = p.getProperty(ElementTags.BORDERWIDTH)) != null) {
                    if (Float.valueOf(value + "f").floatValue() > 0) {
                        tableBorder = true;
                    }
                }
                if (tableBorder) {
                    p.put(ElementTags.LEFT, String.valueOf(true));
                    p.put(ElementTags.RIGHT, String.valueOf(true));
                    p.put(ElementTags.TOP, String.valueOf(true));
                    p.put(ElementTags.BOTTOM, String.valueOf(true));
                }
                super.handleStartingTags(peer.getTag(), p);
                return;
            }
            if (attrs.getValue(MarkupTags.STYLE) != null) {
                Properties p = peer.getAttributes(attrs);
                p.addAll(MarkupParser.parseAttributes(p.remove(MarkupTags.STYLE)));
                p.addAll(MarkupParser.parseAttributes(attrs.getValue(MarkupTags.STYLE)));
                super.handleStartingTags(peer.getTag(), p);
                return;
            }
            super.handleStartingTags(peer.getTag(), peer.getAttributes(attrs));
            return;
        }
        Properties attributes = new Properties();
        if (attrs != null) {
            for (int i = 0; i < attrs.getLength(); i++) {
                String attribute = attrs.getName(i);
                attributes.setProperty(attribute, attrs.getValue(i));
            }
        }
        super.handleStartingTags(name, attributes);
    }
    
/**
 * This method gets called when an end tag is encountered.
 *
 * @param	name		the name of the tag that ends
 */
    
    public void endElement(String name) {
        //System.err.println("End: " + name);
        
        if (((HtmlTagMap)myTags).isHead(name)) {
            // we do nothing
            return;
        }
        if (((HtmlTagMap)myTags).isTitle(name)) {
            if (currentChunk != null) {
                bodyAttributes.put(ElementTags.TITLE, currentChunk.content());
            }
            return;
        }
        if (((HtmlTagMap)myTags).isMeta(name)) {
            // we do nothing
            return;
        }
        if (((HtmlTagMap)myTags).isLink(name)) {
            // we do nothing
            return;
        }
        if (((HtmlTagMap)myTags).isBody(name)) {
            // we do nothing
            return;
        }
        if (myTags.containsKey(name)) {
            XmlPeer peer = (XmlPeer) myTags.get(name);
            if (Table.isTag(peer.getTag())) {
                tableBorder = false;
            }
            super.handleEndingTags(peer.getTag());
            return;
        }
        super.handleEndingTags(name);
    }
}