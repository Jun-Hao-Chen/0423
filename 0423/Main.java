import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class Main {
    private static Graph graph = new Graph();
    private static NodePanel canvas = new NodePanel();

    public static void main(String[] args) {
        JFrame frame = new JFrame("Graph GUI");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 700);
        frame.setLayout(new BorderLayout());

        // 上方：新增節點、邊
        JPanel topPanel = new JPanel();
        JTextField nodeNameField = new JTextField(10);
        JButton addNodeButton = new JButton("Add Node");
        JTextField fromField = new JTextField(5);
        JTextField toField = new JTextField(5);
        JButton addEdgeButton = new JButton("Add Edge");

        topPanel.add(new JLabel("Node Name:"));
        topPanel.add(nodeNameField);
        topPanel.add(addNodeButton);
        topPanel.add(new JLabel("From:"));
        topPanel.add(fromField);
        topPanel.add(new JLabel("To:"));
        topPanel.add(toField);
        topPanel.add(addEdgeButton);

        // 下方：自動產生圖
        JPanel autoPanel = new JPanel();
        JTextField autoNodeField = new JTextField(5);
        JTextField autoEdgeField = new JTextField(5);
        JButton autoGenerateButton = new JButton("Auto Generate Graph");

        autoPanel.add(new JLabel("Number of Nodes:"));
        autoPanel.add(autoNodeField);
        autoPanel.add(new JLabel("Number of Edges:"));
        autoPanel.add(autoEdgeField);
        autoPanel.add(autoGenerateButton);

        // 右側：搜尋控制
        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new GridLayout(0, 1, 5, 5));
        JTextField startNodeField = new JTextField(10);
        JButton dfsButton = new JButton("DFS");
        JButton bfsButton = new JButton("BFS");

        searchPanel.add(new JLabel("Start Node:"));
        searchPanel.add(startNodeField);
        searchPanel.add(dfsButton);
        searchPanel.add(bfsButton);

        canvas.setBackground(Color.WHITE);

        frame.add(canvas, BorderLayout.CENTER);
        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(autoPanel, BorderLayout.SOUTH);
        frame.add(searchPanel, BorderLayout.EAST);

        // Add Node
        addNodeButton.addActionListener(e -> {
            String name = nodeNameField.getText().trim();
            if (!name.isEmpty()) {
                graph.addNode(name, canvas.getWidth(), canvas.getHeight(), false);
                nodeNameField.setText("");
                canvas.repaint();
            }
        });

        // Add Edge
        addEdgeButton.addActionListener(e -> {
            String from = fromField.getText().trim();
            String to = toField.getText().trim();
            graph.addEdge(from, to);
            fromField.setText("");
            toField.setText("");
            canvas.repaint();
        });

        // Auto Generate Graph
        autoGenerateButton.addActionListener(e -> {
            try {
                int nodeCount = Integer.parseInt(autoNodeField.getText().trim());
                int edgeCount = Integer.parseInt(autoEdgeField.getText().trim());

                graph.clear();
                for (int i = 0; i < nodeCount; i++) {
                    graph.addNode("N" + i, canvas.getWidth(), canvas.getHeight(), true);
                }
                graph.addRandomEdges(edgeCount);
                canvas.repaint();

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Please enter valid numbers.");
            }
        });

        // DFS
        dfsButton.addActionListener(e -> {
            String start = startNodeField.getText().trim();
            if (!start.isEmpty()) {
                List<String> result = graph.dfs(start);
                JOptionPane.showMessageDialog(frame, "DFS: " + String.join(" -> ", result));
            }
        });

        // BFS
        bfsButton.addActionListener(e -> {
            String start = startNodeField.getText().trim();
            if (!start.isEmpty()) {
                List<String> result = graph.bfs(start);
                JOptionPane.showMessageDialog(frame, "BFS: " + String.join(" -> ", result));
            }
        });

        frame.setVisible(true);
    }

    static class NodePanel extends JPanel {
        private final int NODE_RADIUS = 25;

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            List<Node> nodes = graph.getNodes();
            List<Edge> edges = graph.getEdges();

            g.setColor(Color.GRAY);
            for (Edge edge : edges) {
                int x1 = edge.getFrom().getX() + NODE_RADIUS;
                int y1 = edge.getFrom().getY() + NODE_RADIUS;
                int x2 = edge.getTo().getX() + NODE_RADIUS;
                int y2 = edge.getTo().getY() + NODE_RADIUS;
                g.drawLine(x1, y1, x2, y2);
            }

            for (Node node : nodes) {
                int x = node.getX();
                int y = node.getY();
                g.setColor(Color.BLUE);
                g.fillOval(x, y, NODE_RADIUS * 2, NODE_RADIUS * 2);
                g.setColor(Color.WHITE);
                g.drawString(node.getName(), x + 10, y + 30);
            }
        }
    }
}
