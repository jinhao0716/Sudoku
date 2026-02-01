import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;

//class containing the logic of the sudoku board
public class Board {
    int[][] board;
    int[][] solution;
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
        this.solution = new int[9][9];

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
        for(int i = 0; i < 9; i++){
            for(int j = 0; j < 9; j++){
                solution[i][j] = valueOf(i, j);
            }
        }
        generateBlanks();
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

    private void generateBlanks(){
        int[][] boardCoords = new int[81][2];
        int x = 0;
        int y = 0;
        for(int i = 0; i < 81; i++){
            boardCoords[i][0] = x;
            boardCoords[i][1] = y;
            y++;
            if(y == 9){
                y = 0;
                x++;
            }
        }

        for(int k = boardCoords.length - 1; k > 0; k--){
            int randVal = rand.nextInt(k + 1);
            int[] a = boardCoords[randVal];
            boardCoords[randVal] = boardCoords[k];
            boardCoords[k] = a;
        }


        for(int k = 0; k < boardCoords.length; k++){
            int i = boardCoords[k][0];
            int j = boardCoords[k][1];

            int block = checkBlock(i, j);
            int temp = board[i][j];

            rowTracker.get(i).remove(Integer.valueOf(temp));
            columnTracker.get(j).remove(Integer.valueOf(temp));
            blockTracker.get(block).remove(Integer.valueOf(temp));

            board[i][j] = 0;
            if(checkSolutions() == 0 || checkSolutions() > 1){
                board[i][j] = temp;
                rowTracker.get(i).add(temp);
                columnTracker.get(j).add(temp);
                blockTracker.get(block).add(temp);
            }
        }
    }

    private int checkSolutions(){
        int totSolutions = 0;
        for(int i = 0; i < 9; i++){
            for(int j = 0; j < 9; j++){
                int curSolutions = 0;
                int block = checkBlock(i, j);
                if(board[i][j] == 0){
                    for(int k = 1; k < 10; k++){
                        if(!rowTracker.get(i).contains(k) && !columnTracker.get(j).contains(k) && !blockTracker.get(block).contains(k)){
                            curSolutions++;
                            if(curSolutions > totSolutions){
                                totSolutions = curSolutions;
                            }
                        }
                    }
                }
            }
        }

        return totSolutions;
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
    public int checkBlock(int i, int j){
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

    public void changeValue(int i, int j, int value){
        int blockNumber = checkBlock(i, j);

        if(board[i][j] != 0){
            rowTracker.get(i).remove(Integer.valueOf(board[i][j]));
            columnTracker.get(j).remove(Integer.valueOf(board[i][j]));
            blockTracker.get(blockNumber).remove(Integer.valueOf(board[i][j]));
        }

        if(value != 0){
            rowTracker.get(i).add(value);
            columnTracker.get(j).add(value);
            blockTracker.get(blockNumber).add(value);
        }
        board[i][j] = value;
    }

    public boolean checkVictory(){
        for(int i = 0; i < 9; i++){
            for(int j = 0; j < 9; j++){
                if(board[i][j] != solution[i][j]){
                    return false;
                }
            }
        }
        return true;
    }

    public ArrayList<int[]> violatedCells(){
        ArrayList<int[]> temp = new ArrayList<>();

        for(int i = 0; i < 9; i++){
            for(int j = 0; j < 9; j++){
                int blockNumber = checkBlock(i, j);
                int rowCount = Collections.frequency(rowTracker.get(i), board[i][j]);
                int colCount = Collections.frequency(columnTracker.get(j), board[i][j]);
                int blockCount = Collections.frequency(blockTracker.get(blockNumber), board[i][j]);
                if(rowCount >= 2){
                    for(int k = 0; k < 9; k++){
                        if(board[i][k] == board[i][j]){
                            temp.add(new int[]{i, k});
                        }
                    }
                }
                if(colCount >= 2){
                    for(int k = 0; k < 9; k++){
                        if(board[k][j] == board[i][j]){
                            temp.add(new int[]{k, j});
                        }
                    }
                }
                if(blockCount >= 2){
                    int gridI = i - (i % 3);
                    int gridJ = j - (j % 3);
                    for(int k = 0; k < 9; k++){
                        if(board[gridI][gridJ] == board[i][j]){
                            temp.add(new int[]{gridI, gridJ});
                        }

                        gridJ++;
                        if(gridJ % 3 == 0){
                            gridJ = j - (j % 3);
                            gridI++;
                        }
                    }
                }
            }
        }

        return temp;
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
