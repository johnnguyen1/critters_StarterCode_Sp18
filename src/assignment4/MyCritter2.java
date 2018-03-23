package assignment4;

import java.util.*;

public class MyCritter2 extends Critter {
    private int fled = 0;
    @Override
    public void doTimeStep() {
        MyCritter2 baby = new MyCritter2();
        reproduce(baby, 2);
    }

    @Override
    //walks northeast
    public boolean fight(String opponent) {
        if(flee('w',1)){
            walk(1);
            fled++;
        }
        return false;
    }

    public String toString() {
        return "2";
    }

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
