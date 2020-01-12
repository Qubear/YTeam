package com.YTeam.cinema.controller;

import com.YTeam.cinema.Bean.AddFilmModel;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import com.YTeam.cinema.postgresql.PSQLConnection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@Controller
public class AddFilmController {

    public AddFilmController()  {    }

    @GetMapping("/addFilm")
    public String getAfisha(
    ) {
        return "WEB-INF/pages/addFilm";
    }

    @PostMapping("/addFilm")
    public ModelAndView postAfisha(
            @ModelAttribute("film") AddFilmModel film
            ) throws SQLException {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("WEB-INF/pages/addFilm");

            if(PSQLConnection.AddFilm(film)){
                modelAndView.addObject("message","Фильм успешно добавлен.");
            }
            else {
                modelAndView.addObject("message","Ошибка добавления");
            }

            return modelAndView;


    }
}
