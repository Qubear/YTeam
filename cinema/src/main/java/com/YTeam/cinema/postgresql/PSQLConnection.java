package com.YTeam.cinema.postgresql;
import com.YTeam.cinema.Bean.AddFilmModel;

import java.sql.*;
import java.util.Random;

public class PSQLConnection {

    /*wifi 192.168.0.107
          cable 192.168.0.103
        * */
    private static String DB_URL = "jdbc:postgresql://127.0.0.1:64000/cinema";
    private static String USER = "pg";
    private static String PASS = "12345678";

    private Connection connection;
    private static Connection connectionST;

    public void setConnectionST(Connection connection) {
        this.connectionST = connection;
    }

    static {
        try {
            connectionST = (Connection) DriverManager.getConnection(DB_URL,USER,PASS);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public PSQLConnection(){
        try {
            connection = (Connection) DriverManager.getConnection(DB_URL,USER,PASS);
        } catch (SQLException e) {
            e.printStackTrace();
            return;
        }
    }

    public static boolean checkOperation(int op_id) throws SQLException {
        Statement stat=connectionST.createStatement();
        String qu="select state from operation where id ="+op_id;
        ResultSet rs= stat.executeQuery(qu);
        rs.next();
        if(rs.getInt(1)==3) return true;
        return false;
    }

    public static void errTicBuy(int op_id) throws SQLException {
        Statement stat=connectionST.createStatement();
        String qu="select return_ticket("+op_id+")";
        ResultSet rs= stat.executeQuery(qu);
    }

    public static int purchaseConfirmation(int op_id) throws SQLException {
        try{
            Statement stat=connectionST.createStatement();
            String qu="update operation set state=3 where  id="+op_id;
            stat.executeUpdate(qu);
            return 0;
        }catch (SQLException e){
            return 1;
        }

    }

    public static ResultSet getFilmsShedule () throws SQLException {
        String q="select name, rating, photo, genre, duration, to_char(day,'dd.mm') as day, age_limit, start_time, shedule_id, film_id from get_films_shedule order by day, start_time";
        Statement stat=connectionST.createStatement();
        return stat.executeQuery(q);
    }

    public static ResultSet getOneFilmSeanse (int film_id) throws SQLException {
        String q = "select name, rating, photo, genre, duration, to_char(day,'dd.mm') as day, age_limit, start_time, shedule_id, film_id from get_films_shedule where film_id="+film_id+" order by day, start_time";
        Statement stat=connectionST.createStatement();
        return stat.executeQuery(q);
    }

    public static ResultSet getFilmQuery (int shedule_id) throws SQLException {

        String q = "select name, photo, to_char(day,'dd.mm'), start_time, age_limit, duration from get_films_shedule where shedule_id="+shedule_id;
        Statement stat=connectionST.createStatement();
        return stat.executeQuery(q);
    }

    public static ResultSet getFilmQueryLimit1 (int film_id) throws SQLException {

        String q = "select name,photo,age_limit,duration,director,genre,description,actors,movie,film_id from get_films_shedule where film_id="+film_id+" limit 1";
        Statement stat=connectionST.createStatement();
        return stat.executeQuery(q);
    }

    public static ResultSet getSessionTickets (int shedule_id) throws SQLException {

        String q="select ID, plase_number,row_number,price,state from ticket  where shedule_id="+shedule_id+" order by row_number,plase_number";
        Statement stat=connectionST.createStatement();
        return stat.executeQuery(q);
    }

    public static ResultSet getTicket (String ticket_id) throws SQLException {

        String q="select t.id,f.name,h.name,t.plase_number,t.row_number,t.price,c.day,c.start_time " +
                "from ticket t left join shedule s on(t.shedule_id=s.id) " +
                "left join film f on(s.film_id=f.id) " +
                "left join hall h on(s.hall_id=h.id) " +
                "left join calendar c on(s.calendar_id=c.id) " +
                "where t.id="+ticket_id;
        Statement stat=connectionST.createStatement();
        return stat.executeQuery(q);
    }

    public static ResultSet buyTicket (String ticket_id) throws SQLException {

        String q = "select buy_ticket("+ticket_id+")";
        Statement stat=connectionST.createStatement();
        return stat.executeQuery(q);
    }

    public static boolean AddFilm (AddFilmModel film) throws SQLException {

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
        Statement stat=connectionST.createStatement();
        ResultSet rs=stat.executeQuery(query);
        rs.next();
        int a=rs.getInt(1);

        if(a==0){
            return true;
        }
        else {
            return false;
        }
    }


    public static int ReturnTicket (int id) throws SQLException {

        String q="select return_ticket("+id+")";
        Statement stat=connectionST.createStatement();
        ResultSet rs= stat.executeQuery(q);
        rs.next();
        return rs.getInt(1);

    }
}
