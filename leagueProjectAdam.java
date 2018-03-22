import java.util.*;
import java.io.*;
import javax.swing.*;

public class leagueProjectAdam{
	public static ArrayList<ArrayList<String>> adminDetails = new ArrayList<ArrayList<String>>();
	public static ArrayList<ArrayList<String>> leagueDetails = new ArrayList<ArrayList<String>>();
	public static ArrayList<String> leagueTeams = new ArrayList<String>();
	public static ArrayList<ArrayList<ArrayList<String>>> fixtureDetails = new ArrayList<ArrayList<ArrayList<String>>>();
	public static ArrayList<ArrayList<String>> resultDetails = new ArrayList<ArrayList<String>>();

	// Currently logged in admin details
	public static int adminID = -1;
	public static int adminImdex = -1;
	// League which is being edited
	public static int leagueIndex = -1;

	public static Scanner in;
	public static FileWriter fw;

	public static void init() throws Exception{

		adminDetails.add(new ArrayList<String>());
		adminDetails.add(new ArrayList<String>());
		adminDetails.add(new ArrayList<String>());

		leagueDetails.add(new ArrayList<String>());
		leagueDetails.add(new ArrayList<String>());
		leagueDetails.add(new ArrayList<String>());

		fixtureDetails.add(new ArrayList<ArrayList<String>>());		

		resultDetails.add(new ArrayList<String>());
		resultDetails.add(new ArrayList<String>());
		resultDetails.add(new ArrayList<String>());

		if( !loadFileToArray("admins", adminDetails, null) ){
			JOptionPane.showMessageDialog(null, "Admin file could not be loaded.", "Warning", JOptionPane.WARNING_MESSAGE);
			System.exit(0);
		}

		if( !loadFileToArray("leagues", leagueDetails, null) ){
			// Create leagues file to be written to later
			fw = new FileWriter("leagues.txt");
			fw.close();
		}
	}

	// Adam O'Brien
	public static boolean loadFileToArray(String fileLoc, ArrayList<ArrayList<String>> arr1, ArrayList<String> arr2) throws Exception{
		File file = new File(fileLoc + ".txt");
		if( file.exists() ){
			in = new Scanner(file);

			while( in.hasNextLine() ){
				String entry = in.nextLine();
				String[] details = entry.split(",");

				// Add details to array
				for( int i = 0; i < details.length; i++ ){
					if( arr2 != null ){
						arr2.add(details[i]);
					} else {
						arr1.get(i).add(details[i]);
					}
				}
			}
			return true;
		}
		return false;
	}
	
	public static boolean saveArrayToFile(String fileName, ArrayList<ArrayList<String>> arr1, ArrayList<String> arr2) throws Exception{
		if( arr1 != null ){
			if( arr1.size() < 1 ){
				return false;
			}
		} else if( arr2 != null ){
			if( arr2.size() < 1 ){
				return false;
			}
		}
		String result = "";

		System.out.println("\n Saving file " + fileName);

		if( arr1 != null ){
			for( int i = 0; i < arr1.get(0).size(); i++ ){
				String element = "";
				for( int j = 0; j < arr1.size(); j++ ){
					element += arr1.get(j).get(i) + ",";
				}
				element = element.substring(0, element.length() -1);
				System.out.println("Element " + element);
				result += element + "\n";
			}
		} else if( arr2 != null ){
			for( int i = 0; i < arr2.size(); i++ ){
				result += arr2.get(i) + "\n";
			}
		}
		System.out.println(result);

		fw = new FileWriter(fileName + ".txt");
		fw.write(result);
		fw.close();
		return true;
	}

	/* 			Fix login attempt function				*/
    public static void attemptLogin(){
		String username, password;
		username = JOptionPane.showInputDialog(null, "Enter username:");
		password = JOptionPane.showInputDialog(null, "Enter password:");
		if( username != null && password != null ){
			for( int i = 0; i < adminDetails.get(0).size(); i++ ){
				if( adminDetails.get(1).get(i).equals(username) && adminDetails.get(2).get(i).equals(password) ){
					adminID = Integer.parseInt(adminDetails.get(0).get(i));
				}
			}
		}
    }

