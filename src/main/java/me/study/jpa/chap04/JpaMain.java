package me.study.jpa.chap04;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("test");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            // 저장
            Team team = new Team();
            team.setName("TeamA");
            em.persist(team);

            Member member = new Member();
            member.setUsername("member1");
            member.changeTeam(team);
            em.persist(member);

            for (Member m : team.getMembers()) {
                System.out.println("m = " + m.getUsername());
            }

            em.flush();
            em.clear();

            // 조회
            Member findMember = em.find(Member.class, member.getId());
            Team findTeam = findMember.getTeam();

            System.out.println("findTeam.name = " + findTeam.getName());

            // 수정
            Team teamB = new Team();
            teamB.setName("TeamB");
            em.persist(teamB);

            findMember.changeTeam(teamB);

            em.flush();
            em.clear();

            // 조회
            Member findMember2 = em.find(Member.class, member.getId());
            List<Member> members = findMember2.getTeam().getMembers();

            for (Member m : members) {
                System.out.println("m = " + m.getUsername());
            }

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}
