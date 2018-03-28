import java.awt.*;
import java.util.*;
import java.io.*;
import javax.swing.*;

public class leagueProject{
    private static ArrayList<ArrayList<String>> adminDetails = new ArrayList<>();
    private static ArrayList<ArrayList<String>> leagueDetails = new ArrayList<>();
    private static ArrayList<String> leagueTeams = new ArrayList<>();
    private static ArrayList<ArrayList<String>> fixtureDetails = new ArrayList<>();
    private static ArrayList<ArrayList<String>> resultDetails = new ArrayList<>();

    // Currently logged in admin details
    private static int adminID = -1;
    private static int adminIndex = -1;
    // League which is being edited
    private static int leagueIndex = -1;
    private static int leagueID = -1;
    private static String leagueTitle = "";
    // Let program know when to refresh league data
    private static boolean refreshLeagueData = true;

    private static Scanner in;
    private static FileWriter fw;

    public static void init(){
        /**
         * init() method
         * initialises arrays
         * loads admin and league files to arrays
         */

        adminDetails.add(new ArrayList<>());
        adminDetails.add(new ArrayList<>());
        adminDetails.add(new ArrayList<>());

        leagueDetails.add(new ArrayList<>());
        leagueDetails.add(new ArrayList<>());
        leagueDetails.add(new ArrayList<>());
        // Points for draw win and loss
        leagueDetails.add(new ArrayList<>());
        leagueDetails.add(new ArrayList<>());
        leagueDetails.add(new ArrayList<>());

        fixtureDetails.add(new ArrayList<>());
        fixtureDetails.add(new ArrayList<>());
        fixtureDetails.add(new ArrayList<>());

        resultDetails.add(new ArrayList<>());
        resultDetails.add(new ArrayList<>());

        if( !loadFileToArray("admins", adminDetails, null) ){
            JOptionPane.showMessageDialog(null, "Admin file could not be loaded.", "Warning", JOptionPane.WARNING_MESSAGE);
            System.exit(0);
        }

        if( !loadFileToArray("leagues", leagueDetails, null) ){
            // Create leagues file to be written to later
            try {
                fw = new FileWriter("leagues.txt");
                fw.close();
            } catch (Exception e){
                System.out.println("Error : Could not write new leagues.txt file");
                System.exit(0);
            }
        }
    }

    // Adam O'Brien
    public static boolean loadFileToArray(String fileLoc, ArrayList<ArrayList<String>> arr1, ArrayList<String> arr2){
        /**
         * loadFileToArray() method loads the contents of a given file to an array
         *
         * returns true if operation was sucessful
         */
        try {
            File file = new File(fileLoc + ".txt");
            System.out.println("Loading file : " + fileLoc + ".txt");
            if (file.exists()) {
                in = new Scanner(file);

                while (in.hasNextLine()) {
                    String entry = in.nextLine();
                    String[] details = entry.split(",");

                    // Add details to array
                    for (int i = 0; i < details.length; i++) {
                        if (arr2 != null) {
                            arr2.add(details[i]);
                        } else {
                            arr1.get(i).add(details[i]);
                        }
                    }
                }
                in.close();
                return true;
            }
            return false;
        } catch (Exception e){
            return false;
        }
    }

    public static boolean saveArrayToFile(String fileName, ArrayList<ArrayList<String>> arr1, ArrayList<String> arr2){
        /**
         * saveArrayToFile() method saves the data from a given array to a file
         */
        try{
            File file = new File(fileName + ".txt");
        } catch (Exception e){
            return false;
        }

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

        if( arr1 != null ){
            for( int i = 0; i < arr1.get(0).size(); i++ ){
                String element = "";
                for( int j = 0; j < arr1.size(); j++ ){
                    element += arr1.get(j).get(i) + ",";
                }
                element = element.substring(0, element.length() -1);
                result += element + "\n";
            }
        } else if( arr2 != null ){
            for( int i = 0; i < arr2.size(); i++ ){
                result += arr2.get(i) + "\n";
            }
        }

        try {
            fw = new FileWriter(fileName + ".txt");
            fw.write(result);
            fw.close();
        } catch (Exception e){
            return false;
        }
        return true;
    }

