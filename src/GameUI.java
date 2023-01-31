import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
public class GameUI extends JPanel {

    private Game game;

    private JFrame frame;

    private BufferedImage grid;

    private BufferedImage x;

    private BufferedImage o;

    public GameUI(Game game) {

        this.game = game;

        try {
            ClassLoader classLoader = this.getClass().getClassLoader();
            grid = ImageIO.read(classLoader.getResource("resources/grid.png"));
            x = ImageIO.read(classLoader.getResource("resources/x.png"));
            o = ImageIO.read(classLoader.getResource("resources/o.png"));
        }
        catch (IllegalArgumentException e){
                System.out.println("classloader error");
        }
        catch (IOException ex) {
            System.out.println("Failed to load images");
        }

        JButton newGameButton = new JButton("New Single Player Game");
        newGameButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        newGameButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                newGameButtonPressed(false);
            }
        });

        JButton new2PlayerGameButton = new JButton("New 2 Player Game");
        new2PlayerGameButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        new2PlayerGameButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                newGameButtonPressed(true);
            }
        });

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                JPanel panel = (JPanel)e.getSource();
                double boardWidth = panel.getWidth();
                double boardHeight = panel.getHeight();
                double boardX = 10;
                double boardY = 40;

                if(e.getX()<boardX || e.getY() < boardY)
                    return;
                int i = (int) (3*((e.getX()-boardX)/(boardWidth-boardX)));
                int j = (int) (3*((e.getY()-boardY)/(boardHeight-boardY)));
                panelMouseClicked(i,j);
            }
        });

        this.add(newGameButton);
        this.add(new2PlayerGameButton);

        final int WIDTH = 620;
        final int HEIGHT = 650;
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));

        frame = new JFrame();
        frame.add(this);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.drawImage(grid, 10, 40, null);
        for(int i=0; i<3;i++){
            for(int j=2; j>=0;j--) {
                if(game.gridAt(i,j)=='x'){
                    g.drawImage(x, 200*i+40,200*j+70, null);
                }
                else if(game.gridAt(i,j)=='o'){
                    g.drawImage(o, 200*i+40,200*j+70, null);
                }
            }
        }
    }


    private void panelMouseClicked(int i, int j) {
        if(!game.playAt(i,j)){
            return;
        }
        this.repaint();
        if(game.doChecks()){
            return;
        }
        game.nextTurn();
        this.repaint();
        if(game.doChecks()){
            return;
        }
    }

    public void gameOver(String message){
        System.out.println(game.toString());
        JOptionPane.showMessageDialog(null, message, "Game Over!", JOptionPane.INFORMATION_MESSAGE);
    }

    private void newGameButtonPressed(boolean twoPlayer) {
        game.newGame(twoPlayer);
        this.repaint();
    }

}