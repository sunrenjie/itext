package test;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import javax.management.OperationsException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.itextpdf.testutils.CompareTool;
import com.itextpdf.text.log.Logger;
import com.itextpdf.text.log.LoggerFactory;

public abstract class GenericTest {
	
	/** The logger class */
    private final static Logger LOGGER = LoggerFactory.getLogger(GenericTest.class.getName());

    /** The class file for the example we're going to test. */
	private Class<?> klass;
	/** An error message */
    private String errorMessage;
    /** A prefix that is part of the error message. */
    private String differenceImagePrefix = "difference";

    /**
     * Gets triggered before the test is performed.
     * When writing tests, you need to override this method to set
     * the klass variable (using the setKlass() method)
     */
    @Before
    public void setup() {
    }

    /**
     * Creates a Class object for the example you want to test.
     * @param	className	the class you want to test
     */
	protected void setKlass(String className) {
		try {
			klass = Class.forName(className);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(className + " not found");
		}
	}

	/**
	 * Tests the example.
	 * If SRC and DEST are defined, the example manipulates a PDF;
	 * if only DEST is defined, the example creates a PDF.
	 */
    @Test(timeout = 60000)
    public void test() throws Exception {
        LOGGER.info("Starting test.");
        // Getting the destination PDF file (must be there!)
        String dest= getDest();
        if (dest == null || dest.length() == 0)
            throw new OperationsException("outPdf cannot be empty!");
        // Getting the source PDF file
        String src = getSrc();
        // if there is none, just create a PDF
        if (src == null || src.length() == 0) {
        	createPdf(dest);
        }
        // if there is one, manipulate the PDF
        else {
        	manipulatePdf(src, dest);
        }
        // Do some further tests on the PDF
        assertPdf(dest);
        // Compare the destination PDF with a reference PDF
        comparePdf(dest, getCmpPdf());
        LOGGER.info("Test complete.");
    }
    
    /**
     * Creates a PDF by invoking the createPdf() method in the
     * original sample class.
     * @param	dest	the resulting PDF
     */
	protected void createPdf(String dest) throws Exception {
        LOGGER.info("Creating PDF.");
    	Method method = klass.getDeclaredMethod("createPdf", String.class);
    	method.invoke(klass.getConstructor().newInstance(), dest);
	}
    
    /**
     * Manupulates a PDF by invoking the manipulatePdf() method in the
     * original sample class.
     * @param	src		the source PDF
     * @param	dest	the resulting PDF
     */
	protected void manipulatePdf(String src, String dest) throws Exception {
        LOGGER.info("Manipulating PDF.");
    	Method method = klass.getDeclaredMethod("manipulatePdf", String.class, String.class);
    	method.invoke(klass.getConstructor().newInstance(), src, dest);
	}
	
	/**
	 * Gets the path to the source PDF from the sample class.
	 * @return	a path to a source PDF
	 */
	protected String getSrc() {
		return getStringField("SRC");
	}
	
	/**
	 * Gets the path to the resulting PDF from the sample class;
	 * this method also creates directories if necessary.
	 * @return	a path to a resulting PDF
	 */
	protected String getDest() {
		String dest = getStringField("DEST");
		if (dest != null) {
			File file = new File(dest);
			file.getParentFile().mkdirs();
		}
		return dest;
	}
	
	/**
	 * Returns a string value that is stored as a static variable
	 * inside an example class.
	 * @param name	the name of the variable
	 * @return	the value of the variable
	 */
	protected String getStringField(String name) {
		try {
			Field field = klass.getField(name);
			if (field == null)
				return null;
			Object obj = field.get(null);
			if (obj == null || ! (obj instanceof String))
				return null;
			return (String)obj;
		}
		catch(Exception e) {
			return null;
		}
	}

	/**
	 * Compares two PDF files using iText's CompareTool.
	 * @param	dest	the PDF that resulted from the test
	 * @param	cmp		the reference PDF
	 */
    protected void comparePdf(String dest, String cmp) throws Exception {
    	if (cmp == null || cmp.length() == 0) return;
        CompareTool compareTool = new CompareTool(dest, cmp);
        String outPath = "./target/" + new File(dest).getParent();
        new File(outPath).mkdirs();
        addError(compareTool.compare(dest, cmp, outPath, differenceImagePrefix));
        addError(compareTool.compareDocumentInfo(dest, cmp));
        addError(compareTool.compareLinks(dest, cmp));

        if (errorMessage != null) Assert.fail(errorMessage);
    }
	
    /**
     * Perform other tests on the resulting PDF.
     * @param	dest	the resulting PDF
     */
    protected void assertPdf(String dest) throws Exception {};

    /**
     * Every test needs to know where to find its reference file.
     */
    protected abstract String getCmpPdf();

    /**
     * Helper method to construct error messages.
     * @param	error	part of an error message.
     */
    private void addError(String error) {
        if (error != null && error.length() > 0) {
            if (errorMessage == null)
                errorMessage = "";
            else
                errorMessage += "\n";

            errorMessage += error;
        }
    }
}