	public static void createNewLeague() throws Exception{
		String leagueTitle = "";
		int leagueID = 0;
		// Ask user for league title
		// Check that leagueTitle isnt already in use
		boolean isDuplicate = true;
		while( isDuplicate ){
			leagueTitle = inputItem("league title", null);
			if( !leagueDetails.get(2).contains(leagueTitle) ){
				isDuplicate = false;
			} else {
				JOptionPane.showMessageDialog(null, "Please enter an unused league title.");
				// Show list of used league titles.
			}
		}
		// Ask user to add teams to the league
		enterLeagueTeams();

		// Get id of new league
		if( leagueDetails.get(1).size() > 0 ){
			leagueID = Integer.parseInt(leagueDetails.get(1).get(leagueDetails.get(1).size() - 1)) + 1;
		}
		System.out.println("The id of the new league is " + leagueID);
		// Setup league in leagueDetails array
		leagueDetails.get(0).add(Integer.toString(adminID));
		leagueDetails.get(1).add(Integer.toString(leagueID));
		leagueDetails.get(2).add(leagueTitle);

		// Save data to files
		saveArrayToFile("leagues", leagueDetails, null);
		saveArrayToFile(leagueID + "_leagueTeams", null, leagueTeams);
	}

	public static String inputItem(String item, String escape){
		String newItem = null,
			pattern = "[0-9a-zA-Z -_]{1,}",
			messageBody = "Enter " + item + ":";
			if( escape != null ){
				messageBody += "(Type \'"+ escape +"\' to move to next step)";
			}

		do{
			newItem = JOptionPane.showInputDialog(null, messageBody);
			
			if( newItem != null ){
				if( newItem.matches(pattern) && !newItem.isEmpty() ){
					if( newItem.equals(escape) ){
						return "";
					}
					return newItem;
				}
			} else {
				if( escape == null ){
					return "";
				}
				JOptionPane.showMessageDialog(null, "Please enter a valid "+ item +" (a-z 0-9 _ - allowed only)");
			}
		} while(newItem == null);

		return newItem;
	}

	public static void enterLeagueTeams() throws Exception{
		String teamName = null;
		boolean addingTeams = true;

		while( addingTeams ){
			teamName = inputItem("team name", "next");
			if( teamName != "" ){
				leagueTeams.add(teamName);
			} else {
				addingTeams = false;
			}
		}
		
		// Show league details to user
		String teamList = "Are these teams correct?\n";
		for( int i = 0; i < leagueTeams.size(); i++ ){
			teamList += i + " : " + leagueTeams.get(i) + "\n";
		}

		boolean teamsCorrect = false;
		while( !teamsCorrect ){
			int selection = JOptionPane.showConfirmDialog(null, teamList);
			System.out.println(selection);
			if( selection != 0 ){
				editLeagueTeams();
			} else {
				teamsCorrect = true;
			}
		}
	}

