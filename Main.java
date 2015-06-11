/*
 * TITLE: Wordament Solver for regular level (includes speed round). Not for Digram (2 joint letters on a tile)
 * AUTHOR: Akshay Utture
 * DESCRIPTION: Given a wordament board, it prints out a set of possible words.
 * INPUT SPECIFICATION: Enter each row as a single joint word
 * HOW TO RUN: Run on a Javac compiler or in a IDE like Eclipse or NetBeans. 
 * NOTE: Set the minLenght variable to specify the minimum length of the words that are to be printed.
 */

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;


public class Main {
	//Holds the complete set of words that we are going to try to search in. It is array of 26 sub arrays.
	//Each sub array holds all words from the file 'a.txt' starting with a specific letter
	public static ArrayList<AlphabetList> arr=new ArrayList<AlphabetList>();
	//the wordament board
	public static char[][] board= new char[4][4];
	//minimum length of the words that are to be printed
	public static int minLength=4;
	
	public static void main(String[] args) throws IOException{
		
		//Initialization
		Scanner sc=new Scanner(System.in);
		int i,j,k;
		for (i=0;i<26;i++){
			arr.add(new AlphabetList());
		}
		i=-1;
		AlphabetList temp=null;
		String line;
		//Reading the file into the array 'arr'. The desciption of 'arr' is provided at its declaration above
		try {
				FileReader f = new FileReader("a.txt");
				BufferedReader bf = new BufferedReader(f);
				while((line = bf.readLine()) != null) {
					if (line.equals("-")){
						i++;
						temp=arr.get(i);
						temp.letter=(char)(i+97);
					}
					else{
						temp.add(line);
					}					
		        }   
				bf.close(); 
				f.close();
	        }
			//catch statement for the read 
		catch(FileNotFoundException ex) {
	            System.out.println("Unable to open file");                    
	    }
		
		//reading the board tiles
		for (i=0;i<4;i++){
			line=sc.next();
			for (j=0;j<4;j++){
				board[i][j]=line.charAt(j);
			}
		}
		
		// calling findWord on each of the 16 tiles on the Wordament board
		for (i=0;i<4;i++){
			for (j=0;j<4;j++){
				findWord("",0,i,j,new boolean[4][4],arr.get((int)board[i][j] - 97));
			}
		}
	}
	
	//recursive find word - A DEPTH FIRST SEARCH, WHERE AT EVERY POINT WE TRY TO MATCH THE CURRENT WORD FORMED BY THE PROGRAM
	/*FORMAT 
	 *currentWord - the word that has been formed by the program so far
	 *wordLength - lenght of the currentWord
	 *row,col - the position of the tile, whose letter we will now add to the currentWord
	 *used - tells us if a tile has been used in making the currentWord. (since we cannot repeat tiles)
	 *a - holds the list in which we should search for words.(If the starting tile is 's', 
	 *		we should only search in the ArrayList for words starting with 's'
	 */
	public static void findWord(String currentWord,int wordLength,int row,int col,boolean used[][],AlphabetList a){
		//base condition for the recursive function
		if (wordLength>15){
			return;
		}
		//set the prospective tile to used, append the currentWord variable with that tile
		used[row][col]=true;
		currentWord+=Character.toString(board[row][col]);
		wordLength++;
		
		//If currentWord is there in the relevant list, print it
		if (a.contains(currentWord)){
			if  (wordLength>=minLength){
				System.out.println(currentWord);
			}	
		}
		//recursively call the function on surrounding tiles (those not already used in currentWord) to make longer words.
		for(int i=-1;i<2;i++){
			for (int j=-1;j<2;j++){
				if (row+i>-1 && row+i<4 && col+j>-1 && col+j<4 && !(i==0 && j==0)){
					if (!used[row+i][col+j]){
						findWord(currentWord,wordLength,row+i,col+j,used,a);
					}
					
				}
			}
		}
		//while coming out of the recursive call, remove the last letter from the word
		used[row][col]=false;
		wordLength--;
		currentWord=currentWord.substring(0, wordLength);
		
	}
}

class AlphabetList extends ArrayList<String>{
	char letter;
}

