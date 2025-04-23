import java.util.*;

class Graph {
    private List<Node> nodes = new ArrayList<>();
    private List<Edge> edges = new ArrayList<>();
    private final int MIN_DISTANCE = 60;

    public void addNode(String name, int width, int height, boolean centerOnly) {
        Random rand = new Random();
        int x, y;
        boolean overlap;

        int minX = centerOnly ? width / 3 : 50;
        int maxX = centerOnly ? width * 2 / 3 : width - 100;
        int minY = centerOnly ? height / 4 : 50;
        int maxY = centerOnly ? height / 2 : height - 150;

        do {
            overlap = false;
            x = minX + rand.nextInt(Math.max(1, maxX - minX));
            y = minY + rand.nextInt(Math.max(1, maxY - minY));
            for (Node other : nodes) {
                int dx = x - other.getX();
                int dy = y - other.getY();
                if (Math.sqrt(dx * dx + dy * dy) < MIN_DISTANCE) {
                    overlap = true;
                    break;
                }
            }
        } while (overlap);

        nodes.add(new Node(name, x, y));
    }

    public void addNode(String name, int width, int height) {
        addNode(name, width, height, false);
    }

    public void addEdge(String fromName, String toName) {
        Node from = getNodeByName(fromName);
        Node to = getNodeByName(toName);
        if (from != null && to != null && !edgeExists(from, to)) {
            edges.add(new Edge(from, to));
        }
    }

    public void addRandomEdges(int edgeCount) {
        Random rand = new Random();
        int attempts = 0;
        while (edges.size() < edgeCount && attempts < edgeCount * 10) {
            Node a = nodes.get(rand.nextInt(nodes.size()));
            Node b = nodes.get(rand.nextInt(nodes.size()));
            if (a != b && !edgeExists(a, b)) {
                edges.add(new Edge(a, b));
            }
            attempts++;
        }
    }

    public List<String> dfs(String startName) {
        List<String> result = new ArrayList<>();
        Set<String> visited = new HashSet<>();
        Node start = getNodeByName(startName);
        if (start != null) {
            dfsHelper(start, visited, result);
        }
        return result;
    }

    private void dfsHelper(Node node, Set<String> visited, List<String> result) {
        if (visited.contains(node.getName())) return;
        visited.add(node.getName());
        result.add(node.getName());
        for (Node neighbor : getNeighbors(node)) {
            dfsHelper(neighbor, visited, result);
        }
    }

    public List<String> bfs(String startName) {
        List<String> result = new ArrayList<>();
        Set<String> visited = new HashSet<>();
        Queue<Node> queue = new LinkedList<>();

        Node start = getNodeByName(startName);
        if (start != null) {
            queue.add(start);
            visited.add(start.getName());

            while (!queue.isEmpty()) {
                Node current = queue.poll();
                result.add(current.getName());

                for (Node neighbor : getNeighbors(current)) {
                    if (!visited.contains(neighbor.getName())) {
                        visited.add(neighbor.getName());
                        queue.add(neighbor);
                    }
                }
            }
        }
        return result;
    }

    private List<Node> getNeighbors(Node node) {
        List<Node> neighbors = new ArrayList<>();
        for (Edge edge : edges) {
            if (edge.getFrom() == node) neighbors.add(edge.getTo());
            else if (edge.getTo() == node) neighbors.add(edge.getFrom());
        }
        return neighbors;
    }

    public void clear() {
        nodes.clear();
        edges.clear();
    }

    public Node getNodeByName(String name) {
        for (Node n : nodes) {
            if (n.getName().equals(name)) return n;
        }
        return null;
    }

    private boolean edgeExists(Node a, Node b) {
        for (Edge edge : edges) {
            if ((edge.getFrom() == a && edge.getTo() == b) || (edge.getFrom() == b && edge.getTo() == a))
                return true;
        }
        return false;
    }

    public List<Node> getNodes() {
        return nodes;
    }

    public List<Edge> getEdges() {
        return edges;
    }
}
