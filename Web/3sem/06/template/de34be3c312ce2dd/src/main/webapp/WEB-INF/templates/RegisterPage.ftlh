<#-- @ftlvariable name="login" type="java.lang.String" -->
<#-- @ftlvariable name="password" type="java.lang.String" -->
<#-- @ftlvariable name="error" type="java.lang.String" -->
<#import "commons.ftlh" as c>

<@c.page>
    <div class="registration form-box">
        <div class="header">Register</div>
        <div class="body">
            <form method="post" action="">
                <input type="hidden" name="action" value="register"/>
                <div class="field">
                    <div class="name">
                        <label for="login">Login</label>
                    </div>
                    <div class="value">
                        <input id="login" name="login" value="${login!}"/>
                    </div>
                </div>
                <div class="field">
                    <div class="name">
                        <label for="password">Password</label>
                    </div>
                    <div class="value">
                        <input id="password" name="password" type="password" value="${password!}"/>
                    </div>
                </div>
                <div class="error">
                    ${error!}
                </div>
                <div class="button-field">
                    <input type="submit" value="Register">
                </div>
            </form>
        </div>
    </div>

    <script>
        $(function () {
            $(".registration form").submit(function () {
                var login = $(this).find("input[name='login']").val();
                var password = $(this).find("input[name='password']").val();
                var $error = $(this).find(".error");

                $.ajax({
                    type: "POST",
                    url: "",
                    dataType: "json",
                    data: {
                        action: "register",
                        login: login,
                        password: password
                    },
                    success: function (response) {
                        if (response["error"]) {
                            $error.text(response["error"]);
                        } else {
                            location.href = response["redirect"];
                        }
                    }
                });
                //
                // $.post("", {
                //     action: "register",
                //     login: login,
                //     password: password
                // }, function (response) {
                //     if (response["error"]) {
                //         $error.text(response["error"]);
                //     } else {
                //         location.href = response["redirect"];
                //     }
                // });

                return false;
            });
        })
    </script>
</@c.page>
