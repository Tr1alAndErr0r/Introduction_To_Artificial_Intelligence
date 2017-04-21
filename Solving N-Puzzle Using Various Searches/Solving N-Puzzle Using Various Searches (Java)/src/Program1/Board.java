package Program1;

public class Board{ // board class to contain a board state
    public int currentBoard[][];
    public int currentHoleRow;
    public int currentHoleColumn;
    public int boardSize;

    public Board(){
        this.currentHoleRow = 0;
        this.currentHoleColumn = 0;
        this.boardSize = 0;
        this.currentBoard = new int[this.boardSize][this.boardSize];
    }

    public Board(int n){
        this.currentHoleRow = 0;
        this.currentHoleColumn = 0;
        this.boardSize = n;
        this.currentBoard = new int[this.boardSize][this.boardSize];
    }

    public Board(Board copy){
        this.boardSize = copy.boardSize;
        this.currentHoleRow = copy.currentHoleRow;
        this.currentHoleColumn = copy.currentHoleColumn;
        this.currentBoard = new int[this.boardSize][this.boardSize];
        for(int i = 0; i < this.boardSize; i++)
        {
            for(int j = 0; j < this.boardSize; j++)
            {
                this.currentBoard[i][j] = copy.currentBoard[i][j];
            }
        }
    }
    public void findHole(){
        breakDone:
        for(int i = 0; i < this.boardSize; i++)
        {
            for(int j = 0; j < this.boardSize; j++)
            {
                if (this.currentBoard[i][j] == 0)
                {
                    this.currentHoleRow = i;
                    this.currentHoleColumn = j;
                    break breakDone;
                }
            }
        }
    }

    public void copyStateJson(int[][] boardState){
        for(int i = 0; i < this.boardSize; i++)
        {
            for(int j = 0; j < this.boardSize; j++)
            {
                this.currentBoard[i][j] = boardState[i][j];
            }
        }
    }
}
