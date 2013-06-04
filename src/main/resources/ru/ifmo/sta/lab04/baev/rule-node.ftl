package ${package};

import java.util.List;

${annotation}
@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class ${name?cap_first}RuleNode extends Node {
    ${init_attr}

    public ${name?cap_first}RuleNode(String name) {
        super(name);
    }

    public ${name?cap_first}RuleNode(String name, List<Node> children) {
        super(name, children);
    }

}
