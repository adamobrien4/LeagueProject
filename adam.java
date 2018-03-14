import java.util.ArrayList;

import javax.swing.JOptionPane;

public class adam{

    // Contains info about each admin
    ArrayList<ArrayList> adminDetails = new ArrayList<ArrayList>();

    // Contains info about each league
    ArrayList<ArrayList> leagueDetails = new ArrayList<ArrayList>();

    // Contains info about currently selected league
    // must be updated if changing leagues
    ArrayList<ArrayList> leagueTeams = new ArrayList<ArrayList>();

    // Contains fixture info about currently selected league
    // must be updated if changing leagues
    ArrayList<ArrayList> fixtureDetails = new ArrayList<ArrayList>();

    // Contains result info about currently selected league
    // must be updated if changing leagues
    ArrayList<ArrayList> resultDetails = new ArrayList<ArrayList>();

    /* --- ToDo --- 

    Generate results table for league

     --- End ToDo --- */

    public adam(){
        init();
        // Call your methods from here

        // E.g.
        helloWorld();
    }

    public void helloWorld(){
        System.out.println("Hello World");
    }


    public void init(){
        // This will run at the beginning when file is ran
        adminDetails.add(new ArrayList<Integer>());     // AdminID
        adminDetails.add(new ArrayList<String>());      // AdminUsername
        adminDetails.add(new ArrayList<String>());      // AdminPassword

        leagueDetails.add(new ArrayList<Integer>());    // AdminID
        leagueDetails.add(new ArrayList<Integer>());    // LeagueID
        leagueDetails.add(new ArrayList<String>());     // LeagueTitle

        leagueTeams.add(new ArrayList<Integer>());      // TeamID
        leagueTeams.add(new ArrayList<String>());       // TeamName

        fixtureDetails.add(new ArrayList<Integer>());   // GameID
        fixtureDetails.add(new ArrayList<Integer>());   // Team1ID
        fixtureDetails.add(new ArrayList<Integer>());   // Team2ID

        resultDetails.add(new ArrayList<Integer>());    // GameID
        resultDetails.add(new ArrayList<String>());     // ResultType (w = win, d = draw)
        resultDetails.add(new ArrayList<Integer>());    // WinnerID (-1 if draw)

        // Insert dummy info into adminDetails and leagueDetails
        for( int i = 0; i < 10; i++ ){
            adminDetails.get(0).add(i);
            adminDetails.get(1).add("adminusername" + i);
            adminDetails.get(2).add("adminpassword" + i);

            leagueDetails.get(0).add(i);
            leagueDetails.get(1).add(i);
            leagueDetails.get(2).add("leaguetitle" + i);
        }
    }

    public boolean createNewLeague(){

        String  leagueTitle,
                pattern = "[a-z0-9 ]{1,}",
                teamName = "";
        ArrayList<String> teams = new ArrayList<String>();

        leagueTitle = JOptionPane.showInputDialog(null, "Enter league title:");

        if( !leagueTitle.matches(pattern) || leagueTitle == null ){
            JOptionPane.showMessageDialog(null, "Please enter a valid league title.");
        } else {
            while( teamName != null ){
                teamName = JOptionPane.showInputDialog(null, "Enter next team name:");
            }
        }

        return false;
    }

    public static void main(String[] args) {
        new adam();
    }
}