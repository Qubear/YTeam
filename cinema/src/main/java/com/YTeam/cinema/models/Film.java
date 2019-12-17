package com.YTeam.cinema.models;

import javax.persistence.*;

@Entity
public class Film {

    @Id
    private int id;
    private String name;
    private float rating;
    private String photo;
    private String genre;
    private String duration;
    private String description;
    private int age_limit;

    public Film() {
    }

    public Film(String name, float rating, String photo, String genre, String duration, int age_limit) {
        this.name = name;
        this.rating = rating;
        this.photo = photo;
        this.genre = genre;
        this.duration = duration;
        this.age_limit = age_limit;
    }
}
