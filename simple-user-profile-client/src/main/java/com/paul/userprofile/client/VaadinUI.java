package com.paul.userprofile.client;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.server.Page;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@SpringUI
public class VaadinUI extends UI {

    private static final long serialVersionUID = 8468664943681723684L;

    private final UserProfileApi repo;

    private final UserProfileEditor editor;

    private final Grid<UserProfile> grid;

    private final Button addButton;

    @Autowired
    public VaadinUI(final UserProfileApi repo, final UserProfileEditor editor) {
        this.repo = repo;
        this.editor = editor;
        this.grid = new Grid<>(UserProfile.class);
        this.addButton = new Button("New user profile");
    }

    @Override
    protected void init(final VaadinRequest request) {
        Page.getCurrent().setTitle("User profile");

        HorizontalLayout actions = new HorizontalLayout(addButton);
        HorizontalLayout gridLayout = new HorizontalLayout(grid, editor);
        gridLayout.setSizeFull();
        gridLayout.setComponentAlignment(editor, Alignment.TOP_CENTER);

        VerticalLayout mainLayout = new VerticalLayout(actions, gridLayout);
        setContent(mainLayout);

        grid.setWidth(800, Unit.PIXELS);
        grid.setHeight(500, Unit.PIXELS);
        grid.setColumns(UserProfile.FIELDS);
        grid.asSingleSelect().addValueChangeListener(e -> editor.editUserProfile(e.getValue()));
        refreshGridData();

        addButton.addClickListener(e -> editor.editUserProfile(new UserProfile()));

        editor.setChangeHandler(() -> {
            editor.setVisible(false);
            refreshGridData();
        });

    }

    private void refreshGridData() {
        grid.setItems(repo.getUserProfiles());
    }
}
