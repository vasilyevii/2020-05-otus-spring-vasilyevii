package ru.otus.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.otus.dto.CommentDto;
import ru.otus.model.Comment;
import ru.otus.service.BookService;
import ru.otus.service.CommentService;
import ru.otus.service.NotFoundException;

import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;
    private final BookService bookService;

    @GetMapping("/comments/list")
    public String listPage(@RequestParam("bookId") long bookId, Model model) {
        model.addAttribute("commentList", commentService.findAllCommentsByBookId(bookId)
                .stream()
                .map(commentService::CommentToCommentDto)
                .collect(Collectors.toList()));
        model.addAttribute("book", bookService.findBookById(bookId).orElseThrow(NotFoundException::new));
        return "comments/list";
    }

    @GetMapping("/comments/add")
    public String addPage(@RequestParam("bookId") long bookId, Model model) {
        model.addAttribute("comment", new CommentDto(0, bookId, null, null));
        return "comments/add";
    }

    @PostMapping("/comments/add")
    public String addComment(CommentDto commentDto, Model model, RedirectAttributes redirectAttributes) {
        Comment comment = commentService.commentDtoToComment(commentDto);
        commentService.addComment(comment);
        redirectAttributes.addAttribute("bookId", commentDto.getBookId());
        return "redirect:/comments/list";
    }
}
