package inputoutput;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class InOut { // in kelas baraye kar ba file mibashad
	
	public static void outputObject(Object obj, String path){ // neveshtane object dar file
		FileOutputStream fileOut = null;
		ObjectOutputStream objectOut = null;
		try{
			fileOut = new FileOutputStream(path);
			objectOut = new ObjectOutputStream(fileOut);
			objectOut.writeObject(obj);
			fileOut.close();
		}catch(Exception e){
			System.out.println("file problem!");
		}
	}
	
	public static Object inputObject(String path){ // khandane object az file
		FileInputStream fileIn = null;
		ObjectInputStream objectIn = null;
		Object result = new ArrayList<>();
		try{
			fileIn = new FileInputStream(path);
			objectIn = new ObjectInputStream(fileIn);
			
			result = objectIn.readObject();
			fileIn.close();
		}catch(Exception e){
			System.out.println("file problem");
		}
		return result;
	}

}
