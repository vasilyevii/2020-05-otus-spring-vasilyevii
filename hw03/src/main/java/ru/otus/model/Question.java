package ru.otus.model;

public class Question {

    private final String text;
    private final String inputType;
    private final String answer;

    public Question(String text, String inputType, String answer) {
        this.text = text;
        this.inputType = inputType;
        this.answer = answer;
    }

    public String getText() {
        return text;
    }

    public String getInputType() {
        return inputType;
    }

    public String getAnswer() {
        return answer;
    }
}
