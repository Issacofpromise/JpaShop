package jpabook.jpashop;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
@Repository
public class RM {
    @PersistenceContext
    private EntityManager em;
    public Long save(Ber member) { em.persist(member);return member.getId(); }
    public Ber find(Long id) {    return em.find(Ber.class, id);  }
}
