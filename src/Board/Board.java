package Board;

import java.util.*;

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
        recreateBoard();
    }

    public void recreateBoard(){
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
        // Build a shuffled list of all 81 cell coordinates
        ArrayList<int[]> cells = new ArrayList<>();
        for(int i = 0; i < 9; i++){
            for(int j = 0; j < 9; j++){
                cells.add(new int[]{i, j});
            }
        }
        Collections.shuffle(cells, rand);

        // Attempt to blank each cell in shuffled order
        for(int[] cell : cells){
            int curI = cell[0];
            int curJ = cell[1];

            //removes the number in the current grid
            int block = checkBlock(curI, curJ);
            rowTracker.get(curI).remove(Integer.valueOf(board[curI][curJ]));
            columnTracker.get(curJ).remove(Integer.valueOf(board[curI][curJ]));
            blockTracker.get(block).remove(Integer.valueOf(board[curI][curJ]));
            board[curI][curJ] = 0;

            //checks for number of solutions across the entire board
            //if number of solutions is not exactly 1, this cell cannot be blank
            if(checkSolutions() != 1){
                board[curI][curJ] = solution[curI][curJ];
                rowTracker.get(curI).add(board[curI][curJ]);
                columnTracker.get(curJ).add(board[curI][curJ]);
                blockTracker.get(block).add(board[curI][curJ]);
            }
        }
    }

    /**
     * Counts the number of solutions the current board state has.
     * Uses recursive backtracking. Caps counting at 2 to avoid unnecessary work —
     * we only care whether the answer is exactly 1.
     * @return number of solutions found (capped at 2)
     */
    private int checkSolutions(){
        return checkSolutionsHelper(0, 0);
    }

    private int checkSolutionsHelper(int startI, int startJ){
        // Find the next empty cell from the given starting position
        int curI = startI;
        int curJ = startJ;
        while(curI < 9 && board[curI][curJ] != 0){
            if(curJ == 8){
                curJ = 0;
                curI++;
            }else{
                curJ++;
            }
        }

        // If no empty cell was found, we've reached a complete solution
        if(curI == 9) return 1;

        int block = checkBlock(curI, curJ);
        int totSolutions = 0;

        // Try each candidate number in this cell
        for(int num = 1; num <= 9; num++){
            if(!rowTracker.get(curI).contains(num)
                    && !columnTracker.get(curJ).contains(num)
                    && !blockTracker.get(block).contains(num)){

                // Place the number temporarily
                board[curI][curJ] = num;
                rowTracker.get(curI).add(num);
                columnTracker.get(curJ).add(num);
                blockTracker.get(block).add(num);

                // Recurse to fill the rest of the board
                int nextJ = (curJ == 8) ? 0 : curJ + 1;
                int nextI = (curJ == 8) ? curI + 1 : curI;
                totSolutions += checkSolutionsHelper(nextI, nextJ);

                // Remove the number (backtrack)
                board[curI][curJ] = 0;
                rowTracker.get(curI).remove(Integer.valueOf(num));
                columnTracker.get(curJ).remove(Integer.valueOf(num));
                blockTracker.get(block).remove(Integer.valueOf(num));

                // Cap at 2, if we already found more than 1 solution, no need to keep searching
                if(totSolutions > 1) return totSolutions;
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

    /**
     * Function for changing the value of a cell
     * @param i i position of the cell
     * @param j j position of the cell
     * @param value value to change cell to
     */
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

    /**
     * Checks if the board is solved
     * @return True if yes, false otherwise
     */
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

    /**
     * Returns an ArrayList containing cells that have broken Sudoku rules
     * @return int array ArrayList containing i and j positions of every cell that broke Sudoku rules
     */
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
                temp += solution[i][j] + " ";
            }
            temp.trim();
            temp += "\n";
        }
        return temp;
    }
}
