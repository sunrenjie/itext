/*
 * $Id$
 * $Name$
 *
 * This code is free software. It may only be copied or modified
 * if you include the following copyright notice:
 *
 * --> Copyright 2000, 2001 by Bruno Lowagie <--
 *
 * This code is part of the 'iText Tutorial'.
 * You can find the complete tutorial at the following address:
 * http://www.lowagie.com/iText/tutorial/
 *
 * This code is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 *
 * ir-arch Bruno Lowagie,
 * Adolf Baeyensstraat 121
 * 9040 Sint-Amandsberg
 * BELGIUM
 * tel. +32 (0)9 228.10.97
 * bruno@lowagie.com
 */

import java.awt.Color;
import java.util.GregorianCalendar;

import com.lowagie.text.BadElementException;
import com.lowagie.text.Cell;
import com.lowagie.text.Chunk;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Table;

/**
 * This class makes a special Table that contains all the days of a given month.
 */

class Month extends Table {

// private final static membervariables

        /** These are the widths of the columns. */
        public static final int[] WIDTHS = {1, 1, 1, 1, 1, 1, 1};

        /**
         * Constructs a Month.
         */

        public Month(int month, int year, String[] days, Color border, Color bgTable, Color bgCells) throws BadElementException, DocumentException {
                super(7);
                setWidth(80);
                setCellpadding(2);
                setCellspacing(2);
                setWidths(WIDTHS);
                setBorderWidth(2); 
                setBorderColor(border);
                setBackgroundColor(bgTable);

                Font weekday =  new Font(Font.HELVETICA, 12, Font.NORMAL, bgCells);
                Font holiday =  new Font(Font.HELVETICA, 12, Font.BOLD, bgCells);
                Font week =             new Font(Font.HELVETICA, 24, Font.BOLD, bgTable);
                Font weekend =  new Font(Font.HELVETICA, 24, Font.BOLD, border);

                for (int day = 0; day < 7; day++) {
                        Cell cell;
                        if (day > 4) {
                                cell = new Cell(new Chunk(days[day], holiday));
                        }
                        else {
                                cell = new Cell(new Chunk(days[day], weekday));
                        }
                        cell.setLeading(12);
                        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                        cell.setBorder(BOTTOM);
                        cell.setBorderColor(bgCells);
                        addCell(cell);
                }

                GregorianCalendar date = new GregorianCalendar(year, month, 1);
                int nDays = date.getActualMaximum(GregorianCalendar.DAY_OF_MONTH) + 1;

                int i = 1;
                for (int day = 1; day < nDays; day++) {
                        date = new GregorianCalendar(year, month, day);
                        Cell cell;
                        while ((i++ % 7) != (date.get(GregorianCalendar.DAY_OF_WEEK) - 1)) {
                                cell = new Cell();
                                cell.setBorder(Cell.NO_BORDER);
                                addCell(cell);
                        }
                        if ((i % 7) == 0 || (i % 7) == 1) {
                                cell = new Cell(new Chunk(String.valueOf(day), weekend));
                        }
                        else {
                                cell = new Cell(new Chunk(String.valueOf(day), week));
                        }

                        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                        cell.setLeading(25);
                        cell.setBorder(Cell.NO_BORDER);
                        cell.setBorderWidth(1);
                        cell.setBackgroundColor(bgCells);
                        addCell(cell);
                }

        }
}