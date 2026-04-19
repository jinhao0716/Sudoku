package Graphic;

import Board.Board;
import com.sun.tools.javac.Main;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.plaf.basic.BasicButtonUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import static java.awt.Font.BOLD;

public class Graphic extends javax.swing.JPanel implements ActionListener {

    //global variables
    //Jframes and panels
    JFrame frame;
    JPanel panel1;
    JPanel panel2;
    JPanel panel3;
    JPanel panel4;
    JPanel panel5;

    //randomizer for background images
    final Random rand = new Random();

    //Sudoku game board class
    Board board;

    //holds each cell of the Sudoku board
    JButton[][] cells;

    //holds the array of buttons for changing numbers on the Sudoku board
    JButton[] addButton = new JButton[10];

    //array for checking which cell on the board is currently highlighted
    int[][] selected = {{-1,-1}};

    //stores numbers on the Sudoku board that are inputted by the user
    boolean[][] placed = new boolean[9][9];

    int width;
    int height;

    HashMap<Integer, String> backgroundList;

    //colors
    Color LIGHT_BLUE = new Color(156,200,255);
    Color LIGHTER_BLUE = new Color(201,240,255);
    Color LIGHT_RED = new Color(245, 130, 130);

    public Graphic(Board board){
        this.board = board;

        backgroundList = new HashMap<>();

        backgroundList.put(1,"background1.jpg");
        backgroundList.put(2,"background2.jpg");
        backgroundList.put(3,"background3.jpg");
        backgroundList.put(4,"background4.jpg");
        backgroundList.put(5,"background5.jpg");
        backgroundList.put(6,"background6.jpg");
        backgroundList.put(7,"background7.jpg");
        backgroundList.put(8,"background8.jpg");
        backgroundList.put(9,"background9.jpg");
        backgroundList.put(10,"background10.jpg");


        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        width = (int) screen.getWidth();
        height = (int) screen.getHeight();

        //Initializes the JFrame
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setMinimumSize(screen);
        frame.setMaximumSize(screen);

        /*
        Sets the JFrame layout to BorderLayout and names the window
         */
        frame.setLayout(new BorderLayout());
        frame.setTitle("Sudoku");
        frame.pack();

        //center
        panel1 = new JPanel();
        panel1.setSize(new Dimension(frame.getWidth(), frame.getHeight()));
        panel1.setLayout(new GridLayout(9, 9));

        //left
        panel2 = new JPanel();
        panel2.setLayout(new GridLayout(1, 1));
        JButton placeHolder3 = new JButton("Placeholder");
        placeHolder3.setPreferredSize(new Dimension(width/3, height));
        placeHolder3.setVisible(false);
        panel2.add(placeHolder3);

        //right
        panel3 = new JPanel();
        panel3.setLayout(new GridLayout(4, 3));
        panel3.setPreferredSize(new Dimension(width/3, height));
        panel3.setLayout(new GridLayout(4, 3, 1, 1));
        panel3.setBorder(BorderFactory.createEmptyBorder((int)(panel3.getPreferredSize().getHeight()/12),(int)(panel3.getPreferredSize().getWidth()/4),(int)(panel3.getPreferredSize().getHeight()/12),(int)(panel3.getPreferredSize().getWidth()/4)));


        //top
        panel4 = new JPanel();
        panel4.setLayout(new GridLayout(1, 3));
        JLabel placeholder1 = new JLabel("TEST");
        placeholder1.setFont(new Font("Arial", BOLD, 30));
        placeholder1.setPreferredSize(new Dimension(width/3, (int)(height/3.5)));
        placeholder1.setVisible(false);
        panel4.add(placeholder1);

        JLabel title = new JLabel("SUDOKU");
        title.setHorizontalAlignment(0);
        title.setPreferredSize(new Dimension(width/3, height/4));
        title.setFont(new Font("Times New Roman", BOLD, (int)(title.getPreferredSize().getHeight()/3)));
        title.setForeground(Color.WHITE);
        panel4.add(title);

        JLabel placeholder2 = new JLabel("TEST");
        placeholder2.setFont(new Font("Arial", BOLD, 30));
        placeholder2.setPreferredSize(new Dimension(width/4, height/4));
        placeholder2.setVisible(false);
        panel4.add(placeholder2);

        //bottom
        panel5 = new JPanel();
        panel5.setLayout(new GridLayout(1,1));
        JButton placeHolder4 = new JButton("Placeholder");
        placeHolder4.setPreferredSize(new Dimension(width, height/5));
        placeHolder4.setVisible(false);
        panel5.add(placeHolder4);

        matchBoard();

        //creates a button for each possible input number
        for(int i = 0; i < 9; i++) {
            addButton[i] = new JButton("" + (i + 1));
            addButton[i].setBackground(LIGHTER_BLUE);
            addButton[i].setPreferredSize(new Dimension(width/40, height/40));
            addButton[i].addActionListener(this);
            panel3.add(addButton[i]);
        }

        JButton temp = new JButton();
        temp.setVisible(false);
        panel3.add(temp);
        addButton[9] = new JButton("⌦");
        addButton[9].setBackground(LIGHTER_BLUE);
        addButton[9].setPreferredSize(new Dimension(width/40, height/40));
        addButton[9].addActionListener(this);
        panel3.add(addButton[9]);


        frame.setResizable(false);
        frame.setVisible(true);

    }

