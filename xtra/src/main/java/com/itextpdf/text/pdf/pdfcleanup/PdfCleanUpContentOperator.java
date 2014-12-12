package com.itextpdf.text.pdf.pdfcleanup;

import com.itextpdf.text.DocWriter;
import com.itextpdf.text.pdf.*;
import com.itextpdf.text.pdf.parser.ContentOperator;
import com.itextpdf.text.pdf.parser.PdfContentStreamProcessor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PdfCleanUpContentOperator implements ContentOperator {

    static private final byte[] TStar = DocWriter.getISOBytes("T*\n");
    static private final byte[] Tw = DocWriter.getISOBytes(" Tw ");
    static private final byte[] TcTStar = DocWriter.getISOBytes(" Tc T*\n");
    static private final byte[] TJ = DocWriter.getISOBytes("] TJ\n");
    static private final byte[] Tc = DocWriter.getISOBytes(" Tc\n");

    static private final Set<String> textShowingOperators = new HashSet<String>() {{
        add("TJ");
        add("Tj");
        add("'");
        add("\"");
    }};

    protected PdfCleanUpRenderListener cleanUpStrategy;
    protected ContentOperator originalContentOperator;

    public static void populateOperators(PdfContentStreamProcessor contentProcessor,
                                         PdfCleanUpRenderListener pdfCleanUpRenderListener) {
        String[] operators = new String[]{
                PdfContentStreamProcessor.DEFAULTOPERATOR, "q", "Q", "g", "G", "rg", "RG", "k", "K",
                "cs", "CS", "sc", "SC", "scn", "SCN", "cm", "gs", "Tc", "Tw", "Tz", "TL", "Tf", "Tr",
                "Ts", "BT", "ET", "BMC", "BDC", "EMC", "Td", "TD", "Tm", "T*", "Tj", "'", "\"", "TJ", "Do"
        };
        for (String operator : operators) {
            PdfCleanUpContentOperator contentOperator = new PdfCleanUpContentOperator(pdfCleanUpRenderListener);
            contentOperator.originalContentOperator = contentProcessor.registerContentOperator(operator, contentOperator);
        }
    }

    public PdfCleanUpContentOperator(PdfCleanUpRenderListener cleanUpStrategy) {
        this.cleanUpStrategy = cleanUpStrategy;
    }

    public void invoke(PdfContentStreamProcessor pdfContentStreamProcessor, PdfLiteral operator, ArrayList<PdfObject> operands) throws Exception {
        PdfContentByte canvas = cleanUpStrategy.getContext().getCanvas();
        PRStream xFormStream = null;
        if ("Do".equals(operator.toString())) {
            if (operands.size() == 2 && operands.get(0).isName()) {
                PdfDictionary xObjResources = cleanUpStrategy.getContext().getResources().getAsDict(PdfName.XOBJECT);
                if (xObjResources != null) {
                    PdfStream xObj = xObjResources.getAsStream((PdfName) operands.get(0));
                    if (xObj instanceof PRStream && xObj.getAsName(PdfName.SUBTYPE) != null &&
                            xObj.getAsName(PdfName.SUBTYPE).compareTo(PdfName.FORM) == 0) {
                        xFormStream = (PRStream) xObj;
                        cleanUpStrategy.registerNewContext(xObj.getAsDict(PdfName.RESOURCES), null);
                    }
                }
            }
        }
        originalContentOperator.invoke(pdfContentStreamProcessor, operator, operands);
        List<PdfCleanUpContentChunk> chunks = cleanUpStrategy.getChunks();
        boolean disableOutput = false;
        if (xFormStream != null) {
            xFormStream.setData(cleanUpStrategy.getContext().getCanvas().toPdf(cleanUpStrategy.getContext().getCanvas().getPdfWriter()));
            cleanUpStrategy.popContext();
            canvas = cleanUpStrategy.getContext().getCanvas();
        }
        if ("Do".equals(operator.toString())) {
            if (chunks.size() > 0 && chunks.get(0).isImage() && !chunks.get(0).isVisible())
                disableOutput = true;
        } else if ("Tc".equals(operator.toString())) {
            cleanUpStrategy.getContext().setCharSpacing((PdfNumber)operands.get(0));
        } else if ("\"".equals(operator.toString())) {
            cleanUpStrategy.getContext().setCharSpacing((PdfNumber)operands.get(1));
        } else if (textShowingOperators.contains(operator.toString()) && !allChunksAreVisible(cleanUpStrategy.getChunks())) {
            disableOutput = true;
            if (operator.toString().equals("'")) {
                canvas.getInternalBuffer().append(TStar);
            } else if ("\"".equals(operator.toString())) {
                operands.get(0).toPdf(canvas.getPdfWriter(), canvas.getInternalBuffer());
                canvas.getInternalBuffer().append(Tw);
                operands.get(1).toPdf(canvas.getPdfWriter(), canvas.getInternalBuffer());
                canvas.getInternalBuffer().append(TcTStar);
                cleanUpStrategy.getContext().setCharSpacing((PdfNumber)operands.get(1));
            }
            canvas.setCharacterSpacing(0);
            canvas.getInternalBuffer().append((byte) '[');
            PdfCleanUpContentChunk prevChunk = null;
            for (PdfCleanUpContentChunk chunk : chunks) {
                if (prevChunk != null) {
                    canvas.getInternalBuffer().append(-(chunk.getStartX() - prevChunk.getEndX()) * 1000 / chunk.getSize()).append(' ');
                }
                if (chunk.isVisible()) {
                    chunk.getString().toPdf(canvas.getPdfWriter(), canvas.getInternalBuffer());
                    canvas.getInternalBuffer().append(' ');
                } else {
                    float length = chunk.getEndX() - chunk.getStartX();
                    canvas.getInternalBuffer().append(-length * 1000 / chunk.getSize()).append(' ');
                }
                prevChunk = chunk;
            }
            canvas.getInternalBuffer().append(TJ);
            cleanUpStrategy.getContext().getCharSpacing().toPdf(canvas.getPdfWriter(), canvas.getInternalBuffer());
            canvas.getInternalBuffer().append(Tc);
        }
        if (!disableOutput) {
            int index = 0;
            for (PdfObject o : operands) {
                o.toPdf(canvas.getPdfWriter(), canvas.getInternalBuffer());
                canvas.getInternalBuffer().append(operands.size() > ++index ? (byte) ' ' : (byte) '\n');
            }
        }
        chunks.clear();
    }

    private boolean allChunksAreVisible(List<PdfCleanUpContentChunk> chunks) {
        for (PdfCleanUpContentChunk chunk : chunks) {
            if (!chunk.isVisible())
                return false;
        }
        return true;
    }

}
