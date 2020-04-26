package gui.mod;
import java.awt.BorderLayout;
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

import gui.MedicineGUI;
import models.Medicine;

public class ModMedicineGUI extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int indexOfMedicineToChange;
	JButton bBack, bOK;
	JLabel jN;
	JTextField tN;
	
	public ModMedicineGUI(int indexOfMedicineToChange) {
		super("modify a medicine");
		this.indexOfMedicineToChange = indexOfMedicineToChange;
		setVisible(true);
		
		setSize(300, 150);
		setLayout(new BorderLayout());
		add(new JLabel("modify information of medicine"), BorderLayout.NORTH);
		
		JPanel pCenter = new JPanel();
		pCenter.setLayout(new GridLayout(1, 2, 0, 0));
		add(pCenter, BorderLayout.CENTER);
		
		jN = new JLabel("change name :");
		tN = new JTextField();
		
		pCenter.add(jN);
		pCenter.add(tN);
		
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
				String name = null;
				name = tN.getText();
				if(!name.equals(""))
					Medicine.medicines.get(indexOfMedicineToChange).setName(name);
				JOptionPane.showMessageDialog(null, "done!");
			}
		});
		
		bBack.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
				new MedicineGUI();
			}
		});
		
	}

}
