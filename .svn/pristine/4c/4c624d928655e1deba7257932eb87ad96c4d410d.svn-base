<#--
  @author       Li.hq 
  @contact      lihuiquan@email.szu.edu.cn
  @since         2014/8/7
  @description  网站页面模版片段
                              包括 @header @body @footer
                              具体调用参数请往下拉至相应位置
                              调用方式请自行 google 关键字 macro
                              有个忠告，别用baidu
  @dependency   language.ftl
-->

<#--
  load dependency
-->
<#include "/pages/common/language.ftl" >

<#--
  assign global navigator
  generally, "n" stands for "name",
             "l" stands for "link"
             and "c" for "children"
-->
<#if locale == "en">
  <#assign 
    navigator = {
      "index": {
        "n": L["index"],
        "l": "/"
      },
      "lab": {
        "n": L["lab-profile"],
        "l": "/lab",
        "c": [{
          "n": L["lab-intro"],
          "l": "/lab"
        }, {
          "n": L["lab-director"],
          "l": "/director"
        }<#--, {
          "n": L["lab-equipment"],
          "l": "/equipment"
        }-->, {
          "n": L["committee"],
          "l": "/committee"
        }, {
          "n": L["contact"],
          "l": "/contact"
        }]
      },
      "team": {
        "n": L["research-team"],
        "l": "/team",
        "c": [{
          "n": L["teachers"],
          "l": "/team"
        }, {
          "n": L["postdoctoral"],
          "l": "/postdoctoral"
        }, {
          "n": L["Doctor_of_Philosophy"],
          "l": "/phd"
        }, {
          "n": L["Master"],
          "l": "/masters"
        }, {
          "n": L["Visiting_Scholar"],
          "l": "/scholars"
        }, {
          "n": L["admin-assistant"],
          "l": "/assistant"
        }, {
          "n": L["Graduate"],
          "l": "/graduate"
        }]
      },
      "research": {
        "n": L["research"],
        "l": "/studies",
        "c": [{
          "n": L["research-area"],
          "l": "/studies"
        }, {
          "n": L["research-project"],
          "l": "/projects"
        }, {
          "n": L["research-findings"],
          "l": "/findings"
        }, {
          "n": L["research-platform"],
          "l": "/platform"
        }<#--, {
          "n": L["industrial-application"],
          "l": "#"
        }-->]
      },
      "exchanges": {
        "n": L["exchanges"],
        "l": "/exchanges",
        "k": "sb",
        "c": [{
          "n": L["academic-conference"],
          "l": "/exchanges"
        }, {
          "n": L["international-cooperation"],
          "l": "/cooperation"
        }]
      },
      "news": {
        "n": L["news-notice"],
        "l": "/news",
        "c": [{
          "n": L["news"],
          "l": "/news"
        }, {
          "n": L["notice"],
          "l": "/notice"
        }]
      },
      "job": {
        "n": L["job-vacancies"],
        "l": "/job",
        "c": [{
          "n": L["teachers"],
          "l": "/job"
        }, {
          "n": L["postdoctoral"],
          "l": "/job_postdoctoral"
        }, {
          "n": L["graduates"],
          "l": "/job_graduates"
        }, {
          "n": L["admin-assistant"],
          "l": "/job_assistant"
        }]
      },
      "search": {
        "n": L["search-result"],
        "l": "/search",
        "h": true,
        "c": [{
          "n": L["search-result"],
          "l": "/search"
        }]
      }
    }
  >
