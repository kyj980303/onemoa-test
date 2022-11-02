package com.bitcamp.onemoaproject.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.bitcamp.onemoaproject.dao.ContestDao;
import com.bitcamp.onemoaproject.vo.contest.Contest;
import com.bitcamp.onemoaproject.vo.contest.ContestAttachedFile;

@Service
public class DefaultContestService implements ContestService {

  @Autowired 
  ContestDao contestDao;

  @Transactional
  @Override
  public void add(Contest contest) throws Exception {
    // 1) 게시글 등록
    if (contestDao.insert(contest) == 0) {
      throw new Exception("게시글 등록 실패!");
    }

    // 2) 첨부파일 등록
    if (contest.getAttachedFiles().size() > 0) {
      contestDao.insertFiles(contest);
    }
  }

  @Transactional
  @Override
  public boolean update(Contest contest) throws Exception {
    // 1) 게시글 변경
    if (contestDao.update(contest) == 0) {
      return false;
    }

    //    // 2) 첨부파일 추가
    //    if (contest.getAttachedFiles().size() > 0) {
    //      contestDao.insertFiles(contest);
    //    }

    return true;
  }

  @Override
  public Contest get(int no) throws Exception {
    return contestDao.findByNo(no); // 첨부파일 데이터까지 조인하여 select를 한 번만 실행한다.
  }

  @Transactional
  @Override
  public boolean delete(int no) throws Exception {
    // 1) 첨부파일 삭제
    //contestDao.deleteFiles(no);

    // 2) 게시글 삭제
    return contestDao.delete(no) > 0;
  }

  @Override
  public List<Contest> list() throws Exception {
    return contestDao.findAll();
  }

  @Override
  public ContestAttachedFile getAttachedFile(int ctstFno) throws Exception {
    return contestDao.findFileByNo(ctstFno);
  }

  @Override
  public boolean deleteAttachedFile(int fileNo) throws Exception {
    return contestDao.deleteFile(fileNo) > 0;
  }

}








