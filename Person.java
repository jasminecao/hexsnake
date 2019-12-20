

public class Person implements Comparable<Person> {
	private String name;
	private int result;
	
	public Person(String name, int x) {
		this.name = name;
		result = x;
	}
	
	//no games played yet
	public Person() {
		this.name = "EMPTY";
		result = 0;
	}

	public String getName() {
		return name;
	}

	public int getResult() {
		return result;
	}
	
	@Override
	public int compareTo(Person p) {
		if (this.getResult() > p.getResult()) return -1; 
		if (this.getResult() < p.getResult()) return 1; 
		else return 0;
	} 
}
