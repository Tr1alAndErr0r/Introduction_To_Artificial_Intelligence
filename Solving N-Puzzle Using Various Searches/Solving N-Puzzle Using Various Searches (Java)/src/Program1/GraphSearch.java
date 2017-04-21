package Program1;

import java.lang.reflect.Array;
import java.util.*;

public class GraphSearch{
    Board goalBoard;
    GraphNode startNode;
    RuleFunctions rules;
    int statesExamined;
    Stack<GraphNode> solutionStates;
    ArrayList<String> moveList;

    public GraphSearch(){
        goalBoard = new Board();
        startNode = new GraphNode();
        rules = new RuleFunctions();
        statesExamined = 0;
        solutionStates = new Stack<GraphNode>();
        moveList = new ArrayList<String>();
    }

    public GraphSearch(Board tStartBoard, Board tGoalBoard){
        goalBoard = tGoalBoard;
        startNode = new GraphNode(tStartBoard);
        rules = new RuleFunctions();
        statesExamined = 0;
        solutionStates = new Stack<GraphNode>();
        moveList = new ArrayList<String>();
    }

    public void doGraphSearchBFS(){ // breadth-first search
        Queue<GraphNode> open = new ArrayDeque<GraphNode>();
        open.add(startNode);
        Queue<GraphNode> closed = new ArrayDeque<GraphNode>();

        while(!open.isEmpty())
        {
            GraphNode n = open.remove();
            closed.add(n);

            if(Arrays.deepEquals(n.boardOb.currentBoard, goalBoard.currentBoard)){ // if goal is met then trace back path
                solutionStates.push(n);
                GraphNode tempPreviousNode = n;
                GraphNode tempNextNode = n.parentNode;

                while(tempNextNode != null)
                {
                    solutionStates.push(tempNextNode);
                    moveList.add(tempNextNode.moveToGetToNeighborNode.get(tempNextNode.neighborNodes.indexOf(tempPreviousNode)));
                    tempPreviousNode = tempNextNode;
                    tempNextNode = tempNextNode.parentNode;
                }
                return;
            }

            List<String> possibleMovesFromBoard = rules.applicableRules(n.boardOb); // possible moves
            while(!possibleMovesFromBoard.isEmpty()) // to check successor boards
            {
                boolean checkInOpen = false;
                boolean checkInClosed = false;
                Board successorBoard = rules.makeMove(n.boardOb,possibleMovesFromBoard.get(0),n.boardOb.boardSize); // generate successor board
                GraphNode successorNode = new GraphNode(successorBoard, n, n.depthOfThisNode+1, n.depthOfThisNode+1); // wrap in node
                n.neighborNodes.add(successorNode); // add to neighboring nodes of parent
                n.moveToGetToNeighborNode.add(possibleMovesFromBoard.get(0)); // add move to get to this node

                for(Iterator itr = open.iterator();itr.hasNext();)
                {
                    GraphNode checkedNode = (GraphNode) itr.next();
                    if(Arrays.deepEquals(checkedNode.boardOb.currentBoard, successorNode.boardOb.currentBoard)) // if in open
                    {
                        if(checkedNode.costG > successorNode.costG) // if cost, in this case depth, is lower in successor then make node in list point to current parent
                        {
                            n.neighborNodes.remove(successorNode);
                            n.neighborNodes.remove(possibleMovesFromBoard.get(0));
                            checkedNode.parentNode = n;
                            checkedNode.costG = successorNode.costG;
                            checkedNode.depthOfThisNode = successorNode.depthOfThisNode;
                            n.neighborNodes.add(checkedNode);
                            n.moveToGetToNeighborNode.add(possibleMovesFromBoard.get(0));
                        }
                        checkInOpen = true;
                    }
                }
                for(Iterator itr = closed.iterator();itr.hasNext();)
                {
                    GraphNode checkedNode = (GraphNode) itr.next();
                    if(Arrays.deepEquals(checkedNode.boardOb.currentBoard, successorBoard.currentBoard))
                    {
                        if(checkedNode.costG > successorNode.costG)
                        {
                            n.neighborNodes.remove(successorNode);
                            n.neighborNodes.remove(possibleMovesFromBoard.get(0));
                            checkedNode.parentNode = n;
                            checkedNode.costG = successorNode.costG;
                            checkedNode.depthOfThisNode = successorNode.depthOfThisNode;
                            n.neighborNodes.add(checkedNode);
                            n.moveToGetToNeighborNode.add(possibleMovesFromBoard.get(0));
                        }
                        checkInClosed = true;
                    }
                }

                if(checkInOpen == false && checkInClosed == false) // if not in open or close then add to open
                {
                    open.add(successorNode);
                }
                statesExamined++; // increment states examined
                possibleMovesFromBoard.remove(0);
            }

            if(open.isEmpty())
            {
                System.out.println("Open is Empty, Fail"); // if no more to check then fail
                System.exit(0);
            }
        }
    }

