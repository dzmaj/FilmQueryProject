package com.skilldistillery.filmquery.app;

import java.util.List;
import java.util.Scanner;

import com.skilldistillery.filmquery.database.DatabaseAccessor;
import com.skilldistillery.filmquery.database.DatabaseAccessorObject;
import com.skilldistillery.filmquery.entities.Film;

public class FilmQueryApp {

	DatabaseAccessor db = new DatabaseAccessorObject();

	public static void main(String[] args) {
		FilmQueryApp app = new FilmQueryApp();
//    app.test();
		app.launch();
	}

	private void test() {
		Film film = db.findFilmById(1);
		System.out.println(film);
	}

	private void launch() {
		Scanner input = new Scanner(System.in);

		startUserInterface(input);

		input.close();
	}

	private void startUserInterface(Scanner input) {
		boolean exit = false;
		while (!exit) {
			boolean validInput = false;
			while (!validInput) {
				displayMenu();
				String selection = input.nextLine();
				switch (selection) {
				case "1":
					searchFilmById(input);
					validInput = true;
					break;
				case "2":
					searchFilmByKeyword(input);
					validInput = true;
					break;
				case "0":
					System.out.println("Bye");
					exit = true;
					validInput = true;
					break;
				default:
					System.out.println("Invalid Input");
					break;
				}
			} 
		}
	}
	
	private void searchFilmByKeyword(Scanner input) {
		System.out.println("Please enter the keyword to search by:");
		String keyword;
		LOOP: while (true) {
			try {
				keyword = input.nextLine();
				break LOOP;
			} catch (NumberFormatException e) {
				System.out.println("You must enter an integer");
			} 
		}
		List<Film> films = db.findFilmsByKeyword(keyword);
		if (films != null) {
			for (Film film : films) {
				System.out.println(film);
			}
			
		} else {
			System.out.println("No Results");
		}
	}

	private void searchFilmById(Scanner input) {
		System.out.println("Please enter the film ID:");
		int id;
		LOOP: while (true) {
			try {
				id = Integer.parseInt(input.nextLine());
				break LOOP;
			} catch (NumberFormatException e) {
				System.out.println("You must enter an integer");
			} 
		}
		Film film = db.findFilmById(id);
		if (film != null) {
			System.out.println(film);			
		} else {
			System.out.println("Film not found");
		}
	}

	private void displayMenu() {
		System.out.println("---MENU---");
		System.out.println("1: Look up film by ID");
		System.out.println("2: Look up film by keyword");
		System.out.println("0: Exit Program");
	}

}
