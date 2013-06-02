package ru.ifmo.sta.lab04.baev;

import java.io.IOException;
import java.util.*;

/**
 * @author Dmitry Baev charlie@yandex-team.ru
 *         Date: 02.06.13
 */
public class ParserGenerator {
    private String result;
    private Set<String> notTerminals;
    private Map<String, List<String>> first;
    private Map<String, List<Rule>> rules;

    public ParserGenerator() {
        this.result = "";
        this.notTerminals = new HashSet<String>();
        this.first = new HashMap<String, List<String>>();
    }

    public void generate() throws IOException {
        for (String notTerminal : notTerminals) {
            for (String term : first.get(notTerminal)) {
                Rule currentRule = null;

                for (Rule rule : rules.get(notTerminal)) {
                    if (rule.containsInFirst(term)) {
                        currentRule = rule;
                        break;
                    }
                }

                if (currentRule == null) {
                    throw new IOException();
                }

                int index = 0;
                for (String token : currentRule.getTokens()) {
                    if (notTerminals.contains(token)) {
                        writeNTLines(notTerminal, token, index);
                    } else {
                        writeTLines(notTerminal, token, index);
                    }
                    index++;
                }
            }

        }
    }

    private void writeTLines(String notTerminal, String token, int index) {
        //To change body of created methods use File | Settings | File Templates.
    }

    private void writeNTLines(String notTerminal, String token, int index) {
        String res = notTerminal
                + "RuleNode "
                + notTerminal
                + index
                + " = A(lexicalAnalyzer);"
                + "\n"
                + "lexicalAnalyzer.nextToken();";
    }


}
