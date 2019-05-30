
<#include "/pages/common/common.ftl" >

<#assign nav_index = "${nav_index}" sub_index=sub_index backurl=backurl!"">

<#macro article_details>
  <#assign title = article.title>
  
  <@header title='${title} | ${L["site-title"]}' nav_index=nav_index sub_index=sub_index>
  <link href="/public/assets/index/style/index.css" rel="stylesheet" />
  
  <script src="/public/assets/common/scripts/header.js"></script>
  <script src="/public/assets/index/scripts/index.js"></script>
  </@header>
  
  <@body has_nav=true nav_index=nav_index sub_index=sub_index backurl=backurl>
    <article>
      <div class="title">
        <h3>${article.title}</h3>
        <p><span>${L["update"]}${article.time}</span><#if article.pageview??><span>${L["view"]}${article.pageview}</span></#if>
      </div>
      <div class="body">${article.content}</div>
      
      <#--
                万恶的需求
                实验室网站修改意见-wu 2015/9/9
      -->
      <#if ((article.attachmentsname?length>0) && (article.attachments?length>0))>
        <#assign filenames = article.attachmentsname?split('||')>
        <#assign filepaths = article.attachments?split(',')>
        <div class="file-list">
          <div class="list-head">附件下载</div>
          <ul>
            <@times times=1>
              <#list filenames as file>
                <li>
                  <p>附件${file_index + 1}.<a href="${filepaths[file_index]}" target="_blank">${file}</a></p>
              </#list>
            </@times>
          </ul>
        </div>
      </#if>
    </article>
  </@body>
  
  <@footer />
</#macro>

<#macro project_details>
  <#assign title = project.name>
  
  <@header title='${title} | ${L["site-title"]}' nav_index=nav_index sub_index=sub_index>
  <link href="/public/assets/index/style/index.css" rel="stylesheet" />
  
  <script src="/public/assets/common/scripts/header.js"></script>
  <script src="/public/assets/index/scripts/index.js"></script>
  </@header>
  
  <@body has_nav=true nav_index=nav_index sub_index=sub_index backurl=backurl>
    <article>
        <div class="name">
          <h3>${project.name}</h3>
        </div>
        <div class="profile">
          <table>
            <tr>
              <th>${L["F_Leader"]}
              <td>${project.principal}
            <tr>
              <th>${L["F_Project_Type"]}
              <td>${project.category}
            <tr>
              <th>${L["F_Year"]}
              <td>${project.year}
            <tr>
              <th>${L["F_Sources_of_Funding"]}
              <td>${project.fundsource}
            <tr>
              <th>${L["F_Research_Period"]}
              <td>${project.restimebegin} ${L["F_to"]} ${project.restimeend}
            <tr>
              <th>${L["F_Contract_Number"]}
              <td>${project.contractno}
          </table>
        </div>
        <div class="body">${project.digest}</div>
        <#if (proAchList?length > 0)>
          <div class="addition-list">
            <h4>相关成果</h4>
            <table class="nested">
              <tr>
                <th class="num">序号</th>
                <th class="short">类型</th>
                <th>名称</th>
              </tr>
              <#list proAchList as item>
                <tr>
                  <td class="num">${item_index +1}</td>
                  <td>${item.achtype}</td>
                  <td><a href="${item.achurl}" target="_blank">${item.achname}</a></td>
                </tr>
              </#list>
            </table>
          </div>
        </#if>
    </article>
  </@body>
  
  <@footer />
</#macro>

<#macro finding_article name="" data={} digest="" cover="">
  <article>
    <div class="name">
      <h3>${name}</h3>
    </div>
    <div class="profile">
      <table>
        <#list data as row>
          <tr>
            <th>${row.n}
            <td><#if row.t??>${row.t}<#else>${row.v}</#if>
        </#list>
      </table>
      <#if (cover?length > 0)>
        <div class="cover"><img src="${cover}"></div>
      </#if>
    </div>
    <#if (digest?length > 0)>
    <div class="body">${digest}</div>
    </#if>
  </article>
</#macro>

