package me.study.jpa.chap19;

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

            Member member = new Member();
            member.setUsername("member1");
            member.setAge(10);
            em.persist(member);

            em.flush();
            em.clear();

            List<Member> resultList = em.createQuery("select m from Member as m", Member.class)
                    .getResultList();
            Member findMember = resultList.get(0);
            findMember.setAge(20);

            List<Team> resultList2 = em.createQuery("select m.team from Member as m", Team.class)
                    .getResultList();

            List<Address> resultList3 = em.createQuery("select o.address from Order as o", Address.class)
                    .getResultList();

            List<MemberDTO> resultList4 = em.createQuery("select distinct new me.study.jpa.chap19.MemberDTO(m.username, m.age) from Member as m", MemberDTO.class)
                    .getResultList();
            for (MemberDTO memberDTO : resultList4) {
                System.out.println("memberDTO = " + memberDTO.getUsername());
                System.out.println("memberDTO = " + memberDTO.getAge());
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
