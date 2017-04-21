package Program2;

import java.util.ArrayList;

public class AlphaBetaSearch{
    Actions actionsObject;
    int depthVal;
    int bestMove;

    AlphaBetaSearch()
    {
        actionsObject = new Actions();
        depthVal = 8; // set depth value here
        bestMove = 0;
    }


    public void performAlphaBeta(Board currentBoard){
        ArrayList<Integer> immediateMoves = actionsObject.validMovesList(currentBoard);
        for(int move:immediateMoves) // check for immediate win
        {
            if(actionsObject.checkIfWon(actionsObject.makeTheMoveAndReturnBoard(move, currentBoard, true)) != 0)
            {
                bestMove = move;
                return;
            }
        }

        for(int move:immediateMoves) // check for immediate block
        {
            if(actionsObject.checkIfWon(actionsObject.makeTheMoveAndReturnBoard(move, currentBoard, false)) != 0)
            {
                bestMove = move;
                return;
            }
        }

        int depth = depthVal;
        int alpha = Integer.MIN_VALUE;
        int beta = Integer.MAX_VALUE;
        boolean player = true;
        alphaBeta(currentBoard, depth, alpha, beta, player); // alpha beta, stores best move
    }

    // mini-max with alpha beta pruning
    public int alphaBeta(Board currentBoard, int depth, int alpha, int beta, boolean whichPlayer){
        int checkWhoWon = actionsObject.checkIfWon(currentBoard); // check if won
        boolean checkIfNoMoreMoves = actionsObject.validMovesList(currentBoard).isEmpty(); // check if end of board
        if (depth == 0 || checkWhoWon != 0 || checkIfNoMoreMoves == true)
        {
            return evaluationFunction(currentBoard, checkWhoWon, checkIfNoMoreMoves, whichPlayer); // return evaluation value
        }

        ArrayList<Integer> possibleMoves = actionsObject.validMovesList(currentBoard); // list of possible moves
        if(whichPlayer == true){ // maximize
            for(int move : possibleMoves)
            {
                Board tempBoard = actionsObject.makeTheMoveAndReturnBoard(move, currentBoard, true); // generate new board state
                int result = alphaBeta(tempBoard,depth-1, alpha, beta, false);
                if(result > alpha)
                {
                    alpha = result;
                    if (depth == depthVal) {
                        bestMove = move; // to hold best move
                    }
                }
                if(beta <= alpha)
                {
                    break;
                }
            }
            return alpha;
        }
        else{ // minimize
            for(int move : possibleMoves)
            {
                Board tempBoard = actionsObject.makeTheMoveAndReturnBoard(move, currentBoard, false);
                int result = alphaBeta(tempBoard,depth-1, alpha, beta, true);
                if(result < beta)
                {
                    beta = result;
                    if (depth == depthVal) {
                        bestMove = move;
                    }
                }
                if(beta <= alpha)
                {
                    break;
                }
            }
            return beta;
        }
    }

