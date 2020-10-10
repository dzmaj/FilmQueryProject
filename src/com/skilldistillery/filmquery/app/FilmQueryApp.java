package com.skilldistillery.filmquery.app;

import java.util.List;
import java.util.Scanner;

import com.skilldistillery.filmquery.database.DatabaseAccessor;
import com.skilldistillery.filmquery.database.DatabaseAccessorObject;
import com.skilldistillery.filmquery.entities.Film;

public class FilmQueryApp {

	DatabaseAccessor db = new DatabaseAccessorObject();
	private boolean displayAll = false;

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
				case "3":
					displayAll = !displayAll;
					System.out.println("Display all film details: " + displayAll);
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
				printFilm(film);
			}
			
		} else {
			System.out.println("No Results");
		}
	}

	private void searchFilmById(Scanner input) {
		int id;
		LOOP: while (true) {
			System.out.println("Please enter the film ID:");
			try {
				id = Integer.parseInt(input.nextLine());
				break LOOP;
			} catch (NumberFormatException e) {
				System.out.println("You must enter an integer");
			} 
		}
		Film film = db.findFilmById(id);
		if (film != null) {
			printFilm(film);			
		} else {
			System.out.println("Film not found");
		}
	}
	
	private void printFilm(Film film) {
		System.out.println(film);
		if (displayAll) {
			String str1 = "Rental Duration: " + film.getRentalDuration();
			str1 += ", Rental Rate: " + film.getRentalRate();
			str1 += ", Replacement Cost: " + film.getReplacementCost();
			String str2 = "Length: " + film.getLength();
			str2 += ", Special Features: " + film.getSpecialFeatures();
			System.out.println(str1);
			System.out.println(str2);
		}
		System.out.println();
	}

	private void displayMenu() {
		System.out.println("---MENU---");
		System.out.println("1: Look up film by ID");
		System.out.println("2: Look up film by keyword");
		if (!displayAll) {
			System.out.println("3: Display All Film Details");
		} else {
			System.out.println("3: Display Less Film Details");
		}
		System.out.println("0: Exit Program");
	}

}
