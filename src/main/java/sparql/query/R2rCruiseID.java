package sparql.query;

import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.RDFNode;

import java.util.ArrayList;
import sparql.*;
import data.R2R;

public class R2rCruiseID
{
	
	 private Format format = new Format ();
 	 private String endpoint = Endpoints.r2r;
	 private R2R r2rData = new R2R ();
	 
	 public R2R getR2rData () { return r2rData; }
	 
     public static void main(String[] args) {

    	 R2rCruiseID r2r = new R2rCruiseID ();
    	 r2r.testEndpoint();
    	 r2r.submitQuery();
    	 R2R data = r2r.getR2rData();
    	 ArrayList <String> cruiseIDs = data.getCruiseIDs();
    	 ArrayList <String> cruiseTitles = data.getCruiseTitles();
    	 ArrayList <String> vesselNames = data.getVesselNames();
    	 for ( int i=0; i<cruiseIDs.size(); i++ )
    	 {
    		 System.out.println( 
    		      "(" + vesselNames.get(i) + ") " + cruiseIDs.get(i) + " | " + cruiseTitles.get(i) );
    	 }
	 
     }

	 public void submitQuery() {
	        
		 String sparqlQueryString = 
				    " PREFIX r2r: <http://linked.rvdata.us/vocab/resource/class/> " + 
				    " PREFIX dcterms: <http://purl.org/dc/terms/> " +
	        		" SELECT ?s ?title ?v WHERE { " +
	        		"   ?s ?p r2r:Cruise . " +
	        		"   ?s dcterms:title ?title . " +
	        		"   ?s r2r:VesselName ?v " +
	        		" } " +
	        		" ORDER BY ?v ";
		 ResultSet results = Endpoints.queryEndpoint( endpoint, sparqlQueryString );
		 while ( results.hasNext() ) 
		 {
			 QuerySolution soln = results.nextSolution();
             RDFNode cID = soln.get("?s");
             RDFNode cTitle = soln.get("?title");
             RDFNode vessel = soln.get("?v");
             r2rData.addCruiseID( format.getR2rCruiseId(cID.toString()) );
             r2rData.addCruiseTitle( cTitle.toString() );
             r2rData.addVesselName( vessel.toString() );
		 }
		 
	 }
	 
	 public void testEndpoint() { Endpoints.testEndpoint( endpoint ); }
	
}