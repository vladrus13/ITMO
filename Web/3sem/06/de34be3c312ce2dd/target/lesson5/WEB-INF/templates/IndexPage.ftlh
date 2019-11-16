<#import "commons.ftlh" as c>
<#-- @ftlvariable name="posts" type="ru.itmo.wp.model.domain.Article[]" -->

<@c.page>
    <div class="posts table">
        <template>
            <@c.postMaker/>
        </template>
        <p class="noData">
           NoData
        </p>
    </div>
    <script>
        $(function () {
            var $noData = $(".posts .noData");
            $noData.text("Loading...");
            ajax({
                    action: "findAll"
                },
                function (response) {
                    response["posts"].forEach(function (post) {
                        $noData.hide();
                        if (post["hidden"] === false) {
                            var $post = $($(".posts template").prop("content")).clone()
                            $post.find(".title").text(post["title"]);
                            $post.find(".body").text(post["text"]);
                            $(".posts").append($post);
                        }
                    });
                }
            );
        })
    </script>
</@c.page>
