import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

public class ScoreBoard extends JFrame implements ActionListener {
    private final JCustomButton backButton;
    public ScoreBoard() throws FileNotFoundException {
        this.setSize(600, 800);
        this.setLocationRelativeTo(null);
        this.setLayout(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        final Color TEXT_COLOR = new Color(148, 236, 190);
        final Color BACKGROUND_COLOR = new Color(30, 63, 32);

        /** score panel **/
        JPanel scorePanel = new JPanel();
        scorePanel.setBounds(0, 0, getWidth(), getHeight());
        scorePanel.setBackground(BACKGROUND_COLOR);
        scorePanel.setLayout(null);
        add(scorePanel);

        /** back button **/
        backButton = new JCustomButton("Back");
        backButton.setBounds(getWidth() / 2 - 120, getHeight() - 120, 240, 60);
        backButton.addActionListener(this);
        scorePanel.add(backButton);

        /** title label **/
        JLabel titleLabel = new JLabel("Your Top 5 Scores");
        titleLabel.setBounds(0, 0, getWidth(), getHeight() / 7);
        titleLabel.setFont(new Font("ComicSans", Font.BOLD, 36));
        titleLabel.setBackground(null);
        titleLabel.setOpaque(true);
        titleLabel.setForeground(TEXT_COLOR);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        scorePanel.add(titleLabel);

        /** top 5 scores **/
        JLabel[] places = new JLabel[5];
        for (int i = 0; i < places.length; ++i) {
            places[i] = new JLabel(i + 1 + ". ");
            places[i].setBounds(0, (i + 1) * (titleLabel.getY() + titleLabel.getHeight()), getWidth(), getHeight() / 7);
            places[i].setBackground(null);
            places[i].setForeground(TEXT_COLOR);
            places[i].setFont(titleLabel.getFont());
            places[i].setHorizontalAlignment(SwingConstants.CENTER);
            scorePanel.add(places[i]);
        }

        Path filePath = Paths.get("score.txt");
        if (Files.exists(filePath)) {
            try {
                AtomicInteger i = new AtomicInteger(0);
                Files.lines(filePath)
                        .forEach(score -> {
                            String tmp = places[i.get()].getText();
                            tmp = tmp.concat(" " + score);
                            places[i.get()].setText(tmp);
                            i.getAndIncrement();
                        });
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        } else {
            for (JLabel place : places) {
                place.setText(place.getText() + " 0");
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == backButton) {
            dispose();
            MainMenu backToHome = new MainMenu();
            backToHome.setVisible(true);
        }
    }
}
