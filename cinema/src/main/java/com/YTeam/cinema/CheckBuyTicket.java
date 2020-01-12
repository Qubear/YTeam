package com.YTeam.cinema;

import com.YTeam.cinema.postgresql.PSQLConnection;

import java.sql.SQLException;

public class CheckBuyTicket
implements Runnable
{
    public int op_id;
    public void run( )
    {
        try {
            Thread.sleep(60000);
            boolean a=PSQLConnection.checkOperation(op_id);
            if (!a)
                PSQLConnection.errTicBuy(op_id);
        } catch (InterruptedException | SQLException e) {
        }
    }
}