<#macro finding_details>
  <#assign title = finding.name>
  
  <@header title='${title} | ${L["site-title"]}' nav_index=nav_index sub_index=sub_index>
  <link href="/public/assets/index/style/index.css" rel="stylesheet" />
  
  <script src="/public/assets/common/scripts/header.js"></script>
  <script src="/public/assets/index/scripts/index.js"></script>
  </@header>
  
  <@body has_nav=true nav_index=nav_index sub_index=sub_index backurl=backurl>
    <#if finding_type == "0">
      <#assign finding_data = [{
        "n": "${L.F_Journal_Name}",
        "v": "${finding.periodical}"
      }, {
        "n": "${L.F_All_Authors}",
        "v": "${finding.authors}"
      }, {
        "n": "${L.F_Publish_Year}",
        "v": "${finding.publishyear}"
      }, {
        "n": "${L.F_Issue}",
        "v": "${finding.reelnumber}"
      }, {
        "n": "${L.F_ISSN}",
        "v": "${finding.issue}"
      }, {
        "n": "${L.F_Page}",
        "v": "${finding.pagination}"
      }, {
        "n": "${L.F_View_Papers}",
        "t": "<a class=btn-pdf target=_blank href=\"" + (finding.pdfurl!"javascript:;") + "\"></a>",
        "v": "${finding.pdfurl!\"\"}"
      }]>
      
    <#elseif finding_type == "1">
      <#assign finding_data = [{
        "n": "${L.F_Conference_Name}",
        "v": "${finding.periodical}"
      }, {
        "n": "${L.F_All_Authors}",
        "v": "${finding.authors}"
      }, {
        "n": "${L.F_Publish_Year}",
        "v": "${finding.publishyear}"
      }, {
        "n": "${L.F_Conference_Address}",
        "v": "${finding.address}"
      },{
        "n": "${L.F_Page}",
        "v": "${finding.pagination}"
      }, {
        "n": "${L.F_View_Papers}",
        "t": "<a class=btn-pdf target=_blank href=\"" + (finding.pdfurl!"javascript:;") + "\"></a>",
        "v": "${finding.pdfurl!\"\"}"
      }]>
      
    <#elseif finding_type == "2">
      <#assign finding_data = [{
        "n": "${L.F_All_Authors}",
        "v": "${finding.authors}"
      }, {
        "n": "${L.F_Instructors}",
        "v": "${finding.tutor}"
      }, {
        "n": "${L.F_Publish_Year}",
        "v": "${finding.publishyear}"
      }, {
        "n": "${L.F_Department}",
        "v": "${finding.unit}"
      },{
        "n": "${L.F_Page}",
        "v": "${finding.pags}"
      }]>
      
    <#elseif finding_type == "3">
      <#assign finding_data = [{
        "n": "${L.F_All_Authors}",
        "v": "${finding.authors}"
      }, {
        "n": "${L.F_Award_Name}",
        "v": "${finding.awardname}"
      }, {
        "n": "${L.F_Award_Category}",
        "v": "${finding.type}"
      }, {
        "n": "${L.F_Award_Rating}",
        "v": "${finding.level}"
      },{
        "n": "${L.F_Granting_Units}",
        "v": "${finding.certigier}"
      },{
        "n": "${L.F_Year_Award}",
        "v": "${finding.year}"
      }]>
      
    <#elseif finding_type == "4">
      <#assign finding_data = [{
        "n": "${L.F_All_Authors}",
        "v": "${finding.authors}"
      }, {
        "n": "${L.F_Patent_Name}",
        "v": "${finding.patentname}"
      }, {
        "n": "${L.F_Patent_Category}",
        "v": "${finding.type}"
      }, {
        "n": "${L.F_Granting_Units}",
        "v": "${finding.certigier}"
      },{
        "n": "${L.F_Year_Award}",
        "v": "${finding.year}"
      }]>
    
    <#elseif finding_type == "5">
      <#assign finding_data = [{
        "n": "${L.F_All_Authors}",
        "v": "${finding.authors}"
      }, {
        "n": "${L.F_Publisher}",
        "v": "${finding.publish}"
      }, {
        "n": "${L.F_Publish_Year}",
        "v": "${finding.publishyear}"
      }, {
        "n": "${L.F_Publish_Address}",
        "v": "${finding.publishaddr}"
      },{
        "n": "${L.F_ISBN_NO}",
        "v": "${finding.isbn}"
      },{
        "n": "${L.F_Page}",
        "v": "${finding.pagination}"
      }]>
    </#if>
    
    <@finding_article name="${finding.name}" data = finding_data digest = finding.digest!"" cover=finding.pic!"" />
    
  </@body>
  
  <@footer />
</#macro>

