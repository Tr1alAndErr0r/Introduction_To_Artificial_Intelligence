package Program1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class BackTrackOne{
    public ArrayList<String> moveList;
    public ArrayList<Board> listOfBoardStates;
    public Board startState;
    public Board goalState;
    public int count;
    RuleFunctions rules;

    public BackTrackOne() {
        moveList = new ArrayList<String>();
        listOfBoardStates = new ArrayList<Board>();
        startState = new Board();
        goalState = new Board();
        count = 0;
        rules = new RuleFunctions();
    }

    public BackTrackOne(Board tStartState, Board tGoalState) {
        moveList = new ArrayList<String>();
        listOfBoardStates = new ArrayList<Board>();
        startState = tStartState;
        goalState = tGoalState;
        count = 0;
        rules = new RuleFunctions();
        listOfBoardStates.add(startState);
    }

    public void addBoardState(Board added){
        listOfBoardStates.add(0,added);
    }

    public boolean doBackTrackOne(int BOUND){
        Board data = listOfBoardStates.get(0); // Line 1; gets most recent board
        if(listOfBoardStates.size() != 1) // Line 2, check if a state is revisited (cycle)
        {
            for (int i = 1; i < listOfBoardStates.size(); i++) {
                if(Arrays.deepEquals(data.currentBoard, listOfBoardStates.get(i).currentBoard)){
                    return false;
                }
            }
        }

        if((listOfBoardStates.size()) - 1 > BOUND)//Line 5; if bound exceeded
        {
            return false;
        }

        if(Arrays.deepEquals(data.currentBoard, goalState.currentBoard)) // Line 3; Check if current board is goal board
        {
            return true;
        }

        List<String> nextStates = new ArrayList<>();
        nextStates = rules.applicableRules(data); //Line 6; rules
        while(!nextStates.isEmpty()) { // while there are still rules to try
            String r = new String(nextStates.get(0)); // Line 8
            nextStates.remove(0); // Line 9
            listOfBoardStates.add(0,rules.makeMove(data, r, data.boardSize)); // Line 10 and 11; create and add to list
            moveList.add(0,r); // add to move list
            count++; // increment states examined
            boolean path = doBackTrackOne(BOUND); // Line 12; recursive call
            if(Arrays.deepEquals(listOfBoardStates.get(0).currentBoard,goalState.currentBoard) && !((listOfBoardStates.size()) - 1 > BOUND)){ //check for true state
                return true;
            }
            if(path == false){
                listOfBoardStates.remove(0); //remove board state if failed
                moveList.remove(0);
            }
            if (nextStates.isEmpty()) { // if no more rules
                return false;
            }
        }

        if(!(Arrays.deepEquals(listOfBoardStates.get(0).currentBoard,goalState.currentBoard))){ //check for true state
            return false;
        }
        return true;
    }

    public boolean findOptimalBackTrackOne(int bound){ // finds optimal solution by starting at depth 1 and then increasing depth if no solution found until specified bound
        int iBound = 1;
        boolean check = false;
        boolean checkTwo = false;
        while(check == false && checkTwo == false)
        {
            if(iBound == bound)
            {
                checkTwo = true;
            }
            moveList.clear();
            listOfBoardStates.clear();
            listOfBoardStates.add(startState);
            check = doBackTrackOne(iBound);
            if(check == false)
            {
                iBound++;
            }
        }
        if(check) {
            return true;
        }
        return false;
    }

    public void printBackTrackOne(){
        System.out.println("Start State:");
        for (int i = 0; i < startState.boardSize; i++)
        {
            for(int j = 0; j < startState.boardSize; j++)
            {
                if(j==0){
                    System.out.print("[");
                }
                System.out.print(startState.currentBoard[i][j]);
                if(j != startState.boardSize - 1){
                    System.out.print(",");
                }
                if(j==goalState.boardSize-1)
                {
                    System.out.print("]");
                }
            }
            System.out.println();
        }
        System.out.println();
        System.out.println("Goal State:");
        for (int i = 0; i < goalState.boardSize; i++)
        {
            for(int j = 0; j < goalState.boardSize; j++)
            {
                if(j==0){
                    System.out.print("[");
                }
                System.out.print(goalState.currentBoard[i][j]);
                if(j != goalState.boardSize - 1){
                    System.out.print(",");
                }
                if(j==goalState.boardSize-1)
                {
                    System.out.print("]");
                }
            }
            if( i!= goalState.boardSize-1)
            System.out.println();
        }
        System.out.println();
        System.out.println();
        System.out.printf("States Examined: %s", count);
        System.out.println();
        System.out.printf("Move List: %s moves", moveList.size());
        System.out.println();
        Collections.reverse(moveList);
        System.out.println(moveList);
    }
}
