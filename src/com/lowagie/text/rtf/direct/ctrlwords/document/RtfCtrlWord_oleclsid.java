/* $Id$
 * $Name$
 *
 * Copyright 2007 by Howard Shank (hgshank@yahoo.com)
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
 * the Initial Developer are Copyright (C) 1999-2006 by Bruno Lowagie.
 * All Rights Reserved.
 * Co-Developer of the code is Paulo Soares. Portions created by the Co-Developer
 * are Copyright (C) 2000-2006 by Paulo Soares. All Rights Reserved.
 *
 * Contributor(s): all the names of the contributors are added in the source code
 * where applicable.
 *
 * Alternatively, the contents of this file may be used under the terms of the
 * LGPL license (the ?GNU LIBRARY GENERAL PUBLIC LICENSE?), in which case the
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
 
package com.lowagie.text.rtf.direct.ctrlwords.document;

import com.lowagie.text.rtf.direct.RtfParser;
import com.lowagie.text.rtf.direct.ctrlwords.basectrlwords.RtfCtrlWordBase_oleclsid;

/**
 * Description:
 * 	," This sub-destination contains the CLSID for an object for
 * 	which  no server is registered on the computer saving the
 * 	given RTF file.
 * 	When reading an RTF file, if this
 * 	destination is present, then readers should know to save the
 * 	CLSID specified by the destination?s argument, and stamp the
 * 	next object that comes in the RTF stream with the specified
 * 	CLSID.
 * 	When writing an RTF file, this destination may be
 * 	instantiated for objects for which no server is registered.
 * 	This destination?s argument shall be constructed as
 * 	follows:
 * 	1. Take the object?s original CLSID
 * 	2.
 * 	Write the CLSID as the argument for \oleclsid
 * 	3. Stamp
 * 	the object with CLSID_SAXXMLReader50
 * 	4. Write the object
 * 	in the \objdata destination
 * 	Note   If a reader ignores
 * 	this destination but uses the corresponding \objdata
 * 	destination, then it will end up with an object that
 * 	believes it is a SAX XML Reader 5.0 object, even though it
 * 	may be something else.
 * Group:
 * 	Objects
 * Type:
 * 	Destination
 * Default Param:
 * 	0
 * Pass Default:
 * 	false
 * RTF Version:
 * 	
 */

public class RtfCtrlWord_oleclsid extends RtfCtrlWordBase_oleclsid {

	public RtfCtrlWord_oleclsid(RtfParser rtfParser){
		super(rtfParser);
	}

}
