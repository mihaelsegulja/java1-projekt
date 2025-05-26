/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hr.algebra.rrrapp.view.model;

import hr.algebra.dao.model.Author;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author miki
 */
public class AuthorTableModel extends AbstractTableModel {

    private final String[] COLUMN_NAMES = {
        "ID",
        "Name",
        "Link"
    };
    
    private List<Author> authors;

    public AuthorTableModel(List<Author> authors) {
        this.authors = authors;
    }
    
    public void setAuthors(List<Author> authors) {
        this.authors = authors;
        fireTableDataChanged();
    }
    
    @Override
    public int getRowCount() {
        return authors.size();
    }

    @Override
    public int getColumnCount() {
        return COLUMN_NAMES.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0:
                return authors.get(rowIndex).getId();
            case 1:
                return authors.get(rowIndex).getName();
            case 2:
                return authors.get(rowIndex).getLink();
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
