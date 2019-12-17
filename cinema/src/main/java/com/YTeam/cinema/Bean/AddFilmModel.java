package com.YTeam.cinema.Bean;

import java.util.Date;

public class AddFilmModel {
    public String name;
    public String type;
    public String director;
    public String cast;
    public String description;
    public String photo;
    public String date;
    public float rating;
    public String genre;
    public Integer duration;
    public Integer ageLimit;

    public AddFilmModel(String name, String type, String director, String cast, String description, String photo, String date, float rating, String genre, Integer duration, Integer ageLimit) {
        this.name = name;
        this.type = type;
        this.director = director;
        this.cast = cast;
        this.description = description;
        this.photo = photo;
        this.date = date;
        this.rating = rating;
        this.genre = genre;
        this.duration = duration;
        this.ageLimit = ageLimit;
    }
}