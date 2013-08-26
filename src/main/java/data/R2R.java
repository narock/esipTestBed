package data;

import java.util.ArrayList;

public class R2R {
	
	private ArrayList <String> cruiseIDs = new ArrayList <String> ();
	private ArrayList <String> cruiseTitles = new ArrayList <String> ();
	private ArrayList <String> vesselNames = new ArrayList <String> ();
	
	public ArrayList <String> getCruiseIDs () { return cruiseIDs; }
	public ArrayList <String> getCruiseTitles () { return cruiseTitles; }
	public ArrayList <String> getVesselNames () { return vesselNames; }
	
	public void addCruiseID ( String a ) { cruiseIDs.add(a); }
	public void addCruiseTitle ( String s ) { cruiseTitles.add(s); }
	public void addVesselName ( String s ) { vesselNames.add(s); }
	
	public ArrayList <String> mergeIDandTitle ( ) {
		
		ArrayList <String> results = new ArrayList <String> ();
		for ( int i=0; i<cruiseIDs.size(); i++ )
		{
			results.add( vesselNames.get(i) + " | " + cruiseIDs.get(i) + " | " + cruiseTitles.get(i) );
		}
		return results;
		
	}
	
}