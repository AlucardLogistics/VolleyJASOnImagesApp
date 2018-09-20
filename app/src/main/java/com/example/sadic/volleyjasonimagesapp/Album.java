package com.example.sadic.volleyjasonimagesapp;

public class Album {

    String albumId, id, title, img, thumb;

    public Album() {
    }

    public Album(String albumId, String id, String title, String img, String thumb) {
        this.albumId = albumId;
        this.id = id;
        this.title = title;
        this.img = img;
        this.thumb = thumb;
    }

    public Album(String albumId, String id, String title) {
        this.albumId = albumId;
        this.id = id;
        this.title = title;
    }

    public String getAlbumId() {
        return albumId;
    }

    public void setAlbumId(String albumId) {
        this.albumId = albumId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }
}
