package de.tixus.mb.opac.client.presenter;

import java.util.List;
import java.util.Set;

import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.view.client.HasData;
import com.google.gwt.view.client.ListDataProvider;
import com.googlecode.objectify.Key;

import de.tixus.mb.opac.client.PersistenceServiceAsync;
import de.tixus.mb.opac.client.event.MediaItemUpdatedEvent;
import de.tixus.mb.opac.client.event.MediaItemUpdatedEventHandler;
import de.tixus.mb.opac.shared.entities.Author;
import de.tixus.mb.opac.shared.entities.MediaItem;
import de.tixus.mb.opac.shared.entities.MediaKind;

public class MediaItemController implements Controller<MediaItem> {

  /**
   * The provider that holds media items from catalog.
   */
  private final ListDataProvider<MediaItem> dataProvider = new ListDataProvider<MediaItem>();
  private final PersistenceServiceAsync persistenceService;
  private final HandlerManager eventBus;

  public MediaItemController(final HandlerManager eventBus, final PersistenceServiceAsync persistenceService) {
    this.eventBus = eventBus;
    this.persistenceService = persistenceService;

    init();
  }

  private void init() {
    eventBus.addHandler(MediaItemUpdatedEvent.TYPE, new MediaItemUpdatedEventHandler() {

      public void onMediaItemUpdated(final MediaItemUpdatedEvent event) {
        doMediaItemUpdated(event.getId());
      }

    });

    final List<MediaItem> mediaItems = dataProvider.getList();
    persistenceService.listAllMediaItems(new AsyncCallback<List<MediaItem>>() {

      @Override
      public void onSuccess(final List<MediaItem> result) {
        mediaItems.addAll(result);
      }

      @Override
      public void onFailure(final Throwable caught) {
        Window.alert("Server-Fehler: " + caught.getMessage());
      }
    });
  }

  private void doMediaItemUpdated(final String id) {
    final List<MediaItem> list = dataProvider.getList();
    final Key<MediaItem> key = new Key<MediaItem>(MediaItem.class, id);
    persistenceService.get(key, new AsyncCallback<MediaItem>() {

      @Override
      public void onSuccess(final MediaItem result) {
        final int indexOf = list.indexOf(result);
        if (indexOf != -1) {
          list.set(indexOf, result);
        }
      }

      @Override
      public void onFailure(final Throwable caught) {
        Window.alert("Server-Fehler: " + caught.getMessage());
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
                                   final Integer publicationYear,
                                   final MediaKind kind,
                                   final Integer count,
                                   final Set<String> genres) {

    persistenceService.createMediaItem(title, mediaNumber, shortDescription, author, publicationYear, kind, count, genres,
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
        final int indexOf = list.indexOf(mediaItem);
        if (indexOf != -1) {
          list.set(indexOf, mediaItem);
        }
        Window.alert("Speichern ok!");
      }
    });

    dataProvider.refresh();
  }

  public static void showWaitCursor() {
    DOM.setStyleAttribute(RootPanel.getBodyElement(), "cursor", "wait");
  }

  public static void showDefaultCursor() {
    DOM.setStyleAttribute(RootPanel.getBodyElement(), "cursor", "default");
  }

  public void search(final String mediaNumber,
                     final String title,
                     final Author author,
                     final Integer publicationYear,
                     final MediaKind selectedMediaKind,
                     final Set<String> genreSet) {

    showWaitCursor();
    persistenceService.search(mediaNumber, title, author, publicationYear, selectedMediaKind, genreSet,
                              new AsyncCallback<List<MediaItem>>() {

                                @Override
                                public void onSuccess(final List<MediaItem> result) {
                                  final List<MediaItem> list = dataProvider.getList();
                                  list.clear();
                                  list.addAll(result);
                                  showDefaultCursor();
                                }

                                @Override
                                public void onFailure(final Throwable caught) {
                                  Window.alert("Server-Fehler: " + caught.getMessage());
                                  showDefaultCursor();
                                }
                              });
  }
}
