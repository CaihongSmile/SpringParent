package com.springmvc.controller;

import java.io.BufferedOutputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.cmbnt.owk.web.userdata.UserData;
import com.cmbnt.owk.web.userdata.UserDataThreadHolder;
import com.cmbnt.owk.web.util.PageUtil;
import com.springmvc.bean.TCommonNotes;
import com.springmvc.service.CmNotesService;
import com.springmvc.util.CommonUtils;
import com.springmvc.util.Constants;
import com.springmvc.util.POIUtil;
import com.springmvc.util.RespModel;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Controller
@RequestMapping("/cmNotes")
public class CmNotesController {

	private final Logger logger = LoggerFactory.getLogger(CmNotesController.class);
	
	@Autowired
	private CmNotesService cmNotesService;

	/**
	 * 主页面
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "", method = RequestMethod.GET)
	public ModelAndView cmNotesMng() {
		return new ModelAndView("cmNotes_mng");
	}
	

	//查询
	@ResponseBody
	@RequestMapping(value = "/query", method = RequestMethod.POST)
	public Map<String, Object> query(TCommonNotes queryModel, int page, int rows) {

		if (queryModel.getSort() == null) {
			PageHelper.orderBy("OPR_TIME desc");
		} else {
			PageHelper.orderBy(CommonUtils.recoverDbColumnName(queryModel.getSort()) + " " + queryModel.getOrder());
		}

		PageHelper.startPage(page, rows, true);

		List<TCommonNotes> pageList = cmNotesService.queryNoteInfo(queryModel);
		return PageUtil.toDataGridPageResult(new PageInfo<TCommonNotes>(pageList));
	}

	/**
	 * 手工增加页面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addPage", method = RequestMethod.GET)
	public ModelAndView manualPage() {
		return new ModelAndView("cmNotes_add");
	}

   //新增保存
	@ResponseBody
	@RequestMapping(value = "/saveInsert", method = RequestMethod.POST)
	public RespModel saveInsert(@RequestBody TCommonNotes queryModel) {
		logger.debug("新增保存履职记录常用语");

		Integer count= cmNotesService.getLastNoteId();
		count = count + 1;
		// 0 代表前面补充0
	    // 10 代表长度为10
	    // d 代表参数为正数型
	    String strLastNoteId = String.format("%010d", count);
		queryModel.setNoteId(strLastNoteId);
		queryModel.setOrgCount(count-1);
		queryModel.setNoteCount(count);

		UserData user = UserDataThreadHolder.get();
		String strUser = user.getId() + "_" + user.getName();
		String strTime = CommonUtils.getNowDateTimeStr();
		queryModel.setCreateName(strUser);
		queryModel.setCreateTime(strTime);
		queryModel.setOprName(strUser);
		queryModel.setOprTime(strTime);
		//待提交
		queryModel.setStatus("1");

		try {
			cmNotesService.insertCmNotes(queryModel);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return RespModel.newInstance(false, "新增'保存'失败:" +e.getMessage(), null);
		}
		return RespModel.newInstance(true, "新增'保存'成功", null);
	}

	//新增提交
	@ResponseBody
	@RequestMapping(value = "/commitInsert", method = RequestMethod.POST)
	public RespModel commitInsert(@RequestBody TCommonNotes queryModel) {
		logger.debug("新增提交履职记录常用语");

		Integer count= cmNotesService.getLastNoteId();
		count = count + 1;
		String strLastNoteId = String.format("%010d", count);
		queryModel.setNoteId(strLastNoteId);
		queryModel.setOrgCount(count-1);
		queryModel.setNoteCount(count);

		UserData user = UserDataThreadHolder.get();
		String strUser = user.getId() + "_" + user.getName();
		String strTime = CommonUtils.getNowDateTimeStr();
		queryModel.setCreateName(strUser);
		queryModel.setCreateTime(strTime);
		queryModel.setOprName(strUser);
		queryModel.setOprTime(strTime);
		//已生效
		queryModel.setStatus("2");
		try {
			cmNotesService.insertCmNotes(queryModel);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return RespModel.newInstance(false, "新增'提交'失败:" +e.getMessage(), null);
		}
		return RespModel.newInstance(true, "新增'提交'成功", null);
	}

	/**
	 * 导入文件页面
	 * @return
	 */
	@RequestMapping(value = "/uploadPage", method = RequestMethod.GET)
	public ModelAndView batchNewPage() {
		return new ModelAndView("cmNotes_upload");
	}

