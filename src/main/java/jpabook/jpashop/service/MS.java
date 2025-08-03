package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MR;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service @Transactional(readOnly = true)
@RequiredArgsConstructor
public class MS {private final MR mr;@Transactional //변경 join 만,
public Long join(Member member) {
valDupl(member);    mr.save(member);   return member.getId();    }
private void valDupl(Member member) {
List<Member> findMembers = mr.findByName(member.getName());
if (!findMembers.isEmpty()){
throw new IllegalStateException("이미 존재하는 회원입니다.");} }
//@Transactional(readOnly = true) 조회용 성능향상 아래 두건 다수라서, 전체 적용.
public List<Member> findMembers() { return mr.findAll();  }
public Member findOne(Long memberId) { return mr.findById(memberId).get(); } // .findOne(memberId); }
@Transactional public void update(Long id, String name) {
Member member =mr.findById(id).get();/*.findOne(id);*/member.setName(name);}}













