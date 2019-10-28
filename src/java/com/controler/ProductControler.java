/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controler;

import com.exam.Employee;
import com.exam.Product;
import com.exam.PurchaseB;
import com.exam.SaleA;
import com.exam.SaledatilesB;
import com.model.A.PurchaseA;
import com.model.A.SaleB;
import com.model.A.SaledatilesA;
import com.util.HibernateUtil;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIForm;
import javax.faces.context.FacesContext;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author User
 */
@ManagedBean
@SessionScoped
public class ProductControler {

    Product product = new Product();
    PurchaseB purchase = new PurchaseB();
    PurchaseA purchaseA = new PurchaseA();
    SaleA saleA=new SaleA();
    SaleB saleB=new SaleB();
    SaledatilesB saledatilesA=new SaledatilesB();
     SaledatilesA saledatilesB=new SaledatilesA();
    Employee employee=new Employee();
    private int Pu_qty;
    private double totalPtice=0;
    private String pDesc;
    private String search;
    
    private List<SaledatilesB>saleList;
    private UIForm tblDisplay=new UIForm();

    public SaleB getSaleB() {
        return saleB;
    }

    public void setSaleB(SaleB saleB) {
        this.saleB = saleB;
    }

    public SaledatilesA getSaledatilesB() {
        return saledatilesB;
    }

    public void setSaledatilesB(SaledatilesA saledatilesB) {
        this.saledatilesB = saledatilesB;
    }
    
    public PurchaseA getPurchaseA() {
        return purchaseA;
    }

    public void setPurchaseA(PurchaseA purchaseA) {
        this.purchaseA = purchaseA;
    }
     
    public UIForm getTblDisplay() {
        return tblDisplay;
    }

    public void setTblDisplay(UIForm tblDisplay) {
        this.tblDisplay = tblDisplay;
    }
    
    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
    
   
    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }
    
    public int getPu_qty() {
        return Pu_qty;
    }

    public void setPu_qty(int Pu_qty) {
        this.Pu_qty = Pu_qty;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public PurchaseB getPurchase() {
        return purchase;
    }

    public void setPurchase(PurchaseB purchase) {
        this.purchase = purchase;
    }

    public SaleA getSaleA() {
        return saleA;
    }

    public void setSaleA(SaleA saleA) {
        this.saleA = saleA;
    }
    public double getTotalPtice() {
        return totalPtice;
    }

    public void setTotalPtice(double totalPtice) {
        this.totalPtice = totalPtice;
    }
    public String getpDesc() {
        return pDesc;
    }

    public void setpDesc(String pDesc) {
        this.pDesc = pDesc;
    }

    public SaledatilesB getSaledatilesA() {
        return saledatilesA;
    }

    public void setSaledatilesA(SaledatilesB saledatilesA) {
        this.saledatilesA = saledatilesA;
    }
    public void newpro(){
     Employee employee=new Employee();
     tblDisplay.setRendered(false);
    }
    public String saveProduct() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.save(product);
            tx.commit();
            session.flush();
        } catch (Exception e) {

        }
        return "productlist";
    }

    public List<Product> productList() {
        Session session = HibernateUtil.getSessionFactory().openSession();

        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            Query q = session.createQuery("from Product");
            List<Product> list = q.list();
            session.close();
            return list;
        } catch (Exception e) {
            session.getTransaction().rollback();
        }
        return null;
    }

    public String updatePro(Product prod) {
        product.setId(prod.getId());
        product.setName(prod.getName());
        product.setCatagory(prod.getCatagory());
        product.setQty(prod.getQty());
        product.setPrice(prod.getPrice());
        product.setDate(prod.getDate());

        return "updatrpro";
    }
       public String updateProduct(){
       Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
            try {
            tx = session.beginTransaction();
            
            session.update(product);
            tx.commit();
            session.flush();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } 
       return "productlist";
       }
    public String deleteProduct(Product pro) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.delete(pro);
            session.flush();
            session.getTransaction().commit();

        } catch (Exception e) {
            session.getTransaction().rollback();
        }
        return "productlist";
    }
