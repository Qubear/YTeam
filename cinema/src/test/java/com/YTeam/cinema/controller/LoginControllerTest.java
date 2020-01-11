package com.YTeam.cinema.controller;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.*;

@RunWith(JUnit4.class)
public class LoginControllerTest {
    private LoginController log;

    @Before
    public void before()
    {
        log = new LoginController();
    }

    @Test
    public void login_Test()
    {
        String err = log.login();
        Assert.assertTrue("LoginController Test - login: Error!",err.equals("WEB-INF/pages/login"));
    }
}
