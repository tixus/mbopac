/*
 * Copyright 2010 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package de.tixus.mb.opac.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.DateTimeFormat.PredefinedFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DateBox;

import de.tixus.mb.opac.client.presenter.PersonsController;
import de.tixus.mb.opac.shared.entities.MediaItem;
import de.tixus.mb.opac.shared.entities.Person;

/**
 * A form used for editing persons.
 */
public class PersonDetailForm extends Composite {

  private static Binder uiBinder = GWT.create(Binder.class);

  interface Binder extends UiBinder<Widget, PersonDetailForm> {
  }

  @UiField
  TextArea addressBox;
  @UiField
  DateBox birthdayBox;
  @UiField
  ListBox categoryBox;
  @UiField
  TextBox firstNameBox;
  @UiField
  TextBox lastNameBox;
  @UiField
  Button updateButton;
  @UiField
  Button createButton;

  private Person person;

  public PersonDetailForm(final PersonsController controller) {
    initWidget(uiBinder.createAndBindUi(this));
    final DateTimeFormat dateFormat = DateTimeFormat.getFormat(PredefinedFormat.DATE_LONG);
    birthdayBox.setFormat(new DateBox.DefaultFormat(dateFormat));

    // Initialize the contact to null.
    setItem(null);

    // Handle events.
    updateButton.addClickHandler(new ClickHandler() {

      public void onClick(final ClickEvent event) {
        //        if (mediaItem == null) {
        //          return;
        //        }
        //
        //        // Update the contact.
        //        mediaItem.mediaNumber = firstNameBox.getText();
        //        mediaItem.title = lastNameBox.getText();
        //        mediaItem.setLastName(lastNameBox.getText());
        //        mediaItem.setAddress(addressBox.getText());
        //        mediaItem.setBirthday(birthdayBox.getValue());
        //        final int categoryIndex = categoryBox.getSelectedIndex();
        //        mediaItem.setCategory(categories[categoryIndex]);

        // Update the views.
        //        controller.update(mediaItem);
      }
    });
    createButton.addClickHandler(new ClickHandler() {

      public void onClick(final ClickEvent event) {
        //        final int categoryIndex = categoryBox.getSelectedIndex();
        //        final Category category = categories[categoryIndex];
        final String mediaNumber = firstNameBox.getText();
        final String title = lastNameBox.getText();
        //        final MediaItem newMediaItem = controller.createMediaItem(title, mediaNumber);

        //        setItem(newMediaItem);
      }
    });
  }

  public void setItem(final MediaItem mediaItem) {
    //    this.mediaItem = mediaItem;
    //    updateButton.setEnabled(mediaItem != null);
    //    if (mediaItem != null) {
    //      firstNameBox.setText(mediaItem.getMediaNumber());
    //      lastNameBox.setText(mediaItem.getTitle());
    //      addressBox.setText(contact.getAddress());
    //      birthdayBox.setValue(contact.getBirthday());
    //      final Category category = contact.getCategory();
    //      final Category[] categories = ContactDatabase.get().queryCategories();
    //      for (int i = 0; i < categories.length; i++) {
    //        if (category == categories[i]) {
    //          categoryBox.setSelectedIndex(i);
    //          break;
    //        }
    //      }
    //    }
  }
}