<#macro exchange_details>
  <#assign title = exchange.name>
  
  <@header title='${title} | ${L["site-title"]}' nav_index=nav_index sub_index=sub_index>
  <link href="/public/assets/index/style/index.css" rel="stylesheet" />
  
  <script src="/public/assets/common/scripts/header.js"></script>
  <script src="/public/assets/index/scripts/index.js"></script>
  </@header>
  
  <@body has_nav=true nav_index=nav_index sub_index=sub_index backurl=backurl>
    <#assign exchange_data = []>
    
    <@finding_article name="${exchange.name}" data = exchange_data digest = exchange.intro!"" />
    
  </@body>
  
  <@footer />
</#macro>

<#macro foundation_details>
  <#assign title = foundation.title>
  
  <@header title='${title} | ${L["site-title"]}' nav_index=nav_index sub_index=sub_index>
  <link href="/public/assets/index/style/index.css" rel="stylesheet" />
  
  <script src="/public/assets/common/scripts/header.js"></script>
  <script src="/public/assets/index/scripts/index.js"></script>
  </@header>
  
  <@body has_nav=true nav_index=nav_index sub_index=sub_index backurl=backurl>
    
    <#if (achlist?? && achlist?size>0)>
      <#assign tmpl = "<table class=inner><thead><tr><th class=center>序号<th>名称<th>类型<tbody>">
      <#list achlist as list>
        <#assign tmpl = tmpl + "<tr><td class=center>${list.num}<td class=name><a href=\"${list.url}\">${list.name!\"\"}</a><td>${list.type}">
      </#list>
      <#assign tmpl = tmpl + "</table>">
    <#else>
      <#assign tmpl = "无">
    </#if>
    
    <#assign foundation_data = [{
      "n": "${L.F_Name}",
      "v": "${foundation.name}"
    }, {
      "n": "${L.F_Department}",
      "v": "${foundation.unit}"
    }, {
      "n": "${L.F_Year}",
      "v": "${foundation.year}"
    }, {
      "n": "${L.F_Keywords}",
      "v": "${foundation.keyword}"
    }]>
    
    <@finding_article name="${foundation.title}" data = foundation_data digest = foundation.digest!"" />
    
  </@body>
  
  <@footer />
</#macro>

<#macro regulation_details>
  <#assign title = regulation.title>
  
  <@header title='${title} | ${L["site-title"]}' nav_index=nav_index sub_index=sub_index>
  <link href="/public/assets/index/style/index.css" rel="stylesheet" />
  
  <script src="/public/assets/common/scripts/header.js"></script>
  <script src="/public/assets/index/scripts/index.js"></script>
  </@header>
  
  <@body has_nav=true nav_index=nav_index sub_index=sub_index backurl=backurl>
    <article>
      <div class="title">
        <h3>${regulation.title}</h3>
        <p><span>${L["update"]}${regulation.time}</span>
      </div>
      <div class="body">
        ${regulation.content}
        <div class="download">
          ${L.F_Download}<a href="${regulation.pdf}" target="_blank">${regulation.pdfname}</a>
        </div>
      </div>
    </article>
  </@body>
  
  <@footer />
</#macro>

