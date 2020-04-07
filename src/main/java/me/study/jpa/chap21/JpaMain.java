package me.study.jpa.chap21;

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

            Team team = new Team();
            team.setName("TeamA");
            em.persist(team);

            Member member = new Member();
            member.setUsername("TeamA");
            member.setAge(10);
            member.changeTeam(team);

            em.persist(member);

            em.flush();
            em.clear();

            String query = "select m from Member as m inner join m.team t";
            List<Member> resultList = em.createQuery(query, Member.class)
                    .getResultList();

            for (Member m : resultList) {
                System.out.println("m = " + m.toString());
            }

            String query2 = "select m from Member as m left outer join m.team t";
            List<Member> resultList2 = em.createQuery(query2, Member.class)
                    .getResultList();

            for (Member m : resultList2) {
                System.out.println("m = " + m.toString());
            }

            String query3 = "select m from Member as m, Team as t where m.username = t.name";
            List<Member> resultList3 = em.createQuery(query3, Member.class)
                    .getResultList();

            for (Member m : resultList3) {
                System.out.println("m = " + m.toString());
            }

            String query4 = "select m from Member as m left join m.team as t on t.name = 'TeamA'";
            List<Member> resultList4 = em.createQuery(query4, Member.class)
                    .getResultList();

            for (Member m : resultList4) {
                System.out.println("m = " + m.toString());
            }

            String query5 = "select m from Member as m left join Team as t on m.username = t.name";
            List<Member> resultList5 = em.createQuery(query5, Member.class)
                    .getResultList();

            for (Member m : resultList5) {
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
