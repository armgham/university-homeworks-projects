package test;
public class Person implements Comparable<Person> {
	private String name;
	private Float finalScore = new Float(0);
	
	public Person(){}
	public Person(String name){
		this.name = name;
	}
	public Person(String name, float score){
		this(name);
		this.finalScore = score;
	}
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public float getScore() {
		return finalScore;
	}
	public void setScore(float score) {
		this.finalScore = score;
	}
	
	
	
	
	
	
	
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) { //equal feghat zamani ke name mosavi bud
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Person other = (Person) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	@Override //compare bar asase score
	public int compareTo(Person arg0) {
		int x = this.finalScore.compareTo(arg0.finalScore);
		if(this.equals(arg0))
			return 0;
		if(x > 0)
			return -1;
		else if(x < 0)
			return 1;
		else if(x == 0)
			return this.name.compareTo(arg0.getName());
		return 1;
		
	}
	
	
	
	

}
