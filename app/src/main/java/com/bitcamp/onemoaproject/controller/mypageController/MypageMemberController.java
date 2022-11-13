package com.bitcamp.onemoaproject.controller.mypageController;

import javax.servlet.http.HttpSession;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.bitcamp.onemoaproject.service.MemberService;
import com.bitcamp.onemoaproject.vo.Member;

@Controller
@RequestMapping("mypage")
public class MypageMemberController {

  MemberService memberService;

  public MypageMemberController(MemberService memberService) {
    System.out.println("MemberController() 호출됨!");
    this.memberService = memberService;
  }

  @GetMapping("changepw")
  public void changepw() throws Exception {
  }

  @PostMapping("checkpassword")
  public String checkpassword(String password, String newPassword, 
      String newPasswordConfirm, HttpSession session) throws Exception {

    // 1. 현재 비밀번호 맞는지 체크
    Member loginMember = (Member) session.getAttribute("loginMember");
    String email = loginMember.getEmail();
    Member member = memberService.get(loginMember.getNo());
    String pw = member.getPassword();
    System.out.println(pw);

    boolean isPasswdRight = BCrypt.checkpw(password, member.getPassword());

    System.out.println(isPasswdRight);

    if(!isPasswdRight) { // 현제 비밀번호가 일치하기않으면
      throw new Exception("현제 패스워드가 일치하지 않습니다.");
    }

    // 2. 새 비밀번호, 새비밀번호 확인 맞는지 체크
    if (newPassword.equals(newPasswordConfirm) == false) { // 새 비밀번호와 새 비밀번호 확인이 일치하기않으면
      throw new Exception("새 비밀번호와 새 비밀번호 확인이 서로 일치하지 않습니다.");
    }

    // 3. DB 비밀번호 변경
    // 3-1. 비밀번호 암호화
    String hashPasswd = BCrypt.hashpw(newPassword, BCrypt.gensalt());
    memberService.modifyPasswd(email, hashPasswd);


    return "redirect:/";
    // 4. 비밀번호 완료 메시지 띄우고 로그아웃 처리   

  }

  @GetMapping("myinfoResult")
  public void myinfoResult() throws Exception {
  }

  @GetMapping("leave")
  public void leave() throws Exception {
  }

  @GetMapping("leaveHere")
  public String leaveHere(HttpSession session) throws Exception {
    Member loginMember = (Member) session.getAttribute("loginMember");
    memberService.updateStatus(loginMember.getEmail());

    return "redirect:leaveResult";
  }

  @GetMapping("leaveResult")
  public void leaveResult() throws Exception {
  }



  @GetMapping("myinfo")
  public void myinfo(HttpSession session, Model model) throws Exception {
    Member loginMember = (Member) session.getAttribute("loginMember");
    model.addAttribute("member", memberService.get(loginMember.getNo()));
    model.getAttribute("member");
    System.out.println(model.getAttribute("member"));

  }

  @PostMapping("myinfoUpdate")
  public String myinfoUpdate(Member member) throws Exception {
    if (!memberService.myinfoUpdate(member)) {
      throw new Exception("회원 변경 오류입니다!");
    }

    return "redirect:myinfoResult";
  }

}






