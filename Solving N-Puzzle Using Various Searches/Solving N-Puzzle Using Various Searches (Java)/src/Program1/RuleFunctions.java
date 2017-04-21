package Program1;

import java.util.ArrayList;
import java.util.List;

public class RuleFunctions{ // class for rules
    public boolean checkIfLeft(Board currentBoard){
        if(currentBoard.currentHoleColumn != 0){
            return true;
        }
        else{
            return false;
        }
    }
    public boolean checkIfRight(Board currentBoard){
        if(currentBoard.currentHoleColumn != ((currentBoard.boardSize)-1)){
            return true;
        }
        else{
            return false;
        }
    }
    public boolean checkIfUp(Board currentBoard){
        if(currentBoard.currentHoleRow != 0){
            return true;
        }
        else{
            return false;
        }
    }
    public boolean checkIfDown(Board currentBoard){
        if(currentBoard.currentHoleRow != ((currentBoard.boardSize)-1)){
            return true;
        }
        else{
            return false;
        }
    }

    public List<String> applicableRules(Board currentBoard){ // return list of possible moves
        List<String> returnList = new ArrayList<String>();
        if(checkIfLeft(currentBoard)){
            returnList.add("LEFT");
        }
        if (checkIfRight(currentBoard)){
            returnList.add("RIGHT");
        }
        if(checkIfUp(currentBoard)){
            returnList.add("UP");
        }
        if (checkIfDown(currentBoard)){
            returnList.add("DOWN");
        }
        return returnList;
    }

    public Board makeMove(Board inputBoard, String currentAction, int boardSize){ //return successor board
        int temp = 0;
        Board successorBoard = new Board(inputBoard);
        switch (currentAction){
            case "LEFT":
                temp = successorBoard.currentBoard[successorBoard.currentHoleRow][((successorBoard.currentHoleColumn)-1)];
                successorBoard.currentBoard[successorBoard.currentHoleRow][successorBoard.currentHoleColumn] = temp;
                successorBoard.currentBoard[successorBoard.currentHoleRow][((successorBoard.currentHoleColumn)-1)] = 0;
                successorBoard.currentHoleColumn--;
                break;
            case "RIGHT":
                temp = successorBoard.currentBoard[successorBoard.currentHoleRow][((successorBoard.currentHoleColumn)+1)];
                successorBoard.currentBoard[successorBoard.currentHoleRow][successorBoard.currentHoleColumn] = temp;
                successorBoard.currentBoard[successorBoard.currentHoleRow][((successorBoard.currentHoleColumn)+1)] = 0;
                successorBoard.currentHoleColumn++;
                break;
            case "DOWN":
                temp = successorBoard.currentBoard[((successorBoard.currentHoleRow)+1)][successorBoard.currentHoleColumn];
                successorBoard.currentBoard[successorBoard.currentHoleRow][successorBoard.currentHoleColumn] = temp;
                successorBoard.currentBoard[((successorBoard.currentHoleRow)+1)][successorBoard.currentHoleColumn] = 0;
                successorBoard.currentHoleRow++;
                break;
            case "UP":
                temp = successorBoard.currentBoard[((successorBoard.currentHoleRow)-1)][successorBoard.currentHoleColumn];
                successorBoard.currentBoard[successorBoard.currentHoleRow][successorBoard.currentHoleColumn] = temp;
                successorBoard.currentBoard[((successorBoard.currentHoleRow)-1)][successorBoard.currentHoleColumn] = 0;
                successorBoard.currentHoleRow--;
                break;
        }
        return successorBoard;
    }
}
