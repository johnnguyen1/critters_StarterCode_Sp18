package assignment4;
/* CRITTERS Critter.java
 * EE422C Project 4 submission by
 * Replace <...> with your actual data.
 * <Student1 Name>
 * <Student1 EID>
 * <Student1 5-digit Unique No.>
 * <Student2 Name>
 * <Student2 EID>
 * <Student2 5-digit Unique No.>
 * Slip days used: <0>
 * Fall 2016
 */


import java.util.ArrayList;
import java.util.List;


/* see the PDF for descriptions of the methods and fields in this class
 * you may add fields, methods or inner classes to Critter ONLY if you make your additions private
 * no new public, protected or default-package code or data can be added to Critter
 */


public abstract class Critter {
	private static String myPackage;
	private	static List<Critter> population = new java.util.ArrayList<Critter>();
	private static List<Critter> babies = new java.util.ArrayList<Critter>();

	// Gets the package name.  This assumes that Critter and its subclasses are all in the same package.
	static {
		myPackage = Critter.class.getPackage().toString().split(" ")[1];
	}

	private static java.util.Random rand = new java.util.Random();
	public static int getRandomInt(int max) {
		return rand.nextInt(max);
	}

	public static void setSeed(long new_seed) {
		rand = new java.util.Random(new_seed);
	}


	/* a one-character long string that visually depicts your critter in the ASCII interface */
	public String toString() { return ""; }

	private int energy = 0;
	protected int getEnergy() { return energy; }

	private int x_coord;
	private int y_coord;

	/*
	0 - right
	1 - right up (x++, y--)
	2 - up
	3 - left up
	4 - left
	5 - left down
	6 - down
	7 - right down

	 */
	protected final void walk(int direction) {
	    energy -= walk_energy_cost;
	    int d = direction;
	    if(d==2){
	        decByOne(y_coord, 'y');
        }
        else if(d==6){
	        incByOne(y_coord, 'y');
        }
	    else if(d==0 || d==1 || d==7){
	        incByOne(x_coord, 'x');
            if(d==1){
                decByOne(y_coord, 'y');
            }
            else if(d==7){
                incByOne(y_coord, 'y');
            }
        }
        else if(d==3 || d==4 || d==5){
            decByOne(x_coord, 'x');
            if(d==3){
                decByOne(y_coord, 'y');
            }
            else if(d==5){
                incByOne(y_coord, 'y');
            }
        }
	}

	private static int decByOne(int coord, char axis){ //0 is x, 1 is y
        int ax = world_width;
        if(axis == 'y')
            ax = world_height;
	    if(coord==0){
	        coord = coord-1+ax;
        }
        else
            coord--;
	    return coord;
    }

    private static int incByOne(int coord, char axis){ //0 is x, 1 is y
        int ax = world_width;
        if(axis == 'y')
            ax = world_height;
        coord = (coord+1)%ax;
        return coord;
    }

	protected final void run(int direction) {   //how to do without zig zag
	    energy += (2*Params.walk_energy_cost);
	    energy -= Params.run_energy_cost;
	    walk(direction);
	    walk(direction);
	}

	protected final void reproduce(Critter offspring, int direction) {
	}

	public abstract void doTimeStep();
	public abstract boolean fight(String opponent);

	/**
	 * create and initialize a Critter subclass.
	 * critter_class_name must be the unqualified name of a concrete subclass of Critter, if not,
	 * an InvalidCritterException must be thrown.
	 * (Java weirdness: Exception throwing does not work properly if the parameter has lower-case instead of
	 * upper. For example, if craig is supplied instead of Craig, an error is thrown instead of
	 * an Exception.)
	 * @param critter_class_name
	 * @throws InvalidCritterException
	 */
	public static void makeCritter(String critter_class_name) throws InvalidCritterException {
		try{
			Class c = Class.forName(critter_class_name);
			Critter crit = (Critter)c.newInstance();
			crit.x_coord = getRandomInt(Params.world_width);
			crit.y_coord = getRandomInt(Params.world_height);
			crit.energy = Params.start_energy;
			population.add(crit);
		}
		catch(ClassNotFoundException cne){
			throw new InvalidCritterException(critter_class_name);
		}
		catch (InstantiationException ie){
			throw new InvalidCritterException(critter_class_name);
		}
		catch (IllegalAccessException iae){
			throw new InvalidCritterException(critter_class_name);
		}
	}

	/**
	 * Gets a list of critters of a specific type.
	 * @param critter_class_name What kind of Critter is to be listed.  Unqualified class name.
	 * @return List of Critters.
	 * @throws InvalidCritterException
	 */
	public static List<Critter> getInstances(String critter_class_name) throws InvalidCritterException {
		List<Critter> result = new java.util.ArrayList<Critter>();
		Critter crit;
		try{
			Class c = Class.forName(critter_class_name);
			crit = (Critter)c.newInstance();
		}
		catch(ClassNotFoundException cne){
			throw new InvalidCritterException(critter_class_name);
		}
		catch (InstantiationException ie){
			throw new InvalidCritterException(critter_class_name);
		}
		catch (IllegalAccessException iae){
			throw new InvalidCritterException(critter_class_name);
		}

		for(Critter w : population){
			if(crit.getClass().isInstance(w))
				result.add(w);
		}
		return result;
	}

