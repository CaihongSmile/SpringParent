<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>

<body>
	<div style="padding:10px 10px;">
		 <form id="cmNotesEditForm" class="easyui-form" method="post">
                <div style="margin-bottom:10px; text-align:center;padding:5px 0px">
                    <label class="label-top" for="noteName">履职记录常用语：<font color="red">*(最多200汉字)</font></label>
                    <br><br/>
                    <input class="easyui-textbox" name="noteName" id="noteName" style="width:600px;height:120px"  value="${detail.noteName}"
                    data-options="required:true, multiline:true, validateOnCreate:false, validateOnBlur:true, missingMessage:'履职记录常用语不能为空', validType:'maxlength[200]'">
                </div>
          </form>
		    <div style="text-align:center;padding:5px 0px">
		        <button id="saveBtn" class="easyui-linkbutton"  iconCls="icon-save" style="width:80px">保存</button>
		        <button id="submitBtn" class="easyui-linkbutton"  iconCls="icon-save" style="width:80px">提交</button>
		     	<button id="cancelBtn" class="easyui-linkbutton" iconCls="icon-cancel" style="width:80px">取消</button>
     	   </div>
	</div>
	
	<script type="text/javascript" src="<c:url value='/assets/js/owk.validate.js' />"></script>
	<script type="text/javascript">
	$(function() {

		var cmNotes_save_url =  "cmNotes/saveModify.do";
		var cmNotes_commit_url =  "cmNotes/commitModify.do";
		var $form = $('#cmNotesEditForm');
		
		$('#saveBtn').click(function(){
			$('#saveBtn').attr({disabled:"disabled"});
			var flag = $form.form('enableValidation').form('validate');
			if (flag) {
				$.messager.alert('请确认',"您确定要'保存'修改吗？",'info',function() {
					var params = $("#cmNotesEditForm").serialize();
					params = params+"&id="+'${detail.noteId}';
					$('#saveBtn').attr({disabled:"disabled"});
					
		    		$.ajax({
						type : "POST",
						url : cmNotes_save_url,
						cache:false,
						data : params,
						dataType : "json",
						beforeSend:function(){
							$.messager.progress({
								title:"请稍等",
								text:"加载中..."
							})
						},
						success : function(result) {
							$.messager.progress('close');
							if(result.success){
								$.messager.alert('提示',"修改'保存'成功！",'info',function() {
									$('#cmNotesDialog').dialog('close');
									$('#cmNotesTable').datagrid('reload');
								});
							} else {
								$.messager.alert('提示',result.msg,'error');
							}
						},
						error:function(error){
							$.messager.progress('close');
							$.messager.alert('提示','请确认网络连接','error');
						}
		    		});
				});
			}else{
				$('#saveBtn').removeClass("disabled");
			}			
		});	
		
		$('#submitBtn').click(function(){
			$('#submitBtn').attr({disabled:"disabled"});
			var flag = $form.form('enableValidation').form('validate');
			if (flag) {
			 $.messager.alert('请确认',"您确定要'提交'修改吗？",'info',function() {
				var params = $("#cmNotesEditForm").serialize();
				params = params+"&id="+'${detail.noteId}';
				$('#submitBtn').attr({disabled:"disabled"});
	    		$.ajax({
					type : "POST",
					url : cmNotes_commit_url,
					cache:false,
					data : params,
					dataType : "json",
					beforeSend:function(){
						$.messager.progress({
							title:"请稍等",
							text:"加载中..."
						})
					},
					success : function(result) {
						$.messager.progress('close');
						if(result.success){
							$.messager.alert('提示',"修改'提交'成功！",'info',function() {
								$('#cmNotesDialog').dialog('close');
								$('#cmNotesTable').datagrid('reload');
							});
						} else {
							$.messager.alert('提示',result.msg,'error');
						}
					},
					error:function(error){
						$.messager.progress('close');
						$.messager.alert('提示','请确认网络连接','error');
					}
	    		});
			});
		}else{
			$('#submitBtn').removeClass("disabled");
		}
	});	
		
		//取消
		$('#cancelBtn').click(function(){
			$('#cmNotesDialog').dialog('close');
		})

	});
	
	</script>
</body>
</html>