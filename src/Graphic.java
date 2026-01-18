import javax.swing.*;
import javax.swing.plaf.basic.BasicButtonUI;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

public class Graphic {
    JFrame frame;
    JPanel panel1;
    JPanel panel2;
    JPanel panel3;
    JPanel panel4;
    JPanel panel5;

    public Graphic(Board board) {
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
        frame.setLayout(new BorderLayout((int)(width/2.7), (int)(height/3.5)));
        frame.setTitle("Sudoku");
        frame.pack();


        panel1 = new JPanel();
        panel1.setSize(new Dimension(frame.getWidth(), frame.getHeight()));
        panel1.setLayout(new GridLayout(9, 9));
        panel1.setPreferredSize(new Dimension(400, 400));

        panel2 = new JPanel();
        panel2.setLayout(new BoxLayout(panel2, BoxLayout.Y_AXIS));

        panel3 = new JPanel();
        panel3.setLayout(new BoxLayout(panel3, BoxLayout.Y_AXIS));

        panel4 = new JPanel();
        panel4.setLayout(new BoxLayout(panel4, BoxLayout.X_AXIS));

        panel5 = new JPanel();
        panel5.setLayout(new BoxLayout(panel5, BoxLayout.X_AXIS));

        JPanel panel3 = new JPanel();


        JButton[][] cells = new JButton[9][9];

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if(board.valueOf(i, j) == 0) {
                    cells[i][j] = new JButton("");
                }else{
                    cells[i][j] = new JButton(Integer.toString(board.valueOf(i, j)));
                }
                cells[i][j].setFont(new Font("Arial", Font.PLAIN, height/50));
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


        frame.setResizable(false);
        frame.add(panel1, BorderLayout.CENTER);
        frame.add(panel2, BorderLayout.WEST);
        frame.add(panel3, BorderLayout.EAST);
        frame.add(panel4, BorderLayout.NORTH);
        frame.add(panel5, BorderLayout.SOUTH);
        frame.setVisible(true);

    }

}
