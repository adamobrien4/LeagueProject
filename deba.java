import java.util.ArrayList;

public class deba{

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

    Allow users to create a new league

    Enter league title
    Enter teams for league
        Teams will be added to leagueTeams array

     --- End ToDo --- */

    public deba(){
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

    public static void main(String[] args) {
        new deba();
    }
}