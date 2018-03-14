import java.util.*;
import java.io.*;
import javax.swing.*;

public class leagueProject{
	public static ArrayList<ArrayList> adminDetails = new ArrayList<ArrayList>();
	public static ArrayList<ArrayList> leagueDetails = new ArrayList<ArrayList>();
	public static ArrayList<ArrayList> leagueTeams = new ArrayList<ArrayList>();
	public static ArrayList<ArrayList> fixtureDetails = new ArrayList<ArrayList>();
	public static ArrayList<ArrayList> resultDetails = new ArrayList<ArrayList>();

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

	public static void main(String[] args) throws IOException{
		init();
	}
}