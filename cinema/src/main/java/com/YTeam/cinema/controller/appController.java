package com.YTeam.cinema.controller;

import com.YTeam.cinema.CheckTicket;
import com.YTeam.cinema.models.Film;
import com.YTeam.cinema.models.Sit;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import postgresql.PSQLConnection;
//import com.google.gson.*;

import java.lang.reflect.Member;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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

    public void setStat(Statement s) {
        stat=s;
    }

    @GetMapping("/afisha")
    public String getAfisha(
            @RequestParam(name="name", required=false, defaultValue="World") String name,
            Map<String, Object> model
    ) {
        model.put("name", name);
        return "WEB-INF/pages/afisha";
    }

    @PostMapping("/afisha")
    public String postAfisha(
            @RequestBody Member member
    ) {
        /*overload*/
        return "WEB-INF/pages/afisha";
    }

    @GetMapping("/")
    public String getFilms(
            Map<String, Object> model
    ) throws SQLException, ParseException {
        String q="select name, rating, photo, genre, duration, day, age_limit, start_time, shedule_id from get_films_shedule";
//        ArrayList<Object> films = new ArrayList<>();
        ResultSet rs = stat.executeQuery(q);
        Map<String, HashMap<String, ArrayList<Object>>> days = new HashMap<>();
        Map<String, Map<String, HashMap<Integer, String>>> schedule = new HashMap<>();
//        Map<String, ArrayList<Object>> schedule = new HashMap<>();

        while (rs.next()){
            ArrayList<Object> list = new ArrayList<>();
            list.add(Integer.parseInt(rs.getString(2)));
            list.add(rs.getString(3));
            list.add(rs.getString(4));
            list.add(rs.getString(5));
            list.add(rs.getString(7));
//            Map<Integer, String> start_time = new HashMap<>();
//            Map<String, Object> time = new HashMap<>();
//            start_time.put(rs.getInt(9), rs.getString(8));
//            time.put("time", start_time);
//            list.add(time);
            String name = rs.getString(1);
            String day = rs.getString(6);
            HashMap<String, HashMap<Integer, String>> nameFilm = new HashMap<>();


            if (!days.containsKey(day) || days.isEmpty()) {
                HashMap<String, ArrayList<Object>> nameMap = new HashMap<>();
                nameMap.put(name, list);
                days.put(day, nameMap);
            } else {
                if (!days.get(day).containsKey(name)) {
                    days.get(day).put(name, list);
                } else {
//                    kek = days.get(day).get(name).get(days.get(day).get(name).size()-1)[0].get("time").put(she);
                }
            }

            if (!schedule.containsKey(day) || schedule.isEmpty()) {
                HashMap<Integer, String> scheduleId = new HashMap<>();
                scheduleId.put(rs.getInt(9), rs.getString(8));
                nameFilm.put(name, scheduleId);
                schedule.put(day, nameFilm);
            } else {
                try {
                    schedule.get(day).get(name).put(rs.getInt(9), rs.getString(8).substring(0, 5));
                } catch (Exception e) {
                    HashMap<Integer, String> scheduleId = new HashMap<>();
                    scheduleId.put(rs.getInt(9), rs.getString(8).substring(0, 5));
                    schedule.get(day).put(name, scheduleId);
                }
//                schedule.get(day).get(start_time.get(0));
            }
        }

//        ArrayList<String> seances = new ArrayList<>();
//        Map<String, ArrayList<String>> days = new HashMap<>();
//        Map<String, Map<String, ArrayList<String>>> map = new HashMap<>();
//
//        for (FilmSession list: FilmSessionList) {
//            if (!map.containsKey(list.name)) {
////                map.put(list.name, )
//                seances.add(list.start_time);
//                days.put(list.day.toString(), seances);
//                map.put(list.name, days);
//            }
//        }

//        JsonArray jsonArray1 = new Gson().toJsonTree(FilmSessionList).getAsJsonArray();

//        System.out.println(jsonArray1);
//        for (FilmSession film: FilmSessionList) {
//
//        }

        model.put("schedule", schedule);
        model.put("days", days);
//        model.put("kek", kek);
        return "WEB-INF/pages/afisha";
    }

//    @RequestMapping("/")
//    public ModelAndView index () {
//        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.setViewName("index");
//        modelAndView.setView();
//        return modelAndView;
//    }

