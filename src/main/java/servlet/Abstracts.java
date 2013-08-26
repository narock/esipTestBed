package servlet;

import java.io.IOException;
import java.util.ArrayList;
import data.Agu;
import data.R2R;
import data.Person;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sparql.query.*;

public class Abstracts extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
    public void doGet (HttpServletRequest request, HttpServletResponse response) throws 
      ServletException, IOException {
    	
    	// get GET variables
    	String login = request.getParameter("Login");
    	String firstName = request.getParameter("FirstName"); 
    	String lastName = request.getParameter("LastName");
     	
     	// get agu data for this person
    	AguPresentations agu = new AguPresentations ();
    	String name = lastName.trim() + ", " + firstName.trim().substring(0, 1);
    	agu.submitQuery( name );
    	ArrayList <Agu> aguData = agu.getAguData();
    	
    	// submit r2r, echo, and gcmd sparql queries
    	ECHO echo = new ECHO ();
    	GCMD gcmd = new GCMD ();
    	R2rCruiseID r2r = new R2rCruiseID ();    	
    	echo.submitQuery();
    	gcmd.submitQuery();
    	r2r.submitQuery();
    	ArrayList <String> echoData = echo.getDataSetNames();
    	ArrayList <String> gcmdData = gcmd.getDataSetNames();
    	R2R r2rObject = r2r.getR2rData();
    	ArrayList <String> r2rData = r2rObject.mergeIDandTitle();
    	
    	// submit  nsf and esip queries
    	Esip esip = new Esip ();
    	Nsf nsf = new Nsf ();
    	esip.submitQuery(lastName.trim());
    	nsf.submitQuery(lastName.trim());
    	ArrayList <Person> esipData = esip.getEsipNames();
    	ArrayList <Person> nsfData = nsf.getNsfNames();
    	
    	// add empty values to the echo, gcmd, and r2r lists
    	// this is so that we can have a null value displayed
    	// by default on the web form rather than the first SPARQL result returned
    	echoData.add( 0, "Not Used" );
    	gcmdData.add( 0, "Not Used" );
    	r2rData.add( 0, "Not Used" );
    	
    	// set the values for the jsp page
    	request.setAttribute( "login", login );
    	request.setAttribute( "firstName", firstName );
    	request.setAttribute( "lastName", lastName );
    	request.setAttribute( "aguData", aguData );
    	request.setAttribute( "r2rData", r2rData );
    	request.setAttribute( "echoData", echoData );
    	request.setAttribute( "gcmdData", gcmdData );
    	request.setAttribute( "nsfData", nsfData );
    	request.setAttribute( "esipData", esipData );
    	
    	// send the request to the jsp page
        request.getRequestDispatcher("/WEB-INF/abstracts.jsp").forward(request, response);
    	
    }
    
  }
