package Program1;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class GraphNode{ // graph node class to wrap board class
    Board boardOb;
    ArrayList<GraphNode> neighborNodes;
    ArrayList<String> moveToGetToNeighborNode;
    GraphNode parentNode;
    int depthOfThisNode;
    int costG;
    int misplacedTilesH;
    int manhattanDistanceH;
    int fOfN;

    public GraphNode(){
        boardOb = new Board();
        neighborNodes = new ArrayList<GraphNode>();
        moveToGetToNeighborNode = new ArrayList<String>();
        parentNode = null;
        depthOfThisNode = 0;
        costG = 0;
        misplacedTilesH = 0;
        manhattanDistanceH = 0;
        fOfN = 0;
    }

    public GraphNode(Board tBoard){
        boardOb = tBoard;
        neighborNodes = new ArrayList<GraphNode>();
        moveToGetToNeighborNode = new ArrayList<String>();
        parentNode = null;
        depthOfThisNode = 0;
        costG = 0;
        misplacedTilesH = 0;
        manhattanDistanceH = 0;
        fOfN = 0;
    }

    public GraphNode(Board tBoard, GraphNode tParentNode, int tDepthOfThisNode)
    {
        boardOb = tBoard;
        neighborNodes = new ArrayList<GraphNode>();
        moveToGetToNeighborNode = new ArrayList<String>();
        parentNode = tParentNode;
        depthOfThisNode = tDepthOfThisNode;
        costG = 0;
        misplacedTilesH = 0;
        manhattanDistanceH = 0;
        fOfN = 0;
    }

    public GraphNode(Board tBoard, GraphNode tParentNode, int tDepthOfThisNode, int tCostG)
    {
        boardOb = tBoard;
        neighborNodes = new ArrayList<GraphNode>();
        moveToGetToNeighborNode = new ArrayList<String>();
        parentNode = tParentNode;
        depthOfThisNode = tDepthOfThisNode;
        costG = tCostG;
        misplacedTilesH = 0;
        manhattanDistanceH = 0;
        fOfN = 0;
    }

    public void computeMisplacedTiles(Board goalState){ // compute misplaced tiles
        for(int i = 0; i < goalState.boardSize; i++)
        {
            for(int j = 0; j < goalState.boardSize; j++)
            {
                if(goalState.currentBoard[i][j] != boardOb.currentBoard[i][j])
                {
                    misplacedTilesH++;
                }
            }
        }
    }

    public void computeFofNMisplacedTiles(){
        fOfN = costG + misplacedTilesH;
    }

    public void computeManhattanDistance(Board goalState) { // compute manhattan distance
        HashMap<Integer, Pair> currentBoardCords = new HashMap<Integer, Pair>();
        HashMap<Integer, Pair> goalBoardCords = new HashMap<Integer, Pair>();
        for(int i = 0; i < goalState.boardSize; i++)
        {
            for(int j = 0; j < goalState.boardSize; j++)
            {
                currentBoardCords.put(boardOb.currentBoard[i][j], new Pair(i,j));
                goalBoardCords.put(goalState.currentBoard[i][j], new Pair(i,j));
            }
        }
        for (int key: currentBoardCords.keySet()) {
            int distance = Math.abs(currentBoardCords.get(key).rowCord - goalBoardCords.get(key).rowCord) + Math.abs(currentBoardCords.get(key).colCord - goalBoardCords.get(key).colCord);
            manhattanDistanceH += distance;
        }
    }

    public void computeFofNManhattanDistance(){
        fOfN = costG + manhattanDistanceH;
    }
}
