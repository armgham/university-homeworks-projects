package gui.add;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import gui.MedicineGUI;
import models.Medicine;

public class AddMedicineGUI implements ActionListener {
	
	MedicineGUI mG;
	/**
	 * safheye ghabl ra migirad ta setvisible false konad
	 * @param mG
	 */
	public AddMedicineGUI(MedicineGUI mG) {
		this.mG = mG;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {//vorudi ra ta zamani ke tekrari ya ghalat nabashad migirad va yek medicine misazad
		mG.setVisible(false);
		
		String name = "";
		try{
			name = JOptionPane.showInputDialog(null, new JLabel("name : "));
			while(name.equals("") || Medicine.getAllNames().contains(name)){
				name = JOptionPane.showInputDialog(null, new JLabel("that name was wrong! please enter another name : "));
			}
			JOptionPane.showMessageDialog(null, "successfully data addded!");
			new Medicine(name);
			mG.setVisible(true);
		}catch(Exception e){
			mG.setVisible(true);
		}
	}

}
