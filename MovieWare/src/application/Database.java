package application;

import java.sql.*;

import entities.Movie;
import entities.MovieList;
import entities.Rating;
import enums.MovieType;

import java.util.ArrayList;

public class Database {
	Connection conn;
	
	public Database() throws Exception {
		Class.forName("org.sqlite.JDBC");
        this.conn = DriverManager.getConnection("jdbc:sqlite:db/movies");
      
        this.initTable();
        
        conn.close();
	}
	
	private void initTable() throws SQLException {
		String movieTableSql = "CREATE TABLE IF NOT EXISTS movies (\n"
                + "	id integer PRIMARY KEY,\n"
                + "	name text,\n"
                + "	director text,\n"
                + "	releaseDate text,\n"
                + "	type text,\n"
                + "	recommendedViewerAge text\n"
                + ");";
		this.conn.createStatement().execute(movieTableSql);
		
		String actorTableSql = "CREATE TABLE IF NOT EXISTS actors (\n"
                + "	id integer PRIMARY KEY,\n"
                + "	name text,\n"
                + "	movieId integer\n"
                + ");";
		this.conn.createStatement().execute(actorTableSql);
		
		String animatorsTableSql = "CREATE TABLE IF NOT EXISTS animators (\n"
                + "	id integer PRIMARY KEY,\n"
                + "	name text,\n"
                + "	movieId integer\n"
                + ");";
		this.conn.createStatement().execute(animatorsTableSql);
		
		String ratingsTableSql = "CREATE TABLE IF NOT EXISTS ratings (\n"
                + "	id integer PRIMARY KEY,\n"
                + "	value text,\n"
                + "	comment text,\n"
                + "	movieId integer\n"
                + ");";
		this.conn.createStatement().execute(ratingsTableSql);
		
		this.conn.close();
	}
	
	public void truncateDB() throws SQLException {
		this.conn = DriverManager.getConnection("jdbc:sqlite:db/movies");
		
		this.conn.createStatement().execute("DELETE FROM movies");
		this.conn.createStatement().execute("DELETE FROM actors");
		this.conn.createStatement().execute("DELETE FROM animators");
		this.conn.createStatement().execute("DELETE FROM ratings");
		
		this.conn.close();
	}
	
	public void insertMovie(Movie movie) throws SQLException {
		this.conn = DriverManager.getConnection("jdbc:sqlite:db/movies");
		
		if (movie == null)
			return;
		
		// Save movie
        String sql = "INSERT INTO movies(name,director,releaseDate,type,recommendedViewerAge) VALUES(?,?,?,?,?)";

        String movieTypeStringified = "";
        if (movie.getType() == MovieType.Cartoon)
        	movieTypeStringified = "Cartoon";
        if (movie.getType() == MovieType.LiveAction)
        	movieTypeStringified = "LiveAction";
        
        PreparedStatement pstmt = this.conn.prepareStatement(sql);
	    pstmt.setString(1, movie.getName());
	    pstmt.setString(2, movie.getDirector());
	    pstmt.setString(3, movie.getReleaseDate());
	    pstmt.setString(4, movieTypeStringified);
	    pstmt.setString(5, Integer.toString(movie.getRecommendedViewerAge()));
	    
	    pstmt.executeUpdate();
	    
	    sql = "SELECT last_insert_rowid() as id from movies";
	    int movieId = this.conn.createStatement().executeQuery(sql).getInt("id");
	    
	    // Save movie related actors or animators
	    if (movie.getType() == MovieType.Cartoon && movie.getAnimators() != null) {
	    	for(int i=0; i<movie.getAnimators().size(); i++) {
	    		sql = "INSERT INTO animators(name,movieId) VALUES(?,?)";
	    		PreparedStatement pstmtForAnimators = this.conn.prepareStatement(sql);
	    		pstmtForAnimators.setString(1,movie.getAnimators().get(i));
	    		pstmtForAnimators.setInt(2, movieId);
	    		
	    		pstmtForAnimators.executeUpdate();
	    	}
	    } else if (movie.getType() == MovieType.LiveAction && movie.getActors() != null) {
	    	for(int i=0; i<movie.getActors().size(); i++) {
	    		sql = "INSERT INTO actors(name,movieId) VALUES(?,?)";
	    		PreparedStatement pstmtForActors = this.conn.prepareStatement(sql);
	    		pstmtForActors.setString(1,movie.getActors().get(i));
	    		pstmtForActors.setInt(2, movieId);
	    		
	    		pstmtForActors.executeUpdate();
	    	}
	    }
	    
	    // Save movie related ratings
	    if (movie.getRatingManager().getRatings() != null) {
	    	for(int i=0; i<movie.getRatingManager().getRatings().size(); i++) {
	    		Rating currentRating = movie.getRatingManager().getRatings().get(i);
	    		
	    		sql = "INSERT INTO ratings(value,comment,movieId) VALUES(?,?,?)";
	    		PreparedStatement pstmtForRatings = this.conn.prepareStatement(sql);
	    		pstmtForRatings.setInt(1,currentRating.getValue());
	    		pstmtForRatings.setString(2,currentRating.getComment());
	    		pstmtForRatings.setInt(3, movieId);
	    		
	    		pstmtForRatings.executeUpdate();
	    	}
	    }
	    
	    this.conn.close();
	}
	
	public MovieList getAllMovies() throws SQLException {
		MovieList movieList = new MovieList();
		
		this.conn = DriverManager.getConnection("jdbc:sqlite:db/movies");
        String sql = "SELECT * FROM movies";
        
        ResultSet movies = this.conn.createStatement().executeQuery(sql);
        while (movies.next()) {
        	Movie movieToLoad = null;
        	
        	int movieId = movies.getInt("id");
            String movieType = movies.getString("type");
            
            if (movieType.contains("LiveAction")) {
            	ArrayList<String> actors = new ArrayList<String>();
            	String actorsSql = "SELECT * FROM actors WHERE movieId = " + movieId;
            	ResultSet actorsSet = this.conn.createStatement().executeQuery(actorsSql);
            	while(actorsSet.next()) {
            		actors.add(actorsSet.getString("name"));
            	}
            	
            	movieToLoad = new Movie(movies.getString("name"), movies.getString("director"), movies.getString("releaseDate"), actors);
            	movieList.add(movieToLoad);
            } else if (movieType.contains("Cartoon")) {
            	ArrayList<String> animators = new ArrayList<String>();
            	String animatorsSql = "SELECT * FROM animators WHERE movieId = " + movieId;
            	ResultSet animatorsSet = this.conn.createStatement().executeQuery(animatorsSql);
            	while(animatorsSet.next()) {
            		animators.add(animatorsSet.getString("name"));
            	}
            	
            	movieToLoad = new Movie(movies.getString("name"), movies.getString("director"), movies.getString("releaseDate"), animators, Integer.parseInt(movies.getString("recommendedViewerAge")));
            }
            
            if (movieToLoad != null) {
            	String ratingsSql = "SELECT * FROM ratings where movieId = " + movieId;
                ResultSet ratings = this.conn.createStatement().executeQuery(ratingsSql);
                while(ratings.next()) {
                	movieToLoad.getRatingManager().addRating(new Rating(ratings.getInt("value"), ratings.getString("comment")));
                }     	
            }
        }
        
        this.conn.close();
        return movieList;
	}
}
