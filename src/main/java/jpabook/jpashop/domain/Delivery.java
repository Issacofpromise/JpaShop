package jpabook.jpashop.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity @Getter @Setter
public class Delivery {  @Id @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "delivery_seq")
@SequenceGenerator(name = "delivery_seq", sequenceName = "delivery_seq", allocationSize = 1)
@Column(name = "delivery_id")
    private Long id;
    @JsonIgnore
    @OneToOne(mappedBy = "delivery", fetch = FetchType.LAZY)
    private Order order;
    @Embedded  private Address address ;
    @Enumerated(EnumType.STRING)
    private DeliveryStatus status; //ENUM [READY(준비), COMP(배송)]
}
















