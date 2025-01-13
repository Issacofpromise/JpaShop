package jpabook.jpashop.service;

import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service @Transactional(readOnly = true) @RequiredArgsConstructor
public class ItemService { //상품 서비스는 상품 리포지토리에 단순 위임만 하는 클래스
private final ItemRepository itemRepository;
//public ItemService(ItemRepository itemRepository) { this.itemRepository = itemRepository;}
@Transactional public void saveItem(Item item) { itemRepository.save(item); }@Transactional
//public void updateItem(ItemDto itemDto) {Item item = itemRepository.findOne(itemDto.getId());
public void updateItem(Long id, String name, int price,int stockQuantity) {
Item item = itemRepository.findOne(id);//  item.change(name,price,stockQuantity);
item.setName(name); item.setPrice(price);item.setStockQuantity(stockQuantity);
/*  public void updateItem(BookForm form) {Item item = itemRepository.findOne(
form.getId());item.setName(form.getName()); item.setPrice(form.getPrice());
item.setStockQuantity(form.getStockQuantity()); */ }public List<Item> findItems(){
return itemRepository.findAll(); }public Item findOne(Long itemId) {
return itemRepository.findOne(itemId);  }}





