import java.util.*;

public class Tetris {
    public String[][] currentBoard;
    //Represents x, y, and rotation
    public int[] position = {0,0,0};

    //Represents the index of the current piece
    public int active = 0;
    //Represents the held piece, -1 represents empty
    public int held = -1;

    //Define pieces
    public String[][][] pieces = {Piece.I, Piece.L, Piece.J, Piece.O, Piece.S, Piece.T, Piece.Z};

    //Creates a new board and makes it empty
    public Tetris(int x, int y){
        currentBoard = new String[y][x];

        for(int i = 0; i < y; i++){
            for(int j = 0; j < x; j++){
                currentBoard[i][j] = "  ";
            }
        }

        switchActive();
    }

    //Main controller
    public void tick(String action){
        char[] actions = action.toCharArray();
        
        for(int i = 0; i < actions.length; i++){
            switch (actions[i]){
                case 'w':
                    rotate();
                    break;
                case 'a':
                    move(-1);
                    break;
                case 'd':
                    move (1);
                    break;
                case 's':
                    down();
                    break;
                case 'h':
                    hold();
                    break;
                case ' ':
                    drop();
                    break;
            }
        }


        //Gravity
        //If not colliding, move down and return
        if(isValid(new int[]{position[0] + 1, position[1], position[2]})) {
            position[0]++;
            return;
        }

        //Otherwise, add the piece to the current board
        String[][] newPiece = rotateArray(cloneActivePiece(), position[2]);
        for(int i = 0; i < newPiece.length; i++){
            for(int j = 0; j < newPiece[i].length; j++){
                if(!newPiece[i][j].equals("  ")) {
                    currentBoard[position[0] + i][position[1] + j] = newPiece[i][j];
                }
            }
        }

        //Change active piece
        switchActive();

        //Check for full lines
        for(int i = currentBoard.length - 1; i >= 0; i--){
            if(!Arrays.asList(currentBoard[i]).contains("  ")){
                //If found, move all lines above it downwards
                for(int j = i; j > 0; j--){
                    currentBoard[j] = currentBoard[j - 1];
                }
                Arrays.fill(currentBoard[0], "  ");

                i++;
            }
        }

        /*
        TODO:
        Add score and lines cleared

        Better controls
        Gravity every other tick?
        Fix bugs?
        */

    }

    public void move(int direction){
        if(isValid(new int[]{position[0], position[1] + direction, position[2]})){
            position[1] += direction;
        }

    }

    public void down(){
        if(isValid(new int[]{position[0] + 1, position[1], position[2]})){
            position[0]++;
        }
    }
    
    public void rotate(){
        //Current position
        if(isValid(new int[]{position[0], position[1], position[2] + 1})){
            position[2]++;
            return;
        }

        //Left
        if(isValid(new int[]{position[0], position[1] - 1, position[2] + 1})){
            position[1]--;
            position[2]++;
            return;
        }

        //Right
        if(isValid(new int[]{position[0], position[1] + 1, position[2] + 1})){
            position[1]++;
            position[2]++;
            return;
        }

        //Up
        if(isValid(new int[]{position[0] - 1, position[1], position[2] + 1})){
            position[0]--;
            position[2]++;
            return;
        }

        //Up 2
        if(isValid(new int[]{position[0] - 2, position[1], position[2] + 1})){
            position[0] -= 2;
            position[2]++;
        }
    }

    public void drop(){
        while(isValid(new int[]{position[0] + 1, position[1], position[2]})){
            position[0]++;
        }
    }

    //Checks if a piece can be at a give position
    public boolean isValid(int[] newPosition){
        //Rotates the piece from the stored value
        String[][] activePiece = rotateArray(cloneActivePiece(), newPosition[2]);

        //Scans through each tile of the piece index
        for(int i = 0; i < activePiece.length; i++){
            for(int j = 0; j < activePiece[0].length; j++){
                //Ignore blank tiles
                if(activePiece[i][j].equals("  ")){
                    continue;
                }

                //Check if it is out of bounds to the left or right
                if(newPosition[1] + j < 0 || newPosition[1] + j >= currentBoard[0].length){
                    return false;
                }

                //Checks if it is out of bounds to the bottom
                if(newPosition[0] + i >= currentBoard.length){
                    return false;
                }

                //Check if it is overlapping a tile
                if(!currentBoard[newPosition[0] + i][newPosition[1] + j].equals("  ")) {
                    return false;
                }
            }
        }

        //Returns true if allowed
        return true;
    }

