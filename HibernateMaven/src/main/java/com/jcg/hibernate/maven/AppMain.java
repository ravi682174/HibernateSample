package com.jcg.hibernate.maven;

import java.util.Date;
import java.util.Properties;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class AppMain {

	static User userObj;
	static Session sessionObj;
	static SessionFactory sessionFactoryObj;
	
	
	public static Properties propertyLoad() {
        Properties properties = null;
        if (properties == null) {
            properties = new Properties();
            try {
                properties.load(AppMain.class
                        .getResourceAsStream("/config.properties"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println("properties"+properties);
        return properties;
    }
	


	private static SessionFactory buildSessionFactory() {
		// Creating Configuration Instance & Passing Hibernate Configuration File
		
		Configuration configure= new Configuration();
		configure.configure("hibernate.cfg.xml");
		 sessionFactoryObj =configure.configure().buildSessionFactory();
		 System.out.println("sessionFactoryObj"+sessionFactoryObj);
				return sessionFactoryObj;
	}

	public static void main(String[] args) {
		System.out.println(".......Hibernate Maven Example.......\n");
		try {
			propertyLoad();
			sessionObj = buildSessionFactory().openSession();
			sessionObj.beginTransaction();
			System.out.println("sessionObj"+sessionObj);
			for(int i = 101; i <= 105; i++) {
				userObj = new User();
				userObj.setUserid(i);
				userObj.setUsername("Editor " + i);
				userObj.setCreatedBy("Administrator");
				userObj.setCreatedDate(new Date());

				sessionObj.save(userObj);
			}
			System.out.println("\n.......Records Saved Successfully To The Database.......\n");

			// Committing The Transactions To The Database
			sessionObj.getTransaction().commit();
		} catch(Exception sqlException) {
			if(null != sessionObj.getTransaction()) {
				System.out.println("\n.......Transaction Is Being Rolled Back.......");
				sessionObj.getTransaction().rollback();
			}
			sqlException.printStackTrace();
		} finally {
			if(sessionObj != null) {
				sessionObj.close();
			}
		}
	}
}