package Program2;

import com.google.gson.Gson;
import java.util.ArrayList;

public class Actions{
    public ArrayList<Integer> validMovesList(Board currentBoard){ // generate list of valid moves from board
        ArrayList<Integer> returnList = new ArrayList<Integer>();
        for(int i = 0; i < currentBoard.width; i++)
        {
            if(currentBoard.grid[i][0] == 0){
                returnList.add(i);
            }
        }
        return returnList;
    }

    public Board makeTheMoveAndReturnBoard(int tMove, Board currentBoard, boolean myPlayer) // return a successor board given move
    {
        Board successorBoard = new Board(currentBoard);
        int tPlayer = 0;
        if(myPlayer == true)
        {
            tPlayer = currentBoard.player;
        }
        else{
            if(currentBoard.player == 1)
            {
                tPlayer = 2;
            }
            else
            {
                tPlayer = 1;
            }
        }

        for(int i = (successorBoard.height-1); i > -1; i--)
        {
            if(successorBoard.grid[tMove][i] == 0)
            {
                successorBoard.grid[tMove][i] = tPlayer;
                break;
            }
        }
        return successorBoard;
    }

    public String makeTheMoveInJson(int tMove){ // wrap move in json
        MakeMoveToJson moveOb = new MakeMoveToJson();
        moveOb.move = tMove;
        String sentMove = new Gson().toJson(moveOb);
        return sentMove;
    }

    public int checkIfWon(Board tBoard){ // check if winner
        int returnCheck = 0;
        for(int y = (tBoard.height-1); y > -1; y--) // check for horizontal win
        {
            for(int x = 0; x < (tBoard.width-3); x++)
            {
                if((tBoard.grid[x][y] == tBoard.grid[x+1][y]) && (tBoard.grid[x+1][y] == tBoard.grid[x+2][y]) && (tBoard.grid[x+2][y] == tBoard.grid[x+3][y]))
                {
                    returnCheck = tBoard.grid[x][y];
                    if(returnCheck == 0)
                    {
                        continue;
                    }
                    else{
                        return returnCheck;
                    }
                }
            }
        }

        for(int x = 0; x < tBoard.width; x++) // check for vertical win
        {
            for(int y = 0; y < (tBoard.height-3); y++)
            {
                if((tBoard.grid[x][y] == tBoard.grid[x][y+1]) && (tBoard.grid[x][y+1] == tBoard.grid[x][y+2]) && (tBoard.grid[x][y+2] == tBoard.grid[x][y+3]))
                {
                    returnCheck = tBoard.grid[x][y];
                    if(returnCheck == 0)
                    {
                        continue;
                    }
                    else{
                        return returnCheck;
                    }
                }
            }
        }

        for (int x = 0; x < (tBoard.width-3); x++) // check for diagonal win #1
        {
           for(int y = 0; y < (tBoard.height-3); y++)
           {
               if((tBoard.grid[x][y] == tBoard.grid[x+1][y+1]) && (tBoard.grid[x+1][y+1] == tBoard.grid[x+2][y+2]) && (tBoard.grid[x+2][y+2] == tBoard.grid[x+3][y+3]))
               {
                   returnCheck = tBoard.grid[x][y];
                   if(returnCheck == 0)
                   {
                       continue;
                   }
                   else{
                       return returnCheck;
                   }
               }
           }
        }

        for (int x = 0; x < (tBoard.width-3); x++) // check for diagonal win #2
        {
            for(int y = 3; y < tBoard.height; y++)
            {
                if((tBoard.grid[x][y] == tBoard.grid[x+1][y-1]) && (tBoard.grid[x+1][y-1] == tBoard.grid[x+2][y-2]) && (tBoard.grid[x+2][y-2] == tBoard.grid[x+3][y-3]))
                {
                    returnCheck = tBoard.grid[x][y];
                    if(returnCheck == 0)
                    {
                        continue;
                    }
                    else{
                        return returnCheck;
                    }
                }
            }
        }
        return returnCheck;
    }
}
