package models;

import java.io.Serializable;
import java.util.ArrayList;

public class Medicine implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private String name;
	
	public static ArrayList<Medicine> medicines = new ArrayList<Medicine>();
	
	public Medicine(String name){
		this.name = name;
		if(!medicines.contains(this)){
			medicines.add(this);
		}
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<Disease> getAllDiseases(){
		ArrayList<Disease> allDi = new ArrayList<Disease>();
		for(Disease d : Disease.diseases)
			for(Medicine m : d.medicines)
				if(m.equals(this)){
					allDi.add(d);
					break;
				}
		return allDi;
	}
	public ArrayList<Patient> getAllPatients(){
		ArrayList<Patient> allP = new ArrayList<Patient>();
		for(Patient p : Patient.patients)
			for(Disease d : p.diseases)
				if(getAllDiseases().contains(d)){
					allP.add(p);
					break;
				}
		return allP;
	}
	public static ArrayList<Medicine> getAllMedicines(){
		return Medicine.medicines;
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
		Medicine other = (Medicine) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	/**
	 * tamame esm ha ra marmigardanad
	 * @return
	 */
	public static ArrayList<String> getAllNames(){
		ArrayList<String> names = new ArrayList<String>();
		for(Medicine m:Medicine.getAllMedicines()){
			names.add(m.getName());
		}
		return names;
	}
}
