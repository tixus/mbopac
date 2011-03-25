package de.tixus.mb.opac.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;

import de.tixus.mb.opac.client.PersistenceServiceAsync;

public class AdminForm extends Composite {

  interface Binder extends UiBinder<Widget, AdminForm> {
  }

  private final PersistenceServiceAsync persistenceService;

  private static class MyPopup extends PopupPanel {

    public MyPopup(final PasswordTextBox passwordTextBox) {
      // PopupPanel's constructor takes 'auto-hide' as its boolean parameter.
      // If this is set, the panel closes itself automatically when the user
      // clicks outside of it.
      super(true);

      // PopupPanel is a SimplePanel, so you have to set it's widget property to
      // whatever you want its contents to be.
      setWidget(passwordTextBox);
    }
  }

  @UiField
  Button clearButton;

  private static Binder uiBinder = GWT.create(Binder.class);

  public AdminForm(final PersistenceServiceAsync persistenceService) {
    this.persistenceService = persistenceService;
    initWidget(uiBinder.createAndBindUi(this));

    final PasswordTextBox passwordTextBox = new PasswordTextBox();
    final MyPopup myPopup = new MyPopup(passwordTextBox);
    passwordTextBox.addKeyDownHandler(new KeyDownHandler() {

      @Override
      public void onKeyDown(final KeyDownEvent event) {
        final int keyCode = event.getNativeEvent().getKeyCode();
        final String value = passwordTextBox.getValue();
        if (keyCode == KeyCodes.KEY_ENTER) {
          myPopup.hide();
          if ("tsptsp".equalsIgnoreCase(value)) {
            doClear();
          } else {
            Window.alert("Password falsch!");
          }
        }
      }
    });

    clearButton.addClickHandler(new ClickHandler() {

      @Override
      public void onClick(final ClickEvent event) {
        myPopup.show();
      }
    });

  }

  private void doClear() {
    persistenceService.setUp(new AsyncCallback<Void>() {

      @Override
      public void onFailure(final Throwable caught) {
        Window.alert("Fehler:" + caught.getLocalizedMessage());
        caught.printStackTrace();
      }

      @Override
      public void onSuccess(final Void result) {
        Window.alert("Erfolgreich!");

      }
    });

  }

}
