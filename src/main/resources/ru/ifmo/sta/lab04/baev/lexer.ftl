package ${package};

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

${annotation}
@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class LexicalAnalyzer {
    private InputStream is;
    private Token currentToken;
    private int position;
    private int operand;
    private String resource;
    private String currentSubstring;

    private static final String[] term = new String[]{
        <#list term as t>
        "${t}"<#if t_has_next>,</#if>
        </#list>
    };
    private static final Token[] tokens = new Token[]{
        <#list tokens as t>
        Token.${t}<#if t_has_next>,</#if>
        </#list>
    };

    private List<Pattern> patterns = new ArrayList<Pattern>();

    public LexicalAnalyzer(InputStream is) throws ParseException {
        this.is = is;
        this.position = 0;
        this.operand = 0;
        this.currentSubstring = "";

        try {
            this.resource = IOUtils.toString(is);
        } catch (IOException e) {
            throw new ParseException(e.getMessage(), position);
        }

        compilePatterns();
    }

    private void compilePatterns() {
        for (String s : term) {
            patterns.add(Pattern.compile("^" + s));
        }
    }

    public void nextToken() throws ParseException {
        if (resource.length() == 0) {
            currentToken = Token.END;
            return;
        }

        int patternIndex = -1;
        for (int i = 0; i < patterns.size(); i++) {
            Matcher matcher = patterns.get(i).matcher(resource);

            if (matcher.find()) {
                patternIndex = i;

                position += matcher.end();
                resource = resource.substring(matcher.end(), resource.length());
                resource = resource.replaceAll("^\\s+", "");

                currentSubstring = matcher.group();
                break;
            }
        }

        if (patternIndex < 0) {
            throw new ParseException("Illegal character \""
                + resource.charAt(0) + "\"", position);
        }

        currentToken = tokens[patternIndex];
    }

    public String getCurrentSubstring() {
        return currentSubstring;
    }

    public Token getCurrentToken() {
        return currentToken;
    }

    public int getPosition() {
        return position;
    }

    public int getOperand() {
        return operand;
    }

    public boolean hasNextToken() {
        return currentToken != Token.END;
    }
}