	public static void editLeagueTeams() throws Exception{
		boolean finished = false;
		boolean changesMade = false;

		while( !finished ){
			String[] teamList = new String[leagueTeams.size() + 1];
			for( int i = 0; i < teamList.length - 1; i++ ){
				teamList[i] = leagueTeams.get(i);
			}
			teamList[leagueTeams.size()] = "+ Add a team";

			String selection = (String)JOptionPane.showInputDialog(null, "Select an option or team to edit", null, JOptionPane.QUESTION_MESSAGE, null, teamList, teamList[0]);
			
			if( selection == null ){
				finished = true;
			} else {

				if( selection.equals(teamList[teamList.length -1]) ){
					String newTeamName = inputItem("team name", null);
					if( leagueTeams.indexOf(newTeamName) != leagueTeams.lastIndexOf(newTeamName) ){
						JOptionPane.showMessageDialog(null, "Cannot have duplicate team names '"+ newTeamName +"'.\nPlease choose a different name or rename the existing team.");
						continue;
					}
					leagueTeams.add(newTeamName);
					continue;
				}

				String updatedTeamName = JOptionPane.showInputDialog(null, "Enter new team name for '"+ selection +"'. (click 'cancel' to delete team)");
				
				int index = leagueTeams.indexOf(selection);
				
				if( updatedTeamName == null ){
					if( leagueTeams.size() < 1 ){
						JOptionPane.showMessageDialog(null, "Please leave at least one team in the league.");
					} else {
						leagueTeams.remove( index );
					}					
				} else {
					int updatedIndex = leagueTeams.indexOf(updatedTeamName);

					if( updatedIndex == -1 ){
						// does not exist in teams list
					} else if( index == updatedIndex ){
						// Overwriting team name with same name
					} else {
						// duplicate
						JOptionPane.showConfirmDialog(null, "Cannot have duplicate team names '"+ updatedTeamName +"'.\nPlease choose a different name or rename the existing team.");
						continue;
					}
					leagueTeams.set( index, updatedTeamName );
				}
				changesMade = true;
			}
		}

		if( changesMade ){
			saveArrayToFile(leagueDetails.get(1).get( leagueIndex ) + "_leagueTeams", null, leagueTeams);
		}
	}

