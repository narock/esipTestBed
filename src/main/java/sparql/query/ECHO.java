package sparql.query;

import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import java.util.ArrayList;
import sparql.*;

public class ECHO
{
	
 	 private String endpoint = Endpoints.echo;
	 private ArrayList <String> dataSetNames = new ArrayList <String> ();
	 
	 public ArrayList <String> getDataSetNames () { return dataSetNames; }
	 
     public static void main(String[] args) {

    	 ECHO echo = new ECHO ();
    	 echo.testEndpoint();
    	 echo.submitQuery();
    	 ArrayList <String> names = echo.getDataSetNames();
    	 for ( int i=0; i<names.size(); i++ )
    	 {
    		 System.out.println( names.get(i) );
    	 }
	 
     }

	 public void submitQuery() {
	        
		 String sparqlQueryString = 
				    "PREFIX echo: <http://esipfed.org/ns/discovery/ECHO/v10/> " +
				    		"select ?name where { " +
				    		" ?s echo:LongName ?name . " +
				    		" ?s echo:DataSetId ?id " +
	 						"} ORDER by ?name";
		 ResultSet results = Endpoints.queryEndpoint( endpoint, sparqlQueryString );
		 while (results.hasNext())
    	 {
    		 QuerySolution soln = results.nextSolution();
             dataSetNames.add( soln.get("?name").toString() );
    	 }

	 }
	 
	 public void testEndpoint() { Endpoints.testEndpoint( endpoint ); }
	
}