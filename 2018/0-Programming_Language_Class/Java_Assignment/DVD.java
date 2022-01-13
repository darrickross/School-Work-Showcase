
public class DVD extends LibraryItem {
	private String director;
	private String screenWriter;
	private double runTime;
	private double currWatchTime;
	
	public DVD(String t, String dir, String sc, String cN, int y, double r) {
		super(t, y, cN);
		director = dir;
		screenWriter = sc;
		runTime = r;
		currWatchTime = 0;
	}
	
	public boolean watch(double time) {
		if ((currWatchTime + time) <= runTime) {
			currWatchTime += time;
			return true;
		}
		return false;
	}
	
	public String toCollectionString() {
		String dvdInfo = super.toCollectionString()
				+ " directed by " + director
				+ " and written by " + screenWriter
				+ ". Currently seen " + currWatchTime
				+ " of " + runTime + " minutes.";
		return dvdInfo;
	}

}
