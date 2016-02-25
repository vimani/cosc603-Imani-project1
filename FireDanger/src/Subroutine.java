/**
 * Subroutine
 * Created on Jan 25 2016.
 *
 * @author Victor Imani
 */

public class Subroutine {
	
	/** The dry factor. */
	//The days dry factor
	public static double dryFactor = 0;
	
	/** The dry. */
	//Dry Bulb Temperature
	public static double dry = 0;
	
	/** The wet. */
	//Wet Bulb Temperature
	public static double wet = 0;;
	
	/** The wind. */
	//The current wind speed in miles per hour
	public static double wind = 0;
	
	/** The precip. */
	//The past 24 hours precipitation
	public static double precip = 0;
	
	/** The b uo index. */
	//Yesterdays Buildup
	public static double bUOIndex = 1;
	
	/** The fine fuel moist. */
	//The current fine fuel moisture
	public static double fineFuelMoist = 99;
	
	/** The adjusted fuel moisture. */
	//Adjusted fine fuel moisture
	public static double adjustedFuelMoisture = 99;
	
	/** The fire load rating. */
	//Fire Load Rating(Man-Hour Base)
	public static double fireLoadRating = 0;
	
	/** The b u index. */
	//The last value of the Build Up Index Recovery
	public static double bUIndex = 1;
	
	/** The snow. */
	//Boolean for if there is snow
	public static boolean snow = false;
	
	/** The rain. */
	//Boolean for if there is rain
	public static boolean rain;
	
	/** The grass index. */
	//Grass spread index
	public static double gRassIndex;
	
	/** The timber index. */
	//Timber spread index
	public static double tImberIndex;
	
	/** The a. Dimension Values */
	//Table Dimension values used in computing the danger ratings
	public static double[] A = new double [4];
	
	/** The b. Dimension Values*/
	public static double[] B = new double [4];
	
	/** The c. Dimension Values*/
	public static double[] C = new double [3];
	
	/** The d. Dimension Values*/
	public static double[] D = new double [6];

	/**  The diff using dry and wet bulb. */
	public static double diff = dry - wet;
	
	/**
	 * Inits the dimensions.
	 */
	public static void initDimensions(){
		//Initialize Dimensions Used in Computing the Danger Ratings
		A[0] = -0.185900;
		A[1] = -0.85900;
		A[2] = -0.059660;
		A[3] = 0.077373;
		B[0] = 30.0;
		B[1] = 19.2;
		B[2] = 13.8;
		B[3] = 22.5;
		C[0] = 4.5;
		C[1] = 12.5;
		C[2] = 27.5;
		D[0] = 16.0;
		D[1] = 10.0;
		D[2] = 7.0;
		D[3] = 5.0;
		D[4] = 4.0;
		D[5] = 3.0;
	}
	
	/**
	 * Sets the snow.
	 *
	 * @param value the new snow
	 */
	//Set snow value
	public static void setSnow(boolean value){
		snow = value;
	}
	
	/**
	 * Checks if is rain.
	 *
	 * @return true, if is rain
	 */
	// Check Rain Level
	public static boolean isRain(){
		if(precip > 1){
			return true;
		}
		return false;
	}
	
	/**
	 * Calc fine fuel moisture.
	 *
	 * @param i the i
	 * @param stage the stage
	 */
	// Calculate Fine Fuel Moisture
	public static void calcFineFuelMoisture(int i, int stage){
		if (Stage.NONE == stage){
			fineFuelMoist = B[i] * Math.exp(A[i]) * (diff);
		}else if(Stage.HERB == stage){
			fineFuelMoist = B[i] * Math.exp(A[i]) * (diff);
		}else{
			fineFuelMoist = fineFuelMoist + (( - 1) * 0.05);
		}
	}
	
	/**
	 * Adjust build up index.
	 */
	//Adjust Build Up Index
	public static void adjustBUI(){
		bUIndex = -50*(Math.log(Math.exp(1-(-Math.exp(bUOIndex/50)))))*Math.exp(1.175*(precip - 0.1));
	}
	
	/**
	 * Calc adjusted fuel moisture.
	 */
	//Adjust Fine Fuel Moisture
	public static void calcAdjustedFuelMoisture(){
		adjustedFuelMoisture = (0.9*(fineFuelMoist)) + (9.5 * Math.exp(-bUIndex/50));
	}
	
	/**
	 * Calc grass spread index.
	 *
	 * @param speed the speed
	 */
	//Calculate grass Spread Index
	public static void calcGrassSpreadIndex(int speed){
		gRassIndex = 0.00918*(wind + speed) * (Math.pow((33 - fineFuelMoist), 1.65) -3);
	}
	
	/**
	 * Calc timber spread index.
	 *
	 * @param speed the speed
	 */
	//Calculate timber Spread Index
	public static void calcTimberSpreadIndex(int speed){
		tImberIndex = 0.00918*(wind + speed) * (Math.pow((33 - fineFuelMoist), 1.65) -3);
	}
	
	/**
	 * Calc fire load index.
	 */
	//Calculate fire load index
	public static void calcFireLoadIndex(){
		fireLoadRating = Math.pow(10, 1.75 * (Math.log10(tImberIndex) + 32 *(Math.log10(bUIndex) - 1.64)));
	}

	/**
	 * Inits the all index.
	 *
	 * @param i the i
	 */
	public static void initAllIndex(int i) {
		gRassIndex = i;
		tImberIndex = i;		
	}

}
