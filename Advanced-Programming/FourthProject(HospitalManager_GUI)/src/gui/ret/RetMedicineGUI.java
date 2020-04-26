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

import gui.MedicineGUI;
import models.Medicine;

public class RetMedicineGUI extends JFrame {// yek safheye gerafiki baraye nashan dadane ettelaate medicine

	private static final long serialVersionUID = 1L;
	int indexOfMedicineToChange;
	JButton bBack;
	JLabel jN, JDiseases, JPatients;
	JList<String> lDis, lPat;
	/**
	 * index e medicine e entekhab shode az safheye ghable ra migirad
	 * @param indexOfMedicineToChange
	 */
	public RetMedicineGUI(int indexOfMedicineToChange) {
		super("show a medicine");
		setVisible(true);
		this.indexOfMedicineToChange = indexOfMedicineToChange;
		setSize(300, 400);
		setLayout(new BorderLayout());
		add(new JLabel("show information of medicine"), BorderLayout.NORTH);
		
		JPanel pCenter = new JPanel();
		pCenter.setLayout(new GridLayout(3, 1, 0, 0));
		add(pCenter, BorderLayout.CENTER);
		
		jN = new JLabel("medicine's name:      " + Medicine.getAllMedicines().get(indexOfMedicineToChange).getName());
		
		String[] jDiseases = new String[Medicine.getAllMedicines().get(indexOfMedicineToChange).getAllDiseases().size()];
		String[] jPatients = new String[Medicine.getAllMedicines().get(indexOfMedicineToChange).getAllPatients().size()];
		for (int i = 0; i < jDiseases.length; i++)
			jDiseases[i] = Medicine.getAllMedicines().get(indexOfMedicineToChange).getAllDiseases().get(i).getName();
		for(int i = 0; i < jPatients.length; i++)
			jPatients[i] = Medicine.getAllMedicines().get(indexOfMedicineToChange).getAllPatients().get(i).getFullName() + "(" + 
									Medicine.getAllMedicines().get(indexOfMedicineToChange).getAllPatients().get(i).id + ")";
		
		lDis = new JList<String>(jDiseases);
		lPat = new JList<String>(jPatients);
		JDiseases = new JLabel("diseases: ");
		JPatients = new JLabel("patients: ");
		
		pCenter.add(jN);
		
		JPanel pDis = new JPanel();
		pDis.setLayout(new GridLayout(1, 2));
		pDis.add(JDiseases);
		pDis.add(new JScrollPane(lDis));
		pCenter.add(pDis);
		
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
				new MedicineGUI();
			}
		});
		
	}

}
