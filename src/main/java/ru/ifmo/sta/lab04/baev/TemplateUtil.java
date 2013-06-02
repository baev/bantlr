package ru.ifmo.sta.lab04.baev;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Dmitry Baev charlie@yandex-team.ru
 *         Date: 02.06.13
 */
public class TemplateUtil {
    private static final String TOKEN_TEMPLATE_FILE_PATH = "ru/ifmo/sta/lab04/baev/token.ftl";
    private static final String LEXER_TEMPLATE_FILE_PATH = "ru/ifmo/sta/lab04/baev/lexer.ftl";
    private static final String EXCEPTION_TEMPLATE_FILE_PATH = "ru/ifmo/sta/lab04/baev/exception.ftl";
    private static final String NODE_TEMPLATE_FILE_PATH = "ru/ifmo/sta/lab04/baev/node.ftl";
    private static final String RULE_NODE_TEMPLATE_FILE_PATH = "ru/ifmo/sta/lab04/baev/rule-node.ftl";
    private static final String ANNOTATION_FILE_PATH = "ru/ifmo/sta/lab04/baev/annotation.txt";

    private File output;
    private String packageName;

    private List<String> tokens;
    private List<String> terminals;
    private List<String> notTerminals;

    public TemplateUtil(String packageName,
                        List<String> tokens,
                        List<String> terminals,
                        List<String> notTerminals
                        ) {
        this.output = new File("bantlr/"
                + packageName.replaceAll("/.", "/"));

        this.packageName = packageName;

        this.tokens = tokens;
        this.terminals = terminals;
        this.notTerminals = notTerminals;
    }

    private Map<String, Object> createMap() throws IOException {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("package", packageName);
        result.put("annotation",
                IOUtils.toString(ClassLoader.getSystemResourceAsStream(ANNOTATION_FILE_PATH)));

        return result;
    }

    public void writeTokens() throws IOException, TemplateException {
        Map<String, Object> map = createMap();
        map.put("tokens", tokens);

        processTemplate(TOKEN_TEMPLATE_FILE_PATH, map, "Token.java");
    }

    public void writeLexer() throws IOException, TemplateException {
        Map<String, Object> map = createMap();
        map.put("tokens", tokens);
        map.put("term", terminals);

        processTemplate(LEXER_TEMPLATE_FILE_PATH, map, "LexicalAnalyzer.java");
    }

    public void writeException() throws IOException, TemplateException {
        processTemplate(EXCEPTION_TEMPLATE_FILE_PATH, createMap(), "ParseException.java");
    }

    public void writeNode() throws IOException, TemplateException {
        processTemplate(NODE_TEMPLATE_FILE_PATH, createMap(), "Node.java");
    }

    public void writeRuleNodes() throws IOException, TemplateException {
        for (String notTermerminal : notTerminals) {
            Map<String, Object> map = createMap();
            map.put("name", notTermerminal);
            map.put("attr_name", "Attribute");
            map.put("attr_type", "int");

            processTemplate(RULE_NODE_TEMPLATE_FILE_PATH, map, notTermerminal + "RuleNode.java");
        }
    }

    private void processTemplate(String templatePath, Map<String, Object> map, String fileName)
            throws IOException, TemplateException {
        String name = new BigInteger(130, new SecureRandom()).toString(32);

        InputStream is = ClassLoader.getSystemResourceAsStream(templatePath);
        Template template = new Template(name, new InputStreamReader(is), new Configuration());

        if (output.exists() || output.mkdirs()) {
            template.process(map, new PrintWriter(new File(output, fileName)));
        }
    }

}
