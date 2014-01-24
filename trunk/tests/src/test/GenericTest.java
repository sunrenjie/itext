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
	
    private final static Logger LOGGER = LoggerFactory.getLogger(GenericTest.class.getName());

	protected Class<?> klass;
    private String errorMessage;
    private String differenceImagePrefix = "difference";

    @Before
    public void setup() {
    }

	protected void setKlass(String className) {
		try {
			klass = Class.forName(className);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(className + " not found");
		}
	}

    @Test(timeout = 60000)
    public void test() throws Exception {
        LOGGER.info("Starting test.");
        String dest= getDest();
        if (dest == null || dest.length() == 0)
            throw new OperationsException("outPdf cannot be empty!");
        String src = getSrc();
        if (src == null || src.length() == 0) {
        	createPdf(src);
        }
        else {
        	manipulatePdf(src, dest);
        }
        assertPdf(dest);
        comparePdf(dest, getCmpPdf());
        LOGGER.info("Test complete.");
    }
    
	protected void createPdf(String dest) throws Exception {
        LOGGER.info("Creating PDF.");
    	Method method = klass.getDeclaredMethod("createPdf", String.class);
    	method.invoke(klass.getConstructor().newInstance(), dest);
	}
    
	protected void manipulatePdf(String src, String dest) throws Exception {
        LOGGER.info("Manipulating PDF.");
    	Method method = klass.getDeclaredMethod("manipulatePdf", String.class, String.class);
    	method.invoke(klass.getConstructor().newInstance(), src, dest);
	}
	
	protected String getSrc() {
		return getStringField("SRC");
	}
	
	protected String getDest() {
		String dest = getStringField("DEST");
		if (dest != null) {
			File file = new File(dest);
			file.getParentFile().mkdirs();
		}
		return dest;
	}
	
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

    protected void comparePdf(String outPdf, String cmpPdf) throws Exception {
    	if (cmpPdf == null || cmpPdf.length() == 0) return;
        CompareTool compareTool = new CompareTool(outPdf, cmpPdf);
        String outPath = "./target/" + new File(outPdf).getParent();
        new File(outPath).mkdirs();
        addError(compareTool.compare(outPdf, cmpPdf, outPath, differenceImagePrefix));
        addError(compareTool.compareDocumentInfo(outPdf, cmpPdf));
        addError(compareTool.compareLinks(outPdf, cmpPdf));

        if (errorMessage != null) Assert.fail(errorMessage);
    }
	
    protected void assertPdf(String outPdf) throws Exception {};

    protected String getCmpPdf() { return ""; }

    private void addError(String error) {
        if (error != null && error.length() > 0) {
            if (errorMessage == null)
                errorMessage = "";
            else
                errorMessage += "\n";

            errorMessage += error;
        }
    }

    protected void deleteDirectory(File path) {
        if (path == null)
            return;
        if (path.exists()) {
            for (File f : path.listFiles()) {
                if (f.isDirectory()) {
                    deleteDirectory(f);
                    f.delete();
                } else {
                    f.delete();
                }
            }
            path.delete();
        }
    }

    protected void deleteFiles(File path) {
        if (path != null && path.exists()) {
            for (File f : path.listFiles()) {
                f.delete();
            }
        }
    }
}
