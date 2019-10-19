<#-- @ftlvariable name="user_id" type="java.lang.Long" -->
<#import "common.ftl" as c/>

<@c.page>
    <#list posts as post>
        <#if post.user_id==user_id>
            <@c.postMaker post=post view=true/>
        </#if>
    </#list>
</@c.page>
