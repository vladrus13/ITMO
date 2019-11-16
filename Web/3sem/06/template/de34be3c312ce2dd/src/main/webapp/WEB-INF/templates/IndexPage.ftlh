<#import "commons.ftlh" as c>

<@c.page>
    Welcome on Codeforces!

<script>
    $(function () {
        $("aside section").click(function () {
            $(this).css("background-color", "red");
        });
    })
</script>
</@c.page>
