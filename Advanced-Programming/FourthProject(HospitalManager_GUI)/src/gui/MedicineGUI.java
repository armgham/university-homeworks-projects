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

import gui.add.AddMedicineGUI;
import gui.mod.ModMedicineGUI;
import gui.ret.RetMedicineGUI;
import models.Medicine;

public class MedicineGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	JButton bAdd, bMod, bRet, bBack;
	JLabel jAdd, jMod, jRet;
	/**
	 * yek safheye gerafiki baray medicine ke 3 button darad
	 */
	public MedicineGUI(){
		super("Medicines' management");
		setSize(700, 500);
		setVisible(true);
		setLayout(new BorderLayout());
		
		add(new JLabel("Medicines' management"), BorderLayout.NORTH);
		
		JPanel pCenter = new JPanel();
		pCenter.setLayout(new GridLayout(3, 2, 5, 40));
		add(pCenter, BorderLayout.CENTER);
		
		jAdd = new JLabel("add medicine");
		jMod = new JLabel("modify medicine");
		jRet = new JLabel("retrieve medicine");
		
		bAdd = new JButton("add medicine");
		bMod = new JButton("modify medicine");
		bRet = new JButton("retrieve medicine");
		
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
		
		bAdd.addActionListener(new AddMedicineGUI(this));//vaghti button e add click shod varede kelase add mishavad
		bMod.addActionListener(new ActionListener() {//listi az medicine ha ra namayesh midahad va index e medicine e entekhab shode ra be kelase mod mibarad
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JFrame f  = new JFrame("modify");
				f.setSize(600, 100);
				setVisible(false);
				Choice ch = new Choice();
				if(Medicine.medicines.size() == 0){
					JOptionPane.showMessageDialog(null, "there is no medicine! please add medicine");
					setVisible(true);
					return;
				}
				else
					for(Medicine m : Medicine.medicines)
						ch.addItem(m.getName());
				JLabel l = new JLabel("select a medicine to modify:");
				f.setLayout(new BorderLayout());
				f.add(l, BorderLayout.WEST);
				f.add(ch, BorderLayout.EAST);
				f.setVisible(true);
				JButton b = new JButton("OK");
				f.add(b, BorderLayout.SOUTH);
				b.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent arg0) {
						for(int i = 0; i < Medicine.medicines.size(); i++)
							if(Medicine.medicines.get(i).getName().equals(ch.getSelectedItem())){
								new ModMedicineGUI(i);
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
				if(Medicine.medicines.size() == 0){
					JOptionPane.showMessageDialog(null, "there is no medicine! please add medicine");
					setVisible(true);
					return;
				}
				else
					for(Medicine m : Medicine.medicines)
						ch.addItem(m.getName());
				JLabel l = new JLabel("select a medicine to show:");
				f.setLayout(new BorderLayout());
				f.add(l, BorderLayout.WEST);
				f.add(ch, BorderLayout.EAST);
				f.setVisible(true);
				JButton b = new JButton("OK");
				f.add(b, BorderLayout.SOUTH);
				b.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent arg0) {
						for(int i = 0; i < Medicine.medicines.size(); i++)
							if(Medicine.medicines.get(i).getName().equals(ch.getSelectedItem())){
								new RetMedicineGUI(i);
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
