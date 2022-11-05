package com.bitcamp.onemoaproject.dao;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import com.bitcamp.onemoaproject.vo.portfolio.Portfolio;

@Mapper
public interface PortfolioDao {

  int insert(Portfolio portfolio);

  Portfolio findByNo(int ptNo);

  int update(Portfolio portfolio);

  int delete(int no);

  //  int deleteByMember(int memberNo);

  List<Portfolio> findAll(int mno);

  //  int insertFiles(Portfolio portfolio);
  //
  //  PortfolioAttachedFile findFileByNo(int ctstFno);
  //
  //  List<AttachedFile> findFilesByPortfolio(int portfolioNo);
  //
  //  int deleteFile(int ctstFno);
  //
  //  int deleteFiles(int portfolioNo);
  //
  //  int deleteFilesByMemberPortfolios(int memberNo);
}














