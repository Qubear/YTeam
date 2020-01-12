package com.YTeam.cinema.controller;


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
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

@RunWith(JUnit4.class)
public class ReturnTicketControllerTest {
    private ReturnTicketController ticketContr;

    @Before
    public void before(){
        ticketContr = new ReturnTicketController(true);
    }
    @Test
    public void getAfisha_Test()
    {
        Map<String, Object> model = new HashMap<String, Object>();
        String err = ticketContr.getAfisha(model);
        Assert.assertTrue("ReturnTicketController Test - getAfisha: Error!",err.equals("WEB-INF/pages/returnTicket"));
    }

    @Test
    public void postAfisha_Test_Admin() throws SQLException
    {
        Statement statMock = Mockito.mock(Statement.class);
        ResultSet resMock = Mockito.mock(ResultSet.class);

        int id=1;
        String s="select return_ticket("+id+")";

        Mockito.when(statMock.executeQuery(s)).thenReturn(resMock);

        Mockito.when(resMock.next()).thenReturn(true);
        Mockito.when(resMock.getInt(1)).thenReturn(1);

        ticketContr.setStat(statMock);

        ModelAndView model1 = ticketContr.postAfisha(id);
        Assert.assertTrue("ReturnTicketControllerTest Test - postAfisha(Admin): Error!",
                model1.getModel().get("state").equals("Данный билет не может быть возвращен! \n Обратитесь к администратору"));

    }
    @Test
    public void postAfisha_Test_Success() throws SQLException
    {
        Statement statMock = Mockito.mock(Statement.class);
        ResultSet resMock = Mockito.mock(ResultSet.class);

        int id=1;
        String s="select return_ticket("+id+")";

        Mockito.when(statMock.executeQuery(s)).thenReturn(resMock);

        Mockito.when(resMock.next()).thenReturn(true);
        Mockito.when(resMock.getInt(1)).thenReturn(0);

        ticketContr.setStat(statMock);

        ModelAndView model1 = ticketContr.postAfisha(id);
        Assert.assertTrue("ReturnTicketControllerTest Test - postAfisha(Success): Error!",
                model1.getModel().get("state").equals("Билет был возвращен!"));

    }
    @Test
    public void postAfisha_Test_Error() throws SQLException
    {
        Statement statMock = Mockito.mock(Statement.class);
        ResultSet resMock = Mockito.mock(ResultSet.class);

        int id=1;
        String s="select return_ticket("+id+")";

        Mockito.when(statMock.executeQuery(s)).thenReturn(resMock);

        Mockito.when(resMock.next()).thenReturn(true);
        Mockito.when(resMock.getInt(1)).thenReturn(5);

        ticketContr.setStat(statMock);

        ModelAndView model1 = ticketContr.postAfisha(id);

        Assert.assertTrue("ReturnTicketControllerTest Test - postAfisha(Error): Error!",
                model1.getModel().get("state").equals("Билет не может быть возвращен!"));

    }
}
