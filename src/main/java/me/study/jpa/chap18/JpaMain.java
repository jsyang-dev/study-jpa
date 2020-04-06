package me.study.jpa.chap18;

import javax.persistence.*;
import java.util.List;

public class JpaMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("test");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {

            Member member = new Member();
            member.setUsername("member1");
            member.setAge(10);
            em.persist(member);

            em.flush();
            em.clear();

            TypedQuery<Member> query = em.createQuery("select m from Member as m", Member.class);
            TypedQuery<String> query2 = em.createQuery("select m.username from Member as m", String.class);
            Query query3 = em.createQuery("select m.username, m.age from Member as m");

            List<Member> resultList = query.getResultList();

            for (Member member1 : resultList) {
                System.out.println("member1 = " + member1.getUsername());
            }

            Member singleResult = em.createQuery("select m from Member as m where m.username = :username", Member.class)
                    .setParameter("username", "member1")
                    .getSingleResult();

            System.out.println("singleResult = " + singleResult.getUsername());

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
