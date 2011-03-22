package de.tixus.mb.opac.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import de.tixus.mb.opac.client.PersistenceServiceAsync;

public class AdminForm extends Composite {

  interface Binder extends UiBinder<Widget, AdminForm> {
  }

  private final PersistenceServiceAsync persistenceService;

  @UiField
  Button clearButton;

  private static Binder uiBinder = GWT.create(Binder.class);

  public AdminForm(final PersistenceServiceAsync persistenceService) {
    this.persistenceService = persistenceService;
    initWidget(uiBinder.createAndBindUi(this));

    clearButton.addClickHandler(new ClickHandler() {

      @Override
      public void onClick(final ClickEvent event) {
        doClear();
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
