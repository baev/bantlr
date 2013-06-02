private static ${name}RuleNode ${name}(LexicalAnalyzer lexicalAnalyzer) throws ParseException {
    Token curToken = lexicalAnalyzer.getCurrentToken();

    switch (curToken) {

        <#list cases as case>
        ${case}<#if case_has_next>

        </#if>
        </#list>

        default:
        throw new ParseException("Expected ${first_to_string}, but has "
        + lexicalAnalyzer.getCurrentToken(), lexicalAnalyzer.getPosition());

    }
}