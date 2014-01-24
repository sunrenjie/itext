/**
 * Example written by Bruno Lowagie in answer to:
 * http://stackoverflow.com/questions/20131610/renaming-named-destinations-in-pdf-files
 * 
 * Searching for all the named destinations, with the purpose to rename them.
 * Change the destination for all GoTo actions from Link annotations on the first page.
 */
package test.annotations;

import test.ManipulationTest;

public class RenameDestinations extends ManipulationTest {

	@Override
	public void setup() {
		setKlass("sandbox.annotations.RenameDestinations");
	}
	
    @Override
    protected String getCmpPdf() {
        return "./results/annotations/cmp_nameddests.pdf";
    }
}
