import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.font.TextAttribute;
import java.util.HashMap;
import java.util.Map;

public class GameFrame extends JFrame implements ActionListener {
    public static JPanel menuBar;       //menubar
    private final JButton restartButton;      //restart button
    private final JButton exitButton;         //exit button
    private TiliToli game;                //the game itself
    private final int DIMENSION = 600;    //dimension of the game panel
    private final int MARGIN = DIMENSION / 10;        //margin constant


    public GameFrame() {
        setLayout(null);
        setSize(DIMENSION, DIMENSION + MARGIN);

        /** initializing the menubar **/
        menuBar = new JPanel();
        menuBar.setBounds(0, 0, getWidth(), MARGIN / 2);
        menuBar.setBackground(new Color(127, 0, 255));
        this.add(menuBar);

        /** initializing the restart button **/
        restartButton = new JButton("Restart");
        restartButton.setBorder(BorderFactory.createEmptyBorder());         //remove border
        Map<TextAttribute, Object> attributes = new HashMap<TextAttribute, Object>();
        attributes.put(TextAttribute.TRACKING, 0.12);
        restartButton.setFont(new Font("ComicSans", Font.BOLD, 18));
        restartButton.setFont(restartButton.getFont().deriveFont(attributes));          //set the text attributes
        restartButton.setForeground(Color.WHITE);
        restartButton.setBackground(null);
        menuBar.add(restartButton);
        menuBar.setLayout(null);
        restartButton.setBounds(0, 0, menuBar.getWidth() / 4, menuBar.getHeight());     //align the button
        restartButton.setFocusable(false);
        restartButton.setContentAreaFilled(false);
        restartButton.addActionListener(this);

        /** initializing the exit button **/
        exitButton = new JButton("Exit");
        exitButton.setBorder(BorderFactory.createEmptyBorder());
        exitButton.setFont(new Font("ComicSans", Font.BOLD, 18));
        exitButton.setFont(exitButton.getFont().deriveFont(attributes));
        exitButton.setForeground(Color.WHITE);
        exitButton.setBackground(null);
        menuBar.add(exitButton);
        exitButton.setBounds(menuBar.getWidth() * 3 / 4, 0, menuBar.getWidth() / 4, menuBar.getHeight());
        exitButton.setFocusable(false);
        exitButton.setContentAreaFilled(false);

        /** adding the game to the frame **/
        setTitle("Tili Toli");
        setResizable(false);
        game = new TiliToli(4, DIMENSION, MARGIN);
        game.setBounds(0, menuBar.getHeight(), getWidth(), getHeight());
        add(game, BorderLayout.CENTER);
        setLocationRelativeTo(null);
        setVisible(true);

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e){
        if(e.getSource()==restartButton) {
            this.remove(game);
            game = new TiliToli(4, DIMENSION, MARGIN);
            game.setBounds(0, menuBar.getHeight(), getWidth(), getHeight());
            add(game, BorderLayout.CENTER);
            SwingUtilities.updateComponentTreeUI(this);
        }
    }
    public static void main(String[] args) {
        GameFrame newGame = new GameFrame();
        newGame.setVisible(true);
        newGame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
