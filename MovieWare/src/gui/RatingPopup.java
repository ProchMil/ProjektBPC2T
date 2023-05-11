package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextField;

import entities.Movie;
import entities.MovieList;
import entities.Rating;

public class RatingPopup extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3362505160162082871L;
	private int movieId;
	
	public RatingPopup(int movieId, MovieList movieList, JTable movieTable) {
		super("Add Rating");
		this.movieId = movieId;
		
		Movie selectedMovie = movieList.get(this.movieId);
		int minRating = selectedMovie.getRatingManager().minRating;
		int maxRating = selectedMovie.getRatingManager().maxRating;
		
		this.setSize(400, 440);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);  
		this.setLayout(null);
		
		JLabel labelForRating = new JLabel("Rating: (From " + minRating + " (worst) to " + maxRating + " (best).)");
		JTextField ratingField = new JTextField();
		labelForRating.setBounds(10,  10, 358, 30);
		ratingField.setBounds(10, 35, 358, 30);
		
		JLabel labelForComment = new JLabel("Comment: (optional)");
		JTextField commentField = new JTextField();
		labelForComment.setBounds(10,  65, 358, 30);
		commentField.setBounds(10, 90, 358, 30);
		
		JButton saveButton = new JButton("Save");
		saveButton.setBounds(266, 330, 100, 30);
		saveButton.setSize(100, 30);
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// TODO: Validate fields.
				
				selectedMovie.getRatingManager().addRating(new Rating(Integer.parseInt(ratingField.getText()), commentField.getText()));
				
				movieList.editMovie(movieId, selectedMovie, movieTable);
				
				dispose();
			}
		});
		
		this.add(labelForRating);
		this.add(ratingField);
		this.add(labelForComment);
		this.add(commentField);
		this.add(saveButton);
	}
	
	
}
