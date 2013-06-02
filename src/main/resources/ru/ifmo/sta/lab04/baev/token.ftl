package ${package};

${annotation}
@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public enum Token {
<#list tokens as t>
    ${t}<#if t_has_next>,</#if>
</#list>
}
