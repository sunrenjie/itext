/*
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
package com.lowagie.text;

/**
 * The ExceptionConverter changes a checked exception into an
 * unchecked exception.
 */
public class ExceptionConverter extends RuntimeException {
    /** we keep a handle to the wrapped exception */
    private Exception ex;

    public ExceptionConverter(Exception ex) {
        this.ex = ex;
    }

    /** and allow the user of ExceptionConverter to get a handle to it. */
    public Exception getException() {
        return ex;
    }

    /** We print the message of the checked exception */
    public String getMessage() {
        return ex.getMessage();
    }

    /** and make sure we also produce a localized version */
    public String getLocalizedMessage() {
        return ex.getLocalizedMessage();
    }

    /** The toString() is changed to be prefixed with ExceptionConverter */
    public String toString() {
        return "ExceptionConverter: " + ex;
    }

    /** we have to override this as well */
    public void printStackTrace() {
        printStackTrace(System.err);
    }

    /** here we prefix, with s.print(), not s.println(), the stack
     * trace with "ExceptionConverter:" */
    public void printStackTrace(java.io.PrintStream s) {
        synchronized (s) {
            s.print("ExceptionConverter: ");
            ex.printStackTrace(s);
        }
    }

    /** Again, we prefix the stack trace with "ExceptionConverter:" */
    public void printStackTrace(java.io.PrintWriter s) {
        synchronized (s) {
            s.print("ExceptionConverter: ");
            ex.printStackTrace(s);
        }
    }

    /** requests to fill in the stack trace we will have to ignore.
     * We can't throw an exception here, because this method
     * is called by the constructor of Throwable */
    public Throwable fillInStackTrace() {
        return this;
    }
}