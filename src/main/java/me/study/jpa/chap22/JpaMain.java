package me.study.jpa.chap22;

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
            member.setUsername("관리자");
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

            em.flush();
            em.clear();

            String query = "select m.username, 'HELLO', true " +
                            "from Member as m " +
                            "where m.memberType = :memberType";
            List<Object[]> resultList = em.createQuery(query)
                    .setParameter("memberType", MemberType.ADMIN)
                    .getResultList();

            for (Object[] objects : resultList) {
                System.out.println("objects[0] = " + objects[0]);
                System.out.println("objects[1] = " + objects[1]);
                System.out.println("objects[2] = " + objects[2]);
            }

            String query2 =
                    "select " +
                        "case when m.age <= 10 then '학생요금' " +
                            "when m.age >= 60 then '경로요금' " +
                            "else '일반요금' " +
                        "end " +
                    "from Member m";
            List<String> resultList2 = em.createQuery(query2, String.class)
                    .getResultList();

            for (String s : resultList2) {
                System.out.println("s = " + s);
            }

            String query3 = "select coalesce(m.username, '이름 없는 회원') from Member m";
            List<String> resultList3 = em.createQuery(query3, String.class)
                    .getResultList();

            for (String s : resultList3) {
                System.out.println("s = " + s);
            }

            String query4 = "select nullif(m.username, '관리자') from Member m";
            List<String> resultList4 = em.createQuery(query4, String.class)
                    .getResultList();

            for (String s : resultList4) {
                System.out.println("s = " + s);
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