    public static void deleteLeague(){
        /**
         * deleteLeague() method deletes all data linked to a league
         * the league details are removed from the leagues array
         *
         * league results, fixtures and teams files are removed
         * the league details array is updated
         */
        leagueDetails.get(0).remove(leagueID);
        leagueDetails.get(1).remove(leagueID);
        leagueDetails.get(2).remove(leagueID);

        deleteFile(leagueID + "_results");
        deleteFile(leagueID + "_teams");
        deleteFile(leagueID + "_fixtures");
        deleteFile(leagueID + "_resultsTable");
        saveArrayToFile("leagues", leagueDetails, null);
    }

    public static void deleteFile(String fileName){
        /**
         * deleteFile() method deletes a file
         */

        System.out.println("Deleting file : " + fileName + ".txt");
        File file = new File("./" + fileName + ".txt");
        if( file.exists() ){
            System.out.println("File exitst");
            if( file.delete() ){
                System.out.println("Done");
            }
        }
    }

    public static void attemptLogin(){
        /**
         * attemptLogin() method asks for a users username and password
         * these details are compared with a list of admins
         * if the details are correct the admin is logged in
         */
        JTextField username = new JTextField();
        JTextField password = new JPasswordField();
        Object[] message = {
                "Username:", username,
                "Password:", password
        };

        int option = JOptionPane.showConfirmDialog(null, message, "Login", JOptionPane.OK_CANCEL_OPTION);
        if( option == JOptionPane.OK_OPTION ){
            for( int i = 0; i < adminDetails.get(0).size(); i++ ){
                if( adminDetails.get(1).get(i).equals(username.getText()) && adminDetails.get(2).get(i).equals(password.getText()) ){
                    adminID = Integer.parseInt(adminDetails.get(0).get(i));
                }
            }
        }
    }

