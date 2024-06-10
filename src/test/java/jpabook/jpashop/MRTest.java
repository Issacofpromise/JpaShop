package jpabook.jpashop;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.junit.jupiter.api.Test;
import org.springframework.transaction.annotation.Transactional;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class MRTest {
    @Autowired RM mr;
    @Test @Transactional   @Rollback(false)
    public void testMember() throws Exception {   Ber member = new Ber();
        member.setUsername("memberA");
        Long savedId = mr.save(member);
        Ber findMember = mr.find(savedId);
        Assertions.assertThat(findMember.getId()).isEqualTo(member.getId());
        Assertions.assertThat(findMember.getUsername()).isEqualTo(member.getUsername());
        Assertions.assertThat(findMember).isEqualTo(member); //원본과 조회한거와,
        System.out.println("findMember == member :" + (findMember == member));
        System.out.println("findMember:" + findMember);
        System.out.println("member :" + member);
    }
}
