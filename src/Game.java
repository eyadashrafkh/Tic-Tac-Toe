import java.util.Arrays;

public class Game {

    private char turn;
    private boolean twoPlayer;
    private char [][] grid;
    private int freeSpots;
    public static GameUI gui;

    public Game() {
        newGame(false);
    }

    public void newGame(boolean twoPlayer){
        this.twoPlayer = twoPlayer;
        grid = new char[3][3];
        for (int i=0; i<3; i++){
            for (int j=0; j<3; j++){
                grid[i][j] = '-';
            }
        }
        freeSpots = 9;
        turn = 'x';
    }

    public char gridAt(int i, int j){
        if(i>=3||j>=3||i<0||j<0)
            return '!';
        return grid[i][j];
    }

    public boolean playAt(int i, int j){
        if(i>=3||j>=3||i<0||j<0)
            return false;
        if(grid[i][j] != '-'){
            return false;
        }
        grid[i][j] = turn;
        freeSpots--;
        return true;
    }

    public String toString(){
        return Arrays.deepToString( this.grid);
    }

    public boolean doChecks() {
        String winnerMessage = checkGameWinner(grid);
        if (!winnerMessage.equals("None")) {
            gui.gameOver(winnerMessage);
            newGame(false);
            return true;
        }
        return false;
    }

    public void nextTurn(){
        if(!twoPlayer){
            if(freeSpots == 0){
                return ;
            }
            int ai_i, ai_j;
            do {
                ai_i = (int) (Math.random() * 3);
                ai_j = (int) (Math.random() * 3);
            }while(grid[ai_i][ai_j] != '-');
            grid[ai_i][ai_j] = 'o';
            freeSpots--;
        }
        else{
            if(turn == 'x'){
                turn = 'o';
            }
            else{
                turn = 'x';
            }
        }
        return;
    }
    
    public String checkGameWinner(char [][]grid){
        String result = "None";
        for(int i=0;i<3;i++) {
            if (grid[i][0] != '-') {
                if (grid[i][0] == grid[i][1] && grid[i][1] == grid[i][2]) {
                    return grid[i][0] + " wins";
                }
            }
        }//check column
        for(int j=0;j<3;j++) {
            if (grid[0][j] != '-') {
                if (grid[0][j] == grid[1][j] && grid[1][j] == grid[2][j]) {
                    return grid[0][j] + " wins";
                }
            }
        }//check row
        if (grid[0][0]==grid[1][1] && grid[1][1]==grid[2][2] && (grid[0][0] != '-')) {
            return grid[0][0] + " wins";
        }//check left diagonal
        else if (grid[2][0]==grid[1][1] && grid[1][1]==grid[0][2] && (grid[0][2] != '-')) {
            return grid[1][1] + " wins";
        }//check right diagonal
        else if (freeSpots==0) {
            return "Tie";
        }//check out of spots
        return result;
    }
}