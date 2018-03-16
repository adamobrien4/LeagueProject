import java.util.*;
import java.io.*;
import javax.swing.*;

public class leagueProject{
	public static ArrayList<ArrayList> adminDetails = new ArrayList<ArrayList>();
	public static ArrayList<ArrayList> leagueDetails = new ArrayList<ArrayList>();
	public static ArrayList<ArrayList> leagueTeams = new ArrayList<ArrayList>();
	public static ArrayList<ArrayList> fixtureDetails = new ArrayList<ArrayList>();
	public static ArrayList<ArrayList> resultDetails = new ArrayList<ArrayList>();

	public static int adminID = -1;

	public static Scanner in;
	public static PrintWriter writer;

	public static void init() throws IOException{
		adminDetails.add(new ArrayList<Integer>());
		adminDetails.add(new ArrayList<String>());
		adminDetails.add(new ArrayList<String>());

		leagueDetails.add(new ArrayList<Integer>());
		leagueDetails.add(new ArrayList<Integer>());
		leagueDetails.add(new ArrayList<String>());

		leagueTeams.add(new ArrayList<Integer>()); 
		leagueTeams.add(new ArrayList<String>());

		fixtureDetails.add(new ArrayList<Integer>());
		fixtureDetails.add(new ArrayList<Integer>());
		fixtureDetails.add(new ArrayList<Integer>());

		resultDetails.add(new ArrayList<Integer>());
		resultDetails.add(new ArrayList<String>());
		resultDetails.add(new ArrayList<Integer>());

		if( !loadFileToArray("admins.txt", adminDetails) ){
			JOptionPane.showMessageDialog(null, "Admin file could not be loaded.", "Warning", JOptionPane.WARNING_MESSAGE);
			System.exit(0);
		}

		if( !loadFileToArray("leagues.txt", adminDetails) ){
			// Create leagues file to be written to later
			writer = new PrintWriter("leagues.txt");
			writer.close();
		}
	}

	// Adam O'Brien
	public static boolean loadFileToArray(String fileLoc, ArrayList<ArrayList> arr) throws IOException{
		File file = new File(fileLoc);
		if( file.exists() ){
			in = new Scanner(file);

			while( in.hasNextLine() ){
				String entry = in.nextLine();
				String[] details = entry.split(",");

				// Add details to array
				for( int i = 0; i < details.length; i++ ){
					arr.get(i).add(details[i]);
				}
			}
			return true;
		}
		return false;
    }

    public static boolean attemptLogin(Arraylist Arr){
    	String Password, Username, cPassword, cUsername;
    	Username = JOptionPane.showInputDialog(null, "Please enter your Username");
		Password = JOptionPane.showInputDialog(null, "Please enter your Password");
    	for(int i = 0; i < Arr.get(1).size(); i++){
    		cUsername = Arr.get(1).get(i);
    		cPassword = Arr.get(2).get(i);
    		if(cUsername.equals(Username) && cPassword.equals(Password)){
    			adminID = i;
    			return true;
    		}
    	}
    }

	public static createNewLeague(){
		// Ask user for league title
		// Ask user to add teams to the league
		// Show league details to user
		// Ask if user accepts the details
		// Save data to files
		return false;
	}

	public static editLeague(int leagueID){
		// Display options about which part of the league needs to be changed

		// Title
		// Ask user to input new title

		// Teams
		// Allow user to remove teams from arraylist
		// Allow user to add teams to arraylist

		// Results
		// Loop through each game
		// Display result
		// Ask user if they want to change it
		// Allow user to skip to end of list and finish editing

		// Save edited data to file
	}

	public static generateResults(int leagueID){
		// Check that results have been entered for selected league
		// Calculate points for each team
		// Display list with teams (points descending)
		// Save generated results to file
	}

	public static generateFixtures(int leagueID){
		// Look through annettes code for fixture generation
	}

	public static void main(String[] args) throws IOException{
		init();

		// Menu handling
	}
}