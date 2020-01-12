package com.YTeam.cinema.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import com.YTeam.cinema.postgresql.PSQLConnection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

@Controller
public class ReturnTicketController {

    public ReturnTicketController() { }

    @GetMapping("/returnTicket")
    public String getAfisha(
            Map<String, Object> model)
    {
        return "WEB-INF/pages/returnTicket";
    }

    @PostMapping("/returnTicket")
    public ModelAndView postAfisha(
            @ModelAttribute("id") Integer id
    ) throws SQLException {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("WEB-INF/pages/returnTicket");

        int kek = PSQLConnection.ReturnTicket(id);
        if (kek == 0){

            modelAndView.addObject("state","Билет был возвращен!");
            return modelAndView;
        }

       modelAndView.addObject("state","Данный билет не может быть возвращен! \n Обратитесь к администратору");
        return modelAndView;
    }


}
