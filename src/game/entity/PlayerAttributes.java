package game.entity;

// this class stores player attributes so that they can be reused when states change
public class PlayerAttributes 
{
	// fields 
	
	private static int score;				// score of the player
	private static int health;				// health of the player
	private static int lives;				// lives of the player
	private static int playerNum;			// number of the player
	private static int coins;				// coins of the player
	private static long time;				// time of the player
	
	// initialize method resets the attributes for the player
	public static void initialize(int playerNumber) 
	{
		// set values for all player attributes
		health = 4;
		lives = 3;
		playerNum = playerNumber;
		coins = 0;
		time = 0;
		score = 0;
	} // end initialize method
	
	// getHalth method returns health
	public static int getHealth() 
	{
		return health;
	} // end getHealth method
	
	// getLives method returns lives
	public static int getLives() 
	{
		return lives;
	} // end getLives method
	
	// getPlayerNum method returns the number of the player
	public static int getPlayerNum() 
	{
		return playerNum;
	} // end getPlayerNum method
	
	// getCoins method returns the number of coins
	public static int getCoins() 
	{
		return coins;
	} // end getCoins method
	
	// getTime method returns the time
	public static long getTime() 
	{
		return time;
	} // end getTime method
	
	// getScore method returns the score
	public static int getScore() 
	{
		return score;
	} // end getScore method
	
	// set attributes
	
	// setHealth method sets the health
	public static void setHealth(int health) 
	{
		PlayerAttributes.health = health;
	} // end setHealth method
	
	// setLives method sets lives
	public static void setLives(int lives) 
	{
		PlayerAttributes.lives = lives;
	} // end setLives method
	
	// setPlayerNum method sets the number of player
	public static void setPlayerNum(int playerNum) 
	{
		PlayerAttributes.playerNum = playerNum;
	} // end setPlayerNum method
	
	// setCoins method sets the number of coins 
	public static void setCoins(int coins) 
	{
		PlayerAttributes.coins = coins;
	} // end setCoins method
	
	// setTime method sets the time
	public static void setTime(long time) 
	{
		PlayerAttributes.time = time;
	} // end setTime method
	
	// setScore method sets the score
	public static void setScore(int score) 
	{
		PlayerAttributes.score = score;
	} // end setScore method
} // end PlayerAttributes class
