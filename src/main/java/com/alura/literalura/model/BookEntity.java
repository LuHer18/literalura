package com.alura.literalura.model;


import jakarta.persistence.*;


@Entity
@Table(name = "books")
public class BookEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;

    @Column(name = "download_count")
    private Integer downloadCount;

    private String languages;

    @OneToOne(mappedBy = "book", cascade = CascadeType.ALL)
    private AuthorEntity author;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getDownloadCount() {
        return downloadCount;
    }

    public void setDownloadCount(Integer downloadCount) {
        this.downloadCount = downloadCount;
    }

    public String getLanguages() {
        return languages;
    }

    public void setLanguages(String languages) {
        this.languages = languages;
    }

    public AuthorEntity getAuthor() {
        return author;
    }

    public void setAuthor(AuthorEntity author) {
        this.author = author;
    }

    @Override
    public String toString() {
        return "---------Libro --------------" +
                ", title='" + title + '\'' +
                ", downloadCount=" + downloadCount +
                ", languages='" + languages + '\'' +
                ", author=" + author +
                "------------------------------";
    }
}
