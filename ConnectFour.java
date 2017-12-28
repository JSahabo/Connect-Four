import java.util.Scanner;

/**
 *
 *
 * Program that allows two people to play Connect Four.
 */

public class ConnectFour {

    private static final int ROWS = 6;
    private static final int COLUMNS = 7;
    private static final char COLOR1 = 'r';
    private static final char COLOR2 = 'b';
    public static void main(String[] args) {
        Scanner key = new Scanner(System.in);
        intro();
        letsPlay(key);
        // complete this method
        // Recall make and use one Scanner coonected to System.in
    }


    // prompt the user for an int. The String prompt will
    // be printed out. I expect key is connected to System.in.
    public static int getInt(Scanner key, String prompt) {
        while(!key.hasNextInt()) {
            String notAnInt = key.nextLine();
            System.out.println();
            System.out.println(notAnInt + " is not an integer.");
            System.out.print(prompt);
        }
        int result = key.nextInt();
        key.nextLine();
        return result;
    }

    //Ask the user for their name then plays the game
    public static void letsPlay(Scanner key){
        System.out.print("Player 1 enter your name: ");
        String player1 = key.nextLine();
        System.out.println();
        System.out.print("Player 2 enter your name: ");
        String player2 = key.nextLine();
        playGame(player1, key, player2);
    }

    //Prints out the initial board
    public static void playGame(String player1, Scanner key, String player2){
        System.out.println();
        System.out.println("Current Board");
        System.out.println("1 2 3 4 5 6 7  column numbers");
        char [][] connectfour = new char [ROWS][COLUMNS];
        for (int row = 0; row < ROWS; row++){
            for(int col = 0; col < COLUMNS; col++){
                connectfour[row][col] = '.'; //makes a 2d array filled with '.'
                System.out.print(connectfour[row][col]+ " ");
            }
            System.out.println();
        }
        eachTurn(player1, player2, key, connectfour );
    }

    //Runs the actual game by checking to see if player1 or 2 has won
    public static void eachTurn( String player1, String player2, Scanner key,char [][] connectfour){
        boolean playgame = true; //determines when the game ends depending on the outcome of the game
        while(playgame){
            playgame = playerTurn(player1, key, connectfour,COLOR1); //the first player will play his turn and the program will check to see if he won or not
            if(playgame){
                playgame = playerTurn(player2, key, connectfour, COLOR2); // the second player gets a turn if the first player has not won
            }
        }
    }

    //Tells the player what color they are and gives them instructions
    public static boolean playerTurn(String playername, Scanner key,char [][] connectfour, char playercolor){
        System.out.println();
        System.out.println(playername + " it is your turn.");
        System.out.println("Your pieces are the " + playercolor +"'s.");
        System.out.print(playername +", enter the column to drop your checker: ");
        String prompt = playername +", enter the column to drop your checker: ";
        boolean playgame = getInput(key, prompt, connectfour, playername, playercolor); //
        return playgame;
    }

    //Get the input from the players
    //the input has to be valid or the player will be asked for another
    public static boolean getInput (Scanner key, String prompt, char [][] connectfour, String playername, char playercolor){
        int choice = getInt(key,prompt);
        boolean playgame = checkingChoice(key, connectfour, prompt, choice, playername, playercolor);//checks the input
        return playgame;
    }

    //checks to see if the input is a valid one if not prompts the user for another
    //the player will keep getting asked for a valid input
    public static boolean checkingChoice(Scanner key, char [][] connectfour, String prompt, int choice, String playername, char playercolor){
        boolean playgame = true;
        if(choice <= 0 || choice > COLUMNS){
            System.out.println();
            System.out.println(choice + " is not a valid column.");
            System.out.print(prompt);
            getInput(key, prompt, connectfour, playername, playercolor);//ask the player for another choice since the previous one was not valid
        }
        else if(connectfour[0][choice-1] != '.'){
            System.out.println();
            System.out.println(choice + " is not a legal column. That column is full");
            System.out.print(playername + ", enter the column to drop your checker: ");
            getInput(key, prompt, connectfour, playername, playercolor); //ask the player for another choice since the previous one was not valid
        }
        else {
            theResult(connectfour, choice, playercolor);
            playgame = getWin(connectfour, playername, playercolor); //at this point we know that the input was valid thus we can drop that letter wherever specified
            theBoard(connectfour, playgame);
        }
        return playgame;
    }

    //Drops in character at a specific row and column
    public static void theResult(char [][] connectfour, int choice, char playercolor){
        int rowstuff =  getRows(choice, connectfour);
        connectfour[rowstuff][choice-1] = playercolor;
    }

