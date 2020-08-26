package ru.otus.service;

import org.springframework.lang.Nullable;

public interface IOService {

    void out(String message);
    void out(String message, @Nullable Object[] args);
    void outText(String message);
    String readString();

}
