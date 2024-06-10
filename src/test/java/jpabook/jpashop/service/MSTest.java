package jpabook.jpashop.service;
import jakarta.persistence.EntityManager;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MR;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest //@Transactional
class MSTest {
    @Autowired MS ms;
    @Autowired MR mr;
    @Autowired EntityManager em;
    @Test @Transactional @Rollback(value = false)
  public void 회원가입() throws Exception{ Member member = new Member();
        member.setName("dlal1010");
        Long saveId = ms.join(member); //em.flush();
        assertEquals(member, mr.findOne(saveId));
    }
    @Test @Commit   // @Rollback(value = false)
    //@Transactional(noRollbackFor = IllegalStateException.class)
    public void 중복_회원_예외() throws Exception {
        //Given
        Member member1 = new Member();  member1.setName("Park");
        Member member2 = new Member();  member2.setName("Park");
        //When
        ms.join(member1);
        assertThrows(IllegalStateException.class, () -> ms.join(member2) );
        //Then
       //fail("예외가 발생해야 한다.");
    }
}
