import javax.swing.*;
import javax.swing.plaf.basic.BasicButtonUI;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

public class Graphic {
    JFrame frame;
    JPanel panel1;
    JPanel panel2;
    public Graphic(Board board) {
        /*
        Initializes the JFrame, sets window size to 2000x1000
         */
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setMinimumSize(new Dimension(1000,1000));

        frame.addComponentListener(listener);

        /*
        Sets the JFrame layout to BorderLayout and names the window
         */
        frame.setLayout(new BorderLayout());
        frame.setTitle("Sudoku");
        frame.pack();


        panel1 = new JPanel();
        panel1.setSize(new Dimension(frame.getWidth(),frame.getHeight()));
        panel1.setBorder(BorderFactory.createEmptyBorder(frame.getHeight()/3, frame.getWidth()/2,frame.getHeight()/3,frame.getWidth()/2));
        panel1.setLayout(new GridLayout(9,9 ));
        panel1.setPreferredSize(new Dimension(200,200));

        panel2 = new JPanel();
        panel2.setLayout(new BoxLayout(panel2,BoxLayout.Y_AXIS));
        panel2.setBorder(BorderFactory.createEmptyBorder(100, 300,100,200));

        JPanel panel3 = new JPanel();


        JButton[][] cells = new JButton[9][9];

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                cells[i][j] = new JButton(Integer.toString(board.valueOf(i,j)));
                cells[i][j].setUI(new BasicButtonUI());
                cells[i][j].setOpaque(true);
                cells[i][j].setBackground(Color.WHITE);
                cells[i][j].setPreferredSize(new Dimension(10,10));
                if(i == 0 && j == 0){
                    cells[i][j].setBorder(BorderFactory.createMatteBorder(4,4,1,1,Color.BLACK));
                }else if(i == 0 && j == 8){
                    cells[i][j].setBorder(BorderFactory.createMatteBorder(4,1,1,4,Color.BLACK));
                }else if(i == 8 && j == 0){
                    cells[i][j].setBorder(BorderFactory.createMatteBorder(1,4,4,1,Color.BLACK));
                }else if(i == 8 && j == 8){
                    cells[i][j].setBorder(BorderFactory.createMatteBorder(1,1,4,4,Color.BLACK));
                }else if(i == 0 && (j == 2 || j == 5)){
                    cells[i][j].setBorder(BorderFactory.createMatteBorder(4,1,1,3,Color.BLACK));
                }else if(i == 8 && (j == 2 || j == 5)){
                    cells[i][j].setBorder(BorderFactory.createMatteBorder(1,1,4,3,Color.BLACK));
                }else if((i == 2 || i == 5) && (j == 2 || j == 5)){
                    cells[i][j].setBorder(BorderFactory.createMatteBorder(1,1,3,3,Color.BLACK));
                }else if((i == 2 || i == 5) && j == 0){
                    cells[i][j].setBorder(BorderFactory.createMatteBorder(1,4,3,1,Color.BLACK));
                }else if((i == 2 || i == 5) && j == 8){
                    cells[i][j].setBorder(BorderFactory.createMatteBorder(1,1,3,4,Color.BLACK));
                }else if(i == 2 || i == 5){
                    cells[i][j].setBorder(BorderFactory.createMatteBorder(1,1,3,1,Color.BLACK));
                }else if(j == 2 || j == 5){
                    cells[i][j].setBorder(BorderFactory.createMatteBorder(1,1,1,3,Color.BLACK));
                }else if(i == 0){
                    cells[i][j].setBorder(BorderFactory.createMatteBorder(4,1,1,1,Color.BLACK));
                }else if(i == 8){
                    cells[i][j].setBorder(BorderFactory.createMatteBorder(1,1,4,1,Color.BLACK));
                }else if(j == 0){
                    cells[i][j].setBorder(BorderFactory.createMatteBorder(1,4,1,1,Color.BLACK));
                }else if(j == 8){
                    cells[i][j].setBorder(BorderFactory.createMatteBorder(1,1,1,4,Color.BLACK));
                }else{
                    cells[i][j].setBorder(BorderFactory.createMatteBorder(1,1,1,1,Color.BLACK));
                }
                panel1.add(cells[i][j]);
            }
        }

        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.add(panel1, BorderLayout.CENTER);
        frame.add(panel2, BorderLayout.WEST);
        frame.setVisible(true);



    }

    ComponentListener listener = new ComponentListener() {
        @Override
        public void componentResized(ComponentEvent e) {
        }

        @Override
        public void componentMoved(ComponentEvent e) {

        }

        @Override
        public void componentShown(ComponentEvent e) {

        }

        @Override
        public void componentHidden(ComponentEvent e) {

        }
    };

}
