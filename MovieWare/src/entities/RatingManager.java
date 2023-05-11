package entities;
import java.io.Serializable;
import java.util.ArrayList;

public class RatingManager implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7549237555067471826L;
	public int minRating;
	public int maxRating;
	ArrayList<Rating> ratings;
	
	public RatingManager(int minRating, int maxRating) {
		this.ratings = new ArrayList<Rating>();
		
		this.minRating = minRating;
		this.maxRating = maxRating;
	}
	
	public void addRating(Rating rating) {
		boolean shouldAddRating = this.validateRatingValue(rating.value);
		if (shouldAddRating == false) {
			return;
		}
		
		this.ratings.add(rating);
	}
	
	public ArrayList<Rating> getRatings() {
		return this.ratings;
	}
	
	public double getAverageRating() {
		int ratingsCount = this.ratings.size();
		if (ratingsCount == 0) {
			return 0d;
		}
		
		double ratingsSum = 0d;
		for (int i=0; i<ratingsCount; i++) {
			ratingsSum += this.ratings.get(i).value;
		}
		
		return ratingsSum / ratingsCount;
	}
	
	private boolean validateRatingValue(int value) {
		if (value > this.maxRating || value < this.minRating) {
			return false;
		}
		
		return true;
	}
	
}
