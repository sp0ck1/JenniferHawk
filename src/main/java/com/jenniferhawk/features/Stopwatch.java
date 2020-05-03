package com.jenniferhawk.features;

    public class Stopwatch {

        private long start;

        /**
         * Initializes a new stopwatch.
         */
        public Stopwatch() {
            start = System.currentTimeMillis();
        }


        /**
         * Returns the elapsed CPU time (in seconds) since the stopwatch was created.
         *
         * @return elapsed CPU time (in seconds) since the stopwatch was created
         */
        public double getElapsedTime() {
            long now = System.currentTimeMillis();
            return (now - start) / 1000.0;
        }

        public void reset() {
            start = System.currentTimeMillis();
        }
}
