<#-- @ftlvariable name="postGet" type="ru.itmo.wp.domain.Post" -->
<#-- @ftlvariable name="comments" type="ru.itmo.wp.domain.Comment[]" -->
<#-- @ftlvariable name="commentForm" type="ru.itmo.wp.domain.Comment" -->
<#-- @ftlvariable name="isLogged" type="boolean" -->
<#import "commons.ftlh" as c>

<@c.page>
    <@c.post post=postGet visible=false/>
    <#if isLogged>
    <div class="form">
        <div class="header">Write Comment</div>
        <div class="body">
            <form method="post" action="">
                <input type="hidden" name="action" value="writeComment"/>
                <div class="field">
                    <div class="name">
                        <label for="text">Text</label>
                    </div>
                    <div class="value">
                        <textarea id="text" name="text">${commentForm.text!}</textarea>
                    </div>
                    <@c.error "commentForm.text"/>
                </div>
                <div class="button-field">
                    <input type="submit" value="Write">
                </div>
            </form>
        </div>
    </div>
    </#if>
    <#list postGet.comments as comment>
        <@c.comment comment=comment/>
    </#list>
</@c.page>
