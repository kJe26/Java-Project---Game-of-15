import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.font.TextAttribute;
import java.util.HashMap;
import java.util.Map;

public class GameFrame extends JFrame {
    private final JMenuBar menuBar;       //menubar
    private final JMenu restartGame;      //restart button
    private final JMenu exitGame;         //exit button

    private TiliToli game;                //the game itself
    private final int MARGIN = 60;        //margin constant

    public GameFrame() {
        restartGame = new JMenu("Restart");
        restartGame.setBorder(BorderFactory.createEmptyBorder());
        Map<TextAttribute, Object> attributes = new HashMap<TextAttribute, Object>();
        attributes.put(TextAttribute.TRACKING, 0.12);
        restartGame.setFont(new Font("ComicSans", Font.BOLD, 18));
        restartGame.setFont(restartGame.getFont().deriveFont(attributes));
        restartGame.setForeground(Color.WHITE);

        exitGame = new JMenu("Exit");
        exitGame.setBorder(BorderFactory.createEmptyBorder());
        exitGame.setFont(new Font("ComicSans", Font.BOLD, 18));
        exitGame.setFont(exitGame.getFont().deriveFont(attributes));
        exitGame.setForeground(Color.WHITE);
        exitGame.setMnemonic(KeyEvent.VK_S);

        menuBar = new JMenuBar();
        menuBar.add(restartGame);
        menuBar.add(Box.createHorizontalGlue());
        menuBar.add(exitGame);
        menuBar.setPreferredSize(new Dimension(getWidth(), MARGIN / 2));
        menuBar.setBackground(new Color(127, 0, 255));
        menuBar.setBorder(BorderFactory.createEmptyBorder());

        setJMenuBar(menuBar);
        setTitle("Tili Toli");
        setResizable(false);
        game = new TiliToli(4, 600, MARGIN);
        add(game, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        restartGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                remove(game);
                game = new TiliToli(4, 600, MARGIN);
                add(game, BorderLayout.CENTER);
            }
        });

        exitGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }

    public static void main(String[] args) {
        GameFrame newGame = new GameFrame();
        newGame.setVisible(true);
        newGame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