	//导入
	@ResponseBody
	@RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
	public RespModel uploadFile(@RequestParam(value = "uploadFile") MultipartFile file) throws Exception {

		String contentType = CommonUtils.getExtensionName(file.getOriginalFilename());
		if (!file.isEmpty()){
			boolean checkRes = contentType.equalsIgnoreCase("xlsx");
			if(!checkRes){
				//文件格式不正确
				return RespModel.newInstance(false, "文件格式不正确，请选择Excel文件上传", null);
			}
		}
		try {
			List<Object[]> list = POIUtil.getDataByExcelFile1(contentType, file.getInputStream());
			if (list == null || list.size() < 1 ) {
				return RespModel.newInstance(false, "文档内容为空，请完善", null);
			}
			List<TCommonNotes> cmNotesList = new ArrayList<TCommonNotes>();

			TCommonNotes TCommonNotes = null;

			Integer count= cmNotesService.getLastNoteId();

			UserData user = UserDataThreadHolder.get();
			String strUser = user.getId() + "_" + user.getName();
			String strTime = CommonUtils.getNowDateTimeStr();

			for(int i = 0; i < list.size(); i++){
				Object[] row = list.get(i);
				//TCommonNotes
				TCommonNotes = new TCommonNotes();
				Integer id = count + i + 1;
				String strLastNoteId = String.format("%010d", id);
				TCommonNotes.setNoteId(strLastNoteId);

				String noteName = null;
				noteName = String.valueOf(row[0]);

				if(noteName.equals("null") || noteName.length() == 0)
				{
					return RespModel.newInstance(false, "上传失败:'履职记录常用语'不能为空！", null);
				}
				else
				{
					TCommonNotes.setNoteName(noteName);
				}

				TCommonNotes.setCreateName(strUser);
				TCommonNotes.setCreateTime(strTime);
				TCommonNotes.setOprName(strUser);
				TCommonNotes.setOprTime(strTime);
				//待提交
				TCommonNotes.setStatus("1");

				cmNotesList.add(TCommonNotes);
			}
			cmNotesService.uploadFile(cmNotesList);

			TCommonNotes noteCountBean = new TCommonNotes();
			noteCountBean.setOrgCount(count);
			noteCountBean.setNoteCount(count + list.size());
			cmNotesService.updateCount(noteCountBean);

		} catch (Exception e) {
			logger.error(e.getMessage());
			return RespModel.newInstance(false, "上传失败:"+e.toString(), null);
		}
		return RespModel.newInstance(true, "上传成功", null);
	}


	/**
	 * 编辑页面
	 * @return
	 */
	@RequestMapping(value = "/editPage/{id}", method = RequestMethod.GET)
	public ModelAndView editPage(@PathVariable String id) {
		ModelAndView mv = new ModelAndView("cmNotes_edit");
		mv.addObject("detail",cmNotesService.queryNotesDetail(id));
		return mv;
	}

