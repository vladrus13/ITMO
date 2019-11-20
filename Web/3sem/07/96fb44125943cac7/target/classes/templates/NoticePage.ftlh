<#-- @ftlvariable name="noticeForm" type="ru.itmo.wp.form.NoticeForm" -->
<#import "/spring.ftl" as spring>
<#import "commons.ftlh" as c>

<@c.page>
    <div class="notice form-box">
        <div class="header">Notice</div>
        <div class="body">
            <form method="post" action="">
                <input type="hidden" name="action" value="notice"/>
                <div class="field">
                    <div class="name">
                        <label for="context">Content</label>
                    </div>
                    <div class="value">
                        <label>
                            <textarea id="content" name="content" rows="5" cols="20"></textarea>
                        </label>
                    </div>
                    <@c.error "noticeForm.content"/>
                </div>
                <div class="button-field">
                    <input type="submit" value="Notice!">
                </div>
            </form>
        </div>
    </div>
</@c.page>
