import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class GameField extends JPanel {
    private int [][] map = new int[4][4];
    private boolean isPlaying = true;
    public Random random = new Random();

    public GameField() {
        initGame();
        addKeyListener(new FieldKeyListener());
        setFocusable(true);
    }

    public void initGame() {
        setBackground(new Color(187, 173, 160));
        generateNewNumber();
        generateNewNumber();
    }

    public void generateNewNumber() {
        boolean isFill = true;
        boolean is2048 = false;
        for(int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {
                if(map[y][x] == 0) isFill = false;
                if(map[y][x] == 2048) is2048 = true;
            }
        }

        if(isFill || is2048) isPlaying = false;
        else {
            int newNumberX, newNumberY;
            do {
                newNumberX = random.nextInt(4);
                newNumberY = random.nextInt(4);
            }
            while (map[newNumberY][newNumberX] != 0);
            int newNumber = random.nextInt(10) + 1;
            if (newNumber <= 9) map[newNumberY][newNumberX] = 2;
            else map[newNumberY][newNumberX] = 4;
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        for(int y = 0; y < 4; y++) {
            for(int x = 0; x < 4; x++) {
                if(map[y][x] == 0) {
                    g.setColor(new Color(205, 192, 180));
                    g.fillRect(x * 80 + 3, y * 80 + 3, 74, 74);
                }
                else {
                    g.setColor(getColorNumber(map[y][x]));
                    g.fillRect(x * 80 + 3, y * 80 + 3, 74, 74);

                    Font font = new Font("Calibri", Font.BOLD, 32);
                    //g.setFont(font);

                    if(map[y][x] >= 8) g.setColor(new Color(251, 242, 241));
                    else g.setColor(new Color(251, 242, 241));
                    g.setColor(new Color(121, 111, 101));

                    drawCenteredString(g, Integer.toString(map[y][x]), new Rectangle(x * 80, y * 80, 80, 80), font);
                }
            }
        }
    }

    public void moveRight() {
        for(int y = 0; y < 4; y++) {
            for(int x = 2; x >= 0; x--) {
                int x2 = x;
                while(x2 < 3 && (map[y][x2+1] == 0 || map[y][x2] == map[y][x2+1])) {
                    if(map[y][x2] == map[y][x2+1]) {
                        map[y][x2+1] *= 2;
                    }
                    else {
                        map[y][x2+1] = map[y][x2];
                    }
                    map[y][x2] = 0;
                    x2++;
                }
            }
        }
        generateNewNumber();
    }

    public void moveLeft() {
        for(int y = 0; y < 4; y++) {
            for(int x = 1; x < 4; x++) {
                int x2 = x;
                while(x2 > 0 && (map[y][x2-1] == 0 || map[y][x2] == map[y][x2-1])) {
                    if(map[y][x2] == map[y][x2-1]) {
                        map[y][x2-1] *= 2;
                    }
                    else {
                        map[y][x2-1] = map[y][x2];
                    }
                    map[y][x2] = 0;
                    x2--;
                }
            }
        }
        generateNewNumber();
    }

    public void moveUp() {
        for(int x = 0; x < 4; x++) {
            for(int y = 1; y < 4; y++) {
                int y2 = y;
                while(y2 > 0 && (map[y2-1][x] == 0 || map[y2][x] == map[y2-1][x])) {
                    if(map[y2][x] == map[y2-1][x]) {
                        map[y2-1][x] *= 2;
                    }
                    else {
                        map[y2-1][x] = map[y2][x];
                    }
                    map[y2][x] = 0;
                    y2--;
                }
            }
        }
        generateNewNumber();
    }

    public void moveDown() {
        for(int x = 0; x < 4; x++) {
            for(int y = 2; y >= 0; y--) {
                int y2 = y;
                while(y2 < 3 && (map[y2+1][x] == 0 || map[y2][x] == map[y2+1][x])) {
                    if(map[y2][x] == map[y2+1][x]) {
                        map[y2+1][x] *= 2;
                    }
                    else {
                        map[y2+1][x] = map[y2][x];
                    }
                    map[y2][x] = 0;
                    y2++;
                }
            }
        }
        generateNewNumber();
    }

    class FieldKeyListener extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            super.keyPressed(e);
            if(isPlaying) {
                int key = e.getKeyCode();
                if (key == KeyEvent.VK_DOWN) moveDown();
                if (key == KeyEvent.VK_UP) moveUp();
                if (key == KeyEvent.VK_RIGHT) moveRight();
                if (key == KeyEvent.VK_LEFT) moveLeft();
                repaint();
            }
        }
    }

    public void drawCenteredString(Graphics g, String text, Rectangle rect, Font font) {
        FontMetrics metrics = g.getFontMetrics(font);
        int x = rect.x + (rect.width - metrics.stringWidth(text)) / 2;
        int y = rect.y + ((rect.height - metrics.getHeight()) / 2) + metrics.getAscent();
        g.setFont(font);
        g.drawString(text, x, y);
    }

    public Color getColorNumber(int num) {
        switch(num) {
            case 2:
                return new Color(238, 228, 218);
            case 4:
                return new Color(237, 224, 200);
            case 8:
                return new Color(242, 177, 121);
            case 16:
                return new Color(245, 149, 99);
            case 32:
                return new Color(245, 124, 97);
            case 64:
                return new Color(245, 93, 60);
            case 128:
                return new Color(237, 206, 114);
            case 256:
                return new Color(237, 204, 97);
            case 512:
                return new Color(235, 200, 80);
            case 1024:
                return new Color(236, 197, 62);
            case 2048:
                return new Color(239, 195, 46);
            default:
                return new Color(0,0,0);
        }
    }
}
