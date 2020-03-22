package chap03;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("test");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            MemberID memberID = new MemberID();
            memberID.setUsername("name");
            em.persist(memberID);
            System.out.println("memberID.id = " + memberID.getId());
            System.out.println("========== IDENTITY END ==========");

            MemberSQ memberSQ = new MemberSQ();
            memberSQ.setUsername("name");
            em.persist(memberSQ);
            System.out.println("memberSQ.id = " + memberSQ.getId());
            System.out.println("========== SEQUENCE END ==========");

            MemberTB memberTB = new MemberTB();
            memberTB.setUsername("name");
            em.persist(memberTB);
            System.out.println("memberTB.id = " + memberTB.getId());
            System.out.println("========== TABLE END ==========");

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}
