package sparql;

import com.hp.hpl.jena.query.ARQ;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.sparql.engine.http.QueryExceptionHTTP;

public class Endpoints 
{
	
	// Rolling Deck to Repository public sparql endpoint provided by Bob Arko
	public static String r2r = "http://linked.rvdata.us/sparql";
	
	// DBpedia public sparql endpoint
	public static String dbpedia = "http://DBpedia.org/sparql";
	
	// The Global Change Master Directory (GCMD) holds more than 28,000 
	// Earth science data set and service descriptions, which cover subject 
	// areas within the Earth and environmental sciences. 
	//
	// Brian Wilson of NASA/JPL has provided this LOD interface into the GCMD holdings
	public static String gcmd = "http://sciflo.jpl.nasa.gov:8888/sparql_dif";
	
	// Earth Observing System (EOS) Clearinghouse (ECHO) is a spatial 
	// and temporal metadata registry built by NASA's Earth Science Data 
	// and Information System (ESDIS) that enables the science community
	// to more easily use and exchange NASA's data and services.
	//
	// Brian Wilson of NASA/JPL has provided this LOD interface into the ECHO holdings
	public static String echo = "http://sciflo.jpl.nasa.gov:8888/sparql_echo";
	
	// AGU Rackspace SPARQL Endpoint for abstracts
	public static String agu = "http://198.61.161.98:8890/sparql";
	
	// ESIP/NSF AWS SPARQL Endpoint
	public static String esip = "http://ec2-107-21-230-196.compute-1.amazonaws.com:8890/sparql";
	
	public static ResultSet queryEndpoint ( String endpoint, String sparqlQueryString ) 
    {
    	Query query = QueryFactory.create(sparqlQueryString);
        ARQ.getContext().setTrue(ARQ.useSAX);
    	QueryExecution qexec = QueryExecutionFactory.sparqlService(endpoint, query);
    	ResultSet results = qexec.execSelect();                                       
    	qexec.close();
    	return results; 
    }
	
	public static void testEndpoint ( String endpoint )
	{
		String queryASK = "ASK { }";
    	Query query = QueryFactory.create(queryASK);
    	QueryExecution qe = QueryExecutionFactory.sparqlService(endpoint, query);
        try {
            if (qe.execAsk()) { System.out.println( endpoint + " is UP" ); } 
        }   catch (QueryExceptionHTTP e) {
            System.out.println( endpoint + " is DOWN");
            System.out.println( e );
        } finally { qe.close(); } 
	}
	
}