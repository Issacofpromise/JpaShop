package jpabook.jpashop.domain;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;
@Entity @Getter @Setter
public class Member { @Id @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "member_seq")
@SequenceGenerator(name = "member_seq", sequenceName = "member_seq", allocationSize = 1)
@Column(name = "member_id")
    private Long id; private String name;
    @Embedded  private Address address;
    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<>();

/*    public String getName() {    return name;    }
    public void setName(String name) {  this.name = name; }
    public Address getAddress() {  return address;  }
    public void setAddress(Address address) {  this.address = address; }
    public List<Order> getOrders() {  return orders;    }
    public void setOrders(List<Order> orders) { this.orders = orders; }
    public Long getId() {  return id;    }
    public void setId(Long id) {   this.id = id;    }*/
}




