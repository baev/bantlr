package ru.ifmo.sta.lab04.baev;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Dmitry Baev charlie@yandex-team.ru
 *         Date: 30.05.13
 */
public class Main {
    private static final String LEXER_FILE_PATH = "src/main/resources/ru/ifmo/sta/lab04/baev/token.ftl";

    public static void main(String[] args) throws IOException, TemplateException {
        List<String> nTerm = new ArrayList<String>();
        nTerm.add("A");
        nTerm.add("S");

        List<String> term = new ArrayList<String>();
        term.add("\\\\+");
        term.add("[0-9]+");

        List<String> tokens = new ArrayList<String>();
        tokens.add("PLUS");
        tokens.add("OPERAND");
        tokens.add("END");

        TemplateUtil util = new TemplateUtil("ru.ifmo.sta.lab04.baev", tokens, term, nTerm);
        util.writeTokens();
        util.writeLexer();
        util.writeException();
        util.writeNode();
        util.writeRuleNodes();


    }
}
