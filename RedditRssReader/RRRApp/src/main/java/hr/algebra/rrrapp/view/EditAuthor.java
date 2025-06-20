/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package hr.algebra.rrrapp.view;

import hr.algebra.dao.AuthorRepository;
import hr.algebra.dao.RepositoryFactory;
import hr.algebra.dao.model.Author;
import hr.algebra.rrrapp.view.model.AuthorTableModel;
import hr.algebra.utilities.MessageUtils;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.ListSelectionModel;
import javax.swing.text.JTextComponent;

/**
 *
 * @author miki
 */
public class EditAuthor extends javax.swing.JPanel {

    private Map<JTextComponent, JLabel> validationMap;
    private AuthorTableModel authorTableModel;
    private AuthorRepository authorRepo;
    private Author selectedAuthor;
    
    /**
     * Creates new form EditAuthor
     */
    public EditAuthor() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tbAuthors = new javax.swing.JTable();
        tfAuthorName = new javax.swing.JTextField();
        tfAuthorLink = new javax.swing.JTextField();
        lbAuthorLink = new javax.swing.JLabel();
        lbAuthorName = new javax.swing.JLabel();
        btnAuthorAdd = new javax.swing.JButton();
        btnAuthorUpdate = new javax.swing.JButton();
        btnAuthorDelete = new javax.swing.JButton();
        lbAuthorNameError = new javax.swing.JLabel();
        lbAuthorLinkError = new javax.swing.JLabel();

