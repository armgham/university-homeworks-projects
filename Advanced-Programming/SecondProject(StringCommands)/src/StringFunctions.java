import java.util.Scanner; //emkane tarkib shodane dastur ha mojood hast (nomre mosbate soal)
class Methods{
	/**
	 * tabe sort ke yek reshte va 2 adad a , b ra migirad va az a ta b ra sort mikonad 
	 * @param x
	 * @param a
	 * @param b
	 * @return
	 */
	static String sort(String x, int a, int b){
		char[] ch = x.toCharArray();
		for (int i = a; i < b + 1; i++) {
			for (int j = a + 1; j < b + 1 - (i - a); j++) {
				if(ch[j-1] > ch[j]){
					char ctemp = ch[j];
					ch[j] = ch[j-1];
					ch[j-1] = ctemp;
				}
			}
		}
		return String.valueOf(ch);
	}
	/**
	 * sort az 0 ta akhar
	 * @param x
	 * @return
	 */
	static String sort(String x){
		return sort(x, 0, x.length() - 1);
	}
	/**
	 * in tabe tamame horufe yek reshte ra kuchak mikonad. az a ta b
	 * @param x
	 * @param a
	 * @param b
	 * @return
	 */
	static String toLower(String x, int a, int b){
		char[] ch = x.toCharArray();
		for (int i = a; i < b + 1; i++) {
			if((int)ch[i] <= 90 && (int)ch[i] >= 65)
				ch[i] += 32;
		}
		return String.valueOf(ch);
	}
	/**
	 * to lower az 0 ta akhar
	 * @param x
	 * @return
	 */
	static String toLower(String x){
		return toLower(x, 0, x.length() - 1);
	}
	/**
	 * in tabe tamame horufe yek reshte ra bozorg mikonad. az a ta b
	 * @param x
	 * @param a
	 * @param b
	 * @return
	 */
	static String toUpper(String x, int a , int b){
		char[] ch = x.toCharArray();
		for (int i = a; i < b + 1; i++) {
			if((int)ch[i] <= 122 && (int)ch[i] >= 97)
				ch[i] -= 32;
		}
		return String.valueOf(ch);
	}
	/**
	 * to upper az 0 ta akhar
	 * @param x
	 * @return
	 */
	static String toUpper(String x){
		return toUpper(x, 0, x.length() - 1);
	}
	/**
	 * in tabe makuse yek reshte ra be dast miavarad
	 * @param x
	 * @return
	 */
	static String reverseString(String x){
		char[] ch = new char[x.length()];
		for (int i = 0; i < ch.length; i++) {
			ch[i] = x.charAt(x.length() - i - 1);
		}
		return String.valueOf(ch);
	}
	/**
	 * 2 string ra daryaft mikonad va barresi mikonad ke in 2 makuse ham hastand ya na.
	 * @param a
	 * @param b
	 * @return
	 */
	static String isPalindromes(String a, String b){		
		if (a.equals(reverseString(b)))
			return "True";
		else
			return "False";
	}
	/**
	 * tabe rotate
	 * @param x
	 * @param n
	 * @return
	 */
	static String rotate(String x, int n){
		n = ((n % x.length()) + x.length()) % x.length();
		char[] ch = x.toCharArray();
		for(int j = 1; j <= n;j++){
			char che = ch[ch.length - 1];
			for(int i = ch.length - 1; i >= 1; i--){
				ch[i] = ch[i - 1];
			}
			ch[0] = che;
		}
		
		return String.valueOf(ch);
	}
	/**
	 * tabe substring
	 * @param x
	 * @param a
	 * @param b
	 * @return
	 */
	static String substring(String x, int a, int b){
		if(a >= x.length() || b >= x.length())
			return null;
		if(a == 0 && b == -1)
			return "";
		char[] ch = new char[b - a + 1];
		
		for (int i = a; i <= b; i++){
			ch[i - a] = x.charAt(i);
		}
		return String.valueOf(ch);
	}
	static int indexOf(String x, String y){
		for (int i = 0; i <= x.length() - y.length(); i++) {
			if(substring(x, i, i + y.length() - 1).equals(y)){
				return i;
			}
		}
		return -1;
	}
	/**
	 * moadele tabe split dar java
	 * @param x
	 * @param y
	 * @return
	 */
	static String[] ssplit(String x, String y){
		int size = 1;
		String xCopy = x;
		while(indexOf(xCopy, y) != -1){
			size++;
			if (indexOf(xCopy, y) == xCopy.length() - y.length())
				xCopy = "";
			else
				xCopy = substring(xCopy, indexOf(xCopy, y) + y.length(), xCopy.length() - 1);
		}
		String[] s = new String[size];
		int i = 0;
		while (indexOf(x, y) != -1){
			s[i] = substring(x, 0, indexOf(x, y) - 1);
			i++;
			if (indexOf(x, y) == x.length() - y.length())
				x = "";
			else
				x = substring(x, indexOf(x, y) + y.length(), x.length() - 1);
		}
		s[i] = x;
		return s;
	}
	static String split(String x, String y){
		String str ;
		String[] splitstr = ssplit(x, y);
		str = splitstr[0];
		for (int i = 1; i < splitstr.length; i++) {
			str = str + "_" + splitstr[i];
		}
		return str;
	}
	/**
	 * in tabe yek string ra migirad va barresi mikonad aya dar an dasturi hast ya na.
	 * @param x
	 * @return
	 */
	static boolean IsCommand(String x){
		String[] Commands = {"sort", "toLower", "toUpper", "isPalindromes", "rotate", "substring", "indexOf", "split"};
		for (int i = 0; i < Commands.length; i++) {
			if (i == Commands.length - 1 && indexOf(x, Commands[i] + "(") == -1)
				return false;
			else if (indexOf(x, Commands[i] + "(") != -1){
				x = substring(x, indexOf(x, Commands[i]) + Commands[i].length() + 2, x.length() - 1);
				break;
			}
		}
		if(indexOf(x, ")") >= 0)
			return true;
		return false;
	}
	/**
	 * in tabe yek string ra migirad va parameter haye an ra be surate yek araye barmigardanad
	 * @param x
	 * @return
	 */
	static String[] Parameters(String x){
		String[] Pms;
		if (IsCommand(x)){
			int size = 0;
			String xtemp = x;
			while(xtemp.length() > 0){
				if(indexOf(xtemp, ",") < indexOf(xtemp, "(") && indexOf(xtemp, ",") != -1 && indexOf(xtemp, "(") != -1){
					xtemp = substring(xtemp, indexOf(xtemp, ",") + 1, xtemp.length() - 1);
					size++;
				}
				else if(indexOf(xtemp, "(") == -1){
					if(indexOf(xtemp, ",") == -1){
						xtemp = "";
						size++;
					}
					else{
						xtemp = substring(xtemp, indexOf(xtemp, ",") + 1, xtemp.length() - 1);
						size++;
					}
				}
				else {
					int ii = 1;
					for(int j = indexOf(xtemp, "(") + 1; j < xtemp.length(); j++){
						if(xtemp.charAt(j) == '(')
							ii++;
						else if(xtemp.charAt(j) == ')')
							ii--;
						if(ii == 0){
							if(indexOf(substring(xtemp, j, xtemp.length() - 1), ",") != -1)
								xtemp = substring(xtemp, j + 2, xtemp.length() - 1);
							else
								xtemp = "";
							size++;
							break;
						}
					}
				}
			}
			Pms = new String[size];
			
			for(int i = 0; i < size - 1; i++){
				if(indexOf(x, ",")  < indexOf(x, "(") && indexOf(x, ",") != -1){
					Pms[i] = substring(x, 0, indexOf(x, ",") - 1);
					x = substring(x, indexOf(x, ",") + 1, x.length() - 1);
				}
				else if(indexOf(x, "(") == -1){
					if(indexOf(x, ",") == -1){
						Pms[i] = x;
						x = "";
					}
					else{
						Pms [i] = substring(x, 0, indexOf(x, ",") - 1);
						x = substring(x, indexOf(x, ",") + 1, x.length() - 1);
					}
				}
				else {
					int ii = 1;
					for(int j = indexOf(x, "(") + 1; j < x.length(); j++){
						if(x.charAt(j) == '('){
							ii++;
						}
						else if(x.charAt(j) == ')'){
							ii--;
						}
						if(ii == 0){
							Pms[i] = substring(x, 0, j);
							x = substring(x, j + 2, x.length() - 1);
							break;
						}
					}
				}
			}
			Pms[size - 1] = x;
		}
		else
			Pms = ssplit(x, ",");	
		return Pms;
	}
	/**
	 * in tabe yek dastur va parameter ra dar 1 string migirad va javab ra barmigardanad.
	 * @param command
	 * @return
	 */
	static String run(String command){
		if(IsCommand(command)){
			String[] CommandAndParameter = new String[2]; // CommandAndParameter[0]=dastur , CommandAndParameter[1]=parameter
			CommandAndParameter[0] = substring(command, 0, indexOf(command, "(") - 1);
			CommandAndParameter[1] = substring(command, indexOf(command, "(") + 1, command.length() - 2);

			if(CommandAndParameter[0].equals("sort")){
				String[] x = Parameters(CommandAndParameter[1]);
				if(x.length == 3)
					return (sort(run(x[0]), Integer.parseInt(run(x[1])), Integer.parseInt(run(x[2]))));
				else if(x.length == 1)
					return (sort(run(x[0])));
			}
			else if(CommandAndParameter[0].equals("toLower")){
				String[] x = Parameters(CommandAndParameter[1]);
				if(x.length == 3)
					return (toLower(run(x[0]), Integer.parseInt(run(x[1])), Integer.parseInt(run(x[2]))));
				else if(x.length == 1)
					return (toLower(run(x[0])));
			}
			else if(CommandAndParameter[0].equals("toUpper")){
				String[] x = Parameters(CommandAndParameter[1]);
				if(x.length == 3)
					return (toUpper(run(x[0]), Integer.parseInt(run(x[1])), Integer.parseInt(run(x[2]))));
				else if(x.length == 1)
					return (toUpper(run(x[0])));
			}
			else if(CommandAndParameter[0].equals("rotate")){
				String[] x = Parameters(CommandAndParameter[1]);
				return (rotate(run(x[0]), Integer.parseInt(run(x[1]))));
			}
			else if(CommandAndParameter[0].equals("split")){
				String[] x = Parameters(CommandAndParameter[1]);
				return (split(run(x[0]), run(x[1])));
			}
			else if(CommandAndParameter[0].equals("isPalindromes")){
				String[] x = Parameters(CommandAndParameter[1]);
				return (isPalindromes(run(x[0]), run(x[1])));
			}
			else if(CommandAndParameter[0].equals("indexOf")){
				String[] x = Parameters(CommandAndParameter[1]);
				return String.valueOf(indexOf(run(x[0]), run(x[1])));
			}
			else if(CommandAndParameter[0].equals("substring")){
				String[] x = Parameters(CommandAndParameter[1]);
				return (substring(run(x[0]), Integer.parseInt(run(x[1])), Integer.parseInt(run(x[2]))));
			}
		}
		else
			return command;
		return "";
	}
}
public class StringFunctions {

	public static void main(String[] args) {
		Scanner reader = new Scanner(System.in);
		String input = reader.nextLine();
		String[] cin = Methods.ssplit(input, "_");
		reader.close();
		for (int i = 0; i < cin.length; i++) {
			System.out.println(Methods.run(cin[i]));
		}
	}

}
