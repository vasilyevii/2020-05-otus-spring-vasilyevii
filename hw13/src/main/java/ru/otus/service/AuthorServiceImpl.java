package ru.otus.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.model.Author;
import ru.otus.repository.AuthorRepository;

@RequiredArgsConstructor
@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository repo;

    @Override
    public Author getAuthor(Author author) {
        return repo.findByName(author.getName())
                .stream().findFirst().orElseGet(() -> repo.save(author));
    }
}
