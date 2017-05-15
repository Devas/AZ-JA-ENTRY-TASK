import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

public class Main extends JFrame {

    private static final int SIZE = 512;
    private final int GAP = 3;
    private int player;

    /**
     * Create JFrame and runBoard it
     */
    public static void main(String[] args) {
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

        Container container = frame.getContentPane();
        container.setLayout(new FlowLayout());
        container.add(panel);

        final JButton buttonX = new JButton("");
        buttonX.setFont(new Font("Arial", Font.PLAIN, 20));
        buttonX.setText("Player X first");
        add(buttonX);
        buttonX.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                player = 0;
                frame.setVisible(false);
            }
        });

        final JButton buttonO = new JButton("");
        buttonO.setFont(new Font("Arial", Font.PLAIN, 20));
        buttonO.setText("Player O first");
        add(buttonO);
        buttonO.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                player = 1;
                frame.setVisible(false);
            }
        });

        panel.setLayout(new FlowLayout());
        panel.add(buttonX);
        panel.add(buttonO);

        frame.pack();
        frame.setVisible(true);
    }

    /**
     * Creates buttons and adds actions to them
     */
    private void runBoard() {
        setLayout(new GridLayout(3, 3, GAP, GAP));
        for (int i = 0; i < 9; i++) {
            final JButton button = new JButton("");
            button.setFont(new Font("Arial", Font.PLAIN, 100));
            add(button);
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    makeMove(button);
                    checkAll();
                }
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
     */
    private void makeMove(JButton button) {
        if (player % 2 == 0) {
            button.setText("X");
        } else {
            button.setText("O");
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

    private void checkAll() {
        int[][] indices = {
                {1, 2, 3}, {4, 5, 6}, {7, 8, 9}, // horizontal
                {1, 4, 7}, {2, 5, 8}, {3, 6, 9}, // vertical
                {1, 5, 9}, {3, 5, 7}             // diagonal
        };
        for (int[] triple : indices) {
            String winner = checkTriple(triple);
            if (winner != null) {
                for (Component component : getContentPane().getComponents()) {
                    component.setEnabled(false);
                }
                JOptionPane.showMessageDialog(this, "The winner is: " + winner);
            }
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

    private void showResults() {
        if (player % 2 == 0) {
            System.out.println("Now plays X");
        } else {
            System.out.println("Now plays O");
        }
    }

}
