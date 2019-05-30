(function() {
  var id = getQueryString("id") == null ? "" : getQueryString("id");
  var relevance = [];
  var relevancename = [];
  var AUTHORS = "";
  var relnum = 0;
  var html = ""; // 作者列表
  $(function() {
    initSidebar(7, 2);

    // 关联人员
    $("#add_relevance").live('click',function() {
      relnum++;
      var str = '<div id="relevance' + relnum
          + '"><select class="relevance" id="selrel' + relnum
          + '" onchange="nosysrelshow(' + relnum + ')">' + html
          + '</select>';
      str += '<input type="text" class="input-normal selrel' + relnum
          + '"  style="display:none" />';
      str += '<input type="button" class="btn normal-btn" value="移除" onclick="reldel('
          + relnum + ')"/></div>';
      $("#relevance_label").append(str);
    });

    $
        .when(getdetail())
        .then(
            function() {
              var authorList = new List;

              if (!!AUTHORS) {
                var tmp, item, arr = [];
                AUTHORS = AUTHORS.split(';');
                $.each(AUTHORS, function(i, v) {
                  item = {};
                  tmp = v.split(',');
                  item.name = tmp[0];
                  item.ename = tmp[1];
                  item.dept = tmp[2];
                  item.edept = tmp[3];
                  item.iscorrespondor = tmp[4];
                  item.isowner = tmp[5];
                  arr.push(item);
                });

                AUTHORS = arr;
              }

              authorList
                  .load(
                      AUTHORS.length ? AUTHORS : '/list/getResUser',
                      function(data) {

                        var $wrapper = $('<div>');

                        this
                            .render(
                                $wrapper,
                                [
                                    {
                                      key : 'row_select',
                                      name : ''
                                    },
                                    {
                                      key : 'row_index',
                                      name : '序号'
                                    },
                                    {
                                    	tdTmpl : '<input type=text class="text w60" name="field_name" value="${name}" title="${name}"  placeholder="中文名">'+
									      '<input type=text class="text w60" name="field_ename" value="${ename}" title="${ename}" placeholder="in english">',
									    name : '姓名'
                                    },
                                    {
                                    	tdTmpl : '<input type=text class="text w120" name="field_dept" value="${dept}" title="${dept}" placeholder="单位名称">'+
								 		  '<input type=text class="text w120" name="field_edept" value="${edept}" title="${edept}" placeholder="fill in english">',
								 		name : '单位'
                                    },
                                    {
                                      tdTmpl : '<input type=checkbox name=iscorrespondor ${iscorrespondor?equal("1", "checked=checked", "")}>',
                                      name : '通讯作者'
                                    },
                                    {
                                      tdTmpl : '<input type=checkbox name=isowner ${isowner?equal("1", "checked=checked", "")}>',
                                      name : '本人'
                                    }]);

                        $wrapper.prependTo($('#author_list'));

                      });
              // 保存数据
              $("#addvalue").click(function() {
                save(authorList);
              });
              // 重置
              $("#resetvalue").click(function() {
                $("#periodical").val("");
                $("#eperiodical").val("");
                $("#thesis_name").val("");
                $("#ethesis_name").val("");
                $("#publishyear").val("");
                $("#issue").val("");
                $("#issn").val("");
                $("#pagination").val("");
                $("#digest").val("");
                $("#edigest").val("");

                $("#mstatus").val("已发表");
                $("#include").val("SCI");
                $("#pdfurl").val("");
                $("#reelnumber").val("");
                authorList.redraw();

                // 移除所有人员
                var fo = $("select[class='relevance']");
                $(fo).each(function() {
                  $(this).parent().remove();

                });
              });
            });

    //关联项目
    $("#add_project").click(function(){
    	getList();
    });
	/***获取项目列表***/
	function getList(){
		var html = "";
		$.post("/list/getAllProjectList",'tkey=periodical_article&achid='+id,function(data){
			
			if(data.tbody.length){
				for(var i=0; i<data.tbody.length; i++){
					html += '<tr class="T-even-tr">';
					if(data.tbody[i].isown=="1"){
						console.log(i);
						html +='<td class="T-td-center"><div class="T-td-wrap T-no-break">'
							 +'<input type="checkbox" class="td-check" checked="checked" name="achid" value="'+data.tbody[i].id+'"/>'
							 +'<input type="hidden" value="'+data.tbody[i].name+'"/>'+(i+1)+'</div></td>';
					}else{
						html +='<td class="T-td-center"><div class="T-td-wrap T-no-break">'
							 +'<input type="checkbox" class="td-check" name="achid" value="'+data.tbody[i].id+'"/>'
							 +'<input type="hidden" value="'+data.tbody[i].name+'"/>'+(i+1)+'</div></td>';
					}
						   
						html +='<td class="T-td-center"><div class="T-td-wrap T-no-break mw400" title="'+data.tbody[i].name+'">'+data.tbody[i].name+'</div></td>'
						     + '</tr>';
				}
			}else{
				html += '<tr class="T-even-tr"><td class="T-td-center" colspan="3"><div class="T-td-wrap T-no-break" style="text-align: center;">暂无项目</div></td>';
			}
			dialog( html );
			
		},'json');
	}
	/****dialog弹窗****/
	function dialog(content){
		var tableStr = '<div style="margin:5px 0px;"><input type="text" placeholder="项目名称" class="normal-input" id="ach_name"/>'
			 + '<input type="button" class="btn normal-btn" id="search_btn" value="搜索"></div>'
			 + '<table class="T-table">'
			 + '<thead><tr><th style="width: 80px;text-align: left;"><div class="T-th-wrap"><input type="checkbox" class="th-check" id="selectall">序号</div></th>'
			 + '<th><div class="T-th-wrap">项目名称</div></th>'
			 + '</tr></thead><tbody class="show-tbody">';

		tableStr += content;
		tableStr += '</tbody></table>';

		$.miniDialog({
			id: 'achieve_select_dialog',
			// 预设的Dialog的class名
			className: 'util-dialog role-select-dialog',
			title: '关联项目',
			width: 550,
			top: 50,
			content: tableStr,
			destroy: false,
			btns: ['<input id="dialogbt" class="btn ${className}" type="button" value="${value}">', {
				value: '提交',
				className: 'normal-btn btn-ok',
				callBack: function(h){
					var str = "", ii=1, htm="";
					htm += '<table><thead>'
						 +	'<tr><th style="width:50px;">序号</th><th>项目名称</th>'
						 +	'</thead><tbody>';
					$("input[name='achid']").each(function(){
						if($(this).attr("checked")){
							htm += '<tr><td class="T-td-center">' + ii + '</td>'
							 	+	'<td>' + $(this).next().val() + '</td>';
							str  += $(this).val()+";";
							
							ii++;
						}
					});
					if(str!=""){
						str = str.substring(0,str.length-1);
					}
					htm += '</tbody></table>';
					$("#prostr").val(str);
					$("#project_label").html(htm);

					return false;
				}
			}, {
				value: '取消',
				className: 'normal-btn  btn-cancel',
				callBack: function(){
					return false;
				}
			}],
			afterOpen: function(){
				//dialog里搜索
				$("#search_btn").live('click',function(){
					var html = "";
					$.post("/list/getAllProjectList",'tkey=periodical_article&achid='+id+"&rename="+$("#ach_name").val(),function(data){
						
						if(data.tbody.length){
							for(var i=0; i<data.tbody.length; i++){
								html += '<tr class="T-even-tr">';
								if(data.tbody[i].isown=="1"){
									html +='<td class="T-td-center"><div class="T-td-wrap T-no-break">'
										 +'<input type="checkbox" class="td-check" checked="checked" name="achid" value="'+data.tbody[i].id+'"/>'
										 +'<input type="hidden" value="'+data.tbody[i].name+'"/>'+(i+1)+'</div></td>';
								}else{
									html +='<td class="T-td-center"><div class="T-td-wrap T-no-break">'
										 +'<input type="checkbox" class="td-check" name="achid" value="'+data.tbody[i].id+'"/>'
										 +'<input type="hidden" value="'+data.tbody[i].name+'"/>'+(i+1)+'</div></td>';
								}
									   
									html +='<td class="T-td-center"><div class="T-td-wrap T-no-break mw400" title="'+data.tbody[i].name+'">'+data.tbody[i].name+'</div></td>'
									     + '</tr>';
							}
						}else{
							html += '<tr class="T-even-tr"><td class="T-td-center" colspan="3"><div class="T-td-wrap T-no-break" style="text-align: center;">暂无成果</div></td>';
						}
						$(".show-tbody").html(html);
					},'json');
					
				});
			}
		});
	}

	//全选
	$("#selectall").live('click',function(){
        var stat = this.checked;
        $("input[name='achid']").each(function(){
            this.checked = stat;
        });
	});
	$("#select").live('click',function(){
        $("input[name='achid']").each(function(){
            this.checked = true;
        });
        $("#selectall").attr("checked",'true');
	});

	$("#reverse").live('click',function(){
    	$("input[name='achid']").each(function(){
		   $(this).attr("checked",!this.checked);
		});
    	check();
	});
	//选子checkbox检查全选状态
	$("input[name='achid']").live("click",function(){
		check();
	});
	//判断子checkbox是否全部选中
	function check(){
		var ischeckAll = true;
		$("input[name='achid']").each(function(){
			if(!$(this).attr("checked")){
				ischeckAll = false;
				return false;
			}
		});
		$("#selectall").attr("checked",ischeckAll);
	}
  });
	/***获取已关联项目***/
	function getPro(){
		var html = "", str = "";
		$.post("/list/getAllProjectList",'tkey=periodical_article&achid='+id,function(data){
			html += '<table><thead>'
				 +	'<tr><th style="width:50px;">序号</th><th>项目名称</th>'
				 +	'</thead><tbody>';
			if(data.tbody.length){
				var ii = 1;
				for(var i=0; i<data.tbody.length; i++){
					if(data.tbody[i].isown){
						html += '<tr><td class="T-td-center">' + ii + '</td>'
							 +	'<td>' + data.tbody[i].name + '</td>';
						str  += data.tbody[i].id+";";
						ii++;
					}
				}
				if(ii==1){
					html += '<tr><td colspan="2" class="T-td-center">无关联项目</td></tr>';
				}
			}else{
				html += '<tr><td colspan="2" class="T-td-center">无关联项目</td></tr>';
			}

			if(str!=""){
				str = str.substring(0,str.length-1);
			}
			html += '</tbody></table>';
			$("#prostr").val(str);
			$("#project_label").html(html);
			
		},'json');
	}
  author_list();
  // 移除作者
  del = function(id) {
    var i = id.match(/\d+/);
    $("div").remove("#authors" + i);
  };

  // 移除关联人员
  reldel = function(id) {
    $("div").remove("#relevance" + id);
  };
  // 判断是否本系统人员
  nosysrelshow = function(id) {
    if ($("#selrel" + id).find("option:selected").val() == "-1") {
      $(".selrel" + id).show();
    } else {
      $(".selrel" + id).hide();
    }
  };

  function getdetail() {
    if (id === "") {
      return false;
    }
    getPro();
    return $
        .post(
            "/detailed/getPerArtById",
            "id=" + id,
            function(data) {
              if (data) {
                AUTHORS = data.authors;
                $("#periodical").val(data.periodical);
                $("#eperiodical").val(data.eperiodical);
                $("#thesis_name").val(data.name);
                $("#ethesis_name").val(data.ename);

                $("#mstatus").val(data.status);
                $("#include").val(data.include);
                $("#pdfurl").val(data.pdfurl);

                $("#publishyear").val(data.publishyear);
                $("#issue").val(data.issue);
                $("#issn").val(data.issn);
                $("#pagination").val(data.pagination);
                $("#digest").val(data.digest);
                $("#edigest").val(data.edigest);
                $("#reelnumber").val(data.reelnumber);
                // 保存关联人员id
                if (data.relevance != "" && data.relevance != null) {
                  relevance = data.relevance.split(';');
                  relevancename = data.relevancename.split(';');
                  if (relevance.length >= 1) {
                    for ( var i = 0, len = relevance.length; i < len; i++) {
                      relnum++;
                      var str = '<div id="relevance' + relnum
                          + '"><select class="relevance" id="selrel' + relnum
                          + '" onchange="nosysrelshow(' + relnum + ')" value="'
                          + relevance[i] + '">' + html + '</select>';
                      if (relevance[i] != -1) {
                        str += '<input type="text" class="input-normal selrel'
                            + relnum + '"  style="display:none" />';
                      } else {
                        str += '<input type="text" class="input-normal selrel'
                            + relnum + '" value="' + relevancename[i] + '" />';
                      }

                      str += '<input type="button" class="btn normal-btn" value="移除" onclick="reldel('
                          + relnum + ')"/></div>';
                      $("#relevance_label").append(str);
                      $("#selrel" + relnum).val(relevance[i]);
                    };
                  };
                };
              }
            }, "json");
  }

  function author_list() {
    $.post("/list/getAllResUser", function(data) {
      if (data) {
        for ( var i = 0; i < data.length; i++) {
          html += '<option id="' + data[i].id + '" value="' + data[i].id + '">'
              + data[i].name + '</option>';
        };
      }
      html += '<option value="-1" >【非本系统科研人员】</option>';
      $("#cauthors").html(html);
      return html;
    }, 'json');
  }

  function getNamelist(author) {
    var str = "";
    var foo = $("select[class='" + author + "']");
    $(foo).each(function() {
      str += this.value + ";";
    });

    return str;
  }
  function checkrepeat(author) {
    var em = [];
    var foo = $("select[class='" + author + "']");
    $(foo).each(function() {
      em.push(this.value);
    });
    for ( var i = 0; i < em.length - 1; i++) {
      for ( var j = i + 1; j < em.length; j++) {
        if (em[i] == em[j]) {
          $.alert("作者不能重复", $("#" + author));
          return false;
        }
      }
    }
    return true;
  }

  // 关联人员合并
  function getRelevance() {
    var str = "";
    var foo = $("select[class='relevance']");
    $(foo).each(function() {
      str += this.value + ";";
    });
    return str;
  }
  // 关联人员名称合并
  function relevanceName() {
    var str = "";
    $("select[class='relevance']").each(function() {
      if ($(this).val() != -1) {
        str += $(this).find("option:selected").text() + ";";
      } else {
        var name = $(this).attr("id");
        var value = $("." + name).val();
        if (value == "") {
          $.alert("人员名称不能为空", $("." + name));
        }
        str += value + ";";
      }

    });

    return str;
  }

  // 检查人员是否为空
  function checknull() {
    var n = 0;
    $("select[class='relevance']").each(function() {
      if ($(this).val() == -1) {
        var name = $(this).attr("id");
        var value = $("." + name).val();
        if (value == "") {
          $.alert("人员名称不能为空", $("." + name));
          n = 1;
        }
      }
    });
    if (n == 1) {
      return false;
    } else {
      return true;
    }
  }
  // 保存
  function save(authorList) {
    var periodical = $("#periodical").val();
    var eperiodical = $("#eperiodical").val();
    var thesis_name = $("#thesis_name").val();
    var ethesis_name = $("#ethesis_name").val();
    var publishyear = $("#publishyear").val();
    var reelnumber = $("#reelnumber").val();
//    var issn = $("#issn").val();
//    var pagination = $("#pagination").val();
    var digest = $("#digest").val();
    if (periodical == "" || eperiodical == "") {
      $.alert("期刊名称不能为空", $("#periodical"));
      $("#periodical").focus();
      return false;
    }
    if (thesis_name == "" || ethesis_name == "") {
      $.alert("论文名称不能为空", $("#thesis_name"));
      $("#thesis_name").focus();
      return false;
    }
    if (publishyear == "" || publishyear == null) {
      $.alert("出版年不能为空", $("#publishyear"));
      $("#publishyear").focus();
      return false;
    }
    if (reelnumber == "" || reelnumber == null) {
      $.alert("卷号不能为空", $("#reelnumber"));
      $("#reelnumber").focus();
      return false;
    }
    if (digest == "" || digest == null) {
      $.alert("摘要不能为空", $("#digest"));
      $("#digest").focus();
      return false;
    }
    // 检测作者是否重复
    if (!checkrepeat("authors")) {
      return false;
    };
    if (!checknull()) {
      return false;
    }
    var relevance = getRelevance();
    relevance = relevance.substring(0, relevance.length - 1);
    var relevancename = relevanceName();
    relevancename = relevancename.substring(0, relevancename.length - 1);

    var authors = authorList.getRows(function($row) {
      var tmp = [], $name = $row.find('input[name="field_name"]'), $dept = $row
          .find('input[name="field_dept"]'), $ename = $row
          .find('input[name="field_ename"]'), name = $name.val(), dept = $dept
          .val(), ename = $ename.val(), $edept = $row.find('input[name="field_edept"]'),
          edept = $edept.val();
      if (!name && !dept && !ename && !edept) {
        return true;
      }
      if (!name && ename) {
        $name.val(name = ename);
      }
      if (!dept && edept) {
        $edept.val(dept = edept);
      }
      tmp.push(name, ename, dept, edept);
      $.each(['iscorrespondor', 'isowner'], function(i, n) {
        tmp.push($row.find('input[name="' + n + '"]:checked').length);
      });
      return tmp.join(',');
    });

    if (!authors) {
      return false;
    }

    authors = authors.join(';');

    // 序列化表单元素
    var formdata = $('form').serialize() + '&model.authors=' + authors
        + '&model.id=' + id + '&model.relevance=' + relevance
        + '&model.relevancename=' + relevancename;
    $.post("/add/saveOrUpdatePerArt", formdata, function(data) {
      if (data.success) {
        $.openSuccess('保存成功', 2000,function(){window.location.href = "/research/addperiodical?id=" + data.id;});
        
      } else {
        $.openError('保存失败', 2000);
      }
    }, "json");
  }

})();
