import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class GameController extends MouseAdapter{
    private final GameModel gameModel;
    private final GameView gameView;
    private boolean gameOver;
    private final GameFrame gameFrame;
    private final int margin;
    private final int gridSize;
    private final int tileSize;
    private int blankPos;
    private final int[] tiles;
    private final int panelSize;
    private final Clip clip;

    public GameController(GameModel gameModel, GameView gameView) {
        this.gameModel = gameModel;
        this.gameView = gameView;
        this.gameOver = gameModel.isGameOver();
        this.gameFrame = gameModel.getGameFrame();
        this.margin = gameModel.getMargin();
        this.gridSize = gameModel.getGridSize();
        this.tileSize = gameModel.getTileSize();
        this.blankPos = gameModel.getBlankPos();
        this.panelSize = gameModel.getPanelSize();
        this.tiles = gameModel.getTiles();

        //audio
        try {
            File soundFile = new File("data/audio/move.wav");
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundFile);
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public void mousePressed(MouseEvent e) {
        /** get the position of the clicked tile **/
        int ex = e.getX() - margin;
        int ey = e.getY() - margin;

        /** if clicked out of the grid **/
        if (ex < 0 || ex > gridSize || ey < 0 || ey > gridSize)
            return;

        /** get the position in the grid **/
        int gridColumn = ex / tileSize;
        int gridRow = ey / tileSize;

        /** get the position of the blank tile **/
        int blankColumn = blankPos % panelSize;
        int blankRow = blankPos / panelSize;

        int clickPos = gridRow * panelSize + gridColumn;

        int dir = 0;

        /** checking for the direction the tile could go **/
        if (gridColumn == blankColumn && Math.abs(gridRow - blankRow) > 0 && (gridRow + 1 == blankRow || gridRow - 1 == blankRow)) {
            dir = (gridRow - blankRow) > 0 ? panelSize : -panelSize;
        } else if (gridRow == blankRow && Math.abs(gridColumn - blankColumn) > 0 && (gridColumn + 1 == blankColumn || gridColumn - 1 == blankColumn)) {
            dir = (gridColumn - blankColumn) > 0 ? 1 : -1;
        }

        /** move the tile in that direction **/
        if (dir != 0) {
            clip.setFramePosition(0);
            clip.start();
            if(!clip.isRunning()) {
                do {
                    int newBlankPos = blankPos + dir;
                    tiles[blankPos] = tiles[newBlankPos];
                    blankPos = newBlankPos;
                } while (blankPos != clickPos);

                tiles[blankPos] = 0;

                gameFrame.setScoreCounter(gameFrame.getScoreCounter() + 1);
                gameFrame.setScore();
            }
        }

        /** check if solved **/
        gameOver = isSolved();

        if (gameOver) {
            try {
                saveScore(gameFrame.getScoreCounter());
            } catch (IOException exp) {
                throw new RuntimeException(exp);
            }
            JOptionPane.showMessageDialog(null, "Congratulations, you've completed the game");
            newGame();
        }

        /** repaint the panel **/
        gameView.repaint();
    }

    public void newGame() {
        do{
            resetGame();
            shuffleTiles();
        } while(!isSolvable());
        gameModel.setGameOver(false);
        gameModel.getGameFrame().setScoreCounter(0);
        gameModel.getGameFrame().setScore();
    }

    private void resetGame() {
        int[] tiles = gameModel.getTiles();

        for (int i = 0; i < tiles.length; ++i) {
            tiles[i] = (i + 1) % tiles.length;
        }

        gameModel.setBlankPos(tiles.length - 1);
    }

    private void shuffleTiles() {
        int n = gameModel.getNoTiles();
        int[] tiles = gameModel.getTiles();
        Random rnd = new Random();

        while (n > 1){
            int rand = rnd.nextInt(n--);
            int temp = tiles[rand];
            tiles[rand] = tiles[n];
            tiles[n] = temp;
        }

        for (int i = 0; i < tiles.length; ++i) {
            if (tiles[i] == 0) {
                blankPos = i;
                break;
            }
        }

    }

    private boolean isSolvable() {
        int noInversions = 0;
        int noTiles = gameModel.getNoTiles();
        int[] tiles = gameModel.getTiles();

        for (int i = 0; i < noTiles; ++i) {
            for (int j = 0; j < i; ++j) {
                if (tiles[j] > tiles[i])
                    noInversions++;
            }
        }

        return noInversions % 2 == 0;
    }

    private boolean isSolved() {
        int[] tiles = gameModel.getTiles();
        int noTiles = gameModel.getNoTiles();

        /** if the blank tile isn't on the last(solved) position -> isn't solved **/
        if (tiles[tiles.length - 1] != 0) {
            return false;
        }

        for (int i = noTiles - 1; i >= 0; --i) {
            if (tiles[i] != i + 1)
                return false;
        }

        return true;
    }

    private void saveScore(int score) throws IOException {
        int[] array = new int[5];
        File file = new File("score.txt");
        if(file.exists()) {
            readIntoArray(file, array);
            insertScore(array, score);
        } else {
            array[0] = score;
        }

        FileWriter fw = new FileWriter("score.txt");
        for (int j : array) {
            fw.write(j + "\n");
        }
        fw.close();
    }

    private void readIntoArray(File f, int[] array) {
        Path filePath = Path.of(f.getPath());
        if(Files.exists(filePath)) {
            AtomicInteger i = new AtomicInteger(0);
            try {
                Files.lines(filePath)
                        .takeWhile(text -> !text.isEmpty() && i.get() < array.length)
                        .forEach(text -> {
                            array[i.get()] = Integer.parseInt(text);
                            i.getAndIncrement();
                        });
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void insertScore(int[] array, int score) {
        for (int i = 0; i < array.length; ++i) {
            if(array[i] == 0) {
                array[i] = score;
                return;
            }
            if(array[i] > score) {
                for (int j = array.length - 2; j >= i; --j) {
                    array[j + 1] = array[j];
                }
                array[i] = score;
                return;
            }
        }
    }
}
