package gui.ret;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import gui.DoctorGUI;
import models.Doctor;

public class RetDoctorGUI extends JFrame {// yek safheye gerafiki baraye nashan dadane ettelaate doctor

	private static final long serialVersionUID = 1L;
	int indexOfDoctorToChange;
	JButton bBack;
	JLabel jFN, jBD, jGen, JPatients;
	JList<String> lPat;
	/**
	 * index e doctor e entekhab shode az safheye ghable ra migirad
	 * @param indexOfDoctorToChange
	 */
	public RetDoctorGUI(int indexOfDoctorToChange) {
		super("show a doctor");
		setVisible(true);
		this.indexOfDoctorToChange = indexOfDoctorToChange;
		setSize(300, 400);
		setLayout(new BorderLayout());
		add(new JLabel("show information of doctor"), BorderLayout.NORTH);
		
		JPanel pCenter = new JPanel();
		pCenter.setLayout(new GridLayout(4, 1, 0, 0));
		add(pCenter, BorderLayout.CENTER);
		
		jFN = new JLabel("doctor's full name:      " + Doctor.doctors.get(indexOfDoctorToChange).getFullName());
		jBD = new JLabel("birth date:                   " + Doctor.doctors.get(indexOfDoctorToChange).getBirthDate());
		jGen = new JLabel("gender:                      " + Doctor.doctors.get(indexOfDoctorToChange).getGender());
		String[] jPatients = new String[Doctor.doctors.get(indexOfDoctorToChange).patients.size()];
		for (int i = 0; i < jPatients.length; i++) {
			jPatients[i] = Doctor.doctors.get(indexOfDoctorToChange).patients.get(i).getFullName() + "(" +
									Doctor.doctors.get(indexOfDoctorToChange).patients.get(i).id + ")";
		}
		lPat = new JList<String>(jPatients);
		JPatients = new JLabel("patients:");
		
		pCenter.add(jFN);
		pCenter.add(jBD);
		pCenter.add(jGen);
		
		JPanel pPat = new JPanel();
		pPat.setLayout(new GridLayout(1, 2));
		pPat.add(JPatients);
		pPat.add(new JScrollPane(lPat));
		pCenter.add(pPat);
		
		bBack = new JButton("Back");
		add(bBack, BorderLayout.SOUTH);
		bBack.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
				new DoctorGUI();
			}
		});
		
	}
	

}
