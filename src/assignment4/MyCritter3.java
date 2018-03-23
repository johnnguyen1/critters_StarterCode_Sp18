package assignment4;

import java.util.*;

public class MyCritter3 extends Critter.TestCritter {
    private int aggression = 0;
    @Override
    public void doTimeStep() {
        run(getRandomInt(8));
    }

    @Override
    public boolean fight(String opponent) {
        aggression++;
        return true;
    }

    public String toString() {
        return "3";
    }
    public static void runStats(java.util.List<Critter> mc3){
        int sum = 0;
        for (Object obj : mc3) {
            MyCritter3 m3 = (MyCritter3) obj;
            sum += m3.aggression;
        }
        System.out.println("Current MyCritter2 instances have initiated a total of: " + sum + " fights.");
    }
    public void test (List<Critter> l) {

    }
}
