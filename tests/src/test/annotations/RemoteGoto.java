package test.annotations;

import org.junit.Test;

import test.GenericTest;

public class RemoteGoto extends GenericTest {

	@Override
	public void setup() {
		setKlass("sandbox.annotations.RemoteGoto");
	}
	
    @Override
    protected String getCmpPdf() {
        return "./results/annotations/subdir/cmp_abc.pdf";
    }

	/**
	 * Tests the example.
	 * If SRC and DEST are defined, the example manipulates a PDF;
	 * if only DEST is defined, the example creates a PDF.
	 */
    @Test(timeout = 60000)
    public void test() throws Exception {
        // Getting the destination PDF file (must be there!)
        String dest= getDest();
        // Getting the source PDF file
        String src = getSrc();

        createPdf(src);
        createPdf(dest);
        
        // Do some further tests on the PDF
        assertPdf(dest);
        // Compare the destination PDF with a reference PDF
        comparePdf(src, "./results/annotations/cmp_xyz.pdf");
        comparePdf(dest, getCmpPdf());
    }
}
