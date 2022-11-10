package com.bitcamp.onemoaproject.dao;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import com.bitcamp.onemoaproject.vo.contest.Contest;
import com.bitcamp.onemoaproject.vo.contest.ContestAttachedFile;

@Mapper
public interface ContestDao {

  int insert(Contest contest);

  Contest findByNo(int no);

  int update(Contest contest);

  int delete(int no);

  int deleteByMember(int memberNo);

  List<Contest> findAll();

  int insertFiles(Contest contest);

  ContestAttachedFile findFileByNo(int ctstFno);

  // List<ContestAttachedFile> findFilesByContest(int contestNo);

  int deleteFile(int ctstFno);

  int deleteFiles(int contestNo);

  int deleteFilesByMemberContests(int memberNo);
}














