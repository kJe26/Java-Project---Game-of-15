import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class MainMenu extends JFrame implements ActionListener {
    private final JLabel imageLabel;              //label for the image
    private final JPanel controlPanel;            //control panel for the buttons
    private final JCustomButton helpButton;             //help button
    private final Color TEXT_COLOR = new Color(148, 236, 190);
    private final JCustomButton backButton;
    private final JPanel contentPanel;

    public MainMenu() {
        this.setSize(600, 800);
        this.setLocationRelativeTo(null);
        this.setLayout(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        final Color BACKGROUND_COLOR = new Color(30, 63, 32);
        final int MARGIN = 60;

        /** adding the logo to the top of the start screen **/
        ImageIcon image = new ImageIcon("logo.png");
        Image temp = image.getImage();
        temp = temp.getScaledInstance(getWidth() - 2 * MARGIN, getWidth() / 2 - MARGIN, Image.SCALE_SMOOTH);
        image = new ImageIcon(temp);
        imageLabel = new JLabel(image);
        imageLabel.setSize(image.getIconWidth() + 2 * MARGIN, image.getIconHeight() + MARGIN);

        imageLabel.setBackground(BACKGROUND_COLOR);
        imageLabel.setOpaque(true);
        add(imageLabel);

        /** setting up the control panel **/
        controlPanel = new JPanel();
        controlPanel.setBackground(BACKGROUND_COLOR);
        controlPanel.setBounds(0, imageLabel.getHeight(), getWidth(), getHeight() - imageLabel.getHeight());
        controlPanel.setLayout(null);
        add(controlPanel);

        /** adding the start button to the panel **/
        //start button
        JCustomButton startButton = new JCustomButton("Start Game");
        startButton.setBounds(MARGIN, MARGIN, getWidth() - 2 * MARGIN, getWidth() / 10);
        controlPanel.add(startButton);

        /** adding the scoreboard button to the panel **/
        //score board button
        JCustomButton scoreBoardButton = new JCustomButton("Scoreboard");
        scoreBoardButton.setBounds(MARGIN, startButton.getY() + startButton.getHeight() + MARGIN / 2, getWidth() - 2 * MARGIN, getWidth() / 10);
        controlPanel.add(scoreBoardButton);

        /** adding the help button to the panel **/
        helpButton = new JCustomButton("Help");
        helpButton.setBounds(MARGIN, scoreBoardButton.getY() + scoreBoardButton.getHeight() + MARGIN / 2, getWidth() - 2 * MARGIN, getWidth() / 10);
        controlPanel.add(helpButton);

        /** adding the exit button to the panel **/
        //exit button
        JCustomButton exitButton = new JCustomButton("Exit");
        exitButton.setBounds(MARGIN, helpButton.getY() + helpButton.getHeight() + MARGIN / 2, getWidth() - 2 * MARGIN, getWidth() / 10);
        controlPanel.add(exitButton);

        /** setting what each button has to do when pressed **/
        startButton.addActionListener(e -> {
            dispose();
            GameOptionsSelector optionsSelector = new GameOptionsSelector();
            optionsSelector.setVisible(true);
        });

        scoreBoardButton.addActionListener(e -> {
            dispose();
            ScoreBoard scoreBoard;
            try {
                scoreBoard = new ScoreBoard();
            } catch (FileNotFoundException ex) {
                throw new RuntimeException(ex);
            }
            scoreBoard.setVisible(true);
        });

        helpButton.addActionListener(this);

        exitButton.addActionListener(e -> System.exit(0));

        /** content panel **/
        contentPanel = new JPanel();
        contentPanel.setBounds(0,0, getWidth(), getHeight());
        contentPanel.setBackground(BACKGROUND_COLOR);
        contentPanel.setLayout(null);

        /** back button **/
        backButton = new JCustomButton("Back");
        backButton.setBounds(10, 10, 100, 30);
        backButton.setFont(new Font("ComicSans", Font.BOLD, 16));
        backButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e){
        if(e.getSource() == helpButton) {
            remove(imageLabel);
            remove(controlPanel);

            add(contentPanel);
            contentPanel.add(backButton);

            /** help text panel **/
            JLabel helpPanel = new JLabel("", JLabel.CENTER);
            helpPanel.setBounds(10, backButton.getHeight() + 10, getWidth() - 20, getHeight() - 10 - backButton.getHeight());
            helpPanel.setBorder(BorderFactory.createEmptyBorder(-backButton.getHeight(), 0, 0, 0));
            helpPanel.setBackground(null);
            helpPanel.setOpaque(true);

            File textFile = new File("helpText.txt");
            Scanner textScanner;
            try {
                textScanner = new Scanner(textFile);
            } catch (FileNotFoundException ex) {
                throw new RuntimeException(ex);
            }
            String fileContent = "";
            while(textScanner.hasNextLine()) {
                fileContent = fileContent.concat(textScanner.nextLine() + "\n");
            }

            helpPanel.setText("<html><div style='text-align: center;'>" + fileContent.replaceAll("<","&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br/>") + "</div></html>");
            helpPanel.setForeground(TEXT_COLOR);
            helpPanel.setFont(new Font("ComicSans", Font.BOLD, 17));
            helpPanel.setAlignmentX(0);
            helpPanel.setAlignmentY(0);
            contentPanel.add(helpPanel);

            SwingUtilities.updateComponentTreeUI(this);
        } else {
            if(e.getSource() == backButton) {
                remove(contentPanel);
                add(imageLabel);
                add(controlPanel);
                SwingUtilities.updateComponentTreeUI(this);
            }
        }
    }

    public static void main(String[] args) {
        MainMenu newGame = new MainMenu();
        newGame.setVisible(true);
        newGame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