    //Swaps the active and held pieces, or if hold is empty, generate a new piece
    public void hold(){
        if(held == -1){
            held = active;
            switchActive();
            return;
        }

        int temp = held;
        held = active;

       switchActive(temp);
    }

    //Switches active tile to a known tile
    public void switchActive(int piece){
        active = piece;

        //Move the piece to the top of the screen and set it to a random rotation
        position[0] = 0;
        position[1] = (currentBoard[0].length - pieces[active].length) / 2;
        position[2] = (int)(Math.random() * 4);
    }

    //Switches active piece to a random piece
    public void switchActive(){
        //Choose a new random piece
        active = (int)(Math.random() * pieces.length);

        //Move the piece to the top of the screen and set it to a random rotation
        position[0] = 0;
        position[1] = (currentBoard[0].length - pieces[active].length) / 2;
        position[2] = (int)(Math.random() * 4);
    }

    public String display(){
        //Define colors
        //Orange is now white, orange does not exist in these color codes
        ArrayList<String> codes = new ArrayList<String>(Arrays.asList("cy", "bl", "or", "yl", "gr", "pr", "rd", "  "));
        ArrayList<String> colors = new ArrayList<String>(Arrays.asList(ColorCodes.CYAN_BACKGROUND, ColorCodes.BLUE_BACKGROUND, ColorCodes.WHITE_BACKGROUND, ColorCodes.YELLOW_BACKGROUND, ColorCodes.GREEN_BACKGROUND, ColorCodes.PURPLE_BACKGROUND, ColorCodes.RED_BACKGROUND, ColorCodes.BLACK_BACKGROUND));
        String reset = ColorCodes.ANSI_RESET;

        StringBuilder output = new StringBuilder();
        String[][] newBoard = overlay();

        for(int i = 0; i < newBoard.length; i++){
            for(int j = 0; j < newBoard[i].length; j++){
                output.append(colors.get(codes.indexOf(newBoard[i][j])));
                output.append("   ");
                output.append(reset);
            }
            output.append("\n");
        }

        /*
        TODO:
        Add score and lines cleared
        Shadow
        */

        return output.toString();
    }

    //Because apparently setting one array to another links them for some reason
    public String[][] cloneCurrentBoard(){
        String[][] output = new String[currentBoard.length][currentBoard[0].length];

        for(int i = 0; i < currentBoard.length; i++){
            for(int j = 0; j < currentBoard[0].length; j++){
                output[i][j] = currentBoard[i][j];
            }
        }

        return output;
    }
    
    //I hate java
    public String[][] cloneActivePiece(){
        String[][] output = new String[pieces[active].length][pieces[active][0].length];
        
        for(int i = 0; i < pieces[active].length; i++){
            for(int j = 0; j < pieces[active][0].length; j++){
                output[i][j] = pieces[active][i][j];
            }
        }
        
        return output;
    }

    //Overlays the current board and the active piece
    public String[][] overlay(){

        String[][] newBoard = cloneCurrentBoard();
        String[][] activePiece = rotateArray(cloneActivePiece(), position[2]);


        for(int i = 0; i < activePiece.length; i++){
            for(int j = 0; j < activePiece[i].length; j++){
                if(!activePiece[i][j].equals("  ")){
                    newBoard[position[0] + i][position[1] + j] = activePiece[i][j];
                }
            }
        }

        return newBoard;
    }

    //Returns a rotated array
    public <T> T[][] rotateArray(T[][] arr, int times){
        times %= 4;

        for(int t = 0; t < times; t++) {
            int n = arr.length;

            // Rotate the array
            for (int i = 0; i < n / 2; i++) {
                for (int j = i; j < n - i - 1; j++) {
                    T temp = arr[i][j];

                    arr[i][j] = arr[n - 1 - j][i];
                    arr[n - 1 - j][i] = arr[n - 1 - i][n - 1 - j];
                    arr[n - 1 - i][n - 1 - j] = arr[j][n - 1 - i];
                    arr[j][n - 1 - i] = temp;
                }
            }
        }

        return arr;
    }
}
