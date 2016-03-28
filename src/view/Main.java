package view;

import domain.Student;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

/**
 * Created by xcl on 16-3-28.
 */

public class Main {


    private Configuration conf=null;
    private SessionFactory sf=null;
    private Session s=null;
    private Transaction trans=null;


    //configuration是一个类
    /*
    * 起作用是读取hibbernate.cfg.xml
    * 负责管理对象关系映射文件
    * 加载配置文件中配置的资源
    * */
    //session,transaction是一个接口
    /*
    * 在configure创建好之后
    * */
    /*
    * sessionFactory在session之后
    * */

    public Main(){
        conf=new Configuration().configure("config/hibernate.cfg.xml");
        System.out.println("configuration的类型是:"+conf);
        sf=conf.buildSessionFactory();
        System.out.println("sessionFactory的类型是:"+sf);
        /*
        * sessionFactory的作用是:
        * 1缓存sql语句的某些数据
        * 2程序初始化时创建,是一个重量级的类,需要保证一个数据库有一个sessionFactory即可
        *
        *
        * */
        s=sf.openSession();

        /*
        * SessionFactory获取session的两个方法
        * 1openSession
        * 是获取一个新的会话
        * openSession在查询的时候不必要做成事物
        * 2getCurrentSession
        * 0getCurrentSession在查询的时候必须要做成事物
        *获取和当前线程绑定的session
        * 如果希望使用getCurrentSession，我们需要做一个配置，在hibernate.cfg.xml文件配置
        * ３如果需要在同一个线程中使用同一个session就是用getCurrentSession
        * 4如果在同一个线程中希望使用不同的session就用oepnSession
        * 5采用getCurrentSession创建的会话在commit和rollback的时候可以自动关闭
        * 而openSession必须手动关闭
        * 6使用getCurrentSession需要将currentSession在配置文件中绑定到当前线程中
        * */
    }

    public void doWork(){
        //使用getCurrentSession执行一个查询
        Session session=sf.getCurrentSession();
        Transaction transaction=session.beginTransaction();//getCurrentSession获取的当前会话除了需要配置之外，其所有操作都需要使用事务，包括查询
        Student stu=session.load(Student.class,"0221");
        System.out.println("学生姓名:"+stu.getStudentName());
        transaction.commit();
        if(session.isOpen()){//getCurrentSession获取的当前会话会自动关闭session，但是openSession的会话则需要手动关闭,getCurrentSession如果关闭的话会导致重复关闭
            session.close();
        }
        sf.close();
    }

    public void B() {
        Session s1=sf.openSession();
        Session s2=sf.openSession();
        System.out.println(s1.hashCode()+","+s1.hashCode());
        System.out.println("getCurrentSession的值1:"+sf.getCurrentSession().hashCode());
        System.out.println("getCurrentSession的值2:"+sf.getCurrentSession().hashCode());
    }

    public void releaseConnection(){
        if(s.isOpen())s.close();//关闭连接
        if(sf!=null)sf.close();
    }

    public void Add() {
        try{
            trans=s.beginTransaction();
            Student ss=new Student();
            ss.setStudentNo("0221");
            ss.setStudentName("麻烦");
            ss.setMajor("计算机");
            s.save(ss);
            int i=1/1;
            trans.commit();
            System.out.println("学号:"+ss.getStudentNo()+",姓名:"+ss.getStudentName()+",专业:"+ss.getMajor());
            //session的load方法可以通过主键获取一个对象
            //持久态，游离态
        }catch (Exception e){
            if(trans!=null){
                trans.rollback();
            }
            System.out.println("发生错误");
            throw new RuntimeException(e.getMessage());
        }finally {
            releaseConnection();
        }
    }

    public static void main(String[] args){

        new Main().doWork();


    }


}
