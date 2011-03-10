/**
 * 
 */
package de.tixus.mb.opac.client.view;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

import de.tixus.mb.opac.client.entities.Lending;
import de.tixus.mb.opac.client.entities.MediaItem;
import de.tixus.mb.opac.client.presenter.LendingController;

/**
 * @author TSP
 * 
 */
public class LendingForm extends Composite {

  private static Binder uiBinder = GWT.create(Binder.class);

  interface Binder extends UiBinder<Widget, LendingForm> {
  }

  @UiField
  TextBox mediaNumberBox;
  @UiField
  TextBox titleBox;
  @UiField
  TextArea shortDescriptionBox;
  @UiField
  Button lendButton;
  @UiField
  Button endLendingButton;
  @UiField
  Label errorLabel;

  private MediaItem mediaItem;

  public LendingForm(final LendingController lendingController) {
    initWidget(uiBinder.createAndBindUi(this));
    final boolean enabled = false;
    mediaNumberBox.setEnabled(enabled);
    titleBox.setEnabled(enabled);
    shortDescriptionBox.setEnabled(enabled);

    endLendingButton.addClickHandler(new ClickHandler() {

      public void onClick(final ClickEvent event) {
        final List<Lending> endLending = lendingController.endLending(mediaItem);
        errorLabel.setText("Medium zurückgeben: " + endLending.toString());
        lendButton.setEnabled(false);
        endLendingButton.setEnabled(false);
      }
    });
  }

  public void setMediaItem(final MediaItem mediaItem) {
    this.mediaItem = mediaItem;
    if (this.mediaItem == null) {
      mediaNumberBox.setText("");
      titleBox.setText("");
      shortDescriptionBox.setText("");
      lendButton.setEnabled(false);
      endLendingButton.setEnabled(false);
    } else {
      mediaNumberBox.setText(mediaItem.getMediaNumber());
      titleBox.setText(mediaItem.getTitle());
      shortDescriptionBox.setText(mediaItem.getShortDescription());
      lendButton.setEnabled(!mediaItem.isLent());
      endLendingButton.setEnabled(mediaItem.isLent());
    }
  }
}
