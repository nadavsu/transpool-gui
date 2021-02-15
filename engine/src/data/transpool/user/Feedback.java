package data.transpool.user;

import javafx.beans.property.*;


public class Feedback {
    private IntegerProperty feedbackerID;
    private StringProperty feedbackerName;
    private IntegerProperty rating;
    private StringProperty comment;

    public Feedback(int feedbackerID, String feedbackName, int rating, String comment) {
        this.feedbackerID = new SimpleIntegerProperty(feedbackerID);
        this.feedbackerName = new SimpleStringProperty(feedbackName);
        this.rating = new SimpleIntegerProperty(rating);
        this.comment = new SimpleStringProperty(comment);
    }

    public int getRating() {
        return rating.get();
    }

    public IntegerProperty ratingProperty() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating.set(rating);
    }

    public String getComment() {
        return comment.get();
    }

    public StringProperty commentProperty() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment.set(comment);
    }

    public String getFeedbackerName() {
        return feedbackerName.get();
    }

    public StringProperty feedbackerNameProperty() {
        return feedbackerName;
    }

    public void setFeedbackerName(String feedbackerName) {
        this.feedbackerName.set(feedbackerName);
    }

    public int getFeedbackerID() {
        return feedbackerID.get();
    }

    public IntegerProperty feedbackerIDProperty() {
        return feedbackerID;
    }

    public void setFeedbackerID(int feedbackerID) {
        this.feedbackerID.set(feedbackerID);
    }

    @Override
    public String toString() {
        return getFeedbackerName() + "\n" +
                "Rating: " + getRating() + "\n" +
                "Comment: " + getComment();
    }
}
