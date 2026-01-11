import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

//class containing the logic of the sudoku board
public class Board {
    int[][] board;
    //randomizer for number generation
    final Random rand = new Random();
    //Hashmaps for keeping track which numbers have been used in each column, row, or block
    HashMap<Integer, ArrayList<Integer>> rowTracker = new HashMap<>();
    HashMap<Integer, ArrayList<Integer>> columnTracker = new HashMap<>();
    HashMap<Integer, ArrayList<Integer>> blockTracker = new HashMap<>();
    //3d ArrayList[i][j][usedNumbers], keeps track which numbers have been tried in each cell
    ArrayList<ArrayList<ArrayList<Integer>>> usedNumbers = new ArrayList<>();

    /**
     * constructor for board class
     */
    public Board(){
        this.board = new int[9][9];

        for(int i = 0; i < 9; i++){
            usedNumbers.add(new ArrayList<>());
            rowTracker.put(i, new ArrayList<>());
            columnTracker.put(i, new ArrayList<>());
            blockTracker.put(i, new ArrayList<>());
            for(int j = 0; j < 9; j++){
                usedNumbers.get(i).add(new ArrayList<>());;
            }
        }
        generateBoard();
    }

    /**
     * Function for generating the 9x9 Sudoku board
     */
    private void generateBoard(){
        //loops through each cell of the sudoku board
        for(int i = 0; i < 9; i++){
            for(int j = 0; j < 9; j++){

                //uses the generateNumber function to find a valid number to generate
                int number = generateNumber(i, j);

                //if there wasn't a valid number to generate, need to backtrack to earlier cell to fix
                if(number == -1){
                    if(j == 0){
                        board[i][j] = 0;
                        i--;
                        j = 7;
                        backTrack(i, j + 1);
                    }else{
                        board[i][j] = 0;
                        j -= 2;
                        backTrack(i, j + 1);
                    }
                }else{ //otherwise, there was a valid number to generate, add the number to the current cell
                    board[i][j] = number;
                }
            }
        }

    }

    /**
     * Function for generating a number for specified cell in the board
     * @param i row index
     * @param j col index
     * @return -1 if no valid number was found, otherwise returns the first valid number found
     */
    private int generateNumber(int i, int j) {
        //array of all possible numbers in sudoku
        int[] numbers = {1,2,3,4,5,6,7,8,9};

        //fisher yates shuffle to randomize the order of numbers to test
        for(int k = numbers.length - 1; k > 0; k--){
            int randVal = rand.nextInt(k + 1);
            int a = numbers[randVal];
            numbers[randVal] = numbers[k];
            numbers[k] = a;
        }

        //checks which block the current cell belongs to
        int blockNumber = checkBlock(i, j);

        //loops through all the possible numbers, in shuffled order
        for(int k = 0; k < 9; k++){
            //if the number doesn't break any sudoku rules
            if(!rowTracker.get(i).contains(numbers[k]) && !columnTracker.get(j).contains(numbers[k]) && !blockTracker.get(blockNumber).contains(numbers[k]) && !usedNumbers.get(i).get(j).contains(numbers[k])){
                //add the number to their respective trackers
                rowTracker.get(i).add(numbers[k]);
                columnTracker.get(j).add(numbers[k]);
                blockTracker.get(blockNumber).add(numbers[k]);
                //return the number
                return numbers[k];
            }
            //if the number hasn't already been tested in the current cell, add it to the list of used numbers
            if(!usedNumbers.get(i).get(j).contains(numbers[k])){
                usedNumbers.get(i).get(j).add(numbers[k]);
            }
        }
        //if the function reached here, then no valid number exists for the current cell, return -1
        return -1;
    }

    /**
     * Function for rolling back a cell's trackers
     * @param i row index
     * @param j col index
     */
    private void backTrack(int i, int j){
        //checks which block the current cell is in
        int blockNumber = checkBlock(i, j);
        //since we're rolling back, then the current number in this cell doesn't work, so add it to the list of used numbers
        usedNumbers.get(i).get(j).add(board[i][j]);

        //remove the current number in the cell from every tracker
        columnTracker.get(j).remove(Integer.valueOf(board[i][j]));
        rowTracker.get(i).remove(Integer.valueOf(board[i][j]));
        blockTracker.get(blockNumber).remove(Integer.valueOf(board[i][j]));
        board[i][j] = 0;
        if(j == 8){
            usedNumbers.get(i+1).getFirst().clear();
        }else{
            usedNumbers.get(i).get(j + 1).clear();
        }
    }

    /**
     * Function for checking which grid the indices i and j belong to
     * @param i index i
     * @param j index j
     * @return The grid number of the indices, -1 if invalid indices
     */
    private int checkBlock(int i, int j){
        int tempI = Math.floorDiv(i, 3);
        int tempJ = Math.floorDiv(j, 3);
        if(tempI == 0){
            if(tempJ == 0){
                return 0;
            }else if(tempJ == 1){
                return 1;
            }else if(tempJ == 2){
                return 2;
            }
        }else if(tempI == 1){
            if(tempJ == 0){
                return 3;
            }else if(tempJ == 1){
                return 4;
            }else if(tempJ == 2){
                return 5;
            }
        }else if(tempI == 2){
            if(tempJ == 0){
                return 6;
            }else if(tempJ == 1){
                return 7;
            }else if(tempJ == 2){
                return 8;
            }
        }
        return -1;
    }

    /**
     * Returns the int value of the element at specified cell
     * @param i row index
     * @param j col index
     * @return integer containing the value in the cell
     */
    public int valueOf(int i, int j){
        return board[i][j];
    }

    /**
     * Turns the current sudoku board into a string
     * @return A string containing the layout of the current sudoku board
     */
    @Override
    public String toString() {
        String temp = "";
        for (int i = 0; i < 9; i++){
            for (int j = 0; j < 9; j++){
                temp += board[i][j] + " ";
            }
            temp.trim();
            temp += "\n";
        }
        return temp;
    }
}
