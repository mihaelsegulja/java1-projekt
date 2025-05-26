/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hr.algebra.rrrapp.view.model;

import hr.algebra.dao.model.Comment;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author miki
 */
public class CommentTableModel extends AbstractTableModel {

    private final String[] COLUMN_NAMES = {
        "ID",
        "Title",
        "Link",
        "Reddit ID",
        "Subreddit",
        "Author Name",
        "Author Link",
        "Date Updated",
        "Content"
    };
    
    private List<Comment> comments;

    public CommentTableModel(List<Comment> comments) {
        this.comments = comments;
    }
    
    public void setComments(List<Comment> comments) {
        this.comments = comments;
        fireTableDataChanged();
    }
    
    @Override
    public int getRowCount() {
        return comments.size();
    }

    @Override
    public int getColumnCount() {
        return COLUMN_NAMES.length;    
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0:
                return comments.get(rowIndex).getId();
            case 1:
                return comments.get(rowIndex).getTitle();
            case 2:
                return comments.get(rowIndex).getLink();
            case 3:
                return comments.get(rowIndex).getRedditId();
            case 4:
                return comments.get(rowIndex).getSubredditName();
            case 5:
                return comments.get(rowIndex).getAuthor().getName();
            case 6:
                return comments.get(rowIndex).getAuthor().getLink();
            case 7:
                return comments.get(rowIndex).getUpdatedDate().format(Comment.DATE_FORMATTER);
            case 8:
                return comments.get(rowIndex).getContent();
            default:
                throw new RuntimeException("No such column");
        }
    }
    
    @Override
    public String getColumnName(int column) {
        return COLUMN_NAMES[column];
    }
    
    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return Integer.class;
        }
        return super.getColumnClass(columnIndex); 
    }    
}
