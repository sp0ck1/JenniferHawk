package com.jenniferhawk.tests;

public class BackxtarTest {

    public static void main(String[] args) {
    runLoop();
    }

    public static void runLoop() {

        long time = System.currentTimeMillis();

        //     ResultSet set = LiteSQL.onQuery("SELECT userid, guildid FROM timeranks WHERE ((julianday(CURRENT_TIMESTAMP) - julianday(time)) * 1000) >= 15");
        while (true) {
          //  System.out.println("It wasn't >= :)");
            if (System.currentTimeMillis() >= time + 1000) {
                time = System.currentTimeMillis();
                System.out.println("onSecond(); time is " + time);
            }
        }
    }
}
