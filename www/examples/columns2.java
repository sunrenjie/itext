import java.io.*;
import java.awt.Color;
import com.lowagie.text.*;
import com.lowagie.text.pdf.*;

public class columns2 {
    static public String headings[] = {
        "Book/Product Model:",
        "Sales Handle:",
        "Why We Published this Book/Product Model:",
        "Key benefits:",
        "About the Author(s):",
        "Technology/Topic Overview: ",
        "Book/Product Content Summary:", 
        "Audience:",
        "What's on the CD/DVD/Web:"
    };

    static public String texts[] = {
        "Ideally, choose one title (2-3 if absolutely necessary) that this book should perform like. Include full title, ISBN, author, and any sell through numbers if possible.",
        "One line description about the sales.",
        "Brief description (one-two lines) on the importance of this book to the audience.",
        "What benefit does this book provide to the consumer? (expert advice, speed, fun, productivity). Why should the Retailer/Wholesaler select this book over its competition? What are the unique features about this book should be highlighted? What makes this book different, better? From other books and the previous edition?",
        "What makes this person so special?  Is she/he an expert, creator of the technology, educational leader, etc.? What is their background, and what relevant experiences do they have to make them the BEST choice? Have he/she/they won awards or been recognized in any way. Other books poublished by the author.\n1. Book one.\n2. Book two.",
        "In brief two to five line description of the technology, topic or relevant information. Please keep descriptions succinct.",
        "Ideal describe the contents of this book. What will this book do for the reader? Will this book help them optimize their system? Increase productivity? offer tips and stragegies?",
        "Who is your intended customer? Experts? Power users? Business professionals? Programmers? What are the demographics?",
        "What is included on the Cd or Web site? Why is it necessary and what will it do for the purchaser (source code, examples, case studies)?\nIs there a value that can be associated with what is on the CD/DVD or Web?"
    };

    public static void main(String[] args)
    {
        Document document = new Document(PageSize.LETTER, 90, 54, 72, 72);
        try {
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("c:\\columns2.pdf"));
            float gutter = 20;
            int numColumns = 3;
            float fullWidth = document.right() - document.left();
            float columnWidth = (fullWidth - (numColumns - 1) * gutter) / numColumns;
            float allColumns[] = new float[numColumns]; // left
            for (int k = 0; k < numColumns; ++k) {
                allColumns[k] = document.left() + (columnWidth + gutter) * k;
            }
            // set the fonts
            Font font24B = new Font(Font.TIMES_NEW_ROMAN, 24, Font.BOLD);
            Font font10B = new Font(Font.TIMES_NEW_ROMAN, 10, Font.BOLD);
            Font font14B = new Font(Font.TIMES_NEW_ROMAN, 14, Font.BOLD, new Color(255, 0, 0));
            Font font9 = new Font(Font.TIMES_NEW_ROMAN, 9);
            Font font11 = new Font(Font.TIMES_NEW_ROMAN, 11);
            
            document.open();            
            // get the stream content
            PdfContentByte cb = writer.getDirectContent();
            // headers
            Phrase fullTitle = new Phrase("Full Title", font24B);
            float currentY = document.top();
            ColumnText ct = new ColumnText(cb);
            ct.setSimpleColumn(fullTitle, document.left(), 0, document.right(), document.top(), 24, Element.ALIGN_JUSTIFIED);
            ct.go();
            currentY = ct.getYLine();
            currentY -= 4;
            cb.setLineWidth(1);
            cb.moveTo(document.left(), currentY);
            cb.lineTo(document.right(), currentY);
            cb.stroke();
            currentY -= 4;
            ct.setYLine(currentY);
            ct.addText(new Chunk("Author: Name of the author comes here", font10B));
            ct.setLeading(10);
            ct.go();
            currentY = ct.getYLine();
            currentY -= 15;
            float topColumn = currentY;
            for (int k = 1; k < numColumns; ++k) {
                float x = allColumns[k] - gutter / 2;
                cb.moveTo(x, topColumn);
                cb.lineTo(x, document.bottom());
            }
            cb.stroke();
            Image img = Image.getInstance("cover.png");
            cb.addImage(img, img.scaledWidth(), 0, 0, img.scaledHeight(), document.left(), currentY - img.scaledHeight());
            currentY -= img.scaledHeight() + 10;
            ct.setYLine(currentY);
            ct.addText(new Chunk("Key Data:", font14B));
            ct.go();
            currentY = ct.getYLine();
            currentY -= 4;
            float pad = 4;
            float tableY = currentY;
            int rows = 15;
            ct.setSimpleColumn(new Phrase("Imprint Name:\nSeries Name:\nISBN:\nUPC Code:\nEAN #\nPrice:\nPage Count:\nDiscount:\nTrim Size:\nCover:\nInterior Color:\nMedia with book:\nAuthor(s):\nEditor:\nPub Date:", font9),
                document.left() + pad, 0, document.right(), currentY + pad, 9 + pad, Element.ALIGN_LEFT);
            ct.go();
            ct.setSimpleColumn(new Phrase("Prentice Hall\n\nHall\n0789718103\n0786718103\n49.99\n500\n10%\n420x340\nHard\nnone\nCD\nBen Forta\nBen Forta\n06/05/1998", font9),
                document.left() + pad + columnWidth / 2, document.bottom(), document.right(), currentY + pad, 9 + pad, Element.ALIGN_LEFT);
            ct.go();
            cb.setLineWidth(0.5f);
            float hh = rows * (9 + pad);
            cb.rectangle(document.left(), tableY - hh, columnWidth, hh);
            cb.moveTo(document.left() + columnWidth / 2, tableY);
            cb.lineTo(document.left() + columnWidth / 2, tableY - hh);
            for (int k = 1; k < 15; ++k) {
                cb.moveTo(document.left(), tableY - (9 + pad) * k);
                cb.lineTo(document.left() + columnWidth, tableY - (9 + pad) * k);
            }
            cb.stroke();
            currentY = tableY - hh - 20;
            for (int k = 0; k < headings.length; ++k) {
                ct.addText(new Chunk(headings[k] + "\n", font14B));
                ct.addText(new Chunk(texts[k] + "\n\n", font11));
            }

            int currentColumn = 0;
            ct.setSimpleColumn(allColumns[currentColumn], document.bottom(),
                allColumns[currentColumn] + columnWidth, currentY, 15, Element.ALIGN_JUSTIFIED);
            ct.setLeading(2, 1);
            for (;;) {
                int rc = ct.go();
                if ((rc & ColumnText.NO_MORE_TEXT) != 0)
                    break;
                // we run out of column. Let's go to another one
                ++currentColumn;
                if (currentColumn >= allColumns.length)
                    break;
                ct.setSimpleColumn(allColumns[currentColumn], document.bottom(),
                    allColumns[currentColumn] + columnWidth, topColumn, 15, Element.ALIGN_JUSTIFIED);
                ct.setLeading(2, 1);
            }
            
            document.close();
            System.out.println("The End.");
        }
        catch (Exception de) {
            System.err.println(de.getMessage());
        }
    }
}
