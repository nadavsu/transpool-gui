package api.components.form;

import api.components.TransPoolController;
import com.jfoenix.validation.RequiredFieldValidator;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;


/**
 * A controller for a component which acts as a form - data is inputted, and then submitted.
 * i.e. Trip request form, Trip offer form.
 * Contains - a required field validator for validating fields.
 *            The main controller
 *            A boolean property which is binded to the engine to check if a file is loaded.
 */
public abstract class FormController implements Form {
    protected RequiredFieldValidator requiredFieldValidator;
    protected TransPoolController transpoolController;
    protected BooleanProperty fileLoaded;


    public FormController() {
        requiredFieldValidator = new RequiredFieldValidator("Required field");
        fileLoaded = new SimpleBooleanProperty(false);
    }

    public void setTransPoolController(TransPoolController transpoolController) {
        this.transpoolController = transpoolController;
    }
    public void initialize() {
        setValidations();
    }
}
