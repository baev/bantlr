package ru.ifmo.sta.lab04.baev;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.commons.lang.WordUtils;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Dmitry Baev charlie@yandex-team.ru
 *         Date: 30.05.13
 */
public class Main {
    private static final String LEXER_FILE_PATH = "src/main/resources/ru/ifmo/sta/lab04/baev/token.ftl";

    public static void main(String[] args) throws IOException, TemplateException, ParseException {
//        Set<String> nTerm = new HashSet<String>();
//        nTerm.add("A");
//
//        List<String> term = new ArrayList<String>();
//        term.add("\\\\+");
//        term.add("[0-9]+");
//
//        List<String> tokens = new ArrayList<String>();
//        tokens.add("PLUS");
//        tokens.add("OPERAND");
//        tokens.add("END");
//
//        TemplateUtil util = new TemplateUtil("ru.ifmo.sta.lab04.baev", tokens, term, nTerm);
//        util.writeTokens();
//        util.writeLexer();
//        util.writeException();
//        util.writeNode();
//        util.writeRuleNodes();
//        util.writeParser();

//        System.out.println(WordUtils.capitalize("1sdsdf sdf fsdf"));;

        Node res = Parser.parse(new FileInputStream(new File("asd.txt")));
        write(res, "\t");


//         new Main().f();
    }

    public static void write(Node node, String tab) {
        System.out.println(tab + node.getName());
        for (Node child : node.getChildren()) {
            write(child, tab + "\t");
        }
    }
//
//    public void f() {
//        try {
//            f();
//        } catch (Exception ignored) {
//        }
//    }
}
