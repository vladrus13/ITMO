<#-- @ftlvariable name="login" type="java.lang.String" -->
<#-- @ftlvariable name="password" type="java.lang.String" -->
<#-- @ftlvariable name="error" type="java.lang.String" -->
<#import "commons.ftlh" as c>

<@c.page>
    <div class="posts datatable">
        <div class="caption">Posts</div>
        <table>
            <thead>
            <tr>
                <th>Id</th>
                <th>Title</th>
                <th>Action</th>
            </tr>
            </thead>
            <tbody>
            <template>
                <tr>
                    <form method="post" action="">
                        <input type="hidden" name="action" value="hide"/>
                        <td class="id"></td>
                        <td class="title"></td>
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
            var $noData = $(".posts .noData");
            $noData.find("td").text("Loading...");
            reload = function() {
                ajax({
                        action: "findAll"
                    },
                    function (response) {
                        $(".posts tr").remove();
                        response["posts"].forEach(function (post) {
                            $noData.hide();
                            var $tr = $($(".posts template").prop("content")).clone();
                            $tr.find(".id").text(post["id"]);
                            $tr.find(".title").text(post["title"]);
                            var value = "NO";
                            if (post["hidden"]) {
                                value = "Hide";
                            } else {
                                value = "Show";
                            }
                            $tr.find(".button-submit").attr({
                                "class": "button-submit",
                                "type": "submit",
                                "value": value,
                                "onclick": "complete(" + post["id"] + ")"
                            });
                            $(".posts tbody").append($tr);
                        });

                    }
                );
            };
            complete = function (postId) {
                ajax({
                        action: "mark",
                        id: postId},
                    function (response) {
                        reload();
                    }
                );
                return false;
            };
            reload();
        })
    </script>
</@c.page>
