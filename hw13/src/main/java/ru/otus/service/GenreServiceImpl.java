package ru.otus.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.stereotype.Service;
import ru.otus.model.Genre;
import ru.otus.repository.GenreRepository;
import ru.otus.security.AclService;

import java.util.Arrays;

@RequiredArgsConstructor
@Service
public class GenreServiceImpl implements GenreService {

    private final GenreRepository repo;
    private final AclService aclService;

    @Override
    public Genre getGenre(Genre genre) {
        return repo.findByName(genre.getName())
                .stream().findFirst().orElseGet(() -> {
                    repo.save(genre);
                    aclService.addAclPermissions(genre, Arrays.asList(BasePermission.READ, BasePermission.WRITE));
                    return genre;
                });
    }
}
