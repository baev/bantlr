package ru.ifmo.sta.lab04.baev;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Dmitry Baev charlie@yandex-team.ru
 *         Date: 02.06.13
 */
public class Rule {
    private String from;
    private List<String> tokens;
    private Set<String> first;

    public Rule(String from, List<String> tokens) {
        this.from = from;
        this.tokens = tokens;
        this.first = new HashSet<String>();
    }

    public String getFrom() {
        return from;
    }

    public List<String> getTokens() {
        return tokens;
    }

    public boolean containsInFirst(String terminal) {
        return first.contains(terminal);
    }
}
