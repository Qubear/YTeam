package com.YTeam.cinema.controller;

import com.YTeam.cinema.Bean.AddFilmModel;

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

import static org.junit.Assert.*;

@RunWith(JUnit4.class)
public class AddFilmControllerTest {
    private AddFilmController addFilm;
    @Before
    public void before(){
        addFilm = new AddFilmController(true);

    }

    @Test
    public void getAfisha_Test()
    {
        String err = addFilm.getAfisha();
        Assert.assertTrue("AddFilmController Test - getAfisha: Error!",err.equals("WEB-INF/pages/addFilm"));
    }

    @Test
    public void postAfisha_Test_ErrorAdd() throws SQLException
    {
        AddFilmModel film = new AddFilmModel("Film1","1","1","1","1","1","2000-01-01",1,"1",1,1);

        Statement statMock = Mockito.mock(Statement.class);
        ResultSet resMock = Mockito.mock(ResultSet.class);

        String s ="select add_film('"+
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
                ")";;

        Mockito.when(statMock.executeQuery(s)).thenReturn(resMock);

        Mockito.when(resMock.next()).thenReturn(true,false);
        Mockito.when(resMock.getInt(1)).thenReturn(1);

        addFilm.setStat(statMock);

        ModelAndView model1 = addFilm.postAfisha(film);
        ModelAndView model2 = addFilm.postAfisha(film);

        Assert.assertTrue("Test - postAfisha (Error Add): Error!",
                model1.getModel().get("message").equals("Ошибка добавления"));
    }
    @Test
    public void postAfisha_Test_Add() throws SQLException
    {
        AddFilmModel film = new AddFilmModel("Film1","1","1","1","1","1","2000-01-01",1,"1",1,1);

        Statement statMock = Mockito.mock(Statement.class);
        ResultSet resMock = Mockito.mock(ResultSet.class);

        String s ="select add_film('"+
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
                ")";;

        Mockito.when(statMock.executeQuery(s)).thenReturn(resMock);

        Mockito.when(resMock.next()).thenReturn(true, false);
        Mockito.when(resMock.getInt(1)).thenReturn(0);

        addFilm.setStat(statMock);

        ModelAndView model1 = addFilm.postAfisha(film);

        Assert.assertTrue("Test - postAfisha (Add): Error!",
                model1.getModel().get("message").equals("Фильм успешно добавлен."));
    }

}