	/**
	 * 修改保存履职记录常用语
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/saveModify", method = RequestMethod.POST)
	public RespModel saveModify(@RequestParam Map<String, Object> param) {
		logger.debug("修改保存履职记录常用语");
		UserData user = UserDataThreadHolder.get();
		param.put("oprName", user.getId() + "_" + user.getName());
		param.put("oprTime", CommonUtils.getNowDateTimeStr());
		//待提交
		param.put("status", "1");
		try {
			cmNotesService.modifyCmNotesInfo(param);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return RespModel.newInstance(false, "修改'保存'失败:" +e.getMessage(), null);
		}
		return RespModel.newInstance(true, "修改'保存'成功", null);
	}

	/**
	 * 修改提交履职记录常用语
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/commitModify", method = RequestMethod.POST)
	public RespModel commitModify(@RequestParam Map<String, Object> param) {
		logger.debug("修改提交履职记录常用语");
		UserData user = UserDataThreadHolder.get();
		param.put("oprName", user.getId() + "_" + user.getName());
		param.put("oprTime", CommonUtils.getNowDateTimeStr());
		//已生效
		param.put("status", "2");
		try {
			cmNotesService.modifyCmNotesInfo(param);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return RespModel.newInstance(false, "修改'提交'失败:" +e.getMessage(), null);
		}
		return RespModel.newInstance(true, "修改'提交'成功", null);
	}

	 /* 批量提交
	 *
	 * @param TCommonNotes
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/commit", method = RequestMethod.POST)
	public void commit(@RequestBody TCommonNotes TCommonNotes) throws Exception {
		if(null==TCommonNotes.getCheckList()||TCommonNotes.getCheckList().equals("")){
			//如果没有勾选，就不处理
			;
		}else{
			cmNotesService.batchCommit(TCommonNotes);
		}
	}

	 /* 批量失效
	 *
	 * @param TCommonNotes
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/remove", method = RequestMethod.POST)
	public void remove(@RequestBody TCommonNotes TCommonNotes) throws Exception {
		if(null==TCommonNotes.getCheckList()||TCommonNotes.getCheckList().equals("")){
			//如果没有勾选，就不处理
			;
		}else{
			cmNotesService.batchRemove(TCommonNotes);
		}
	}
	
	/**
	 * 下载模板
	 * @param response
	 * @param param
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/excelExport", method = RequestMethod.GET)
	public void  excelExport(HttpServletResponse response, @RequestParam Map<String, String> param) throws Exception {
		
		String fName = "履职记录常用语模板表.xlsx";
		String fileName = fName;
		String[] headerArr= {"履职记录常用语*"};
		String[] keyArr= {"noteName"};
		
		fileName = URLEncoder.encode(fileName, StandardCharsets.UTF_8.toString());
    	response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"; filename*=utf-8''" + fileName);
    	response.setContentType("applicaiton/vnd.ms-excel;charset=UTF-8");
    	response.setHeader("Pragma", "no-cache");
    	response.setHeader("Cache-Control", "no-cache");
    	response.setDateHeader("Expires", 0);
    	
    	List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
    	
		OutputStream os = response.getOutputStream();
		Workbook workbook = POIUtil.exportExcelXssf(fName, headerArr, keyArr, data);
		BufferedOutputStream bos = new BufferedOutputStream(os);
		workbook.write(bos);
		bos.flush();
		bos.close();
		workbook.close();
	}
	
	/**
	 * 导出表
	 * @param response
	 * @param param
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/infoExport", method = RequestMethod.GET)
	public void  infoExport(HttpServletResponse response, @RequestParam Map<String, String> param) throws Exception {
		
		String fName = "履职记录常用语导出表.xlsx";
		String fileName = fName;
		String[] headerArr= {"编号","履职记录常用语","创建人","创建时间","操作人","操作时间","当前状态"};
		String[] keyArr= {"noteId","noteName","createName","createTime","oprName","oprTime","statusName"};
		
		fileName = URLEncoder.encode(fileName, StandardCharsets.UTF_8.toString());
    	response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"; filename*=utf-8''" + fileName);
    	response.setContentType("applicaiton/vnd.ms-excel;charset=UTF-8");
    	response.setHeader("Pragma", "no-cache");
    	response.setHeader("Cache-Control", "no-cache");
    	response.setDateHeader("Expires", 0);
    	
    	List<Map<String,Object>> data = cmNotesService.queryExpData(param);
    	if (data == null) {
    		return;
    	}
    	
		OutputStream os = response.getOutputStream();
		Workbook workbook = POIUtil.exportExcelXssf(fName, headerArr, keyArr, data);
		BufferedOutputStream bos = new BufferedOutputStream(os);
		workbook.write(bos);
		bos.flush();
		bos.close();
		workbook.close();
	}
	
	@RequestMapping(value = "/noteParam", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> noteParam() {

		Map<String, Object> result = new HashMap<String, Object>();
		result.put(Constants.BODY, cmNotesService.queryAllNotes());
		result.put(Constants.RETURN_CODE, Constants.SUCCESS);
		return result;
	}

}
