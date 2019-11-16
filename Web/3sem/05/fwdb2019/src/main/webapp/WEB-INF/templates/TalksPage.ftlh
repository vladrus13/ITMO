<#-- @ftlvariable name="talks" type="ru.itmo.wp.model.domain.Talk[]" -->
<#-- @ftlvariable name="users" type="ru.itmo.wp.model.domain.User[]" -->
<#import "commons.ftlh" as c>

<@c.page>
    <form method="post" action="">
        <input type="hidden" name="action" value="talk"/>
        <div class="field">
            <label>
                <select name="select">
                    <!--<option selected disabled>Выберите Пользователя</option>-->
                    <#list users as user>
                        <option>${user.login}</option>
                    </#list>
                </select>
            </label>
        </div>
        <div class="field">
            <label>
                <textarea name="text" rows="5" cols="60"></textarea>
            </label>
            <div class="button-field">
                <input type="submit" value="Text">
            </div>
        </div>
    </form>
    <div class="datatable">
        <div class="caption">Messages</div>
        <table>
            <thead>
            <tr>
                <th>Id</th>
                <th>To</th>
                <th>From</th>
                <th>Text</th>
                <th>When</th>
            </tr>
            </thead>
            <tbody>
            <#list talks as talk>
                <#assign target=findBy(users, "id", talk.targetUserId)!/>
                <#assign source=findBy(users, "id", talk.sourceUserId)!/>
                <tr>
                    <td>${talk.id}</td>
                    <td>${target.login}</td>
                    <td>${source.login}</td>
                    <td>${talk.text}</td>
                    <td>${talk.creationTime}</td>
                </tr>
            </#list>
            </tbody>
        </table>
    </div>
</@c.page>

<#function findBy items key id>
    <#list items as item>
        <#if item[key]==id>
            <#assign index = item_index>
            <#return items[index]!/>
        </#if>
    </#list>
</#function>
