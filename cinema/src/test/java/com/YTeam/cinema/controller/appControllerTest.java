package com.YTeam.cinema.controller;


import com.YTeam.cinema.postgresql.PSQLConnection;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mockito;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.*;

import static org.junit.Assert.*;

@RunWith(JUnit4.class)
public class appControllerTest {
    private appController appContr;
    private PSQLConnection connection;

    @Before
    public void before()
    {
        appContr = new appController(true);
        connection = new PSQLConnection();
    }

    @Test
    public void getFilms_Test() throws SQLException, ParseException
    {
        Connection connectMock = Mockito.mock(Connection.class);
        Statement statMock = Mockito.mock(Statement.class);
        ResultSet resMock = Mockito.mock(ResultSet.class);

        String s="select name, rating, photo, genre, duration, to_char(day,'dd month') as day, age_limit, start_time, shedule_id, film_id from get_films_shedule order by day, start_time";

        Mockito.when(connectMock.createStatement()).thenReturn(statMock);
        Mockito.when(statMock.executeQuery(s)).thenReturn(resMock);

        Mockito.when(resMock.next()).thenReturn(true,true,true, false);
        Mockito.when(resMock.getString(1)).thenReturn("Film1","Film1","Film2"); //name
        Mockito.when(resMock.getString(2)).thenReturn("1");
        Mockito.when(resMock.getString(3)).thenReturn("1");
        Mockito.when(resMock.getString(4)).thenReturn("1");
        Mockito.when(resMock.getString(5)).thenReturn("1");
        Mockito.when(resMock.getString(6)).thenReturn("1"); // day
        Mockito.when(resMock.getString(7)).thenReturn("1");
        Mockito.when(resMock.getInt(9)).thenReturn(1,2,3);
        Mockito.when(resMock.getInt(10)).thenReturn(1);
        Mockito.when(resMock.getString(8)).thenReturn("1111111","222222","333333");

        connection.setConnectionST(connectMock);

        Map<String, Object> model=new HashMap<String, Object>();
        String err = appContr.getFilms(model);

        // Получим информацию из model
        Map<String, Map<String, TreeMap<Integer, String>>> schedule = (Map<String, Map<String, TreeMap<Integer, String>>>) model.get("schedule");
        Map<String, TreeMap<String, ArrayList<Object>>> day = (Map<String, TreeMap<String, ArrayList<Object>>>) model.get("days");

        Set<String> sch1 = schedule.keySet();                           // "1"
        Set<String> day1 = day.keySet();                                // "1"
        Set<String> sch2 = schedule.get("1").keySet();                  // "Film1" "Film2"
        Set<String> day2 = day.get("1").keySet();                       // "Film1" "Film2"
        Set<Integer> sch3_1 = schedule.get("1").get("Film1").keySet();  // "1" "2"
        Set<Integer> sch3_2 = schedule.get("1").get("Film2").keySet();  // "3"

        // Проверим информацию из model
        int f=0;
        if(sch1.size()==1 && sch2.size()==2 && sch3_1.size()==2 && sch3_2.size()==1&& day1.size()==1 && day2.size()==2)
            if(sch1.contains("1") && sch2.contains("Film1") && sch2.contains("Film2") &&  sch3_1.contains(1)  && sch3_1.contains(2) && sch3_2.contains(3))
                if(day1.contains("1")  &&  day2.contains("Film1") && day2.contains("Film2"))
                    f=1;

        Assert.assertTrue("appController Test - getFilms: Error!",err.equals("WEB-INF/pages/afisha") && f==1);

    }

    @Test
    public void getOneFilm_Test() throws SQLException, ParseException
    {
        Connection connectMock = Mockito.mock(Connection.class);
        Statement statMock = Mockito.mock(Statement.class);
        ResultSet resMock = Mockito.mock(ResultSet.class);

        int id=1;
        String s = "select name, rating, photo, genre, duration, to_char(day,'dd month') as day, age_limit, start_time, shedule_id, film_id from get_films_shedule where film_id="+id+" order by day, start_time";

        Mockito.when(connectMock.createStatement()).thenReturn(statMock);
        Mockito.when(statMock.executeQuery(s)).thenReturn(resMock);

        Mockito.when(resMock.next()).thenReturn(true,true,true, false);
        Mockito.when(resMock.getString(1)).thenReturn("Film1","Film1","Film2"); //name
        Mockito.when(resMock.getString(2)).thenReturn("1");
        Mockito.when(resMock.getString(3)).thenReturn("1");
        Mockito.when(resMock.getString(4)).thenReturn("1");
        Mockito.when(resMock.getString(5)).thenReturn("1");
        Mockito.when(resMock.getString(6)).thenReturn("1"); // day
        Mockito.when(resMock.getString(7)).thenReturn("1");
        Mockito.when(resMock.getInt(9)).thenReturn(1,2,3);
        Mockito.when(resMock.getInt(10)).thenReturn(1);
        Mockito.when(resMock.getString(8)).thenReturn("1111111","222222","333333");

        connection.setConnectionST(connectMock);

        Map<String, Object> model=new HashMap<String, Object>();
        String err =appContr.getOneFilm(id,model);

        // Получим информацию из model
        Map<String, Map<String, TreeMap<Integer, String>>> schedule = (Map<String, Map<String, TreeMap<Integer, String>>>) model.get("schedule");
        Map<String, TreeMap<String, ArrayList<Object>>> day = (Map<String, TreeMap<String, ArrayList<Object>>>) model.get("days");

        Set<String> sch1 = schedule.keySet();                           // "1"
        Set<String> day1 = day.keySet();                                // "1"
        Set<String> sch2 = schedule.get("1").keySet();                  // "Film1" "Film2"
        Set<String> day2 = day.get("1").keySet();                       // "Film1" "Film2"
        Set<Integer> sch3_1 = schedule.get("1").get("Film1").keySet();  // "1" "2"
        Set<Integer> sch3_2 = schedule.get("1").get("Film2").keySet();  // "3"

        // Проверим информацию из model
        int f=0;
        if(sch1.size()==1 && sch2.size()==2 && sch3_1.size()==2 && sch3_2.size()==1&& day1.size()==1 && day2.size()==2)
            if(sch1.contains("1") && sch2.contains("Film1") && sch2.contains("Film2") &&  sch3_1.contains(1)  && sch3_1.contains(2) && sch3_2.contains(3))
                if(day1.contains("1")  &&  day2.contains("Film1") && day2.contains("Film2"))
                    f=1;


        Assert.assertTrue("appController Test - getOneFilm: Error!",err.equals("WEB-INF/pages/oneFilmSeance") && f==1);
    }

}
