package jpabook.jpashop.service;
import jakarta.persistence.EntityManager;
import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.exception.NotEnoughStockException;
import jpabook.jpashop.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class) @SpringBootTest @Transactional
public class OrderServiceTest {
    @Autowired EntityManager em;
    @Autowired OrderService orderService;
    @Autowired OrderRepository orderRepository;
    @Test
    public void 상품주문() throws Exception {   //Given
        Member member = createMember();
        Book book = createBook("시골 JPA",10000,10);
        int orderCount = 2;
        //When
        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);
        //Then
        Order getOrder = orderRepository.findOne(orderId);
assertEquals(OrderStatus.ORDER,getOrder.getStatus(),"상품 주문시 상태는 ORDER");
assertEquals(1,getOrder.getOrderItems().size(),"주문한 상품 종류 수가 정확해야 한다.");
assertEquals(10000 * 2, getOrder.getTotalPrice(),"주문 가격은 가격 * 수량이다.");
assertEquals(8, book.getStockQuantity(),"주문 수량만큼 재고가 줄어야 한다.");
}
    @Test
    public void 상품주문_재고수량초과() throws Exception {
        Member member = createMember();
        Book book = createBook("JPA",20000,10);
        int orderCount = 11;
        assertThrows(NotEnoughStockException.class, () -> {
            orderService.order(member.getId(), book.getId(), orderCount);
        });//fail("재고 수량 부족 예외가 발생해야 한다.");
         }
    @Test
    public void 주문취소() throws Exception{
        Member member = createMember();
        Book book = createBook("시골 JPA",10000,10);
        int orderCount = 2;
        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);
        //When
        orderService.cancelOrder(orderId);
        //Then
        Order getOrder = orderRepository.findOne(orderId);
        assertEquals(OrderStatus.CANCEL, getOrder.getStatus(),"주문 취소시 상태는 CANCEL 이다.");
        assertEquals(10, book.getStockQuantity(),"주문이 취소된 상품은 그만큼 재고가 증가해야 한다."); }
    private Book createBook(String name, int price, int stockQuantity ){
        Book book = new Book(); book.setName(name);
        book.setStockQuantity(stockQuantity); book.setPrice(price);
        em.persist(book);  return book;  }
    private Member createMember() {
        Member member = new Member();  member.setName("회원1");
        member.setAddress(new Address("서울", "강가", "123-123"));
        em.persist(member);   return member;   }
}