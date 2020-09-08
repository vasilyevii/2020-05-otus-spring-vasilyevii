package ru.otus.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.otus.model.User;
import ru.otus.service.UserService;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) {
        Optional<User> userByName = userService.findUserByName(username);
        if (userByName.isEmpty()) {
            throw new UsernameNotFoundException(username);
        }
        return new UserDetailsImpl(userByName.get());
    }
}
