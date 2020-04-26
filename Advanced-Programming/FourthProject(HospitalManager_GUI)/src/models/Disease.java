package models;

import java.io.Serializable;
import java.util.ArrayList;

public class Disease implements Serializable {

	private static final long serialVersionUID = 1L;
	public ArrayList<String> symptoms = new ArrayList<String>();
	public ArrayList<Medicine> medicines = new ArrayList<Medicine>();
	private String name;
	
	public static ArrayList<Disease> diseases = new ArrayList<Disease>();
	
	public Disease(String name) {
		this.name = name;
		if(!diseases.contains(this))
			diseases.add(this);
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public ArrayList<Patient> getAllInfected(){
		ArrayList<Patient> allInf = new ArrayList<Patient>();
		for(Patient p : Patient.patients)
			for(Disease d : p.diseases)
				if(d.equals(this)){
					allInf.add(p);
					break;
				}
		return allInf;
	}
	public void addSymptom(String symptom){
		if(!this.symptoms.contains(symptom))
			this.symptoms.add(symptom);
	}
	public void addAllSymptoms(ArrayList<String> symptoms){
		for(int i = 0; i < symptoms.size(); i++)
			this.addSymptom(symptoms.get(i));
	}
	public boolean removeSymptom(String symptom){
		return this.symptoms.remove(symptom);
	}
	public void removeAllSymptoms(ArrayList<String> symptoms){
		this.symptoms.removeAll(symptoms);
	}
	public ArrayList<String> getAllSymptoms(){
		return this.symptoms;
	}
	public void addMedicine(Medicine medicine){
		if(!this.medicines.contains(medicine))
			this.medicines.add(medicine);
	}
	public void addAllMedicines(ArrayList<Medicine> medicines){
		for (int i = 0; i < medicines.size(); i++) {
			addMedicine(medicines.get(i));
		}
	}
	public boolean removeMedicine(Medicine medicine){
		return this.medicines.remove(medicine);
	}
	public void removeAllMedicines(ArrayList<Medicine> medicines){
		this.medicines.removeAll(medicines);
	}
	public ArrayList<Medicine> getAllMedicines(){
		return this.medicines;
	}
	public static ArrayList<Disease> getAllDiseases(){
		return Disease.diseases;
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
		Disease other = (Disease) obj;
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
		for(Disease di:Disease.getAllDiseases()){
			names.add(di.getName());
		}
		return names;
	}
	
}
