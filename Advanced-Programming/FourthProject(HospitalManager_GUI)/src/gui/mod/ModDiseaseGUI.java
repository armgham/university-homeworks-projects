package gui.mod;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import gui.DiseaseGUI;
import models.Disease;
import models.Medicine;

public class ModDiseaseGUI extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int indexOfDiseaseToChange;
	JButton bBack, bOK;
	JLabel jN, jAddSym, jRemSym, jAddMed, jRemMed;
	JTextField tN;
	JTextArea tAAddSym;
	JList<String> lRemSym, lAddMed, lRemMed;
	
	public ModDiseaseGUI(int indexOfDiseaseToChange) {
		super("modify a disease");
		this.indexOfDiseaseToChange = indexOfDiseaseToChange;
		setVisible(true);
		
		setSize(700, 500);
		setLayout(new BorderLayout());
		add(new JLabel("modify information of disease"), BorderLayout.NORTH);
		
		JPanel pCenter = new JPanel();
		pCenter.setLayout(new GridLayout(5, 2, 0, 0));
		add(pCenter, BorderLayout.CENTER);
		
		jN = new JLabel("change name :");
		jAddSym = new JLabel("add symptom(s) (each line one symptom) :");
		jRemSym = new JLabel("remove symptom(s) :");
		jAddMed = new JLabel("add medicine(s) :");
		jRemMed = new JLabel("remove medicine(s) :");
		
		tN = new JTextField();
		tAAddSym = new JTextArea();
		String[] nameAllMed = new String[Medicine.medicines.size()];
		String[] nameDisSym = new String[Disease.diseases.get(indexOfDiseaseToChange).symptoms.size()];
		String[] nameDisMed = new String[Disease.diseases.get(indexOfDiseaseToChange).medicines.size()];
		
		for(int i = 0; i < Medicine.medicines.size(); i++)
			nameAllMed[i] = Medicine.medicines.get(i).getName();
		for(int i = 0; i < Disease.diseases.get(indexOfDiseaseToChange).symptoms.size(); i++)
			nameDisSym[i] = Disease.diseases.get(indexOfDiseaseToChange).symptoms.get(i);
		for(int i = 0; i < Disease.diseases.get(indexOfDiseaseToChange).medicines.size(); i++)
			nameDisMed[i] = Disease.diseases.get(indexOfDiseaseToChange).medicines.get(i).getName();
		
		lAddMed = new JList<String>(nameAllMed); 
		lAddMed.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		lRemSym = new JList<String>(nameDisSym);
		lRemSym.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		lRemMed = new JList<String>(nameDisMed);
		lRemMed.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		
		pCenter.add(jN);
		pCenter.add(tN);
		
		pCenter.add(jAddSym);
		pCenter.add(tAAddSym);
		
		pCenter.add(jAddMed);
		pCenter.add((Medicine.medicines.size() != 0)?new JScrollPane(lAddMed):new JLabel("there is no medicine!"));
		
		pCenter.add(jRemSym);
		pCenter.add((Disease.diseases.get(indexOfDiseaseToChange).symptoms.size() != 0)?new JScrollPane(lRemSym):
																			new JLabel("this disease has no symptom to remove!"));
		
		pCenter.add(jRemMed);
		pCenter.add((Disease.diseases.get(indexOfDiseaseToChange).medicines.size() != 0)?new JScrollPane(lRemMed):
																			new JLabel("this disease has no medicine to remove!"));
		
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
				
				String name = null, addSym;
				name = tN.getText();
				addSym = tAAddSym.getText();
				String[] syms = addSym.split("\n");
				
				if(!name.equals(""))
					Disease.diseases.get(indexOfDiseaseToChange).setName(name);
				if(!addSym.equals(""))
					if(syms.length == 1)
						Disease.diseases.get(indexOfDiseaseToChange).addSymptom(addSym);
					else if(syms.length > 1)
						Disease.diseases.get(indexOfDiseaseToChange).addAllSymptoms(new ArrayList<String>(Arrays.asList(syms)));
				int[] selectedAM = lAddMed.getSelectedIndices();
				int[] selectedRS = lRemSym.getSelectedIndices();
				int[] selectedRM = lRemMed.getSelectedIndices();
				
				
				if(selectedAM.length != 1 && selectedAM.length != 0){
					ArrayList<Medicine> mToAdd = new ArrayList<Medicine>();
					for(int i = 0; i < selectedAM.length; i++)
						mToAdd.add(Medicine.medicines.get(selectedAM[i]));
					Disease.diseases.get(indexOfDiseaseToChange).addAllMedicines(mToAdd);
				}
				else if(selectedAM.length != 0)
					Disease.diseases.get(indexOfDiseaseToChange).addMedicine(Medicine.medicines.get(selectedAM[0]));
				
				if(selectedRS.length != 1 && selectedRS.length != 0){
					ArrayList<String> sToRem = new ArrayList<String>();
					for (int i = 0; i < selectedRS.length; i++)
						sToRem.add(Disease.diseases.get(indexOfDiseaseToChange).symptoms.get(selectedRS[i]));
					Disease.diseases.get(indexOfDiseaseToChange).removeAllSymptoms(sToRem);
				}
				else if(selectedRS.length != 0)
					Disease.diseases.get(indexOfDiseaseToChange).
									removeSymptom(Disease.diseases.get(indexOfDiseaseToChange).symptoms.get(selectedRS[0]));
				
				if(selectedRM.length != 1 && selectedRM.length != 0){
					ArrayList<Medicine> mToRem = new ArrayList<Medicine>();
					for (int i = 0; i < selectedRM.length; i++)
						mToRem.add(Medicine.medicines.get(selectedRM[i]));
					Disease.diseases.get(indexOfDiseaseToChange).removeAllMedicines(mToRem);
				}
				else if(selectedRM.length != 0)
					Disease.diseases.get(indexOfDiseaseToChange).removeMedicine(Medicine.medicines.get(selectedRM[0]));
				
				JOptionPane.showMessageDialog(null, "done!");
			}
		});
		bBack.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
				new DiseaseGUI();
			}
		});
		
	}

}
