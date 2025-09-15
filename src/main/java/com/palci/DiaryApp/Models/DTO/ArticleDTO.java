package com.palci.DiaryApp.Models.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.time.LocalTime;

public class ArticleDTO {

    private long articleId;

    @NotBlank(message = "Fill in the title")
    @Size(max = 100, message = "Title is too long")
    private String title;

    @NotNull(message = "Fill in the date")
    private LocalDate date; // TODO maybe add title

    @NotBlank(message = "Fill in the description")
    @Size(max = 140, message = "Description is too long")
    private String description;

    private int mood;

    @NotBlank(message = "Fill in the content")
    private String text;

    private boolean top; //TODO add time

    @NotNull(message = "Fill in the time")
    private LocalTime time;



    // Getters and setters

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getMood() {
        return mood;
    }

    public void setMood(int mood) {
        this.mood = mood;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public long getArticleId() {
        return articleId;
    }

    public void setArticleId(long articleId) {
        this.articleId = articleId;
    }

    public boolean isTop() {
        return top;
    }

    public void setTop(boolean top) {
        this.top = top;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }
}
