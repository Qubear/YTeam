package com.YTeam.cinema;

import java.util.Date;

public class CheckTicket {
    private Integer ID;
    private String name;
    private String hall;
    private Integer plaseNumber;
    private Integer rowNumber;
    private Integer price;
    private Date day;
    private String startTime;

    public CheckTicket(Integer ID, String name, String hall, Integer plaseNumber, Integer rowNumber, Integer price, Date day, String startTime) {
        this.ID = ID;
        this.name = name;
        this.hall = hall;
        this.plaseNumber = plaseNumber;
        this.rowNumber = rowNumber;
        this.price = price;
        this.day = day;
        this.startTime = startTime;
    }
}

