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
    // Tedis Stumbrs
    public static boolean attemptLogin(Arraylist Arr){
    	public String Password, Username, cPassword, cUsername;
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

	// Tedis Stumbrs
	public static editLeague(int leagueID){
		// Display options about which part of the league needs to be changed
		if(attempLogin() == true){
		int dir;
		dir = JOptionPane.showInputDialog(null, "To display all leagues and alter a league title, Enter \"0\" " + \n + "To Add or Remove teams from a 
			selected league, Enter \"1\"" + \n + "To check results of each game within a league or to alter results, Enter \"2\"")
		leagueDirectory(dir);

		}
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

	public static leagueDirectory(int dir){
	// If login is successful will direct to a list of leagues owned by the admin
	// Separate total leagues into from leagues created by admin
		if(dir == 0){
		public Arraylist<String> usersLeagues = new ArrayList<String>();
		public String selectedLeague, newTitle;
		for(int i = 0; i < leagueDetails.size(); i++){
			if(leagueDetails.get(0).get(i) == adminID){
				usersLeagues.add.leagueDetails.get(2).get(i);
			}
		}
		JOptionPane.showMessageDialog(null, "Here is a list of all leagues owned by " + Username + ":" + /n + usersLeagues);
		// Prompt user to select a league from a list of leagues that they have created
		selectedLeague = JOptionPane.showInputDialog(null, "Which league would you like to change?");
		newTitle = JOptionPane.showInputDialog(null, "Enter new league title");
		for(int n = 0; n < leagueDetails.size(); n++){
			if(leagueDetails.get(2).get(n) == selectedLeague){
				leagueDetails.get(2).get(n) = newTitle;
			}
		}
		for(int x = 0; x < usersLeagues.size(); x++){
			if(usersLeagues.get(x) == selectedLeague){
				usersLeagues.get(x) = newTitle;
			}
		}
		JOptionPane.showMessageDialog(null, "Here is an updated list of all leagues owned by " + Username + ":"+ /n + usersLeagues);
	}
	if(dir == 1){

	}

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