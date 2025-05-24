/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hr.algebra.dao;

import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author miki
 */
public final class RepositoryFactory {
    private static final Properties properties = new Properties();
    private static final String PATH = "/config/repository.properties";
    
    private static final String USER_CLASS_NAME = "USER_CLASS_NAME";
    private static final String AUTHOR_CLASS_NAME = "AUTHOR_CLASS_NAME";
    private static final String COMMENT_CLASS_NAME = "COMMENT_CLASS_NAME";
    private static final String POST_CLASS_NAME = "POST_CLASS_NAME";
    private static final String ADMIN_CLASS_NAME = "ADMIN_CLASS_NAME";

    private static UserRepository userRepo;
    private static AuthorRepository authorRepo;
    private static CommentRepository commentRepo;
    private static PostRepository postRepo;
    private static AdministrationRepository adminRepo;

    static {
        try (InputStream is = RepositoryFactory.class.getResourceAsStream(PATH)) {
            properties.load(is);
            userRepo = (UserRepository) Class
                    .forName(properties.getProperty(USER_CLASS_NAME))
                    .getDeclaredConstructor()
                    .newInstance();
            authorRepo = (AuthorRepository) Class
                    .forName(properties.getProperty(AUTHOR_CLASS_NAME))
                    .getDeclaredConstructor()
                    .newInstance();
            commentRepo = (CommentRepository) Class
                    .forName(properties.getProperty(COMMENT_CLASS_NAME))
                    .getDeclaredConstructor()
                    .newInstance();
            postRepo = (PostRepository) Class
                    .forName(properties.getProperty(POST_CLASS_NAME))
                    .getDeclaredConstructor()
                    .newInstance();
            adminRepo = (AdministrationRepository) Class
                    .forName(properties.getProperty(ADMIN_CLASS_NAME))
                    .getDeclaredConstructor()
                    .newInstance();
        } catch (Exception ex) {
            Logger.getLogger(RepositoryFactory.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static UserRepository getUserRepo() {
        return userRepo;
    }

    public static AuthorRepository getAuthorRepo() {
        return authorRepo;
    }

    public static CommentRepository getCommentRepo() {
        return commentRepo;
    }

    public static PostRepository getPostRepo() {
        return postRepo;
    }
    
    public static AdministrationRepository getAdminRepo() {
        return adminRepo;
    }
}
