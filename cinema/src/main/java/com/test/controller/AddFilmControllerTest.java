package com.test.controller;

import com.YTeam.cinema.Bean.AddFilmModel;
import com.YTeam.cinema.controller.AddFilmController;
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
    public void postAfisha_Test() throws SQLException
    {
        AddFilmModel film = new AddFilmModel("1","1","1","1","1","1","1",1,"1",1,1);

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

        Mockito.when(resMock.next()).thenReturn(true,true, false);
        Mockito.when(resMock.getInt(1)).thenReturn(1,0);

        addFilm.setStat(statMock);

        ModelAndView model1 = addFilm.postAfisha(film);
        ModelAndView model2 = addFilm.postAfisha(film);

        Assert.assertTrue("Test - postAfisha: Error!",
                model1.getModel().get("message").equals("Ошибка добавления") &&
                        model2.getModel().get("message").equals("Фильм успешно добавлен."));
    }

}