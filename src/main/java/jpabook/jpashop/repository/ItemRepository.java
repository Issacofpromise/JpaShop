package jpabook.jpashop.repository;
import jakarta.persistence.EntityManager;
import jpabook.jpashop.domain.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Repository @RequiredArgsConstructor @Transactional
public class ItemRepository { private final EntityManager em;
public void save(Item item) {  if (item.getId() == null) { em.persist(item);
} else {  Item merge = em.merge(item); // getId()(item)준영속, merge는 영속.
}} public Item findOne(Long id) { return em.find(Item.class, id);  }
public List<Item> findAll() {
return em.createQuery("select i from Item i",Item.class).getResultList();
}
public void deleteById(Long id) {Item item = findOne(id);if (item != null)em.remove(item);
}
}




























