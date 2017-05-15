import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Main extends JFrame {

    private static int xSize = 3;
    private static int ySize = 3;

    private static final int SIZE = 512;
    private final int GAP = 3;
    private int player;
    private static boolean isRunning = true;

    private static Results results;

    static {
        results = new Results();
    }

    public static void main(String[] args) {
        run();
    }

    /**
     * Create JFrame and runGame
     */
    private static void run() {
        EventQueue.invokeLater(() -> {
            Main main = new Main();
            main.init();
            main.runWhoFirst();
            main.runBoard();
        });
    }

    /**
     * Init the JFrame
     */
    private void init() {
        setSize(SIZE, SIZE);
        setMinimumSize(new Dimension(SIZE, SIZE));
        setMaximumSize(new Dimension(SIZE, SIZE));
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        setTitle("Circle And Cross");
    }

    /**
     * Asks who should play first
     */
    private void runWhoFirst() {
        JFrame frame = new JFrame("Who first");
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        final JPanel panel = new JPanel(); // new GridLayout(1, 2, GAP, GAP)
        panel.setLayout(new FlowLayout());

        Container container = frame.getContentPane();
        container.setLayout(new FlowLayout());
        container.add(panel);

        final JButton buttonX = new JButton("");
        buttonX.setFont(new Font("Arial", Font.PLAIN, 20));
        buttonX.setText("Player X first");
        panel.add(buttonX);
        buttonX.addActionListener(e -> {
            player = 0;
            frame.setVisible(false);
            setTitle("Now plays X");
        });

        final JButton buttonO = new JButton("");
        buttonO.setFont(new Font("Arial", Font.PLAIN, 20));
        buttonO.setText("Player O first");
        panel.add(buttonO);
        buttonO.addActionListener(e -> {
            player = 1;
            frame.setVisible(false);
            setTitle("Now plays O");
        });

        frame.pack();
        frame.setVisible(true);
    }

    /**
     * Creates buttons and adds actions to them
     */
    private void runBoard() {
//        JFrame frame = new JFrame("Game");
//        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
//        final JPanel panel = new JPanel();
//
//        Container container = frame.getContentPane();
//        container.setLayout(new GridLayout(3, 3, GAP, GAP));
//        container.add(panel);

        setLayout(new GridLayout(xSize, ySize, GAP, GAP));
        for (int i = 0; i < 9; i++) {
            final JButton button = new JButton("");
            button.setFont(new Font("Arial", Font.PLAIN, 100));
            add(button);
            button.addActionListener(e -> {
                makeMove(button);
                checkAll();
            });
        }
    }

    /**
     * Delete all components
     */
    private void clean() {
        for (Component component : getContentPane().getComponents()) {
            getContentPane().remove(component);
        }
    }

    /**
     * Detects player and mark X or O on board
     * And also informs who plays in the title of the frame
     */
    private void makeMove(JButton button) {
        if (player % 2 == 0) {
            button.setText("X");
            setTitle("Now plays O");
        } else {
            button.setText("O");
            setTitle("Now plays X");
        }
        button.setEnabled(false);
        player++;
    }

    private String checkTriple(int[] triple) {
        String winner = null;
        java.util.List<String> texts = new ArrayList<String>();
        for (int index : triple) {
            texts.add(((JButton) getContentPane().getComponent(index - 1)).getText());
        }
        String text1 = texts.get(0);
        String text2 = texts.get(1);
        String text3 = texts.get(2);
        if (!text2.equals("") && text2.equals(text1)) {
            if (!text3.equals("") && text3.equals(text1)) {
                for (int index : triple) {
                    getContentPane().getComponent(index - 1).setBackground(Color.RED);
                }
                winner = text1;
            }
        }
        return winner;
    }

    /**
     * Detects if player won. If someone won we start new game
     */
    private void checkAll() {
        int[][] indices = {
                {1, 2, 3}, {4, 5, 6}, {7, 8, 9}, // horizontal
                {1, 4, 7}, {2, 5, 8}, {3, 6, 9}, // vertical
                {1, 5, 9}, {3, 5, 7}             // diagonal
        };
        for (int[] triple : indices) {
            String winner = checkTriple(triple);
            if (winner != null) {
                disableComponents();
                if (winner.equals("X")) {
                    results.incXScore();
                } else if (winner.equals("O")) {
                    results.incOScore();
                }
                JOptionPane.showMessageDialog(this, "Wygrywa " + winner + ". O: " + results.getOScore() + " X: " + results.getXScore());
                run();
            }
        }
    }

    private void disableComponents() {
        for (Component component : getContentPane().getComponents()) {
            component.setEnabled(false);
        }
    }

//    private void createComponentMap() {
//        componentMap = new HashMap<String, Component>();
//        Component[] components = getContentPane().getComponents();
//        for (Component component : components) {
//            componentMap.put(component.getName(), component);
//        }
//    }
//
//    public Component getComponentByName(String name) {
//        if (componentMap.containsKey(name)) {
//            return componentMap.get(name);
//        } else return null;
//    }

}
