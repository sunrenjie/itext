/*
 * $Id$
 *
 * This file is part of the iText project.
 * Copyright (c) 1998-2009 1T3XT BVBA
 * Authors: Bruno Lowagie, Paulo Soares, et al.
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License version 3
 * as published by the Free Software Foundation with the addition of the
 * following permission added to Section 15 as permitted in Section 7(a):
 * FOR ANY PART OF THE COVERED WORK IN WHICH THE COPYRIGHT IS OWNED BY 1T3XT,
 * 1T3XT DISCLAIMS THE WARRANTY OF NON INFRINGEMENT OF THIRD PARTY RIGHTS.
 *
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Affero General Public License for more details.
 * You should have received a copy of the GNU Affero General Public License
 * along with this program; if not, see http://www.gnu.org/licenses or write to
 * the Free Software Foundation, Inc., 51 Franklin Street, Fifth Floor,
 * Boston, MA, 02110-1301 USA, or download the license from the following URL:
 * http://itextpdf.com/terms-of-use/
 *
 * The interactive user interfaces in modified source and object code versions
 * of this program must display Appropriate Legal Notices, as required under
 * Section 5 of the GNU Affero General Public License.
 *
 * In accordance with Section 7(b) of the GNU Affero General Public License,
 * you must retain the producer line in every PDF that is created or manipulated
 * using iText.
 *
 * You can be released from the requirements of the license by purchasing
 * a commercial license. Buying such a license is mandatory as soon as you
 * develop commercial activities involving the iText software without
 * disclosing the source code of your own applications.
 * These activities include: offering paid services to customers as an ASP,
 * serving PDFs on the fly in a web application, shipping iText with a closed
 * source product.
 *
 * For more information, please contact iText Software Corp. at this
 * address: sales@itextpdf.com
 */
package com.lowagie.text.xml;

import java.util.Properties;

import org.xml.sax.Attributes;

import com.lowagie.text.ElementTags;

/**
 * This interface is implemented by the peer of all the iText objects.
 */

public class XmlPeer {
    
/** This is the name of the alias. */
    protected String tagname;
    
/** This is the name of the alias. */
    protected String customTagname;
    
/** This is the Map that contains the aliases of the attributes. */
    protected Properties attributeAliases = new Properties();
    
/** This is the Map that contains the default values of the attributes. */
    protected Properties attributeValues = new Properties();
    
/** This is String that contains the default content of the attributes. */
    protected String defaultContent = null;
    
/**
 * Creates a XmlPeer.
 * @param name the iText name of a tag
 * @param alias the user defined name of a tag
 */
    
    public XmlPeer(String name, String alias) {
        this.tagname = name;
        this.customTagname = alias;
    }
    
/**
 * Gets the tagname of the peer.
 * @return the iText name of a tag
 */
    
    public String getTag() {
        return tagname;
    }
    
/**
 * Gets the tagname of the peer.
 * @return the user defined tagname
 */
    
    public String getAlias() {
        return customTagname;
    }
    
/** Gets the list of attributes of the peer. 
 * @param attrs the user defined set of attributes
 * @return the set of attributes translated to iText attributes
 */
    public Properties getAttributes(Attributes attrs) {
        Properties attributes = new Properties();
        attributes.putAll(attributeValues);
        if (defaultContent != null) {
            attributes.put(ElementTags.ITEXT, defaultContent);
        }
        if (attrs != null) {
            for (int i = 0; i < attrs.getLength(); i++) {
                String attribute = getName(attrs.getQName(i));
                attributes.setProperty(attribute, attrs.getValue(i));
            }
        }
        return attributes;
    }
    
/**
 * Sets an alias for an attribute.
 *
 * @param   name    the iText tagname
 * @param   alias   the custom tagname
 */
    
    public void addAlias(String name, String alias) {
        attributeAliases.put(alias, name);
    }
    
/**
 * Sets a value for an attribute.
 *
 * @param   name    the iText tagname
 * @param   value   the default value for this tag
 */
    
    public void addValue(String name, String value) {
        attributeValues.put(name, value);
    }
    
/**
 * Sets the default content.
 *
 * @param   content    the default content
 */
    
    public void setContent(String content) {
        this.defaultContent = content;
    }
    
/**
 * Returns the iText attribute name.
 *
 * @param   name   the custom attribute name
 * @return  iText translated attribute name
 */
    
    public String getName(String name) {
        String value;
        if ((value = attributeAliases.getProperty(name)) != null) {
            return value;
        }
        return name;
    }
    
/**
 * Returns the default values.
 * @return A set of default (user defined) values
 */
    
    public Properties getDefaultValues() {
        return attributeValues;
    }
}