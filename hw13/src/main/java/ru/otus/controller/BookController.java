package ru.otus.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.dto.BookDto;
import ru.otus.model.Book;
import ru.otus.security.BookSecurityService;
import ru.otus.service.NotFoundException;
import ru.otus.service.BookService;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;
    private final BookSecurityService bookSecurityService;

    @GetMapping("/books/list")
    public String listPage(Model model) {
        bookSecurityService.onlyAdminOrUser();
        model.addAttribute("bookList", bookService.findAllBooks()
                .stream()
                .map(bookService::bookToBookDto)
                .collect(Collectors.toList()));
        return "books/list";
    }

    @GetMapping("/books/add")
    public String addPage(Model model) {
        bookSecurityService.onlyAdminOrUser();
        model.addAttribute("book", new BookDto());
        return "books/add";
    }

    @PostMapping("/books/add")
    public String addBook(BookDto bookDto, Model model) {
        bookSecurityService.onlyAdminOrUser();
        Book book = bookService.saveBook(bookService.bookDtoToBook(bookDto));
        model.addAttribute(bookService.bookToBookDto(book));
        return "redirect:/books/list";
    }

    @GetMapping("/books/edit")
    public String editPage(@RequestParam("id") long bookId, Model model) {
        bookSecurityService.onlyAdminOrUser();
        Book book = bookService.findBookById(bookId).orElseThrow(NotFoundException::new);
        model.addAttribute("book", bookService.bookToBookDto(book));
        return "books/edit";
    }

    @PostMapping("/books/edit")
    public String editBook(BookDto bookDto, Model model) {
        Book book = bookService.saveBook(bookService.bookDtoToBook(bookDto));
        model.addAttribute(bookService.bookToBookDto(book));
        return "redirect:/books/list";
    }

    @PostMapping("/books/delete")
    public String deleteBook(@RequestParam("id") long bookId, Model model) {
        bookService.deleteBookById(bookId);
        return "redirect:/books/list";
    }
}
