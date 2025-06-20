/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package hr.algebra.rrrapp.view;

import hr.algebra.dao.CommentRepository;
import hr.algebra.dao.PostRepository;
import hr.algebra.dao.RepositoryFactory;
import hr.algebra.dao.model.Comment;
import hr.algebra.dao.model.Post;
import hr.algebra.rrrapp.parser.model.Entry;
import hr.algebra.rrrapp.parser.model.EntryMapper;
import hr.algebra.rrrapp.parser.rss.RedditParser;
import hr.algebra.utilities.MessageUtils;
import hr.algebra.utilities.RedditUrlUtils;
import hr.algebra.utilities.RedditUrlUtils.RedditUrlType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.text.JTextComponent;

/**
 *
 * @author miki
 */
public class UploadContent extends javax.swing.JPanel {
    
    private PostRepository postRepo;
    private CommentRepository commentRepo;
    private DefaultListModel<String> model;
    private Map<JTextComponent, JLabel> validationMap;
    
    
    /**
     * Creates new form UploadContent
     */
    public UploadContent() {
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
        lsContent = new javax.swing.JList<>();
        btnUploadContent = new javax.swing.JButton();
        lbRedditRssUrl = new javax.swing.JLabel();
        lbRedditRssUrlError = new javax.swing.JLabel();
        tfRedditRssUrl = new javax.swing.JTextField();

        setPreferredSize(new java.awt.Dimension(900, 600));
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                formComponentShown(evt);
            }
        });

        jScrollPane1.setViewportView(lsContent);

        btnUploadContent.setText("Upload content");
        btnUploadContent.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUploadContentActionPerformed(evt);
            }
        });

        lbRedditRssUrl.setText("Reddit RSS URL or subreddit name:");

        lbRedditRssUrlError.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lbRedditRssUrlError.setForeground(new java.awt.Color(255, 0, 0));
        lbRedditRssUrlError.setText("X");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(btnUploadContent, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addComponent(lbRedditRssUrl)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(tfRedditRssUrl, javax.swing.GroupLayout.DEFAULT_SIZE, 661, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbRedditRssUrlError, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
            .addComponent(jScrollPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tfRedditRssUrl, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbRedditRssUrl)
                    .addComponent(lbRedditRssUrlError, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 473, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnUploadContent, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnUploadContentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUploadContentActionPerformed
        if (!formValid()) {
            return;
        }
        new Thread(() -> {
            try {
                String url = tfRedditRssUrl.getText().trim();
                String normalizedRssUrl = RedditUrlUtils.normalizeRedditRssUrl(url);
                RedditUrlType type = RedditUrlUtils.detectRedditUrlType(normalizedRssUrl);

                List<Entry> entries = RedditParser.parse(normalizedRssUrl);

                switch (type) {
                    case SUBREDDIT -> {
                        List<Post> posts = EntryMapper.mapToPosts(entries);
                        postRepo.createPosts(posts);
                        SwingUtilities.invokeLater(() -> display(new ArrayList<>(posts)));
                    }
                    case POST -> {
                        Entry firstEntry = entries.get(0);
                        Post post = new Post(
                                firstEntry.getId(),
                                firstEntry.getRedditId(),
                                firstEntry.getTitle(),
                                firstEntry.getAuthor(),
                                firstEntry.getLink(),
                                firstEntry.getThumbnailLink(),
                                firstEntry.getContent(),
                                firstEntry.getPublishedDate(),
                                firstEntry.getUpdatedDate(),
                                firstEntry.getSubredditName()
                        );
                        Optional<Post> existingPost = postRepo.selectPosts().stream()
                                .filter(p -> post.getRedditId().equals(p.getRedditId()))
                                .findFirst();
                        if (existingPost.isEmpty()) {
                            postRepo.createPost(post);
                        }
                        entries.remove(0);
                        List<Comment> comments = EntryMapper.mapToComments(entries);
                        commentRepo.createComments(comments);
                        SwingUtilities.invokeLater(() -> display(new ArrayList<>(comments)));
                    }
                    default -> SwingUtilities.invokeLater(() -> 
                        MessageUtils.showErrorMessage("Error", "Unsupported link type")
                    );
                }
                clearForm();
                
            } catch (Exception ex) {
                Logger.getLogger(UploadContent.class.getName()).log(Level.SEVERE, null, ex);
                SwingUtilities.invokeLater(() ->
                    MessageUtils.showErrorMessage("Unrecoverable error", "Unable to upload content")
                );
            }
        }).start();
    }//GEN-LAST:event_btnUploadContentActionPerformed

    private void formComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentShown
        init();
    }//GEN-LAST:event_formComponentShown

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnUploadContent;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lbRedditRssUrl;
    private javax.swing.JLabel lbRedditRssUrlError;
    private javax.swing.JList<String> lsContent;
    private javax.swing.JTextField tfRedditRssUrl;
    // End of variables declaration//GEN-END:variables
    
    private void init() {
        try {
            initValidation();
            clearForm();
            hideErrors();
            initRepo();
            model = new DefaultListModel<>();
        } catch (Exception ex) {
            Logger.getLogger(UploadContent.class.getName()).log(Level.SEVERE, null, ex);
            MessageUtils.showErrorMessage("Unrecoverable error", "Cannot initiate the form");
        }
    }

    private <T> void display(List<T> list) {
        model.clear();
        list.forEach(itm -> model.addElement(itm.toString()));
        lsContent.setModel(model);
    }

    private void initValidation() {
        validationMap = new HashMap<>();
        
        validationMap.put(tfRedditRssUrl, lbRedditRssUrlError);
    }

    private void hideErrors() {
        validationMap.forEach((key, val) -> val.setVisible(false));
    }

    private void clearForm() {
        hideErrors();
        validationMap.forEach((key, val) -> key.setText(""));
    }
    
    private void initRepo() {
        postRepo = RepositoryFactory.getPostRepo();
        commentRepo = RepositoryFactory.getCommentRepo();
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
}
