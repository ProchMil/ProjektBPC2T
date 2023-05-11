package gui;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import entities.Movie;
import entities.MovieList;
import enums.MovieType;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTable;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;

public class MoviePopup extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8832086964316985914L;

	public MoviePopup(MovieList movieList, JTable movieTable) {
		this.setSize(400, 440);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);  
		this.setLayout(null);
		
		JPanel cartoonPage = this.initCartoonPage(movieList, movieTable);
		JPanel liveActionPage = this.initLiveActionPage(movieList, movieTable);
		
		JTabbedPane tabbedPane = new JTabbedPane();
		tabbedPane.add("Live Action", liveActionPage);
		tabbedPane.add("Cartoon", cartoonPage);
		tabbedPane.setBounds(0,0, 383, 400);
		
		this.add(tabbedPane);
	}
	
	public MoviePopup(MovieList movieList, JTable movieTable, Movie movie) {
		this.setSize(400, 440);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);  
		this.setLayout(null);
		
		JPanel cartoonPage = this.initCartoonPage(movieList, movieTable, movie);
		JPanel liveActionPage = this.initLiveActionPage(movieList, movieTable, movie);
		
		if (movie.getType() == MovieType.Cartoon) {
			this.add(cartoonPage);
		}
		else if (movie.getType() == MovieType.LiveAction) {
			this.add(liveActionPage);			
		}
		
	}
	
	private JPanel initLiveActionPage(MovieList movieList, JTable movieTable) {
		JPanel liveActionPage = new JPanel(null);
		liveActionPage.setBounds(0,0, 400, 440);
		
		JLabel labelForName = new JLabel("Name: ");
		JTextField nameField = new JTextField();
		labelForName.setBounds(10,  10, 358, 30);
		nameField.setBounds(10, 35, 358, 30);
		
		JLabel labelForDirector = new JLabel("Director: ");
		JTextField directorField = new JTextField();
		labelForDirector.setBounds(10,  65, 358, 30);
		directorField.setBounds(10, 90, 358, 30);
		
		JLabel labelForReleaseDate = new JLabel("Release Date: ");
		JTextField releaseDateField = new JTextField();
		labelForReleaseDate.setBounds(10,  120, 358, 30);
		releaseDateField.setBounds(10, 145, 358, 30);
		
		JLabel labelForActors = new JLabel("Actors: (separate with comma)");
		JTextField actorsField = new JTextField();
		labelForActors.setBounds(10,  175, 358, 30);
		actorsField.setBounds(10, 200, 358, 30);
		
		JButton saveButton = new JButton("Save");
		saveButton.setBounds(266, 330, 100, 30);
		saveButton.setSize(100, 30);
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// TODO: Validate fields.
				
				String[] actors = actorsField.getText().split("\\s*,\\s*");
				ArrayList<String> actorsList = new ArrayList<String>();
				for (int i=0; i<actors.length; i++) {
					actorsList.add(actors[i]);
				}
				
				Movie movieToSave = new Movie(nameField.getText(), directorField.getText(), releaseDateField.getText(), actorsList);
				movieList.addMovie(movieToSave, movieTable);
				dispose();
			}
		});
		
		liveActionPage.add(labelForName);
		liveActionPage.add(nameField);
		liveActionPage.add(labelForDirector);
		liveActionPage.add(directorField);
		liveActionPage.add(labelForReleaseDate);
		liveActionPage.add(releaseDateField);
		liveActionPage.add(labelForActors);
		liveActionPage.add(actorsField);
		liveActionPage.add(saveButton);
		
		return liveActionPage;
	}
	
	private JPanel initCartoonPage(MovieList movieList, JTable movieTable) {
		JPanel cartoonPage = new JPanel(null);
		cartoonPage.setBounds(0,0, 400, 440);
		
		JLabel labelForName = new JLabel("Name: ");
		JTextField nameField = new JTextField();
		labelForName.setBounds(10,  10, 358, 30);
		nameField.setBounds(10, 35, 358, 30);
		
		JLabel labelForDirector = new JLabel("Director: ");
		JTextField directorField = new JTextField();
		labelForDirector.setBounds(10,  65, 358, 30);
		directorField.setBounds(10, 90, 358, 30);
		
		JLabel labelForReleaseDate = new JLabel("Release Date: ");
		JTextField releaseDateField = new JTextField();
		labelForReleaseDate.setBounds(10,  120, 358, 30);
		releaseDateField.setBounds(10, 145, 358, 30);
		
		JLabel labelForAnimators = new JLabel("Animators: (separate with comma)");
		JTextField animatorsField = new JTextField();
		labelForAnimators.setBounds(10,  175, 358, 30);
		animatorsField.setBounds(10, 200, 358, 30);
		
		JLabel labelForRecommendedViewerAge = new JLabel("Recommended Viewer Age: ");
		JTextField recommendedViewerAgeField = new JTextField();
		labelForRecommendedViewerAge.setBounds(10,  230, 358, 30);
		recommendedViewerAgeField.setBounds(10, 255, 358, 30);
		
		JButton saveButton = new JButton("Save");
		saveButton.setBounds(266, 330, 100, 30);
		saveButton.setSize(100, 30);
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// TODO: Validate fields.
				
				String[] animators = animatorsField.getText().split("\\s*,\\s*");
				ArrayList<String> animatorsList = new ArrayList<String>();
				for (int i=0; i<animators.length; i++) {
					animatorsList.add(animators[i]);
				}
				
				Movie movieToSave = new Movie(nameField.getText(), directorField.getText(), releaseDateField.getText(), animatorsList, Integer.parseInt(recommendedViewerAgeField.getText()));
				movieList.addMovie(movieToSave, movieTable);
				dispose();
			}
		});
		
		cartoonPage.add(labelForName);
		cartoonPage.add(nameField);
		cartoonPage.add(labelForDirector);
		cartoonPage.add(directorField);
		cartoonPage.add(labelForReleaseDate);
		cartoonPage.add(releaseDateField);
		cartoonPage.add(labelForAnimators);
		cartoonPage.add(animatorsField);
		cartoonPage.add(labelForRecommendedViewerAge);
		cartoonPage.add(recommendedViewerAgeField);
		cartoonPage.add(saveButton);
		
		return cartoonPage;
	}
	
	private JPanel initLiveActionPage(MovieList movieList, JTable movieTable, Movie movie) {
		JPanel liveActionPage = new JPanel(null);
		liveActionPage.setBounds(0,0, 400, 440);
		
		JLabel labelForName = new JLabel("Name: ");
		JTextField nameField = new JTextField();
		nameField.setText(movie.getName());
		labelForName.setBounds(10,  10, 358, 30);
		nameField.setBounds(10, 35, 358, 30);
		
		JLabel labelForDirector = new JLabel("Director: ");
		JTextField directorField = new JTextField();
		directorField.setText(movie.getDirector());
		labelForDirector.setBounds(10,  65, 358, 30);
		directorField.setBounds(10, 90, 358, 30);
		
		JLabel labelForReleaseDate = new JLabel("Release Date: ");
		JTextField releaseDateField = new JTextField();
		releaseDateField.setText(movie.getReleaseDate());
		labelForReleaseDate.setBounds(10,  120, 358, 30);
		releaseDateField.setBounds(10, 145, 358, 30);
		
		JLabel labelForActors = new JLabel("Actors: (separate with comma)");
		JTextField actorsField = new JTextField();
		actorsField.setText(movie.getPrettyActors());
		labelForActors.setBounds(10,  175, 358, 30);
		actorsField.setBounds(10, 200, 358, 30);
		
		JButton saveButton = new JButton("Save");
		saveButton.setBounds(266, 330, 100, 30);
		saveButton.setSize(100, 30);
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// TODO: Validate fields.
				
				String[] actors = actorsField.getText().split("\\s*,\\s*");
				ArrayList<String> actorsList = new ArrayList<String>();
				for (int i=0; i<actors.length; i++) {
					actorsList.add(actors[i]);
				}
				
				Movie movieToSave = new Movie(nameField.getText(), directorField.getText(), releaseDateField.getText(), actorsList);
				movieList.editMovie(
					movieList.indexOf(movie), 
					movieToSave, 
					movieTable
				);
				dispose();
			}
		});
		
		liveActionPage.add(labelForName);
		liveActionPage.add(nameField);
		liveActionPage.add(labelForDirector);
		liveActionPage.add(directorField);
		liveActionPage.add(labelForReleaseDate);
		liveActionPage.add(releaseDateField);
		liveActionPage.add(labelForActors);
		liveActionPage.add(actorsField);
		liveActionPage.add(saveButton);
		
		return liveActionPage;
	}
	
	private JPanel initCartoonPage(MovieList movieList, JTable movieTable, Movie movie) {
		JPanel cartoonPage = new JPanel(null);
		cartoonPage.setBounds(0,0, 400, 440);
		
		JLabel labelForName = new JLabel("Name: ");
		JTextField nameField = new JTextField();
		nameField.setText(movie.getName());
		labelForName.setBounds(10,  10, 358, 30);
		nameField.setBounds(10, 35, 358, 30);
		
		JLabel labelForDirector = new JLabel("Director: ");
		JTextField directorField = new JTextField();
		directorField.setText(movie.getDirector());
		labelForDirector.setBounds(10,  65, 358, 30);
		directorField.setBounds(10, 90, 358, 30);
		
		JLabel labelForReleaseDate = new JLabel("Release Date: ");
		JTextField releaseDateField = new JTextField();
		releaseDateField.setText(movie.getReleaseDate());
		labelForReleaseDate.setBounds(10,  120, 358, 30);
		releaseDateField.setBounds(10, 145, 358, 30);
		
		JLabel labelForAnimators = new JLabel("Animators: (separate with comma)");
		JTextField animatorsField = new JTextField();
		animatorsField.setText(movie.getPrettyAnimators());
		labelForAnimators.setBounds(10,  175, 358, 30);
		animatorsField.setBounds(10, 200, 358, 30);
		
		JLabel labelForRecommendedViewerAge = new JLabel("Recommended Viewer Age: ");
		JTextField recommendedViewerAgeField = new JTextField();
		recommendedViewerAgeField.setText(Integer.toString(movie.getRecommendedViewerAge()));
		labelForRecommendedViewerAge.setBounds(10,  230, 358, 30);
		recommendedViewerAgeField.setBounds(10, 255, 358, 30);
		
		JButton saveButton = new JButton("Save");
		saveButton.setBounds(266, 330, 100, 30);
		saveButton.setSize(100, 30);
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// TODO: Validate fields.
				
				String[] animators = animatorsField.getText().split("\\s*,\\s*");
				ArrayList<String> animatorsList = new ArrayList<String>();
				for (int i=0; i<animators.length; i++) {
					animatorsList.add(animators[i]);
				}
				
				Movie movieToSave = new Movie(nameField.getText(), directorField.getText(), releaseDateField.getText(), animatorsList, Integer.parseInt(recommendedViewerAgeField.getText()));
				movieList.editMovie(movieList.indexOf(movie), movieToSave, movieTable);
				dispose();
			}
		});
		
		cartoonPage.add(labelForName);
		cartoonPage.add(nameField);
		cartoonPage.add(labelForDirector);
		cartoonPage.add(directorField);
		cartoonPage.add(labelForReleaseDate);
		cartoonPage.add(releaseDateField);
		cartoonPage.add(labelForAnimators);
		cartoonPage.add(animatorsField);
		cartoonPage.add(labelForRecommendedViewerAge);
		cartoonPage.add(recommendedViewerAgeField);
		cartoonPage.add(saveButton);
		
		return cartoonPage;
	}
	
	
}
