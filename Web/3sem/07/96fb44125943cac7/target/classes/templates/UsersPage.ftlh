<#-- @ftlvariable name="users" type="ru.itmo.wp.domain.User[]" -->
<#-- @ftlvariable name="noticeForm" type="ru.itmo.wp.form.DisableForm" -->
<#import "commons.ftlh" as c>

<@c.page>
    <div class="users datatable">
        <div class="caption">User</div>
        <table>
            <thead>
            <tr>
                <th>Id</th>
                <th>Login</th>
                <th>Created</th>
                <th>Disable</th>
            </tr>
            </thead>
            <tbody>
            <#if users?size==0>
                <tr class="noData">
                    <td colspan="4">
                        No data
                    </td>
                </tr>
            <#else>
                <#list users as user>
                <tr>
                    <td class="id">${user.id}</td>
                    <td class="login">${user.login}</td>
                    <td class="creationTime">${user.creationTime}</td>
                    <#if user.disabled>
                        <#assign value="Enable">
                    <#else>
                        <#assign value="Disable">
                    </#if>
                    <td class="disable">
                        <form method="post" action="">
                            <input type="hidden" name="action" value="disable"/>
                            <input type="hidden" name="id" value="${user.id}"/>
                            <input type="hidden" name="disable" value="${user.disabled?then('true' 'false')}"/>
                            <div class="button-field">
                                <input type="submit" value=${value}>
                            </div>
                        </form>
                    </td>
                </tr>
                </#list>
            </#if>
            </tbody>
        </table>
    </div>
</@c.page>
