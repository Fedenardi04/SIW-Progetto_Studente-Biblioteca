package it.uniroma3.siw.dto;

import java.time.LocalDate;

import it.uniroma3.siw.model.Review;

public class ReviewDTO {

    private Long id;
    private String text;
    private LocalDate creationDate;
    private String username;

    public ReviewDTO() {
    }

    public ReviewDTO(Review review) {
        this.id = review.getId();
        this.text = review.getText();
        this.creationDate = review.getCreationDate();

        if (review.getUser() != null && review.getUser().getCredentials() != null) {

            this.username = review.getUser().getCredentials().getUsername();
        } else {
            this.username = "Utente sconosciuto";
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}