<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
	<head>
	    <meta charset="UTF-8">
	    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
	    <meta name="viewport" content="width=device-width, initial-scale=1.0">
	    
	    <link rel="stylesheet" type="text/css" href="<c:url value='/assets/easyui/themes/bootstrap/easyui.css'/>"/>
        <link rel="stylesheet" type="text/css" href="<c:url value='/assets/easyui/themes/icon.css'/>"/>
        <link rel="stylesheet" type="text/css" href="<c:url value='/lsjrms/lz/css/main.css?v=1.0'/>"/>
	</head>
    
     <body>
     
	    <div class="container-fluid">
	        <div class="row">
	            <div class="col-xs-12" style="width:100%;height:600px;"  fit="true">
	                <table id="cmNotesTable" title="履职记录常用语查询" data-options="toolbar:'#toolbar'" nowrap="false" word-wrap="break-all" ></table>
	            </div>
	        </div>
	    </div>
	              
	    
	    <div id="toolbar" style="padding:2px 5px;">
	    	<div style="margin-bottom:5px">
	    	    <button id="importBtn" class="easyui-linkbutton" plain="true" iconCls="icon-add">批量导入</button>
				<button id="addManualBtn" class="easyui-linkbutton" plain="true" iconCls="icon-add">新增</button>
				<button id="editBtn" class="easyui-linkbutton" plain="true" iconCls="icon-edit">修改</button>
				<button id="commitBtn" class="easyui-linkbutton" plain="true" iconCls="icon-tip">提交<font color="red">（勾选）</font></button>
            	<button id="removeBtn" class="easyui-linkbutton" plain="true" iconCls="icon-remove">失效<font color="red">（勾选）</font></button>
            	<button id="excelDownloadBtn" class="easyui-linkbutton" plain="true" iconCls="icon-save">下载模板</button>
            	<button id="infoDownloadBtn" class="easyui-linkbutton" plain="true" iconCls="icon-save">导出<font color="red">（勾选，仅导出当前页信息）</font></button>
            	
	    	</div>
	    	
	    	<div class="row">
                <label class="label-left" for="status">状态:</label>
              <select class="easyui-combobox" id="status" name="status" panelHeight="auto" data-options="prompt:'请选择状态'" editable="false"  style="width:200px">
              	<option value="">--请选择--</option>
              </select> 
               
              <button class="easyui-linkbutton" id="queryBtn" iconCls="icon-search">查询</button>
          </div>
		</div>
		
		<form id="relateForm" class="easyui-form" method="post">
             <div style="margin-bottom:20px">
					<input type="hidden" name="checkList" id="checkList" />
             </div>
         </form>
         
        <script type="text/javascript">
   			var checkList = '${checkList}';
		</script>
	   	
		
	    <script type="text/javascript" src="<c:url value='/assets/js/jquery.min.js' />"></script>
	    <script type="text/javascript" src="<c:url value='/assets/easyui/jquery.easyui.min.js' />"></script>
    	<script type="text/javascript" src="<c:url value='/assets/easyui/locale/easyui-lang-zh_CN.js' />"></script>
        <script type="text/javascript" src="<c:url value='/assets/js/owk.util.js' />"></script>
        <script type="text/javascript" src="<c:url value='/assets/js/moment.js' />"></script>
        <script type="text/javascript" src="<c:url value='/lsjrms/lz/js/md5.js' />"></script>
		<script type="text/javascript">
		$(function() {
			
			var names = [];
        	var status_cns = [];
			
        	var $form = $('#relateForm');
        	
			$.ajax({
   	     		type:"POST",
   	     		url:"param/listParam.do",
   	     		cache:false,
   	     		data:{type:'NOTE_STATUS'},
   	     		dataType:'json',
   	     		success:function(data){
   	     			if(data.return_code == 'success') {
	   	     			if (data.body != null) {
		   	     			$("#status").combobox({
		   	     				data:data.body,
		   	     				valueField:'paramId',
		   	     				textField:'paramName',
			   	     			loadFilter:function(data)
		   	     				{
			   	 					var obj={};
			   	 					obj.paramId='';
			   	 					obj.paramName='-请选择-';
			   	 					data.splice(0,0,obj);
			   	 					return data;
		   	 				    }
		   	     			})
	    				}
   	     			}
   	     		},
   	     		error:function (err){
	   	     		$.messager.alert('提示','网络异常','error');
   	     		}
   	     	});

			// 定义的url
			var query_comNote_url = "cmNotes/query.do";
			var upload_comNote_url = "cmNotes/uploadPage.jspx";
		    var add_comNote_url = "cmNotes/addPage.jspx";
		    var editPage_comNote_url = "cmNotes/editPage/{}.jspx";
		    var commit_cmNote_url = "cmNotes/commit.do";
		    var remove_comNote_url = "cmNotes/remove.do";
		    
        	//names = checkList.split(",");
        	console.log('checkList:'+checkList);
		    
		    var grid_selector = '#cmNotesTable';  
			// 表单内容
			$(grid_selector).datagrid({
				url : query_comNote_url,
				method : "post",
		        width: '100%',
		        columns : [[
					{ field: 'ck',checkbox:true},
		        	{ title: '编号', field: 'noteId', width : 20, sortable:true},
		        	{ title: '履职记录常用语', field: 'noteName', width : 80, sortable:true},
		            { title: '创建人', field: 'createName', width : 30, sortable:true},
		            { title: '创建时间', field: 'createTime', width : 30, sortable:true},
		            { title: '操作人', field: 'oprName', width : 30, sortable:true},
		            { title: '操作时间', field: 'oprTime', width : 30, sortable:true},
		            { title: '状态', field: 'statusName', width : 30, sortable:true},
		        ]],		
		        fit:true,
		        fitColumns:true,
		        remoteSort:true,
		        pagination:true,
		        singleSelect:true,
		        checkOnSelect:false,  
                selectOnCheck:false,
		        pageList:[10,20,30,50,100],	
		        onCheck:function(index, row){
	        		var checkedItems = $(grid_selector).datagrid('getChecked');
            		$.each(checkedItems, function(index, row){
            		    if(names.indexOf(row.noteId)>=0){
            		    	//存在的就不加
            		    }else{
            		    	names.push(row.noteId);
            		    	status_cns.push(row.statusName);
            		    }
            		});
                	console.log('清单='+names);
                	console.log('status_cns='+status_cns);
                }, 
                onUncheck:function(index, row){
            		//取消的时候
                	var ind = names.indexOf(row.noteId);
            		names.splice(ind,1);
            		status_cns.splice(ind,1);
                	checkList=names.join(",");
                	console.log('清单='+names);
                	console.log('status_cns='+status_cns);
                },
                onCheckAll:function(rows){
            		for(var i=0;i<rows.length;i++){
            			if(names.indexOf(rows[i].noteId)>=0){
            				;
            			}else{
                			names.push(rows[i].noteId);
                			status_cns.push(rows[i].statusName);
            			}
            		}
                	checkList=names.join(",");
                	console.log('清单='+names);
                	console.log('status_cns='+status_cns);
            	},
                onUncheckAll :function(rows){
                	var ind = 0;
            		for(var i=0;i<rows.length;i++){
            			if(names.indexOf(rows[i].noteId)>=0){
            				ind = names.indexOf(rows[i].noteId);
            	    		names.splice(ind,1);
            	    		status_cns.splice(ind,1);
            			}else{
            				;
            			}
            		}
                	console.log('清单='+names);
                	console.log('status_cns='+status_cns);
                }   
               	
			});
			
			// 弹出窗的默认状态
			var  div_dialog = $('<div style="position:relative"/>').dialog({
				closed:true,
		    	cache:false,
		    	modal:true,
				left:100,
				top:20,
				onClose:function(){
		    		$(this).dialog('destroy', true);
		    	}
			});
			
			// 点击查询按钮
			$('#queryBtn').click(function(e) {
				e.preventDefault();
				names = [];
        		status_cns = [];
        		$(grid_selector).datagrid("uncheckAll");
				var status = $('#status').combobox('getValue');
				$(grid_selector).datagrid('load',{
					status : status,
				})
			});
			
			// 批量导入
			$('#importBtn').click(function(){
				div_dialog.dialog({
					id:"cmNotesDialog",
					title: '导入模板',
					width: 600,
					height: 500,
					resizable:true,
					closed:false,
					href: upload_comNote_url,
					iconCls: 'icon-add',
					onLoad:function(){
						names = [];
		        		status_cns = [];
		        		$(grid_selector).datagrid("uncheckAll");
						console.log("cmNotesDialog");
					}
				})
			});	
			
			// 点击新增按钮
			$('#addManualBtn').click(function(){
				div_dialog.dialog({
					id:"cmNotesDialog",
					title: '新增常用语',
					left:100,
					top:20,
					width: 500,
					height: 500,
					resizable:true,
					closed:false,
					href: add_comNote_url,
					iconCls: 'icon-add',
					onLoad:function(){
						names = [];
		        		status_cns = [];
		        		$(grid_selector).datagrid("uncheckAll");
						console.log("cmNotesDialog");
					}
				})
			});	
			
			//点击修改按钮
			$("#editBtn").click(function(){
				var rowData =$(grid_selector).datagrid('getSelected');
				//alert(rowData.noteId);
				//alert(rowData.statusName);
			    if(rowData == null ||rowData==""){
	       			$.messager.alert("提示","请选择一条数据","warning");
	       			return;
       	        }
				if(rowData.statusName == "已生效" || rowData.statusName == "失效")
				{
					$.messager.alert('提示','此状态不可进行‘修改’操作！','warning');
					return;
				}
				div_dialog.dialog({
					id:"cmNotesDialog",
					title: '修改页面',
					top:10,
					width: 1000,
					height: 800,
					resizable:true,
					closed:false,
					href: owk.fn.url(editPage_comNote_url, rowData.noteId),
					iconCls: 'icon-edit',
					onLoad:function(){
						names = [];
		        		status_cns = [];
		        		$(grid_selector).datagrid("uncheckAll");
						console.log("cmNotesDialog");
					}
				})
				
			});
			
			//点击提交按钮
			$('#commitBtn').click(function(){
				$('#commitBtn').attr({disabled:"disabled"});
        		//alert("点击提交按钮");
        		//alert("选前:"+names);
        		//alert("选前:"+status_cns);
        	    if (names.length < 1) {
        			$.messager.alert("提示","请选择一条数据","warning");
        			return;
        	    }
        	    if(status_cns.indexOf('已生效') != -1 || status_cns.indexOf('失效') != -1 )
        	    {
        		   $.messager.alert("提示","仅'待提交'的记录允许提交操作，请重新选择！","warning");
       			   return;
        	    }
        		
        	    var flag = $form.form("enableValidation").form('validate');
        		$("#checkList").val(names);
        		
        		if(flag){       
        			$.messager.alert('请确认',"您确定要'提交'勾选记录吗？",'info',function() {
	        			var relateParam = $form.serializeObject();    
	                    $('#commitBtn').attr({disabled:"disabled"});
	            		$.ajax({
	        				type : "post",
	        				url : commit_cmNote_url,
	        				data : JSON.stringify(relateParam),
	        				contentType : "application/json",
	        				dataType : "text",
	        				success : function(result) {
	        					$.messager.alert('提示',"'提交'数据操作成功！",'info',function() {
	        						$('#commitBtn').removeClass("disabled");
	        						names = [];
	        		        		status_cns = [];
	        		        		$(grid_selector).datagrid("uncheckAll");
	        						$('#cmNotesDialog').dialog('close');
	        						$('#cmNotesTable').datagrid('reload');
	        					});
	        				}
	            		});
        			});	
        		}else{
        			$('#commitBtn').removeClass("disabled");
        		}
        		
                //alert("选后:"+names);
        		//alert("选后:"+status_cns);
			});	
			
			
			// 点击失效按钮
			$('#removeBtn').click(function(){
				$('#removeBtn').attr({disabled:"disabled"});
        		//alert("点击失效按钮");
        		//alert("选前:"+names);
        		//alert("选前:"+status_cns);
        	    if (names.length < 1) {
        			$.messager.alert("提示","请选择一条数据","warning");
        			return;
        	    }
        	    if(status_cns.indexOf('待提交') != -1 || status_cns.indexOf('失效') != -1 )
        	    {
        		   $.messager.alert("提示","仅'已生效'的记录允许失效操作，请重新选择！","warning");
       			   return;
        	    }
        		
        	    var flag = $form.form("enableValidation").form('validate');
        		$("#checkList").val(names);
        		
        		if(flag){       
        			$.messager.alert('请确认',"您确定要'失效'勾选记录吗？",'info',function() {
	        			var relateParam = $form.serializeObject();    
	                    $('#removeBtn').attr({disabled:"disabled"});
	            		$.ajax({
	        				type : "post",
	        				url : remove_comNote_url,
	        				data : JSON.stringify(relateParam),
	        				contentType : "application/json",
	        				dataType : "text",
	        				success : function(result) {
	        					$.messager.alert('提示',"批量'失效'数据成功！",'info',function() {
	        						$('#removeBtn').removeClass("disabled");
	        						names = [];
	        		        		status_cns = [];
	        		        		$(grid_selector).datagrid("uncheckAll");
	        						$('#cmNotesDialog').dialog('close');
	        						$('#cmNotesTable').datagrid('reload');
	        					});
	        				}
	            		});
        			});	
        		}else{
        			$('#removeBtn').removeClass("disabled");
        		}
                //alert("选后:"+names);
        		//alert("选后:"+status_cns);
			});	
	
			//模板下载
			$("#excelDownloadBtn").click(function(){
				window.location.href =  "cmNotes/excelExport.do";
				names = [];
        		status_cns = [];
        		$(grid_selector).datagrid("uncheckAll");
			});
			
			//导出
			$("#infoDownloadBtn").click(function(){
			    if (names.length < 1) {
        			$.messager.alert("提示","请选择一条数据","warning");
        			return;
        	    }
        	   // $("#checkList").val(names);
				window.location.href =  "cmNotes/infoExport.do?checkList=" + names;
				names = [];
        		status_cns = [];
        		$(grid_selector).datagrid("uncheckAll");
			});
			
		});
		</script>
	</body>	
</html>