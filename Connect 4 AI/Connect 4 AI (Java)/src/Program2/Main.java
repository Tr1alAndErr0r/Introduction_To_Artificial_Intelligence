package Program2;

import com.google.gson.*;
import java.io.*;
import java.util.Scanner;


public class Main {

    public static Board readIn(String inString){ //convert json to board
        JsonObject jsonObject = new JsonParser().parse(inString).getAsJsonObject();
        Board inputJson = new Gson().fromJson(jsonObject, Board.class);
        return inputJson;
    }


    public static void main(String[] args) throws IOException {
        Scanner scan = new Scanner(System.in);
        PrintWriter outStream = new PrintWriter(System.out);
        PrintWriter outErrStream = new PrintWriter(System.err);
        Actions gameActions = new Actions();
        AlphaBetaSearch absObject = new AlphaBetaSearch();
        int counter = 0;
        int thisMove = 0;

        while(true) {
            if (scan.hasNextLine()) // read in from standard input
            {
                String inputLine = scan.nextLine();
                Board inputJson = readIn(inputLine);
                if(counter != 0) // if not first move then do min max, otherwise go middle
                {
                    absObject.performAlphaBeta(inputJson);
                    thisMove = absObject.bestMove;
                }
                else{
                    thisMove = (inputJson.width/2);
                }
                outStream.println(gameActions.makeTheMoveInJson(thisMove)); // send out move
                outStream.flush();
                outErrStream.println(gameActions.makeTheMoveInJson(thisMove));
                outErrStream.flush();
                counter++;

            }
        }
    }
}
