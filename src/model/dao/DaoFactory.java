package model.dao;

import db.DB;
import model.dao.impl.DepartmentDaojdbc;
import model.dao.impl.SellerDaoJDBC;

public class DaoFactory {
	
	// para não expor a implementação 
	public static SellerDao createSellerDao() {
		return new SellerDaoJDBC(DB.getConnection());
	}
	
	public static DepartmentDao createDepartmentDao() {
		return new DepartmentDaojdbc(DB.getConnection());
	}

}
