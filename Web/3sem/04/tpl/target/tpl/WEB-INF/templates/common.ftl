<#macro header>
<header>
    <a href="/"><img src="/img/logo.png" alt="Codeforces" title="Codeforces"/></a>
    <div class="languages">
        <a href="#"><img src="/img/gb.png" alt="In English" title="In English"/></a>
        <a href="#"><img src="/img/ru.png" alt="In Russian" title="In Russian"/></a>
    </div>
    <div class="enter-or-register-box">
        <#if user??>
            <@userlink user=user nameOnly=true/>
            |
            <a href="#">Logout</a>
        <#else>
            <a href="#">Enter</a>
            |
            <a href="#">Register</a>
        </#if>
    </div>
    <nav>
        <ul>
            <#list links as link>
                <li><a href="${link.link}"
                       <#if uri?starts_with(link.link)>
                            style="border-bottom: 3px solid blue"
                       </#if>
                    >${link.name}
                    </a>
                </li>
            </#list>
        </ul>
    </nav>
</header>
</#macro>

<#macro sidebar>
<aside>
    <section>
        <#list posts as i>
            <div class="mix-post">
                <div class="header">
                    Post #${i.id}
                </div>
                <div class="body" style="list-style-type: none">
                    <@textPostMaker post=i view=true/>
                </div>
                <div class="footer">
                    <a href="/post?post_id=${i.id}">View all</a>
                </div>
            </div>
        </#list>
    </section>
</aside>
</#macro>

<#macro footer>
<footer>
    <a href="#">Codeforces</a> &copy; 2010-2019 by Mike Mirzayanov
</footer>
</#macro>

<#macro page>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Codeforces</title>
    <link rel="stylesheet" type="text/css" href="/css/normalize.css">
    <link rel="stylesheet" type="text/css" href="/css/style.css">
    <link rel="icon" href="/favicon.ico" type="image/x-icon"/>
    <link rel="shortcut icon" href="/favicon.ico" type="image/x-icon"/>
    <link rel="stylesheet" type="text/css" href="/css/templatestyle.css"/>
</head>
<body>
    <@header/>
    <div class="middle">
        <@sidebar/>
        <main>
            <#nested/>
        </main>
    </div>
    <@footer/>
</body>
</html>
</#macro>

<#macro userlink user nameOnly>
    <#if nameOnly>
        <a href="/user?handle=${user.handle}">${user.name}</a>
    <#else>
        <a href="/user?handle=${user.handle}" style="font-weight: bold; color:${user.color}; text-decoration: none">${user.name}</a>
    </#if>
</#macro>

<#macro textPostMaker post view>
    <#assign lines=post.text?split("\n")>
    <#if view>
        <#assign countSymbols=256>
    </#if>
    <#list lines as line>
        <#if view>
            <#if countSymbols!=0>
                <#if countSymbols<line?length>
                    <li>${line?substring(0, countSymbols-1)}...</li>
                    <#assign countSymbols=0>
                <#else>
                    <li>${line}</li>
                    <#assign countSymbols-=line?length>
                </#if>
            </#if>
        <#else>
            <li>${line}</li>
        </#if>
    </#list>
</#macro>

<#macro postMaker post view>
    <div class="post">
        <div class="title">${post.title}</div>
        <div class="body">
            <@textPostMaker post=post view=view/>
        </div>
        <div class="footer">
            <div class="left">
                <img src="img/voteup.png" title="Vote Up" alt="Vote Up"/>
                <span class="positive-score">+173</span>
                <img src="img/votedown.png" title="Vote Down" alt="Vote Down"/>
            </div>
            <div class="right">
                <img src="img/date_16x16.png" title="Publish Time" alt="Publish Time"/>
                2 days ago
                <img src="img/comments_16x16.png" title="Comments" alt="Comments"/>
                <a href="#">68</a>
            </div>
        </div>
    </div>
</#macro>

<#function countPosts user_id>
    <#assign answer=0/>
    <#list posts as i>
        <#if i.user_id==user_id>
            <#assign answer++/>
        </#if>
    </#list>
    <#return answer/>
</#function>

<#function findBy items key id>
    <#list items as item>
        <#if item[key]==id>
            <#assign index = item_index>
            <#return [items[index - 1]!, items[index]!, items[index + 1]!]/>
        </#if>
    </#list>
</#function>
