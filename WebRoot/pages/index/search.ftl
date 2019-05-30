
<#include "/pages/common/common.ftl" >

<#assign nav_index = "search" sub_index=0>

<@header title='${L.T_Search_results_on_the_$keyword?replace("$keyword", q)} | ${L["site-title"]}' keyword="${q}" nav_index=nav_index sub_index=sub_index>
<link href="/public/assets/index/style/index.css" rel="stylesheet" />

<script type="text/javascript">
  var KEYWORD = "${q?web_safe}";
</script>
<script src="/public/assets/common/scripts/jquery.simplePagination.js"></script>
<script src="/public/assets/common/scripts/header.js"></script>
<script src="/public/assets/index/scripts/index.js"></script>
</@header>

<@body has_nav=true nav_index=nav_index sub_index=sub_index>
  <div id="search_list" class="list-wrap"></div>
</@body>

<@footer />
  
