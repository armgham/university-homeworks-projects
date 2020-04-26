package gui.mod;

import java.awt.BorderLayout;
import java.awt.Checkbox;
import java.awt.CheckboxGroup;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import gui.DoctorGUI;
import models.Doctor;
import models.Patient;

public class ModDoctorGUI extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int indexOfDoctorToChange;
	JButton bBack, bOK;
	JLabel jFN, jLN, jBD, jGen, jAddP, jRemP;
	JTextField tFN, tLN, tBD;
	JList<String> lAddP, lRemP;
	CheckboxGroup cBGender;
	Checkbox cBMale, cBFemale;
	
	public ModDoctorGUI(int indexOfDoctorToChange) {
		super("modify a doctor");
		setVisible(true);
		this.indexOfDoctorToChange = indexOfDoctorToChange;
		setSize(700, 500);
		setLayout(new BorderLayout());
		add(new JLabel("modify information of doctor"), BorderLayout.NORTH);
		
		JPanel pCenter = new JPanel();
		pCenter.setLayout(new GridLayout(6, 2, 0, 0));
		add(pCenter, BorderLayout.CENTER);
		
		jFN = new JLabel("change first name:");
		jLN = new JLabel("change last name:");
		jBD = new JLabel("set(or change) birth date:");
		jGen = new JLabel("set(or change) gender:");
		jAddP = new JLabel("add patient(s) (to multiple choice use Ctrl)");
		jRemP = new JLabel("remove patient(s) (to multiple choice use Ctrl)");
		
		tFN = new JTextField();
		tLN = new JTextField();
		tBD = new JTextField();
		
		String[] fullnameAllPat = new String[Patient.patients.size()];
		String[] fullnameDocPat = new String[Doctor.doctors.get(indexOfDoctorToChange).patients.size()];
		
		for(int i = 0; i < Doctor.doctors.get(indexOfDoctorToChange).patients.size(); i++)
			fullnameDocPat[i] = Doctor.doctors.get(indexOfDoctorToChange).patients.get(i).getFullName() + "(" + 
														Doctor.doctors.get(indexOfDoctorToChange).patients.get(i).id + ")";
		lRemP = new JList<String>(fullnameDocPat);
		lRemP.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		for(int i = 0; i < Patient.patients.size(); i++)
			fullnameAllPat[i] = Patient.patients.get(i).getFullName() + "(" + Patient.patients.get(i).id + ")";
		lAddP = new JList<String>(fullnameAllPat);
		
		lAddP.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		
		pCenter.add(jFN);
		pCenter.add(tFN);
		
		pCenter.add(jLN);
		pCenter.add(tLN);
		
		pCenter.add(jBD);
		pCenter.add(tBD);
		
		cBGender = new CheckboxGroup();
		cBMale = new Checkbox("Male", cBGender, false);
		cBFemale = new Checkbox("Female", cBGender, false);
		JPanel pCB = new JPanel();
		pCB.add(cBMale);
		pCB.add(cBFemale);
		
		
		pCenter.add(jGen);
		pCenter.add(pCB);
		
		pCenter.add(jAddP);
		pCenter.add((Patient.patients.size() != 0)?new JScrollPane(lAddP):new JLabel("there is no patient"));
		
		pCenter.add(jRemP);
		pCenter.add((Doctor.doctors.get(indexOfDoctorToChange).patients.size() == 0)?
				new JLabel("this doctor has no patient to remove"):new JScrollPane(lRemP));
		
		JPanel pSouth = new JPanel();
		pSouth.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		bBack = new JButton("Back");
		bOK = new JButton("OK");
		pSouth.add(bBack);
		pSouth.add(bOK);
		add(pSouth, BorderLayout.SOUTH);
		
		bOK.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				String firstName = null, lastName = null, gender = null, birthDate = null;
				firstName = tFN.getText();
				lastName = tLN.getText();
				gender = (cBMale.getState())?"male":(cBFemale.getState()?"female":" ");
				birthDate = tBD.getText();
				
				if(!firstName.equals(""))
					Doctor.doctors.get(indexOfDoctorToChange).setFirstName(firstName);
				if(!lastName.equals(""))
					Doctor.doctors.get(indexOfDoctorToChange).setLastName(lastName);
				if(!gender.equals(""))
					Doctor.doctors.get(indexOfDoctorToChange).setGender(gender);
				if(!birthDate.equals(""))
					Doctor.doctors.get(indexOfDoctorToChange).setBirthDate(birthDate);
				int[] selectedAP = lAddP.getSelectedIndices();
				int[] selectedRP =lRemP.getSelectedIndices();
				
				if(selectedAP.length != 1 && selectedAP.length != 0){
					ArrayList<Patient> pToAdd = new ArrayList<Patient>();
					for(int i = 0; i < selectedAP.length; i++)
						pToAdd.add(Patient.patients.get(selectedAP[i]));
					Doctor.doctors.get(indexOfDoctorToChange).addAllPatients(pToAdd);
				}
				else if(selectedAP.length != 0)
					Doctor.doctors.get(indexOfDoctorToChange).addPatient(Patient.patients.get(selectedAP[0]));
				
				if(selectedRP.length != 1 && selectedRP.length != 0){
					ArrayList<Patient> pToRem = new ArrayList<Patient>();
					for (int i = 0; i < selectedRP.length; i++)
						pToRem.add(Patient.patients.get(selectedRP[i]));
					Doctor.doctors.get(indexOfDoctorToChange).removeAllPatients(pToRem);
				}
				else if(selectedRP.length != 0)
					Doctor.doctors.get(indexOfDoctorToChange).removePatient(Patient.patients.get(selectedRP[0]));
				
				JOptionPane.showMessageDialog(null, "done!");
				}
		});
		
		bBack.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
				new DoctorGUI();
			}
		});
		
	}
	
}
