<#import "common.ftl" as c/>

<@c.page>
    <div class="datatableusers">
        <div class="caption">All users</div>
        <table>
            <thead>
            <tr>
                <th>Id</th>
                <th>Name</th>
                <th>Handle</th>
            </tr>
            </thead>
            <tbody>
            <#list users as i>
                <tr>
                    <td>${i.id}</td>
                    <td><@c.userlink user=i nameOnly=false/></td>
                    <td>${i.handle}</td>
                </tr>
            </#list>
            </tbody>
        </table>
    </div>
</@c.page>
