package jpabook.jpashop.web;

import jakarta.validation.Valid;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.repository.ItemRepository;
import jpabook.jpashop.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
@Controller @RequiredArgsConstructor
public class ItemController {
private final ItemRepository itemRepository;
private final ItemService itemService;
@GetMapping(value = "/items/new")
public String createForm(Model model) {
model.addAttribute("bookForm", new BookForm());
return "items/createItemForm";
}
@PostMapping(value = "/items/new")
public String create(@Valid BookForm form, BindingResult result) {
if (result.hasErrors()) { return "items/createItemForm";}
Book book = new Book(); book.setName(form.getName());
book.setPrice(form.getPrice()); book.setStockQuantity(form.getStockQuantity());
book.setAuthor(form.getAuthor()); book.setIsbn(form.getIsbn());
itemRepository.save(book); return "redirect:/";
} @GetMapping(value = "/items")
public String list(Model model) {
/* List<Member> members = ms.findMembers();
model.addAttribute("members", members);*/
model.addAttribute("items", itemRepository.findAll());
return "items/itemList";    }
@GetMapping(value = "/items/{itemId}/edit")
public String updateItemForm(@PathVariable("itemId") Long itemId,Model model){
Book item = (Book) itemRepository.findOne(itemId); BookForm form = new BookForm();
form.setId(item.getId()); form.setName(item.getName());
form.setPrice(item.getPrice()); form.setStockQuantity(item.getStockQuantity());
form.setAuthor(item.getAuthor()); form.setIsbn(item.getIsbn());
model.addAttribute("form", form);
return "items/updateItemForm";    }
@PostMapping(value = "/items/{itemId}/edit")   //@Transactional
public String updateItem(@ModelAttribute("form") BookForm form) {
Book book = new Book();   book.setId(form.getId());
book.setName(form.getName());  book.setPrice(form.getPrice());
book.setStockQuantity(form.getStockQuantity()); book.setAuthor(form.getAuthor());
book.setIsbn(form.getIsbn());/*book.setIsbn=null;*/itemRepository.save(book);
//itemService.updateItem(form.getId(), form.getName(),form.getPrice(),form.getStockQuantity());
//itemService.updateItem(form); Item item=itemRepository.findOne(form.getId());
//item.change(form.getName(),form.getPrice(),form.getStockQuantity());*/
return "redirect:/items";}
@PostMapping("/items/{itemId}/delete")
public String deleteItem(@PathVariable("itemId") Long itemId) {
itemRepository.deleteById(itemId);return "redirect:/items"; }
}








