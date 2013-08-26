package sparql.query;

import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.RDFNode;
import sparql.*;
import java.util.ArrayList;
import data.Person;

public class Esip
{
	
 	 private String endpoint = Endpoints.esip;
	 private ArrayList <Person> people = new ArrayList <Person> ();
	 
	 public ArrayList <Person> getEsipNames () { return people; }
	 
     public static void main(String[] args) {

    	 Esip esip = new Esip ();
    	 esip.testEndpoint();
    	 esip.submitQuery( args[0] );
    	 ArrayList <Person> results = esip.getEsipNames();
    	 for ( int i=0; i<results.size(); i++ ) { 
    		Person p = results.get(i);
    		System.out.println( p.getName() + " " + p.getUri() );
    	 }
	 
     }

	 public void submitQuery( String name ) {
	        
		 String sparqlQueryString = 
				    " prefix foaf:    <http://xmlns.com/foaf/0.1/> " +	
				    " select distinct ?name ?person " +
				    " from <http://localhost:8890/esip> " +
				    " where { " +
				    "   ?person a foaf:Person . " +
				    "   ?person foaf:name ?name . " +
				    "   FILTER regex(?name, \"" + name + "\") . " +
	 				"}";
		 
		 ResultSet results = Endpoints.queryEndpoint( endpoint, sparqlQueryString );
		 while (results.hasNext())
    	 {
			 Person person = new Person ();
    		 QuerySolution soln = results.nextSolution();
    		 RDFNode n = soln.get("?name");
    		 RDFNode p = soln.get("?person");
    		 person.setName( n.toString() );
    		 person.setUri( p.toString() );
    		 people.add( person );
    	 }
		 
	 }
	 
	 public void testEndpoint() { Endpoints.testEndpoint( endpoint ); }
	
}