    public void doAStarHeuristicOne(){ // A* with misplaced tiles heuristic
        ArrayList<GraphNode> open = new ArrayList<GraphNode>();
        startNode.computeMisplacedTiles(goalBoard); // compute misplaced tiles
        startNode.computeFofNMisplacedTiles(); // find f(n)
        open.add(startNode);

        ArrayList<GraphNode> closed = new ArrayList<GraphNode>();
        while(!open.isEmpty())
        {
            GraphNode n = open.remove(0);
            closed.add(0,n);

            if(Arrays.deepEquals(n.boardOb.currentBoard, goalBoard.currentBoard)){
                solutionStates.push(n);
                GraphNode tempPreviousNode = n;
                GraphNode tempNextNode = n.parentNode;

                while(tempNextNode != null)
                {
                    solutionStates.push(tempNextNode);
                    moveList.add(tempNextNode.moveToGetToNeighborNode.get(tempNextNode.neighborNodes.indexOf(tempPreviousNode)));
                    tempPreviousNode = tempNextNode;
                    tempNextNode = tempNextNode.parentNode;
                }
                return;
            }

            List<String> possibleMovesFromBoard = rules.applicableRules(n.boardOb);
            while(!possibleMovesFromBoard.isEmpty())
            {
                boolean checkInOpen = false;
                boolean checkInClosed = false;
                Board successorBoard = rules.makeMove(n.boardOb,possibleMovesFromBoard.get(0),n.boardOb.boardSize);
                GraphNode successorNode = new GraphNode(successorBoard, n, n.depthOfThisNode+1, n.depthOfThisNode+1);
                successorNode.computeMisplacedTiles(goalBoard);
                successorNode.computeFofNMisplacedTiles();
                n.neighborNodes.add(successorNode);
                n.moveToGetToNeighborNode.add(possibleMovesFromBoard.get(0));

                for(int i = 0; i < open.size(); i++)
                {
                    GraphNode checkedNode = open.get(i);
                    if(Arrays.deepEquals(checkedNode.boardOb.currentBoard, successorNode.boardOb.currentBoard))
                    {
                        if(checkedNode.costG > successorNode.costG)
                        {
                            n.neighborNodes.remove(successorNode);
                            n.neighborNodes.remove(possibleMovesFromBoard.get(0));
                            checkedNode.parentNode = n;
                            checkedNode.costG = successorNode.costG;
                            checkedNode.depthOfThisNode = successorNode.depthOfThisNode;
                            n.neighborNodes.add(checkedNode);
                            n.moveToGetToNeighborNode.add(possibleMovesFromBoard.get(0));
                        }
                        checkInOpen = true;
                    }
                }
                for(int i = 0; i < closed.size(); i++)
                {
                    GraphNode checkedNode = closed.get(0);
                    if(Arrays.deepEquals(checkedNode.boardOb.currentBoard, successorBoard.currentBoard))
                    {
                        if(checkedNode.costG > successorNode.costG)
                        {
                            n.neighborNodes.remove(successorNode);
                            n.neighborNodes.remove(possibleMovesFromBoard.get(0));
                            checkedNode.parentNode = n;
                            checkedNode.costG = successorNode.costG;
                            checkedNode.depthOfThisNode = successorNode.depthOfThisNode;
                            n.neighborNodes.add(checkedNode);
                            n.moveToGetToNeighborNode.add(possibleMovesFromBoard.get(0));
                        }
                        checkInClosed = true;
                    }
                }

                if(checkInOpen == false && checkInClosed == false)
                {
                    open.add(0,successorNode);
                }
                statesExamined++;
                possibleMovesFromBoard.remove(0);
            }

            if(open.isEmpty())
            {
                System.out.println("Open is Empty, Fail");
                System.exit(0);
            }
            Collections.sort(open, new Comparator<GraphNode>(){  // reorder list with lowest f(n) at the top
                public int compare(GraphNode ob1, GraphNode ob2){
                    if(ob1.fOfN == ob2.fOfN)
                        return 0;
                    return ob1.fOfN < ob2.fOfN ? -1 : 1;
                }
            });
        }
    }

