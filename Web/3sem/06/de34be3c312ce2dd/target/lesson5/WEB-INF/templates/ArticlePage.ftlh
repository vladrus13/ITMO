<#-- @ftlvariable name="login" type="java.lang.String" -->
<#-- @ftlvariable name="password" type="java.lang.String" -->
<#-- @ftlvariable name="error" type="java.lang.String" -->
<#import "commons.ftlh" as c>

<@c.page>
    <div class="posting form-box">
        <div class="header">Make post</div>
        <div class="body">
            <form method="post" action="">
                <input type="hidden" name="action" value="posting"/>
                <div class="field">
                    <div class="name">
                        <label for="title">Title</label>
                    </div>
                    <div class="value">
                        <input id="title" name="title"/>
                    </div>
                </div>
                <div class="field">
                    <label>
                        <textarea id="text" name="text" rows="5" cols="60"></textarea>
                    </label>
                </div>
                <div class="button-field">
                    <input type="submit" value="Post">
                </div>
            </form>
        </div>
    </div>

    <script>
        $(function () {
            $(".posting form").submit(function () {
                var title = $(this).find("input[name='title']").val();
                var text = $(this).find("textarea[name='text']").val();

                ajax({
                    action: "article",
                    title: title,
                    text: text},
                    function (response) {

                    }
                );
                return false;
            });
        })
    </script>
</@c.page>
