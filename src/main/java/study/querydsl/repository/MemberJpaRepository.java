package study.querydsl.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import study.querydsl.entity.Member;
import study.querydsl.entity.QMember;

import java.util.List;
import java.util.Optional;

import static study.querydsl.entity.QMember.member;

@Repository
public class MemberJpaRepository {

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    // JPAQueryFactory 인스턴스 생성
    // 주입받는게 1개이므로 테스트코드 작성 시 조금 편함
    // @RequiredArgsConstructor 사용 불가
    public MemberJpaRepository(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

    // spring 시작 지점에 bean 등록 후 받아오기
    // @RequiredArgsConstructor 사용 가능
    // 테스트코드가 조금 더 복잡해짐
//    public MemberJpaRepository(EntityManager em, JPAQueryFactory jpaQueryFactory) {
//        this.em = em;
//        this.queryFactory = jpaQueryFactory;
//    }

    public void save(Member member) {
        em.persist(member);
    }

    public Optional<Member> findById(Long id) {
        Member findMember = em.find(Member.class, id);
        return Optional.ofNullable(findMember);
    }

    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }

    public List<Member> findAll_Querydsl() {
        return queryFactory
                .selectFrom(member)
                .fetch();
    }

    public List<Member> findByUsername(String username) {
        return em.createQuery("select m from Member m where m.username = :username", Member.class)
                .setParameter("username", username)
                .getResultList();
    }

    public List<Member> findByUsername_Querydsl(String username) {
        return queryFactory
                .selectFrom(member)
                .where(member.username.eq(username))
                .fetch();
    }
}
