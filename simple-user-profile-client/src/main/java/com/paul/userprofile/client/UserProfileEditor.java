package com.paul.userprofile.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.data.Binder;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.DateField;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

@UIScope
@SpringComponent
public class UserProfileEditor extends VerticalLayout {
    private final static Logger LOG = LoggerFactory.getLogger(UserProfileEditor.class);

    private static final long serialVersionUID = 3018339365162595710L;

    private UserProfile userProfile;
    private final UserProfileApi userProfileApi;

    TextField firstName = new TextField("First name");
    TextField lastName = new TextField("Last name");
    DateField birthDate = new DateField("Birth date");


    Label error = new Label(String.format("<font color=\"red\">Could not process an action.</font>"), ContentMode.HTML);

    Button save = new Button("Save");
    Button cancel = new Button("Cancel");
    Button delete = new Button("Delete");
    CssLayout actions = new CssLayout(save, cancel, delete);

    Binder<UserProfile> binder = new Binder<>(UserProfile.class);

    @Autowired
    public UserProfileEditor(final UserProfileApi userProfileApi) {
        this.userProfileApi = userProfileApi;
        
        setWidth(50, Unit.PERCENTAGE);
        setMargin(false);
        setSpacing(true);

        addComponents(error, firstName, lastName, birthDate, actions);

        binder.bindInstanceFields(this);

        error.setVisible(false);
        error.setStyleName(ValoTheme.NOTIFICATION_ERROR);

        actions.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
        
        save.setStyleName(ValoTheme.BUTTON_PRIMARY);
        save.addClickListener(e -> {
            try {
                userProfileApi.saveUserProfile(userProfile);
            } catch (Exception ex) {
                error.setVisible(true);
                LOG.error("Could not save profile: " + ex.getMessage());
            }

        });
        
        delete.addClickListener(e -> {
            try {
                userProfileApi.deleteUserProfile(userProfile.getId());
            } catch (Exception ex) {
                error.setVisible(true);
                LOG.error("Could not deete profile: " + ex.getMessage());
            }
        });

        cancel.addClickListener(e -> {
            editUserProfile(userProfile);
            error.setVisible(false);
        });
        setVisible(false);
    }

    public interface ChangeHandler {
        void onChange();
    }

    public final void editUserProfile(final UserProfile userProfileToEdit) {
        error.setVisible(false);
        save.setComponentError(null);
        delete.setComponentError(null);
        
        if (userProfileToEdit == null) {
            setVisible(false);
            return;
        }

        boolean persisted = userProfileToEdit.getId() != null;
        if (persisted) {
             userProfile = userProfileApi.getUserProfile(userProfileToEdit.getId());
        } else {
            userProfile = userProfileToEdit;
        }
        cancel.setVisible(persisted);

        binder.setBean(userProfile);

        firstName.selectAll();
        
        setVisible(true);
    }

    public void setChangeHandler(final ChangeHandler h) {
        save.addClickListener(e -> h.onChange());
        delete.addClickListener(e -> h.onChange());
    }

}
