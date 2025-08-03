package jpabook.jpashop;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManager;
import jpabook.jpashop.domain.*;
import jpabook.jpashop.domain.item.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component @RequiredArgsConstructor public class InitDb{private final InitService initService;
@PostConstruct public void init() {initService.dbInit1(); initService.dbInit2();}
@Component @Transactional @RequiredArgsConstructor static class InitService{private final
EntityManager em;public void dbInit1() {System.out.println("Init1" + this.getClass());
Member member = createMember("userA", "서울", "3로", "30092");
/*em.persist(member);*/Book book1 = createBook("JPA1 BOOK", 10000, 90);
/*em.persist(book1);*/Book book2 = createBook("JPA2 BOOK", 20000, 150);
/*em.persist(book2);*/OrderItem orderItem1 = OrderItem.createOrderItem(book1, 50000,
8);OrderItem orderItem2 = OrderItem.createOrderItem(book2, 20000, 2);
Delivery delivery = createDelivery(member);
Order order = Order.createOrder(member, delivery, orderItem1, orderItem2);
em.persist(order);} public void dbInit2() {
Member member = createMember("userB", "진주", "신로", "954");
Book book1 = createBook("SPRING1 BOOK", 30000, 200);
Book book2 = createBook("SPRING2 BOOK", 40000, 300);
OrderItem orderItem1=OrderItem.createOrderItem(book1,70000,5);
OrderItem orderItem2 = OrderItem.createOrderItem(book2, 40000, 4);
Delivery delivery = createDelivery(member);
Order order = Order.createOrder(member, delivery, orderItem1, orderItem2);em.persist(order);
} private Book createBook(String name, int price, int stockQuantity) {
Book book1 = new Book();book1.setName(name);book1.setPrice(price);book1.setStockQuantity(
stockQuantity);em.persist(book1);return book1;}
private Member createMember(String name, String city, String street, String zipcode) {
Member member = new Member();member.setName(name);member.setAddress(
new Address(city, street, zipcode));em.persist(member);return member;}
private Delivery createDelivery(Member member){
Delivery delivery = new Delivery();delivery.setAddress(member.getAddress());return delivery;}
}}



















