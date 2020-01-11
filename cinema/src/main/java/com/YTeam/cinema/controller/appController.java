package com.YTeam.cinema.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.YTeam.cinema.postgresql.PSQLConnection;
//import com.google.gson.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.*;

@Controller
public class appController {
    private PSQLConnection connection ;
    private Statement stat;
    public appController() throws SQLException {
        connection = new PSQLConnection();
        stat=connection.getConnection().createStatement();
    }
    public appController(boolean l) {    }
    public appController(PSQLConnection connection, Statement stat) {
        this.connection = connection;
        this.stat = stat;
    }

    @GetMapping("/")
    public String getFilms(
            Map<String, Object> model
    ) throws SQLException{

        ResultSet rs=PSQLConnection.getFilmsShedule();
        Map<String, TreeMap<String, ArrayList<Object>>> days = new TreeMap<>();
        Map<String, Map<String, TreeMap<Integer, String>>> schedule = new TreeMap<>();

        while (rs.next()){
            ArrayList<Object> list = new ArrayList<>();
            list.add(Double.parseDouble(rs.getString(2)));
            list.add(rs.getString(3));
            list.add(rs.getString(4));
            list.add(rs.getString(5));
            list.add(rs.getString(7));
            list.add(rs.getInt(10));

            String name = rs.getString(1);
            String day = rs.getString(6);
            TreeMap<String, TreeMap<Integer, String>> nameFilm = new TreeMap<>();

            if (!days.containsKey(day) || days.isEmpty()) {
                TreeMap<String, ArrayList<Object>> nameMap = new TreeMap<>();
                nameMap.put(name, list);
                days.put(day, nameMap);
            } else {
                if (!days.get(day).containsKey(name)) {
                    days.get(day).put(name, list);
                }
            }

            if (!schedule.containsKey(day) || schedule.isEmpty()) {
                TreeMap<Integer, String> scheduleId = new TreeMap<>();
                scheduleId.put(rs.getInt(9), rs.getString(8).substring(0, 5));
                nameFilm.put(name, scheduleId);
                schedule.put(day, nameFilm);
            } else {
                try {
                    schedule.get(day).get(name).put(rs.getInt(9), rs.getString(8).substring(0, 5));
                } catch (Exception e) {
                    TreeMap<Integer, String> scheduleId = new TreeMap<>();
                    scheduleId.put(rs.getInt(9), rs.getString(8).substring(0, 5));
                    schedule.get(day).put(name, scheduleId);
                }
            }
        }

        model.put("schedule", schedule);
        model.put("days", days);
        return "WEB-INF/pages/afisha";
    }

    @GetMapping("/oneFilmSeance")
    public String getOneFilm(
            @RequestParam(name="film_id", required=false, defaultValue="4") int film_id,
            Map<String, Object> model
    ) throws SQLException, ParseException {
        ResultSet res = PSQLConnection.getOneFilmSeanse(film_id);
        Map<String, TreeMap<String, ArrayList<Object>>> days = new TreeMap<>();
        Map<String, Map<String, TreeMap<Integer, String>>> schedule = new TreeMap<>();

        while (res.next()){
            ArrayList<Object> list = new ArrayList<>();
            list.add(Integer.parseInt(res.getString(2)));
            list.add(res.getString(3));
            list.add(res.getString(4));
            list.add(res.getString(5));
            list.add(res.getString(7));
            list.add(res.getInt(10));

            String name = res.getString(1);
            String day = res.getString(6);
            TreeMap<String, TreeMap<Integer, String>> nameFilm = new TreeMap<>();

            if (!days.containsKey(day) || days.isEmpty()) {
                TreeMap<String, ArrayList<Object>> nameMap = new TreeMap<>();
                nameMap.put(name, list);
                days.put(day, nameMap);
            } else {
                if (!days.get(day).containsKey(name)) {
                    days.get(day).put(name, list);
                }
            }

            if (!schedule.containsKey(day) || schedule.isEmpty()) {
                TreeMap<Integer, String> scheduleId = new TreeMap<>();
                scheduleId.put(res.getInt(9), res.getString(8).substring(0, 5));
                nameFilm.put(name, scheduleId);
                schedule.put(day, nameFilm);
            } else {
                try {
                    schedule.get(day).get(name).put(res.getInt(9), res.getString(8).substring(0, 5));
                } catch (Exception e) {
                    TreeMap<Integer, String> scheduleId = new TreeMap<>();
                    scheduleId.put(res.getInt(9), res.getString(8).substring(0, 5));
                    schedule.get(day).put(name, scheduleId);
                }
            }
        }

        model.put("schedule", schedule);
        model.put("days", days);
        return "WEB-INF/pages/oneFilmSeance";
    }



}