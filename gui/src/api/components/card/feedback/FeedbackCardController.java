package api.components.card.feedback;

import api.Constants;
import api.components.card.CardController;
import data.transpool.user.Feedback;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXMLLoader;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

import java.io.IOException;

public class FeedbackCardController extends CardController<Feedback> {
    @FXML private Label labelFeedbackerName;
    @FXML private TextArea textAreaComment;
    @FXML private Label labelRating;

    @Override
    protected void loadCard() {
        loader = new FXMLLoader(Constants.FEEDBACK_CARD_RESOURCE);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void initializeValues(Feedback feedback) {
        labelFeedbackerName.textProperty().bind(feedback.feedbackerNameProperty());
        labelRating.textProperty().bind(Bindings.concat("Rating: ", feedback.getRating()));
        textAreaComment.textProperty().bind(feedback.commentProperty());
    }
}
