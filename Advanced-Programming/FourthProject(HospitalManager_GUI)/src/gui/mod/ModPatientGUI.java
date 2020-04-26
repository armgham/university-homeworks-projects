package gui.mod;
import java.awt.BorderLayout;
import java.awt.Checkbox;
import java.awt.CheckboxGroup;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import gui.PatientGUI;
import models.Disease;
import models.Doctor;
import models.Patient;

public class ModPatientGUI extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int indexOfPatientToChange;
	JButton bBack, bOK;
	JLabel jFN, jLN, jBD, jGen, jSetDo, jAddDi, jCureDi;
	JTextField tFN, tLN, tBD;
	JList<String> lAddDi, lSetD, lCureDi;
	CheckboxGroup cBGender;
	Checkbox cBMale, cBFemale;
	public ModPatientGUI(int indexOfPatientToChange) {
		super("modify a patient");
		setVisible(true);
		this.indexOfPatientToChange = indexOfPatientToChange;
		setSize(700, 500);
		setLayout(new BorderLayout());
		add(new JLabel("modify information of doctor"), BorderLayout.NORTH);
		
		JPanel pCenter = new JPanel();
		pCenter.setLayout(new GridLayout(7, 2, 0, 0));
		add(pCenter, BorderLayout.CENTER);
		
		jFN = new JLabel("change first name:");
		jLN = new JLabel("change last name:");
		jBD = new JLabel("set(or change) birth date:");
		jGen = new JLabel("set(or change) gender:");
		jSetDo = new JLabel("set doctor"); ///
		jAddDi = new JLabel("add disease"); ///
		jCureDi = new JLabel("cure disease"); ///
		
		tFN = new JTextField();
		tLN = new JTextField();
		tBD = new JTextField();
		
		String[] fullnameAllDoc = new String[Doctor.doctors.size()];
		String[] nameAllDis = new String[Disease.diseases.size()];
		String[] namePatDis = new String[Patient.patients.get(indexOfPatientToChange).getAllDiseases().size()];
		
		for(int i = 0; i < Doctor.doctors.size(); i++)
			fullnameAllDoc[i] = Doctor.doctors.get(i).getFullName() + "(" +
												Doctor.doctors.get(i).id + ")";
		for(int i = 0; i < Disease.diseases.size(); i++)
			nameAllDis[i] = Disease.diseases.get(i).getName();
		for(int i = 0; i < Patient.patients.get(indexOfPatientToChange).getAllDiseases().size(); i++)
			namePatDis[i] = Patient.patients.get(indexOfPatientToChange).getAllDiseases().get(i).getName();
		
		lSetD = new JList<String>(fullnameAllDoc); 
		lSetD.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		lAddDi = new JList<String>(nameAllDis);
		lAddDi.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		lCureDi = new JList<String>(namePatDis);
		lCureDi.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
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
		
		pCenter.add(jSetDo);
		pCenter.add((Doctor.doctors.size() != 0)?new JScrollPane(lSetD):new JLabel("there is no doctor"));
		
		pCenter.add(jAddDi);
		pCenter.add((Disease.diseases.size() != 0)?new JScrollPane(lAddDi):new JLabel("there is no Disease"));
		
		pCenter.add(jCureDi);
		pCenter.add((Patient.patients.get(indexOfPatientToChange).getAllDiseases().size() == 0)?
				new JLabel("this patient has no disease to cure"):new JScrollPane(lCureDi));
		
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
					Patient.patients.get(indexOfPatientToChange).setFirstName(firstName);
				if(!lastName.equals(""))
					Patient.patients.get(indexOfPatientToChange).setLastName(lastName);
				if(!gender.equals(""))
					Patient.patients.get(indexOfPatientToChange).setGender(gender);
				if(!birthDate.equals(""))
					Patient.patients.get(indexOfPatientToChange).setBirthDate(birthDate);
				if(!lSetD.isSelectionEmpty())
					Patient.patients.get(indexOfPatientToChange).setDoctor(Doctor.doctors.get(lSetD.getLeadSelectionIndex()));
				if(!lAddDi.isSelectionEmpty())
					Patient.patients.get(indexOfPatientToChange).addDisease(Disease.diseases.get(lAddDi.getLeadSelectionIndex()));
				if(!lCureDi.isSelectionEmpty())
					Patient.patients.get(indexOfPatientToChange).curedDisease(Disease.diseases.get(lCureDi.getLeadSelectionIndex()));
				JOptionPane.showMessageDialog(null, "done!");
			}
		});
		
		bBack.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
				new PatientGUI();
			}
		});
	}
	
}
