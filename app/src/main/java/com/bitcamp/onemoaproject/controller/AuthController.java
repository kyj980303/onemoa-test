package com.bitcamp.onemoaproject.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import com.bitcamp.onemoaproject.service.MemberService;
import com.bitcamp.onemoaproject.vo.Member;


@Controller 
public class AuthController {

  MemberService memberService;

  public AuthController(MemberService memberService) {
    System.out.println("AuthController() 호출됨!");
    this.memberService = memberService;
  }

  @PostMapping("/login") // 응답 url
  public ModelAndView login(String email, String password, String saveEmail, HttpServletResponse response,
      HttpSession session) throws Exception {

    Member member = memberService.get(email, password);
    System.out.println(member + "<-- result adminCheck() AuthController.java"); 

    if (member != null) {
      session.setAttribute("loginMember", member); 
    }

    Cookie cookie = new Cookie("email", email); 
    if (saveEmail == null) {
      cookie.setMaxAge(0); 
    } else {
      cookie.setMaxAge(60 * 60 * 24 * 7); // 7일
    }
    response.addCookie(cookie); 
    ModelAndView mv = new ModelAndView("redirect:/");
    mv.addObject("member", member);
    return mv;
  }


  @GetMapping("logout") 
  public String logout(HttpSession session) throws Exception {
    session.invalidate(); 
    return "redirect:/"; 
  }
}






