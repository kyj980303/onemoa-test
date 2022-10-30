package com.bitcamp.onemoaproject.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.bitcamp.onemoaproject.service.MemberService;
import com.bitcamp.onemoaproject.vo.Member;

@Controller
public class AuthController {
  MemberService memberService;

  public AuthController(MemberService memberService) {
    this.memberService = memberService;
  }

  @GetMapping("index")
  public String form(@CookieValue(name = "email", defaultValue = "") String email,
      Model model) {
    model.addAttribute("email", email);
    return "index";
  }


  @ResponseBody
  @PostMapping("login")
  public String login(String email, String password, String saveEmail, HttpServletRequest request,
      HttpServletResponse response, HttpSession session) throws Exception {

    Member member = memberService.get(email, password);

    if (member != null) {
      session.setAttribute("loginMember", member);
    }

    Cookie cookie = new Cookie("email", email);
    if (saveEmail == null) {
      cookie.setMaxAge(0);
    } else {
      cookie.setMaxAge(60 * 60 * 24 * 7);
    }
    response.addCookie(cookie);

    if (member == null) {
      return "false";
    }
    return "true";
  }

  @GetMapping("logout")
  public String logout(HttpSession session) throws Exception {
    session.invalidate(); // 현재 세션을 무효화시킨다.
    return "redirect:/index";
  }


}
