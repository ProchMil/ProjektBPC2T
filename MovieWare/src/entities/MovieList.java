package entities;

import java.util.ArrayList;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class MovieList extends ArrayList<Movie> {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6102974697465494853L;
	
	public void addMovie(Movie movieToSave, JTable movieTable) {
		this.add(movieToSave);
		
		DefaultTableModel tableModel =  (DefaultTableModel) movieTable.getModel();
		Object[] movieObject = this.movieToTableObject(movieToSave);
		tableModel.addRow(movieObject);
	}
	
	public void removeMovie(JTable movieTable) {
		int selectedRowIndex = movieTable.getSelectedRow();
		// Returns the index of the first selected row, -1 if no row is selected 
		if (selectedRowIndex == -1) {
			return;
		}
		
		int idOfSelectedMovie = (int) movieTable.getValueAt(selectedRowIndex, 0);
		
		DefaultTableModel tableModel = (DefaultTableModel) movieTable.getModel();
		
		tableModel.removeRow(selectedRowIndex);
		this.set(idOfSelectedMovie, null);
	}
	
	public void editMovie(int movieId, Movie editedMovie, JTable movieTable) {
		DefaultTableModel tableModel = (DefaultTableModel) movieTable.getModel();
		
		int selectedRow = -1;
		for(int i=0; i<tableModel.getRowCount(); i++) {
			int foundId = (int) tableModel.getValueAt(i, 0);
			if (foundId == movieId) {
				selectedRow = i;
				break;
			}
		}
		
		if (selectedRow == -1) {
			return;
		}
		
		tableModel.setValueAt(editedMovie.getName(), selectedRow, 1);
		tableModel.setValueAt(editedMovie.getDirector(), selectedRow, 2);
		tableModel.setValueAt(editedMovie.getReleaseDate(), selectedRow, 3);
		tableModel.setValueAt(editedMovie.getPrettyActors(), selectedRow, 4);
		tableModel.setValueAt(editedMovie.getPrettyAnimators(), selectedRow, 5);
		tableModel.setValueAt(editedMovie.getRecommendedViewerAge(), selectedRow, 6);
		tableModel.setValueAt(editedMovie.getRatingManager().getAverageRating(), selectedRow, 7);
		
		this.set(movieId, editedMovie);
	}
	
	public Object[] movieToTableObject(Movie movie) {
		Object[] rowData = new Object[8];
		
		rowData[0] = this.indexOf(movie);
		rowData[1] = movie.getName();
		rowData[2] = movie.getDirector();
		rowData[3] = movie.getReleaseDate();
		rowData[4] = movie.getPrettyActors();
		rowData[5] = movie.getPrettyAnimators();
		rowData[6] = movie.getRecommendedViewerAge();
		rowData[7] = movie.getRatingManager().getAverageRating();
		
		return rowData;
	}
}
