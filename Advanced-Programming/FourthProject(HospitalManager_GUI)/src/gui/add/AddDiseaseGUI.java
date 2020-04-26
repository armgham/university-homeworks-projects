package gui.add;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import gui.DiseaseGUI;
import models.Disease;

public class AddDiseaseGUI implements ActionListener {
	DiseaseGUI diG;
	
	/**
	 * safheye ghabl ra migirad ta setvisible false konad
	 * @param diG
	 */
	public AddDiseaseGUI(DiseaseGUI diG) {
		this.diG = diG;
	}
	
	
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		diG.setVisible(false);
		String name = "";
		try{ //vorudi ra ta zamani ke tekrari ya ghalat nabashad migirad va yek disease misazad
			name = JOptionPane.showInputDialog(null, new JLabel("name : "));
			while(name.equals("") || Disease.getAllNames().contains(name)){
				name = JOptionPane.showInputDialog(null, new JLabel("that name was wrong! please enter another name : "));
			}
			JOptionPane.showMessageDialog(null, "successfully data addded!");
			new Disease(name);
			diG.setVisible(true);
		}catch(Exception e){
			diG.setVisible(true);
		}
	}

}
