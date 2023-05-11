package entities;

import java.io.Serializable;
import java.util.ArrayList;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.File;
import java.util.Date;

import enums.MovieType;

public class Movie implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8785373651371669310L;
	// General
	protected String name;
	protected String director;
	protected String releaseDate;
	protected RatingManager ratingManager;
	protected MovieType type;
	
	// Live Action Specific
	protected ArrayList<String> actors;
	
	// Cartoon Specific
	protected ArrayList<String> animators;
	protected int recommendedViewerAge;
	
	public Movie (String name,	String director, String releaseDate, ArrayList<String> actors) {
		this.name = name;
		this.director = director;
		this.releaseDate = releaseDate;
		this.actors = actors;
		
		this.ratingManager = new RatingManager(1, 5);
		this.type = MovieType.LiveAction;
	}
	
	public Movie (String name,	String director, String releaseDate, ArrayList<String> animators, int recommendedViewerAge) {
		this.name = name;
		this.director = director;
		this.releaseDate = releaseDate;
		this.animators = animators;
		this.recommendedViewerAge = recommendedViewerAge;
		
		this.ratingManager = new RatingManager(1, 10);
		this.type = MovieType.Cartoon;
	}
	
	public RatingManager getRatingManager() {
		return this.ratingManager;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getDirector() {
		return this.director;
	}
	
	public String getReleaseDate() {
		return this.releaseDate;
	}
	
	public ArrayList<String> getActors() {
		return this.actors;
	}
	
	public String getPrettyActors() {
		if (this.actors == null) {
			return "";
		}
		
		String prettyActors = "";
		for (int i=0; i<this.actors.size(); i++) {
			prettyActors += this.actors.get(i);
			if (i != this.actors.size()-1) {
				prettyActors += ", ";
			}
		}
		
		return prettyActors;
	}
	
	public String getPrettyAnimators() {
		if (this.animators == null) {
			return "";
		}
		
		String prettyAnimators = "";
		for (int i=0; i<this.animators.size(); i++) {
			prettyAnimators += this.animators.get(i);
			if (i != this.animators.size()-1) {
				prettyAnimators += ", ";
			}
		}
		
		return prettyAnimators;
	}
	
	public ArrayList<String> getAnimators() {
		return this.animators;
	}
	
	public int getRecommendedViewerAge() {
		return this.recommendedViewerAge;
	}
	
	public MovieType getType() {
		return this.type;
	}
	
	public void saveToFile() {
		try (
				FileOutputStream fos = new FileOutputStream(new File("saved-movies","sm-" + new Date().getTime() +".dat"));
			    ObjectOutputStream oos = new ObjectOutputStream(fos);
    		 ) {
			    oos.writeObject(this);

			} catch (IOException ex) {
			    ex.printStackTrace();
			}
	}
}
