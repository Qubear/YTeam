package com.YTeam.cinema.controller;

import com.YTeam.cinema.Bean.AddFilmModel;
import com.YTeam.cinema.CheckTicket;
import com.YTeam.cinema.FilmSession;
import com.YTeam.cinema.Payment;
import com.YTeam.cinema.postgresql.PSQLConnection;
import com.YTeam.cinema.ticket;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mockito;
import org.springframework.web.servlet.ModelAndView;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static org.junit.Assert.*;

@RunWith(JUnit4.class)
public class SeetSelectionControllerTest {

    private SeetSelectionController select;
    private PSQLConnection connection;

    @Before
    public void before()
    {
        select = new SeetSelectionController();
        connection = new PSQLConnection();
    }

    @Test
    public void getSeetSelection_Test_Int() throws SQLException
    {
        Connection connectMock = Mockito.mock(Connection.class);
        Statement statMock = Mockito.mock(Statement.class);
        ResultSet resMock = Mockito.mock(ResultSet.class);
        ResultSet resMock2 = Mockito.mock(ResultSet.class);
        connection.setConnectionST(connectMock);

        int id=1,id2=1;
        String s="select ID, plase_number,row_number,price,state from ticket  where shedule_id="+id+" order by row_number,plase_number";
        String s2 = "select name, photo, to_char(day,'dd month'), start_time, age_limit, duration from get_films_shedule where shedule_id="+id2;

        Mockito.when(connectMock.createStatement()).thenReturn(statMock);
        Mockito.when(statMock.executeQuery(s)).thenReturn(resMock);

        Mockito.when(resMock.next()).thenReturn(true,true, true,false);
        Mockito.when(resMock.getInt(1)).thenReturn(1,0,1);
        Mockito.when(resMock.getInt(2)).thenReturn(1,0,1);
        Mockito.when(resMock.getInt(3)).thenReturn(1,0,1);
        Mockito.when(resMock.getInt(4)).thenReturn(1,0,1);
        Mockito.when(resMock.getInt(5)).thenReturn(1,0,1);

        Mockito.when(statMock.executeQuery(s2)).thenReturn(resMock2);

        Mockito.when(resMock2.next()).thenReturn(true, false);
        Mockito.when(resMock2.getString (1)).thenReturn("1");
        Mockito.when(resMock2.getString(2)).thenReturn("1");
        Mockito.when(resMock2.getString(3)).thenReturn("1");
        Mockito.when(resMock2.getString(4)).thenReturn("111111");
        Mockito.when(resMock2.getInt(5)).thenReturn(1);
        Mockito.when(resMock2.getString(6)).thenReturn("1");

        Map<String, Object> model=new HashMap<String, Object>();
        String err = select.getSeetSelection(id, model);

        Assert.assertTrue("appController Test - getSeetSelection(Int): Error!",err.equals("WEB-INF/pages/SeetSelection"));
    }

    @Test
    public void getSeetSelection_Test_String_PaymentTrue() throws SQLException, ParseException, InterruptedException
    {
        Random randMock = Mockito.mock(Random.class);
        Payment pay = new Payment();
        pay.setRandom(randMock);
        Mockito.when(randMock.nextInt(10 )).thenReturn(2);

        Connection connectMock = Mockito.mock(Connection.class);
        Statement statMock = Mockito.mock(Statement.class);
        ResultSet resMock = Mockito.mock(ResultSet.class);
        ResultSet resMock1 = Mockito.mock(ResultSet.class);
        //ResultSet resMock2 = Mockito.mock(ResultSet.class);
        ResultSet resMock3 = Mockito.mock(ResultSet.class);

        connection.setConnectionST(connectMock);

        int id=1;
        String s = "select buy_ticket("+id+")";
        String s1="select t.id,f.name,h.name,t.plase_number,t.row_number,t.price,c.day,c.start_time " +
                "from ticket t left join shedule s on(t.shedule_id=s.id) " +
                "left join film f on(s.film_id=f.id) " +
                "left join hall h on(s.hall_id=h.id) " +
                "left join calendar c on(s.calendar_id=c.id) " +
                "where t.id="+id;
        String s2="update operation set state=3 where  id="+id;
        String s3="select state from operation where id ="+id;
        String s4="select return_ticket("+id+")";


        Mockito.when(connectMock.createStatement()).thenReturn(statMock);
        Mockito.when(statMock.executeQuery(s)).thenReturn(resMock);

        Mockito.when(resMock.next()).thenReturn(true);
        Mockito.when(resMock.getInt(1)).thenReturn(1);

        Mockito.when(statMock.executeQuery(s1)).thenReturn(resMock1);

        Mockito.when(resMock1.next()).thenReturn(true, false);
        Mockito.when(resMock1.getInt(2)).thenReturn(1);
        Mockito.when(resMock1.getInt(3)).thenReturn(1);
        Mockito.when(resMock1.getInt(4)).thenReturn(1);
        Mockito.when(resMock1.getInt(5)).thenReturn(1);
        Mockito.when(resMock1.getInt(6)).thenReturn(1);
        Mockito.when(resMock1.getString(7)).thenReturn("2000-01-01");
        Mockito.when(resMock1.getString(8)).thenReturn("111111");

        Mockito.when(statMock.executeQuery(s3)).thenReturn(resMock3);
        Mockito.when(statMock.executeQuery(s4)).thenReturn(resMock3);

        Mockito.when(resMock3.next()).thenReturn(true);
        Mockito.when(resMock3.getInt(1)).thenReturn(3,0);

        Mockito.when(statMock.executeUpdate(s2)).thenReturn(0);
        //Mockito.when(statMock.executeUpdate(s2)).thenThrow(new SQLException());

        Map<String, Object> model=new HashMap<String, Object>();
        ModelAndView modRes = select.getSeetSelection("1!1", model);

        Assert.assertTrue("SeetSelectionController Test - getSeetSelection(String, Payment - True): Error!",
                modRes.getViewName().equals("WEB-INF/pages/receipt"));
    }

