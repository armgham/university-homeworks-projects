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

import gui.add.AddDoctorGUI;
import gui.mod.ModDoctorGUI;
import gui.ret.RetDoctorGUI;
import models.Doctor;

public class DoctorGUI extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JButton bAdd, bMod, bRet, bBack;
	JLabel jAdd, jMod, jRet;
	
	
	
	
	public DoctorGUI() {
		/**
		 * yek safheye gerafiki baray doctor ke 3 button darad
		 */
		super("Doctors' management");
		setSize(700, 500);
		setVisible(true);
		setLayout(new BorderLayout());
		
		add(new JLabel("Doctors' management"), BorderLayout.NORTH);
		
		JPanel pCenter = new JPanel();
		pCenter.setLayout(new GridLayout(3, 2, 5, 40));
		add(pCenter, BorderLayout.CENTER);
		
		jAdd = new JLabel("add doctor");
		jMod = new JLabel("modify doctor (add patient and ...)");
		jRet = new JLabel("retrieve doctor");
		
		bAdd = new JButton("add Doctor");
		bMod = new JButton("modify doctor");
		bRet = new JButton("retrieve doctor");
		
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
		
		bAdd.addActionListener(new AddDoctorGUI(this)); //vaghti button e add click shod varede kelase add mishavad 
		bMod.addActionListener(new ActionListener() {//listi az doctor ha ra namayesh midahad va index e doctor e entekhab shode ra be kelase mod mibarad
			@Override
			public void actionPerformed(ActionEvent e) {
				JFrame f  = new JFrame("modify");
				f.setSize(600, 100);
				setVisible(false);
				Choice ch = new Choice();
				if(Doctor.doctors.size() == 0){
					JOptionPane.showMessageDialog(null, "there is no doctor! please add doctor");
					setVisible(true);
					return;
				}
				else
					for(Doctor d : Doctor.doctors)
						ch.addItem(d.getFullName()+"("+String.valueOf(d.id)+")");
				JLabel l = new JLabel("select a doctor to modify:");
				f.setLayout(new BorderLayout());
				f.add(l, BorderLayout.WEST);
				f.add(ch, BorderLayout.EAST);
				f.setVisible(true);
				JButton b = new JButton("OK");
				f.add(b, BorderLayout.SOUTH);
				b.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent arg0) {
						long idOfDoctor = Long.parseLong(ch.getSelectedItem().split(".+\\(|\\)")[1]);
						for(int i = 0; i < Doctor.doctors.size(); i++)
							if(Doctor.doctors.get(i).id == idOfDoctor){
								new ModDoctorGUI(i);
								break;
							}
						f.setVisible(false);
					}
				});
			}
		});
		bRet.addActionListener(new ActionListener() { // manande button mod
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JFrame f  = new JFrame("show");
				f.setSize(600, 100);
				setVisible(false);
				Choice ch = new Choice();
				if(Doctor.doctors.size() == 0){
					JOptionPane.showMessageDialog(null, "there is no doctor! please add doctor");
					setVisible(true);
					return;
				}
				else
					for(Doctor d : Doctor.doctors)
						ch.addItem(d.getFullName()+"("+String.valueOf(d.id)+")");
				JLabel l = new JLabel("select a doctor to show:");
				f.setLayout(new BorderLayout());
				f.add(l, BorderLayout.WEST);
				f.add(ch, BorderLayout.EAST);
				f.setVisible(true);
				JButton b = new JButton("OK");
				f.add(b, BorderLayout.SOUTH);
				b.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent arg0) {
						long idOfDoctor = Long.parseLong(ch.getSelectedItem().split(".+\\(|\\)")[1]);
						for(int i = 0; i < Doctor.doctors.size(); i++)
							if(Doctor.doctors.get(i).id == idOfDoctor){
								new RetDoctorGUI(i);
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
}
