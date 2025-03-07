
package jpabook.jpashop.api;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.service.MS;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;
@RestController @RequiredArgsConstructor
public class MemberApiController {private final MS ms;
@PostMapping("/api/v1/members")
public CreateMemberResponse saveMemberV1(@RequestBody @Valid Member member) {
Long id = ms.join(member);return new CreateMemberResponse(id);}
@PostMapping("/api/v2/members")
public CreateMemberResponse saveMemberV2(@RequestBody @Valid CreateMemberRequest request) {
Member member = new Member();member.setName(request.getName());Long id = ms.join(member);
return new CreateMemberResponse(id);}
@PutMapping("/api/v2/members/{id}")
public UpdateMemberResponse updateMemberV2(@PathVariable("id") Long id,
@RequestBody @Valid UpdateMemberRequest request) {//{"name":"MyL-5"} JSON>DTO Mapping.
ms.update(id, request.getName());Member findMember = ms.findOne(id);
return new UpdateMemberResponse(findMember.getId(), findMember.getName());}
//조회 V1: 안 좋은 버전, 모든 엔티티가 노출, @JsonIgnore -> 이건 정말 최악, api가 이거 하나인가! 화면에 종속적이지 마라!
@GetMapping("/api/v1/members") public List<Member> membersV1() {return ms.findMembers();}//List<Member>확장성X
//조회 V2: 응답 값으로 엔티티가 아닌 별도의 DTO를 반환한다.
@GetMapping("/api/v2/members") public Result membersV2() {List<Member> findMembers =
ms.findMembers();List<MemberDto> collect = findMembers.stream()
.map(m -> new MemberDto(m.getName()))
.collect(Collectors.toList());return new Result(collect.size(),collect);//static class MemberApiController 내부서만 사용 가능
} @Data @AllArgsConstructor static class Result<T> { private int count; private T data;}// Result 클래스로 컬렉션을 감싸서 향후 필드 추가 확장성.
@Data @AllArgsConstructor static class MemberDto { private String name;}
@Data static class UpdateMemberRequest { private String name;}@Data @AllArgsConstructor
static class UpdateMemberResponse { private Long id;private String name;}
@Data static class CreateMemberRequest { @NotEmpty private String name;}@Data static class
CreateMemberResponse { private Long id;public CreateMemberResponse(
Long id){this.id = id;}}
}






















