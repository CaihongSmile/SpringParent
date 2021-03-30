<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<body>
	
	<div class="easyui-panel"
		style="width: 100%; height: 100%; padding: 30px 60px;">
		<form id="uploadForm" class="easyui-form" method="POST"  enctype="multipart/form-data" >
			 <div style="margin-bottom:20px">
                   <label class="label-top" for="uploadFile">上传履职记录常用语:</label> 
				<input id="uploadFile" name="uploadFile" class="easyui-filebox" 
					style="width:80%"  data-options="prompt:'请选择文件',buttonText:'浏览文件',accept:'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet'">
             </div>  
		</form>
		<div style="text-align: center; padding: 5px 0px">
			<button id="cancelBtn" class="easyui-linkbutton"
				iconCls="icon-cancel" style="width: 80px">取消</button>
			<button id="uploadBtn" class="easyui-linkbutton"
				iconCls="icon-save" style="width: 80px">提交</button>
			
		</div>
	</div>
	<script type="text/javascript"
		src="<c:url value='/assets/js/owk.validate.js' />"></script>
	<script type="text/javascript">
		
		$(function() {
			
			$('#cancelBtn').click(function(){
				$('#cmNotesDialog').dialog('close');
			});	
			
			$('#uploadBtn').click(function(e){
				e.preventDefault();
				var path = $("#uploadFile").filebox('getValue');
				if(path && ""!= path){					
					$("#uploadForm").form("submit", {
						url:"cmNotes/uploadFile.do",
						onSubmit:function(){
							$.messager.progress({
								title:"请稍等",
								text:"加载中..."
							});
						},
						success:function(data){
							$.messager.progress('close');
							var result = JSON.parse(data);
							if(result.success){
								$.messager.alert('提示',"上传成功！",'info',function() {
									$('#cmNotesDialog').dialog('close');
									$('#cmNotesTable').datagrid('reload');
								});
							} else {
								$.messager.alert({
									width:300,
									title:"上传失败",
									msg:result.msg
								});
							}
						}
					});					
				} else {
					$.messager.alert('提示','请选择要上传的文件！','warning');
				}
				
			});
		});
		</script>
</body>
</html>