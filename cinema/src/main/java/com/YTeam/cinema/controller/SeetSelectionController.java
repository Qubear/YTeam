package com.YTeam.cinema.controller;

import com.YTeam.cinema.CheckBuyTicket;
import com.YTeam.cinema.Payment;
import com.YTeam.cinema.models.Sit;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import com.YTeam.cinema.postgresql.PSQLConnection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


@Controller
public class SeetSelectionController {
    @GetMapping("/SeetSelection")
    public String getSeetSelection(
            @RequestParam(name="shedule_id", required=false, defaultValue="18") int shedule_id,
            Map<String, Object> model
    ) throws SQLException {
        ArrayList<Sit> sitList = new ArrayList<>();
        ResultSet rs = PSQLConnection.getSessionTickets(shedule_id);

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

        ArrayList<Object> filmParamList = new ArrayList<>();
        ResultSet resultQuery = PSQLConnection.getFilmQuery(shedule_id);

        while (resultQuery.next()){
            filmParamList.add(resultQuery.getString(1));
            filmParamList.add(resultQuery.getString(2));
            filmParamList.add(resultQuery.getString(3));
            filmParamList.add(resultQuery.getString(4).substring(0, 5));
            filmParamList.add(Integer.parseInt(String.valueOf(resultQuery.getInt(5))));
            filmParamList.add(resultQuery.getString(6));
        }

        model.put("film", filmParamList);
        model.put("sit_num", sit_num);
        return "WEB-INF/pages/SeetSelection";
    }


    @PostMapping("/SeetSelection")
    public ModelAndView getSeetSelection(
            @RequestParam("filter") String tickets_id, @RequestParam Map<String, Object> model
    ) throws SQLException, ParseException, InterruptedException {
        Map<String, ArrayList<Object>> map = new HashMap<>();
        String[] q=tickets_id.split("!");

        for (String i : q) {
            ResultSet order_id=PSQLConnection.buyTicket(i);
            order_id.next();

            CheckBuyTicket a=new CheckBuyTicket();
            a.op_id=Integer.valueOf(order_id.getInt(1));
            Thread myThready = new Thread(a);
            myThready.start();


            int operationId=order_id.getInt(1);

            ResultSet rs=PSQLConnection.getTicket(i);
            rs.next();
            ArrayList<Object> ticketList=new ArrayList<>();
            ticketList.add(operationId);
            ticketList.add(rs.getString(2));
            ticketList.add(rs.getString(3));
            ticketList.add(rs.getInt(4));
            ticketList.add(rs.getInt(5));
            ticketList.add(rs.getInt(6));
            ticketList.add(new SimpleDateFormat("yyyy-MM-dd").parse(rs.getString(7)));
            ticketList.add(rs.getString(8).substring(0, 5));
            map.put(i, ticketList);
        }



        /*Унести это в фронт поделив на 2 операции бронирование билета и покупка*/
        if(Payment.ticketPayment())
            for (String i : q) {
                Object a=map.get(i).get(0);
                PSQLConnection.purchaseConfirmation(Integer.valueOf(a.toString()));
            }
        else {
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.setViewName("redirect:/");
            return modelAndView;
        }
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("WEB-INF/pages/receipt");
        modelAndView.addObject("ticket", map);
        return modelAndView;
    }
}
