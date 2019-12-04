<#-- @ftlvariable name="user" type="ru.itmo.wp.domain.User" -->
<#-- @ftlvariable name="message" type="java.lang.String" -->
<#import "/spring.ftl" as spring/>
<#macro page>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Codeforces</title>
    <link rel="icon" type="image/png" href="<@spring.url '/favicon.png'/>" />
    <link rel="stylesheet" type="text/css" href="<@spring.url '/css/normalize.css'/>">
    <link rel="stylesheet" type="text/css" href="<@spring.url '/css/style.css'/>">
    <link rel="stylesheet" type="text/css" href="<@spring.url '/css/form.css'/>">
    <link rel="stylesheet" type="text/css" href="<@spring.url '/css/datatable.css'/>">
    <link rel="stylesheet" type="text/css" href="<@spring.url '/css/article.css'/>">
    <script src="<@spring.url '/js/jquery-3.4.1.min.js'/>"></script>
    <script src="<@spring.url '/js/notify.js'/>"></script>
</head>
<body>
<header>
    <a href="/"><img src="<@spring.url '/img/logo.png'/>" alt="Codeforces" title="Codeforces"/></a>
    <div class="languages">
        <a href="#"><img src="<@spring.url '/img/gb.png'/>" alt="In English" title="In English"/></a>
        <a href="#"><img src="<@spring.url '/img/ru.png'/>" alt="In Russian" title="In Russian"/></a>
    </div>
    <div class="enter-or-register-box">
        <#if user??>
            ${user.login}
            |
            <a href="/logout">Logout</a>
        <#else>
            <a href="/enter">Enter</a>
            |
            <a href="/register">Register</a>
        </#if>
    </div>
    <nav>
        <ul>
            <li><a href="/">Home</a></li>
            <li><a href="/users/all">Users</a></li>
            <#if user??>
                <li><a href="/writePost">Write Post</a></li>
                <li><a href="/posts">Posts</a></li>
            </#if>
        </ul>
    </nav>
</header>
<div class="middle">
    <aside>
        <section>
            <div class="header">
                Pay attention
            </div>
            <div class="body">
                Lorem ipsum dolor sit amet, consectetur adipisicing elit. Cupiditate ducimus enim facere impedit nobis,
                nulla placeat quam suscipit unde voluptatibus.
            </div>
            <div class="footer">
                <a href="#">View all</a>
            </div>
        </section>
    </aside>
    <main>
        <#nested/>
    </main>
</div>
<footer>
    <a href="/">Codeforces</a> &copy; 2019 by Mike Mirzayanov
</footer>
</body>
<script>
    $(function () {
        <#if message??>
            $.notify("${message?js_string}", {className: "success", position: "bottom right"});
        </#if>
    })
</script>
</html>
</#macro>

<#macro error field>
    <@spring.bind field/>
    <#if spring.status.errorMessage??>
        <div class="error">${spring.status.errorMessage}</div>
    </#if>
</#macro>

<#macro post post visible>
<#-- @ftlvariable name="post" type="ru.itmo.wp.domain.Post" -->
    <div class="post">
        <div class="title"><a href="/post/${post.id}">${post.title}</a></div>
        <div class="information">By ${post.user.login}, ${post.creationTime}</div>
        <div class="body">
            ${post.text?no_esc}
        </div>
        <ul class="attachment">
            <#list post.tages as tag>
                <li>${tag.name}</li>
            </#list>
        </ul>
        <#if visible>
        <div class="footer">
            <div class="left">
                <img src="img/voteup.png" title="Vote Up" alt="Vote Up"/>
                <span class="positive-score">+173</span>
                <img src="img/votedown.png" title="Vote Down" alt="Vote Down"/>
            </div>
            <div class="right">
                <img src="img/date_16x16.png" title="Publish Time" alt="Publish Time"/>
                ${post.creationTime}
                <img src="img/comments_16x16.png" title="Comments" alt="Comments"/>
                <a href="#">68</a>
            </div>
        </div>
        </#if>
    </div>
</#macro>

<#macro comment comment>
<#-- @ftlvariable name="comment" type="ru.itmo.wp.domain.Comment" -->
    <div class="comment">
        <div class="information">By ${comment.user.login} </div>
        <div class="body">
            ${comment.text?no_esc}
        </div>
    </div>
</#macro>