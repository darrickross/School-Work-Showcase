
public class Book extends LibraryItem {
	private String author;
	private int maxPage;
	private int currPage;
	
	public Book(String t, String a, String cN, int y, int p) {
		super(t, y, cN);
		author = a;
		maxPage = p;
		currPage = 1;
	}
	
	public boolean readPages(int readNumPages) {
		if ((currPage + readNumPages) <= maxPage) {
			currPage += readNumPages;
			return true;
		}
		return false;
	}
	

	public String toCollectionString() {
		String bookInfo = super.toCollectionString() 
				+ " written by " + author 
				+ ". Currently on page " + currPage 
				+ " of " + maxPage;
		return bookInfo;
	}
}
