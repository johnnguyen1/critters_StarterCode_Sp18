package assignment4;

import assignment4.Critter.TestCritter;

public class MyCritter6 extends TestCritter {
	
	@Override
	public void doTimeStep() {
		run(2);
	}

	@Override
	public boolean fight(String opponent) {
		run(getRandomInt(8));
		return false;
	}

	@Override
	public String toString () {
		return "5";
	}
}
