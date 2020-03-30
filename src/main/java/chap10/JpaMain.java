package chap10;

import org.hibernate.Hibernate;

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
            Member member = new Member();
            member.setUsername("member1");
            em.persist(member);

            em.flush();
            em.clear();

//            Member findMember = em.find(Member.class, member.getId());
            Member findMember = em.getReference(Member.class, member.getId());
            System.out.println("before findMember = " + findMember.getClass());
            System.out.println("====================");
            System.out.println("findMember.id = " + findMember.getId());
            System.out.println("====================");
            System.out.println("findMember.username = " + findMember.getUsername());
            System.out.println("====================");
            System.out.println("after findMember = " + findMember.getClass());
            System.out.println("====================");

            em.clear();

            Member m1 = em.find(Member.class, member.getId());
            System.out.println("m1 = " + m1.getClass());

            Member m2 = em.getReference(Member.class, member.getId());
            System.out.println("m2 = " + m2.getClass());

            System.out.println("m1 == m2: " + (m1 == m2));

            em.clear();

            Member m3 = em.getReference(Member.class, member.getId());
            System.out.println("m3 = " + m3.getClass());
            System.out.println("isLoaded = " + emf.getPersistenceUnitUtil().isLoaded(m3));

            Member m4 = em.find(Member.class, member.getId());
            System.out.println("m4 = " + m4.getClass());
            System.out.println("isLoaded = " + emf.getPersistenceUnitUtil().isLoaded(m3));

            System.out.println("m3 == m4: " + (m3 == m4));

            em.clear();

            Member m5 = em.getReference(Member.class, member.getId());
            System.out.println("isLoaded = " + emf.getPersistenceUnitUtil().isLoaded(m5));
            Hibernate.initialize(m5);
            System.out.println("isLoaded = " + emf.getPersistenceUnitUtil().isLoaded(m5));

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
