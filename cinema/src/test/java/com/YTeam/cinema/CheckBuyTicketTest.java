package com.YTeam.cinema;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mockito;
import com.YTeam.cinema.postgresql.PSQLConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

//@RunWith(JUnit4.class)
//public class CheckBuyTicketTest {
//    private CheckBuyTicket check;
//    @Before
//    public void before(){
//        check = new CheckBuyTicket();
//    }
//
//    @Test
//    public void run_Test_True() throws SQLException
//    {
//        try{
//            PSQLConnection connect = new PSQLConnection(true);
//            Connection conMockTS = Mockito.mock(Connection.class);
//            connect.setConnectionST(conMockTS);
//
//            Statement statMock = Mockito.mock(Statement.class);
//            ResultSet resMock = Mockito.mock(ResultSet.class);
//            ResultSet resMock1 = Mockito.mock(ResultSet.class);
//
//            int id=1;
//            check.setOp_id(id);
//            String s = "select state from operation where id ="+id;
//            String s1 = "select return_ticket("+id+")";
//
//            Mockito.when(conMockTS.createStatement()).thenReturn(statMock);
//            Mockito.when(statMock.executeQuery(s)).thenReturn(resMock);
//            Mockito.when(resMock.next()).thenReturn(true);
//            Mockito.when(resMock.getInt(1)).thenReturn(3,1);
//            Mockito.when(statMock.executeQuery(s1)).thenReturn(resMock1);
//            Mockito.when(resMock1.next()).thenReturn(true);
//
//            check.run();
//            check.run();
//
//            Assert.assertTrue("CheckBuyTicketTest Test(false) - run: Error!",true);
//        }catch (SQLException e) {
//            Assert.assertTrue("CheckBuyTicketTest Test(false) - run: Error!",false);
//        }
//
//    }
//
//}
