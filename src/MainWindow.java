import javax.swing.*;

/**
 * Created by Alex Vlasov on 25/12/20.
 */

public class MainWindow extends JFrame {
    public MainWindow() {
        setTitle("2048");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(336, 359);
        setLocation(400, 400);
        setResizable(false);
        add(new GameField());
        setVisible(true);
    }

    public static void main(String[] args) {
        MainWindow mw = new MainWindow();
    }
}
