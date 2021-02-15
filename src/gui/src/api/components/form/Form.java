package api.components.form;

import data.transpool.TransPoolData;

public interface Form {
    void clear();                   //Clears the form
    void setValidations();          //Sets the required field validations of the form
    boolean isValid();              //Checks if the form is valid.
    void submit();                  //Submits the form somewhere.
}
