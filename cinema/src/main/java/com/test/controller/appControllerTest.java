package com.test.controller;

import com.YTeam.cinema.controller.appController;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mockito;
import org.springframework.web.servlet.ModelAndView;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;


@RunWith(JUnit4.class)
public class appControllerTest {

    private appController appContr;

    @Before
    public void before(){
        appContr = new appController(true);
    }

    @Test
    public void getAfisha_Test()
    {
        Map<String, Object> model=new HashMap<String, Object>();
        String err = appContr.getAfisha("name", model);
        Assert.assertTrue("appController Test - getAfisha: Error!",err.equals("WEB-INF/pages/afisha"));
    }

    //    @Test
//    public void postAfisha_Test()
//    {
//        String err = appContr.postAfisha( new Member());
//        Assert.assertTrue("appController Test - postAfisha: Error!",err.equals("WEB-INF/pages/afisha"));
//    }

    @Test
    public void getFilm_Test() throws SQLException, ParseException {
        Statement statMock = Mockito.mock(Statement.class);
        ResultSet resMock = Mockito.mock(ResultSet.class);

        String s ="select name, rating, photo, genre, duration, day, age_limit, start_time, shedule_id from get_films_shedule";

        Mockito.when(statMock.executeQuery(s)).thenReturn(resMock);

        Mockito.when(resMock.next()).thenReturn(true,true,true, false);
        Mockito.when(resMock.getString(1)).thenReturn("1","1","0");
        Mockito.when(resMock.getString(2)).thenReturn("1");
        Mockito.when(resMock.getString(3)).thenReturn("1");
        Mockito.when(resMock.getString(4)).thenReturn("1");
        Mockito.when(resMock.getString(5)).thenReturn("1");
        Mockito.when(resMock.getString(6)).thenReturn("1","1","1");
        Mockito.when(resMock.getString(7)).thenReturn("1");

        Mockito.when(resMock.getInt(9)).thenReturn(1);
        Mockito.when(resMock.getString(8)).thenReturn("1111111");

        appContr.setStat(statMock);

        Map<String, Object> model=new HashMap<String, Object>();
        String err = appContr.getFilms(model);

        Assert.assertTrue("appController Test - getFilms: Error!",err.equals("WEB-INF/pages/afisha"));
    }


    @Test
    public void getSeetSelection_Test_Int() throws SQLException
    {
        Statement statMock = Mockito.mock(Statement.class);
        ResultSet resMock = Mockito.mock(ResultSet.class);

        int id=1;

        String s="select ID, plase_number,row_number,price,state from ticket  where shedule_id="+id+" order by row_number,plase_number";

        Mockito.when(statMock.executeQuery(s)).thenReturn(resMock);

        Mockito.when(resMock.next()).thenReturn(true,true, false);    // Нужно вернуть сначала true потом false
        Mockito.when(resMock.getInt(1)).thenReturn(1,0);
        Mockito.when(resMock.getInt(2)).thenReturn(1,0);
        Mockito.when(resMock.getInt(3)).thenReturn(1,0);
        Mockito.when(resMock.getInt(4)).thenReturn(1,0);
        Mockito.when(resMock.getInt(5)).thenReturn(1,0);
        //Mockito.when(resMock.getInt(6)).thenReturn(1);

        appContr.setStat(statMock);

        Map<String, Object> model=new HashMap<String, Object>();
        String err = appContr.getSeetSelection(id, model);

        Assert.assertTrue("appController Test - getSeetSelection(Int): Error!",err.equals("WEB-INF/pages/SeetSelection"));
    }

    @Test
    public void getSeetSelection_Test_String() throws SQLException, ParseException
    {
        Statement statMock = Mockito.mock(Statement.class);
        ResultSet resMock = Mockito.mock(ResultSet.class);
        ResultSet resMock1 = Mockito.mock(ResultSet.class);

        String s = "select buy_ticket(1)";
        String s1 ="select t.id,f.name,h.name,t.plase_number,t.row_number,t.price,c.day,c.start_time " +
                "from ticket t left join shedule s on(t.shedule_id=s.id) " +
                "left join film f on(s.film_id=f.id) " +
                "left join hall h on(s.hall_id=h.id) " +
                "left join calendar c on(s.calendar_id=c.id) " +
                "where t.id=1";

        Mockito.when(statMock.executeQuery(s)).thenReturn(resMock);
        Mockito.when(statMock.executeQuery(s1)).thenReturn(resMock1);

        Mockito.when(resMock.next()).thenReturn(true, false);    // Нужно вернуть сначала true потом false
        Mockito.when(resMock.getInt(1)).thenReturn(1);

        Mockito.when(resMock1.next()).thenReturn(true, false);    // Нужно вернуть сначала true потом false
        Mockito.when(resMock1.getInt(2)).thenReturn(1);
        Mockito.when(resMock1.getInt(3)).thenReturn(1);
        Mockito.when(resMock1.getInt(4)).thenReturn(1);
        Mockito.when(resMock1.getInt(5)).thenReturn(1);
        Mockito.when(resMock1.getInt(6)).thenReturn(1);
        Mockito.when(resMock1.getString(7)).thenReturn("2000-01-01");
        Mockito.when(resMock1.getString(8)).thenReturn("1");

        appContr.setStat(statMock);

        Map<String, Object> model=new HashMap<String, Object>();
        ModelAndView modRes = appContr.getSeetSelection("1", model);

        Assert.assertTrue("appController Test - getSeetSelection(String): Error!",
                modRes.getViewName().equals("WEB-INF/pages/receipt"));
    }

}