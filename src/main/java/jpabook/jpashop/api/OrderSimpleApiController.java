package jpabook.jpashop.api;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.repository.OrderRepository;
import jpabook.jpashop.repository.OrderSearch;
import jpabook.jpashop.repository.order.simplequery.OrderSimpleQueryDto;
import jpabook.jpashop.repository.order.simplequery.OrderSimpleQueryRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

/** * xToOne(ManyToOne, OneToOne) 관계 최적화
* Order -> Member
* Order -> Delivery */
@RestController
@RequiredArgsConstructor
public class OrderSimpleApiController {private final OrderRepository orderRepository;
private final OrderSimpleQueryRepository orderSimpleQueryRepository; //의존관계 주입
/** V1. 엔티티 직접 노출 - Hibernate5Module 모듈 등록, LAZY=null 처리-양방향 관계 문제 발생 -> @JsonIgnore */
@GetMapping("/api/v1/simple-orders")
public List<Order> ordersV1() {
List<Order> all = orderRepository.findAllByString(new OrderSearch());
for(Order order:all){order.getMember().getName();order.getDelivery().getAddress();}//Lazy 강제 초기화
return all;}/** V2. 엔티티를 조회해서 DTO로 변환(fetch join 사용X)
* - 단점: 지연로딩으로 쿼리 N번 호출*/
@GetMapping("/api/v2/simple-orders")
public List<SimpleOrderDto> ordersV2() {/*
List<Order> orders = orderRepository.findAllByString(new OrderSearch());;
List<SimpleOrderDto> result = orders.stream()
.map(o -> new SimpleOrderDto(o))
.collect(Collectors.toList());return result;*/
return orderRepository.findAllByString(new OrderSearch()).stream()
.map(SimpleOrderDto::new)
.collect(toList());}/** V3. 엔티티를 조회해서 DTO로 변환(fetch join 사용O)
* - fetch join 로 초반에 연관 객체를 EAGER 호출로 다 불러옴. 그래서 초반에 쿼리 1회 호출. */
@GetMapping("/api/v3/simple-orders") public List<SimpleOrderDto> ordersV3() {
    return orderRepository.findAllWithMemberDelivery().stream()
    .map(o -> new SimpleOrderDto(o))
    .collect(toList());}/*DTO 직접 호출 은 화면API 스펙에 딱 맞춘 코드가 repository
에 fix 되어 들어가, 리포지토리를 엔티티로 받아, 여러형태의 API 화면 스펙 재상용성이 높은 Ver3
에 비해 고정적여서 화면API 스펙 ==  repository 1:1 로 만 사용될 수 있음.*/
@GetMapping("/api/v4/simple-orders")
public List<OrderSimpleQueryDto> ordersV4() {
    return orderSimpleQueryRepository.findOrderDtos();
}


@Data
static class SimpleOrderDto {

private Long orderId;
private String name;
private LocalDateTime orderDate; //주문시간
private OrderStatus orderStatus;
private Address address;

public SimpleOrderDto(Order order) {
orderId = order.getId();
name = order.getMember().getName();
orderDate = order.getOrderDate();
orderStatus = order.getStatus();
address = order.getDelivery().getAddress();}}}













