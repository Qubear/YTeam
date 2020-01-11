package com.YTeam.cinema;

import com.YTeam.cinema.Payment;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Random;


@RunWith(JUnit4.class)
public class PaymentTest {
    private Payment payment;

    @Before
    public void before(){
        payment = new Payment();
    }

    @Test
    public void ticketPayment_Test_True()throws InterruptedException
    {
        Random randomMock = Mockito.mock(Random.class);
        Mockito.when(randomMock.nextInt(10)).thenReturn(2);
        payment.setRandom(randomMock);

        Assert.assertTrue("Test(true) - ticketPayment: Error!", payment.ticketPayment());
    }
    @Test
    public void ticketPayment_Test_False()throws InterruptedException
    {
        Random randomMock = Mockito.mock(Random.class);
        Mockito.when(randomMock.nextInt(10)).thenReturn(0);
        payment.setRandom(randomMock);

        Assert.assertFalse("Test(false) - ticketPayment: Error!", payment.ticketPayment());
    }

}