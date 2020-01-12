package com.YTeam.cinema.controller;


import com.YTeam.cinema.postgresql.PSQLConnection;
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
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

@RunWith(JUnit4.class)
public class ReturnTicketControllerTest {
    private ReturnTicketController ticketContr;
    private PSQLConnection connection;

    @Before
    public void before(){
        ticketContr = new ReturnTicketController();
        connection = new PSQLConnection();
    }

    @Test
    public void getAfisha_Test()
    {
        Map<String, Object> model = new HashMap<String, Object>();
        String err = ticketContr.getAfisha(model);
        Assert.assertTrue("ReturnTicketController Test - getAfisha: Error!",err.equals("WEB-INF/pages/returnTicket"));
    }

    @Test
    public void postAfisha_Test_Error() throws SQLException
    {
        Connection connectMock = Mockito.mock(Connection.class);
        Statement statMock = Mockito.mock(Statement.class);
        ResultSet resMock = Mockito.mock(ResultSet.class);

        int id=1;
        String s="select return_ticket("+id+")";

        Mockito.when(connectMock.createStatement()).thenReturn(statMock);
        Mockito.when(statMock.executeQuery(s)).thenReturn(resMock);

        Mockito.when(resMock.next()).thenReturn(true);
        Mockito.when(resMock.getInt(1)).thenReturn(1);

        connection.setConnectionST(connectMock);

        ModelAndView model1 = ticketContr.postAfisha(id);
        Assert.assertTrue("ReturnTicketControllerTest Test - postAfisha(Admin): Error!",
                model1.getModel().get("state").equals("Данный билет не может быть возвращен! \n Обратитесь к администратору"));

    }
    @Test
    public void postAfisha_Test_Success() throws SQLException
    {
        Connection connectMock = Mockito.mock(Connection.class);
        Statement statMock = Mockito.mock(Statement.class);
        ResultSet resMock = Mockito.mock(ResultSet.class);

        int id=1;
        String s="select return_ticket("+id+")";

        Mockito.when(connectMock.createStatement()).thenReturn(statMock);
        Mockito.when(statMock.executeQuery(s)).thenReturn(resMock);

        Mockito.when(resMock.next()).thenReturn(true);
        Mockito.when(resMock.getInt(1)).thenReturn(0);

        connection.setConnectionST(connectMock);

        ModelAndView model1 = ticketContr.postAfisha(id);
        Assert.assertTrue("ReturnTicketControllerTest Test - postAfisha(Success): Error!",
                model1.getModel().get("state").equals("Билет был возвращен!"));

    }
}
