import java.util.Scanner;
public class Programmer {
	/**
	 * in tabe reshteye diposits va tedade bank va tedade sepordegozar ra migirad va yek reshte az esme sepordegozar ha ra barmigardanad
	 * @param diposits
	 * @param TedadeSepordegozar
	 * @param TedadeBank
	 * @return esms
	 */
	static String[] Esms(String diposits, int TedadeSepordegozar,int TedadeBank){
		String[] esms = new String[TedadeSepordegozar];
		String temp = diposits;
		for(int i = 0; i < TedadeSepordegozar; i++){
			esms[i] = temp.substring(0, temp.indexOf(" ")); //ghesmate avvale reshte ke esm hast ra dakhele araye esms mirizad
			for(int j = 0; j <= TedadeBank ;j++) //be tedade bank + 1 space ra jolo mibarad ta beresad be esme badi
				temp = temp.substring(temp.indexOf(" ") + 1, temp.length());
		}
		return esms;
	}
	/**
	 * in tabe mohasebe mikonad har sepordegozar cheghadr pool sepordegozari karde ast
	 * @param diposits
	 * @param esms
	 * @param TedadeSepordegozar
	 * @return Pool
	 */
	static float[] Seporde(String diposits, String[] esms, int TedadeSepordegozar){
		float[] Pool = new float[TedadeSepordegozar];
		String PoolStr = null;
		for(int i=0; i < TedadeSepordegozar; i++){ //be tedade afrad. ta poole har fard hesab shavad 
			if(i != (TedadeSepordegozar - 1))
				PoolStr = diposits.substring(diposits.indexOf(esms[i]) + esms[i].length() + 1, diposits.indexOf(esms[i + 1]) - 1); // az jaei ke esme[i] hast ta esme badi yani esme[i+1] mishavad poole poole farde i
			else if(i == (TedadeSepordegozar - 1)) // agar be farde akhar residim poole fard mishavad az jaei ke esm hast ta akhar
				PoolStr = diposits.substring(diposits.indexOf(esms[i]) + esms[i].length() + 1, diposits.length());		
			Pool[i]=0;
			for(int j = 0 ; j < PoolStr.length(); j++){
				switch (PoolStr.charAt(j)) {   // in switch baraye mohasebeye poole har fard be soorate adad
				case 'I':
					Pool[i] = Pool[i] + 1;
					break;
				case 'V':
					Pool[i] = Pool[i] + 5;
					break;
				case 'X':
					Pool[i] = Pool[i] + 10;
					break;
				case 'L':
					Pool[i] = Pool[i] + 50;
					break;
				case 'C':
					Pool[i] = Pool[i] + 100;
					break;
				case 'D':
					Pool[i] = Pool[i] + 500;
					break;
				case 'M':
					Pool[i] = Pool[i] + 1000;
					break;
				case ' ':
					Pool[i] = Pool[i] + 0;
					break;
				default:
					break;
				}
			}
		}
		return Pool;
	}
	/**
	 * in tabe Poole sanavie ra ba estefade az sood va tedade sal mohasebe mikonad
	 * @param Pool
	 * @param Sood
	 * @param TedadeSal
	 * @return
	 */
	static float PoolSanaviyeh(float Pool, float Sood, int TedadeSal){
		float PS = Pool;
		for (int i = 1; i <= TedadeSal; i++) {
			PS = PS + (PS * (Sood / 100));
		}
		return PS;
	}
	/**
	 * in tabe Pool ha va esm ha ra az bozorg be koochak morattab mikonad
	 * @param esms
	 * @param Pools
	 */
	static void Sort(String[] esms, float[] Pools){ //bubble sort
		for (int i = 0; i < Pools.length; i++) {
			for (int j = Pools.length - 1; j > i; j--) {
				if (Pools[j] > Pools[j - 1]){
					float temp = Pools[j];
					Pools[j] = Pools[j - 1];
					Pools[j - 1] = temp;				
					String Stemp = esms[j];
					esms[j] = esms[j - 1];
					esms[j - 1] = Stemp;
				}
			}
		}
	}
	static void process(String initialData,String diposits){
		String[] initial = initialData.split(" "); //initial[0]=sepordegozar , initial[1]=bank , initial[2]=darsad , initial[3]=sal
		String[] esms = Esms(diposits, Integer.parseInt(initial[0]), Integer.parseInt(initial[1]));
		float[] pools = Seporde(diposits, esms, Integer.parseInt(initial[0]));
		Sort(esms, pools);
		for (int i = 0; i < pools.length; i++) {
			System.out.println(esms[i] + " " + Math.floor(PoolSanaviyeh(pools[i], Float.parseFloat(initial[2]), Integer.parseInt(initial[3])) * 100) / 100.0);
		}
	}

	public static void main(String args[]){
		String initialData,diposits;
        Scanner scanner= new Scanner(System.in);
        initialData=scanner.nextLine();
        diposits=scanner.nextLine();
		process(initialData,diposits);
		scanner.close();
	}
}