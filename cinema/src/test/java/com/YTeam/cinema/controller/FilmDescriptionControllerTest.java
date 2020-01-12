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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import static org.junit.Assert.*;

@RunWith(JUnit4.class)
public class FilmDescriptionControllerTest {
    private FilmDescriptionController controller;
    private PSQLConnection connection;

    @Before
    public void before()
    {
        connection = new PSQLConnection(true);
        controller = new FilmDescriptionController();
    }

    @Test
    public  void getFilmDescription_Test() throws SQLException
    {
        Connection connectMock = Mockito.mock(Connection.class);
        Statement statMock = Mockito.mock(Statement.class);
        ResultSet resMock = Mockito.mock(ResultSet.class);

        int id=1;
        String s = "select name,photo,age_limit,duration,director,genre,description,actors,movie,film_id from get_films_shedule where film_id="+id+" limit 1";

        Mockito.when(connectMock.createStatement()).thenReturn(statMock);
        Mockito.when(statMock.executeQuery(s)).thenReturn(resMock);

        Mockito.when(resMock.next()).thenReturn(true, false);
        Mockito.when(resMock.getString(1)).thenReturn("0");
        Mockito.when(resMock.getString(2)).thenReturn("1");
        Mockito.when(resMock.getInt(3)).thenReturn(2);
        Mockito.when(resMock.getInt(4)).thenReturn(3);
        Mockito.when(resMock.getString(5)).thenReturn("4");
        Mockito.when(resMock.getString(6)).thenReturn("5");
        Mockito.when(resMock.getString(7)).thenReturn("6");
        Mockito.when(resMock.getString(8)).thenReturn("7");
        Mockito.when(resMock.getString(9)).thenReturn("8");
        Mockito.when(resMock.getInt(10)).thenReturn(9);

        connection.setConnectionST(connectMock);

        Map<String, Object> model=new HashMap<String, Object>();
        String err = controller.getFilmDescription(id,model);

        ArrayList<Object> filmList = ( ArrayList<Object>) model.get("film");

        int f=1;
        for (int i=0;i<10;i++){
            if( !String.valueOf(i).equals( String.valueOf( filmList.get(i) )))
                f=0;
        }

        Assert.assertTrue("appController Test - getFilms: Error!",err.equals("WEB-INF/pages/filmDescription") && f==1);
    }
}
