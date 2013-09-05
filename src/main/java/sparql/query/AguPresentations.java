package sparql.query;

import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.RDFNode;
import sparql.*;
import java.util.ArrayList;
import data.Agu;

public class AguPresentations 
{
	
	 private Format format = new Format ();
 	 private String endpoint = Endpoints.agu;
	 private ArrayList <Agu> aguData = new ArrayList <Agu> ();
	 
	 public ArrayList <Agu> getAguData () { return aguData; }
	 
	 private boolean hasValue ( String abs ) 
	 {
		 boolean result = false;
		 for ( int i=0; i<aguData.size(); i++ ) 
		 {
			Agu agu = aguData.get(i);
			if ( agu.getAbstract().equals(abs) ) {
				result = true;
				break;
			}
		 }
		 return result;
	 }
	 
     public static void main(String[] args) {

    	 AguPresentations agu = new AguPresentations ();
    	 agu.testEndpoint();
    	 agu.submitQuery( args[0] );
    	 ArrayList <Agu> aguResults = agu.getAguData();
    	 for ( int i=0; i<aguResults.size(); i++ )
    	 {
    		 Agu a = aguResults.get(i);
    		 System.out.println( a.getAbstract() + " " + a.getSession() + " " + a.getMeeting() + " " + a.getYear() );
    	 }
	 
     }

	 public void submitQuery( String name ) {
	        
		 String sparqlQueryString = 
				    " prefix dc:      <http://purl.org/dc/terms/>" +
				 	" prefix foaf:    <http://xmlns.com/foaf/0.1/>" +
				    " prefix tw:      <http://tw.rpi.edu/schema/>" +
				    " prefix swrc:    <http://swrc.ontoware.org/ontology#>" +
				 	" prefix swc:     <http://data.semanticweb.org/ns/swc/ontology#>" +
				 	" select distinct ?abstract ?name ?stitle ?atitle ?meeting ?year where {" +
				    " ?abstract swc:relatedToEvent ?session ." +
				    " ?abstract dc:title ?atitle ." +
				    " ?session swrc:eventTitle ?stitle ." +
				    " ?session swc:isSubEventOf ?m ." +
				 	" ?m swrc:eventTitle ?meeting ." +
				 	" ?m swrc:year ?year ." +
				    " ?abstract tw:hasAgentWithRole ?role ." +
				 	" ?person tw:hasRole ?role ." +
				  	" ?person foaf:name ?name ." +
				 	//" FILTER regex(?name, \"" + name + "\")" +
				  	" FILTER (str(?name) = \"" + name + "\")" +
	 				" } ORDER BY DESC(?year)";
		 		 
		 ResultSet results = Endpoints.queryEndpoint( endpoint, sparqlQueryString );
		 while (results.hasNext())
    	 {

			 QuerySolution soln = results.nextSolution();
    		 RDFNode abstractTitle = soln.get("?atitle");
    		 RDFNode sessionTitle = soln.get("?stitle");
    		 RDFNode abstractUri = soln.get("?abstract");
    		 RDFNode meeting = soln.get("?meeting");
    		 RDFNode year = soln.get("?year");
    		 
    		 Agu agu = new Agu ();
    		 agu.setSession( format.removeLanguage( sessionTitle.toString() ) );
    		 agu.setAbstract( format.removeLanguage( abstractTitle.toString() ) ); 
    		 agu.setMeeting( format.removeLanguage( meeting.toString() ) );
    		 agu.setYear( format.getYear(format.removeDataType( year.toString() )) );
    	     agu.setUri( abstractUri.toString() );
    		 
    		 // the current sparql query returns multiple years because ?year includes
             // month and day as well and AGU sessions span multiple days. since we
             // are only interested in the year portion we use the following if statement
             // to check if we've already seen this abstract.
             if ( !hasValue(format.removeLanguage( abstractTitle.toString() )) ) { aguData.add(agu); }
    		     		 
    	 }
		 
	 }
	 
	 public void testEndpoint() { Endpoints.testEndpoint( endpoint ); }
	
}