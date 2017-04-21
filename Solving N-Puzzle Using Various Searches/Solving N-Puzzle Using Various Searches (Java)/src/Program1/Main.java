package Program1;

import java.io.*;
import java.lang.reflect.Array;
import java.util.*;
import com.google.gson.*;

public class Main {

    public class InputJsonDeserialize { // parsed into java object
        public int n;
        public int start[][];
        public int goal[][];
    }

    public static InputJsonDeserialize jsonChecks(){
        JsonObject jsonObject = null;
        try {
            jsonObject = new JsonParser().parse(new FileReader("1-move.json")).getAsJsonObject(); // select which file to run
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        if(!(jsonObject.has("n")) || !(jsonObject.has("start")) || !(jsonObject.has("goal"))){ //check, json must have required fields
            System.out.println("Invalid fields");
            System.exit(0);
        }

        InputJsonDeserialize inputJson = new Gson().fromJson(jsonObject, InputJsonDeserialize.class);
        if(inputJson.n < 1){ // check, n must be greater than 1
            System.out.println("n must be greater than 1");
            System.exit(0);
        }

        for(int i = 0; i < inputJson.n; i++) // check, integers must in range of 0 to n^2-1
        {
            for(int j = 0; j < inputJson.n; j++)
            {
                if(inputJson.start[i][j] > ((inputJson.n * inputJson.n) - 1) || inputJson.goal[i][j] > ((inputJson.n * inputJson.n) - 1) || inputJson.start[i][j] < 0 || inputJson.goal[i][j] < 0){
                    System.out.println("Integer in start/goal field must be in range of 0 to (n^2)-1");
                    System.exit(0);
                }
            }
        }
        return inputJson;
    }

    public static void main(String[] args) {
        InputJsonDeserialize inputJson = jsonChecks(); // Json checks

        Board startState = new Board(inputJson.n); // Create start board
        startState.copyStateJson(inputJson.start);
        startState.findHole();

        Board goalState = new Board(inputJson.n); // Create goal board
        goalState.copyStateJson(inputJson.goal);
        goalState.findHole();


        // To run backtracking
        BackTrackOne backTrackOneObject = new BackTrackOne(startState,goalState);
        int bound = 31; // set bound
        backTrackOneObject.doBackTrackOne(bound); // do recursive backtracking with a set bound
        backTrackOneObject.printBackTrackOne(); // print out backtrack results
        // backTrackOneObject.findOptimalBackTrackOne(bound); // do iterative-deepening dfs with a set limit (bound), finds optimal moves
        // System.out.println();
        // backTrackOneObject.printBackTrackOne(); // prints out results


        /*
        //To run graph search BFS
        GraphSearch graphSearchObject = new GraphSearch(startState, goalState);
        graphSearchObject.doGraphSearchBFS();
        graphSearchObject.printGraphSearch();
        */

        /*
        //To run A* graph search for heuristic one
        GraphSearch graphSearchObjectTwo = new GraphSearch(startState,goalState);
        graphSearchObjectTwo.doAStarHeuristicOne();
        graphSearchObjectTwo.printGraphSearch();
        */

        /*
        //To run A* graph search for heuristic two
        GraphSearch graphSearchObjectTwo = new GraphSearch(startState,goalState);
        graphSearchObjectTwo.doAStarHeuristicTwo();
        graphSearchObjectTwo.printGraphSearch();
        */
    }
}
