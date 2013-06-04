package ru.ifmo.sta.lab04.baev;

import java.io.FileNotFoundException;

/**
* @author Dmitry Baev charlie@yandex-team.ru
*         Date: 04.06.13
*/
public class RunSample {
    public static final String EXAMPLE_FILE_PATH = "example.txt";

    public static void main(String[] args) throws FileNotFoundException, ParseException {
        SRuleNode res = Parser.parse(ClassLoader.getSystemResourceAsStream(EXAMPLE_FILE_PATH));
        write(res, "\t");
        System.out.println(res.val);
    }

    public static void write(Node node, String tab) {
        System.out.println(tab + node.getName());
        for (Node child : node.getChildren()) {
            write(child, tab + "\t");
        }
    }
}
