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

import gui.add.AddDiseaseGUI;
import gui.mod.ModDiseaseGUI;
import gui.ret.RetDiseaseGUI;
import models.Disease;

public class DiseaseGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	JButton bAdd, bMod, bRet, bBack;
	JLabel jAdd, jMod, jRet;
	/**
	 * yek safheye gerafiki baray disease ke 3 button darad
	 */
	public DiseaseGUI(){
		super("Diseases' management");
		setSize(700, 500);
		setVisible(true);
		setLayout(new BorderLayout());
		
		add(new JLabel("Diseases' management"), BorderLayout.NORTH);
		
		JPanel pCenter = new JPanel();
		pCenter.setLayout(new GridLayout(3, 2, 5, 40));
		add(pCenter, BorderLayout.CENTER);
		
		jAdd = new JLabel("add disease");
		jMod = new JLabel("modify disease (add medicine and symptom ...)");
		jRet = new JLabel("retrieve disease");
		
		bAdd = new JButton("add disease");
		bMod = new JButton("modify disease");
		bRet = new JButton("retrieve disease");
		
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
		
		bAdd.addActionListener(new AddDiseaseGUI(this)); // vaghti button e add click shod varede kelase add mishavad 
		bMod.addActionListener(new ActionListener() { // listi az disease ha ra namayesh midahad va index e disease e entekhab shode ra be kelase moddisease mibarad
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JFrame f  = new JFrame("modify");
				f.setSize(600, 100);
				setVisible(false);
				Choice ch = new Choice();
				if(Disease.diseases.size() == 0){
					JOptionPane.showMessageDialog(null, "there is no disease! please add disease");
					setVisible(true);
					return;
				}
				else
					for(Disease d : Disease.diseases)
						ch.addItem(d.getName()); // list disease
				JLabel l = new JLabel("select a disease to modify:");
				f.setLayout(new BorderLayout());
				f.add(l, BorderLayout.WEST);
				f.add(ch, BorderLayout.EAST);
				f.setVisible(true);
				JButton b = new JButton("OK");
				f.add(b, BorderLayout.SOUTH);
				b.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent arg0) {
						for(int i = 0; i < Disease.diseases.size(); i++)
							if(Disease.diseases.get(i).getName().equals(ch.getSelectedItem())){
								new ModDiseaseGUI(i);
								break;
							}
						f.setVisible(false);
					}
				});
			}
		});
		bRet.addActionListener(new ActionListener() { // manande ghabli
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JFrame f  = new JFrame("show");
				f.setSize(600, 100);
				setVisible(false);
				Choice ch = new Choice();
				if(Disease.diseases.size() == 0){
					JOptionPane.showMessageDialog(null, "there is no disease! please add disease");
					setVisible(true);
					return;
				}
				else
					for(Disease d : Disease.diseases)
						ch.addItem(d.getName());
				JLabel l = new JLabel("select a disease to show:");
				f.setLayout(new BorderLayout());
				f.add(l, BorderLayout.WEST);
				f.add(ch, BorderLayout.EAST);
				f.setVisible(true);
				JButton b = new JButton("OK");
				f.add(b, BorderLayout.SOUTH);
				b.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent arg0) {
						for(int i = 0; i < Disease.diseases.size(); i++)
							if(Disease.diseases.get(i).getName().equals(ch.getSelectedItem())){
								new RetDiseaseGUI(i);
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
