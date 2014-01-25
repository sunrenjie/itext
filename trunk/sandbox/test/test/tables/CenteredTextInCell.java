/**
 * Example written by Bruno Lowagie in answer to:
 * http://stackoverflow.com/questions/19703715/centered-text-in-itext-pdf-table-cell
 * 
 * We create a table with a single column and a single cell.
 * We add some content that needs to be centered vertically.
 */
package test.tables;

import test.GenericTest;

public class CenteredTextInCell extends GenericTest {

	@Override
	public void setup() {
		setKlass("sandbox.tables.CenteredTextInCell");
	}
	
    @Override
    protected String getCmpPdf() {
        return "cmpfiles/tables/cmp_centered_text.pdf";
    }
}
