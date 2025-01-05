package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import dbh.DbHelper;
import model.kullanici;

public class kullanici_dao {
	//kullanıcı giriş işlemi
	public kullanici login(String Kullanici_adi, String Sifre) {
		String url = "SELECT * FROM bilgiler WHERE Kullanici_adi = ? AND Sifre = ?";
		try(Connection connection=DbHelper.getConnection();PreparedStatement statement=connection.prepareStatement(url)){
			statement.setString(1, Kullanici_adi);
			statement.setString(2, Sifre);
			try(ResultSet resultSet = statement.executeQuery()){
				if(resultSet.next()) {
					kullanici Kullanici = new kullanici();
					Kullanici.setId(resultSet.getInt("id_Bilgi"));
					Kullanici.setKullanici_adi(resultSet.getString("Kullanici_adi"));
					Kullanici.setSifre(resultSet.getString("Sifre"));
					return Kullanici;
				}
			}
		}catch (SQLException e) {
			System.err.println("Giriş hatası : "+e.getMessage());
		}
		return null;
	}

}