<#macro user_details>
  <#assign title = user.name + L["T_User_Profile"]>
  
  <@header title='${title} | ${L["site-title"]}' nav_index=nav_index sub_index=sub_index>
  <link href="/public/assets/index/style/index.css" rel="stylesheet" />
  
  <script src="/public/assets/common/scripts/header.js"></script>
  <script src="/public/assets/index/scripts/index.js"></script>
  </@header>
  
  <@body has_nav=true nav_index=nav_index sub_index=sub_index backurl=backurl>
    <article>
      <div class="desc clearfix">
        <div class="avatar">
          <img src="${user.photo!""}" title="${user.name}" alt="${user.name}">
        </div>
        <div class="info">
          <p>${L["F_Name"]}${user.name!""}
          <#if user.degree == "教师">
            <p>${L["F_Title"]}${user.title!""}
          </#if>
          
          <#if user.degree != "教师" && user.degree != "博士后" && user.degree != "行政助理">
            <p>${L["F_Major"]}${user.profession!""}
          </#if>
          <#if user.degree == "行政助理">
            <p>${L["F_Tel"]}${user.phone!""}
            <p>${L["F_Fax"]}${user.fax!""}
          <#else>
          <p>${L["F_Research"]}${user.researcharea!""}
          </#if>
          <p>${L["F_Email"]}${user.email!""}
        </div>
      </div>
      
      <@fieldset title=L["T_User_Introduction"] data=user.introduce!"" name="intro" />
      
      <@fieldset title="${L.T_User_Projects}" data=[] + user.projectlist
        settings = [{
          "key": "row_index",
          "name": "${L.number}"
        }, {
          "key": "compose",
          "link": "url",
          "name": "${L[\"project-name\"]}"
        }]/>
        
      <@fieldset title="${L.T_Monographs}" data=[] + user.treatiselist
        settings = [{
          "key": "row_index",
          "name": "${L.number}"
        }, {
          "key": "compose",
          "link": "url",
          "name": "${L.T_Thesis_Title}"
        }]/>
        
      <@fieldset title="${L.T_Published_Papers}" data=[] + user.perartlist + user.conartlist
        settings = [{
          "key": "row_index",
          "name": "${L.number}"
        }, {
          "key": "compose",
          "link": "url",
          "name": "${L.T_Thesis_Title}"
        }]/>
      
      <#if locale != "en">
      <@fieldset title="${L.T_Rewarded}" data=[] + user.awardlist
        settings = [{
          "key": "row_index",
          "name": "${L.number}"
        }, {
          "key": "compose",
          "link": "url",
          "name": "${L.name}"
        }]/>
        
      <@fieldset title="${L.T_Patents}" data=[] + user.patentlist
        settings = [{
          "key": "row_index",
          "name": "${L.number}"
        }, {
          "key": "compose",
          "link": "url",
          "name": "${L.name}"
        }]/>
      </#if>
        
    </article>
  </@body>
  
  <@footer />
</#macro>

<#macro _404>
  <#assign title = "404 " + L["not-found"]>
  
  <@header title='${title} | ${L["site-title"]}' nav_index=nav_index sub_index=sub_index>
  <link href="/public/assets/index/style/index.css" rel="stylesheet" />
  
  <script src="/public/assets/common/scripts/header.js"></script>
  <script src="/public/assets/index/scripts/index.js"></script>
  </@header>
  
  <@body has_nav=true nav_index=nav_index sub_index=sub_index>
    <article>
        <div class="body empty">${L["not-found-tips"]}</div>
    </article>
  </@body>
  
  <@footer />
</#macro>

<#macro fieldset data title="未定义哦" name="" settings="">
  <#if ((data?is_indexable && (data?size > 0)) || (data?is_string && (data?length > 0)))>
    <div class="p<#if (name?length>0)> ${name}</#if>">
      <div class="h">${title}</div>
      <div class="b">
        <#if (data?is_indexable && settings?is_indexable)>
          <table class="light-table">
            <#--<thead>
              <tr>
                <#list settings as set>
                  <th class="<#if set.key == "row_index">row-index</#if>">${set.name}
                </#list>-->
              
            <tbody>
              <#list data as row>
                <tr>
                  <#list settings as set>
                    <#if set.key == "row_index">
                      <td class="row-index">
                        ${row_index + 1}
                    <#else>
                      <td>
                        <#if set.link??>
                          <a href="${row[set.link]!"javascript:;"}" target="_blank" title="${row[set.key]!""}">${row[set.key]!""}</a>
                        <#else>
                          ${row[set.key]!""}
                        </#if>
                    </#if>
                  </#list>
              </#list>
          </table>
        <#elseif (data?is_string)>
          ${data}
        </#if>
      </div>
    </div>
  </#if>
</#macro>


<#if (article??)>

  <#--文章类详细页-->
  <@article_details />
  
<#elseif (project??)>

  <#--研究项目详细页-->
  <@project_details />
  
<#elseif (finding??)>

  <#--研究成果详细页-->
  <@finding_details />
  
<#elseif (exchange??)>

  <#--学术交流详细页-->
  <#assign article = {
    "title": exchange.name,
    "time": exchange.time!"",
    "content": exchange.intro,
    "pageview": exchange.pageview
  }>
  <@article_details />
  
<#elseif (regulation??)>

  <#--规章制度详细页-->
  <@regulation_details />
  
<#elseif (foundation??)>

  <#--开放基金详细页-->
  <@foundation_details />
  
<#elseif (user??)>

  <#--用户详细页-->
  <@user_details />
  
<#else>

  <#--404页面-->
  <@_404 />
  
</#if>
