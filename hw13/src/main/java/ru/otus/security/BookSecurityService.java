package ru.otus.security;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
public class BookSecurityService {

    @PreAuthorize("hasRole('USER')")
    public void onlyUser() {}

    @PreAuthorize("hasRole('ADMIN')")
    public void onlyAdmin() {}

    @PreAuthorize("hasRole('ADMIN') || hasRole('USER')")
    public void onlyAdminOrUser() {}

}
