package ru.otus.dao;

import java.io.IOException;
import java.util.List;

public interface QuizDao {

    List<List<String>> read() throws IOException;

}
