package ru.ifmo.sta.lab04.baev;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.WordUtils;

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
    private static final String PARSER_TEMPLATE = "ru/ifmo/sta/lab04/baev/parser.ftl";
    private static final String PARSER_METHOD_TEMPLATE = "ru/ifmo/sta/lab04/baev/parser-method.ftl";
    private static final String PARSER_METHOD_CASE_TEMPLATE = "ru/ifmo/sta/lab04/baev/parser-method-case.ftl";

    private File output;
    private String packageName;

    private List<String> tokens;
    private List<String> regexps;
    private Set<String> notTerminals;
    private String firstNotTerminal;

    private Map<String, Set<String>> first;
    private Map<String, List<Rule>> rules;
    private Map<String, String> initCodes;

    public TemplateUtil(String packageName,
                        List<String> tokens,
                        List<String> regexps,
                        Set<String> notTerminals
    ) {
        this.output = new File("bantlr/"
                + packageName.replaceAll("/.", "/"));

        this.packageName = packageName;

        this.tokens = tokens;
        this.regexps = regexps;
        this.notTerminals = notTerminals;
        this.first = new HashMap<String, Set<String>>();
        first.put("A", new HashSet<String>(Arrays.asList("PLUS", "OPERAND")));
        this.rules = new HashMap<String, List<Rule>>();

        rules.put("A", Arrays.asList(
                new Rule("A", Arrays.asList("PLUS", "A", "A"),
                        new HashSet<String>(Arrays.asList("PLUS"))
                ),
                new Rule("A", Arrays.asList("OPERAND"),
                        new HashSet<String>(Arrays.asList("OPERAND"))
                )
        ));

        this.firstNotTerminal = "A";
    }

    public TemplateUtil(String packageName,
                        String firstNotTerminal,
                        Map<String, String> regexps,
                        Set<String> notTerminals,
                        Map<String, String> initCodes,
                        Map<String, List<Rule>> ruleCodes,
                        Map<String, Set<String>> first) {
        this.output = new File("bantlr/"
                + packageName.replaceAll("/.", "/"));

        this.packageName = packageName;
        this.firstNotTerminal = firstNotTerminal;

        this.tokens = new ArrayList<String>();
        this.regexps = new ArrayList<String>();

        List<String> tmp1 = new ArrayList<String>();
        List<String> tmp2 = new ArrayList<String>();

        for (String s : regexps.keySet()) {
            if (regexps.get(s).isEmpty()) {
                tmp1.add(s);
                tmp2.add(regexps.get(s));
                continue;
            }
            this.tokens.add(s);
            this.regexps.add(regexps.get(s));
        }

        for (int i = 0; i < tmp1.size(); i++) {
            this.tokens.add(tmp1.get(i));
            this.regexps.add(tmp2.get(i));
        }

        tokens.add("END");

        this.rules = ruleCodes;
        this.notTerminals = notTerminals;
        this.initCodes = initCodes;
        this.first = first;
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
        map.put("term", regexps);

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
            map.put("init_attr", initCodes.get(notTermerminal));

            processTemplate(RULE_NODE_TEMPLATE, map, WordUtils.capitalize(notTermerminal) + "RuleNode.java");
        }
    }

    public void writeParser() throws IOException, TemplateException {
        List<String> methods = new ArrayList<String>();

        for (String notTerminal : notTerminals) {
            methods.add(createParserMethod(notTerminal));
        }

        Map<String, Object> map = createMap();
        map.put("first_rule_name", firstNotTerminal);
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

    private String createParserMethodCase(String notTerminal, String currentToken) throws IOException, TemplateException {
        Rule currentRule = null;
        for (Rule rule : rules.get(notTerminal)) {
            if (rule.containsInFirst(currentToken)) {
                currentRule = rule;
                break;
            }
        }

        if (currentRule == null) {
            throw new IOException("if u see this error - something wrong with grammar =(");
        }

        List<String> lines = new ArrayList<String>();
        List<String> createdVars = new ArrayList<String>();
        int index = 0;
        for (String token : currentRule.getTokens()) {
            if (notTerminals.contains(token)) {
                lines.add(getLine1(token, index));
            } else {
                lines.add(getLine2(token, index));
            }

            createdVars.add(token + index);
            index++;
        }

        Map<String, Object> map = createMap();
        map.put("token", currentToken);
        map.put("lines", lines);
        map.put("name", notTerminal);
        map.put("created_vars", createdVars);
        map.put("code", currentRule.getCode());

        return templateToString(PARSER_METHOD_CASE_TEMPLATE, map);
    }

    private String getLine1(String token, int index) {
        return WordUtils.capitalize(token)
                + "RuleNode "
                + token
                + index
                + " = "
                + token
                + "(lexicalAnalyzer);";
    }

    private String getLine2(String token, int index) {
        return "Node "
                + token
                + index
                + " = new "
                + "Node(lexicalAnalyzer.getCurrentSubstring());";
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
