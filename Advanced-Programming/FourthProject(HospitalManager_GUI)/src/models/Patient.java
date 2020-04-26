package models;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

public class Patient extends Person{

	private static final long serialVersionUID = 1L;
	public long id;
	Doctor doctor;
	ArrayList<Disease> diseases = new ArrayList<Disease>();
	

	public static ArrayList<Patient> patients = new ArrayList<Patient>();
 	
	public Patient(String firstName, String lastName) {
		super(firstName, lastName);
		idGenerator();
		Patient.patients.add(this);
	}
	public Patient(String firstName,String lastName,long id){
		super(firstName, lastName);
		idGenerator(id);
		Patient.patients.add(this);
	}
	public Patient(String firstName,String lastName,String gender,String birthDate,long id){
		super(firstName, lastName, gender, birthDate);
		idGenerator(id);
		Patient.patients.add(this);
	}
	public Doctor getDoctor(){
		return this.doctor;
	}
	public void setDoctor(Doctor doctor){
		this.doctor = doctor;
		this.doctor.addPatient(this);
	}
	public void addDisease(Disease disease){
		if(!this.diseases.contains(disease))
			this.diseases.add(disease);
	}
	public boolean curedDisease(Disease disease){
		return this.diseases.remove(disease);
	}
	public ArrayList<Disease> getAllDiseases(){
		return this.diseases;
	}
	public ArrayList<Medicine> getAllMedicine(){
		ArrayList<Medicine> allMed = new ArrayList<Medicine>();
		for(Disease d : this.diseases)
			allMed.addAll(d.getAllMedicines());
		return allMed;
	}
	public static ArrayList<Patient> getAllPatients(){
		return Patient.patients;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
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
		Patient other = (Patient) obj;
		if (id != other.id)
			return false;
		return true;
	}


	@Override
	public void idGenerator() {
		Random randome = new Random();
		long i = 0;
		do{
			i = randome.nextLong();
		} while(Patient.getAllIds().contains(i) || i == 0);
		this.id = i;
	}

	@Override
	public void idGenerator(long id) {
		Random randome = new Random();
		while(Patient.getAllIds().contains(id) || id == 0){
			id = randome.nextLong();
		}
		this.id = id;
	}
	/**
	 * tamame id ha ra barmigardanad
	 * @return
	 */
	public static HashSet<Long> getAllIds(){
		HashSet<Long> ids = new HashSet<Long>();
		for(Patient p:Patient.getAllPatients()){
			ids.add(p.id);
		}
		return ids;
	}
}
