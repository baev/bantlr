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
    private String code;

    public Rule(String from, List<String> tokens) {
        this.from = from;
        this.tokens = tokens;
        this.first = new HashSet<String>();
    }

    public Rule(String from, List<String> tokens, Set<String> first) {
        this.from = from;
        this.tokens = tokens;
        this.first = first;
    }

    public String getFrom() {
        return from;
    }

    public List<String> getTokens() {
        return tokens;
    }

    public String getCode() {
        return code;
    }

    public void setFirst(Set<String> first) {
        this.first = first;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public boolean containsInFirst(String terminal) {
        return first.contains(terminal);
    }

    @Override
    public String toString() {
        return "Rule{" +
                "tokens=" + tokens +
                ", from='" + from + '\'' +
                '}';
    }
}
