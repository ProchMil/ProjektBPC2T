package application;

import entities.MovieList;
import gui.MainFrame;

public class AppManager {
	Database database;
	MovieList movieList;
	
	public AppManager() throws Exception {
		this.database = new Database();
		this.movieList = this.database.getAllMovies();
		 		
		new MainFrame(movieList, this.database);
	}
}
