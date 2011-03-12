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
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DateBox;

import de.tixus.mb.opac.client.presenter.MediaItemController;
import de.tixus.mb.opac.shared.FieldVerifier;
import de.tixus.mb.opac.shared.entities.Author;
import de.tixus.mb.opac.shared.entities.MediaKind;

/**
 * A form used for editing media items.
 */
public class MediaItemSearchForm extends Composite {

  private static Binder uiBinder = GWT.create(Binder.class);

  interface Binder extends UiBinder<Widget, MediaItemSearchForm> {
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
  ListBox mediaKindBox;
  @UiField
  ListBox genreBox;
  @UiField
  Button searchButton;
  @UiField
  Button clearButton;
  @UiField
  Label lentLabel;
  @UiField
  Label errorLabel;

  private final String[] genres = new String[] { "Belletristik", "Krimi", "Technik", "Thriller", "Frauen", "Gesellschaft und Politik" };

  public MediaItemSearchForm(final MediaItemController mediaItemController) {
    initWidget(uiBinder.createAndBindUi(this));

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

    // Handle events.
    searchButton.addClickHandler(new ClickHandler() {

      public void onClick(final ClickEvent event) {
        final String mediaNumber = mediaNumberBox.getText();
        final String title = titleBox.getText();
        final String text = authorBox.getText();
        if (!FieldVerifier.isWhiteSpaceSeparatedName(text)) {
          errorLabel.setText("Bitte Vor- und Nachnamen eingeben. (z.B. Max Frisch)");
          return;
        }
        final String[] split = text.split(" ");
        final Author author = new Author(split[0], split[1]);

        final Date publicationYear = yearBox.getValue();
        final MediaKind selectedMediaKind = getMediaKind();
        // FIXME list to set
        final int categoryIndex = genreBox.getSelectedIndex();
        final Set<String> genreSet = new HashSet<String>(Arrays.asList(genres[categoryIndex]));
        mediaItemController.search(mediaNumber, title, author, publicationYear, selectedMediaKind, genreSet);
      }

    });

    clearButton.addClickHandler(new ClickHandler() {

      public void onClick(final ClickEvent event) {
        mediaNumberBox.setText("");
        titleBox.setText("");
        authorBox.setText("");
        yearBox.setValue(null);
        mediaKindBox.setSelectedIndex(-1);
        genreBox.setSelectedIndex(-1);
      }
    });
  }

  private MediaKind getMediaKind() {
    final String mediaKindValue = mediaKindBox.getValue(mediaKindBox.getSelectedIndex());
    final MediaKind[] values = MediaKind.values();
    for (final MediaKind mediaKind : values) {
      if (mediaKindValue.equals(mediaKind.getDescription())) {
        return mediaKind;
      }
    }

    return null;
  }
}
