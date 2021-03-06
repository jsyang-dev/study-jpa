package me.study.jpa.chap01;

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
            Member newMember = new Member();
            newMember.setId(1L);
            newMember.setName("HelloA");
            em.persist(newMember);

            Member updateMember = em.find(Member.class, 1L);
            updateMember.setName("HelloJPA");

            List<Member> result = em.createQuery("select m from MemberSQ as m", Member.class).getResultList();

            for (Member member : result) {
                System.out.println("member.name = " + member.getName());
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
