package bookservice;
import java.util.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookService service;

    public BookController(BookService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public Book getBook(@PathVariable int id) throws Exception {
        return service.getBook(id);
    }

    @PostMapping
    public String saveBook(@RequestBody Book book) throws Exception {
        service.saveBook(book);
        return "Book inserted into Excel";
    }
    
    @GetMapping
    public List<Book> getAllBooks() throws Exception {
        return service.getAllBooks();
    }
}