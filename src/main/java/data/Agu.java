package data;

public class Agu {
	
	private String abstrct;
	private String session;
	private String uri;
	private String meeting;
	private String year;
	
	public String getAbstract () { return abstrct; }
	public String getSession () { return session; }
	public String getUri() { return uri; }
	public String getYear() { return year; }
	public String getMeeting() { return meeting; }
	
	public void setAbstract ( String a ) { abstrct = a; }
	public void setSession ( String s ) { session = s; }
	public void setUri ( String u ) { uri = u; }
	public void setYear ( String y ) { year = y; }
	public void setMeeting ( String m ) { meeting = m; }
	
}