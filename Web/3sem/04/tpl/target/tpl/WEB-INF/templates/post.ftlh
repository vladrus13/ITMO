<#-- @ftlvariable name="post_id" type="java.lang.Long" -->
<#import "common.ftl" as c/>

<@c.page>
    <#assign poster=c.findBy(posts, "id", post_id)!/>
    <#assign post=poster[1]>
    <@c.postMaker post=post view=false/>
</@c.page>
