package gui;

import javax.swing.JScrollPane;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.JLabel;
import javax.swing.JFileChooser;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import application.Database;
import entities.Movie;
import entities.MovieList;

public class MainFrame extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8026416994513756565L;
	private JTable movieTable;
	
	public MainFrame(MovieList movieList, Database db) {
		super("MovieWare");
		this.configureMainFrame();
		
		this.addWindowListener(new WindowAdapter(){
	        public void windowClosing(WindowEvent e){
	            try {
					db.truncateDB();
					
					for(int i=0; i<movieList.size(); i++) {
						db.insertMovie(movieList.get(i));
					}
					
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
	            finally {
	            	System.exit(0);
	            }
	            
	        }
	    });
		
		JPanel moviePage = new JPanel(null);
		
		JTabbedPane tabbedPane = new JTabbedPane();
		tabbedPane.addTab("Movies", moviePage);
		tabbedPane.setBounds(0, 0, 1183, 360);
		
		this.movieTable = this.createMovieTable(movieList);
		JPanel searchPanel = this.initSearchPanel(movieTable);
		JScrollPane scrollableMovieTable = this.initScrollableMovieTable(movieTable);
		
		JButton loadButton = this.initLoadButton(movieList);
		JButton saveButton = this.initSaveButton(movieList);
		JButton addButton = this.initAddButton(movieList);
		JButton addRatingButton = this.initAddRatingButton(movieList);
		JButton editButton = this.initEditButton(movieList);
		JButton deleteButton = this.initDeleteButton(movieList);
		
		moviePage.add(searchPanel);
		moviePage.add(scrollableMovieTable);
		moviePage.add(loadButton);
		moviePage.add(saveButton);
		moviePage.add(addButton);
		moviePage.add(editButton);
		moviePage.add(deleteButton);
		moviePage.add(addRatingButton);
		
		this.add(tabbedPane);
	}
	
	private JTable createMovieTable(MovieList movieList) {
		String[] columns = {"ID", "Name", "Director", "Release Date", "Actors", "Animators", "Recommended Age", "Rating"};
		String[][] emptyData = {};
		
		DefaultTableModel movieTableModel = new DefaultTableModel(emptyData, columns);
		JTable movieTable = new JTable(movieTableModel) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 3887408488723123123L;

			public boolean isCellEditable(int row, int column) {                
                return false;               
			};
		};
		
		for (int i=0; i<movieList.size(); i++) {
			Movie currentMovie = movieList.get(i);
			movieTableModel.addRow(movieList.movieToTableObject(currentMovie));
		}
		
		return movieTable;
	}
	
	private JScrollPane initScrollableMovieTable(JTable movieTable) {
		JScrollPane scrollableMovieTablePane =  new JScrollPane(movieTable);
		scrollableMovieTablePane.setBounds(10, 35, 1165, 200);
		return scrollableMovieTablePane;
	}
	
	private JPanel initSearchPanel(JTable table) {
		JPanel searchPanel = new JPanel(null);
		searchPanel.setBounds(0, 0, 1200, 40);
		
		JLabel filterLabel = new JLabel("Search: ");
		filterLabel.setBounds(10, 5, 100, 23);
		
		JTextField textFilter = new JTextField();
		textFilter.setBounds(60, 5, 400, 23);
		
		
		TableRowSorter<TableModel> rowSorter = new TableRowSorter<>(table.getModel());
		rowSorter.toggleSortOrder(7);
		rowSorter.toggleSortOrder(7);
		table.setRowSorter(rowSorter);
		
		textFilter.getDocument().addDocumentListener(new DocumentListener() {
			@Override
            public void insertUpdate(DocumentEvent e) {
                String text = textFilter.getText();

                if (text.trim().length() == 0) {
                    rowSorter.setRowFilter(null);
                } else {
                    rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                String text = textFilter.getText();

                if (text.trim().length() == 0) {
                    rowSorter.setRowFilter(null);
                } else {
                    rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                }
            }
            
            @Override
            public void changedUpdate(DocumentEvent e) {
                throw new UnsupportedOperationException("Not implemented.");
            }
		});
		
		
		searchPanel.add(filterLabel);
		searchPanel.add(textFilter);
		
		return searchPanel;
	}
	
	private JButton initAddRatingButton(MovieList movieList) {
		JButton addButton = new JButton("Add Rating");
		
		addButton.setBounds(760, 239, 100, 20);
		addButton.getModel().addActionListener(new ActionListener() {
			@Override
            public void actionPerformed(ActionEvent e) {
				int selectedRow = movieTable.getSelectedRow();
				if (selectedRow == -1) {
					return;
				}
				int selectedMovieId = (int) movieTable.getValueAt(selectedRow, 0);
				
				new RatingPopup(selectedMovieId, movieList, movieTable);
			}
		});
		
		return addButton;
	}
	
	private JButton initAddButton(MovieList movieList) {
		JButton addButton = new JButton("Add Movie");
		
		addButton.setBounds(1075, 239, 100, 20);
		addButton.getModel().addActionListener(new ActionListener() {
			@Override
            public void actionPerformed(ActionEvent e) {
				new MoviePopup(movieList, movieTable);
			}
		});
		
		return addButton;
	}
	
	private JButton initEditButton(MovieList movieList) {
		JButton editButton = new JButton("Edit Movie");
		
		editButton.setBounds(970, 239, 100, 20);
		editButton.getModel().addActionListener(new ActionListener() {
			@Override
            public void actionPerformed(ActionEvent e) {
				int selectedRow = movieTable.getSelectedRow();
				if (selectedRow == -1) {
					return;
				}
				
				int selectedMovieId = (int) movieTable.getValueAt(selectedRow, 0);
				new MoviePopup(movieList, movieTable, movieList.get(selectedMovieId));
			}
		});
		
		return editButton;
	}
	
	private JButton initDeleteButton(MovieList movieList) {
		JButton deleteButton = new JButton("Delete Movie");
		
		deleteButton.setBounds(865, 239, 100, 20);
		deleteButton.getModel().addActionListener(new ActionListener() {
			@Override
            public void actionPerformed(ActionEvent e) {
				movieList.removeMovie(movieTable);
			}
		});
		
		return deleteButton;
	}
	
	private JButton initLoadButton(MovieList movieList) {
		JButton loadButton = new JButton("Load Movie");
		
		loadButton.setBounds(10, 239, 100, 20);
		loadButton.getModel().addActionListener(new ActionListener() {
			@Override
            public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				int fileChooserResult = fileChooser.showOpenDialog(null);
				
				if (fileChooserResult == JFileChooser.APPROVE_OPTION) 
	            {
					try {
						FileInputStream fi = new FileInputStream(fileChooser.getSelectedFile());
						ObjectInputStream oi = new ObjectInputStream(fi);
						
						Movie loadedMovie = (Movie) oi.readObject();
						
						movieList.addMovie(loadedMovie, movieTable);
						
					}
					catch (IOException | ClassNotFoundException e1) {
						System.out.println("File could not have been loaded!");
					}
	    			
	    			
	            }
			}
		});
		
		return loadButton;
	}
	
	private JButton initSaveButton(MovieList movieList) {
		JButton saveButton = new JButton("Save Movie");
		
		saveButton.setBounds(115, 239, 100, 20);
		saveButton.getModel().addActionListener(new ActionListener() {
			@Override
            public void actionPerformed(ActionEvent e) {
				int selectedRow = movieTable.getSelectedRow();
				if (selectedRow == -1) {
					return;
				}
				
				int selectedMovieId = (int) movieTable.getValueAt(selectedRow, 0);
				movieList.get(selectedMovieId).saveToFile();
			}
		});
		
		return saveButton;
	}
	
	private void configureMainFrame() {
		this.setLayout(null);
		this.setSize(1200, 400);
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);  
		this.setVisible(true);
	}
}
