package com.bitcamp.onemoaproject.service;

import java.util.List;
import com.bitcamp.onemoaproject.vo.portfolio.Portfolio;

// 비즈니스 로직을 수행하는 객체의 사용규칙(호출규칙)
//
public interface PortfolioService {

  void add(Portfolio portfolio) throws Exception;

  boolean update(Portfolio portfolio) throws Exception;

  Portfolio get(int ptNo) throws Exception;

  boolean delete(int no) throws Exception;

  List<Portfolio> list(int mno) throws Exception;

  //  PortfolioAttachedFile getAttachedFile(int ctstFno) throws Exception;
  //
  //  boolean deleteAttachedFile(int ctstFno) throws Exception;

}








