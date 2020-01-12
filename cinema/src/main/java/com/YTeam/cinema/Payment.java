package com.YTeam.cinema;

import java.util.Random;

public class Payment {
    private static Random random= new Random();;
    public static void setRandom(Random r){ random = r; }

    public static boolean ticketPayment() throws InterruptedException {
        int i = random.nextInt(10 );
        Thread.sleep(i*1000);
        if (i<1)
            return false;
        return true;
    }
}
