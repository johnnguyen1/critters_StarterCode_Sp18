package assignment4;

import java.util.*;

public class MyCritter3 extends Critter.TestCritter {
    private int aggression = 0;
    @Override
    /**
     * runs MyCritter3 in a random direction
     */
    public void doTimeStep() {
        run(getRandomInt(8));
    }

    @Override
    /**
     * always tries to fight in a battle
     * @return true always
     */
    public boolean fight(String opponent) {
        aggression++;
        return true;
    }
    /**
     * toString
     * @return 2 display char of MyCritter2
     */
    public String toString() {
        return "3";
    }
    /**
     * prints out the number of times each MyCritter3 has tried to initiate a battle
     */
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
