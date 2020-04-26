package test;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Formatter;
import java.util.Random;
import java.util.Scanner;
import java.math.BigDecimal;

public class Hangman {
	/**
	 * jomleye mask ra tolid mikonad
	 * @param sentence
	 * @return
	 */
	public static String mask(String sentence){
		String mask = "";
		for(int i = 0; i < sentence.length(); i++){
			if(sentence.charAt(i) == ' ')
				mask = mask + " ";
			else
				mask = mask + "_";
		}
		return mask;
	}
	
	/**
	 * in tabe barresi mikonad bazi tamam shode ast ya na
	 * @param score
	 * @param mask
	 * @return
	 */
	public static boolean gameOver(int score, String mask){
		if(score == 0 || mask.indexOf("_") == -1)
			return true;
		return false;
	}
	
	/**
	 * aya inout wrong ast ya na
	 * @param input
	 * @return
	 */
	public static boolean wrongInput(String input){
		input = input.toLowerCase();
		if(input.length() > 1 || input.length() < 1)
			return true;
		else if(input.charAt(0) > 'z' || input.charAt(0) < 'a')
			return true;
		return false;
	}
	/**
	 * aya inout dorost ast ya na
	 * @param input
	 * @param sentence
	 * @return
	 */
	public static boolean rightGuess(String input, String sentence){
		if(sentence.toLowerCase().indexOf(input.toLowerCase()) != -1 )
			return true;
		return false;
	}
	/**
	 * kar haye moghe inoute dorost
	 * @param input
	 * @param sentence
	 * @param mask
	 * @return
	 */
	public static String rightGuessWorks(String input, String sentence, String mask){
		System.out.println("Right guess^_^");
		for(int i = 0; i < sentence.length(); i++){
			if(sentence.toLowerCase().charAt(i) == input.toLowerCase().charAt(0))
				mask = mask.substring(0, i) + sentence.charAt(i) + mask.substring(i + 1);
		}
		return mask;
	}
	/**
	 * kar haye moghe inoute wrong
	 */
	public static void wrongInputWorks(){
		System.out.println("Wrong input!!");
	}
	/**
	 * mohasebeye final score 
	 * @param score
	 * @param time
	 * @return
	 */
	public static float finalScoreCal(float score, long time){
		return  (float)((float)(score * 1000) / (Math.sqrt(Math.log(time) / Math.log(2))));
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public static void main(String[] args) throws FileNotFoundException {
		String[] sentences = {"Only those who will risk going too far can possibly find out how far one can go",
				"The man who moves a mountain begins by carrying away small stones",
				"Be the change that you wish to see in the world",
				"In the end its not going to matter how many breaths you took",
				"but how many moments took your breath away",
				"When life gives you a hundred reasons to cry",
				"show life that you have a thousand reasons to smile",
				"Be who you are and say what you feel",
				"because those who mind dont matter",
				"those who matter dont mind",
				"All our dreams can come true if we have the courage to pursue them",
				"Success usually comes to those who are too busy to be looking for it",
				"Things work out best for those who make the best of how things work out",
				"Our deepest fear is not that we are inadequate"
				};
		File file = new File("saveGame.txt");
		Formatter f;
		BigDecimal bd; //baraye 2 ragham aashar
		Random rand = new Random();
		ArrayList<Person> playerArray = new ArrayList<Person>(); //listi az player ha
		if(!file.exists())
			f = new Formatter(file);
		Scanner sc = new Scanner(file);
		while(sc.hasNextLine()){ //khandan az file
			String[] str = sc.nextLine().split(" ");
			Person p = new Person(str[0],Float.parseFloat(str[1]));
			playerArray.add(p);
		}
		sc.close();
		System.out.println("Welcome to Hangman");
		Scanner reader = new Scanner(System.in);
		String endQuestion = "yes";
		
		
		
		
		do {
			int score = 6;
			String sentence = sentences[rand.nextInt(sentences.length)]; //random
			String name;
			String wrongGuessed = "";
			String mask = "";
			String input;
			mask = mask(sentence);
			
			
			
			System.out.print("Enter your name : ");
			name = reader.nextLine();
			Person player = new Person(name);
			if(playerArray.contains(player)){ //agar esm tekrari bud
				System.out.print("this name has been entered before! (yes) to continue and override the score (no) to change name: ");
				String answer = reader.nextLine();
				if(answer.equals("no")){
					do {
						System.out.print("please enter another name: ");
						name = reader.nextLine();
					} while (playerArray.contains(new Person(name)));
					player = new Person(name);
				}
				if(answer.equals("yes"))
					playerArray.remove(player); //ghabli pak mishe
			}
			player.setName(name);
			
			
			
			
			System.out.println("Game is started : ");
			long time = System.currentTimeMillis();
			
			
			while(!gameOver(score, mask)){
				System.out.println("Score : " + score);
				
				System.out.println("Wrong guessed : " + ((wrongGuessed.equals(""))?"none":wrongGuessed)); //agar  wrong guess khali bud = none
				
				System.out.println("Sentence : " + mask);
				System.out.print("Enter your guess : ");
				input = reader.nextLine();
				
				if(wrongInput(input))
					wrongInputWorks();
				else if(mask.toLowerCase().indexOf(input.toLowerCase()) != -1 || wrongGuessed.toLowerCase().indexOf(input.toLowerCase()) != -1)
					System.out.println("You have entered " + input + " before."); //input tekrari
				else if(rightGuess(input, sentence))
					mask = rightGuessWorks(input, sentence, mask);
				else{ //wrong guess
					System.out.println("Wrong guess-_-");
					score--;
					wrongGuessed = wrongGuessed + ((wrongGuessed.equals(""))? "":",") + input;
				}
				System.out.println("\n\n\n");
			}
			time = System.currentTimeMillis() - time;
			
			
			
			if(score == 0)
				System.out.println("Game Over! you lose-_-");
			else if(mask.indexOf("_") == -1){ //agar jomle kamel beshe (barande shodan) 
				System.out.println("Sentence : " + mask);
				System.out.println("Congratulations you are the winner^_^");
				System.out.println("Time : " + (int)(time / 1000) + "s");
				player.setScore(finalScoreCal(score, (long)(time / 1000)));
				playerArray.add(player); 
			}
			
			f = new Formatter(file);
			
			Collections.sort(playerArray);
			System.out.println("Ranking : \n\n");
			if(playerArray.size() == 0)
				System.out.println("ranking is empty");
			else{
				for (Person person : playerArray) {
					bd = new BigDecimal(person.getScore());
					bd = bd.setScale(2, BigDecimal.ROUND_FLOOR);
					f.format("%s %s", person.getName(), bd + "\r\n"); //neveshtan ru file
					System.out.println(person.getName() + " " + bd);
				}
			}
			
			System.out.print("do u want to play again? yes or no: ");
			endQuestion = reader.nextLine();
			
		} while (endQuestion.toLowerCase().equals("yes"));
		f.close();
		reader.close();
	}

}
