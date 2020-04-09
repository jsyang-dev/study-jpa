package me.study.jpa.chap23;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.Collection;
import java.util.List;

public class JpaMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("test");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {

            Team team = new Team();
            team.setName("TeamA");
            em.persist(team);

            Member member = new Member();
            member.setUsername("관리자1");
            member.setAge(10);
            member.setMemberType(MemberType.ADMIN);
            member.setTeam(team);
            em.persist(member);

            Member member2 = new Member();
            member2.setUsername(null);
            member2.setAge(66);
            member2.setMemberType(MemberType.USER);
            member2.setTeam(team);
            em.persist(member2);

            Member member3 = new Member();
            member3.setUsername("관리자2");
            member3.setAge(30);
            member3.setMemberType(MemberType.ADMIN);
            member3.setTeam(team);
            em.persist(member3);

            em.flush();
            em.clear();

            // 상태 필드
            String query = "select m from Member m";
            List<Member> resultList = em.createQuery(query, Member.class)
                    .getResultList();

            for (Member m : resultList) {
                System.out.println("m = " + m.toString());
            }

            // 단일 값 연관 경로
            String query2 = "select m.team from Member m";
            List<Team> resultList2 = em.createQuery(query2, Team.class)
                    .getResultList();

            for (Team t : resultList2) {
                System.out.println("t = " + t.toString());
            }

            // 컬렉션 값 연관 경로
            String query3 = "select t.members from Team t";
            Collection resultList3 = em.createQuery(query3, Collection.class)
                    .getResultList();

            for (Object o : resultList3) {
                System.out.println("o = " + o.toString());
            }

            String query4 = "select m from Team t join t.members m";
            List<Member> resultList4 = em.createQuery(query4, Member.class)
                    .getResultList();

            for (Member m : resultList4) {
                System.out.println("m = " + m.toString());
            }

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }

        emf.close();
    }
}
