


package jpabook.jpashop.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jpabook.jpashop.domain.item.Item;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Entity @Table(name = "order_item") @Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItem { @Id @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "order_item_seq")
@SequenceGenerator(name = "order_item_seq", sequenceName = "order_item_seq", allocationSize = 1)
@Column(name = "order_item_id") private Long id;
@ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "item_id")private Item item;
@JsonIgnore @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "order_id")
private Order order; private int orderPrice; private int count;
//protected OrderItem(){}
public void cancel() {getItem().addStock(count); }
public int getTotalPrice() { return getOrderPrice()*getCount(); }
public static OrderItem createOrderItem(Item item, int orderPrice, int
count) { OrderItem orderItem = new OrderItem();
orderItem.setItem(item); orderItem.setOrderPrice(orderPrice);
orderItem.setCount(count); item.removeStock(count);
return orderItem;   }
}





















