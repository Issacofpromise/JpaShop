package jpabook.jpashop.web;

import jakarta.validation.Valid;
import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MR;
import jpabook.jpashop.service.MS;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller @RequiredArgsConstructor
public class MemberController {private final MR mr;private final MS ms;
@GetMapping(value = "/members/new")
public String createForm(Model model) {
model.addAttribute("memberForm", new MemberForm());
return "members/createMemberForm";}
@PostMapping(value = "/members/new")
public String create(@Valid MemberForm form, BindingResult result) {
if (result.hasErrors()) { return "members/createMemberForm";  }
Address address = new Address(form.getCity(),form.getStreet(),form.getZipcode());
Member member = new Member(); member.setName(form.getName());
member.setAddress(address); ms.join(member); return "redirect:/"; }
@GetMapping(value = "/members")
public String list(Model model) {/* List<Member> members = ms.findMembers();
model.addAttribute("members", members);*/
model.addAttribute("members", mr.findAll());
return "members/memberList"; }
}
