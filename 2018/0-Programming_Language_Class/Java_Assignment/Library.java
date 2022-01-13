import java.util.ArrayList;

public class Library {
	private String libraryName;
	private ArrayList<LibraryItem> invList = new ArrayList<LibraryItem>();

	public Library(String n) {
		libraryName = n;
	}
	
	public void addItem(LibraryItem item) {
		invList.add(item);
	}
	
	public LibraryItem getItem(String callNumber) {
		for (LibraryItem item: invList) {
			if (item.getCallNo().equals(callNumber)) {
				return item;
			}
		}
		return null;
	}
	
	public void printCollection() {
		System.out.println("The " + libraryName + " Library Catalog");
		for (LibraryItem item: invList) {
			System.out.println(item.toCollectionString());
		}
		System.out.println("\n\n");
	}
}
