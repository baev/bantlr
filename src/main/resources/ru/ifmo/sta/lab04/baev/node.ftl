package ${package};

import java.util.ArrayList;
import java.util.List;

${annotation}
@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class Node {
    private String name;
    private List<Node> children;

    public Node(String name) {
        this.name = name;
        this.children = new ArrayList<Node>();
    }

    public Node(String name, List<Node> children) {
        this.name = name;
        this.children = children;
    }

    public String getName() {
        return name;
    }

    public List<Node> getChildren() {
        return children;
    }
}
