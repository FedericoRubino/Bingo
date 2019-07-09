/*
  Federico Rubino
  frubino
  federico.rubino8@gmail.com
  Bingo.java
  Assignment#4 Bingo
  working/ tested 
*/

import java.util.Scanner;
import java.util.ArrayList;
/*
  This program finds six or seven letter words that can be made from a set of letters.
  This program will feature the interaction of two objects. The Bingo object
  will manage the input and output and will utilize a Dictionary object.
*/

public class Bingo{
    
    public Dictionary dictionary;
    
    /*
      function parse
      search args for specific flags, and returns the next string, which will be either
      a file name or a guess(String)
    */
    public static String parse(String [] args, String flag){
	if( args.length >= 2 && args[0].equals(flag)){
	    return args[1];
	} else if(args.length >= 4 && args[2].equals(flag)){
	    return args[3];
	} else if(flag.equals("-f")){
	    return "unixdict.txt";
	} else {
	    return "NO_GUESS";
	}
    }
    
    /*
      Bingo constructor	that passes the filename to the Dictionary class
     */
    public Bingo(String filename) {
	dictionary = new Dictionary(filename);
    }
    
    /*
      function play
      finds anagrams of guess, by utalising the dictionary class
      finds the matches and outputs the results
      In case it doesn't find matching 7 letter words it will use findPossibleSix,
      to find possible 6 letter anagrams
    */
    public void play(String guess) {
	ArrayList<String> foundWords;
	foundWords = dictionary.find(guess);
	int lengthOfGuess = guess.length();
	System.out.printf("looking for anagrams of \"%s\" ...%n" , guess);
	if(foundWords == null){
	    foundWords = new ArrayList<String>();
	    findPossibleSix(foundWords, guess);
	    lengthOfGuess = 6;
	}
	if(foundWords.size() > 0){
	    System.out.printf("%d letters: [", lengthOfGuess);
	    for(int i = 0; i < foundWords.size() - 1; i++){
		System.out.printf("%s, ", foundWords.get(i));
	    }
	    System.out.printf("%s]%n", foundWords.get(foundWords.size() - 1));
	} else {
	    System.out.println("No six or seven letter words found.");
	}
    }
    
    /*
     if there are no seven letter anagrams for our guess
     findPossibleSix will search for six letter anagrams
    */
    public void findPossibleSix (ArrayList<String> list, String s){
	ArrayList<String> temp = new ArrayList<>();
	String[] tempString = new String[7];
	String aStr;
	String bStr;
	tempString[0] = s.substring(0,6);
	tempString[6] = s.substring(1,7);
	for(int i = 1; i < 6 ; i++){
	    aStr = s.substring(0, 6-i);
	    bStr = s.substring(7-i, 7);
	    tempString[i] = aStr + bStr;
	}
	for(int j = 0; j < tempString.length; j++){
	    temp = dictionary.find(tempString[j]);
	    for(int k = 0; temp != null && k < temp.size(); k++){
		if(!(list.contains(temp.get(k)))){
		    list.add(temp.get(k));
		}
	    }
	}
    }
    
    // formats the input into a string of all lowercase letters
    public static String format(String input){
	String transformedString = ""; 
	char c; 
	int sLength = input.length( );
	for (int i = 0; i < sLength; i++){
	    c = input.charAt(i);
	    if (Character.isLetter(c)) {
		transformedString += c;
	    }
	}
	return transformedString.toLowerCase();
    }
    
    /*
      main
      takes in input, in the case that there was no "-g" flag in args
      constructs a Bingo Object, then uses the Play function to print
      out anagrams, incase of invalid input in either args or STDIN
      it will close the program
     */
    public static void main(String args[]){
	Scanner input = new Scanner(System.in);
	String filename = parse(args,"-f");
	String temp;
	String guess = parse(args, "-g");
	if(guess == "NO_GUESS"){
	    System.out.print("Your Letters? ");
	    temp = input.nextLine();
	    guess = format(temp);
	}
	if(guess.length() != 7){
	    System.err.println("not a 7 letter guess!!");
	    System.exit(0);
	}
	Bingo b = new Bingo(filename);
	b.play(guess);
    }
}

