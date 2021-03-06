package me.study.jpa.chap24;

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

            Team teamA = new Team();
            teamA.setName("팀A");
            em.persist(teamA);

            Team teamB = new Team();
            teamB.setName("팀B");
            em.persist(teamB);

            Team teamC = new Team();
            teamC.setName("팀C");
            em.persist(teamC);

            Member member1 = new Member();
            member1.setUsername("회원1");
            member1.setTeam(teamA);
            em.persist(member1);

            Member member2 = new Member();
            member2.setUsername("회원2");
            member2.setTeam(teamA);
            em.persist(member2);

            Member member3 = new Member();
            member3.setUsername("회원3");
            member3.setTeam(teamB);
            em.persist(member3);

            Member member4 = new Member();
            member4.setUsername("회원4");
            em.persist(member4);

            em.flush();
            em.clear();

            String query = "select m from Member m join fetch m.team";
            List<Member> resultList = em.createQuery(query, Member.class)
                    .getResultList();

            for (Member member : resultList) {
                System.out.println("member = " + member.getUsername() + ", " + member.getTeam().getName());
            }

            em.clear();

            String query2 = "select distinct t from Team t join fetch t.members";
            List<Team> resultList2 = em.createQuery(query2, Team.class)
                    .getResultList();

            for (Team team : resultList2) {
                System.out.println("team = " + team.getName() + ", " + team.getMembers().size());
                for (Member member : team.getMembers()) {
                    System.out.println("-> member = " + member);
                }
            }

            em.clear();

            String query3 = "select t from Team t";
            List<Team> resultList3 = em.createQuery(query3, Team.class)
                    .setFirstResult(0)
                    .setMaxResults(2)
                    .getResultList();

            for (Team team : resultList3) {
                System.out.println("team = " + team.getName() + ", " + team.getMembers().size());
                for (Member member : team.getMembers()) {
                    System.out.println("-> member = " + member);
                }
            }

            em.clear();

            String query4 = "select count(m) from Member m";
            Long result = em.createQuery(query4, Long.class)
                    .getSingleResult();

            System.out.println("result = " + result);

            em.clear();

            String query5 = "select m from Member m where m = :member";
            Member findMember = em.createQuery(query5, Member.class)
                    .setParameter("member", member1)
                    .getSingleResult();

            System.out.println("member = " + findMember);

            em.clear();

            String query6 = "select m from Member m where m.team = :team";
            List<Member> resultList4 = em.createQuery(query6, Member.class)
                    .setParameter("team", teamA)
                    .getResultList();

            for (Member member : resultList4) {
                System.out.println("member = " + member);
            }

            em.clear();

            List<Member> resultList5 = em.createNamedQuery("Member.findByUsername", Member.class)
                    .setParameter("username", "회원1")
                    .getResultList();

            for (Member member : resultList5) {
                System.out.println("member = " + member);
            }

            em.clear();

            int resultCount = em.createQuery("update Member m set m.age = 20")
                    .executeUpdate();

            System.out.println("resultCount = " + resultCount);

            member1 = em.find(Member.class, member1.getId());
            System.out.println("member1 = " + member1);
            
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
