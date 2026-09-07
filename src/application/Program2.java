package application;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.entities.Departament;

public class Program2 {
	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		
		DepartmentDao departmentDao = DaoFactory.createDepartmentDao();
		Departament newdepartment = new Departament(null, "RH");
		departmentDao.insert(newdepartment);
		System.out.println("Insertion sucess! id = "+newdepartment.getId());
		
		Departament dep1 = departmentDao.findById(2);
		System.out.println(dep1);
		
		Departament dep = departmentDao.findById(2);
		dep.setName("Logistica");
		departmentDao.update(dep);
		System.out.println("Update completend");
		
		//System.out.println("digite id: ");
		//int id = sc.nextInt();
		//departmentDao.deleteById(id);
		sc.close();
		
		List<Departament> list = departmentDao.findAll();
		for(Departament dp : list) {
			System.out.println(dp);
		}
		
	}

}
