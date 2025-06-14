package java_files;

public class Product implements Comparable<Product> {
	//Attributes
	private String name;
	private String country;
	private String category;
	private double price;

	// Constructor
	public Product(String name, String country, String category, double price) {
		this.name = name;
		this.country = country;
		this.category = category;
		this.price = price;
	}

	// Getters
	public String getName() {
		return name;
	}

	public String getCountry() {
		return country;
	}

	public String getCategory() {
		return category;
	}

	public double getPrice() {
		return price;
	}

	// Setters
	public void setName(String name) {
		this.name = name;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public void setPrice(double price) {
		this.price = price;
	}
	
	

	@Override
	//toString
	public String toString() {
		return name + "," + country + "," + category + "," + price;
	}

	@Override
	//compareTo
	public int compareTo(Product other) {
		return this.name.compareToIgnoreCase(other.name);
	}
}
