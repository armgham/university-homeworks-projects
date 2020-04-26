package models;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

public class Doctor extends Person{

	private static final long serialVersionUID = 1L;
	public long id;
	public ArrayList<Patient> patients = new ArrayList<Patient>();
	
	
	public static ArrayList<Doctor> doctors = new ArrayList<Doctor>();
 	
	public Doctor(String firstName, String lastName){
		super(firstName, lastName);
		idGenerator();
		Doctor.doctors.add(this);
	}
	public Doctor(String firstName,String lastName,long id){
		super(firstName, lastName);
		idGenerator(id);
		Doctor.doctors.add(this);
	}
	public Doctor(String firstName,String lastName,String gender,String birthDate,long id){
		super(firstName, lastName, gender, birthDate);
		idGenerator(id);
		Doctor.doctors.add(this);
	}
	
	public void addPatient(Patient patient){
		if(!this.patients.contains(patient)){
			this.patients.add(patient);
			patient.setDoctor(this);
		}
	}
	
	public void addAllPatients(ArrayList<Patient> patients){
		for(int i = 0; i < patients.size(); i++)
			this.addPatient(patients.get(i));
	}
	
	public boolean removePatient(Patient patient){
		return this.patients.remove(patient);
	}
	
	public void removeAllPatients(ArrayList<Patient> patients){
		this.patients.removeAll(patients);
	}
	
	
	public ArrayList<Patient> getAllPatients(){
		return this.patients;
	}
	public static ArrayList<Doctor> getAllDoctors(){
		return Doctor.doctors;
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
		Doctor other = (Doctor) obj;
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
		} while(Doctor.getAllIds().contains(i) || i == 0);
		this.id = i;
	}

	@Override
	public void idGenerator(long id) {
		Random randome = new Random();
		while(Doctor.getAllIds().contains(id) || id == 0){
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
		for(Doctor d:Doctor.doctors){
			ids.add(d.id);
		}
		return ids;
	}
}
