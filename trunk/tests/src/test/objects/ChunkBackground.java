/**
 * Example written by Bruno Lowagie in answer to:
 * http://stackoverflow.com/questions/19976343/how-to-set-the-paragraph-of-itext-pdf-file-as-rectangle-with-background-color-in
 * 
 * We create a Chunk and add a background color.
 */
package test.objects;

import test.GenericTest;

public class ChunkBackground extends GenericTest {

	@Override
	public void setup() {
		setKlass("sandbox.objects.ChunkBackground");
	}
	
    @Override
    protected String getCmpPdf() {
        return "./results/objects/cmp_chunk_background.pdf";
    }
}
