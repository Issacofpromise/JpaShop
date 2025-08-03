package jpabook.jpashop.repository;

import jakarta.persistence.EntityManager;
import jpabook.jpashop.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Repository @RequiredArgsConstructor @Transactional
public class MRold {  private final EntityManager em;
//public MR(EntityManager em) { this.em = em; }
public Long join(Member member) {
valDupl(member);   save(member);   return member.getId();    }
private void valDupl(Member member) {
List<Member> findMembers =    findByName(member.getName());
if (!findMembers.isEmpty()) {
throw new IllegalStateException("이미 존재하는 회원입니다.");} }
public void save(Member member) {  em.persist(member);  }
public Member findOne(Long id) { return em.find(Member.class, id); }
public List<Member> findAll() {
return em.createQuery("select m from Member m", Member.class).getResultList();
/*List<Member> rst= em.createQuery("select m from Member m", Member.class)
.getResultList(); return rst; / ctr+alt+n inline */}
public List<Member> findByName(String name) {
return em.createQuery("select m from Member m where m.name = :name", Member.class)
.setParameter("name", name).getResultList();
}}























