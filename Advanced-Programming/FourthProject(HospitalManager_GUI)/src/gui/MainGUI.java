package gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import inputoutput.InOut;
import models.Disease;
import models.Doctor;
import models.Medicine;
import models.Patient;

public class MainGUI extends JFrame {
	
	private static final long serialVersionUID = 1L;
	JButton bPat, bDoc, bMed, bDis, bExit;
	JLabel jWelcome, jPat, jDoc, jMed, jDis;
	/**
	 *  yek safheye gerafiki baray disease ke 4 button darad
	 */
	public MainGUI(){
		super("Hospital Manager");
		setSize(700, 500);
		setVisible(true);
		setLayout(new BorderLayout());
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		jWelcome = new JLabel("Welcome to hospital manager!");
		add(jWelcome, BorderLayout.NORTH);
		
		JPanel pCenter = new JPanel();
		pCenter.setLayout(new GridLayout(4, 2, 5, 40));
		add(pCenter, BorderLayout.CENTER);
		
		jDoc = new JLabel("add or remove or retrieve doctors");
		jPat = new JLabel("add or remove or retrieve patients");
		jMed = new JLabel("add or remove or retrieve medicines");
		jDis = new JLabel("add or remove or retrieve diseases");
		
		bDoc = new JButton("Doctors");
		bPat = new JButton("Patients");
		bMed = new JButton("Medicines");
		bDis = new JButton("Diseases");
		
		pCenter.add(jDoc);
		pCenter.add(bDoc);
		
		pCenter.add(jPat);
		pCenter.add(bPat);
		
		pCenter.add(jDis);
		pCenter.add(bDis);
		
		pCenter.add(jMed);
		pCenter.add(bMed);
		
		pCenter.setBorder(new EmptyBorder(20, 0, 20, 20));
		
		JPanel pSouth = new JPanel();
		pSouth.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));
		bExit = new JButton("save and exit");
		pSouth.add(bExit);
		add(pSouth, BorderLayout.SOUTH);
		bExit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
				// neveshtan ruye file
				InOut.outputObject(Doctor.getAllDoctors(), "doctors.ser");
				InOut.outputObject(Patient.getAllPatients(), "patients.ser");
				InOut.outputObject(Disease.getAllDiseases(), "diseases.ser");
				InOut.outputObject(Medicine.getAllMedicines(), "medicines.ser");
				System.exit(0);
			}
		});
		
		bDoc.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				new DoctorGUI();
				setVisible(false);
			}
		});
		bPat.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				new PatientGUI();
				setVisible(false);
			}
		});
		
		bDis.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				new DiseaseGUI();
				setVisible(false);
			}
		});
		bMed.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				new MedicineGUI();
				setVisible(false);
			}
		});
		
	}
	
	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		// khandan az file
		Doctor.doctors = (ArrayList<Doctor>) InOut.inputObject("doctors.ser");
		Patient.patients = (ArrayList<Patient>) InOut.inputObject("patients.ser");
		Disease.diseases = (ArrayList<Disease>) InOut.inputObject("diseases.ser");
		Medicine.medicines = (ArrayList<Medicine>) InOut.inputObject("medicines.ser");
		new MainGUI();
	}
	
}
