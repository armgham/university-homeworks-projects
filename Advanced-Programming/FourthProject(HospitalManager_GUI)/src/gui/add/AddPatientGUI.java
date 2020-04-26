package gui.add;

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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import gui.PatientGUI;
import models.Patient;

public class AddPatientGUI extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	PatientGUI pG;
	JLabel jFName, jLName, jBDate, jGender, jID;
	JTextField tFName, tLName, tBDate, tID;
	CheckboxGroup cBGender;
	Checkbox cBMale, cBFemale;
	JButton bBack, bOK;
	/**
	 * safheye ghabl ra migirad ta setvisible false konad
	 * @param pG
	 */
	public AddPatientGUI(PatientGUI pG) {// yek safhe baraye gereftane vorudi
		super("add new patient");
		this.pG = pG;
		setSize(320, 500);
		setLayout(new BorderLayout());
		
		add(new JLabel("Please enter information : "), BorderLayout.NORTH);
		
		JPanel pCenter = new JPanel();
		pCenter.setLayout(new GridLayout(5, 2, 0, 60));
		add(pCenter, BorderLayout.CENTER);
		
		jFName = new JLabel("first name : ");
		jLName = new JLabel("last name : ");
		jGender = new JLabel("gender (optional) : ");
		jBDate = new JLabel("birth date (optional) : ");
		jID = new JLabel("id (optional) : ");
		
		tFName = new JTextField();
		tLName = new JTextField();
		tBDate = new JTextField();
		tID = new JTextField();
		
		cBGender = new CheckboxGroup();
		cBMale = new Checkbox("Male", cBGender, false);
		cBFemale = new Checkbox("Female", cBGender, false);
		JPanel pCB = new JPanel();
		pCB.add(cBMale);
		pCB.add(cBFemale);
		
		pCenter.add(jFName);
		pCenter.add(tFName);
		
		pCenter.add(jLName);
		pCenter.add(tLName);
		
		pCenter.add(jBDate);
		pCenter.add(tBDate);
		
		pCenter.add(jGender);
		pCenter.add(pCB);
		
		pCenter.add(jID);
		pCenter.add(tID);
		
		JPanel pSouth = new JPanel();
		pSouth.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		bBack = new JButton("Back");
		bOK = new JButton("OK");
		pSouth.add(bBack);
		pSouth.add(bOK);
		add(pSouth, BorderLayout.SOUTH);
		
		bOK.addActionListener(new ActionListener() { // bar asase vorudi patient misazad
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String firstName = null, lastName = null, gender = null, birthDate = null;
				Long id = new Long(0);
				firstName = tFName.getText();
				lastName = tLName.getText();
				gender = (cBMale.getState())?"male":(cBFemale.getState()?"female":" ");
				birthDate = tBDate.getText();
				
				if(firstName.equals("") || lastName.equals("")){
					JOptionPane.showMessageDialog(null, "please enter first name and last name");
					return;
				}
				
				if(!tID.getText().equals(""))
					try {
						id = Long.parseLong(tID.getText());
						if(Patient.getAllIds().contains(id))
							JOptionPane.showMessageDialog(null, "repetitious id!. we'll generate another id for this doctor");
					} catch (Exception e) {
						JOptionPane.showMessageDialog(null, "wrong id. we'll generate another id for this doctor");
					}
				
				if(id.equals(0))
					new Patient(firstName, lastName);
				else if(gender.equals("") && birthDate.equals(""))
					new Patient(firstName, lastName, id);
				else
					new Patient(firstName, lastName, gender, birthDate, id);
				JOptionPane.showMessageDialog(null, "successfully data added!");
				
			}
		});
		
		bBack.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
				pG.setVisible(true);
			}
		});
	}
	
	
	
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		pG.setVisible(false);
		setVisible(true);
	}

}