        setPreferredSize(new java.awt.Dimension(900, 600));
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                formComponentShown(evt);
            }
        });

        tbAuthors.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tbAuthors.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbAuthorsMouseClicked(evt);
            }
        });
        tbAuthors.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tbAuthorsKeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(tbAuthors);

        lbAuthorLink.setText("Author Link");

        lbAuthorName.setText("Author Name");

        btnAuthorAdd.setText("Add");
        btnAuthorAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAuthorAddActionPerformed(evt);
            }
        });

        btnAuthorUpdate.setText("Update");
        btnAuthorUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAuthorUpdateActionPerformed(evt);
            }
        });

        btnAuthorDelete.setText("Delete");
        btnAuthorDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAuthorDeleteActionPerformed(evt);
            }
        });

        lbAuthorNameError.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lbAuthorNameError.setForeground(new java.awt.Color(255, 0, 0));
        lbAuthorNameError.setText("X");

        lbAuthorLinkError.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lbAuthorLinkError.setForeground(new java.awt.Color(255, 0, 0));
        lbAuthorLinkError.setText("X");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 900, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnAuthorAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnAuthorUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnAuthorDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30))
            .addGroup(layout.createSequentialGroup()
                .addGap(69, 69, 69)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbAuthorName)
                    .addComponent(lbAuthorLink)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(tfAuthorLink, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(lbAuthorLinkError, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(tfAuthorName, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(lbAuthorNameError, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(56, 56, 56)
                .addComponent(lbAuthorName)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tfAuthorName, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbAuthorNameError))
                .addGap(18, 18, 18)
                .addComponent(lbAuthorLink)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tfAuthorLink, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbAuthorLinkError))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 79, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAuthorAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAuthorUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAuthorDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(44, 44, 44)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 264, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void formComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentShown
        init();
    }//GEN-LAST:event_formComponentShown

    private void btnAuthorAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAuthorAddActionPerformed
        if (!formValid()) {
            return;
        }
        try {
            Author author = new Author(
                tfAuthorName.getText().trim(),
                tfAuthorLink.getText().trim()
            );
            authorRepo.createAuthor(author);
            authorTableModel.setAuthors(authorRepo.selectAuthors());
            clearForm();
        } catch (Exception ex) {
            Logger.getLogger(EditAuthor.class.getName()).log(Level.SEVERE, null, ex);
            MessageUtils.showErrorMessage("Error", "Unable to create author");
        }
    }//GEN-LAST:event_btnAuthorAddActionPerformed

    private void btnAuthorUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAuthorUpdateActionPerformed
        if (selectedAuthor == null) {
            MessageUtils.showErrorMessage("Wronk operation", "Please choose author first");
            return;
        }
        if (!formValid()) {
            return;
        }
        try {
            selectedAuthor.setName(tfAuthorName.getText().trim());
            selectedAuthor.setLink(tfAuthorLink.getText().trim());
            
            authorRepo.updateAuthor(selectedAuthor.getId(), selectedAuthor);
            authorTableModel.setAuthors(authorRepo.selectAuthors());
            clearForm();
        } catch (Exception ex) {
            Logger.getLogger(EditAuthor.class.getName()).log(Level.SEVERE, null, ex);
            MessageUtils.showErrorMessage("Error", "Unable to update author");
        }
    }//GEN-LAST:event_btnAuthorUpdateActionPerformed

    private void btnAuthorDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAuthorDeleteActionPerformed
        if (selectedAuthor == null) {
            MessageUtils.showErrorMessage("Wronk operation", "Please choose author first");
            return;
        }
        if (MessageUtils.showConfirmDialog("Delete author", "Fr fr?")) {
            try {
                authorRepo.deleteAuthor(selectedAuthor.getId());
                authorTableModel.setAuthors(authorRepo.selectAuthors());
                clearForm();
            } catch (Exception ex) {
                Logger.getLogger(EditAuthor.class.getName()).log(Level.SEVERE, null, ex);
                MessageUtils.showErrorMessage("Error", "Unable to delete author");
            }
        }
    }//GEN-LAST:event_btnAuthorDeleteActionPerformed

    private void tbAuthorsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbAuthorsMouseClicked
        showAuthor();
    }//GEN-LAST:event_tbAuthorsMouseClicked

    private void tbAuthorsKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbAuthorsKeyReleased
        showAuthor();
    }//GEN-LAST:event_tbAuthorsKeyReleased

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAuthorAdd;
    private javax.swing.JButton btnAuthorDelete;
    private javax.swing.JButton btnAuthorUpdate;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lbAuthorLink;
    private javax.swing.JLabel lbAuthorLinkError;
    private javax.swing.JLabel lbAuthorName;
    private javax.swing.JLabel lbAuthorNameError;
    private javax.swing.JTable tbAuthors;
    private javax.swing.JTextField tfAuthorLink;
    private javax.swing.JTextField tfAuthorName;
    // End of variables declaration//GEN-END:variables

    private void init() {
        try {
            initValidation();
            clearForm();
            hideErrors();
            initRepo();
            initTable();
        } catch (Exception ex) {
            Logger.getLogger(EditAuthor.class.getName()).log(Level.SEVERE, null, ex);
            MessageUtils.showErrorMessage("Unrecoverable error", "Cannot initiate the form");
        }
    }
    
    private void initValidation() {
        validationMap = new HashMap<>();
        
        validationMap.put(tfAuthorName, lbAuthorNameError);
        validationMap.put(tfAuthorLink, lbAuthorLinkError);
    }

    private void hideErrors() {
        validationMap.forEach((key, val) -> val.setVisible(false));
    }

    private void clearForm() {
        hideErrors();
        validationMap.forEach((key, val) -> key.setText(""));
        selectedAuthor = null;
    }

    private void initRepo() throws Exception {
        authorRepo = RepositoryFactory.getAuthorRepo();
    }
    
    private void initTable() throws Exception {
        tbAuthors.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tbAuthors.setAutoCreateRowSorter(true);
        tbAuthors.setRowHeight(25);
        authorTableModel = new AuthorTableModel(authorRepo.selectAuthors());
        tbAuthors.setModel(authorTableModel);
    }
    
    private boolean formValid() {
        hideErrors();
        boolean ok = true;

        for (Map.Entry<JTextComponent, JLabel> entry : validationMap.entrySet()) {
            JTextComponent key = entry.getKey();
            JLabel val = entry.getValue();

            boolean invalidInput = key.getText().trim().isEmpty();
            val.setVisible(invalidInput);
            if (invalidInput) {
                ok = false;
            }
        }

        return ok;
    }
    
    private void showAuthor() {
        clearForm();
        int selectedRow = tbAuthors.getSelectedRow();
        int rowIndex = tbAuthors.convertRowIndexToModel(selectedRow);
        int selectedAuthorId = (int) authorTableModel.getValueAt(rowIndex, 0);
        
        try {
            Optional<Author> optAuthor = authorRepo.selectAuthor(selectedAuthorId);
            if (optAuthor.isPresent()) {
                selectedAuthor = optAuthor.get();
                fillForm(selectedAuthor);
            }
        } catch (Exception ex) {
            Logger.getLogger(EditAuthor.class.getName()).log(Level.SEVERE, null, ex);
            MessageUtils.showErrorMessage("Error", "Unable to show author");
        }
    }

    private void fillForm(Author author) {
        tfAuthorName.setText(author.getName());
        tfAuthorLink.setText(author.getLink());
    }
}
