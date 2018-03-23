package assignment4;

import java.util.*;

public class MyCritter2 extends Critter {
    private int fled = 0;
    @Override
    /**
     * reproduces a critter to the position above
     */
    public void doTimeStep() {
        MyCritter2 baby = new MyCritter2();
        reproduce(baby, 2);
    }

    @Override
    /**
     * always tries to flee a battle or die
     * @return false but has the opportunity to run away
     */
    public boolean fight(String opponent) {
        if(flee('w',1)){
            walk(1); //walks northeast
            fled++;
        }
        return false;
    }
    /**
     * toString
     * @return 2 display char of MyCritter2
     */
    public String toString() {
        return "2";
    }
    /**
     * prints our the number of times each MyCritter2 has fled a battle
     */
    public static void runStats(java.util.List<Critter> mc2){
        int sum = 0;
        for (Object obj : mc2) {
            MyCritter2 m2 = (MyCritter2) obj;
            sum += m2.fled;
        }
        System.out.println("Current MyCritter2 instances have fled a total of: " + sum + " times.");
    }
    public void test (List<Critter> l) {

    }
}