//    @GetMapping("/addFilm")
//    public String admin() {
//        return "WEB-INF/pages/admin";
//    }
//
//    @PostMapping("/addFilm")
//    public ModelAndView postAfisha(@ModelAttribute Film film, Model model) throws SQLException {
//        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.setViewName("WEB-INF/pages/addFilm");
//        String query="";
//        if(stat.executeQuery(query).getInt(1)==0){
//            modelAndView.addObject("message","Фильм успешно добавлен.");
//        }
//        else {
//            modelAndView.addObject("message","Ошибка добавления");
//        }
//
//        return modelAndView;
//    }

    @GetMapping("/SeetSelection")
    public String getSeetSelection(
            @RequestParam(name="shedule_id", required=false, defaultValue="18") int shedule_id,
            Map<String, Object> model
    ) throws SQLException {
        String q="select ID, plase_number,row_number,price,state from ticket  where shedule_id="+shedule_id+" order by row_number,plase_number";
        ArrayList<Sit> sitList = new ArrayList<>();
        ResultSet rs = stat.executeQuery(q);

        while (rs.next()){
            sitList.add(
               new Sit(
                    rs.getInt(1),
                    rs.getInt(2),
                    rs.getInt(3),
                    rs.getInt(4),
                    rs.getInt(5)
                )
            );
        }

        Map<Integer, HashMap<Integer, ArrayList<Integer>>> sit_num = new HashMap<>();

        for (Sit sit: sitList) {
            ArrayList<Integer> list = new ArrayList<Integer>();
            list.add(sit.ID);
            list.add(sit.price);
            list.add(sit.state);

            if (!sit_num.containsKey(sit.row_number) || sit_num.isEmpty()) {
                HashMap<Integer, ArrayList<Integer>> map = new HashMap<>();
                map.put(sit.place_number, list);
                sit_num.put(sit.row_number, map);
            } else {
                HashMap<Integer, ArrayList<Integer>> map = sit_num.get(sit.row_number);
                map.put(sit.place_number, list);

                sit_num.put(sit.row_number, map);
            }
        }

        model.put("sit_num", sit_num);
        return "WEB-INF/pages/SeetSelection";
    }

//    @GetMapping("/receipt")
//    public String receipt(
//            @RequestParam(name="tic", required=false, defaultValue="") String tickets,
//            Map<String, Object> model
//    ) {
//
//        String[] q=tickets.split("!");
//        for (String i :q
//             ) {
//            model.put("tic"+i, i);
//        }
//        return "WEB-INF/pages/receipt";
//    }

    @PostMapping("/SeetSelection")
    public ModelAndView getSeetSelection(
            @RequestParam("filter") String tickets_id, @RequestParam Map<String, Object> model
    ) throws SQLException, ParseException {
        ArrayList<CheckTicket> ticketList=new ArrayList<CheckTicket>();
        String[] q=tickets_id.split("!");
        //String upStr="update into ticket set state=1 where id=";
        String selTickets="select t.id,f.name,h.name,t.plase_number,t.row_number,t.price,c.day,c.start_time " +
                            "from ticket t left join shedule s on(t.shedule_id=s.id) " +
                            "left join film f on(s.film_id=f.id) " +
                            "left join hall h on(s.hall_id=h.id) " +
                            "left join calendar c on(s.calendar_id=c.id) " +
                            "where t.id=";
        for (String i :q
             ) {
            String createOrder = "select buy_ticket("+i+")";
            ResultSet order_id=stat.executeQuery(createOrder);
            order_id.next();
            int operationId=order_id.getInt(1);

            ResultSet rs=stat.executeQuery(selTickets+i);
            rs.next();

            ticketList.add(new CheckTicket(
                    operationId,
                    rs.getString(2),
                    rs.getString(3),
                    rs.getInt(4),
                    rs.getInt(5),
                    rs.getInt(6),
                    new SimpleDateFormat("yyyy-MM-dd").parse(rs.getString(7)),
                    rs.getString(8)
            ));
        }

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("WEB-INF/pages/receipt");
        modelAndView.addObject("tic",ticketList);
        return modelAndView;
    }

//    @PostMapping("/returnTicket")
//    public ModelAndView retutnTicket(
//            @RequestParam("filter") Integer operation_id, @RequestParam Map<String, Object> model
//    ) throws SQLException {
////        String checkReturnVal = "select return_ticket("+operation_id+")";
////        ResultSet order_id=stat.executeQuery(createOrder);
////        order_id.next();
//
//        String createOrder = "select return_ticket("+operation_id+")";
//        ResultSet order_id=stat.executeQuery(createOrder);
//        order_id.next();
//        ModelAndView modelAndView = new ModelAndView();
//       // modelAndView.setViewName("WEB-INF/pages/receipt");
//       // modelAndView.addObject("tic",ticketList);
//        return modelAndView;
//    }
}