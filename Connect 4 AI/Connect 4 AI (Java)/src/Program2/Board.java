package Program2;

public class Board{ // to hold/deserialize json and represent board
    public int grid[][];
    public int height;
    public int player;
    public int width;

    Board(Board tBoard){
        height = tBoard.height;
        width = tBoard.width;
        player = tBoard.player;
        grid = new int[tBoard.width][tBoard.height];
        for(int i = 0; i < width; i++)
        {
            for(int k = 0; k < height; k++)
            {
                grid[i][k] = tBoard.grid[i][k];
            }
        }
    }
}
