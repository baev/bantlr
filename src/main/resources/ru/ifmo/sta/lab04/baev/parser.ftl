package ${package};

import java.io.InputStream;
import java.util.Arrays;

${annotation}
public class Parser {
    public static ${first_rule_name?cap_first}RuleNode parse(InputStream is) throws ParseException {
        LexicalAnalyzer lexicalAnalyzer = new LexicalAnalyzer(is);

        lexicalAnalyzer.nextToken();
        return ${first_rule_name}(lexicalAnalyzer);
    }

    <#list methods as method>
    ${method}<#if method_has_next>

    </#if>
    </#list>


    public static int toInteger(String s) {
        return Integer.parseInt(s);
    }
}