/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hr.algebra.rrrapp.view.model;

import hr.algebra.dao.model.Post;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;

/**
 *
 * @author miki
 */
public class PostTransferable implements Transferable {
    public static final DataFlavor POST_FLAVOR = new DataFlavor(Post.class, "Post");
    private final Post post;

    public PostTransferable(Post post) {
        this.post = post;
    }

    @Override
    public DataFlavor[] getTransferDataFlavors() {
        return new DataFlavor[]{POST_FLAVOR};
    }

    @Override
    public boolean isDataFlavorSupported(DataFlavor flavor) {
        return POST_FLAVOR.equals(flavor);
    }

    @Override
    public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException {
        if (POST_FLAVOR.equals(flavor)) {
            return post;
        }
        throw new UnsupportedFlavorException(flavor);
    }
}
