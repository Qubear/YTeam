package com.YTeam.cinema.postgresql;

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

import static org.junit.Assert.*;

@RunWith(JUnit4.class)
public class PSQLConnectionTest {
    private PSQLConnection connection;
    @Before
    public void before(){
        connection = new PSQLConnection();
    }

    @Test
    public void purchaseConfirmation_Test_SQLException() throws SQLException
    {
        Connection connectMock = Mockito.mock(Connection.class);
        Statement statMock = Mockito.mock(Statement.class);
        connection.setConnectionST(connectMock);

        int id=1;
        String s="update operation set state=3 where  id="+id;

        Mockito.when(connectMock.createStatement()).thenReturn(statMock);

        //Mockito.when(statMock.executeUpdate(s)).thenReturn(0);
        Mockito.when(statMock.executeUpdate(s)).thenThrow(new SQLException());

        int rez = connection.purchaseConfirmation(id);

        Assert.assertTrue("PSQLConnection Test - purchaseConfirmation(SQLException): Error!",
                rez==1);
    }

    @Test
    public void purchaseConfirmation_Test_Success() throws SQLException
    {
        Connection connectMock = Mockito.mock(Connection.class);
        Statement statMock = Mockito.mock(Statement.class);
        ResultSet resMock = Mockito.mock(ResultSet.class);
        connection.setConnectionST(connectMock);

        int id=1;
        String s="update operation set state=3 where  id="+id;

        Mockito.when(connectMock.createStatement()).thenReturn(statMock);

        Mockito.when(statMock.executeUpdate(s)).thenReturn(0);
        //Mockito.when(statMock.executeUpdate(s)).thenThrow(new SQLException());

        int rez = connection.purchaseConfirmation(id);

        Assert.assertTrue("PSQLConnection Test - purchaseConfirmation: Error!",
                rez==0);
    }

    @Test
    public void checkOperation_Test_True() throws SQLException
    {
        Connection connectMock = Mockito.mock(Connection.class);
        Statement statMock = Mockito.mock(Statement.class);
        ResultSet resMock = Mockito.mock(ResultSet.class);
        connection.setConnectionST(connectMock);

        int id=1;
        String s="select state from operation where id ="+id;

        Mockito.when(connectMock.createStatement()).thenReturn(statMock);
        Mockito.when(statMock.executeQuery(s)).thenReturn(resMock);

        Mockito.when(resMock.next()).thenReturn(true);
        Mockito.when(resMock.getInt(1)).thenReturn(3);

        boolean rez = connection.checkOperation(id);

        Assert.assertTrue("PSQLConnection Test - checkOperation(True): Error!", rez);
    }

    @Test
    public void checkOperation_Test_False() throws SQLException
    {
        Connection connectMock = Mockito.mock(Connection.class);
        Statement statMock = Mockito.mock(Statement.class);
        ResultSet resMock = Mockito.mock(ResultSet.class);
        connection.setConnectionST(connectMock);

        int id=1;
        String s="select state from operation where id ="+id;

        Mockito.when(connectMock.createStatement()).thenReturn(statMock);
        Mockito.when(statMock.executeQuery(s)).thenReturn(resMock);

        Mockito.when(resMock.next()).thenReturn(true);
        Mockito.when(resMock.getInt(1)).thenReturn(0);

        boolean rez = connection.checkOperation(id);

        Assert.assertFalse("PSQLConnection Test - checkOperation(False): Error!", rez);
    }

    @Test
    public void getFilmQuery_Test() throws SQLException
    {
        Connection connectMock = Mockito.mock(Connection.class);
        Statement statMock = Mockito.mock(Statement.class);
        ResultSet resMock = Mockito.mock(ResultSet.class);
        connection.setConnectionST(connectMock);
        int id=1;
        String s = "select name, photo, to_char(day,'dd.mm'), start_time, age_limit, duration from get_films_shedule where shedule_id="+id;

        Mockito.when(connectMock.createStatement()).thenReturn(statMock);
        Mockito.when(statMock.executeQuery(s)).thenReturn(resMock);
        Mockito.when(resMock.getInt(1)).thenReturn(1);

        ResultSet rez = connection.getFilmQuery(id);
        Assert.assertTrue("PSQLConnection Test - getFilmQuery: Error!", rez.getInt(1)==1);
    }

    @Test
    public void getFilmQueryLimit1_Test() throws SQLException
    {
        Connection connectMock = Mockito.mock(Connection.class);
        Statement statMock = Mockito.mock(Statement.class);
        ResultSet resMock = Mockito.mock(ResultSet.class);
        connection.setConnectionST(connectMock);
        int id=1;
        String s = "select name,photo,age_limit,duration,director,genre,description,actors,movie,film_id from get_films_shedule where film_id="+id+" limit 1";

        Mockito.when(connectMock.createStatement()).thenReturn(statMock);
        Mockito.when(statMock.executeQuery(s)).thenReturn(resMock);
        Mockito.when(resMock.getInt(1)).thenReturn(1);

        ResultSet rez = connection.getFilmQueryLimit1(id);
        if(rez.getInt(1)==1)
        Assert.assertTrue("PSQLConnection Test - getFilmQueryLimit1: Error!", rez.getInt(1)==1);
    }

//    @Test
//    public void getSessionTickets_Test() throws SQLException
//    {
//        Connection connectMock = Mockito.mock(Connection.class);
//        Statement statMock = Mockito.mock(Statement.class);
//        ResultSet resMock = Mockito.mock(ResultSet.class);
//        connection.setConnectionST(connectMock);
//        int id=1;
//        String s="select ID, plase_number,row_number,price,state from ticket  where shedule_id="+id+" order by row_number,plase_number";
//
//        Mockito.when(connectMock.createStatement()).thenReturn(statMock);
//        Mockito.when(statMock.executeQuery(s)).thenReturn(resMock);
//        Mockito.when(resMock.getInt(1)).thenReturn(1);
//
//        ResultSet rez = connection.getSessionTickets(id);
//        Assert.assertTrue("PSQLConnection Test - getSessionTickets: Error!", rez.getInt(1)==1);
//    }
}
