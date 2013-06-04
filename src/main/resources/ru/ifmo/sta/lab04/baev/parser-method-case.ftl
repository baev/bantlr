case ${token}: {
    <#list lines as line>
    ${line}<#if line_has_next>
    lexicalAnalyzer.nextToken();
    </#if>
    </#list>

    ${name?cap_first}RuleNode ${name} = new ${name?cap_first}RuleNode("${name}", Arrays.<Node>asList(
        <#list created_vars as var>
        ${var}<#if var_has_next>,</#if>
        </#list>
    ));

    ${code};

    return ${name};
}