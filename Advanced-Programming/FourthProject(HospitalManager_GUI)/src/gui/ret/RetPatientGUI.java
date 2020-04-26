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

import gui.PatientGUI;
import models.Patient;

public class RetPatientGUI extends JFrame {// yek safheye gerafiki baraye nashan dadane ettelaate patient

	private static final long serialVersionUID = 1L;
	int indexOfPatientToChange;
	JButton bBack;
	JLabel jFN, jBD, jGen, jDoctor, JDiseases, JMedicines;
	JList<String> lDis, lMed;
	/**
	 * index e patient e entekhab shode az safheye ghable ra migirad
	 * @param indexOfPatientToChange
	 */
	public RetPatientGUI(int indexOfPatientToChange){
		super("show a patient");
		setVisible(true);
		this.indexOfPatientToChange = indexOfPatientToChange;
		setSize(300, 400);
		setLayout(new BorderLayout());
		add(new JLabel("show information of patient"), BorderLayout.NORTH);
		
		JPanel pCenter = new JPanel();
		pCenter.setLayout(new GridLayout(6, 1, 0, 0));
		add(pCenter, BorderLayout.CENTER);
		jFN = new JLabel("patient's full name:      " + Patient.patients.get(indexOfPatientToChange).getFullName());
		jBD = new JLabel("birth date:                  " + Patient.patients.get(indexOfPatientToChange).getBirthDate());
		jGen = new JLabel("gender:                     " + Patient.patients.get(indexOfPatientToChange).getGender());
		try{
			jDoctor = new JLabel("doctor: " + Patient.patients.get(indexOfPatientToChange).getDoctor().getFullName() + "(" +
									Patient.patients.get(indexOfPatientToChange).getDoctor().id + ")");
		}catch(Exception e){
			jDoctor = new JLabel("doctor: ");
		}
		
		String[] jDiseases = new String[Patient.patients.get(indexOfPatientToChange).getAllDiseases().size()];
		String[] jMedicines = new String[Patient.patients.get(indexOfPatientToChange).getAllMedicine().size()];
		for (int i = 0; i < jDiseases.length; i++)
			jDiseases[i] = Patient.patients.get(indexOfPatientToChange).getAllDiseases().get(i).getName();
		for(int i = 0; i < jMedicines.length; i++)
			jMedicines[i] = Patient.patients.get(indexOfPatientToChange).getAllMedicine().get(i).getName();
		
		lDis = new JList<String>(jDiseases);
		lMed = new JList<String>(jMedicines);
		JDiseases = new JLabel("diseases: ");
		JMedicines = new JLabel("medicines: ");
		
		pCenter.add(jFN);
		pCenter.add(jBD);
		pCenter.add(jGen);
		pCenter.add(jDoctor);
		
		JPanel pDis = new JPanel();
		pDis.setLayout(new GridLayout(1, 2));
		pDis.add(JDiseases);
		pDis.add(new JScrollPane(lDis));
		pCenter.add(pDis);
		
		JPanel pMed = new JPanel();
		pMed.setLayout(new GridLayout(1, 2));
		pMed.add(JMedicines);
		pMed.add(new JScrollPane(lMed));
		pCenter.add(pMed);
		
		bBack = new JButton("Back");
		add(bBack, BorderLayout.SOUTH);
		bBack.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
				new PatientGUI();
			}
		});
		
	}

}
