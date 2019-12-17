package com.YTeam.cinema;

import java.util.Date;

public class FilmSession {
//    public int shedule_id;
//    public int hall_id;
//    public int film_id;
    public String name;
//    public String TYPE;
//    public String director;
//    public String cast;
//    public String description;
    public String photo;
    public Date day;
    public String start_time;


    public FilmSession(/*int shedule_id, int hall_id, int film_id, */String name, /*String TYPE, String director, String cast, String dsecription, */String photo, Date day, String start_time) {
//        this.shedule_id = shedule_id;
//        this.hall_id = hall_id;
//        this.film_id = film_id;
        this.name = name;
//        this.TYPE = TYPE;
//        this.director = director;
//        this.cast = cast;
//        this.dsecription = dsecription;
        this.photo = photo;
        this.day = day;
        this.start_time = start_time;
    }
}
