package com.springmvc.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Map;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections4.CollectionUtils;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.springmvc.bean.TCommonNotes;
import com.springmvc.dao.TCommonNotesMapper;
import com.springmvc.service.CmNotesService;
import com.springmvc.util.CommonUtils;
import com.cmbnt.owk.web.userdata.UserData;
import com.cmbnt.owk.web.userdata.UserDataThreadHolder;

@Service
public class CmNotesServiceImpl implements CmNotesService {
	
	private final Logger logger = LoggerFactory.getLogger(CmNotesServiceImpl.class);
	
	@Autowired
	private TCommonNotesMapper cmNotesBeanMapper;
	
	@Override
	public List<TCommonNotes> queryNoteInfo(TCommonNotes queryModel) {
		return cmNotesBeanMapper.selectByCondition(queryModel);
	}
	
	@Override
	public List<TCommonNotes> queryAllNotes() {
		return cmNotesBeanMapper.selectAllNotes();
	}

	@Override
	@Transactional("transactionManager")
	public void updateCount(TCommonNotes queryModel){
		cmNotesBeanMapper.updateNoteCount(queryModel);
	}
	
	@Override
	public Integer getLastNoteId() {
		return cmNotesBeanMapper.selectCount();
	}
	
	@Override
	public void insertCmNotes(TCommonNotes queryModel){
		cmNotesBeanMapper.insertSelective(queryModel);
		cmNotesBeanMapper.updateNoteCount(queryModel);
	}
	
	@Override
	@Transactional("transactionManager")
	public void uploadFile(List<TCommonNotes> list) {
		for (TCommonNotes cmNotesBean : list) {
			cmNotesBeanMapper.insertSelective(cmNotesBean);
		}
	}
	
	@Override
	public TCommonNotes queryNotesDetail(String noteId) {
		TCommonNotes cmNotesBean = cmNotesBeanMapper.selectById(noteId);

		return cmNotesBean;
	}
	
	
	/**
	 * 修改保存履职记录常用语
	 */
	@Override
	@Transactional("transactionManager")
	public void modifyCmNotesInfo(Map<String, Object> param) {
		logger.info(param.toString());
		
		TCommonNotes cmNotesBean = new TCommonNotes();
		try {
			BeanUtils.copyProperties(cmNotesBean, param);
			cmNotesBean.setNoteId(String.valueOf(param.get("id")));
		} catch (Exception e) {
			e.printStackTrace();
		} 
		cmNotesBeanMapper.updateByPrimaryKeySelective(cmNotesBean);
	}

	/**
	 * 批量提交
	 */
	@Override
	public void batchCommit(TCommonNotes cmNotesBean) {
		// TODO Auto-generated method stub
		
		String checkList = cmNotesBean.getCheckList();
		String noteId[] = checkList.split(",");

		UserData user = UserDataThreadHolder.get();
		String sUser = user.getId() + "_" + user.getName();
		String curDate = CommonUtils.getNowDateTimeStr(); 
		for(int i=0;i<noteId.length;i++){
			TCommonNotes noteBean = new TCommonNotes();
			noteBean.setNoteId(noteId[i]);			
			
			noteBean.setOprName(sUser);
			noteBean.setOprTime(curDate);
			//已生效
			noteBean.setStatus("2");
			cmNotesBeanMapper.updateByNoteId(noteBean);
		}
	}	
	
	/**
	 * 批量失效
	 */
	@Override
	public void batchRemove(TCommonNotes cmNotesBean) {
		// TODO Auto-generated method stub
		
		String checkList = cmNotesBean.getCheckList();
		String noteId[] = checkList.split(",");
		
		UserData user = UserDataThreadHolder.get();
		String sUser = user.getId() + "_" + user.getName();
		String curDate = CommonUtils.getNowDateTimeStr(); 

		for(int i=0;i<noteId.length;i++){
			TCommonNotes noteBean = new TCommonNotes();
			noteBean.setNoteId(noteId[i]);			
			noteBean.setOprName(sUser);
			noteBean.setOprTime(curDate);
			//失效
			noteBean.setStatus("3");
			cmNotesBeanMapper.updateByNoteId(noteBean);
		}
	}	
	
	/**
	 * 导出履职记录常用语表
	 */
	@Override
	public List<Map<String, Object>> queryExpData(Map<String, String> param) throws Exception {
		
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = null;
		String[] noteIds = param.get("checkList").split(",");
		if (noteIds != null && noteIds.length > 0) {
			for (int i = 0;i<noteIds.length;i++) {
				map = new HashMap<String,Object>();
				TCommonNotes cmNotesBean = cmNotesBeanMapper.exportById(noteIds[i]);
				if (cmNotesBean != null) {
					map.putAll(CommonUtils.convertBeanToMap(cmNotesBean, false));
					list.add(map);
				}
			}
			 //将List集合按照Map的status进行排序
	        if(CollectionUtils.isNotEmpty(list)){
	            Collections.sort(list,new Comparator<Map<String,Object>>() {
                    @Override
                    public int compare(Map<String,Object> o1, Map<String,Object> o2) {
                    	String t1 = (String)o1.get("status");
                    	String t2 = (String)o2.get("status");
                    	if (StringUtils.isEmpty(t1)) {
                    		t1 = "";
						}
                    	if (StringUtils.isEmpty(t2)) {
                    		t2 = "";
						}     
                    	return t1.compareTo(t2);
                    }
	            }); 
	        }
			return list;
		}
		return null;
	}
	

}
