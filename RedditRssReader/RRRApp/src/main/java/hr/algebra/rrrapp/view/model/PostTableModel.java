/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hr.algebra.rrrapp.view.model;

import hr.algebra.dao.model.Post;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author miki
 */
public class PostTableModel extends AbstractTableModel {

    private final String[] COLUMN_NAMES = {
        "ID",
        "Title",
        "Link",
        "Reddit ID",
        "Subreddit",
        "Thumbnail Link",
        "Author Name",
        "Author Link",
        "Date Published",
        "Date Updated",
        "Content"
    };
    
    private List<Post> posts;
    
    public PostTableModel(List<Post> posts) {
        this.posts = posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
        fireTableDataChanged();
    }
    
    @Override
    public int getRowCount() {
        return posts.size();
    }

    @Override
    public int getColumnCount() {
        return COLUMN_NAMES.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0:
                return posts.get(rowIndex).getId();
            case 1:
                return posts.get(rowIndex).getTitle();
            case 2:
                return posts.get(rowIndex).getLink();
            case 3:
                return posts.get(rowIndex).getRedditId();
            case 4:
                return posts.get(rowIndex).getSubredditName();
            case 5:
                return posts.get(rowIndex).getThumbnailLink();
            case 6:
                return posts.get(rowIndex).getAuthor().getName();
            case 7:
                return posts.get(rowIndex).getAuthor().getLink();
            case 8:
                return posts.get(rowIndex).getPublishedDate().format(Post.DATE_FORMATTER);
            case 9:
                return posts.get(rowIndex).getUpdatedDate().format(Post.DATE_FORMATTER);
            case 10:
                return posts.get(rowIndex).getContent();
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
