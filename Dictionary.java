/*
  Federico Rubino
  frubino
  federico.rubino8@gmail.com
  Dictionary.java
  Assignment#4 Bingo
*/

import java.util.Scanner;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.io.FileNotFoundException;
/*
  The Dictionary object supports the word list. To find candidate words,
  the basic technique used is to sort the letters in the word so that
  they are in alphabetical order to facilitate comparison
*/

public class Dictionary{

    private HashMap<String, ArrayList<String>> dictionary;

    /*
      Dictionary Constructor
      fills the hash map dictionary
      with 6 and 7 letter words from the given file
      the key will be the alphabeticaly ordered anagram (String) 
      the value will be an ArrayList with all the angrams of that word
      found in the file
    */
    public Dictionary(String filename){
	dictionary = new HashMap<>();
	String temp;
	try{
	    Scanner fl = new Scanner (new File(filename));
	    while(fl.hasNextLine()){
		temp = fl.nextLine();
		if (temp.length() == 6 || temp.length() == 7) {
		    String sortedTemp = sortLetters(temp);
		    if(dictionary.containsKey(sortedTemp)){
			dictionary.get(sortedTemp).add(temp);
		    } else {
			ArrayList<String> words = new ArrayList<>();
			words.add(temp);
			dictionary.put(sortedTemp, words);
		    }
		}
	    }
	} catch (FileNotFoundException e){
	    System.err.println(filename+ ": File not found!");
	}
    }
    
    /*
      find function
      finds the list of Strings that are anagrams of the given String
      by simply using the .get() method of the Hashmap Api
    */
    public ArrayList<String> find(String s) {
	return dictionary.get(sortLetters(s));
    }
    
    /*
      add function
      add s to the dictionary HashMap
      uses the same code as in the constructor
    */
    public void add(String s){
	String sortedWord = sortLetters(s);
	if(dictionary.containsKey(sortedWord)){
	    dictionary.get(sortedWord).add(s);
	} else {
	    ArrayList<String> words = new ArrayList<>();
	    words.add(s);
	    dictionary.put(sortedWord, words);
	}
    }
    
    /*
      sortLetters function
      using a bubble sorting algorithem to sort the letters 
    */
    public static String sortLetters(String s){
	char[] letters = new char[s.length()];
	for(int i = 0; i < s.length(); i++){
	    letters[i] = s.charAt(i);
	} 
	char temp;
	for(int i = 0; i < s.length() - 1; i++){ 
	    for(int j = 0; j < s.length() - 1; j++){ 
		if(letters[j] > letters[j + 1]){
		    temp = letters[j];
		    letters[j] = letters[j+1];
		    letters[j+1] = temp;
		}
	    }
	}
	return new String(letters);
    }
}

