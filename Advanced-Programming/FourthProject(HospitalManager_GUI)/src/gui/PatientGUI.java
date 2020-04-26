package gui;

import java.awt.BorderLayout;
import java.awt.Choice;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import gui.add.AddPatientGUI;
import gui.mod.ModPatientGUI;
import gui.ret.RetPatientGUI;
import models.Patient;

public class PatientGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	JButton bAdd, bMod, bRet, bBack;
	JLabel jAdd, jMod, jRet;
	/**
	 * yek safheye gerafiki baray patient ke 3 button darad
	 */
	public PatientGUI() {

		super("Patients' management");
		setSize(700, 500);
		setVisible(true);
		setLayout(new BorderLayout());
		
		add(new JLabel("Patients' management"), BorderLayout.NORTH);
		
		JPanel pCenter = new JPanel();
		pCenter.setLayout(new GridLayout(3, 2, 5, 40));
		add(pCenter, BorderLayout.CENTER);
		
		jAdd = new JLabel("add patient");
		jMod = new JLabel("modify patient (add diseases and ...)");
		jRet = new JLabel("retrieve patient");
		
		bAdd = new JButton("add patient");
		bMod = new JButton("modify patient");
		bRet = new JButton("retrieve patient");
		
		pCenter.add(jAdd);
		pCenter.add(bAdd);
		
		pCenter.add(jMod);
		pCenter.add(bMod);
		
		pCenter.add(jRet);
		pCenter.add(bRet);
		
		pCenter.setBorder(new EmptyBorder(20, 0, 20, 20));
		
		JPanel pSouth = new JPanel();
		pSouth.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		bBack = new JButton("Back");
		pSouth.add(bBack);
		add(pSouth, BorderLayout.SOUTH);
		
		bAdd.addActionListener(new AddPatientGUI(this));//vaghti button e add click shod varede kelase add mishavad
		bMod.addActionListener(new ActionListener() {//listi az patient ha ra namayesh midahad va index e patient e entekhab shode ra be kelase mod mibarad
			@Override
			public void actionPerformed(ActionEvent e) {
				JFrame f  = new JFrame("modify");
				f.setSize(600, 100);
				setVisible(false);
				Choice ch = new Choice();
				if(Patient.patients.size() == 0){
					JOptionPane.showMessageDialog(null, "there is no patient! please add patient");
					setVisible(true);
					return;
				}
				else
					for(Patient p : Patient.patients)
						ch.addItem(p.getFullName()+"("+String.valueOf(p.id)+")");
				JLabel l = new JLabel("select a patient to modify:");
				f.setLayout(new BorderLayout());
				f.add(l, BorderLayout.WEST);
				f.add(ch, BorderLayout.EAST);
				f.setVisible(true);
				JButton b = new JButton("OK");
				f.add(b, BorderLayout.SOUTH);
				b.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent arg0) {
						long idOfPatient = Long.parseLong(ch.getSelectedItem().split(".+\\(|\\)")[1]);
						for(int i = 0; i < Patient.patients.size(); i++)
							if(Patient.patients.get(i).id == idOfPatient){
								new ModPatientGUI(i);
								break;
							}
						f.setVisible(false);
					}
				});
			}
		});
		bRet.addActionListener(new ActionListener() {// manande button mod
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JFrame f  = new JFrame("show");
				f.setSize(600, 100);
				setVisible(false);
				Choice ch = new Choice();
				if(Patient.patients.size() == 0){
					JOptionPane.showMessageDialog(null, "there is no patient! please add patient");
					setVisible(true);
					return;
				}
				else
					for(Patient p : Patient.patients)
						ch.addItem(p.getFullName()+"("+String.valueOf(p.id)+")");
				JLabel l = new JLabel("select a patient to show:");
				f.setLayout(new BorderLayout());
				f.add(l, BorderLayout.WEST);
				f.add(ch, BorderLayout.EAST);
				f.setVisible(true);
				JButton b = new JButton("OK");
				f.add(b, BorderLayout.SOUTH);
				b.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent arg0) {
						long idOfPatient = Long.parseLong(ch.getSelectedItem().split(".+\\(|\\)")[1]);
						for(int i = 0; i < Patient.patients.size(); i++)
							if(Patient.patients.get(i).id == idOfPatient){
								new RetPatientGUI(i);
								break;
							}
						f.setVisible(false);
					}
				});
			}
		});
		
		
		bBack.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
				new MainGUI();
			}
		});
	}
	
	public static void main(String[] args) {
		new PatientGUI();
	}
	
}
