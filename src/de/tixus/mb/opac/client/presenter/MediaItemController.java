package de.tixus.mb.opac.client.presenter;

import java.util.Date;
import java.util.List;
import java.util.Set;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.view.client.HasData;
import com.google.gwt.view.client.ListDataProvider;

import de.tixus.mb.opac.client.PersistenceServiceAsync;
import de.tixus.mb.opac.client.entities.Author;
import de.tixus.mb.opac.client.entities.MediaItem;
import de.tixus.mb.opac.client.entities.MediaKind;

public class MediaItemController implements Controller<MediaItem> {

  /**
   * The provider that holds media items from catalog.
   */
  private final ListDataProvider<MediaItem> dataProvider = new ListDataProvider<MediaItem>();
  private final PersistenceServiceAsync persistenceService;

  public MediaItemController(final PersistenceServiceAsync persistenceService) {
    this.persistenceService = persistenceService;
    final List<MediaItem> mediaItems = dataProvider.getList();
    persistenceService.listAllMediaItems(new AsyncCallback<List<MediaItem>>() {

      @Override
      public void onSuccess(final List<MediaItem> result) {
        mediaItems.addAll(result);
      }

      @Override
      public void onFailure(final Throwable caught) {
        throw new RuntimeException(caught);
      }
    });
  }

  public void refresh() {
    dataProvider.refresh();
  }

  /**
   * Add a display to the database. The current range of interest of the display will be populated with data.
   * 
   * @param display a {@Link HasData}.
   */
  public void addDataDisplay(final HasData<MediaItem> display) {
    dataProvider.addDataDisplay(display);
  }

  public MediaItem createMediaItem(final String title,
                                   final String mediaNumber,
                                   final String shortDescription,
                                   final Author author,
                                   final Date publicationYear,
                                   final MediaKind kind,
                                   final Set<String> genres) {

    persistenceService.createMediaItem(title, mediaNumber, shortDescription, author, publicationYear, kind, genres,
                                       new AsyncCallback<MediaItem>() {

                                         @Override
                                         public void onSuccess(final MediaItem mediaItem) {
                                           final List<MediaItem> list = dataProvider.getList();
                                           list.remove(mediaItem);
                                           list.add(mediaItem);
                                           Window.alert("Eintrag angelegt.");
                                         }

                                         @Override
                                         public void onFailure(final Throwable caught) {
                                           Window.alert("Server-Fehler: " + caught.getMessage());
                                         }
                                       });
    dataProvider.refresh();

    return null;
  }

  public void update(final MediaItem mediaItem) {
    persistenceService.update(mediaItem, new AsyncCallback<Void>() {

      @Override
      public void onFailure(final Throwable caught) {
        Window.alert("Server-Fehler: " + caught.getMessage());
      }

      @Override
      public void onSuccess(final Void result) {
        final List<MediaItem> list = dataProvider.getList();
        list.remove(mediaItem);
        list.add(mediaItem);
        Window.alert("Speichern ok!");
      }
    });

    dataProvider.refresh();
  }
}
