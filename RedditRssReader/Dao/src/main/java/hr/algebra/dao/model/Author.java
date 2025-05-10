/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hr.algebra.dao.model;

/**
 *
 * @author miki
 */
public final class Author {
    private int id;
    private String name;
    private String link;

    public Author() { }

    public Author(int id, String name, String link) {
        this.id = id;
        this.name = name;
        this.link = link;
    }

    public Author(String name, String link) {
        this.name = name;
        this.link = link;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
