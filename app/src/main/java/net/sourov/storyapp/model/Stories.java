package net.sourov.storyapp.model;

public class Stories {
    String title, details;

    public Stories() {
    }

    public Stories(String title, String detals) {
        this.title = title;
        this.details = detals;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
