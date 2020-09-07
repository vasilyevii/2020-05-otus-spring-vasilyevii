import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.server.RouterFunction;
import reactor.core.publisher.Flux;
import ru.otus.dto.BookDto;
import ru.otus.route.BookRouter;
import ru.otus.service.BookService;

import java.util.Arrays;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {BookRouter.class})
public class BookEndpointTest {

    private static final BookDto FIRST_BOOK_DTO = new BookDto("1", "book1", "authors", "genre");
    private static final BookDto SECOND_BOOK_DTO = new BookDto("2", "book2", "authors", "genre");

    @MockBean
    private BookService bookService;

    @Autowired
    private RouterFunction route;

    @Test
    public void shouldGetAllBooks() {
        Mockito.when(bookService.findAll()).thenReturn(Flux.fromIterable(Arrays.asList(FIRST_BOOK_DTO, SECOND_BOOK_DTO)));
        WebTestClient webClient = WebTestClient.bindToRouterFunction(route).build();
        webClient.get().uri("/api/books")
                .header(HttpHeaders.ACCEPT, "application/json")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(BookDto.class);
    }
}