	public static void editLeague() throws Exception{
		// Display options about which part of the league needs to be changed
		int leagueID = Integer.valueOf(leagueDetails.get(1).get(leagueIndex));
		String leagueTitle = leagueDetails.get(2).get(leagueIndex);

		String menuOptions = "League : "+ leagueID +"0 : Edit League Title\n1 : Edit League Teams\n2 : Edit League Results\n3 : Generate Fixtures\n4 : Exit league editor";
		String option = JOptionPane.showInputDialog(null, menuOptions);

		if( option.equals("0") ) {
			String newTitle = JOptionPane.showInputDialog(null, "Enter a new league title. Currently '" + leagueTitle + "'.");
			
			String newTitle = inputItem("league title", null);
			if( newTitle.isEmpty() ){
				editLeague();
			}
			leagueDetails.get(2).set(leagueIndex, newTitle);
			saveArrayToFile("leagues", leagueDetails, null);
			editLeague();
		} else if( option.equals("1") ){
			// Load teams from file to array
			leagueTeams.clear();
			loadFileToArray(leagueID + "_leagueTeams", null, leagueTeams);
			editLeagueTeams();
		} else if( option.equals("2") ){
			resultDetails.get(0).clear();
			resultDetails.get(1).clear();
			resultDetails.get(2).clear();

			loadFileToArray(leagueID + "_results", resultDetails, null);
		} else if( option.equals("3") ){
			leagueTeams.clear();
			loadFileToArray(leagueID + "_leagueTeams", null, leagueTeams);
			generateFixtures();
		} else {
			showMenuOptions();
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
	}

	public static void generateResults(int leagueID){
		// Check that results have been entered for selected league
		// Calculate points for each team
		// Display list with teams (points descending)
		// Save generated results to file
	}

	public static void generateFixtures() throws Exception{
		int numberOfTeams, totalNumberOfRounds, numberOfMatchesPerRound;
		int roundNumber, matchNumber, homeTeamNumber, awayTeamNumber, even, odd;
		boolean additionalTeamIncluded = false;
		String[][] fixtures;
		String[][] revisedFixtures;
		String[] elementsOfFixture;
		String fixtureAsText;

		numberOfTeams = leagueTeams.size();
		if (numberOfTeams % 2 == 1) {
			numberOfTeams++;
			additionalTeamIncluded = true;
		}
		totalNumberOfRounds	= numberOfTeams - 1;
		numberOfMatchesPerRound = numberOfTeams / 2;

		fixtures = new String[totalNumberOfRounds][numberOfMatchesPerRound];  
			
		for (roundNumber = 0; roundNumber < totalNumberOfRounds; roundNumber++) {
			for (matchNumber = 0; matchNumber < numberOfMatchesPerRound; matchNumber++) {
				homeTeamNumber = (roundNumber + matchNumber) % (numberOfTeams - 1);
				awayTeamNumber = (numberOfTeams - 1 - matchNumber + roundNumber) % (numberOfTeams - 1);
				if (matchNumber == 0){
					awayTeamNumber = numberOfTeams - 1;
				}
				fixtures[roundNumber][matchNumber] = (homeTeamNumber + 1) + " v " + (awayTeamNumber + 1);
			}
		} 
		revisedFixtures = new String[totalNumberOfRounds][numberOfMatchesPerRound];
		even = 0;
		odd = numberOfTeams / 2;
		for (int i = 0; i < fixtures.length; i++) {
			if (i % 2 == 0){	
				revisedFixtures[i] = fixtures[even++];
			} else { 				
				revisedFixtures[i] = fixtures[odd++];
			}
		}
		fixtures = revisedFixtures;
			
		for (roundNumber = 0; roundNumber < fixtures.length; roundNumber++) {
			if (roundNumber % 2 == 1) {
				fixtureAsText = fixtures[roundNumber][0];
				elementsOfFixture = fixtureAsText.split(" v ");
				fixtures[roundNumber][0] = elementsOfFixture[1] + " v " + elementsOfFixture[0];
			}
		}

		for (roundNumber = 0; roundNumber < totalNumberOfRounds; roundNumber++) {
			System.out.println("Round " + (roundNumber + 1) + "\t\t");  
			for (matchNumber = 0; matchNumber < numberOfMatchesPerRound; matchNumber++) 
				System.out.println("\tMatch " + (matchNumber + 1) + ": " + fixtures[roundNumber][matchNumber] + "\t\n");
		}		  
		System.out.print("\nYou will have to use the mirror image");
		System.out.println(" of these fixtures for return fixtures.");
		if (additionalTeamIncluded){
			System.out.println("\nSince you had " + (numberOfTeams - 1) 
				+ " teams at the outset (uneven number), fixtures "
				+ "against team number " 
				+ numberOfTeams + " are byes.");
		}

		editLeague();
	}

	public static void showMenuOptions() throws Exception{
		String options = "";

		options += "What do you want to do?\n";
		options += "0: View My Leagues\n";
		options += "1: Create a new league\n";
		options += "2: Exit";

		String selection = JOptionPane.showInputDialog(null, options);

		switch(Integer.parseInt(selection)){
			case 0:
				if( leagueDetails.get(0).contains(Integer.toString(adminID)) ){
					showLeagues();
				} else {
					JOptionPane.showMessageDialog(null, "No leagues found. Please create a new league.");
					showMenuOptions();
				}
				break;
			case 1:
				createNewLeague();
				break;
			case 2:
				System.exit(0);
				break;
			default:
				showMenuOptions();
				break;
		}
	}

	public static void showLeagues() throws Exception{
		String options = "";
		for( int i = 0; i < leagueDetails.get(0).size(); i++ ){
			if( Integer.parseInt(leagueDetails.get(0).get(i).toString()) == adminID ){
				options += leagueDetails.get(2).get(i) + ",";
			}
		}
		options = options.substring(0, options.length() -1);

		String[] optionArr = options.split(",");

		String selection = "";
		
		try {
			selection = JOptionPane.showInputDialog(null, 
				"Select a league",
				"Select League",
				JOptionPane.QUESTION_MESSAGE, 
				null, 
				optionArr,
				optionArr[0]).toString();

			leagueIndex = leagueDetails.get(2).indexOf(selection);
			editLeague();
		} catch (Exception e){
			showMenuOptions();
		}
	}

	// Adam O'Brien
	public static void main(String[] args) throws Exception{
		init();

		JOptionPane.showMessageDialog(null, "Welcome to out league manager. Please login using your credentials.");

		int adminLoginCounter = 0;
		while( adminID == -1 && adminLoginCounter < 3 ){
			attemptLogin();
			adminLoginCounter++;
		}

		if( adminID == -1 ){
			JOptionPane.showMessageDialog(null, "You ran out of login attempts.");
			System.exit(0);
		}

		showMenuOptions();

		// Menu handling
	}
}