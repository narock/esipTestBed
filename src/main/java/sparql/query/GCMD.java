package sparql.query;

import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.RDFNode;
import java.util.ArrayList;
import sparql.*;

public class GCMD
{
	
 	 private String endpoint = Endpoints.gcmd;
	 private ArrayList <String> titles = new ArrayList <String> ();
	 private ArrayList <String> versions = new ArrayList <String> ();
	 
	 public ArrayList <String> getDataSetNames () { return titles; }
	 public ArrayList <String> getVersions () { return versions; }
	 
     public static void main(String[] args) {

    	 GCMD gcmd = new GCMD ();
    	 gcmd.testEndpoint();
    	 gcmd.submitQuery();
    	 ArrayList <String> ts = gcmd.getDataSetNames();
    	 ArrayList <String> vs = gcmd.getVersions();
    	 for ( int i=0; i<ts.size(); i++ )
    	 {
    		 if ( vs.get(i) == null ) 
    		 { 
    			 System.out.println( ts.get(i) );
    		 } else {
    			 System.out.println( ts.get(i) + " " + vs.get(i) );
    		 }
    	 }
	 
     }

     // version does not exist for many products and will likely be null
	 public void submitQuery() {
	        
		 String sparqlQueryString = 
				    "PREFIX gcmd: <http://gcmd.gsfc.nasa.gov/gcmd/v9/> " +
				    		"select distinct ?title ?version where { " +
				    		"?dataset a gcmd:Data_Set_Citation . " +
				    		"?dataset gcmd:Data_Set_Citation-Dataset_Title ?title . " +
				    		"OPTIONAL { ?dataset gcmd:Data_Set_Citation-Version ?version . } " +
	 						"} ORDER by ?title";
		 ResultSet results = Endpoints.queryEndpoint( endpoint, sparqlQueryString );
		 while (results.hasNext())
    	 {
    		 QuerySolution soln = results.nextSolution();
             titles.add( soln.get("?title").toString() );
             RDFNode version = soln.get("?version");
             if ( version == null ) {
            	 versions.add( null );
             } else {
            	 versions.add( soln.get("?version").toString() );  
             }
    	 }

	 }
	 
	 public void testEndpoint() { Endpoints.testEndpoint( endpoint ); }
	
}