    public void doAStarHeuristicTwo(){ // A* with manhattan distance heuristic
        ArrayList<GraphNode> open = new ArrayList<GraphNode>();
        startNode.computeManhattanDistance(goalBoard); // compute manhattan distance
        startNode.computeFofNManhattanDistance();
        open.add(startNode);

        ArrayList<GraphNode> closed = new ArrayList<GraphNode>();
        while(!open.isEmpty())
        {
            GraphNode n = open.remove(0);
            closed.add(0,n);

            if(Arrays.deepEquals(n.boardOb.currentBoard, goalBoard.currentBoard)){
                solutionStates.push(n);
                GraphNode tempPreviousNode = n;
                GraphNode tempNextNode = n.parentNode;

                while(tempNextNode != null)
                {
                    solutionStates.push(tempNextNode);
                    moveList.add(tempNextNode.moveToGetToNeighborNode.get(tempNextNode.neighborNodes.indexOf(tempPreviousNode)));
                    tempPreviousNode = tempNextNode;
                    tempNextNode = tempNextNode.parentNode;
                }
                return;
            }

            List<String> possibleMovesFromBoard = rules.applicableRules(n.boardOb);
            while(!possibleMovesFromBoard.isEmpty())
            {
                boolean checkInOpen = false;
                boolean checkInClosed = false;
                Board successorBoard = rules.makeMove(n.boardOb,possibleMovesFromBoard.get(0),n.boardOb.boardSize);
                GraphNode successorNode = new GraphNode(successorBoard, n, n.depthOfThisNode+1, n.depthOfThisNode+1);
                successorNode.computeManhattanDistance(goalBoard);
                successorNode.computeFofNManhattanDistance();
                n.neighborNodes.add(successorNode);
                n.moveToGetToNeighborNode.add(possibleMovesFromBoard.get(0));

                for(int i = 0; i < open.size(); i++)
                {
                    GraphNode checkedNode = open.get(i);
                    if(Arrays.deepEquals(checkedNode.boardOb.currentBoard, successorNode.boardOb.currentBoard))
                    {
                        if(checkedNode.costG > successorNode.costG)
                        {
                            n.neighborNodes.remove(successorNode);
                            n.neighborNodes.remove(possibleMovesFromBoard.get(0));
                            checkedNode.parentNode = n;
                            checkedNode.costG = successorNode.costG;
                            checkedNode.depthOfThisNode = successorNode.depthOfThisNode;
                            n.neighborNodes.add(checkedNode);
                            n.moveToGetToNeighborNode.add(possibleMovesFromBoard.get(0));
                        }
                        checkInOpen = true;
                    }
                }
                for(int i = 0; i < closed.size(); i++)
                {
                    GraphNode checkedNode = closed.get(0);
                    if(Arrays.deepEquals(checkedNode.boardOb.currentBoard, successorBoard.currentBoard))
                    {
                        if(checkedNode.costG > successorNode.costG)
                        {
                            n.neighborNodes.remove(successorNode);
                            n.neighborNodes.remove(possibleMovesFromBoard.get(0));
                            checkedNode.parentNode = n;
                            checkedNode.costG = successorNode.costG;
                            checkedNode.depthOfThisNode = successorNode.depthOfThisNode;
                            n.neighborNodes.add(checkedNode);
                            n.moveToGetToNeighborNode.add(possibleMovesFromBoard.get(0));
                        }
                        checkInClosed = true;
                    }
                }

                if(checkInOpen == false && checkInClosed == false)
                {
                    open.add(0,successorNode);
                }
                statesExamined++;
                possibleMovesFromBoard.remove(0);
            }

            if(open.isEmpty())
            {
                System.out.println("Open is Empty, Fail");
                System.exit(0);
            }
            Collections.sort(open, new Comparator<GraphNode>(){
                public int compare(GraphNode ob1, GraphNode ob2){
                    if(ob1.fOfN == ob2.fOfN)
                        return 0;
                    return ob1.fOfN < ob2.fOfN ? -1 : 1;
                }
            });
        }
    }

    public void printGraphSearch(){ // print out results
        System.out.println("Start State:");
        for (int i = 0; i < startNode.boardOb.boardSize; i++)
        {
            for(int j = 0; j < startNode.boardOb.boardSize; j++)
            {
                if(j == 0){
                    System.out.print("[");
                }
                System.out.print(startNode.boardOb.currentBoard[i][j]);
                if(j != startNode.boardOb.boardSize - 1){
                    System.out.print(",");
                }
                if(j == startNode.boardOb.boardSize-1)
                {
                    System.out.print("]");
                }
            }
            System.out.println();
        }
        System.out.println();
        System.out.println("Goal State:");
        for (int i = 0; i < goalBoard.boardSize; i++)
        {
            for(int j = 0; j < goalBoard.boardSize; j++)
            {
                if(j==0){
                    System.out.print("[");
                }
                System.out.print(goalBoard.currentBoard[i][j]);
                if(j != goalBoard.boardSize - 1){
                    System.out.print(",");
                }
                if(j == goalBoard.boardSize-1)
                {
                    System.out.print("]");
                }
            }
            if(i!= goalBoard.boardSize-1)
                System.out.println();
        }
        System.out.println();
        /*
        Testing - To look at board states

        while (!solutionStates.isEmpty()){
            GraphNode temp = solutionStates.pop();
            System.out.println();
            for (int i = 0; i < temp.boardOb.boardSize; i++)
            {
                for(int j = 0; j < temp.boardOb.boardSize; j++)
                {
                    if(j == 0){
                        System.out.print("[");
                    }
                    System.out.print(temp.boardOb.currentBoard[i][j]);
                    if(j != temp.boardOb.boardSize - 1){
                        System.out.print(",");
                    }
                    if(j == temp.boardOb.boardSize-1)
                    {
                        System.out.print("]");
                    }
                }
                System.out.println();
            }
        }
        */
        System.out.println();
        System.out.printf("States Examined: %s", statesExamined);
        System.out.println();
        System.out.printf("Move List: %s moves", moveList.size());
        System.out.println();
        Collections.reverse(moveList);
        System.out.println(moveList);
    }
}