    @Test
    public void getSeetSelection_Test_String_PaymentFalse() throws SQLException, ParseException, InterruptedException
    {
        Random randMock = Mockito.mock(Random.class);
        Payment pay = new Payment();
        pay.setRandom(randMock);
        Mockito.when(randMock.nextInt(10 )).thenReturn(0);

        Connection connectMock = Mockito.mock(Connection.class);
        Statement statMock = Mockito.mock(Statement.class);
        ResultSet resMock = Mockito.mock(ResultSet.class);
        ResultSet resMock1 = Mockito.mock(ResultSet.class);
        //ResultSet resMock2 = Mockito.mock(ResultSet.class);
        ResultSet resMock3 = Mockito.mock(ResultSet.class);

        connection.setConnectionST(connectMock);

        int id=1;
        String s = "select buy_ticket("+id+")";
        String s1="select t.id,f.name,h.name,t.plase_number,t.row_number,t.price,c.day,c.start_time " +
                "from ticket t left join shedule s on(t.shedule_id=s.id) " +
                "left join film f on(s.film_id=f.id) " +
                "left join hall h on(s.hall_id=h.id) " +
                "left join calendar c on(s.calendar_id=c.id) " +
                "where t.id="+id;
        String s2="update operation set state=3 where  id="+id;
        String s3="select state from operation where id ="+id;
        String s4="select return_ticket("+id+")";


        Mockito.when(connectMock.createStatement()).thenReturn(statMock);
        Mockito.when(statMock.executeQuery(s)).thenReturn(resMock);

        Mockito.when(resMock.next()).thenReturn(true);
        Mockito.when(resMock.getInt(1)).thenReturn(1);

        Mockito.when(statMock.executeQuery(s1)).thenReturn(resMock1);

        Mockito.when(resMock1.next()).thenReturn(true, false);
        Mockito.when(resMock1.getInt(2)).thenReturn(1);
        Mockito.when(resMock1.getInt(3)).thenReturn(1);
        Mockito.when(resMock1.getInt(4)).thenReturn(1);
        Mockito.when(resMock1.getInt(5)).thenReturn(1);
        Mockito.when(resMock1.getInt(6)).thenReturn(1);
        Mockito.when(resMock1.getString(7)).thenReturn("2000-01-01");
        Mockito.when(resMock1.getString(8)).thenReturn("111111");

        Mockito.when(statMock.executeQuery(s3)).thenReturn(resMock3);
        Mockito.when(statMock.executeQuery(s4)).thenReturn(resMock3);

        Mockito.when(resMock3.next()).thenReturn(true);
        Mockito.when(resMock3.getInt(1)).thenReturn(3,0);

        //Mockito.when(statMock.executeUpdate(s2)).thenReturn(0);
        Mockito.when(statMock.executeUpdate(s2)).thenThrow(new SQLException());

        Map<String, Object> model=new HashMap<String, Object>();
        ModelAndView modRes = select.getSeetSelection("1!1", model);

        Assert.assertTrue("SeetSelectionController Test - getSeetSelection(String, Payment - False): Error!",
                modRes.getViewName().equals("redirect:/"));
    }
}