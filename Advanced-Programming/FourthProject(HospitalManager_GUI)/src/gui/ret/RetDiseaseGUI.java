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

import gui.DiseaseGUI;
import models.Disease;

public class RetDiseaseGUI extends JFrame {// yek safheye gerafiki baraye nashan dadane ettelaate disease

	private static final long serialVersionUID = 1L;
	int indexOfDiseaseToChange;
	JButton bBack;
	JLabel jN, JAllInfected, JAllSym, JAllMed;
	JList<String> lAllInfected, lAllSym, lAllMed;
	/**
	 * index e disease e entekhab shode az safheye ghable ra migirad
	 * @param indexOfDiseaseToChange
	 */
	public RetDiseaseGUI(int indexOfDiseaseToChange){
		
		super("show a disease");
		setVisible(true);
		this.indexOfDiseaseToChange = indexOfDiseaseToChange;
		setSize(300, 400);
		setLayout(new BorderLayout());
		add(new JLabel("show information of disease"), BorderLayout.NORTH);
		
		JPanel pCenter = new JPanel();
		pCenter.setLayout(new GridLayout(4, 1, 0, 0));
		add(pCenter, BorderLayout.CENTER);
		
		jN = new JLabel("disease's name:       " + Disease.diseases.get(indexOfDiseaseToChange).getName());
		JAllInfected = new JLabel("infected :");
		JAllMed = new JLabel("medicines :");
		JAllSym = new JLabel("symptoms :");
		
		String[] jAllInfected = new String[Disease.diseases.get(indexOfDiseaseToChange).getAllInfected().size()];
		for (int i = 0; i < jAllInfected.length; i++)
			jAllInfected[i] = Disease.diseases.get(indexOfDiseaseToChange).getAllInfected().get(i).getFullName() + "(" +
									Disease.diseases.get(indexOfDiseaseToChange).getAllInfected().get(i).id + ")";
		lAllInfected = new JList<String>(jAllInfected);
		String[] jAllMed = new String[Disease.diseases.get(indexOfDiseaseToChange).getAllMedicines().size()];
		for(int i = 0; i < jAllMed.length; i++)
			jAllMed[i] = Disease.diseases.get(indexOfDiseaseToChange).getAllMedicines().get(i).getName();
		lAllMed = new JList<String>(jAllMed);
		String[] jAllSym = new String[Disease.diseases.get(indexOfDiseaseToChange).getAllSymptoms().size()];
		for(int i = 0; i < jAllSym.length; i++)
			jAllSym[i] = Disease.diseases.get(indexOfDiseaseToChange).getAllSymptoms().get(i);
		lAllSym = new JList<String>(jAllSym);
		pCenter.add(jN);
		
		JPanel pInf = new JPanel();
		pInf.setLayout(new GridLayout(1, 2));
		pInf.add(JAllInfected);
		pInf.add(new JScrollPane(lAllInfected));
		pCenter.add(pInf);
		
		JPanel pMed = new JPanel();
		pMed.setLayout(new GridLayout(1, 2));
		pMed.add(JAllMed);
		pMed.add(new JScrollPane(lAllMed));
		pCenter.add(pMed);
		
		JPanel pSym = new JPanel();
		pSym.setLayout(new GridLayout(1, 2));
		pSym.add(JAllSym);
		pSym.add(new JScrollPane(lAllSym));
		pCenter.add(pSym);
		
		bBack = new JButton("Back");
		add(bBack, BorderLayout.SOUTH);
		
		bBack.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
				new DiseaseGUI();
			}
		});
		
	}

}
