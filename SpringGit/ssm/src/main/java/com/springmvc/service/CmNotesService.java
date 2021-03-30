package com.springmvc.service;

import java.util.List;
import java.util.Map;

import com.springmvc.bean.TCommonNotes;

public interface CmNotesService {
	
	List<TCommonNotes> queryNoteInfo(TCommonNotes queryModel);
	
	void insertCmNotes(TCommonNotes queryModel);

	Integer getLastNoteId();
	
	void updateCount(TCommonNotes queryModel);
	
	void uploadFile(List<TCommonNotes> list);
	
	TCommonNotes queryNotesDetail(String noteId);
	
	void modifyCmNotesInfo(Map<String, Object> param);
	
	void batchCommit(TCommonNotes editBean);
	
	void batchRemove(TCommonNotes TCommonNotes);
	
	List<Map<String, Object>> queryExpData(Map<String, String> param) throws Exception;

	List<TCommonNotes> queryAllNotes();
	
	
}
