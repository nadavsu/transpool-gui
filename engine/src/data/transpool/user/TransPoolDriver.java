package data.transpool.user;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class TransPoolDriver extends TransPoolUserAccount implements Feedbackable {

    private static int IDGenerator = 30000;
    private ObservableList<Feedback> feedbacks;
    private DoubleProperty averageRating;
    private double totalRating;

    public TransPoolDriver(String username) {
        super(username);
        this.setID(IDGenerator++);
        this.feedbacks = FXCollections.observableArrayList();
        this.averageRating = new SimpleDoubleProperty(0);
        this.totalRating = 0;
    }

    public TransPoolDriver(TransPoolDriver other) {
        super(other.username.get());
        this.setID(other.getID());
        this.feedbacks = FXCollections.observableArrayList(other.feedbacks);
        this.averageRating = new SimpleDoubleProperty(other.getAverageRating());
        this.totalRating = other.totalRating;
    }

    @Override
    public ObservableList<Feedback> getAllFeedbacks() {
        return feedbacks;
    }

    @Override
    public double getAverageRating() {
        return averageRating.get();
    }

    @Override
    public DoubleProperty averageRatingProperty() {
        return averageRating;
    }

    @Override
    public void addFeedback(Feedback feedback) {
        this.feedbacks.add(feedback);
        this.totalRating += feedback.getRating();
        this.averageRating.set(totalRating / feedbacks.size());
    }

    @Override
    public Feedback getFeedback(int riderID) {
        return feedbacks
                .stream()
                .filter(feedback -> feedback.getFeedbackerID() == riderID)
                .findFirst()
                .orElse(null);
    }
}

