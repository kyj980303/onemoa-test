package com.bitcamp.onemoaproject.controller.AdminController;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import com.bitcamp.onemoaproject.service.ContestService;
import com.bitcamp.onemoaproject.vo.contest.Contest;
import com.bitcamp.onemoaproject.vo.contest.ContestAttachedFile;

@Controller
@RequestMapping("admin/contest")
public class AdminContestController {

  ServletContext sc;
  ContestService contestService;

  public AdminContestController(ContestService contestService, ServletContext sc) {
    System.out.println("ContestController() 호출됨!");
    this.contestService = contestService;
    this.sc = sc;
  }

  @GetMapping("contestForm")
  public String contestForm() {
    return "admin/contest/contestForm";
  }

  @GetMapping("contestList")
  public String contestList(Model model) throws Exception {
    model.addAttribute("contests", contestService.list());

    return "admin/contest/contestList";
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

  @PostMapping("contestUpdate")
  public String update(
      Contest contest,
      MultipartFile[] files,
      MultipartFile files2,
      HttpSession session) 
          throws Exception {

    contest.setAttachedFiles(saveAttachedFiles(files));
    contest.setThumbNail(saveAttachedFiles2(files2));

    // checkOwner(contest.getNo(), session);

    if (!contestService.update(contest)) {
      throw new Exception("게시글을 변경할 수 없습니다!");
    }

    return "redirect:contestList";
  }


  @GetMapping("contestDelete")
  public String delete(
      int ctstNo, 
      HttpSession session) 
          throws Exception {

    // checkOwner(no, session);
    if (!contestService.delete(ctstNo)) {
      throw new Exception("게시글을 삭제할 수 없습니다.");
    }

    return "redirect:contestList";
  }

  @GetMapping("fileDelete")
  public String fileDelete(
      int ctstFno,
      HttpSession session) 
          throws Exception {

    ContestAttachedFile attachedFile = contestService.getAttachedFile(ctstFno); 

    // Member loginMember = (Member) session.getAttribute("loginMember");
    Contest contest = contestService.get(attachedFile.getCtstNo()); 

    //    if (contest.getWriter().getNo() != loginMember.getNo()) {
    //      throw new Exception("게시글 작성자가 아닙니다.");
    //    }

    if (!contestService.deleteAttachedFile(ctstFno)) {
      throw new Exception("게시글 첨부파일을 삭제할 수 없습니다.");
    }

    return "redirect:contestDetail?ctstNo=" + contest.getCtstNo();
  }

  @PostMapping("contestAdd") 
  public String add(
      Contest contest,
      MultipartFile[] files,
      MultipartFile files2,
      HttpSession session) throws Exception {

    contest.setAttachedFiles(saveAttachedFiles(files));
    contest.setThumbNail(saveAttachedFiles2(files2));

    contestService.add(contest);
    return "redirect:contestList";
  }

  private String saveAttachedFiles2(MultipartFile files)
      throws IOException, ServletException {
    List<ContestAttachedFile> attachedFiles = new ArrayList<>();
    String dirPath = sc.getRealPath("/contest/files");

    String filename = UUID.randomUUID().toString();
    files.transferTo(new File(dirPath + "/" + filename));

    return filename;
  }

  private List<ContestAttachedFile> saveAttachedFiles(MultipartFile[] files)
      throws IOException, ServletException {
    List<ContestAttachedFile> attachedFiles = new ArrayList<>();
    String dirPath = sc.getRealPath("/contest/files");

    for (MultipartFile part : files) {
      if (part.isEmpty()) {
        continue;
      }

      String filepath = UUID.randomUUID().toString();
      String filename = part.getOriginalFilename();
      part.transferTo(new File(dirPath + "/" + filepath));
      attachedFiles.add(new ContestAttachedFile(filename, filepath));

    }
    return attachedFiles;
  }

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
  //    if (contestService.get(contestNo).getWriter().getNo() != loginMember.getNo()) {
  //      throw new Exception("게시글 작성자가 아닙니다.");
  //    }
  //  }

  //

}






