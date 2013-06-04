package ru.ifmo.sta.lab04.baev;

import freemarker.template.TemplateException;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Dmitry Baev charlie@yandex-team.ru
 *         Date: 04.06.13
 */
public class Generator {
    private static final String GRAMMAR_FILE_PATH = "grammar.txt";

    private static final String EPS = "EPS";

    private static final String REGEXP_INIT = "^(.+)<(.+)>$";
    private static final String REGEXP_RULE = "^\\W*(\\w+)\\s*:(.*)\\{(.*)}$";
    private static final String REGEXP_TERM = "^\\W*(\\w+)\\s*:.*<(.*)>[^>]*$";

    private String grammarFile;
    private String firstNotTerminal = "";
    private Map<String, String> initCodes = new HashMap<String, String>();
    private Map<String, List<Rule>> rules = new HashMap<String, List<Rule>>();
    private Set<String> notTerminals = new HashSet<String>();
    private Map<String, String> terminals = new HashMap<String, String>();
    private Map<String, Set<String>> firsts = new HashMap<String, Set<String>>();

    public void parse() throws IOException {
        grammarFile = IOUtils.toString(ClassLoader.getSystemResourceAsStream(GRAMMAR_FILE_PATH));
        String[] lines = grammarFile.split("\n");

        int i;
        for (i = 0; i < lines.length && !lines[i].equals("#"); i++) {
            if (lines[i].replaceAll("\\s", "").isEmpty()) {
                continue;
            }

            String code = lines[i].replaceAll(REGEXP_INIT, "$2");
            String nt = lines[i].replaceAll(REGEXP_INIT, "$1").replaceAll("\\s", "");
            initCodes.put(nt, code);
        }
        i++;

        for (; i < lines.length; i++) {
            if (lines[i].replaceAll("\\s", "").isEmpty()) {
                continue;
            }

            Pattern pattern = Pattern.compile(REGEXP_RULE);
            Matcher matcher = pattern.matcher(lines[i]);

            if (matcher.find()) {
                String nt = matcher.group(1);
                String to = matcher.group(2);
                String code = matcher.group(3);

                if (firstNotTerminal.isEmpty()) {
                    firstNotTerminal = nt;
                }

                List<String> tokens = new ArrayList<String>();
                for (String s : to.split("\\s")) {
                    if (!s.isEmpty()) {
                        tokens.add(s);
                    }
                }

                Rule rule = new Rule(nt, tokens);
                rule.setCode(code);

                if (rules.containsKey(nt)) {
                    rules.get(nt).add(rule);
                } else {
                    List<Rule> tmp = new ArrayList<Rule>();
                    tmp.add(rule);
                    rules.put(nt, tmp);
                }

                notTerminals.add(nt);
            } else {
                Pattern p = Pattern.compile(REGEXP_TERM);
                Matcher m = p.matcher(lines[i]);

                if (m.find()) {
                    String terminal = m.group(1);
                    String regexp = m.group(2);

                    terminals.put(terminal, regexp);
                }
            }
        }

//        firsts.put(EPS, new HashSet<String>(Arrays.asList(EPS)));
        for (String nt : notTerminals) {
            firsts.put(nt, new HashSet<String>());
        }

        for (String t : terminals.keySet()) {
            Set<String> s = new HashSet<String>();
            s.add(t);
            firsts.put(t, s);
        }

        for (String nt : rules.keySet()) {
            for (Rule rule : rules.get(nt)) {
                for (String token : rule.getTokens()) {
                    if (token.contains(EPS)) {
                        firsts.get(nt).add(EPS);
                    }
                }
            }
        }

        boolean q = true;
        while (q) {
            q = false;

            for (String nt : rules.keySet()) {
                for (Rule rule : rules.get(nt)) {
                    int index = 0;
                    do {
                        for (String f : firsts.get(rule.getTokens().get(index))) {
                            if (!firsts.get(nt).contains(f)) {
                                q = true;
                                firsts.get(nt).add(f);
                            }
                        }
                        index++;
                    } while (index < rule.getTokens().size() &&
                            firsts.get(rule.getTokens().get(index)).contains(EPS));
                }
            }
        }

        for (String nt : rules.keySet()) {
            for (Rule rule : rules.get(nt)) {
                Set<String> f = new HashSet<String>();
                int index = 0;
                do {
                    f.addAll(firsts.get(rule.getTokens().get(index)));
                    index++;
                } while (index < rule.getTokens().size() &&
                        firsts.get(rule.getTokens().get(index)).contains(EPS));

                rule.setFirst(f);
            }
        }
    }

    public static void main(String[] args) throws IOException, TemplateException {
        Generator generator = new Generator();
        generator.parse();

        String packageName = "ru.ifmo.sta.lab04.baev";
        TemplateUtil util = new TemplateUtil(
                packageName,
                generator.firstNotTerminal,
                generator.terminals,
                generator.notTerminals,
                generator.initCodes,
                generator.rules,
                generator.firsts
        );

        util.writeTokens();
        util.writeLexer();
        util.writeException();
        util.writeNode();
        util.writeRuleNodes();
        util.writeParser();
    }
}
