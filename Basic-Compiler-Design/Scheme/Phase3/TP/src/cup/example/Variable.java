package cup.example;

import java.util.ArrayList;

public class Variable {
	private String name;
	private String type = "";
	private ArrayList<Variable> vars = new ArrayList<Variable>();
	
	public Variable(String name) {
		this.name = name;
	}
	
	public Variable(String name, String type) {
		this.name = name;
		this.type = type;
	}
	
	public boolean addVar(String name, String type) {
		if (vars.contains(new Variable(name, type)))
			return false;
		vars.add(new Variable(name, type));
		return true;
		
	}

	public String getName() {
		return name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public ArrayList<Variable> getVars() {
		return vars;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Variable other = (Variable) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	

}