<#else>
  <#assign 
    navigator = {
      "index": {
        "n": L["index"],
        "l": "/"
      },
      "lab": {
        "n": L["lab-profile"],
        "l": "/lab",
        "c": [{
          "n": L["lab-intro"],
          "l": "/lab"
        }, {
          "n": L["lab-director"],
          "l": "/director"
        }<#--, {
          "n": L["lab-equipment"],
          "l": "/equipment"
        }-->, {
          "n": L["committee"],
          "l": "/committee"
        }, {
          "n": L["contact"],
          "l": "/contact"
        }]
      },
      "team": {
        "n": L["research-team"],
        "l": "/team",
        "c": [{
          "n": L["teachers"],
          "l": "/team"
        }, {
          "n": L["postdoctoral"],
          "l": "/postdoctoral"
        }, {
          "n": L["Doctor_of_Philosophy"],
          "l": "/phd"
        }, {
          "n": L["Master"],
          "l": "/masters"
        }, {
          "n": L["Visiting_Scholar"],
          "l": "/scholars"
        }, {
          "n": L["admin-assistant"],
          "l": "/assistant"
        }, {
          "n": L["Graduate"],
          "l": "/graduate"
        }]
      },
      "research": {
        "n": L["research"],
        "l": "/studies",
        "c": [{
          "n": L["research-area"],
          "l": "/studies"
        }, {
          "n": L["research-project"],
          "l": "/projects"
        }, {
          "n": L["research-findings"],
          "l": "/findings"
        }, {
          "n": L["research-platform"],
          "l": "/platform"
        }<#--, {
          "n": L["industrial-application"],
          "l": "#"
        }-->]
      },
      "exchanges": {
        "n": L["exchanges"],
        "l": "/exchanges",
        "c": [{
          "n": L["academic-conference"],
          "l": "/exchanges"
        }, {
          "n": L["come-to-visit"],
          "l": "/come_to_visit"
        }, {
          "n": L["visiting"],
          "l": "/visiting"
        }, {
          "n": L["international-cooperation"],
          "l": "/cooperation"
        }, {
          "n": L["foundation"],
          "l": "/foundation"
        }]
      },
      "regulations": {
        "n": L["regulations"],
        "l": "/regulations"
      },
      "news": {
        "n": L["news-notice"],
        "l": "/news",
        "c": [{
          "n": L["news"],
          "l": "/news"
        }, {
          "n": L["notice"],
          "l": "/notice"
        }]
      },
      "job": {
        "n": L["job-vacancies"],
        "l": "/job",
        "c": [{
          "n": L["teachers"],
          "l": "/job"
        }, {
          "n": L["postdoctoral"],
          "l": "/job_postdoctoral"
        }, {
          "n": L["graduates"],
          "l": "/job_graduates"
        }, {
          "n": L["admin-assistant"],
          "l": "/job_assistant"
        }]
      },
      "search": {
        "n": L["search-result"],
        "l": "/search",
        "h": true,
        "c": [{
          "n": L["search-result"],
          "l": "/search"
        }]
      }
    }
  >
</#if>

<#--
  @author       Li.hq 
  @contact      lihuiquan@email.szu.edu.cn
  @since         2014/8/7
  @description  网站页面模版 @header 片段
  @param        title       文档标题
  @param        nav_index   头部主导航栏的焦点ID，默认为index，
                                                    可选ID请参考navigator
  @param        sub_index   头部主导航栏子级菜单的偏移量，默认为0，
                                                     请参考navigator以获取相应的偏移量
  @param        charset     定义文档编码，默认为utf-8
  @param        lang        定义文档根语言，由后台Session设置，默认为zh
-->
<#macro header title="别忘记定义title啦" nav_index="index" sub_index=0 keyword="" charset="utf-8" lang="${locale}">
  <!DOCTYPE html>
  <html lang="${lang}">
    <head>
      <meta charset="${charset}">
      <meta name="renderer" content="webkit">
      <meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1" >
      <title>${title}</title>
      <link href="/public/assets/common/style/base.css" rel="stylesheet" />
      <link href="/public/assets/common/style/tool.css" rel="stylesheet" />
      
      <@language_script />
      
      <script src="/public/assets/common/scripts/jquery.js"></script>
      <script src="/public/assets/common/scripts/common.js"></script>
      <script src="/public/assets/common/scripts/tool.js"></script>
      <script src="/public/assets/common/scripts/header.js"></script>
    
      <#nested>
      
    </head>
    
    <body>
      <header>
        <div class="top">
          <div class="wrap">
            <a class="logo" href="/" title="深圳大学空间信息智能感知与服务深圳市重点实验室首页">深圳大学空间信息智能感知与服务深圳市重点实验室</a>
            <div class="settings">
              <div id="language" class="ib">
                <label class="ib"><input id="locale_lang_zh" class="ib" type="radio" name="locale_lang" value="zh" <#if locale == "zh">checked="checked"</#if>/>中文</label>
                <label class="ib"><input id="locale_lang_en" class="ib" type="radio" name="locale_lang" value="en" <#if locale == "en">checked="checked"</#if>/>English</label>
              </div>
              <div id="search" class="ib">
                <input class="ib text" name="search_key" placeholder='${L["any-keywords"]}' value="${keyword}"><a
                  class="ib btn-search" title='${L["search"]}'>${L["search"]}</a>
              </div>
            </div>
          </div>
        </div>
        
        <nav id="navigator">
          <div class="wrap">
            <ul class="main clearfix">
            
              <#assign keys = navigator?keys>
              <#list keys as key>
              
                <#if !(navigator[key].h?? && navigator[key].h)>
                  <li class="main-item<#if navigator[key].k??> ${navigator[key].k}</#if><#if nav_index?? && nav_index == key> on</#if>">
                    <a href=${navigator[key]["l"]} class="item-title"><span>${navigator[key]["n"]}</span></a>
                    
                    <#if (navigator[key].c?exists && navigator[key].c?size>0)>
                    <ul class="sub">
                      <#list navigator[key]["c"] as sub_nav>
                      <li<#--<#if nav_index?? && nav_index == key && sub_nav_index == sub_index> class="on"</#if>-->>
                        <a href="${sub_nav.l}"><i>.</i>${sub_nav.n}</a>
                      </#list>
                    </ul>
                    </#if>
                </#if>
                
              </#list>
            </ul>
          </div>
        </nav>
      </header>
