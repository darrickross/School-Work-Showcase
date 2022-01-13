
public class Cone {
	private double radius;
	private double height;
	
	
	public Cone (double r, double h) {
		radius = r;
		height = h;
	}
	
	public double getVolume() {
		return ((double)(Math.PI * (radius * radius)* height) / 3);
	}
	
	public double getSurfaceArea() {
		return ((Math.PI * radius * radius) + 
				(Math.PI * radius * 
						(Math.sqrt(
								(radius * radius) + (height * height))
						)
				));
	}
}
