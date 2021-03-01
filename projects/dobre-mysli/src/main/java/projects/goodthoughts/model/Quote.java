package projects.goodthoughts.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "quotes",
        uniqueConstraints = @UniqueConstraint(name = "unique_quote", columnNames = {"content", "author"}))
public class Quote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;
    private String translation;
    private String author;
    private String category;
    private LocalDateTime createdOn;

    public Quote() {
    }

    public Quote(String content, String translation, String author, String category, LocalDateTime createdOn) {
        this.content = content;
        this.translation = translation;
        this.author = author;
        this.category = category;
        this.createdOn = createdOn;
    }

    @PrePersist
    public void setCreatedOn() {
        this.createdOn = LocalDateTime.now();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Quote quote = (Quote) o;

        if (content != null ? !content.equals(quote.content) : quote.content != null) return false;
        return author != null ? author.equals(quote.author) : quote.author == null;
    }

    @Override
    public int hashCode() {
        int result = content != null ? content.hashCode() : 0;
        result = 31 * result + (author != null ? author.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Quote{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", translation='" + translation + '\'' +
                ", author='" + author + '\'' +
                ", category='" + category + '\'' +
                ", createdOn=" + createdOn +
                '}';
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTranslation() {
        return translation;
    }

    public void setTranslation(String translation) {
        this.translation = translation;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
