package ru.ifmo.sta.lab04.baev;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.*;

/**
 * @author Dmitry Baev charlie@yandex-team.ru
 *         Date: 02.06.13
 */
public class TemplateUtil {
    private static final String TOKEN_TEMPLATE = "ru/ifmo/sta/lab04/baev/token.ftl";
    private static final String LEXER_TEMPLATE = "ru/ifmo/sta/lab04/baev/lexer.ftl";
    private static final String EXCEPTION_TEMPLATE = "ru/ifmo/sta/lab04/baev/exception.ftl";
    private static final String NODE_TEMPLATE = "ru/ifmo/sta/lab04/baev/node.ftl";
    private static final String RULE_NODE_TEMPLATE = "ru/ifmo/sta/lab04/baev/rule-node.ftl";
    private static final String ANNOTATION = "ru/ifmo/sta/lab04/baev/annotation.txt";
    private static final String PARSER_TEMPLATE = "ru/ifmo/sta/lab04/baev/parser.txt";
    private static final String PARSER_METHOD_TEMPLATE = "ru/ifmo/sta/lab04/baev/parser-method.txt";
    private static final String PARSER_METHOD_CASE_TEMPLATE = "ru/ifmo/sta/lab04/baev/parser-method-case.txt";

    private File output;
    private String packageName;

    private List<String> tokens;
    private List<String> terminals;
    private Set<String> notTerminals;
    private String firstTerminal;

    private Map<String, List<String>> first;
    private Map<String, List<Rule>> rules;

    public TemplateUtil(String packageName,
                        List<String> tokens,
                        List<String> terminals,
                        Set<String> notTerminals
    ) {
        this.output = new File("bantlr/"
                + packageName.replaceAll("/.", "/"));

        this.packageName = packageName;

        this.tokens = tokens;
        this.terminals = terminals;
        this.notTerminals = notTerminals;
        this.first = new HashMap<String, List<String>>();
        this.firstTerminal = "A";
    }

    private Map<String, Object> createMap() throws IOException {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("package", packageName);
        result.put("annotation",
                IOUtils.toString(ClassLoader.getSystemResourceAsStream(ANNOTATION)));

        return result;
    }

    public void writeTokens() throws IOException, TemplateException {
        Map<String, Object> map = createMap();
        map.put("tokens", tokens);

        processTemplate(TOKEN_TEMPLATE, map, "Token.java");
    }

    public void writeLexer() throws IOException, TemplateException {
        Map<String, Object> map = createMap();
        map.put("tokens", tokens);
        map.put("term", terminals);

        processTemplate(LEXER_TEMPLATE, map, "LexicalAnalyzer.java");
    }

    public void writeException() throws IOException, TemplateException {
        processTemplate(EXCEPTION_TEMPLATE, createMap(), "ParseException.java");
    }

    public void writeNode() throws IOException, TemplateException {
        processTemplate(NODE_TEMPLATE, createMap(), "Node.java");
    }

    public void writeRuleNodes() throws IOException, TemplateException {
        for (String notTermerminal : notTerminals) {
            Map<String, Object> map = createMap();
            map.put("name", notTermerminal);
            map.put("attr_name", "Attribute");
            map.put("attr_type", "int");

            processTemplate(RULE_NODE_TEMPLATE, map, notTermerminal + "RuleNode.java");
        }
    }

    public void writeParser() throws IOException, TemplateException {
        List<String> methods = new ArrayList<String>();

        for (String notTerminal : notTerminals) {
            methods.add(createParserMethod(notTerminal));
        }

        Map<String, Object> map = createMap();
        map.put("first_rule_name", firstTerminal);
        map.put("methods", methods);

        processTemplate(PARSER_TEMPLATE, map, "Parser.java");
    }

    private String createParserMethod(String notTerminal) throws IOException, TemplateException {
        List<String> cases = new ArrayList<String>();

        for (String currentToken : first.get(notTerminal)) {
            cases.add(createParserMethodCase(notTerminal, currentToken));
        }

        Map<String, Object> map = createMap();
        map.put("name", notTerminal);
        map.put("cases", cases);
        map.put("first_set", first.get(notTerminal).toString());

        return templateToString(PARSER_METHOD_TEMPLATE, map);
    }

    private String createParserMethodCase(String NotTerminal, String currentToken) throws IOException {
        Rule currentRule = null;

        for (Rule rule : rules.get(NotTerminal)) {
            if (rule.containsInFirst(currentToken)) {
                currentRule = rule;
                break;
            }
        }

        if (currentRule == null) {
            throw new IOException("if u see this error - something wrong with grammar =(");
        }

        int index = 0;
        for (String token : currentRule.getTokens()) {
            if (notTerminals.contains(token)) {

            } else {

            }
            index++;
        }
        return "";
    }

    private void processTemplate(String templatePath, Map<String, Object> map, String fileName)
            throws IOException, TemplateException {
        if (output.exists() || output.mkdirs()) {
            getTemplate(templatePath).process(map, new PrintWriter(new File(output, fileName)));
        }
    }

    private String templateToString(String templatePath, Map<String, Object> map) throws IOException, TemplateException {
        StringWriter result = new StringWriter();
        getTemplate(templatePath).process(map, result);

        return result.toString();
    }

    private Template getTemplate(String templatePath) throws IOException {
        String name = new BigInteger(130, new SecureRandom()).toString(32);

        InputStream is = ClassLoader.getSystemResourceAsStream(templatePath);
        return new Template(name, new InputStreamReader(is), new Configuration());
    }

}
