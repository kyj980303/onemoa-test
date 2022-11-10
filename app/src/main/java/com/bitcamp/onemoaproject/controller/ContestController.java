package com.bitcamp.onemoaproject.controller;

import javax.servlet.ServletContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
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

  @ResponseBody
  @PostMapping("contestTeam/detail")
  public Contest contestTeamDetail(int contestNumber) throws Exception {
    Contest contest = contestService.get(contestNumber);
    return contest;
  }


}






