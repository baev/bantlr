case ${token}:
    <#list lines as line>
    ${line}<#if line_has_next>
    lexicalAnalyzer.nextToken();
    </#if>
    </#list>

    return new ${name}RuleNode("${name}", Arrays.asList(
        <#list created_vars as var>
        ${var}<#if var_has_next>,</#if>
        </#list>
    ));