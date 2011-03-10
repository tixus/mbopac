package de.tixus.mb.opac.client.presenter;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.view.client.HasData;
import com.google.gwt.view.client.ListDataProvider;

import de.tixus.mb.opac.client.PersistenceServiceAsync;
import de.tixus.mb.opac.client.entities.Lending;
import de.tixus.mb.opac.client.entities.MediaItem;
import de.tixus.mb.opac.client.entities.Person;

public class LendingController {

  /**
   * The provider that holds the list of lendings for a persons.
   */
  private final ListDataProvider<MediaItem> dataProvider = new ListDataProvider<MediaItem>();
  private final PersistenceServiceAsync persistenceService;

  public LendingController(final PersistenceServiceAsync persistenceService) {
    this.persistenceService = persistenceService;
  }

  /**
   * Add a display to the database. The current range of interest of the display will be populated with data.
   * 
   * @param display a {@Link HasData}.
   */
  public void addDataDisplay(final HasData<MediaItem> display) {
    dataProvider.addDataDisplay(display);
  }

  public void lend(final MediaItem mediaItem, final Person customer) {
    //    persistenceService.lend(mediaItem, customer, new AsyncCallback<Lending>() {
    //
    //      @Override
    //      public void onFailure(final Throwable caught) {
    //        Window.alert("Server-Fehler: " + caught.getMessage());
    //      }
    //
    //      @Override
    //      public void onSuccess(final Lending lending) {
    //        final List<Lending> list = dataProvider.getList();
    //        list.remove(lending);
    //        list.add(lending);
    //        Window.alert("Speichern ok!");
    //      }
    //    });

    dataProvider.refresh();
  }

  public void listAllLendings(final Person person) {
    final List<MediaItem> lendings = dataProvider.getList();
    lendings.clear();
    persistenceService.listAllLendings(person, new AsyncCallback<List<MediaItem>>() {

      @Override
      public void onSuccess(final List<MediaItem> result) {
        lendings.addAll(result);
      }

      @Override
      public void onFailure(final Throwable caught) {
        throw new RuntimeException(caught);
      }
    });
  }

  public List<Lending> endLending(final MediaItem mediaItem) {
    final List<Lending> lendingResults = new ArrayList<Lending>();

    persistenceService.endLending(mediaItem, new AsyncCallback<Lending>() {

      @Override
      public void onSuccess(final Lending result) {
        lendingResults.add(result);
      }

      @Override
      public void onFailure(final Throwable caught) {
        throw new RuntimeException(caught);
      }
    });

    // refresh lendings list
    final List<MediaItem> lendings = dataProvider.getList();
    lendings.remove(mediaItem);

    return lendingResults;
  }
}
