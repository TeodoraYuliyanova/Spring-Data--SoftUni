package com.example.gamestore.domain.dtos;

import java.math.BigDecimal;
import java.time.LocalDate;

import static com.example.gamestore.Constants.ExceptionMessages.*;

public class GameDTO {

    private String title;
    private BigDecimal price;
    private double size;
    private String trailer;
    private String imageThumbnailURL;
    private String description;
    private LocalDate releaseDate;

    public GameDTO() {
    }

    public GameDTO(String title, BigDecimal price, double size, String trailer, String imageThumbnailURL, String description, LocalDate releaseDate) {
        setTitle(title);
        setPrice(price);
        setSize(size);
        setTrailer(trailer);
        setImageThumbnailURL(imageThumbnailURL);
        setDescription(description);
        setReleaseDate(releaseDate);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        if (!Character.isUpperCase(title.charAt(0)) || title.length() < 3 || title.length() > 100){
            throw new IllegalArgumentException(INVALID_GAME_TITLE);
        }

        this.title = title;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        if (price.longValue() < 0){
            throw new IllegalArgumentException(INVALID_PRICE);
        }

        this.price = price;
    }

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        if (size < 0){
            throw new IllegalArgumentException(INVALID_SIZE);
        }

        this.size = size;
    }

    public String getTrailer() {
        return trailer;
    }

    public void setTrailer(String trailer) {
        if (trailer.length() != 11){
            throw new IllegalArgumentException(INVALID_TRAILER);
        }

        this.trailer = trailer;
    }

    public String getImageThumbnailURL() {
        return imageThumbnailURL;
    }

    public void setImageThumbnailURL(String imageThumbnailURL) {
        if (!imageThumbnailURL.startsWith("http://") && !imageThumbnailURL.startsWith("https://")){
            throw new IllegalArgumentException(INVALID_THUMBNAIL_URL);
        }

        this.imageThumbnailURL = imageThumbnailURL;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        if (description.length() < 20){
            throw new IllegalArgumentException(INVALID_DESCRIPTION);
        }

        this.description = description;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

}
