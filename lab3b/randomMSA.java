// CSCI474 wwu.edu, Filip Jagodzinski
// File : randomMSA.java
// Description : pseudorandom MSA 

// import necessary functionality
import java.util.Random;
import java.io.*;

// randomMSA class
public class randomMSA{

    // class fields
    private static String[][] sequencesArray;
    private static int msaWidth = 0;
    private static int numSequences = 0;
    private static int numArguments = 0;

    // main routine
    public static void main(String args[]) {
	
	// set count of arguments received
	numArguments = args.length;
	if (numArguments < 2){
	    System.out.println("Two or more sequences are needed. Quitting");
	    System.exit(0);
	}
	
	// set number of sequences
	numSequences = numArguments;

	// set the MSA width to be twice the longest sequence
	int longestSequence = 0;
	for (int i=0; i<numSequences; i++){
	    if (args[i].length() > longestSequence){
		longestSequence = args[i].length();
	    }
	}
	msaWidth=longestSequence * 2 - 1;
	    
	// create 2-d array twice as wide as the longest sequence
	sequencesArray = new String[numSequences][msaWidth];
	
	// initialize the msa grid with all "-"s
	for (int i=0; i<numSequences; i++){
	    for (int j = 0; j < msaWidth; j++){
		sequencesArray[i][j] = "-";
	    }
	}

	// place each sequence "randomly" in a row
	Random rand = new Random();
	for (int i=0; i<numArguments; i++){
	    
	    int randStart =
		rand.nextInt(msaWidth - args[i].length() + 1);
	    
	    for (int j=0; j<args[i].length(); j++){
		sequencesArray[i][randStart + j] = args[i].substring(j,j+1);
	    }
	}

	// calculate the MSA score
	int msaScore = scoreMSA();

	// write out to file if score > 0
	if (msaScore > 0){
	    writeMSAtoFile();
	}	

	// outputthe MSA score
	System.out.println(msaScore);

    }

    // function to write the MSA grid to a file
    private static void writeMSAtoFile(){

	try {

	    File fileOut = new File("msa.txt");
	    PrintWriter writer = new PrintWriter(fileOut);

	    for (int i=0; i<numSequences; i++){
		String fileLine = "";
		for (int j=0; j<msaWidth; j++){
		    fileLine += sequencesArray[i][j];
		}
		writer.println(fileLine);
	    }

	    writer.close();

	} catch (FileNotFoundException e){

	    System.out.println("Writing to file unsuccessful");

	}
    }

    // function to score the MSA grid
    private static int scoreMSA(){

	int score = 0;
	String seqLet = "";
	boolean allAlign = true;
	for (int i=0; i<msaWidth; i++){
	    seqLet = sequencesArray[0][i];
	    allAlign = true;
	    for (int j=1; j<numSequences; j++){
		if (! seqLet.equals(sequencesArray[j][i])){
		    j = numSequences;
		    allAlign = false;
		}
	    }

	    // if a column has all matching S or R, +2, else -1
	    if (allAlign && ! seqLet.equals("-")){
		score += 2;
	    }else if (allAlign && seqLet.equals("-")){
		// all values in column are - which is neither
		// a penalty or reward
		score = score;
	    }else{
		score -= 1;
	    }
	}

	return score;
    }

    // function for debugging; print the MSA
    private static void printMSA(){

	String row = "";
	for (int i=0; i<sequencesArray.length; i++){
	    for (int j=0; j<sequencesArray[i].length; j++){
		row += sequencesArray[i][j];
	    }
	    System.out.println(row);
	    row = "";
	}
    }    
}
