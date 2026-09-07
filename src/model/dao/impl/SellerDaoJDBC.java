package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import db.DB;
import db.DbException;
import model.dao.SellerDao;
import model.entities.Departament;
import model.entities.Seller;

public class SellerDaoJDBC implements SellerDao{
	
	private Connection conn;
	// dependencia
	public  SellerDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Seller obj) {
		
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"INSERT INTO Seller "
					+"(Name, Email, BirthDate, BaseSalary, DepartmentId) "
					+"VALUES "
					+"(?, ?, ?, ?, ?)", 
					Statement.RETURN_GENERATED_KEYS);
			
			st.setString(1, obj.getName());
			st.setString(2, obj.getEmail());
			st.setDate(3, new java.sql.Date(obj.getBirthDate().getTime()));
			st.setDouble(4, obj.getBaseSalary());
			st.setInt(5, obj.getDep().getId());
			
			int rows = st.executeUpdate();
			
			if(rows > 0){
				ResultSet rs = st.getGeneratedKeys();
				if(rs.next()) {
					int id = rs.getInt(1);
					obj.setId(id);
				}
				DB.closeResultSet(rs);
			}else {
				throw new DbException("Error");
			}
			
			}catch(SQLException e) {
				throw new DbException(e.getMessage());
			}finally {
				DB.closeStatement(st);
			}
		}

	@Override
	public void update(Seller obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"UPDATE seller "
							+"SET Name = ?, Email = ?, BirthDate = ?, BaseSalary = ?, DepartmentId = ? "
							+"WHERE Id = ?" );
			
			st.setString(1, obj.getName());
			st.setString(2, obj.getEmail());
			st.setDate(3, new java.sql.Date(obj.getBirthDate().getTime()));
			st.setDouble(4, obj.getBaseSalary());
			st.setInt(5, obj.getDep().getId());
			st.setInt(6, obj.getId());
			
			st.executeUpdate();
			
			}catch(SQLException e) {
				throw new DbException(e.getMessage());
			}finally {
				DB.closeStatement(st);
			}
		
	}
	


	@Override
	public void deleteById(Integer id) {
		
	}

	@Override
	public Seller findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"SELECT seller.*,  department.Name as DepName "
					+"FROM seller INNER JOIN department "
					+"ON seller.DepartmentId = department.Id "
					+"WHERE seller.Id = ?"
					);
			st.setInt(1, id);
			rs = st.executeQuery();
			if (rs.next()) { // testar se veio resultado 
				Departament dep = instantiateDepartment(rs);
				Seller obj = instatiateSeller(rs, dep);
				return obj;
			}
			return null;
		}catch(SQLException e) { // tratando ecessões 
			throw new DbException(e.getMessage());
		}finally {
			DB.closeResultSet(rs);
			DB.closeStatement(st);
		}
	}

	private Seller instatiateSeller(ResultSet rs, Departament dep) throws SQLException{ // propagando excessão
		Seller obj = new Seller();
		obj.setId(rs.getInt("Id"));
		obj.setName(rs.getString("Name"));
		obj.setEmail(rs.getString("Email"));
		obj.setBaseSalary(rs.getDouble("BaseSalary"));
		obj.setBirthDate(rs.getDate("BirthDate"));
		obj.setDep(dep);
		return obj;
	}

	private Departament instantiateDepartment(ResultSet rs) throws SQLException{
		Departament dep = new Departament();
		dep.setId(rs.getInt("DepartmentId"));
		dep.setName(rs.getString("DepName"));
		return dep;
		
	}

	@Override
	public List<Seller> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"SELECT seller.*,  department.Name as DepName "
					+"FROM seller INNER JOIN department "
					+"ON seller.DepartmentId = department.Id "
					+"ORDER BY Name");
			rs = st.executeQuery();
			List<Seller> list = new ArrayList<>();
			Map<Integer, Departament> map = new HashMap();
			
			while (rs.next()) { 
				Departament dep1 = map.get(rs.getInt("DepartmentId"));
				if(dep1 == null) {
					dep1 = instantiateDepartment(rs);
					map.put(rs.getInt("DepartmentId"), dep1); // salvo department no map quando não achar o resultando
				}
				Seller obj = instatiateSeller(rs, dep1);
				list.add(obj);
			}
			return list;
		}catch(SQLException e) { // tratando ecessões 
			throw new DbException(e.getMessage());
		}finally {
			DB.closeResultSet(rs);
			DB.closeStatement(st);
		}
	}

	@Override
	public List<Seller> findByDepartment(Departament dep) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"SELECT seller.*,  department.Name as DepName "
					+"FROM seller INNER JOIN department "
					+"ON seller.DepartmentId = department.Id "
					+"WHERE DepartmentId = ? "
					+"ORDER BY Name");
			st.setInt(1, dep.getId());
			rs = st.executeQuery();
			List<Seller> list = new ArrayList<>();
			Map<Integer, Departament> map = new HashMap();
			
			while (rs.next()) { 
				Departament dep1 = map.get(rs.getInt("DepartmentId"));
				if(dep1 == null) {
					dep1 = instantiateDepartment(rs);
					map.put(rs.getInt("DepartmentId"), dep1); // salvo department no map quando não achar o resultando
				}
				Seller obj = instatiateSeller(rs, dep1);
				list.add(obj);
			}
			return list;
		}catch(SQLException e) { // tratando ecessões 
			throw new DbException(e.getMessage());
		}finally {
			DB.closeResultSet(rs);
			DB.closeStatement(st);
		}
	}
		

}