    public static void createNewLeague(){
        /**
         * createNewLeague() method allows the user to create a new league
         * the user is prompted to enter a league title
         *
         * calls the enterLeagueTeams() method to allow the user to enter teams for their league
         *
         * the data from the user is added to arrays
         * these arrays are saved as files to the computer
         *
         * the showMenuOptions() method is called on successful completion of the league
         */
        String leagueTitle = "";
        int leagueID = 0;
        // Ask user for league title
        // Check that leagueTitle isnt already in use
        boolean isDuplicate = true;
        while( isDuplicate ){
            leagueTitle = inputItem("league title", null);
            if( leagueTitle.isEmpty() ){
                JOptionPane.showMessageDialog(null, "League title cannot be empty.");
                continue;
            }
            if( !leagueDetails.get(2).contains(leagueTitle) ){
                isDuplicate = false;
            } else {
                JOptionPane.showMessageDialog(null, "Please enter an unused league title.");
                // Show list of used league titles.
            }
        }

        boolean valid = false;

        while (!valid){
            String draw = JOptionPane.showInputDialog(null, "Enter amount of points earned for draw.");
            String win = JOptionPane.showInputDialog(null, "Enter amount of points earned for win.");
            String loss = JOptionPane.showInputDialog(null, "Enter amount of points deducted for loss.");

            if( (draw != null && !draw.isEmpty()) && (win != null && !win.isEmpty()) && (loss != null && !loss.isEmpty()) ){
                leagueDetails.get(3).add(draw);
                leagueDetails.get(4).add(win);
                leagueDetails.get(5).add(loss);
                valid = true;
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
        if( !saveArrayToFile("leagues", leagueDetails, null) ){
            System.out.println("Could not save file leagues.txt");
            System.exit(0);
        }
        if( !saveArrayToFile(leagueID + "_teams", null, leagueTeams) ){
            System.out.println("Could not save file " + leagueID + "_teams.txt");
            System.exit(0);
        }

        showMenuOptions();
    }

    public static void clearArray(ArrayList<ArrayList<String>> arr1, ArrayList<String> arr2){
        if( arr1 != null ){
            for( int i = 0; i < arr1.size(); i++ ){
                arr1.get(i).clear();
            }
        } else {
            arr2.clear();
        }
    }

    public static String inputItem(String item, String escape){
        /**
         * inputItem() method prompts the user to enter a string
         * all input validation is done
         * this method returns the entered string if it is valid
         */
        String newItem = null,
                pattern = "[0-9a-zA-Z -_]+",
                messageBody = "Enter " + item + ":";
        if( escape != null ){
            messageBody += "(Type \'"+ escape +"\' to move to next step)";
        }

        do{
            newItem = JOptionPane.showInputDialog(null, messageBody);

            if( newItem != null && !newItem.isEmpty() ){
                if (newItem.matches(pattern)) {
                    if (newItem.equals(escape)) {
                        return null;
                    }
                    return newItem;
                }
            } else {
                if( escape == null ){
                    return null;
                }
                JOptionPane.showMessageDialog(null, "Please enter a valid "+ item +" (a-z 0-9 _ - allowed only)");
            }
        } while(newItem == null);

        return newItem;
    }

    public static void enterLeagueTeams(){
        /**
         * enterLeagueTeams() prompts the user to enter the teams for a league
         * continues to loop while the user is adding teams
         *
         * prompts the user with a list of added teams to check they are correct
         */
        String teamName = null;
        boolean addingTeams = true;

        while( addingTeams ){
            teamName = inputItem("team name", "next");

            if( teamName == null || teamName.isEmpty() ){
                if( leagueTeams.size() < 2 ){
                    JOptionPane.showMessageDialog(null, "You must have at least 2 teams in your league.");
                } else {
                    addingTeams = false;
                }
            } else {
                leagueTeams.add(teamName);
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

    public static void editLeagueTeams(){
        boolean finished = false;
        boolean changesMade = false;

        while( !finished ){
            String[] teamList = new String[leagueTeams.size() + 1];
            for( int i = 0; i < teamList.length - 1; i++ ){
                teamList[i] = leagueTeams.get(i);
            }
            teamList[leagueTeams.size()] = "+ Add a team";

            String selection = (String)JOptionPane.showInputDialog(null, "Select an option or team to edit", null, JOptionPane.QUESTION_MESSAGE, null, teamList, teamList[teamList.length-1]);

            if( selection == null ){
                finished = true;
            } else {

                if( selection.equals(teamList[teamList.length -1]) ){
                    String newTeamName = inputItem("team name", null);
                    if( newTeamName.isEmpty() ){
                        continue;
                    }
                    int teamIndex = leagueTeams.indexOf(newTeamName);
                    if( (teamIndex != -1) && !(teamIndex != teamIndex) ){
                        JOptionPane.showMessageDialog(null, "Cannot have duplicate team names '"+ newTeamName +"'.\nPlease choose a different name or rename the existing team.");
                        continue;
                    }
                    leagueTeams.add(newTeamName);
                    changesMade = true;
                    continue;
                }

                String updatedTeamName = JOptionPane.showInputDialog(null, "Enter new team name for '"+ selection +"'. (click 'cancel' to delete team)");

                int index = leagueTeams.indexOf(selection);

                if( updatedTeamName == null ){
                    if( leagueTeams.size() < 2 ){
                        JOptionPane.showMessageDialog(null, "Please leave at least two teams in the league.");
                    } else {
                        leagueTeams.remove( index );
                    }
                } else {
                    int updatedIndex = leagueTeams.indexOf(updatedTeamName);

                    if( !(updatedIndex == -1 || index == updatedIndex) ){
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
            saveArrayToFile(leagueID + "_teams", null, leagueTeams);
            JOptionPane.showMessageDialog(null, "League teams sucessfully updated.");
        }
    }

    public static void enterLeagueResults(){
        resultDetails.get(0).clear();
        resultDetails.get(1).clear();

        boolean cancel = false;

        for( int i = 0; i < fixtureDetails.get(0).size(); i++ ){
            boolean validResult = false;
            do {
                String messageBody = "Select winner of game "+ i +".\n";
                messageBody += "0 : Draw\n";
                messageBody += "1 : " + leagueTeams.get( Integer.parseInt(fixtureDetails.get(1).get(i).toString()) ) + "\n";
                messageBody += "2 : " + leagueTeams.get( Integer.parseInt(fixtureDetails.get(2).get(i).toString()) );

                String selection = JOptionPane.showInputDialog(null, messageBody);

                if( selection != null && !selection.isEmpty() ){
                    if( selection.equals("0") || selection.equals("1") || selection.equals("2") ){
                        resultDetails.get(0).add(fixtureDetails.get(0).get(i));
                        resultDetails.get(1).add(selection);
                        validResult = true;
                    }
                } else {
                    cancel = true;
                }
            } while(!validResult && !cancel );

            if( cancel ){
                break;
            }
        }

        if( !cancel ){
            saveArrayToFile(leagueID + "_results", resultDetails, null);
            JOptionPane.showMessageDialog(null, "League results sucessfully added.");
        }
    }

    public static void editLeagueResults(){
        boolean finished = false;
        String[] gameList = new String[fixtureDetails.get(0).size()];
        do{
            for( int i = 0; i < gameList.length; i++ ){
                // find out which team won each game
                gameList[i] = i + " : ";

                int t1 = Integer.parseInt(fixtureDetails.get(1).get(i).toString()),
                        t2 = Integer.parseInt(fixtureDetails.get(2).get(i).toString());

                switch( Integer.parseInt(resultDetails.get(1).get(i)) ){
                    case 0:
                        // draw
                        gameList[i] += leagueTeams.get(t1) + " vs " + leagueTeams.get( t2 );
                        break;
                    case 1:
                        // team 1 won
                        gameList[i] += leagueTeams.get(t1) + "(*) vs " + leagueTeams.get( t2 );
                        break;
                    case 2:
                        // team 2 won
                        gameList[i] += leagueTeams.get(t1) + " vs " + leagueTeams.get( t2 ) + "(*)";
                        break;
                }
            }

            Object selectedMatch = JOptionPane.showInputDialog(null, "Select a match (cancel to finish)", "Edit match result", JOptionPane.QUESTION_MESSAGE, null, gameList, gameList[0]);
            if( selectedMatch != null ){
                String selection = selectedMatch.toString();
                // find which match the user selected
                int matchID = Integer.parseInt( selection.substring(0, 2).replaceAll("\\s+","") );
                do {
                    String[] options = {
                            "Draw",
                            leagueTeams.get(Integer.parseInt(fixtureDetails.get(1).get(matchID))),
                            leagueTeams.get(Integer.parseInt(fixtureDetails.get(2).get(matchID)))
                    };
                    String matchResult = JOptionPane.showInputDialog(null, "Enter result of match " + matchID + "\n\n", "Edit match result", JOptionPane.QUESTION_MESSAGE, null, options, options[0]).toString();

                    if( matchResult != null ){
                        if( matchResult.equals("Draw") ){
                            resultDetails.get(1).set(matchID, "0");
                        } else {
                            if( fixtureDetails.get(1).get(matchID).equals( String.valueOf(leagueTeams.indexOf(matchResult)) ) ) {
                                resultDetails.get(1).set(matchID, "1");
                            } else {
                                resultDetails.get(1).set(matchID, "2");
                            }
                        }
                        matchID = -1;
                    }
                } while(matchID != -1);
            } else {
                if( saveArrayToFile(leagueID + "_result", resultDetails, null) ){
                    JOptionPane.showMessageDialog(null, "Results Updated");
                    finished = true;
                }
            }
        } while(!finished);
    }

    public static void editLeague(){
        /**
         * editLeague() method allow the user to edit different parts of their league
         *
         * a list is displayed to the user to select which section of the league they want to edit
         * or what operation they want to do e.g. delete league
         *
         * calls methods depending on selection
         *
         * calls showMenuOptions() method
         */

        // Display options about which part of the league needs to be changed
        // Check if user has changed league they wish to edit
        if( refreshLeagueData ){
            leagueID = Integer.valueOf(leagueDetails.get(1).get(leagueIndex));
            leagueTitle = leagueDetails.get(2).get(leagueIndex);

            // empty out arrays
            leagueTeams.clear();
            clearArray(fixtureDetails, null);
            clearArray(resultDetails, null);

            loadFileToArray(leagueID + "_teams", null, leagueTeams);
            loadFileToArray(leagueID + "_fixtures", fixtureDetails, null);
            loadFileToArray(leagueID + "_results", resultDetails, null);

            refreshLeagueData = false;
        }

        String[] menuOptions = {
                "Edit League Title",
                "Input / Edit League Teams",
                "Input / Edit League Results",
                "Generate Fixtures",
                "Show Fixtures",
                "Generate Results Table",
                "Show Results Table",
                "Delete League",
                "Exit League Editor"
        };
        String option = JOptionPane.showInputDialog(null, "Edit League", "Select an option", JOptionPane.QUESTION_MESSAGE, null, menuOptions, menuOptions[0]).toString();

        if( option != null ){

            int index = -1;
            for( int i = 0; i < menuOptions.length; i++ ){
                if( menuOptions[i].equals(option) ){
                    index = i;
                    break;
                }
            }

            switch ( index ){
                case 0:
                    // Edit league title
                    // Get new league title from user
                    String newTitle = inputItem("league title (currently '" + leagueTitle + "'", null);
                    // Validate new title
                    if( newTitle.isEmpty() ){
                        editLeague();
                    }

                    // Update title
                    leagueDetails.get(2).set(leagueIndex, newTitle);
                    // Save updated title to file
                    saveArrayToFile("leagues", leagueDetails, null);
                    // Display edit league options
                    editLeague();
                    break;
                case 1:
                    int confirm = JOptionPane.showConfirmDialog(null, "Notice : Editing teams will remove all current fixture and result data for this league. Is that ok?", "Edit teams notice", JOptionPane.OK_CANCEL_OPTION);

                    if( confirm == 0 ){
                        // Clear fixtures and results to stop old data from creating bugs
                        clearArray(fixtureDetails, null);
                        clearArray(resultDetails, null);
                        deleteFile(leagueID + "_fixtures");
                        deleteFile(leagueID + "_results");
                        editLeagueTeams();
                    } else {
                        editLeague();
                    }
                    break;
                case 2:
                    // Edit / Enter league results
                    // Check that league teams have been loaded and league consists of at least 2 teams
                    if( leagueTeams.size() >= 2 ){
                        if( resultDetails.get(0).size() > 0 ){
                            // Allow user to edit league results
                            editLeagueResults();
                        } else {
                            // Results file could not be loaded
                            if( fixtureDetails.get(0).size() > 0 ){
                                // Allow user to enter league results
                                enterLeagueResults();
                            } else {
                                // Fixtures could not be loaded
                                // Generate fixtures for league
                                JOptionPane.showMessageDialog(null, "No fixtures found for current league. Auto generating fixtures.");

                                clearArray(fixtureDetails, null);
                                deleteFile(leagueID + "_fixtures");
                                generateFixtures();
                                enterLeagueResults();
                            }
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "You need at least 2 teams in your league.");
                        editLeagueTeams();
                    }
                    break;
                case 3:
                    clearArray(fixtureDetails, null);
                    deleteFile(leagueID + "_fixtures");
                    if( leagueTeams.size() > 1 ){
                        generateFixtures();
                    } else {
                        JOptionPane.showMessageDialog(null, "Please enter teams before generating fixtures.");
                    }
                    break;
                case 4:
                    if( fixtureDetails.get(0).size() > 0 ){
                        showFixtures();
                    } else {
                        JOptionPane.showMessageDialog(null, "Please generate fixtures.");
                    }
                    break;
                case 5:
                    generateResultsTable();
                    break;
                case 6:
                    showResultsTable();
                    break;
                case 7:
                    if( JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this leauge?") == 0 ){
                        deleteLeague();
                        showMenuOptions();
                    }
                    break;
                case 8:
                    showMenuOptions();
                    break;
                default:
                    editLeague();
                    break;
            }
        } else {
            showMenuOptions();
        }

        editLeague();
    }

    public static void generateFixtures(){

        if( leagueTeams.size() < 1 ){
            JOptionPane.showMessageDialog(null, "Please add some teams before generating fixtures.");
            return;
        }

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
                fixtures[roundNumber][matchNumber] = homeTeamNumber + " v " + awayTeamNumber;
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

        int gameCounter = 0;

        for( int i = 0; i < fixtures.length; i++ ){
            for( int j = 0; j < fixtures[i].length; j++ ){
                String[] gameData = fixtures[i][j].split(" v ");
                int t1 = Integer.parseInt(gameData[0]),
                        t2 = Integer.parseInt(gameData[1]);
                if( additionalTeamIncluded ){
                    if( t1 == (numberOfTeams - 1) || t2 == (numberOfTeams - 1) ){
                        System.out.println("Match id : " + gameCounter + " is cancelled");
                        continue;
                    }
                }
                fixtureDetails.get(0).add(String.valueOf(gameCounter));
                fixtureDetails.get(1).add( String.valueOf(t1) );
                fixtureDetails.get(2).add( String.valueOf(t2) );
                gameCounter++;
            }
        }

        saveArrayToFile(leagueID + "_fixtures", fixtureDetails, null);

        JOptionPane.showMessageDialog(null, "Fixtures have been sucessfully generated.");
    }

    public static void showFixtures(){
        String result = "";
        int matchesPerRound = 0,
            round = 0;
        if( leagueTeams.size() % 2 == 0 ){
            // Even
            matchesPerRound = leagueTeams.size() / 2;
        } else {
            // Odd
            // amount of rounds
            matchesPerRound = (leagueTeams.size() - 1) / 2;
        }
        for( int i = 0; i < fixtureDetails.get(0).size(); i++ ){
            String team1 = leagueTeams.get( Integer.parseInt(fixtureDetails.get(1).get(i)) ),
                    team2 = leagueTeams.get( Integer.parseInt(fixtureDetails.get(2).get(i)) );
            // Display which games are being played in which rounds
            if( i % matchesPerRound == 0 ){
                round++;
                result += "\tRound " + round + "\n";
            }

            result += team1 + " vs " + team2 + "\n";
        }

        JOptionPane.showMessageDialog(null, result, "", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void generateResultsTable(){

        if( resultDetails.get(0).size() < 1 ){
            JOptionPane.showMessageDialog(null, "Please enter match results before generating results table.");
            return;
        }

        int[] teamScore = new int[leagueTeams.size()];
        int[] teams = new int[leagueTeams.size()];

        // Draw, Win, Loss
        int[] scores = {
                Integer.parseInt( leagueDetails.get(3).get(leagueIndex) ),
                Integer.parseInt( leagueDetails.get(4).get(leagueIndex) ),
                Integer.parseInt( leagueDetails.get(5).get(leagueIndex) )
        };

        // Populate teams array
        for( int i = 0; i < teams.length; i++ ){
            teams[i] = i;
        }

        for( int i = 0; i < resultDetails.get(0).size(); i++ ){
            int matchID = Integer.parseInt( resultDetails.get(0).get(i) ),
                    resultType = Integer.parseInt( resultDetails.get(1).get(i) );

            int team1 = Integer.parseInt( fixtureDetails.get(1).get(matchID) ),
                    team2 = Integer.parseInt( fixtureDetails.get(2).get(matchID) );

            switch(resultType){
                case 0:
                    // draw
                    teamScore[team1] += scores[0];
                    teamScore[team2] += scores[0];
                    break;
                case 1:
                    // team1 win
                    teamScore[team1] += scores[1];
                    // in case losing team is deducted points
                    teamScore[team2] += scores[2];
                    break;
                case 2:
                    teamScore[team2] += scores[1];
                    // in case losing team is deducted points
                    teamScore[team1] += scores[2];
                    // team2 win
                    break;
            }
        }

        for( int i = 0; i < teamScore.length-1; i++ ){
            int counter = 0;
            while( (i - counter) >= 0 && (teamScore[i - counter] < teamScore[(i+1) - counter]) ){
                int temp = teamScore[(i+1) - counter];
                teamScore[(i+1) - counter] = teamScore[i - counter];
                teamScore[i - counter] = temp;

                temp = teams[(i+1) - counter];
                teams[(i+1) - counter] = teams[i - counter];
                teams[i - counter] = temp;
                counter++;
            }
        }

        String fileBody = "";
        for( int i = 0; i < teamScore.length; i++ ){
            if( i == 0 ){
                fileBody += leagueTitle + " Results Table.\n\n";
                fileBody += "Team Name : Points Accumulated\n";
                fileBody += leagueTeams.get(teams[i]) + " : " + teamScore[i];
            } else {
                fileBody += leagueTeams.get(teams[i]) + " : " + teamScore[i];
            }

            fileBody += "\n";
        }

        try{
            fw = new FileWriter(leagueID + "_resultsTable.txt");
            fw.write(fileBody);
            fw.close();
        } catch (IOException e){
            e.printStackTrace();
        }

        JOptionPane.showMessageDialog(null, "Results Table for league generated and saved sucessfully.");
    }

    public static void showResultsTable(){
        String result = "";
        File file = new File(leagueID + "_resultsTable.txt");
        if( file.exists() ){
            try{
                in = new Scanner(file);
                while(in.hasNextLine() ){
                    result += in.nextLine() + "\n";
                }
            } catch(IOException e){
                System.out.println("Could not find file : " + leagueID + "_resultsTable.txt");
            }

            JOptionPane.showMessageDialog(null, result, leagueTitle + " Results Table.", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "Could not find result table data. Please generate results table.");
        }
    }

    public static void showMenuOptions(){
        /**
         * showMenuOptions() method displays a list of actions to the user
         * e.g. show list of leagues managed by current admin, showLeagues()
         */
        String pattern = "[0-9]+";
        String options = "";

        options += "What do you want to do?\n";
        options += "0: View My Leagues\n";
        options += "1: Create a new league\n";
        options += "2: Exit";

        String selection = JOptionPane.showInputDialog(null, options);

        if( selection != null && selection.matches(pattern) ){
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
            }
        }
        showMenuOptions();
    }

    public static void showLeagues(){
        /**
         * showLeagues() method shows a list of leagues that the currently logged in user is admin of
         * allows the user to select one of their leagues to edit etc.
         *
         * calls the editLeague() method
         */
        String options = "";
        for( int i = 0; i < leagueDetails.get(0).size(); i++ ){
            if( Integer.parseInt(leagueDetails.get(0).get(i).toString()) == adminID ){
                options += leagueDetails.get(2).get(i) + ",";
            }
        }
        options = options.substring(0, options.length() -1);

        String[] optionArr = options.split(",");

        String selection = "";

        selection = JOptionPane.showInputDialog(null,
                "Select a league",
                "Select League",
                JOptionPane.QUESTION_MESSAGE,
                null,
                optionArr,
                optionArr[0]).toString();
        if( selection != null ){
            if( leagueIndex != leagueDetails.get(2).indexOf(selection) ){
                // User has changed league
                // refresh data from files for this league
                refreshLeagueData = true;
            }
            leagueIndex = leagueDetails.get(2).indexOf(selection);
            editLeague();
        }
        showMenuOptions();
    }

    // Adam O'Brien
    public static void main(String[] args){
        init();

        JOptionPane.showMessageDialog(null, "Welcome to out league manager. Please login using your credentials.");

        int adminLoginCounter = 0;
        while( adminID == -1 && adminLoginCounter < 3 ){
            if( adminLoginCounter > 0 ){
                JOptionPane.showMessageDialog(null, "Incorrect login information.\nYou have " + (3 - adminLoginCounter) + " login attempts remaining.");
            }
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