package ctrl;

import gui.Screen;
import net.Sender;

public final class TicTacToe {
    
    private int[][] grid;
    private int lastPlay;
    private final Screen screen;
    private final Sender sender;

    public TicTacToe(Screen screen) {
        this.screen = screen;
        sender = new Sender(this);
        reset();
    }
    
    public void reset() {
        lastPlay = 2;
        grid = new int[3][3];
        for(int i = 0; i < 3; i++)
            for(int j = 0; j < 3; j++)
                grid[i][j] = 0;
        for(int i = 0; i < 10; i++)
            screen.changeButtonColor(0, i);
    }
    
    public int winner() {
        for(int player = 1; player <= 2; player++){
            if((grid[0][0] == grid[1][1] && grid[0][0] == grid[2][2] && grid[0][0] == player) ||
               (grid[0][2] == grid[1][1] && grid[0][2] == grid[2][0] && grid[0][2] == player))
                return player;
            for(int i = 0; i < 3; i++)
                if((grid[i][0] == grid[i][1] && grid[i][0] == grid[i][2] && grid[i][0] == player) ||
                   (grid[0][i] == grid[1][i] && grid[0][i] == grid[2][i] && grid[0][i] == player))
                    return player;
        }
        return 0;
    }
    
    public void makePlay(int player, int pos) {
        if(grid[ pos / 3 ][ pos % 3 ] == 0 && !isOver() && lastPlay != player){
            lastPlay = player;
            grid[ pos / 3 ][ pos % 3 ] = player;
            screen.changeButtonColor(player, pos+1);
            if(player == 1){
                sender.writePlay(pos);
                sender.readPlay();
            }
        }
    }

    public void connect(String address, String port, boolean isTcp) {
        sender.connect(address, port, isTcp);
        lastPlay = 2;
    }
    
    public void connected(){
        lastPlay = 1;
        sender.readPlay();
    }
    
    public boolean isOver() {
        if(winner() != 0){
            screen.createDialog("Jogador " + winner() + " venceu!", "Resultado");
            return true;
        }
        
        for(int i = 0; i < 3; i++)
            for(int j = 0; j < 3; j++)
                if(grid[i][j] == 0)
                    return false;
        
        screen.createDialog("Deu velha!", "Resultado");
        return true;
    }

    public void setLastPlay(int lastPlay) {
        this.lastPlay = lastPlay;
    }
}
