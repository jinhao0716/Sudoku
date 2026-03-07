import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.plaf.basic.BasicButtonUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.sql.Array;
import java.util.ArrayList;
import java.util.HashMap;

import static java.awt.Font.BOLD;

public class Graphic extends javax.swing.JFrame implements ActionListener {

    //global variables
    //Jframes and panels
    JFrame frame;
    JPanel panel1;
    JPanel panel2;
    JPanel panel3;
    JPanel panel4;
    JPanel panel5;

    //sudoku game board class
    Board board;

    JButton[][] cells = new JButton[9][9];
    JButton[] addButton = new JButton[10];
    int[][] selected = {{-1,-1}};
    boolean[][] placed = new boolean[9][9];

    Image background = Toolkit.getDefaultToolkit().getImage("background1.jpg");

    //colors
    Color LIGHT_BLUE = new Color(156,200,255);
    Color LIGHTER_BLUE = new Color(201,240,255);
    Color LIGHT_RED = new Color(245, 130, 130);

    public Graphic(Board board){
        this.board = board;

        //Initializes the JFrame
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) screen.getWidth();
        int height = (int) screen.getHeight();
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


        panel1 = new JPanel();
        panel1.setSize(new Dimension(frame.getWidth(), frame.getHeight()));
        panel1.setLayout(new GridLayout(9, 9));
        panel1.setBorder(BorderFactory.createEmptyBorder((int)(height/7.8), (int)(width/3), (int)(height/5), (int)(width/16)));

        panel2 = new JPanel();
        panel2.setLayout(new BoxLayout(panel2, BoxLayout.Y_AXIS));
        panel2.setBorder(BorderFactory.createEmptyBorder(0,width/40,0,0));

        panel3 = new JPanel();
        panel3.setLayout(new GridLayout(4, 3));
        panel3.setBorder(BorderFactory.createEmptyBorder(height/4, width/10, height/3, width/11));


        panel4 = new JPanel();
        panel4.setLayout(new GridLayout(1, 3));
        panel4.setBorder(BorderFactory.createEmptyBorder(height/10,(int)(width/8.7),height/100,0));
        JLabel placeholder1 = new JLabel("TEST");
        placeholder1.setFont(new Font("Arial", BOLD, 30));
        placeholder1.setVisible(false);
        panel4.add(placeholder1);

        JLabel title = new JLabel("SUDOKU");
        title.setFont(new Font("Times New Roman", BOLD, 80));
        panel4.add(title);

        JLabel placeholder2 = new JLabel("TEST");
        placeholder2.setFont(new Font("Arial", BOLD, 30));
        placeholder2.setVisible(false);
        panel4.add(placeholder2);

        panel5 = new JPanel();
        panel5.setLayout(new BoxLayout(panel5, BoxLayout.X_AXIS));

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

        for(int i = 0; i < 9; i++) {
            addButton[i] = new JButton("" + (i + 1));
            addButton[i].setBackground(LIGHTER_BLUE);
            addButton[i].setPreferredSize(new Dimension(width/40, height/40));
            addButton[i].setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.BLUE));
            addButton[i].addActionListener(this);
            panel3.add(addButton[i]);
        }

        JButton temp = new JButton();
        temp.setVisible(false);
        panel3.add(temp);
        addButton[9] = new JButton("⌦");
        addButton[9].setBackground(LIGHTER_BLUE);
        addButton[9].setPreferredSize(new Dimension(width/40, height/40));
        addButton[9].setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.BLUE));
        addButton[9].addActionListener(this);
        panel3.add(addButton[9]);


        frame.setResizable(false);
        frame.add(panel1, BorderLayout.CENTER);
        frame.add(panel2, BorderLayout.WEST);
        frame.add(panel3, BorderLayout.EAST);
        frame.add(panel4, BorderLayout.NORTH);
        frame.add(panel5, BorderLayout.SOUTH);
        frame.setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        boolean add = false;

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
                        JOptionPane.showMessageDialog(null, "You win!", "Congratulations!", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            }
        }
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

    private void colorWrong(){
        ArrayList<int[]> temp = board.violatedCells();
        for(int k = 0; k < temp.size(); k++){
            if(!placed[temp.get(k)[0]][temp.get(k)[1]]){
                cells[temp.get(k)[0]][temp.get(k)[1]].setBackground(LIGHT_RED);
            }
        }
    }

    private void colorGrid(int i, int j){
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

        if(!replaced.isEmpty()){
            for(int k = 0; k < replaced.size(); k++){
                cells[replaced.get(k)[0]][replaced.get(k)[1]].setBackground(LIGHT_RED);
            }
        }
    }

    private void uncolorGrid(){
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
