package jpabook.jpashop.domain;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.*;

@Entity @Table(name = "orders") @Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order { @Id @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "orders_seq")
@SequenceGenerator(name = "orders_seq", sequenceName = "orders_seq", allocationSize = 1)
@Column(name = "order_id")
    private Long id;
    @ManyToOne(fetch = LAZY) @JoinColumn(name = "member_id")
    private Member member; //주문 회원
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();
    @OneToOne(fetch = LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;  private LocalDateTime orderDate;
    @Enumerated(EnumType.STRING)
    private OrderStatus status; //주문상태 [ORDER, CANCEL]
    public static Order createOrder(Member member, Delivery delivery,
            OrderItem... orderItems  ) {  Order order = new Order();
        order.setMember(member);   order.setDelivery(delivery);
        for(OrderItem orderItem:orderItems){ order.addOrderItem(orderItem);}
        order.setStatus(OrderStatus.ORDER);
        order.setOrderDate(LocalDateTime.now());
        return order;    }
    //==연관관계 메서드== 양방향, 둘 중 컨트롤 하는쪽에,
    public void addOrderItem(OrderItem orderItem) {  this.orderItems.add(orderItem);
        orderItem.setOrder(this); }
    public void setMember(Member member) {this.member = member;
        member.getOrders().add(this);    } // 위를 밖에서 원래대로 하면, 아래,
/*    public static void main(String[] args) { Member member = new Member();
       Order order= new Order();
       member.getOrders().add(order);
       order.setMember(member);    } */
    public void setDelivery(Delivery delivery) { this.delivery = delivery;
        delivery.setOrder(this);    }
    //==비즈니스 로직==//
    public void cancel() {
        if (delivery.getStatus() == DeliveryStatus.COMP) {
            throw new IllegalStateException("이미 배송된 상품 취소 불가합니다.");  }
        this.setStatus(OrderStatus.CANCEL);
        for (OrderItem orderItem : orderItems) { orderItem.cancel();  } }
    public int getTotalPrice() {
   /*     int totalPrice = 0;    ver.1
        for (OrderItem orderItem : orderItems) { totalPrice += orderItem.getTotalPrice(); }
        return totalPrice; */
/*      int totalPrice = orderItems.stream().mapToInt(OrderItem::getTotalPrice).sum();
        return totalPrice;       ver.2   */
        return orderItems.stream().mapToInt(OrderItem::getTotalPrice).sum();
    }
}