    //Prints the current board while playing and the final if the game stops
    public static void theBoard(char [][] connectfour, boolean playgame){
        if(playgame){
            System.out.println();
            System.out.println("Current Board");
        }
        else{
            System.out.println();
            System.out.println("Final Board");
        }
        System.out.println("1 2 3 4 5 6 7  column numbers");
        for (int row = 0; row < ROWS; row++){
            for(int col = 0; col < COLUMNS; col++){
                System.out.print(connectfour[row][col]+ " ");
            }
            System.out.println();
        }

    }

    //gets the row number to drop a character at
    public static int getRows(int choice, char [][] connectfour){
        int result = 0;
        for (int row = ROWS-1; row >= 0; row--){
            if( connectfour[row][choice-1] == '.'){
                return row; //returns the row and columns to put a letter in as long as it was filled with another letter
            }
        }
        return result;
    }

    //checks to see if the game is a draw
    public static boolean itsDraw (char [][] connectfour){
        boolean playgame = true;
        int count = 0;
        for (int row = 0; row < ROWS; row++){
            for(int col = 0; col < COLUMNS; col++){
                if( connectfour[row][col] == '.'){
                    count++; //counts up if there is a dot somewhere on the board
                }
            }
        }
        if (count == 0){
            playgame = false; //if the counter is zero,this means that all the dots have been replaced with letters
            System.out.println("The game is a draw.");
        }
        return playgame;
    }

    //returns true or false depending on whether has won or not
    //checks in all directions for a probable win
    public static boolean getWin (char [][] connectfour, String playername, char playercolor){
        boolean playgame = true;
        for (int row = ROWS - 1; row > 0; row--){
            for (int col = 0; col < COLUMNS; col++){
                if(connectfour[row][col] != '.'){
                    char letter = verticalCheck(connectfour, row, col);//checks vertically for 4 in a row
                    char letter2 = horizontalCheck(connectfour, row, col);// checks horizontally for 4 in a row
                    char letter3 = diagonalLeft(connectfour, row, col);// checks diagonally to left for a win
                    char letter4 = diagonalRight(connectfour, row, col);//checks diagonally to the rigth for a win
                    if(letter == playercolor || letter2 == playercolor|| letter3 == playercolor|| letter4 == playercolor){
                        playgame = false;
                    }
                }
            }
        }
        if(playgame) {
            playgame = itsDraw(connectfour);
        }
        else{
            System.out.println();
            System.out.println(playername + " wins!!");
        }
        return playgame;
    }

    //Checks for a vertical win
    public static char verticalCheck(char [][] connectfour, int row, int col){
        int count = 0;
        if(row >= 3){//this prevents the program from going out of bounds when checking
            for (int i = 1; i <= 3; i++){
                if(connectfour[row][col] == connectfour[row-i][col]){
                    count ++;
                }
                if (count == 3){
                    return connectfour[row][col];
                }
            }
        }
        return 'c'; //return c if the 3 letters that were checked were not the same. c was chosen at random
    }

    //cheks for a horizontal win
    public static char horizontalCheck (char [][] connectfour, int row, int col){
        int count = 0;
        if(col <= 3){//makes sure it does go out of bound
            for (int i = 1; i <= 3; i++){
                if(connectfour[row][col] == connectfour[row][col+i]){//checks horizontally
                    count ++;
                }
                if (count == 3){
                    return connectfour[row][col];
                }
            }
        }
        return 'c';
    }

    //Checks for 4 in a row diagonally right
    public static char diagonalRight(char [][] connectfour, int row, int col){
        int count = 0;
        if(row <= 2 && col <= 3){//makes sure it does go out of bound
            for (int i = 1; i <= 3; i++){
                if(connectfour[row][col] == connectfour[row+i][col+i]){//moves down and to the right
                    count ++;
                }
                if (count == 3){
                    return connectfour[row][col];
                }
            }
        }
        return 'c';
    }

    //Checks for 4 in a row diagonally left
    public static char diagonalLeft(char [][] connectfour, int row, int col){
        int count = 0;
        if(row <= 2 && col >= 3){//makes sure it does go out of bound
            for (int i = 1; i <= 3; i++){
                if(connectfour[row][col] == connectfour[row+i][col-i]){
                    count ++;
                }
                if (count == 3){
                    return connectfour[row][col];
                }
            }
        }
        return 'c';
    }

    // show the intro
    public static void intro() {
        System.out.println("This program allows two people to play the");
        System.out.println("game of Connect four. Each player takes turns");
        System.out.println("dropping a checker in one of the open columns");
        System.out.println("on the board. The columns are numbered 1 to 7.");
        System.out.println("The first player to get four checkers in a row");
        System.out.println("horizontally, vertically, or diagonally wins");
        System.out.println("the game. If no player gets fours in a row and");
        System.out.println("and all spots are taken the game is a draw.");
        System.out.println("Player one's checkers will appear as r's and");
        System.out.println("player two's checkers will appear as b's.");
        System.out.println("Open spaces on the board will appear as .'s.\n");
    }
} 