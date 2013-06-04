private static ${name?cap_first}RuleNode ${name}(LexicalAnalyzer lexicalAnalyzer) throws ParseException {
    Token curToken = lexicalAnalyzer.getCurrentToken();

    switch (curToken) {

        <#list cases as case>
        ${case}<#if case_has_next>

        </#if>
        </#list>

        default:
        throw new ParseException("Expected ${first_set?upper_case}, but has "
        + lexicalAnalyzer.getCurrentToken(), lexicalAnalyzer.getPosition());

    }
}