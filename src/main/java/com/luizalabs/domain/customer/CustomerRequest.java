package domain.customer;

import org.apache.commons.validator.routines.EmailValidator;

import static util.StringUtils.exceedsMaxLength;
import static util.StringUtils.isNullOrEmpty;

class CustomerRequest {

    private static final int LENGTH_THRESHOLD = 255;

    private String name;
    private String email;

    String getName() {
        return name;
    }

    String getEmail() {
        return email;
    }

    boolean isValid() {
        return isNameValid() && isEmailValid();
    }

    private boolean isNameValid() {
        return !isNullOrEmpty(getName()) && !exceedsMaxLength(getName(), LENGTH_THRESHOLD);
    }

    private boolean isEmailValid() {
        return !isNullOrEmpty(getEmail()) && !exceedsMaxLength(getEmail(), LENGTH_THRESHOLD) && EmailValidator.getInstance().isValid(getEmail());
    }
}