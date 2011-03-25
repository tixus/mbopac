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

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.DateTimeFormat.PredefinedFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DateBox;

import de.tixus.mb.opac.client.presenter.MediaItemController;
import de.tixus.mb.opac.shared.FieldVerifier;
import de.tixus.mb.opac.shared.entities.Author;
import de.tixus.mb.opac.shared.entities.MediaItem;
import de.tixus.mb.opac.shared.entities.MediaKind;

/**
 * A form used for editing media items.
 */
public class MediaItemDetailForm extends Composite {

  private static Binder uiBinder = GWT.create(Binder.class);

  interface Binder extends UiBinder<Widget, MediaItemDetailForm> {
  }

  @UiField
  TextBox mediaNumberBox;
  @UiField
  TextBox titleBox;
  @UiField
  TextBox authorBox;
  @UiField
  DateBox yearBox;
  @UiField
  TextArea shortDescriptionBox;
  @UiField
  ListBox mediaKindBox;
  @UiField
  TextBox countBox;
  @UiField
  Label countBoxLabel;
  @UiField
  ListBox genreBox;
  @UiField
  Button createButton;
  @UiField
  Button updateButton;
  @UiField
  Label lentLabel;
  @UiField
  Label errorLabel;

  private MediaItem mediaItem;

  private final String[] genres = MediaItem.genresType;

  public MediaItemDetailForm(final MediaItemController mediaItemController) {
    initWidget(uiBinder.createAndBindUi(this));
    // TODO updating the unique identifier needs more than property setting
    mediaNumberBox.setEnabled(false);
    //TODO implement later
    createButton.setEnabled(false);
    updateButton.setEnabled(true);

    final DateTimeFormat dateFormat = DateTimeFormat.getFormat(PredefinedFormat.YEAR);
    yearBox.setFormat(new DateBox.DefaultFormat(dateFormat));
    // FIXME put into persistent field

    for (final String genre : genres) {
      genreBox.addItem(genre);
    }

    final MediaKind[] values = MediaKind.values();
    for (final MediaKind mediaKind : values) {
      mediaKindBox.addItem(mediaKind.getDescription());
    }
    // Initialize the contact to null.
    setItem(null);

    // Handle events.
    updateButton.addClickHandler(new ClickHandler() {

      public void onClick(final ClickEvent event) {
        if (mediaItem == null) {
          return;
        }
        // Update the contact.
        // mediaItem.mediaNumber = mediaNumberBox.getText();
        final String title = titleBox.getText();
        final String text = authorBox.getText();
        if (!FieldVerifier.isWhiteSpaceSeparatedName(text)) {
          errorLabel.setText("Bitte Vor- und Nachnamen eingeben.");
          return;
        }
        final String[] split = text.split(" ");
        final Author author = new Author(split[0], split[1]);
        final Integer publicationYear = yearBox.getValue() != null ? yearBox.getValue().getYear() : null;
        final String shortDescription = shortDescriptionBox.getText();
        // FIXME list to set
        final MediaKind kind = mediaItem.getKind();
        mediaKindBox.getSelectedIndex();
        // FIXME use countBox
        final String countString = countBox.getText();
        final Integer count = Integer.valueOf(countString);
        // FIXME list to set
        final int categoryIndex = genreBox.getSelectedIndex();
        final Set<String> genreSet = new HashSet<String>(Arrays.asList(genres[categoryIndex]));

        mediaItem.update(title, shortDescription, author, publicationYear, kind, genreSet);
        // Update the views.
        mediaItemController.update(mediaItem);
      }
    });

    createButton.addClickHandler(new ClickHandler() {

      public void onClick(final ClickEvent event) {
        final String mediaNumber = mediaNumberBox.getText();
        final String title = titleBox.getText();
        final String authorText = authorBox.getText();
        if (!FieldVerifier.isWhiteSpaceSeparatedName(authorText)) {
          errorLabel.setText("Bitte Vor- und Nachnamen eingeben.");
          return;
        }
        final String[] split = authorText.split(" ");
        final Author author = new Author(split[0], split[1]);
        final Integer publicationYear = yearBox.getValue() != null ? yearBox.getValue().getYear() : null;
        final String shortDescription = shortDescriptionBox.getText();
        mediaKindBox.getSelectedIndex();
        // FIXME list to set
        final int categoryIndex = genreBox.getSelectedIndex();
        //        final Set<String> genres = new HashSet<String>(Arrays.asList(genres[categoryIndex]));

        final MediaKind kind = null;
        final String countString = countBox.getText();
        final Integer count = Integer.valueOf(countString);
        final Set<String> genreSet = null;
        // Update the views.
        final MediaItem createdMediaItem = mediaItemController.createMediaItem(title, mediaNumber, shortDescription, author,
                                                                               publicationYear, kind, count, genreSet);
        setItem(createdMediaItem);
      }
    });

  }

  public void setItem(final MediaItem mediaItem) {
    this.mediaItem = mediaItem;
    //    updateButton.setEnabled(mediaItem != null);
    if (mediaItem != null) {
      mediaNumberBox.setText(mediaItem.getMediaNumber());
      titleBox.setText(mediaItem.getTitle());
      authorBox.setText(nullSafeToString(mediaItem.getAuthor()));
      yearBox.setValue(new Date(mediaItem.getPublicationYear() - 1900, 1, 1));
      shortDescriptionBox.setText(mediaItem.getShortDescription());
      //      TODO
      mediaKindBox.setSelectedIndex(0);
      countBox.setText(nullSafeToString(mediaItem.getCount()));
      countBoxLabel.setText(mediaItem.getKind().getCountAsString());

      final Set<String> genres2 = mediaItem.getGenres();
      if (genres2 != null) {
        outer: for (final String string : genres2) {
          for (int i = 0; i < genres.length; i++) {
            if (string.equals(genres[i])) {
              genreBox.setSelectedIndex(i);
              break outer;
            }
          }
        }
      }
      lentLabel.setText(mediaItem.isLent() ? "Verliehen" : "Entleihbar");
    }
  }

  private <T> String nullSafeToString(final T t) {
    return t == null ? "" : t.toString();
  }
}
