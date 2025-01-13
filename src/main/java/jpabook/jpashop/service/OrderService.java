package jpabook.jpashop.service;

import jpabook.jpashop.domain.*;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.ItemRepository;
import jpabook.jpashop.repository.MR;
import jpabook.jpashop.repository.OrderRepository;
import jpabook.jpashop.repository.OrderSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service @Transactional(readOnly = true) @RequiredArgsConstructor
public class OrderService { private final MR mr;
    private final OrderRepository orderRepository;
    private final ItemRepository itemRepository;
    @Transactional
    public Long order(Long memberId, Long itemId, int count) {
        Member member = mr.findOne(memberId);
        Item item = itemRepository.findOne(itemId);
        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());
        delivery.setStatus(DeliveryStatus.READY);
OrderItem orderItem = OrderItem.createOrderItem(item,item.getPrice(),count);
//OrderItem orderItem1=new OrderItem();
  Order order = Order.createOrder(member, delivery, orderItem);
//new Order(); 정해진 형식 외 @NoArgsConstructor(access = AccessLevel.PROTECTED)
        orderRepository.save(order);   return order.getId();    }
    @Transactional
    public void cancelOrder(Long orderId) {
Order order = orderRepository.findOne(orderId); order.cancel();  }
 public List<Order> findOrders(OrderSearch orderSearch) {
 return orderRepository.findAllByString(orderSearch); }
}















