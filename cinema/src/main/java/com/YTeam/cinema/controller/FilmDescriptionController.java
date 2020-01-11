package com.YTeam.cinema.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.YTeam.cinema.postgresql.PSQLConnection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

@Controller
public class FilmDescriptionController {
    @GetMapping("/description")
    public String getFilmDescription(
            @RequestParam(name="film_id", required=false) int film_id,
            Map<String, Object> model
    ) throws SQLException {
        ArrayList<Object> film = new ArrayList<>();
        ResultSet result = PSQLConnection.getFilmQueryLimit1(film_id);

        while (result.next()){
            film.add(result.getString(1));
            film.add(result.getString(2));
            film.add(result.getInt(3));
            film.add(result.getInt(4));
            film.add(result.getString(5));
            film.add(result.getString(6));
            film.add(result.getString(7));
            film.add(result.getString(8));
            film.add(result.getString(9));
            film.add(result.getInt(10));
        }

        model.put("film", film);
        return "WEB-INF/pages/filmDescription";
    }
}
