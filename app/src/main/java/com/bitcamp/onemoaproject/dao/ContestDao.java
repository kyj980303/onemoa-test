package com.bitcamp.onemoaproject.dao;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import com.bitcamp.onemoaproject.vo.contest.Contest;

@Mapper
public interface ContestDao {

  int insert(Contest contest);

  Contest findByNo(int no);

  int update(Contest contest);

  int delete(int no);

  int deleteByMember(int memberNo);

  List<Contest> findAll();

  int insertFiles(Contest contest);

  // AttachedFile findFileByNo(int fileNo);

  // List<AttachedFile> findFilesByContest(int contestNo);

  int deleteFile(int fileNo);

  int deleteFiles(int contestNo);

  int deleteFilesByMemberContests(int memberNo);
}














