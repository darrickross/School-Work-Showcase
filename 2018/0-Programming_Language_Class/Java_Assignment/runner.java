
public class runner {

	public static void main(String[] args) {
		//Given Example Test
		Library mineL = new Library("Personal");

		Book hpss =  new Book("Harry Potter and the Sorcerer's Stone", "JK Rowling", "PZ7.R79835 Har 1998", 1998, 309);
		Book hpcs =  new Book("Harry Potter and the Chamber of Secrets", "JK Rowling", "PZ7.R79835 Haj 1999", 1999, 500);
		DVD hp_dvd = new DVD("Harry Potter and the the Sorcerer's Stone", "Christopher Columbus", "Steve Kloves", "CGD 2281-2289" , 2001, 152);

		mineL.addItem(hpss);
		mineL.addItem(hpcs);
		mineL.printCollection();

		mineL.addItem(hp_dvd);
		mineL.printCollection();

		Book book1 = (Book)mineL.getItem("PZ7.R79835 Har 1998");
		book1.readPages(12);
		mineL.printCollection();
		
		
		//Other Test not included in example
		System.out.println(book1.readPages(13));
		System.out.println(book1.toCollectionString());
		System.out.println(book1.readPages(1000));
		System.out.println(book1.toCollectionString());
		
		Book book2 = (Book)mineL.getItem("PZ7.R79835 Haj 1999");
		System.out.println(book2.readPages(13));
		System.out.println(book2.toCollectionString());
		System.out.println(book2.readPages(1000));
		System.out.println(book2.toCollectionString());
		
		DVD dvd1 = (DVD)mineL.getItem("CGD 2281-2289");
		System.out.println(dvd1.watch(13.5));
		System.out.println(dvd1.toCollectionString());
		System.out.println(dvd1.watch(1000));
		System.out.println(dvd1.toCollectionString());
		
		System.out.println("\n\n");
		Cone test34 = new Cone(3,4);
		System.out.println(test34.getVolume());
		System.out.println(test34.getSurfaceArea());
	}
}
