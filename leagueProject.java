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
		if(attemptLogin() == true){
		int dir;
		dir = JOptionPane.showInputDialog(null, "To display all leagues and alter a league title, Enter \"0\" " + "\n" + "To Add or Remove teams from a 
			selected league, Enter \"1\"" + "\n" + "To check results of each game within a league or to alter results, Enter \"2\"")
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
	// Separate total leagues from leagues created by admin
		if(dir == 0){
		public Arraylist<String> usersLeagues = new ArrayList<String>();
		public String selectedLeague, newTitle;
		for(int i = 0; i < leagueDetails.size(); i++){
			if(leagueDetails.get(0).get(i) == adminID){
				usersLeagues.add(leagueDetails.get(2).get(i));
			}
		}
		// Display list of leagues that the user created
		JOptionPane.showMessageDialog(null, "Here is a list of all leagues owned by " + Username + ":" + "\n" + usersLeagues);
		// Prompt user to select a league from the list
		selectedLeague0 = JOptionPane.showInputDialog(null, "Which league would you like to change?");
		// Ask user to enter new title for selected league
		newTitle = JOptionPane.showInputDialog(null, "Enter new league title");
		// Simply switching the titles of the leagues for both the total leagues and users leagues
		for(int i = 0; i < leagueDetails.size(); i++){
			if(leagueDetails.get(2).get(i) == selectedLeague0){
				leagueDetails.get(2).get(i) = newTitle;
			}
		}
		for(int i = 0; i < usersLeagues.size(); i++){
			if(usersLeagues.get(i) == selectedLeague0){
				usersLeagues.get(i) = newTitle;
			}
		}
		// Display list of leagues post edit
		JOptionPane.showMessageDialog(null, "Here is an updated list of all leagues owned by " + Username + ":"+ "\n" + usersLeagues);
	}
	// User wishes to access teams in a league
	if(dir == 1){
		ArayList<String> teams = new ArrayList<String>;
		String selectedLeague1, newTeam;
		int id, dir1;
		// Display list of leagues belonging to user
		JOptionPane.showMessageDialog(null, "Here is a list of all leagues owned by " + Username + ":" + "\n" + usersLeagues);
		selectedLeague1 = JOptionPane.showInputDialog(null, "Which league would you like to access?");
		// Loop through selected league matching League ID to leagueTeam ID
		for(int i = 0; i < leagueDetails.size(); i++){
			if(leagueDetails.get(2).get(i) == selectedleague1){
				id = i;
			}
		}
		for(int i = 0; i < leagueTeams.size(); i ++){
			if(leagueTeams.get(0).get(i) == id){
				teams.add(leagueTeams.get(0).get(i));		
			}
		}
		// Display teams in selected league
		JOptionPane.showMessageDialog(null, "Here is a list of all the teams in " + selectedLeague1 + ":" + "\n" + teams);
		// Create another directory
		dir1 = JOptionPane.showInputDialog(null, "To add a new team to the league, Enter : \"1\" " + "\n" "To remove a team from the league, Enter : \"2\"")
		// Add new team
		if(dir1 == 1){
			newTeam = JOptionPane.showInputDialog(null, "Enter new team name");
			teams.add(newTeam);
		}
		// Remove a team
		if(dir1 == 2){

		}
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