</#macro>


<#--
  @author       Li.hq 
  @contact      lihuiquan@email.szu.edu.cn
  @since         2014/8/7
  @description  网站页面模版 @body 片段
  @param        has_nav     是否有左导航栏，默认为false
  @param        nav_index   头部主导航栏的焦点ID，默认为index，
                                                    可选ID请参考navigator
  @param        sub_index   头部主导航栏子级菜单的偏移量，默认为0，
                                                    请参考navigator以获取相应的偏移量
-->
<#macro body has_nav = false nav_index = "index" sub_index = 0 backurl = "">
  <section class="main">
    <div class="wrap">
      <#if !has_nav>
        <#nested>
      <#else>
        <#assign left_nav = navigator[nav_index]>
        <div class="left-nav" id="left_nav">
          <div class="nav-name">${left_nav.n}</div>
          <ul class="nav-list">
            <#if (left_nav.c?exists && left_nav.c?size>0)>
	            <#list left_nav.c as sub_nav>
	              <li<#if sub_nav_index == sub_index> class="on"</#if> nav_name="${sub_nav.l?substring(1)}">
	                <a href="${sub_nav.l}">&nbsp;&nbsp;·&nbsp;&nbsp;${sub_nav.n}<i></i></a>
	            </#list>
            <#else>
              <li class="on" nav_name="${left_nav.l?substring(1)}">
                <a href="${left_nav.l}">&nbsp;&nbsp;·&nbsp;&nbsp;${left_nav.n}<i></i></a>
            </#if>
          </ul>
        </div>
        <div class="content">
          <div class="location">
            <i></i>
            <span><a class="link link-gray" href="/">${L["index"]}</a>
              </span>
              <#assign has_child = (left_nav.c?exists && left_nav.c?size>0)>
              <#if has_child><span class="s">&gt;</span><span><a
                class="link link-gray" href="${navigator[nav_index].l}">
                  ${navigator[nav_index].n}</a>
                </span><span class="s">&gt;</span><span>
                  ${left_nav.c[sub_index].n}</span>
              <#else><span class="s">&gt;</span><span>
                  ${navigator[nav_index].n}
                </span>
              </#if>
          </div>
          <div class="content-title clearfix">
            <p>
              <#if has_child>
                ${left_nav.c[sub_index].n}
              <#else>
                ${navigator[nav_index].n}
              </#if>
            </p>
            <#if (backurl?length > 0)>
            <a href="${backurl}">${L.S_Go_Back}</a>
            </#if>
          </div>
          
          <div class="content-main">
            <#nested>
          </div>
        </div>
      </#if>
      
    </div>
  </section>
</#macro>

<#--
  @author       Li.hq 
  @contact      lihuiquan@email.szu.edu.cn
  @since         2014/8/7
  @description  网站页面模版 @footer 片段
  @param        dummy       就是没有的意思，我喜欢这片段
-->
<#macro footer>
  <footer>
    <div class="wrap">
      <#--
              万恶的需求
              实验室网站修改意见-wu 2015/9/9
        `三、  相关网站挂接`
              考虑微博、重要的网站以及微信公众号等的重要性，考虑将来的可扩展性，采用一种松散方式对这些链接进行挂接，如下：在主页版权的上方加入一个滚动的链接窗口（如下图），在这个窗口里面可以挂接微博、重要的网站以及微信公众号等的地址或二维码。这样在数据库中加入“相关网站”表，表中包括一个类别（网站、微信、微博等）、一个图标以及文字的图片（规定尺寸）、链接的名称和链接的地址或二维码图片(附件1)。后台系统中加入对这个表的维护（增加、删除和修改），前台显示，如果多了自动滚动。如果是网站等，自动跳转；如果是微信等的二维码，要打开一个图片窗口，显示可以直接扫的二维码。
      -->
            
      <#--
              类型对应
              便于区别不同的链接类型
      -->
      <#assign linkTypesMapping = {
        "微信": "weixin",
        "微博": "weibo",
        "网站": "website"
      }>
      
      <div class="links clearfix">
        <@times times=1>
          <#list linklist as link>
            <#assign linkType = linkTypesMapping[link.type]>
            <a class="link ${linkType}" title="${link.name}" data-link="${link.url!link.picurl!''}">
              <img alt="${link.name}" src="${link.icon}">
            </a>
          </#list>
        </@times>
      </div>
      <p>${L.root}
      <p>${L.copyright}
    </div>
  </footer>
</#macro>

<#--
  @author       Li.hq 
  @contact      lihuiquan@email.szu.edu.cn
  @since         2015/9/9
  @description  测试专用！简单而有用的模版，用于重复渲染
                              用于列表式的渲染测试
  @param        times       重复渲染的次数
-->
<#macro times times=1>
  <#list 1..times as one>
    <#nested>
  </#list>
</#macro>

