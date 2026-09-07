package application;

import java.util.Date;
import java.util.List;
import java.util.Scanner;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Departament;
import model.entities.Seller;

public class Program {

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		
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
		

		System.out.println("==== TEST 3: SELLER findByAll ====");
		list = sellerdao.findAll();
		
		for(Seller obj : list) {
			System.out.println(obj);
		}
		
		System.out.println("==== TEST 4: SELLER Insert ====");
		Seller newSeller = new Seller(null, "greg", "greg@gmail.com", new Date(), 4000.00, dep);
		sellerdao.insert(newSeller);
		System.out.println("Inserted! new id = "+ newSeller.getId());
		
		System.out.println("==== TEST 5: SELLER Update ====");
		seller = sellerdao.findById(1);
		seller.setName("Maria waine");
		sellerdao.update(seller);
		System.out.println("Update completed");
		
		System.out.println("==== TEST 6: SELLER Delete ====");
		System.out.println("enter id for delete test: ");
		int id = sc.nextInt();
		
		sellerdao.deleteById(id);
		
		System.out.println("Delete completed");
		sc.close();

	}

}