	/**
	 * Prints out how many Critters of each type there are on the board.
	 * @param critters List of Critters.
	 */
	public static void runStats(List<Critter> critters) {
		System.out.print("" + critters.size() + " critters as follows -- ");
		java.util.Map<String, Integer> critter_count = new java.util.HashMap<String, Integer>();
		for (Critter crit : critters) {
			String crit_string = crit.toString();
			Integer old_count = critter_count.get(crit_string);
			if (old_count == null) {
				critter_count.put(crit_string,  1);
			} else {
				critter_count.put(crit_string, old_count.intValue() + 1);
			}
		}
		String prefix = "";
		for (String s : critter_count.keySet()) {
			System.out.print(prefix + s + ":" + critter_count.get(s));
			prefix = ", ";
		}
		System.out.println();
	}

	/* the TestCritter class allows some critters to "cheat". If you want to
	 * create tests of your Critter model, you can create subclasses of this class
	 * and then use the setter functions contained here.
	 *
	 * NOTE: you must make sure that the setter functions work with your implementation
	 * of Critter. That means, if you're recording the positions of your critters
	 * using some sort of external grid or some other data structure in addition
	 * to the x_coord and y_coord functions, then you MUST update these setter functions
	 * so that they correctly update your grid/data structure.
	 */
	static abstract class TestCritter extends Critter {
		protected void setEnergy(int new_energy_value) {
			super.energy = new_energy_value;
		}

		protected void setX_coord(int new_x_coord) {
			super.x_coord = new_x_coord;
		}

		protected void setY_coord(int new_y_coord) {
			super.y_coord = new_y_coord;
		}

		protected int getX_coord() {
			return super.x_coord;
		}

		protected int getY_coord() {
			return super.y_coord;
		}


		/*
		 * This method getPopulation has to be modified by you if you are not using the population
		 * ArrayList that has been provided in the starter code.  In any case, it has to be
		 * implemented for grading tests to work.
		 */
		protected static List<Critter> getPopulation() {
			return population;
		}

		/*
		 * This method getBabies has to be modified by you if you are not using the babies
		 * ArrayList that has been provided in the starter code.  In any case, it has to be
		 * implemented for grading tests to work.  Babies should be added to the general population
		 * at either the beginning OR the end of every timestep.
		 */
		protected static List<Critter> getBabies() {
			return babies;
		}
	}

	/**
	 * Clear the world of all critters, dead and alive
	 */
	public static void clearWorld() {
		population.clear();
		babies.clear();
	}

	public static void worldTimeStep() {
		for(Critter c : population){	//each critter does its timestep
			c.doTimeStep();
		}
		int[][] elevMap = new int[Params.world_height][Params.world_width];
        for(Critter c : population){
            int col = c.x_coord;
            int row = c.y_coord;
            elevMap[row][col]++;
        }
        for(int row = 0; row < elevMap.length; row++){
            for(int col = 0; col < elevMap[0].length; col++){
                if(elevMap[row][col]>1){
                    List<Integer> same = sameLocation(row, col);
                    for(int i = 0; i<same.size(); i++){
                        if(same.size()==2){
                            //need to figure out encounters
                        }
                    }
                }
            }
        }

	}
    //returns the indexes of the critter with the specified coordinates
	private static List<Integer> sameLocation(int row, int col){
	    List<Integer> sameList = new ArrayList<Integer>();
	    for(int i = 0; i<population.size(); i++){
	        if(population.get(i).x_coord == col && population.get(i).y_coord == row){
	            sameList.add(i);
            }
        }
        return sameList;
    }



	public static void displayWorld() {
		char[][] grid = new char[Params.world_height+2][Params.world_width+2];
		//this handles the border printing
		for(int row = 0; row < grid.length; row++){
		    for(int col = 0; col < grid[0].length; col++){
		        if(row==0 || row == grid.length-1){
		            if(col==0 || col==grid[0].length-1){
		                grid[row][col] = '+';
                    }
                    else{
                        grid[row][col] = '-';
                    }
                }
                else if(col==0 || col==grid[0].length-1){
                    grid[row][col] = '|';
                }
            }
        }
        //this puts each critter in the appropriate part of the grid
        //Overwrites to the critter later in the list
        for(Critter c : population){
		    int col = c.x_coord + 1;
		    int row = c.y_coord + 1;
		    grid[row][col] = c.toString().charAt(0);
        }
	}
}
