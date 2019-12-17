package com.YTeam.cinema.controller;

import com.YTeam.cinema.Bean.AddFilmModel;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import postgresql.PSQLConnection;

import javax.xml.transform.Result;
import java.lang.reflect.Member;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

@Controller
public class AddFilmController {
    private PSQLConnection connection ;
    private Statement stat;

    public AddFilmController() throws SQLException {
        this.connection = new PSQLConnection();
        this.stat=connection.getConnection().createStatement();
    }
    public AddFilmController( boolean i){    }
    public void setStat(Statement s) {
        stat=s;
    }

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

        String query="select add_film('"+
                film.name+"','"+
                film.type+"','"+
                film.director+"','"+
                film.cast+"','"+
                film.description+"',to_date('"+
                film.date+"','yyyy-mm-dd'),'"+
                film.photo+"',"+
                (int)film.rating+",'"+
                film.genre+"',"+
                film.duration+","+
                film.ageLimit+
                ")";
        ResultSet rs=stat.executeQuery(query);
        rs.next();
        int a=rs.getInt(1);
        if(a==0){
            modelAndView.addObject("message","Фильм успешно добавлен.");
        }
        else {
            modelAndView.addObject("message","Ошибка добавления");
        }


        return modelAndView;
    }
}
