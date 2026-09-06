package application;

import java.util.List;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Departament;
import model.entities.Seller;

public class Program {

	public static void main(String[] args) {
		
		SellerDao sellerdao = DaoFactory.createSellerDao();
		
		Seller seller = sellerdao.findById(3);
		
		System.out.println("==== TEST 1: SELLER findById ====");
		System.out.println(seller);
		
		System.out.println("==== TEST 2: SELLER findByDepartmentId ====");
		Departament dep = new Departament(2, null);
		List<Seller> list = sellerdao.findByDepartment(dep);
		
		for(Seller obj : list) {
			System.out.println(obj);
		}
	}

}
