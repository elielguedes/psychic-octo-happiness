package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

import db.DB;
import db.DbException;
import model.dao.DepartmentDao;
import model.entities.Departament;

public class DepartmentDaojdbc implements DepartmentDao{
	
	private Connection conn;
	
	public DepartmentDaojdbc(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Departament obj) {
		PreparedStatement st = null;
		
		try {
			st = conn.prepareStatement("INSERT INTO department(Name) VALUES(?)", Statement.RETURN_GENERATED_KEYS);
			
			st.setString(1, obj.getName());
			
			int rowsAffeted = st.executeUpdate();
			if(rowsAffeted > 0) {
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
	public void update(Departament obj) {
		PreparedStatement st = null;
		
		try {
			st = conn.prepareStatement("UPDATE department SET Name = ? WHERE Id = ?");
			st.setString(1, obj.getName());
			st.setInt(2, obj.getId());
			
			st.executeUpdate();
		}catch(SQLException e) {
			throw new DbException(e.getMessage());
		}finally {
			DB.closeStatement(st);
		}
	}

	@Override
	public void deleteById(Integer id) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("DELETE FROM department WHERE Id = ?");
			st.setInt(1, id);
			
			int rowsAffected = st.executeUpdate();
			if(rowsAffected == 0) 
				throw new DbException("Cadastro inexistente");
		}catch(SQLException e) {
			throw new DbException(e.getMessage());
		}finally {
			DB.closeStatement(st);
		}
		
	}

	@Override
	public Departament findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("SELECT Id, Name FROM department WHERE Id = ?");
			st.setInt(1, id);
			
			rs = st.executeQuery();
			
			if(rs.next()) {
				Departament dep = new Departament();
				dep.setId(rs.getInt("Id"));
				dep.setName(rs.getString("Name"));
				return dep;
			}else
				return null;
		}catch(SQLException e) {
			throw new DbException(e.getMessage());
		}finally {
			DB.closeResultSet(rs);
			DB.closeResultSet(rs);
		}
	}

	@Override
	public List<Departament> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			st = conn.prepareStatement("select Id, Name from department");
			
			rs = st.executeQuery();
			List<Departament> list = new ArrayList<>();
			while(rs.next()) {
				Departament dep = new Departament();
				dep.setId(rs.getInt("Id"));
				dep.setName(rs.getString("Name"));
				list.add(dep);
			}
			return list;
			
		}catch(SQLException e) {
			throw new DbException(e.getMessage());
		}finally {
			DB.closeResultSet(rs);
			DB.closeStatement(st);
		}
	}

}
