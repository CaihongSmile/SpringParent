package com.springmvc.dao;

import com.springmvc.bean.TCommonNotes;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface TCommonNotesMapper {

    List<TCommonNotes> selectByCondition(TCommonNotes queryModel);

    int insertSelective(TCommonNotes record);

    int updateNoteCount(TCommonNotes record);

    int selectCount();

    TCommonNotes selectById(@Param("noteId") String noteId);

    int updateByPrimaryKeySelective(TCommonNotes record);

    int updateByNoteId(TCommonNotes record);

    TCommonNotes exportById(@Param("noteId") String noteId);

    List<TCommonNotes> selectAllNotes();


}