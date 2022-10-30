package com.bitcamp.onemoaproject.service;

import java.util.List;
import com.bitcamp.onemoaproject.vo.Contest;

// 비즈니스 로직을 수행하는 객체의 사용규칙(호출규칙)
//
public interface ContestService {

  void add(Contest contest) throws Exception;

  boolean update(Contest contest) throws Exception;

  Contest get(int no) throws Exception;

  boolean delete(int no) throws Exception;

  List<Contest> list() throws Exception;

  // AttachedFile getAttachedFile(int fileNo) throws Exception;

  // boolean deleteAttachedFile(int fileNo) throws Exception;

}