    private void matchBoard(){
        //try catch block for checking for IO exceptions
        try{
            int max = 10;
            int min = 1;
            int randVal = rand.nextInt(max - min + 1) + min;

            //first try loading from JAR resources
            InputStream imgStream = getClass().getResourceAsStream("/Graphic/backgrounds/" + backgroundList.get(randVal));

            //if not found, try the backgrounds folder next to the JAR
            if (imgStream == null) {
                File f = new File(new File(Main.class.getProtectionDomain()
                        .getCodeSource()
                        .getLocation()
                        .toURI())
                        .getParentFile(), "backgrounds/" + backgroundList.get(randVal));
                imgStream = new FileInputStream(f);
            }

            BufferedImage background = ImageIO.read(imgStream);
            Image newImage = background.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            JLabel backgroundLabel = new JLabel(new ImageIcon(newImage));
            frame.setContentPane(backgroundLabel);

        }catch(IOException e){
            System.out.println(e.getMessage());
            System.exit(1);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }

        //reset layout and re-add all panels after setContentPane
        frame.setLayout(new BorderLayout());
        frame.add(panel1, BorderLayout.CENTER);
        frame.add(panel2, BorderLayout.LINE_START);
        frame.add(panel3, BorderLayout.LINE_END);
        frame.add(panel4, BorderLayout.PAGE_START);
        frame.add(panel5, BorderLayout.PAGE_END);
        panel1.setOpaque(false);
        panel2.setOpaque(false);
        panel3.setOpaque(false);
        panel4.setOpaque(false);
        panel5.setOpaque(false);

        panel1.removeAll();
        panel1.repaint();

        cells = new JButton[9][9];
        //creates a button for each cell of the Sudoku board
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if(board.valueOf(i, j) == 0) {
                    placed[i][j] = true;
                    cells[i][j] = new JButton("");
                    cells[i][j].setForeground(Color.BLUE);
                    cells[i][j].addActionListener(this);
                }else{
                    placed[i][j] = false;
                    cells[i][j] = new JButton(Integer.toString(board.valueOf(i, j)));
                }
                cells[i][j].setFont(new Font("Arial", BOLD, height/50));
                cells[i][j].setUI(new BasicButtonUI());
                cells[i][j].setOpaque(true);
                cells[i][j].setBackground(Color.WHITE);
                cells[i][j].setPreferredSize(new Dimension(10, 10));
                if (i == 0 && j == 0) {
                    cells[i][j].setBorder(BorderFactory.createMatteBorder(4, 4, 1, 1, Color.BLACK));
                } else if (i == 0 && j == 8) {
                    cells[i][j].setBorder(BorderFactory.createMatteBorder(4, 1, 1, 4, Color.BLACK));
                } else if (i == 8 && j == 0) {
                    cells[i][j].setBorder(BorderFactory.createMatteBorder(1, 4, 4, 1, Color.BLACK));
                } else if (i == 8 && j == 8) {
                    cells[i][j].setBorder(BorderFactory.createMatteBorder(1, 1, 4, 4, Color.BLACK));
                } else if (i == 0 && (j == 2 || j == 5)) {
                    cells[i][j].setBorder(BorderFactory.createMatteBorder(4, 1, 1, 3, Color.BLACK));
                } else if (i == 8 && (j == 2 || j == 5)) {
                    cells[i][j].setBorder(BorderFactory.createMatteBorder(1, 1, 4, 3, Color.BLACK));
                } else if ((i == 2 || i == 5) && (j == 2 || j == 5)) {
                    cells[i][j].setBorder(BorderFactory.createMatteBorder(1, 1, 3, 3, Color.BLACK));
                } else if ((i == 2 || i == 5) && j == 0) {
                    cells[i][j].setBorder(BorderFactory.createMatteBorder(1, 4, 3, 1, Color.BLACK));
                } else if ((i == 2 || i == 5) && j == 8) {
                    cells[i][j].setBorder(BorderFactory.createMatteBorder(1, 1, 3, 4, Color.BLACK));
                } else if (i == 2 || i == 5) {
                    cells[i][j].setBorder(BorderFactory.createMatteBorder(1, 1, 3, 1, Color.BLACK));
                } else if (j == 2 || j == 5) {
                    cells[i][j].setBorder(BorderFactory.createMatteBorder(1, 1, 1, 3, Color.BLACK));
                } else if (i == 0) {
                    cells[i][j].setBorder(BorderFactory.createMatteBorder(4, 1, 1, 1, Color.BLACK));
                } else if (i == 8) {
                    cells[i][j].setBorder(BorderFactory.createMatteBorder(1, 1, 4, 1, Color.BLACK));
                } else if (j == 0) {
                    cells[i][j].setBorder(BorderFactory.createMatteBorder(1, 4, 1, 1, Color.BLACK));
                } else if (j == 8) {
                    cells[i][j].setBorder(BorderFactory.createMatteBorder(1, 1, 1, 4, Color.BLACK));
                } else {
                    cells[i][j].setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));
                }
                panel1.add(cells[i][j]);
            }
        }
        frame.revalidate();
        frame.repaint();
    }

    /**
     * Checks which button was pressed and perform corresponding action
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        boolean add = false;

        //checks if the button that was pressed was one of the input buttons
        for(int i = 0; i < 10; i++) {
            if(addButton[i].getModel().isRollover()) {
                add = true;
                if(i == 9){
                    cells[selected[0][0]][selected[0][1]].setText("");
                    board.changeValue(selected[0][0], selected[0][1], 0);
                    clearColor();
                    colorWrong();
                }else if(selected[0][0] != -1 && selected[0][1] != -1){
                    cells[selected[0][0]][selected[0][1]].setText(addButton[i].getText());
                    board.changeValue(selected[0][0], selected[0][1], Integer.parseInt(addButton[i].getText()));

                    clearColor();
                    colorWrong();
                    if(board.checkVictory()){
                        Object[] options = {"Yes", "No"};

                        int result = JOptionPane.showOptionDialog(
                                null,
                                "You win! Do you want to start another game?",
                                "Congratulations",
                                JOptionPane.YES_NO_OPTION,
                                JOptionPane.INFORMATION_MESSAGE,
                                null,
                                options,
                                options[0]
                        );

                        if (result == JOptionPane.YES_OPTION) {
                            //if yes was clicked, start a new game
                            board.recreateBoard();
                            matchBoard();
                            repaint();
                        } else if (result == JOptionPane.NO_OPTION || result == -1) {
                            //if no was clicked, close the window and end the program
                            frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
                            System.exit(0);
                        }
                    }
                }
            }
        }

        //if it wasn't an input button, then it was a cell on the board, highlight the appropriate cells
        if(!add){
            for(int i = 0; i < 9; i++) {
                for(int j = 0; j < 9; j++) {
                    if (cells[i][j].getModel().isRollover()) {
                        if(selected[0][0] != -1 && selected[0][1] != -1){
                            cells[selected[0][0]][selected[0][1]].setBackground(Color.WHITE);
                            uncolorCol();
                            uncolorRow();
                            uncolorGrid();
                        }
                        cells[i][j].setBackground(LIGHT_BLUE);
                        colorCol(i, j);
                        colorRow(i, j);
                        colorGrid(i, j);

                        selected[0][0] = i;
                        selected[0][1] = j;
                    }
                }
            }
        }
    }

    /**
     * Colors every cell of the board that has violated Sudoku properties red
     */
    private void colorWrong(){
        ArrayList<int[]> temp = board.violatedCells();
        for(int k = 0; k < temp.size(); k++){
            if(!placed[temp.get(k)[0]][temp.get(k)[1]]){
                cells[temp.get(k)[0]][temp.get(k)[1]].setBackground(LIGHT_RED);
            }
        }
    }

    /**
     * Colors the grid of the current clicked cell light blue
     * @param i i position of the current cell
     * @param j j position of the current cell
     */
    private void colorGrid(int i, int j){
        //ArrayList for recording which red cells were overwritten by light blue
        ArrayList<int[]> replaced = new ArrayList<>();

        int iPos = i % 3;
        int jPos = j % 3;

        int gridI = i - (i % 3);
        int gridJ = j - (j % 3);
        for(int k = 0; k < 9; k++){
            if(cells[gridI][gridJ].getBackground().equals(LIGHT_RED)){
                replaced.add(new int[]{gridI, gridJ});
            }

            gridJ++;
            if(gridJ % 3 == 0){
                gridJ = j - (j % 3);
                gridI++;
            }
        }

        //for when center of grid is clicked
        if(iPos == 1 && jPos == 1){
            cells[i - 1][j - 1].setBackground(LIGHTER_BLUE);
            cells[i - 1][j + 1].setBackground(LIGHTER_BLUE);
            cells[i + 1][j - 1].setBackground(LIGHTER_BLUE);
            cells[i + 1][j + 1].setBackground(LIGHTER_BLUE);
        }

        //for when top left of grid is clicked
        if(iPos == 0 && jPos == 0){
            cells[i + 1][j + 1].setBackground(LIGHTER_BLUE);
            cells[i + 1][j + 2].setBackground(LIGHTER_BLUE);
            cells[i + 2][j + 1].setBackground(LIGHTER_BLUE);
            cells[i + 2][j + 2].setBackground(LIGHTER_BLUE);
        }

        //for when bottom right of grid is clicked
        if(iPos == 2 && jPos == 2){
            cells[i - 1][j - 1].setBackground(LIGHTER_BLUE);
            cells[i - 1][j - 2].setBackground(LIGHTER_BLUE);
            cells[i - 2][j - 1].setBackground(LIGHTER_BLUE);
            cells[i - 2][j - 2].setBackground(LIGHTER_BLUE);
        }

        //for when top right of grid is clicked
        if(iPos == 0 && jPos == 2){
            cells[i + 1][j - 1].setBackground(LIGHTER_BLUE);
            cells[i + 1][j - 2].setBackground(LIGHTER_BLUE);
            cells[i + 2][j - 1].setBackground(LIGHTER_BLUE);
            cells[i + 2][j - 2].setBackground(LIGHTER_BLUE);
        }

        //for when bottom left of grid is clicked
        if(iPos == 2 && jPos == 0){
            cells[i - 1][j + 1].setBackground(LIGHTER_BLUE);
            cells[i - 1][j + 2].setBackground(LIGHTER_BLUE);
            cells[i - 2][j + 1].setBackground(LIGHTER_BLUE);
            cells[i - 2][j + 2].setBackground(LIGHTER_BLUE);
        }

        //for when top middle of grid is clicked
        if(iPos == 0 && jPos == 1){
            cells[i + 1][j - 1].setBackground(LIGHTER_BLUE);
            cells[i + 1][j + 1].setBackground(LIGHTER_BLUE);
            cells[i + 2][j - 1].setBackground(LIGHTER_BLUE);
            cells[i + 2][j + 1].setBackground(LIGHTER_BLUE);
        }

        //for when the left middle of grid is clicked
        if(iPos == 1 && jPos == 0){
            cells[i - 1][j + 1].setBackground(LIGHTER_BLUE);
            cells[i - 1][j + 2].setBackground(LIGHTER_BLUE);
            cells[i + 1][j + 1].setBackground(LIGHTER_BLUE);
            cells[i + 1][j + 2].setBackground(LIGHTER_BLUE);
        }

        //for when the right middle of grid is clicked
        if(iPos == 1 && jPos == 2){
            cells[i - 1][j - 1].setBackground(LIGHTER_BLUE);
            cells[i - 1][j - 2].setBackground(LIGHTER_BLUE);
            cells[i + 1][j - 1].setBackground(LIGHTER_BLUE);
            cells[i + 1][j - 2].setBackground(LIGHTER_BLUE);
        }

        //for when the bottom middle of the grid is clicked
        if(iPos == 2 && jPos == 1){
            cells[i - 1][j - 1].setBackground(LIGHTER_BLUE);
            cells[i - 2][j - 1].setBackground(LIGHTER_BLUE);
            cells[i - 1][j + 1].setBackground(LIGHTER_BLUE);
            cells[i - 2][j + 1].setBackground(LIGHTER_BLUE);
        }

        //restore the replaced red cells if there are any
        if(!replaced.isEmpty()){
            for(int k = 0; k < replaced.size(); k++){
                cells[replaced.get(k)[0]][replaced.get(k)[1]].setBackground(LIGHT_RED);
            }
        }
    }

    /**
     * Resets the grid color of the previous clicked cell back to white
     */
    private void uncolorGrid(){
        //code is largely the same as colorGrid except the cells are colored white instead
        ArrayList<int[]> replaced = new ArrayList<>();

        int iPos = selected[0][0] % 3;
        int jPos = selected[0][1] % 3;
        int i = selected[0][0];
        int j = selected[0][1];

        int gridI = i - (i % 3);
        int gridJ = j - (j % 3);
        for(int k = 0; k < 9; k++){
            if(cells[gridI][gridJ].getBackground().equals(LIGHT_RED)){
                replaced.add(new int[]{gridI, gridJ});
            }

            gridJ++;
            if(gridJ % 3 == 0){
                gridJ = j - (j % 3);
                gridI++;
            }
        }

        //if previously clicked cell was the center of a grid
        if(iPos == 1 && jPos == 1){
            cells[i - 1][j - 1].setBackground(Color.WHITE);
            cells[i - 1][j + 1].setBackground(Color.WHITE);
            cells[i + 1][j - 1].setBackground(Color.WHITE);
            cells[i + 1][j + 1].setBackground(Color.WHITE);

        }

        //if previous clicked cell was the top left of a grid
        if(iPos == 0 && jPos == 0){
            cells[i + 1][j + 1].setBackground(Color.WHITE);
            cells[i + 1][j + 2].setBackground(Color.WHITE);
            cells[i + 2][j + 1].setBackground(Color.WHITE);
            cells[i + 2][j + 2].setBackground(Color.WHITE);
        }

        //if previous clicked cell was the bottom right of a grid
        if(iPos == 2 && jPos == 2){
            cells[i - 1][j - 1].setBackground(Color.WHITE);
            cells[i - 1][j - 2].setBackground(Color.WHITE);
            cells[i - 2][j - 1].setBackground(Color.WHITE);
            cells[i - 2][j - 2].setBackground(Color.WHITE);
        }

        //if previous clicked cell was the top right of a grid
        if(iPos == 0 && jPos == 2){
            cells[i + 1][j - 1].setBackground(Color.WHITE);
            cells[i + 1][j - 2].setBackground(Color.WHITE);
            cells[i + 2][j - 1].setBackground(Color.WHITE);
            cells[i + 2][j - 2].setBackground(Color.WHITE);
        }

        //if previous clicked cell was the bottom left of a grid
        if(iPos == 2 && jPos == 0){
            cells[i - 1][j + 1].setBackground(Color.WHITE);
            cells[i - 1][j + 2].setBackground(Color.WHITE);
            cells[i - 2][j + 1].setBackground(Color.WHITE);
            cells[i - 2][j + 2].setBackground(Color.WHITE);
        }

        //if previous clicked cell was the top middle of a grid
        if(iPos == 0 && jPos == 1){
            cells[i + 1][j - 1].setBackground(Color.WHITE);
            cells[i + 1][j + 1].setBackground(Color.WHITE);
            cells[i + 2][j - 1].setBackground(Color.WHITE);
            cells[i + 2][j + 1].setBackground(Color.WHITE);
        }

        //if previous clicked cell was the left middle of a grid
        if(iPos == 1 && jPos == 0){
            cells[i - 1][j + 1].setBackground(Color.WHITE);
            cells[i - 1][j + 2].setBackground(Color.WHITE);
            cells[i + 1][j + 1].setBackground(Color.WHITE);
            cells[i + 1][j + 2].setBackground(Color.WHITE);
        }

        //if previous clicked cell was the right middle of a grid
        if(iPos == 1 && jPos == 2){
            cells[i - 1][j - 1].setBackground(Color.WHITE);
            cells[i - 1][j - 2].setBackground(Color.WHITE);
            cells[i + 1][j - 1].setBackground(Color.WHITE);
            cells[i + 1][j - 2].setBackground(Color.WHITE);
        }

        //if previous clicked cell was the bottom middle of a grid
        if(iPos == 2 && jPos == 1){
            cells[i - 1][j - 1].setBackground(Color.WHITE);
            cells[i - 2][j - 1].setBackground(Color.WHITE);
            cells[i - 1][j + 1].setBackground(Color.WHITE);
            cells[i - 2][j + 1].setBackground(Color.WHITE);
        }

        if(!replaced.isEmpty()){
            for(int k = 0; k < replaced.size(); k++){
                cells[replaced.get(k)[0]][replaced.get(k)[1]].setBackground(LIGHT_RED);
            }
        }
    }

    /**
     * Resets the row color of the previous clicked cell back to white
     */
    private void uncolorRow(){
        if(selected[0][1] != 0){
            for(int k = selected[0][1] - 1; k >= 0; k--){
                if(!cells[selected[0][0]][k].getBackground().equals(LIGHT_RED)){
                    cells[selected[0][0]][k].setBackground(Color.WHITE);
                }            }
        }
        if(selected[0][1] != 8){
            for(int k = 8; k > selected[0][1]; k--){
                if(!cells[selected[0][0]][k].getBackground().equals(LIGHT_RED)){
                    cells[selected[0][0]][k].setBackground(Color.WHITE);
                }
            }
        }
    }

    /**
     * Resets the column color of the previous clicked cell back to white
     */
    private void uncolorCol(){
        if(selected[0][0] != 0){
            for(int k = selected[0][0] - 1; k >= 0; k--){
                if(!cells[k][selected[0][1]].getBackground().equals(LIGHT_RED)){
                    cells[k][selected[0][1]].setBackground(Color.WHITE);
                }

            }
        }
        if(selected[0][0] != 8){
            for(int k = 8; k > selected[0][0]; k--){
                if(!cells[k][selected[0][1]].getBackground().equals(LIGHT_RED)){
                    cells[k][selected[0][1]].setBackground(Color.WHITE);
                }            }
        }
    }

    /**
     * Colors the row of the currently clicked cell light blue
     * @param i i position of the current cell
     * @param j j position of the current cell
     */
    private void colorRow(int i, int j){
        if(j != 0){
            for(int k = j - 1; k >= 0; k--){
                if(!cells[i][k].getBackground().equals(LIGHT_RED)){
                    cells[i][k].setBackground(LIGHTER_BLUE);
                }
            }
        }
        if(j != 8){
            for(int k = 8; k > j; k--){
                if(!cells[i][k].getBackground().equals(LIGHT_RED)){
                    cells[i][k].setBackground(LIGHTER_BLUE);
                }
            }
        }
    }

    /**
     * Colors the column of the currently clicked cell light blue
     * @param i i position of the current cell
     * @param j j position of the current cell
     */
    private void colorCol(int i, int j){
        if(i != 0){
            for(int k = i - 1; k >= 0; k--){
                if(!cells[k][j].getBackground().equals(LIGHT_RED)){
                    cells[k][j].setBackground(LIGHTER_BLUE);
                }
            }
        }
        if(i != 8){
            for(int k = 8; k > i; k--){
                if(!cells[k][j].getBackground().equals(LIGHT_RED)){
                    cells[k][j].setBackground(LIGHTER_BLUE);
                }
            }
        }
    }

    /**
     * Removes every red highlighted cell on the board
     */
    private void clearColor(){
        for(int i = 0; i < 9; i++){
            for(int j = 0; j < 9; j++){
                int curBlock = board.checkBlock(i, j);
                if(cells[i][j].getBackground().equals(LIGHT_RED)){
                    if(i == selected[0][0] || j == selected[0][1] || curBlock == board.checkBlock(selected[0][0], selected[0][1])){
                        cells[i][j].setBackground(LIGHTER_BLUE);
                    }else{
                        cells[i][j].setBackground(Color.WHITE);
                    }
                }
            }
        }
    }
}
