/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.exam;

import com.util.HibernateUtil;
import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

@ManagedBean
@SessionScoped
@Entity
@Table(name="userlogin")
public class User_login implements Serializable {
  @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id; 
   @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;
    @Column(name = "brance")
    private String brance;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getBrance() {
        return brance;
    }

    public void setBrance(String brance) {
        this.brance = brance;
    }
    public String userLogin() {
       
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Query query = session.createQuery("from User_login  where email= :email and password= :password and brance= :brance");
            query.setString("email", email);
            query.setString("password", password);
            query.setString("brance", brance);
            List<User_login> list = query.list();
            session.flush();
            String br=list.get(0).brance;
            System.out.println(br);
            if(br.equals("dhanmondi")) {
                return "userHomepageB";
            }else if(br.equals("zikathola")){
            return "userHomepageA";
            }
            else{
              return "welcomePrimefaces";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
      
}
