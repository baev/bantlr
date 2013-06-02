package ru.ifmo.sta.lab04.baev;

/**
* @author Dmitry Baev charlie@yandex-team.ru
*         Date: 02.06.13
*/

import java.io.InputStream;
import java.util.Arrays;

public class Parser {
    public static Node parse(InputStream is) throws ParseException {
        LexicalAnalyzer lexicalAnalyzer = new LexicalAnalyzer(is);

        lexicalAnalyzer.nextToken();
        return A(lexicalAnalyzer);
    }

    private static ARuleNode A(LexicalAnalyzer lexicalAnalyzer) throws ParseException {
        Token curToken = lexicalAnalyzer.getCurrentToken();
        String substr = lexicalAnalyzer.getCurrentSubstring();

        switch (curToken) {
            case PLUS:
                lexicalAnalyzer.nextToken();
                ARuleNode a1 = A(lexicalAnalyzer);
                lexicalAnalyzer.nextToken();
                ARuleNode a2 = A(lexicalAnalyzer);

                return new ARuleNode("A", Arrays.asList(
                        new Node(substr), a1, a2));

            case OPERAND:

                return new ARuleNode(substr);

            default:
                throw new ParseException("Expected [PLUS, OPERAND], but has "
                        + lexicalAnalyzer.getCurrentToken(), lexicalAnalyzer.getPosition());

        }
    }
}