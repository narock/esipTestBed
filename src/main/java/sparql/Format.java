package sparql;

public class Format 
{
	
	public String removeLanguage ( String s )
	{
		String[] parts = s.split("@");
		return parts[0];
	}
	
	public String removeDataType ( String s ) 
	{
		String[] parts = s.split("\\^");
		return parts[0];
	}
	
	public String getR2rCruiseId ( String s )
	{
		String[] parts = s.split("/");
		return parts[ parts.length-1 ];
	}
	
	public String getYear ( String s ) 
	{
		String[] parts = s.split("-");
		return parts[0];
	}
	
}