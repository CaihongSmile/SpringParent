<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>  
	<body>
        <div class="easyui-panel" style="width:100%;height:100%;padding:30px 60px;">
            <form id="cmNotesAddForm" class="easyui-form" method="post">
                <div style="margin-bottom:10px">
                    <label class="label-top" for="noteName">履职记录常用语：<font color="red">*(最多200汉字)</font></label> 
                    <input class="easyui-textbox" name="noteName" id="noteName" style="width:340px;height:120px" 
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

			var cmNotes_save_url =  "cmNotes/saveInsert.do";
			var cmNotes_commit_url =  "cmNotes/commitInsert.do";
			var $savebtn = $('#saveBtn');
			var $btn = $('#submitBtn');
			var $form = $('#cmNotesAddForm');
			
			//保存
			$savebtn.click(function(e) {	
				$savebtn.attr({disabled:"disabled"});
				var flag = $form.form('enableValidation').form('validate');
				if (flag) {
					$.messager.alert('请确认',"您确定要'保存'吗？",'info',function() {
			            var params = $form.serializeObject();
			            
			            $savebtn.attr({disabled:"disabled"});
			    		$.ajax({
							type : "post",
							url : cmNotes_save_url,
							contentType : "application/json;charset=utf-8",
							data : JSON.stringify(params),
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
									$.messager.alert('提示',"新增'保存'成功！",'info',function() {
										$savebtn.removeClass("disabled");
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
					$savebtn.removeClass("disabled");
				}
			});
			
			//提交
			$btn.click(function(e) {	
				$btn.attr({disabled:"disabled"});
				var flag = $form.form('enableValidation').form('validate');
				if (flag) {
					$.messager.alert('请确认',"您确定要'提交'吗？",'info',function() {
			            var params = $form.serializeObject();
			            
			            $btn.attr({disabled:"disabled"});
			    		$.ajax({
							type : "post",
							url : cmNotes_commit_url,
							contentType : "application/json;charset=utf-8",
							data : JSON.stringify(params),
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
									$.messager.alert('提示',"新增'提交'成功！",'info',function() {
										$btn.removeClass("disabled");
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
					$btn.removeClass("disabled");
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