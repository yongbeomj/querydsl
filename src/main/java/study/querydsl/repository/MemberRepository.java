package study.querydsl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import study.querydsl.entity.Member;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long>, MemberRepositoryCustom, QuerydslPredicateExecutor<Member> {

    List<Member> findByUsername(String username);

    /**
     * QuerydslPredicateExecutor 한계
     * - 조인 안됨 (left join 등)
     * - querydsl에 의존 (순수 dto 등을 전달해서 해결 불가)
     * - 실무에서 권장하지 않음 (보통 테이블 하나만 있는게 아니라 복잡하게 연결되어 있으므로)
     */


}
