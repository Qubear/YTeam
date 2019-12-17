package com.YTeam.cinema.models;

import javax.persistence.*;

@Entity
public class Sit {
    @Id
    public int ID;
    public int place_number;
    public int row_number;
    public int price;
    public int state;

    public Sit() {
    }

    public Sit(int ID, int place_number, int row_number, int price, int state) {
        this.ID = ID;
        this.place_number = place_number;
        this.row_number = row_number;
        this.price = price;
        this.state = state;
    }
}
