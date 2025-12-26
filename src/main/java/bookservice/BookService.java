package bookservice;
import java.util.*;
import org.apache.commons.csv.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.*;

@Service
public class BookService {

    private static final String CSV_FILE = "src/main/resources/Book1.csv";
    private static final String EXCEL_FILE = "src/main/resources/books_output.xlsx";

    public Book getBook(int id) throws Exception {
        Reader reader = Files.newBufferedReader(Paths.get(CSV_FILE));
        Iterable<CSVRecord> records =
                CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(reader);

        for (CSVRecord record : records) {
            if (Integer.parseInt(record.get("id")) == id) {
                Book book = new Book();
                book.id = id;
                book.title = record.get("title");
                book.author = record.get("author");
                book.price = Double.parseDouble(record.get("price"));
                return book;
            }
        }
        throw new RuntimeException("Book not found");
    }

    public void saveBook(Book book) throws Exception {
        XSSFWorkbook workbook =
                new XSSFWorkbook(new FileInputStream(EXCEL_FILE));
        var sheet = workbook.getSheetAt(0);
        var row = sheet.createRow(sheet.getLastRowNum() + 1);

        row.createCell(0).setCellValue(book.id);
        row.createCell(1).setCellValue(book.title);
        row.createCell(2).setCellValue(book.author);
        row.createCell(3).setCellValue(book.price);

        workbook.write(new FileOutputStream(EXCEL_FILE));
        workbook.close();
    }
    
    
    public List<Book> getAllBooks() throws Exception {

        List<Book> books = new ArrayList<>();

        Reader reader = Files.newBufferedReader(Paths.get(CSV_FILE));
        Iterable<CSVRecord> records =
                CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(reader);

        for (CSVRecord record : records) {
            Book book = new Book();
            book.id = Integer.parseInt(record.get("id"));
            book.title = record.get("title");
            book.author = record.get("author");
            book.price = Double.parseDouble(record.get("price"));
            books.add(book);
        }

        return books;
    }
}