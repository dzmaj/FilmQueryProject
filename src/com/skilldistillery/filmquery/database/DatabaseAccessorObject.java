package com.skilldistillery.filmquery.database;

import java.util.ArrayList;
import java.util.List;

import com.skilldistillery.filmquery.entities.Actor;
import com.skilldistillery.filmquery.entities.Film;
import java.sql.*;

public class DatabaseAccessorObject implements DatabaseAccessor {
	private static final String URL = "jdbc:mysql://localhost:3306/sdvid?useSSL=false";
	private static final String USER = "student";
	private static final String PASSWORD = "student";

	static {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
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
			String sql = "select * from film " + "join language on language.id = film.language_id where film.id = ?";
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, filmId);
			rs = stmt.executeQuery();

			while (rs.next()) {
				int id = rs.getInt("film.id");
				String title = rs.getString("film.title");
				String description = rs.getString("film.description");
				Integer releaseYear = rs.getInt("film.release_year");
				String language = rs.getString("language.name");
				int rentalDuration = rs.getInt("film.rental_duration");
				double rentalRate = rs.getDouble("film.rental_rate");
				Integer length = rs.getInt("film.length");
				double replacementCost = rs.getDouble("film.replacement_cost");
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
				film.setActorList(findActorsByFilmId(id));

			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
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
		Actor actor = null;
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			String sql = "select * from actor where actor.id = ?";
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, actorId);
			rs = stmt.executeQuery();

			while (rs.next()) {
				int id = rs.getInt("actor.id");
				String firstName = rs.getString("actor.first_name");
				String lastName = rs.getString("actor.last_name");

				actor = new Actor();
				actor.setId(id);
				actor.setFirstName(firstName);
				actor.setLastName(lastName);

			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
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
		return actor;
	}

	@Override
	public List<Actor> findActorsByFilmId(int filmId) {
		List<Actor> actors = null;
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			String sql = "select * from actor " + "join film_actor on actor.id = film_actor.actor_id "
					+ "join film on film_actor.film_id = film.id " + "where film.id = ?";
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, filmId);
			rs = stmt.executeQuery();

			while (rs.next()) {
				int id = rs.getInt("actor.id");
				String firstName = rs.getString("actor.first_name");
				String lastName = rs.getString("actor.last_name");

				Actor actor = new Actor();
				actor.setId(id);
				actor.setFirstName(firstName);
				actor.setLastName(lastName);
				if (actors == null) {
					actors = new ArrayList<>();
				}
				actors.add(actor);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
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
		return actors;
	}

	public List<Film> findFilmsByKeyword(String keyword) {
		List<Film> films = null;
		Film film = null;
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			String sql = "select film.id from film "
					+ "where film.description like ? or film.title like ?";
			stmt = conn.prepareStatement(sql);
			keyword = "%" + keyword + "%";
			stmt.setString(1, keyword);
			stmt.setString(2, keyword);
			rs = stmt.executeQuery();

			while (rs.next()) {
				int id = rs.getInt("film.id");
				film = findFilmById(id);
				if (films == null) {
					films = new ArrayList<>();
				}
				if (film != null) {
					films.add(film);
				}

			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
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
		return films;
	}

}
