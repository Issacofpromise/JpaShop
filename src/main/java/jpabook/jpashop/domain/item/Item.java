package jpabook.jpashop.domain.item;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity @Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype") @Getter @Setter
public abstract class Item { @Id @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "item_seq")
@SequenceGenerator(name = "item_seq", sequenceName = "item_seq", allocationSize = 1)
@Column(name = "item_id")
    private Long id; private String name; private int price; private int stockQuantity;
    @ManyToMany(mappedBy = "items")
    private List<Category> categories = new ArrayList<>(); //==비즈니스 로직==//
    public void addStock(int quantity) {  this.stockQuantity += quantity;  }

    public void change(String name, int price, int stockQuantity) {
        this.setName(name); this.setPrice(price);
        this.setStockQuantity(stockQuantity);   }
    public String removeStock(int quantity) {
        int restStock = this.stockQuantity - quantity;
if (restStock < 0) { return "재고 부족! 남은 재고: " + this.stockQuantity; }//throw new NotEnoughStockException("need more stock"); }
this.stockQuantity = restStock; return "재고 차감 완료! 남은 재고: " + this.stockQuantity; }
}







