package com.skilldistillery.filmquery.database;

import java.util.List;

import com.skilldistillery.filmquery.entities.Actor;
import com.skilldistillery.filmquery.entities.Film;
import java.sql.*;
import java.time.LocalDate;

public class DatabaseAccessorObject implements DatabaseAccessor {
	private static final String URL = "jdbc:mysql://localhost:3306/sdvid?useSSL=false";
	private static final String USER = "student";
	private static final String PASSWORD = "student";
	
	static {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public Film findFilmById(int filmId) {
		Film film = null;
			Connection conn = null;
			PreparedStatement stmt = null;
			ResultSet rs = null;
		try {
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			String sql = "select * from film where film.id = ?";
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, filmId);
			rs = stmt.executeQuery();
			
			while(rs.next()) {
				int id = rs.getInt("film.id");
				String title = rs.getString("film.title");
				String description = rs.getString("film.description");
				Integer releaseYear = rs.getInt("film.release_year");
				String language = rs.getString("language.name");
				int rentalDuration = rs.getInt("film.rental_duration");
				double rentalRate = rs.getDouble("film.rental_rate");
				Integer length = rs.getInt("film.length");
				double replacementCost = rs.getDouble("film.replacementCost");
				String rating = rs.getString("film.rating");
				String specialFeatures = rs.getNString("film.special_features");
				
				film = new Film();
				film.setId(id);
				film.setTitle(title);
				film.setDescription(description);
				film.setReleaseYear(releaseYear);
				film.setLanguage(language);
				film.setRentalDuration(rentalDuration);
				film.setRentalRate(rentalRate);
				film.setLength(length);
				film.setReplacementCost(replacementCost);
				film.setRating(rating);
				film.setSpecialFeatures(specialFeatures);
				
				
				
			}
			
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (stmt != null) {
					stmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException sqle) {
				System.err.println(sqle);
			}
		}
		return film;
	}

	@Override
	public Actor findActorById(int actorId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Actor> findActorsByFilmId(int filmId) {
		// TODO Auto-generated method stub
		return null;
	}

}