    // to compute scores
    public int evaluationFunction(Board tBoard, int thereIsWinner, boolean checkIfNoMoreMoves, boolean whichPlayer){
        if(thereIsWinner != 0 )
        {
            if((thereIsWinner == 1 && tBoard.player == 1) || (thereIsWinner == 2 && tBoard.player == 2))
            {
                return 512; // if win by player
            }
            else{
                return -512; // if win by opponent
            }
        }
        else if (checkIfNoMoreMoves == true) // end in draw score 0
        {
            return 0;
        }

        int scoreForOneThrees = 0;
        int scoreForTwoThrees = 0;
        int scoreForOneTwos = 0;
        int scoreForTwoTwos = 0;
        int scoreForOneOnes = 0;
        int scoreForTwoOnes = 0;
        int finalScore = 0;
        int playerOneOrTwoCheck = 0;

        for(int y = (tBoard.height-1); y > -1; y--) // check horizontal
        {
            for(int x = 0; x < (tBoard.width-3); x++)
            {
                // continue if all 0's
                if((tBoard.grid[x][y] == 0) && (tBoard.grid[x+1][y] == 0) && (tBoard.grid[x+2][y] == 0) && (tBoard.grid[x+3][y]) == 0)
                {
                    continue;
                }

                // check horizontal 3's
                if((tBoard.grid[x][y] == tBoard.grid[x+1][y]) && (tBoard.grid[x+1][y] == tBoard.grid[x+2][y]) && ((tBoard.grid[x+3][y]) == 0))
                {
                    playerOneOrTwoCheck = tBoard.grid[x][y];
                    if (playerOneOrTwoCheck == 1){
                        scoreForOneThrees++;
                        continue;
                    }
                    else if (playerOneOrTwoCheck == 2){
                        scoreForTwoThrees++;
                        continue;
                    }
                }
                if(((tBoard.grid[x][y]) == 0) && (tBoard.grid[x+1][y] == tBoard.grid[x+2][y]) && (tBoard.grid[x+2][y] == tBoard.grid[x+3][y]))
                {
                    playerOneOrTwoCheck = tBoard.grid[x+1][y];
                    if (playerOneOrTwoCheck == 1){
                        scoreForOneThrees++;
                        continue;
                    }
                    else if (playerOneOrTwoCheck == 2){
                        scoreForTwoThrees++;
                        continue;
                    }
                }
                if((tBoard.grid[x][y] == tBoard.grid[x+2][y]) && (tBoard.grid[x+2][y] == tBoard.grid[x+3][y]) && ((tBoard.grid[x+1][y]) == 0))
                {
                    playerOneOrTwoCheck = tBoard.grid[x][y];
                    if (playerOneOrTwoCheck == 1){
                        scoreForOneThrees++;
                        continue;
                    }
                    else if (playerOneOrTwoCheck == 2){
                        scoreForTwoThrees++;
                        continue;
                    }
                }
                if((tBoard.grid[x][y] == tBoard.grid[x+1][y]) && (tBoard.grid[x+1][y] == tBoard.grid[x+3][y]) && ((tBoard.grid[x+2][y]) == 0))
                {
                    playerOneOrTwoCheck = tBoard.grid[x][y];
                    if (playerOneOrTwoCheck == 1){
                        scoreForOneThrees++;
                        continue;
                    }
                    else if (playerOneOrTwoCheck == 2){
                        scoreForTwoThrees++;
                        continue;
                    }
                }

                // check horizontal 2's
                if((tBoard.grid[x][y] == tBoard.grid[x+1][y]) && ((tBoard.grid[x+2][y]) == 0) && ((tBoard.grid[x+3][y]) == 0))
                {
                    playerOneOrTwoCheck = tBoard.grid[x][y];
                    if (playerOneOrTwoCheck == 1){
                        scoreForOneTwos++;
                        continue;
                    }
                    else if (playerOneOrTwoCheck == 2){
                        scoreForTwoTwos++;
                        continue;
                    }
                }
                if((tBoard.grid[x+1][y] == tBoard.grid[x+2][y]) && ((tBoard.grid[x][y]) == 0) && ((tBoard.grid[x+3][y]) == 0))
                {
                    playerOneOrTwoCheck = tBoard.grid[x+1][y];
                    if (playerOneOrTwoCheck == 1){
                        scoreForOneTwos++;
                        continue;
                    }
                    else if (playerOneOrTwoCheck == 2){
                        scoreForTwoTwos++;
                        continue;
                    }
                }
                if((tBoard.grid[x+2][y] == tBoard.grid[x+3][y]) && ((tBoard.grid[x][y]) == 0) && ((tBoard.grid[x+1][y]) == 0))
                {
                    playerOneOrTwoCheck = tBoard.grid[x+2][y];
                    if (playerOneOrTwoCheck == 1){
                        scoreForOneTwos++;
                        continue;
                    }
                    else if (playerOneOrTwoCheck == 2){
                        scoreForTwoTwos++;
                        continue;
                    }
                }
                if((tBoard.grid[x][y] == tBoard.grid[x+3][y]) && ((tBoard.grid[x+1][y]) == 0) && ((tBoard.grid[x+2][y]) == 0))
                {
                    playerOneOrTwoCheck = tBoard.grid[x][y];
                    if (playerOneOrTwoCheck == 1){
                        scoreForOneTwos++;
                        continue;
                    }
                    else if (playerOneOrTwoCheck == 2){
                        scoreForTwoTwos++;
                        continue;
                    }
                }

                //check for horizontal 1's
                if((tBoard.grid[x][y] == 0) && ((tBoard.grid[x+1][y]) == 0) && ((tBoard.grid[x+2][y]) == 0) && (tBoard.grid[x+3][y] != 0))
                {
                    playerOneOrTwoCheck = tBoard.grid[x+3][y];
                    if (playerOneOrTwoCheck == 1){
                        scoreForOneOnes++;
                        continue;
                    }
                    else if (playerOneOrTwoCheck == 2){
                        scoreForTwoOnes++;
                        continue;
                    }
                }
                if((tBoard.grid[x][y] == 0) && ((tBoard.grid[x+1][y]) == 0) && ((tBoard.grid[x+3][y]) == 0) && (tBoard.grid[x+2][y] != 0))
                {
                    playerOneOrTwoCheck = tBoard.grid[x+2][y];
                    if (playerOneOrTwoCheck == 1){
                        scoreForOneOnes++;
                        continue;
                    }
                    else if (playerOneOrTwoCheck == 2){
                        scoreForTwoOnes++;
                        continue;
                    }
                }
                if((tBoard.grid[x][y] == 0) && ((tBoard.grid[x+2][y]) == 0) && ((tBoard.grid[x+3][y]) == 0) && (tBoard.grid[x+1][y] != 0))
                {
                    playerOneOrTwoCheck = tBoard.grid[x+1][y];
                    if (playerOneOrTwoCheck == 1){
                        scoreForOneOnes++;
                        continue;
                    }
                    else if (playerOneOrTwoCheck == 2){
                        scoreForTwoOnes++;
                        continue;
                    }
                }
                if((tBoard.grid[x+1][y] == 0) && ((tBoard.grid[x+2][y]) == 0) && ((tBoard.grid[x+3][y]) == 0) && (tBoard.grid[x][y] != 0))
                {
                    playerOneOrTwoCheck = tBoard.grid[x][y];
                    if (playerOneOrTwoCheck == 1){
                        scoreForOneOnes++;
                        continue;
                    }
                    else if (playerOneOrTwoCheck == 2){
                        scoreForTwoOnes++;
                        continue;
                    }
                }
            }
        }

        for(int x = 0; x < tBoard.width; x++) // check vertical
        {
            for(int y = 0; y < (tBoard.height-3); y++)
            {
                // continue if all 0's
                if((tBoard.grid[x][y] == 0) && (tBoard.grid[x][y+1] == 0) && (tBoard.grid[x][y+2] == 0) && (tBoard.grid[x][y+3]) == 0)
                {
                    continue;
                }

                // check for vertical 3's
                if((tBoard.grid[x][y] == tBoard.grid[x][y+1]) && (tBoard.grid[x][y+1] == tBoard.grid[x][y+2]) && ((tBoard.grid[x][y+3]) == 0))
                {
                    playerOneOrTwoCheck = tBoard.grid[x][y];
                    if (playerOneOrTwoCheck == 1){
                        scoreForOneThrees++;
                        continue;
                    }
                    else if (playerOneOrTwoCheck == 2){
                        scoreForTwoThrees++;
                        continue;
                    }
                }
                if(((tBoard.grid[x][y]) == 0) && (tBoard.grid[x][y+1] == tBoard.grid[x][y+2]) && (tBoard.grid[x][y+2] == tBoard.grid[x][y+3]))
                {
                    playerOneOrTwoCheck = tBoard.grid[x][y+1];
                    if (playerOneOrTwoCheck == 1){
                        scoreForOneThrees++;
                        continue;
                    }
                    else if (playerOneOrTwoCheck == 2){
                        scoreForTwoThrees++;
                        continue;
                    }
                }
                if((tBoard.grid[x][y] == tBoard.grid[x][y+2]) && (tBoard.grid[x][y+2] == tBoard.grid[x][y+3]) && ((tBoard.grid[x][y+1]) == 0))
                {
                    playerOneOrTwoCheck = tBoard.grid[x][y];
                    if (playerOneOrTwoCheck == 1){
                        scoreForOneThrees++;
                        continue;
                    }
                    else if (playerOneOrTwoCheck == 2){
                        scoreForTwoThrees++;
                        continue;
                    }
                }
                if((tBoard.grid[x][y] == tBoard.grid[x][y+1]) && (tBoard.grid[x][y+1] == tBoard.grid[x][y+3]) && ((tBoard.grid[x][y+2]) == 0))
                {
                    playerOneOrTwoCheck = tBoard.grid[x][y];
                    if (playerOneOrTwoCheck == 1){
                        scoreForOneThrees++;
                        continue;
                    }
                    else if (playerOneOrTwoCheck == 2){
                        scoreForTwoThrees++;
                        continue;
                    }
                }

                // check for vertical 2's
                if((tBoard.grid[x][y] == tBoard.grid[x][y+1]) && ((tBoard.grid[x][y+2]) == 0) && ((tBoard.grid[x][y+3]) == 0))
                {
                    playerOneOrTwoCheck = tBoard.grid[x][y];
                    if (playerOneOrTwoCheck == 1){
                        scoreForOneTwos++;
                        continue;
                    }
                    else if (playerOneOrTwoCheck == 2){
                        scoreForTwoTwos++;
                        continue;
                    }
                }
                if((tBoard.grid[x][y+1] == tBoard.grid[x][y+2]) && ((tBoard.grid[x][y]) == 0) && ((tBoard.grid[x][y+3]) == 0))
                {
                    playerOneOrTwoCheck = tBoard.grid[x][y+1];
                    if (playerOneOrTwoCheck == 1){
                        scoreForOneTwos++;
                        continue;
                    }
                    else if (playerOneOrTwoCheck == 2){
                        scoreForTwoTwos++;
                        continue;
                    }
                }
                if((tBoard.grid[x][y+2] == tBoard.grid[x][y+3]) && ((tBoard.grid[x][y]) == 0) && ((tBoard.grid[x][y+1]) == 0))
                {
                    playerOneOrTwoCheck = tBoard.grid[x][y+2];
                    if (playerOneOrTwoCheck == 1){
                        scoreForOneTwos++;
                        continue;
                    }
                    else if (playerOneOrTwoCheck == 2){
                        scoreForTwoTwos++;
                        continue;
                    }
                }
                if((tBoard.grid[x][y] == tBoard.grid[x][y+3]) && ((tBoard.grid[x][y+1]) == 0) && ((tBoard.grid[x][y+2]) == 0))
                {
                    playerOneOrTwoCheck = tBoard.grid[x][y];
                    if (playerOneOrTwoCheck == 1){
                        scoreForOneTwos++;
                        continue;
                    }
                    else if (playerOneOrTwoCheck == 2){
                        scoreForTwoTwos++;
                        continue;
                    }
                }

                //check for vertical 1's
                if((tBoard.grid[x][y] == 0) && ((tBoard.grid[x][y+1]) == 0) && ((tBoard.grid[x][y+2]) == 0) && (tBoard.grid[x][y+3] != 0))
                {
                    playerOneOrTwoCheck = tBoard.grid[x][y+3];
                    if (playerOneOrTwoCheck == 1){
                        scoreForOneOnes++;
                        continue;
                    }
                    else if (playerOneOrTwoCheck == 2){
                        scoreForTwoOnes++;
                        continue;
                    }
                }
                if((tBoard.grid[x][y] == 0) && ((tBoard.grid[x][y+1]) == 0) && ((tBoard.grid[x][y+3]) == 0) && (tBoard.grid[x][y+2] != 0))
                {
                    playerOneOrTwoCheck = tBoard.grid[x][y+2];
                    if (playerOneOrTwoCheck == 1){
                        scoreForOneOnes++;
                        continue;
                    }
                    else if (playerOneOrTwoCheck == 2){
                        scoreForTwoOnes++;
                        continue;
                    }
                }
                if((tBoard.grid[x][y] == 0) && ((tBoard.grid[x][y+2]) == 0) && ((tBoard.grid[x][y+3]) == 0) && (tBoard.grid[x][y+1] != 0))
                {
                    playerOneOrTwoCheck = tBoard.grid[x][y+1];
                    if (playerOneOrTwoCheck == 1){
                        scoreForOneOnes++;
                        continue;
                    }
                    else if (playerOneOrTwoCheck == 2){
                        scoreForTwoOnes++;
                        continue;
                    }
                }
                if((tBoard.grid[x][y+1] == 0) && ((tBoard.grid[x][y+2]) == 0) && ((tBoard.grid[x][y+3]) == 0) && (tBoard.grid[x][y] != 0))
                {
                    playerOneOrTwoCheck = tBoard.grid[x][y];
                    if (playerOneOrTwoCheck == 1){
                        scoreForOneOnes++;
                        continue;
                    }
                    else if (playerOneOrTwoCheck == 2){
                        scoreForTwoOnes++;
                        continue;
                    }
                }
            }
        }


        for (int x = 0; x < (tBoard.width-3); x++) // check for diagonal #1
        {
            for(int y = 0; y < (tBoard.height-3); y++)
            {
                // continue if all 0's
                if((tBoard.grid[x][y] == 0) && (tBoard.grid[x+1][y+1] == 0) && (tBoard.grid[x+2][y+2] == 0) && (tBoard.grid[x+3][y+3]) == 0)
                {
                    continue;
                }

                //check for diagonal 3's #1
                if((tBoard.grid[x][y] == tBoard.grid[x+1][y+1]) && (tBoard.grid[x+1][y+1] == tBoard.grid[x+2][y+2]) && (tBoard.grid[x+3][y+3] == 0))
                {
                    playerOneOrTwoCheck = tBoard.grid[x][y];
                    if (playerOneOrTwoCheck == 1){
                        scoreForOneThrees++;
                        continue;
                    }
                    else if (playerOneOrTwoCheck == 2){
                        scoreForTwoThrees++;
                        continue;
                    }
                }
                if((tBoard.grid[x+1][y+1] == tBoard.grid[x+2][y+2]) && (tBoard.grid[x+2][y+2] == tBoard.grid[x+3][y+3]) && (tBoard.grid[x][y] == 0))
                {
                    playerOneOrTwoCheck = tBoard.grid[x+1][y+1];
                    if (playerOneOrTwoCheck == 1){
                        scoreForOneThrees++;
                        continue;
                    }
                    else if (playerOneOrTwoCheck == 2){
                        scoreForTwoThrees++;
                        continue;
                    }
                }
                if((tBoard.grid[x][y] == tBoard.grid[x+2][y+2]) && (tBoard.grid[x+2][y+2] == tBoard.grid[x+3][y+3]) && (tBoard.grid[x+1][y+1] == 0))
                {
                    playerOneOrTwoCheck = tBoard.grid[x][y];
                    if (playerOneOrTwoCheck == 1){
                        scoreForOneThrees++;
                        continue;
                    }
                    else if (playerOneOrTwoCheck == 2){
                        scoreForTwoThrees++;
                        continue;
                    }
                }
                if((tBoard.grid[x][y] == tBoard.grid[x+1][y+1]) && (tBoard.grid[x+1][y+1] == tBoard.grid[x+3][y+3]) && (tBoard.grid[x+2][y+2] == 0))
                {
                    playerOneOrTwoCheck = tBoard.grid[x][y];
                    if (playerOneOrTwoCheck == 1){
                        scoreForOneThrees++;
                        continue;
                    }
                    else if (playerOneOrTwoCheck == 2){
                        scoreForTwoThrees++;
                        continue;
                    }
                }

                //check for diagonal 2's #1
                if((tBoard.grid[x][y] == tBoard.grid[x+1][y+1]) && ((tBoard.grid[x+2][y+2]) == 0) && ((tBoard.grid[x+3][y+3]) == 0))
                {
                    playerOneOrTwoCheck = tBoard.grid[x][y];
                    if (playerOneOrTwoCheck == 1){
                        scoreForOneTwos++;
                        continue;
                    }
                    else if (playerOneOrTwoCheck == 2){
                        scoreForTwoTwos++;
                        continue;
                    }
                }
                if((tBoard.grid[x+1][y+1] == tBoard.grid[x+2][y+2]) && ((tBoard.grid[x][y]) == 0) && ((tBoard.grid[x+3][y+3]) == 0))
                {
                    playerOneOrTwoCheck = tBoard.grid[x+1][y+1];
                    if (playerOneOrTwoCheck == 1){
                        scoreForOneTwos++;
                        continue;
                    }
                    else if (playerOneOrTwoCheck == 2){
                        scoreForTwoTwos++;
                        continue;
                    }
                }
                if((tBoard.grid[x+2][y+2] == tBoard.grid[x+3][y+3]) && ((tBoard.grid[x][y]) == 0) && ((tBoard.grid[x+1][y+1]) == 0))
                {
                    playerOneOrTwoCheck = tBoard.grid[x+2][y+2];
                    if (playerOneOrTwoCheck == 1){
                        scoreForOneTwos++;
                        continue;
                    }
                    else if (playerOneOrTwoCheck == 2){
                        scoreForTwoTwos++;
                        continue;
                    }
                }
                if((tBoard.grid[x][y] == tBoard.grid[x+3][y+3]) && ((tBoard.grid[x+1][y+1]) == 0) && ((tBoard.grid[x+2][y+2]) == 0))
                {
                    playerOneOrTwoCheck = tBoard.grid[x][y];
                    if (playerOneOrTwoCheck == 1){
                        scoreForOneTwos++;
                        continue;
                    }
                    else if (playerOneOrTwoCheck == 2){
                        scoreForTwoTwos++;
                        continue;
                    }
                }

                //check for diagonal 1's #1
                if((tBoard.grid[x][y] == 0) && ((tBoard.grid[x+1][y+1]) == 0) && ((tBoard.grid[x+2][y+2]) == 0) && (tBoard.grid[x+3][y+3] != 0))
                {
                    playerOneOrTwoCheck = tBoard.grid[x+3][y+3];
                    if (playerOneOrTwoCheck == 1){
                        scoreForOneOnes++;
                        continue;
                    }
                    else if (playerOneOrTwoCheck == 2){
                        scoreForTwoOnes++;
                        continue;
                    }
                }
                if((tBoard.grid[x][y] == 0) && ((tBoard.grid[x+1][y+1]) == 0) && ((tBoard.grid[x+3][y+3]) == 0) && (tBoard.grid[x+2][y+2] != 0))
                {
                    playerOneOrTwoCheck = tBoard.grid[x+2][y+2];
                    if (playerOneOrTwoCheck == 1){
                        scoreForOneOnes++;
                        continue;
                    }
                    else if (playerOneOrTwoCheck == 2){
                        scoreForTwoOnes++;
                        continue;
                    }
                }
                if((tBoard.grid[x][y] == 0) && ((tBoard.grid[x+2][y+2]) == 0) && ((tBoard.grid[x+3][y+3]) == 0) && (tBoard.grid[x+1][y+1] != 0))
                {
                    playerOneOrTwoCheck = tBoard.grid[x+1][y+1];
                    if (playerOneOrTwoCheck == 1){
                        scoreForOneOnes++;
                        continue;
                    }
                    else if (playerOneOrTwoCheck == 2){
                        scoreForTwoOnes++;
                        continue;
                    }
                }
                if((tBoard.grid[x+1][y+1] == 0) && ((tBoard.grid[x+2][y+2]) == 0) && ((tBoard.grid[x+3][y+3]) == 0) && (tBoard.grid[x][y] != 0))
                {
                    playerOneOrTwoCheck = tBoard.grid[x][y];
                    if (playerOneOrTwoCheck == 1){
                        scoreForOneOnes++;
                        continue;
                    }
                    else if (playerOneOrTwoCheck == 2){
                        scoreForTwoOnes++;
                        continue;
                    }
                }
            }
        }

        for (int x = 0; x < (tBoard.width-3); x++) // check diagonal #2
        {
            for(int y = 3; y < tBoard.height; y++)
            {
                // continue if all 0's
                if((tBoard.grid[x][y] == 0) && (tBoard.grid[x+1][y-1] == 0) && (tBoard.grid[x+2][y-2] == 0) && (tBoard.grid[x+3][y-3]) == 0)
                {
                    continue;
                }

                // check for diagonal 3's #2
                if((tBoard.grid[x][y] == tBoard.grid[x+1][y-1]) && (tBoard.grid[x+1][y-1] == tBoard.grid[x+2][y-2]) && (tBoard.grid[x+3][y-3] == 0))
                {
                    playerOneOrTwoCheck= tBoard.grid[x][y];
                    if (playerOneOrTwoCheck == 1){
                        scoreForOneThrees++;
                        continue;
                    }
                    else if (playerOneOrTwoCheck == 2){
                        scoreForTwoThrees++;
                        continue;
                    }
                }
                if((tBoard.grid[x+1][y-1] == tBoard.grid[x+2][y-2]) && (tBoard.grid[x+2][y-2] == tBoard.grid[x+3][y-3]) && (tBoard.grid[x][y] == 0))
                {
                    playerOneOrTwoCheck= tBoard.grid[x+1][y-1];
                    if (playerOneOrTwoCheck == 1){
                        scoreForOneThrees++;
                        continue;
                    }
                    else if (playerOneOrTwoCheck == 2){
                        scoreForTwoThrees++;
                        continue;
                    }
                }
                if((tBoard.grid[x][y] == tBoard.grid[x+2][y-2]) && (tBoard.grid[x+2][y-2] == tBoard.grid[x+3][y-3]) && (tBoard.grid[x+1][y-1] == 0))
                {
                    playerOneOrTwoCheck= tBoard.grid[x][y];
                    if (playerOneOrTwoCheck == 1){
                        scoreForOneThrees++;
                        continue;
                    }
                    else if (playerOneOrTwoCheck == 2){
                        scoreForTwoThrees++;
                        continue;
                    }
                }
                if((tBoard.grid[x][y] == tBoard.grid[x+1][y-1]) && (tBoard.grid[x+1][y-1] == tBoard.grid[x+3][y-3]) && (tBoard.grid[x+2][y-2] == 0))
                {
                    playerOneOrTwoCheck= tBoard.grid[x][y];
                    if (playerOneOrTwoCheck == 1){
                        scoreForOneThrees++;
                        continue;
                    }
                    else if (playerOneOrTwoCheck == 2){
                        scoreForTwoThrees++;
                        continue;
                    }
                }

                //check for diagonal 2's #2
                if((tBoard.grid[x][y] == tBoard.grid[x+1][y-1]) && ((tBoard.grid[x+2][y-2]) == 0) && ((tBoard.grid[x+3][y-3]) == 0))
                {
                    playerOneOrTwoCheck = tBoard.grid[x][y];
                    if (playerOneOrTwoCheck == 1){
                        scoreForOneTwos++;
                        continue;
                    }
                    else if (playerOneOrTwoCheck == 2){
                        scoreForTwoTwos++;
                        continue;
                    }
                }
                if((tBoard.grid[x+1][y-1] == tBoard.grid[x+2][y-2]) && ((tBoard.grid[x][y]) == 0) && ((tBoard.grid[x+3][y-3]) == 0))
                {
                    playerOneOrTwoCheck = tBoard.grid[x+1][y-1];
                    if (playerOneOrTwoCheck == 1){
                        scoreForOneTwos++;
                        continue;
                    }
                    else if (playerOneOrTwoCheck == 2){
                        scoreForTwoTwos++;
                        continue;
                    }
                }
                if((tBoard.grid[x+2][y-2] == tBoard.grid[x+3][y-3]) && ((tBoard.grid[x][y]) == 0) && ((tBoard.grid[x+1][y-1]) == 0))
                {
                    playerOneOrTwoCheck = tBoard.grid[x+2][y-2];
                    if (playerOneOrTwoCheck == 1){
                        scoreForOneTwos++;
                        continue;
                    }
                    else if (playerOneOrTwoCheck == 2){
                        scoreForTwoTwos++;
                        continue;
                    }
                }
                if((tBoard.grid[x][y] == tBoard.grid[x+3][y-3]) && ((tBoard.grid[x+1][y-1]) == 0) && ((tBoard.grid[x+2][y-2]) == 0))
                {
                    playerOneOrTwoCheck = tBoard.grid[x][y];
                    if (playerOneOrTwoCheck == 1){
                        scoreForOneTwos++;
                        continue;
                    }
                    else if (playerOneOrTwoCheck == 2){
                        scoreForTwoTwos++;
                        continue;
                    }
                }

                //check for diagonal 1's #1
                if((tBoard.grid[x][y] == 0) && ((tBoard.grid[x+1][y-1]) == 0) && ((tBoard.grid[x+2][y-2]) == 0) && (tBoard.grid[x+3][y-3] != 0))
                {
                    playerOneOrTwoCheck = tBoard.grid[x+3][y-3];
                    if (playerOneOrTwoCheck == 1){
                        scoreForOneOnes++;
                        continue;
                    }
                    else if (playerOneOrTwoCheck == 2){
                        scoreForTwoOnes++;
                        continue;
                    }
                }
                if((tBoard.grid[x][y] == 0) && ((tBoard.grid[x+1][y-1]) == 0) && ((tBoard.grid[x+3][y-3]) == 0) && (tBoard.grid[x+2][y-2] != 0))
                {
                    playerOneOrTwoCheck = tBoard.grid[x+2][y-2];
                    if (playerOneOrTwoCheck == 1){
                        scoreForOneOnes++;
                        continue;
                    }
                    else if (playerOneOrTwoCheck == 2){
                        scoreForTwoOnes++;
                        continue;
                    }
                }
                if((tBoard.grid[x][y] == 0) && ((tBoard.grid[x+2][y-2]) == 0) && ((tBoard.grid[x+3][y-3]) == 0) && (tBoard.grid[x+1][y-1] != 0))
                {
                    playerOneOrTwoCheck = tBoard.grid[x+1][y-1];
                    if (playerOneOrTwoCheck == 1){
                        scoreForOneOnes++;
                        continue;
                    }
                    else if (playerOneOrTwoCheck == 2){
                        scoreForTwoOnes++;
                        continue;
                    }
                }
                if((tBoard.grid[x+1][y-1] == 0) && ((tBoard.grid[x+2][y-2]) == 0) && ((tBoard.grid[x+3][y-3]) == 0) && (tBoard.grid[x][y] != 0))
                {
                    playerOneOrTwoCheck = tBoard.grid[x][y];
                    if (playerOneOrTwoCheck == 1){
                        scoreForOneOnes++;
                        continue;
                    }
                    else if (playerOneOrTwoCheck == 2){
                        scoreForTwoOnes++;
                        continue;
                    }
                }
            }
        }

        // compute final scores
        if(tBoard.player == 1)
        {
            finalScore += 50 * (scoreForOneThrees + (-1 * scoreForTwoThrees));
            finalScore += 10 * (scoreForOneTwos + (-1 * scoreForTwoTwos));
            finalScore += (scoreForOneOnes + (-1 * scoreForTwoOnes));
            if (whichPlayer == true)
            {
                finalScore += 16;
            }
            else
            {
                finalScore += -16;
            }
        }
        else
        {
            finalScore += 50 * ((-1 * scoreForOneThrees) + scoreForTwoThrees);
            finalScore += 10 * ((-1 * scoreForOneTwos) + scoreForTwoTwos);
            finalScore += ((-1 * scoreForOneOnes) + scoreForTwoOnes);
            if (whichPlayer == true)
            {
                finalScore += 16;
            }
            else
            {
                finalScore += -16;
            }
        }

        return finalScore;
    }

}
