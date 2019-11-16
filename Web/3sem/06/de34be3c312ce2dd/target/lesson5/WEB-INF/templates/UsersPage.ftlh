<#-- @ftlvariable name="users" type="ru.itmo.wp.model.domain.User[]" -->
<#import "commons.ftlh" as c>

<@c.page>
    <div class="users datatable">
        <div class="caption">User</div>
        <table>
            <thead>
            <tr>
                <th>Id</th>
                <th>Login</th>
            </tr>
            </thead>
            <tbody>
            <template>
                <tr>
                    <form method="post" action="">
                        <input type="hidden" name="action" value="hide"/>
                        <td class="user_id"></td>
                        <td class="user_login"></td>
                        <td>
                            <div class="button-field">
                                <input class="button-submit" type="submit" value="NO">
                            </div>
                        </td>
                    </form>
                </tr>
            </template>
            <tr class="noData">
                <td colspan="3">
                    No data
                </td>
            </tr>
            </tbody>
        </table>
    </div>

    <script>
        $(function () {
            var $noData = $(".users .noData");
            $noData.find("td").text("Loading...");
            reload = function () {
                ajax({
                        action: "findAll"
                    },
                    function (response) {
                        $(".users tr").remove();
                        var logged = response["logged"];
                        response["users"].forEach(function (user) {
                            $noData.hide();
                            var $tr = $($(".users template").prop("content")).clone();
                            $tr.find(".user_id").text(user["id"]);
                            $tr.find(".user_login").text(user["login"]);
                            if (logged !== undefined && logged["admin"] === true) {
                                var value = "NO";
                                if (user["admin"]) {
                                    value = "Enable";
                                } else {
                                    value = "Disable";
                                }
                                $tr.find(".button-submit").attr({
                                    "class": "button-submit",
                                    "type": "submit",
                                    "value": value,
                                    "onclick": "adminMark(" + user["id"] + "," + (!user["admin"]) + ")"
                                });
                            } else {
                                $tr.find(".button-submit").parent().parent().hide();
                            }
                            $(".users tbody").append($tr);
                        });
                    }
                );
            };
            adminMark = function (userId, to) {
                ajax({
                        action: "markAdmin",
                        choosen: userId,
                        to: to},
                    function (response) {
                        reload();
                    }
                );
                return false;
            }
            reload();
        })
    </script>
</@c.page>
