<#-- @ftlvariable name="info_user" type="ru.itmo.wp.domain.User" -->
<#import "commons.ftlh" as c>

<@c.page>
    <div class="info-user">
        <#if info_user??>
            <p style="font-size: 1.5rem; font-weight: bold">Login: ${info_user.login}</p>
            <p>Id: ${info_user.id}</p>
            <p>Creation time: ${info_user.creationTime}</p>
        <#else>
            <p style="font-size: 1.5rem; font-weight: bold">NO SUCH USER</p>
        </#if>
    </div>
</@c.page>
