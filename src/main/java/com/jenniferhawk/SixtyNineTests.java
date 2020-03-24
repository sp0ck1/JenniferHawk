package com.jenniferhawk;

import java.util.Random;


class SixtyNineTests {

    private static long start;
    private static int rolls = 0;
    public static void Stopwatch() {
        start = System.currentTimeMillis();
    }

    public static double elapsedTime() {
        long now = System.currentTimeMillis();
        return (now - start) / 1000.0;
    }

    public static int matt() {

        Random random = new Random();
        double elapsed = elapsedTime();
//Roll 4d20
        int firstD20 = random.nextInt(20) + 1;
        int secondD20 = random.nextInt(20) + 1;
        int thirdD20 = random.nextInt(20) + 1;
        int fourthD20 = random.nextInt(20) + 1;
        int j;
//System.out.println(elapsed);
        j = firstD20 + secondD20 + thirdD20 + fourthD20;
//  System.out.printf("%s + %s + %s + %s = %s \n",firstD20,secondD20,thirdD20,fourthD20,j);
        return j;

    }


    // How long will it take to get 69 on the 69th roll 69 times?
    public static void main(String[] args) throws InterruptedException {

        System.out.println("How long will it take to roll 420 sets of 69 sets of 4d20s rolled 69 times until the 69th roll is 69? Let's find out!");
        int theResult;

        boolean Attempts;
        Stopwatch();
        int sixtyNineTimes;
        int m;
        int j = 0;
        int i;

        // 420 Sets of 69 sets of 4d20s rolled 69 times until the 69th roll is 69
        for (m = 0; m<420; m++) {

            // 69 sets of 4d20s rolled 69 times until the 69th roll is 69.
            // Increment sixtyNineTimes by 1 each time Attempts is set to false.
            // Once sixtyNineTimes is 69, break the loop and increment m by one, then set Attempts to true and restart the process.
            for  (sixtyNineTimes = 0; sixtyNineTimes < 69; sixtyNineTimes++) {
                Attempts = true;

                // Roll 4d20 69 times. If by the 69th try they do not equal 69, start over.
                while (Attempts) {

                    i = 0;
                    j++;

                    // Roll 4d20 until they add up to 69
                    while (i < 69) {
                        theResult = matt();
                        i++;
                        rolls++;
                        if (theResult == 69 && i == 69) { // Set Attempts to false; this steps one loop up and increments sixtyNineTimes by one
                            Attempts = false;

                        }
                    }
                }
            }
        }
        double elapsed = elapsedTime();
        System.out.println("Nice. We did it " + m + " times. It took " + elapsed + " seconds and " + rolls + " rolls.");
    }
}


