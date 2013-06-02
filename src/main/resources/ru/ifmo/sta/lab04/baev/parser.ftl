package ${package};

import java.io.InputStream;
import java.util.Arrays;

${annotation}
public class Parser {
    public static Node parse(InputStream is) throws ParseException {
        LexicalAnalyzer lexicalAnalyzer = new LexicalAnalyzer(is);

        lexicalAnalyzer.nextToken();
        return ${first_rule_name}(lexicalAnalyzer);
    }

    <#list methods as method>
    ${method}<#if method_has_next>

    </#if>
    </#list>
}