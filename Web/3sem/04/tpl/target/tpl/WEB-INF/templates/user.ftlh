<#-- @ftlvariable name="handle" type="java.lang.String" -->
<#-- @ftlvariable name="user_id" type="java.lang.Long" -->
<#import "common.ftl" as c/>

<@c.page>
    <#if handle??>
        <#assign viewedUserInfo=c.findBy(users, "handle", handle)!/>
    <#else>
        <#assign viewedUserInfo=c.findBy(users, "id", user_id)!/>
    </#if>
    <#assign viewedUser=viewedUserInfo[1]!>
    <#assign viewedUserPrevious=viewedUserInfo[0]!>
    <#assign viewedUserNext=viewedUserInfo[2]!>
    <div class="info-user">
        <#if viewedUser.id??>
            <div class="left">
                <div class="link">
                    <@c.userlink user=viewedUser nameOnly=false/>
                </div>
                <li>id: ${viewedUser.id}</li>
                <li>handle: ${viewedUser.handle}</li>
                <li>name: ${viewedUser.name}</li>
                <li><a href="/posts?user_id=${viewedUser.id}">${c.countPosts(viewedUser.id)}</a></li>
            </div>
            <div class="right">
                <img src="img/user.jpg">
            </div>
        <#else>
            <h1>No such user</h1>
        </#if>
    </div>
    <div class="arrows">
        <div class="left-arrow">
            <#if viewedUserPrevious?has_content>
                <a href="/user?handle=${viewedUserPrevious.handle}" style="text-decoration: none">←</a>
            <#else>
                ←
            </#if>
        </div>
        <div class="right-arrow">
            <#if viewedUserNext?has_content>
                <a href="/user?handle=${viewedUserNext.handle}" style="text-decoration: none">→</a>
            <#else>
                →
            </#if>
        </div>
    </div>
</@c.page>
