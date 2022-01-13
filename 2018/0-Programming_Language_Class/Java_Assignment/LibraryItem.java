
public class LibraryItem {
	private String callNumber;
	private String title;
	private int year;
	
	public LibraryItem(String t, int y, String cN) {
		callNumber = cN;
		title = t;
		year = y;
	}
	
	public String toCollectionString() {
		String printInfo = title + "(Call Number: " 
							+ callNumber + "), first published in " + year;
		return printInfo;
	}
	
	public String getCallNo() {
		return callNumber;
	}
}