package ${package};

import java.util.List;

${annotation}
@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class ${name?cap_first}RuleNode extends Node {
    private ${attr_type} ${attr_name?lower_case};

    public ${name?cap_first}RuleNode(String name) {
        super(name);
    }

    public ${name?cap_first}RuleNode(String name, List<Node> children) {
        super(name, children);
    }

    public ${attr_type} get${attr_name?lower_case?cap_first}() {
        return ${attr_name?lower_case};
    }

    public void set${attr_name?lower_case?cap_first}(${attr_type} ${attr_name?lower_case}) {
        this.${attr_name?lower_case} = ${attr_name?lower_case};
    }
}