//start purchaseb brance
    public String purchaseA(Product prod) {
        product.setId(prod.getId());
        product.setName(prod.getName());
        product.setCatagory(prod.getCatagory());
        product.setQty(prod.getQty());
        product.setPrice(prod.getPrice());
        product.setDate(prod.getDate());
        return "purchaseA";
    }

    public String savePurchaseA(ProductControler productControler) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
            try {
            tx = session.beginTransaction();
            int av_qty = product.getQty();
            product.setQty(av_qty-Pu_qty);
            session.update(product);
            tx.commit();
            session.flush();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } 
        try {
            tx = session.beginTransaction();
            purchase.setP_id(product.getId());
            purchase.setName(product.getName());
            purchase.setCatagory(product.getCatagory());
            purchase.setQty(Pu_qty);
            purchase.setPrice(product.getPrice());
            session.save(purchase);
            tx.commit();
            session.flush();
        } catch (Exception e) {
          session.getTransaction().rollback();
        }
        
        return "purchaseA";
    }
    public List<PurchaseB> purchaseList() {
        Session session = HibernateUtil.getSessionFactory().openSession();

        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            Query q = session.createQuery("from PurchaseB");
            List<PurchaseB> list = q.list();
            session.close();
            return list;
        } catch (Exception e) {
            session.getTransaction().rollback();
        }
        return null;
    }
     public String saleA(PurchaseB prod) {
         purchase.setId(prod.getId());
        purchase.setP_id(prod.getP_id());
        purchase.setName(prod.getName());
        purchase.setCatagory(prod.getCatagory());
        purchase.setQty(prod.getQty());
        purchase.setPrice(prod.getPrice());
        purchase.setDate(prod.getDate());
        return "saleA";
    }
      public String saveSaleA(ProductControler productControler) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
            try {
            tx = session.beginTransaction();
            int av_qty = purchase.getQty();
            purchase.setQty(av_qty-Pu_qty);
            session.update(purchase);
            tx.commit();
            session.flush();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } 
        try {
            tx = session.beginTransaction();
            saleA.setP_id(purchase.getId());
            saleA.setName(purchase.getName());
            saleA.setCatagory(purchase.getCatagory());
            saleA.setQty(Pu_qty);
            saleA.setPrice(purchase.getPrice()* Pu_qty);
            session.save(saleA);
            tx.commit();
            session.flush();
        } catch (Exception e) {

        }
        
        return "saleA";
    }
      public List<SaleA> saleDetais() {
        Session session = HibernateUtil.getSessionFactory().openSession();

        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            Query q = session.createQuery("from SaleA");
            List<SaleA> list = q.list();
            session.close();
            totalPtice=0;
            pDesc="";
            for (SaleA sale : list) {
                totalPtice += sale.getPrice();
                pDesc +=sale.getName()+",";
            }
            return list;
        } catch (Exception e) {
            session.getTransaction().rollback();
        }
        return null;
    }
      public String deleteSale(SaleA saleA) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.delete(saleA);
            session.flush();
            session.getTransaction().commit();

        } catch (Exception e) {
            session.getTransaction().rollback();
        }
        return "saleA";
    }
   public String saveSaleDetais() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            saledatilesA.setP_descri(pDesc);
            saledatilesA.setPrice(totalPtice);
            session.save(saledatilesA);
            tx.commit();
            session.flush();
        } catch (Exception e) {

        }
        return "saleA";
    }
   public void salesReport() {
       Session session = HibernateUtil.getSessionFactory().openSession();
       Transaction tx = null;
       try {
           tx = session.beginTransaction();
           System.out.println(search);
           Query q = session.createQuery("from SaledatilesB where recevier = :search");
           q.setString("recevier",search);
           saleList = q.list();
           System.out.println(saleList.get(1).getId());
           session.flush();
       } catch (Exception e) {
//            tx.rollback();
       }
   }

    public List<SaledatilesB> getSaleList() {
        return saleList;
    }

    public void setSaleList(List<SaledatilesB> saleList) {
        this.saleList = saleList;
    }

    public String saveEmpolyee() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.save(employee);
            tx.commit();
            session.flush();
        } catch (Exception e) {

        }
        return "empoLIst";
    }
    public List<Employee> empoyList() {
        Session session = HibernateUtil.getSessionFactory().openSession();

        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            Query q = session.createQuery("from Employee");
            List<Employee> list = q.list();
            session.close();
            return list;
        } catch (Exception e) {
            session.getTransaction().rollback();
        }
        return null;
    }
    public List<Employee> empoyListzi() {
        Session session = HibernateUtil.getSessionFactory().openSession();

        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            Query q = session.createQuery("from Employee where brance ='Zikathola'");
            List<Employee> list = q.list();
            session.close();
            return list;
        } catch (Exception e) {
            session.getTransaction().rollback();
        }
        return null;
    }
    public List<Employee> empoyListdh() {
        Session session = HibernateUtil.getSessionFactory().openSession();

        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            Query q = session.createQuery("from Employee where brance ='Dhanmondi'");
            List<Employee> list = q.list();
            session.close();
            return list;
        } catch (Exception e) {
            session.getTransaction().rollback();
        }
        return null;
    }
    public String deleteEmpo(Employee employee) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.delete(employee);
            session.flush();
            session.getTransaction().commit();

        } catch (Exception e) {
            session.getTransaction().rollback();
        }
        return "empoLIst";
    }
    public String eidteEmpo(Employee emp) {
       employee.setId(emp.getId());
       employee.setName(emp.getName());
       employee.setAddress(emp.getAddress());
       employee.setPhone(emp.getPhone());
       employee.setEmail(emp.getEmail());
       employee.setPost(emp.getPost());
       employee.setBrance(emp.getBrance());
       employee.setDate(emp.getDate());
        return "upempo";
    }
    public String updateEmpo(){
       Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
            try {
            tx = session.beginTransaction();
            
            session.update(employee);
            tx.commit();
            session.flush();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } 
       return "empoLIst";
       }
    public List<SaledatilesB> saleshowList() {
        Session session = HibernateUtil.getSessionFactory().openSession();

        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            Query q = session.createQuery("from SaledatilesB");
            List<SaledatilesB> list = q.list();
            session.close();
            return list;
        } catch (Exception e) {
            session.getTransaction().rollback();
        }
        return null;
    }
    //start purchaseA brance
    public String purchaseB(Product prod) {
        product.setId(prod.getId());
        product.setName(prod.getName());
        product.setCatagory(prod.getCatagory());
        product.setQty(prod.getQty());
        product.setPrice(prod.getPrice());
        product.setDate(prod.getDate());
        return "purchaseB";
    }
    public String savePurchaseB(ProductControler productControler) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
            try {
            tx = session.beginTransaction();
            int av_qty = product.getQty();
            product.setQty(av_qty-Pu_qty);
            session.update(product);
            tx.commit();
            session.flush();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } 
        try {
            tx = session.beginTransaction();
            purchaseA.setP_id(product.getId());
            purchaseA.setName(product.getName());
            purchaseA.setCatagory(product.getCatagory());
            purchaseA.setQty(Pu_qty);
            purchaseA.setPrice(product.getPrice());
            session.save(purchaseA);
            tx.commit();
            session.flush();
        } catch (Exception e) {
          session.getTransaction().rollback();
        }
        
        return "purchaseB";
    }
    public List<PurchaseA> purchaseListB() {
        Session session = HibernateUtil.getSessionFactory().openSession();

        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            Query q = session.createQuery("from PurchaseA");
            List<PurchaseA> list = q.list();
            session.close();
            return list;
        } catch (Exception e) {
            session.getTransaction().rollback();
        }
        return null;
    }
    public String saleB(PurchaseA prod) {
         purchaseA.setId(prod.getId());
        purchaseA.setP_id(prod.getP_id());
        purchaseA.setName(prod.getName());
        purchaseA.setCatagory(prod.getCatagory());
        purchaseA.setQty(prod.getQty());
        purchaseA.setPrice(prod.getPrice());
        purchaseA.setDate(prod.getDate());
        return "saleB";
    }
    public String saveSaleB(ProductControler productControler) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
            try {
            tx = session.beginTransaction();
            int av_qty = purchaseA.getQty();
            purchaseA.setQty(av_qty-Pu_qty);
            session.update(purchaseA);
            tx.commit();
            session.flush();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } 
        try {
            tx = session.beginTransaction();
            saleB.setP_id(purchaseA.getId());
            saleB.setName(purchaseA.getName());
            saleB.setCatagory(purchaseA.getCatagory());
            saleB.setQty(Pu_qty);
            saleB.setPrice(purchaseA.getPrice()* Pu_qty);
            session.save(saleB);
            tx.commit();
            session.flush();
        } catch (Exception e) {

        }
        
        return "saleB";
    }
    public List<SaleB> saleDetaisB() {
        Session session = HibernateUtil.getSessionFactory().openSession();

        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            Query q = session.createQuery("from SaleB");
            List<SaleB> list = q.list();
            session.close();
            totalPtice=0;
            pDesc="";
            for (SaleB sale : list) {
                totalPtice += sale.getPrice();
                pDesc +=sale.getName()+",";
            }
            return list;
        } catch (Exception e) {
            session.getTransaction().rollback();
        }
        return null;
    }
    public String deleteSaleB(SaleB saleB) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.delete(saleB);
            session.flush();
            session.getTransaction().commit();

        } catch (Exception e) {
            session.getTransaction().rollback();
        }
        return "saleB";
    }
    public String saveSaleDetaisB() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            saledatilesB.setP_descri(pDesc);
            saledatilesB.setPrice(totalPtice);
            session.save(saledatilesB);
            tx.commit();
            FacesContext f = FacesContext.getCurrentInstance();
           f.addMessage(null, new FacesMessage("Successfully Save data"));
            session.flush();
        } catch (Exception e) {

        }
        return "saleB";
    }
    public List<SaledatilesA> saleshowListB() {
        Session session = HibernateUtil.getSessionFactory().openSession();

        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            Query q = session.createQuery("from SaledatilesA");
            List<SaledatilesA> list = q.list();
            session.close();
            return list;
        } catch (Exception e) {
            session.getTransaction().rollback();
        }
        return null;
    }
}
