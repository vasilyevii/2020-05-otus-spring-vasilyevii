package ru.otus.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.DemoHw12;
import ru.otus.model.Book;
import ru.otus.service.BookService;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(BookController.class)
@EnableAutoConfiguration(exclude=DemoHw12.class)
public class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    @WithMockUser(
            username = "admin"
    )
    @Test
    public void shouldOkListPage() throws Exception {

        List<Book> bookDtoList = new ArrayList<>();
        Mockito.when(bookService.findAllBooks()).thenReturn(bookDtoList);

        mockMvc.perform(get("/", "/books/list"))
                .andExpect(status().isOk());
    }
}