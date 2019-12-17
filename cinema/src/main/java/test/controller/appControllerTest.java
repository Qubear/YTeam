package test.controller;


import com.YTeam.cinema.Bean.AddFilmModel;
import com.YTeam.cinema.CheckTicket;
import com.YTeam.cinema.FilmSession;
import com.YTeam.cinema.controller.appController;
import com.YTeam.cinema.ticket;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mockito;
import org.springframework.web.servlet.ModelAndView;
import postgresql.PSQLConnection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@RunWith(JUnit4.class)
public class appControllerTest {

    private appController appContr;

    @Before
    public void before()
    {
        appContr = new appController(true);
    }

    @Test
    public  void getFilmDescription_Test() throws SQLException {
        PSQLConnection conMock = Mockito.mock(PSQLConnection.class);
        Statement statMock = Mockito.mock(Statement.class);
        appContr = new appController(conMock,statMock);
        ResultSet resMock = Mockito.mock(ResultSet.class);

        int id=1;
        String s = "select name,photo,age_limit,duration,director,genre,description from get_films_shedule where film_id="+id+" limit 1";

        Mockito.when(statMock.executeQuery(s)).thenReturn(resMock);
        Mockito.when(resMock.next()).thenReturn(true, false);
        Mockito.when(resMock.getString(1)).thenReturn("1");
        Mockito.when(resMock.getString(2)).thenReturn("1");
        Mockito.when(resMock.getInt(3)).thenReturn(1);
        Mockito.when(resMock.getInt(4)).thenReturn(1);
        Mockito.when(resMock.getString(5)).thenReturn("1");
        Mockito.when(resMock.getString(6)).thenReturn("1");
        Mockito.when(resMock.getString(7)).thenReturn("1");


        Map<String, Object> model=new HashMap<String, Object>();
        String err = appContr.getFilmDescription(id,model);

        Assert.assertTrue("appController Test - getFilms: Error!",err.equals("WEB-INF/pages/filmDescription"));
    }

    @Test
    public void getFilms_Test() throws SQLException, ParseException
    {
        Statement statMock = Mockito.mock(Statement.class);
        ResultSet resMock = Mockito.mock(ResultSet.class);

        String s ="select name, rating, photo, genre, duration, to_char(day,'dd month') as day, age_limit, start_time, shedule_id, film_id from get_films_shedule order by day, start_time";


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
        Mockito.when(resMock.getInt(10)).thenReturn(1);
        Mockito.when(resMock.getString(8)).thenReturn("1111111");
        appContr.setStat(statMock);

        Map<String, Object> model=new HashMap<String, Object>();
        String err = appContr.getFilms(model);

        Assert.assertTrue("appController Test - getFilms: Error!",err.equals("WEB-INF/pages/afisha"));
    }

    @Test
    public void login_Test()
    {
        String err = appContr.login();
        Assert.assertTrue("appController Test - getFilms: Error!",err.equals("WEB-INF/pages/login"));
    }


    @Test
    public void getSeetSelection_Test_Int() throws SQLException
    {
        Statement statMock = Mockito.mock(Statement.class);
        ResultSet resMock = Mockito.mock(ResultSet.class);
        ResultSet resMock1 = Mockito.mock(ResultSet.class);

        int id=1;
        String s ="select ID, plase_number,row_number,price,state from ticket  where shedule_id="+id+" order by row_number,plase_number";
        String s1 = "select name, photo, day, start_time, age_limit, duration from get_films_shedule where shedule_id="+id;


        Mockito.when(statMock.executeQuery(s)).thenReturn(resMock);
        Mockito.when(statMock.executeQuery(s1)).thenReturn(resMock1);
        Mockito.when(resMock.next()).thenReturn(true,true, true,false);    // Нужно вернуть сначала true потом false
        Mockito.when(resMock1.next()).thenReturn(true, false);
        Mockito.when(resMock.getInt(1)).thenReturn(1,0,1);
        Mockito.when(resMock.getInt(2)).thenReturn(1,0,1);
        Mockito.when(resMock.getInt(3)).thenReturn(1,0,1);
        Mockito.when(resMock.getInt(4)).thenReturn(1,0,1);
        Mockito.when(resMock.getInt(5)).thenReturn(1,0,1);

        Mockito.when(resMock.getString (1)).thenReturn("1");
        Mockito.when(resMock.getString(2)).thenReturn("1");
        Mockito.when(resMock.getString(3)).thenReturn("1");
        Mockito.when(resMock.getString(4)).thenReturn("1");
        Mockito.when(resMock.getInt(5)).thenReturn(1);
        Mockito.when(resMock.getString(6)).thenReturn("1");
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
        Date dat = new Date();
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
        AddFilmModel a = new AddFilmModel("1","1","1","1","1","1","1",1,"1",1,1);
        CheckTicket c = new CheckTicket(1,"1","1",1,1,1,dat,"1");
        FilmSession f = new FilmSession("1","1", dat,"1");
        ticket t = new ticket(1,1,1,1,1,1);
        appContr.setStat(statMock);
        Map<String, Object> model=new HashMap<String, Object>();
        ModelAndView modRes = appContr.getSeetSelection("1", model);
        Assert.assertTrue("appController Test - getSeetSelection(String): Error!",
                modRes.getViewName().equals("WEB-INF/pages/receipt"));
    }


}