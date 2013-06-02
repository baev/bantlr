package ${package};

import java.util.List;

${annotation}
@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class ${name}RuleNode extends Node {
    private ${attr_type} ${attr_name};

    public ${name}RuleNode(String name, List<Node> children) {
        super(name, children);
    }

    public ${attr_type} get${attr_name}() {
        return ${attr_name};
    }

    public void set${attr_name}(${attr_type} ${attr_name}) {
        this.${attr_name} = ${attr_name};
    }
}
