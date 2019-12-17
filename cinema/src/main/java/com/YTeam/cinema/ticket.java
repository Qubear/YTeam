package com.YTeam.cinema;

public class ticket {
    public int ID;
    public int shedule_id;
    public int  place_number;
    public int row_number;
    public int price;
    public int state;

    public ticket(int ID, int shedule_id, int place_number, int row_number, int price, int state) {
        this.ID = ID;
        this.shedule_id = shedule_id;
        this.place_number = place_number;
        this.row_number = row_number;
        this.price = price;
        this.state = state;
    }
}
