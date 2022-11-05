package com.bitcamp.onemoaproject.web.controller;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.bitcamp.onemoaproject.service.PortfolioService;
import com.bitcamp.onemoaproject.vo.Member;
import com.bitcamp.onemoaproject.vo.portfolio.Portfolio;

@Controller
@RequestMapping("portfolio")
public class PortfolioController {

  ServletContext sc;
  PortfolioService portfolioService;

  public PortfolioController(PortfolioService portfolioService, ServletContext sc) {
    System.out.println("PortfolioController() 호출됨!");
    this.portfolioService = portfolioService;
    this.sc = sc;
  }

  @GetMapping("portfolioList")
  public String list(Model model, HttpSession session) throws Exception {
    Member loginMember = (Member) session.getAttribute("loginMember");
    model.addAttribute("portfolios", portfolioService.list(loginMember.getNo()));

    return "portfolio/portfolioList";
  }

  @PostMapping("portfolioAdd") 
  public String add(
      Portfolio portfolio,
      //MultipartFile[] files,
      //MultipartFile files2,
      HttpSession session) throws Exception {

    //    contest.setAttachedFiles(saveAttachedFiles(files));
    //    contest.setThumbNail(saveAttachedFiles2(files2));

    portfolio.setMember((Member) session.getAttribute("loginMember"));

    portfolioService.add(portfolio);
    System.out.println("인서트 성공!");
    return "redirect:portfolioList";
  }

  @GetMapping("portfolioDetail")
  public Map contestDetail(int ptNo) throws Exception {
    Portfolio portfolio = portfolioService.get(ptNo);
    if (portfolio == null) {
      throw new Exception("해당 번호의 게시글이 없습니다!");
    }

    Map map = new HashMap();
    map.put("portfolio", portfolio);
    return map;
  }

  @PostMapping("portfolioUpdate")
  public String update(
      Portfolio portfolio,
      //MultipartFile[] files,
      //MultipartFile files2,
      HttpSession session) 
          throws Exception {

    //    contest.setAttachedFiles(saveAttachedFiles(files));
    //    contest.setThumbNail(saveAttachedFiles2(files2));

    // checkOwner(contest.getNo(), session);

    if (!portfolioService.update(portfolio)) {
      throw new Exception("게시글을 변경할 수 없습니다!");
    }

    return "redirect:portfolioList";
  }

  @GetMapping("portfolioDelete")
  public String delete(
      int ptNo, 
      HttpSession session) 
          throws Exception {

    // checkOwner(no, session);
    if (!portfolioService.delete(ptNo)) {
      throw new Exception("게시글을 삭제할 수 없습니다.");
    }

    return "redirect:portfolioList";
  }

  //  @GetMapping("contestList")
  //  public String contestList(Model model) throws Exception {
  //    model.addAttribute("contests", portfolioService.list());
  //    return "contest/contestList";
  //  }
  //
  //  @ResponseBody
  //  @PostMapping("contestTeam/detail")
  //  public Contest contestTeamDetail(int contestNumber) throws Exception {
  //    Contest contest = portfolioService.get(contestNumber);
  //    return contest;
  //  }
  //

  //

  //
  //

  //
  //  @GetMapping("fileDelete")
  //  public String fileDelete(
  //      int ctstFno,
  //      HttpSession session) 
  //          throws Exception {
  //
  //    ContestAttachedFile attachedFile = portfolioService.getAttachedFile(ctstFno); 
  //
  //    // Member loginMember = (Member) session.getAttribute("loginMember");
  //    Contest contest = portfolioService.get(attachedFile.getCtstNo()); 
  //
  //    //    if (contest.getWriter().getNo() != loginMember.getNo()) {
  //    //      throw new Exception("게시글 작성자가 아닙니다.");
  //    //    }
  //
  //    if (!portfolioService.deleteAttachedFile(ctstFno)) {
  //      throw new Exception("게시글 첨부파일을 삭제할 수 없습니다.");
  //    }
  //
  //    return "redirect:contestDetail?ctstNo=" + contest.getCtstNo();
  //  }
  //

  //
  //  private String saveAttachedFiles2(MultipartFile files)
  //      throws IOException, ServletException {
  //    List<ContestAttachedFile> attachedFiles = new ArrayList<>();
  //    String dirPath = sc.getRealPath("/contest/files");
  //
  //    String filename = UUID.randomUUID().toString();
  //    files.transferTo(new File(dirPath + "/" + filename));
  //
  //    return filename;
  //  }
  //
  //  private List<ContestAttachedFile> saveAttachedFiles(MultipartFile[] files)
  //      throws IOException, ServletException {
  //    List<ContestAttachedFile> attachedFiles = new ArrayList<>();
  //    String dirPath = sc.getRealPath("/contest/files");
  //
  //    for (MultipartFile part : files) {
  //      if (part.isEmpty()) {
  //        continue;
  //      }
  //
  //      String filepath = UUID.randomUUID().toString();
  //      String filename = part.getOriginalFilename();
  //      part.transferTo(new File(dirPath + "/" + filepath));
  //      attachedFiles.add(new ContestAttachedFile(filename, filepath));
  //
  //    }
  //    return attachedFiles;
  //  }

  //  private List<ContestAttachedFile> saveAttachedFiles(Part[] files)
  //      throws IOException, ServletException {
  //    List<ContestAttachedFile> attachedFiles = new ArrayList<>();
  //    String dirPath = sc.getRealPath("/contest/files");
  //
  //    for (Part part : files) {
  //      if (part.getSize() == 0) {
  //        continue;
  //      }
  //
  //      String filename = UUID.randomUUID().toString();
  //      part.write(dirPath + "/" + filename);
  //      attachedFiles.add(new ContestAttachedFile(filename));
  //    }
  //    return attachedFiles;
  //  }




  //  private void checkOwner(int contestNo, HttpSession session) throws Exception {
  //    Member loginMember = (Member) session.getAttribute("loginMember");
  //    if (portfolioService.get(contestNo).getWriter().getNo() != loginMember.getNo()) {
  //      throw new Exception("게시글 작성자가 아닙니다.");
  //    }
  //  }

  //

}






