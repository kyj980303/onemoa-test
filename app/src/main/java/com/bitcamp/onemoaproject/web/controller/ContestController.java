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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import com.bitcamp.onemoaproject.service.ContestService;
import com.bitcamp.onemoaproject.vo.contest.Contest;

@Controller
@RequestMapping("contest")
public class ContestController {

  ServletContext sc;
  ContestService contestService;

  public ContestController(ContestService contestService, ServletContext sc) {
    System.out.println("ContestController() 호출됨!");
    this.contestService = contestService;
    this.sc = sc;
  }

  @GetMapping("contestTeam")
  public String list(Model model) throws Exception {
    model.addAttribute("contests", contestService.list());

    return "contest/contestTeam";
  }

  @GetMapping("contestList")
  public String contestList(Model model) throws Exception {
    model.addAttribute("contests", contestService.list());
    return "contest/contestList";
  }

  @ResponseBody
  @PostMapping("contestTeam/detail")
  public Contest contestTeamDetail(int contestNumber) throws Exception {
    Contest contest = contestService.get(contestNumber);
    return contest;
  }

  @GetMapping("contestDetail")
  public Map contestDetail(int ctstNo) throws Exception {
    Contest contest = contestService.get(ctstNo);
    if (contest == null) {
      throw new Exception("해당 번호의 게시글이 없습니다!");
    }

    Map map = new HashMap();
    map.put("contest", contest);
    return map;
  }


  @PostMapping("add") 
  public String add(
      Contest contest,
      MultipartFile[] files,
      HttpSession session) throws Exception {

    //contest.setAttachedFiles(saveAttachedFiles(files));

    contestService.add(contest);
    return "redirect:contestTeam";
  }

  //  private List<ContestAttachedFile> saveAttachedFiles(MultipartFile[] files)
  //      throws IOException, ServletException {
  //    List<ContestAttachedFile> attachedFiles = new ArrayList<>();
  //    String dirPath = sc.getRealPath("/board/files");
  //
  //    for (MultipartFile part : files) {
  //      if (part.isEmpty()) {
  //        continue;
  //      }
  //
  //      String filename = UUID.randomUUID().toString();
  //      part.transferTo(new File(dirPath + "/" + filename));
  //      attachedFiles.add(new ContestAttachedFile(filename));
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


  //
  //  @PostMapping("update")
  //  public String update(
  //      Contest contest,
  //      Part[] files,
  //      HttpSession session) 
  //          throws Exception {
  //
  //    // contest.setAttachedFiles(saveAttachedFiles(files));
  //
  //    // checkOwner(contest.getNo(), session);
  //
  //    if (!contestService.update(contest)) {
  //      throw new Exception("게시글을 변경할 수 없습니다!");
  //    }
  //
  //    return "redirect:list";
  //  }

  //  private void checkOwner(int contestNo, HttpSession session) throws Exception {
  //    Member loginMember = (Member) session.getAttribute("loginMember");
  //    if (contestService.get(contestNo).getWriter().getNo() != loginMember.getNo()) {
  //      throw new Exception("게시글 작성자가 아닙니다.");
  //    }
  //  }

  //  @GetMapping("delete")
  //  public String delete(
  //      int no, 
  //      HttpSession session) 
  //          throws Exception {
  //
  //    checkOwner(no, session);
  //    if (!contestService.delete(no)) {
  //      throw new Exception("게시글을 삭제할 수 없습니다.");
  //    }
  //
  //    return "redirect:list";
  //  }
  //
  //  @GetMapping("fileDelete")
  //  public String fileDelete(
  //      int no,
  //      HttpSession session) 
  //          throws Exception {
  //
  //    AttachedFile attachedFile = contestService.getAttachedFile(no); 
  //
  //    Member loginMember = (Member) session.getAttribute("loginMember");
  //    Contest contest = contestService.get(attachedFile.getContestNo()); 
  //
  //    if (contest.getWriter().getNo() != loginMember.getNo()) {
  //      throw new Exception("게시글 작성자가 아닙니다.");
  //    }
  //
  //    if (!contestService.deleteAttachedFile(no)) {
  //      throw new Exception("게시글 첨부파일을 삭제할 수 없습니다.");
  //    }
  //
  //    return "redirect:detail?no=" + contest.getNo();
  //  }
}






