<#import "common.ftl" as c/>

<@c.page>
        <#list posts?reverse as i>
                <@c.postMaker post=i view=true/>
        </#list>
</@c.page>
