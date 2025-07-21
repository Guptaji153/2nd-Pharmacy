package com.infinite.jsf.test;

import com.infinite.jsf.pharmacy.controller.ReviewPharmacyController;
import com.infinite.jsf.pharmacy.dao.ReviewPharmacyaDao;
import com.infinite.jsf.pharmacy.daoImpl.ReviewPharmacyaDaoImpl;

public class ReviewPharmacyTest {
	public static void main(String[] args) {
		
		ReviewPharmacyController controller=new ReviewPharmacyController();
		ReviewPharmacyaDao dao=new ReviewPharmacyaDaoImpl();
		System.out.println("===================");
//		controller.showPharmacyAllForReview().forEach(System.out::println);
//		System.out.println(controller.showPharmacyAllForReview().get(0).getEquipments());
 
//	System.out.println(controller.showPharmacyAllForReview().get(0).getStatus());
	dao.reviewPharmacyDetails().forEach(System.out::println);
 
	}
 
}
 
 