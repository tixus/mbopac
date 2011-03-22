package de.tixus.mb.opac.client.presenter;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.view.client.HasData;
import com.google.gwt.view.client.ListDataProvider;

import de.tixus.mb.opac.client.PersistenceServiceAsync;
import de.tixus.mb.opac.shared.entities.Person;

public class PersonsController implements Controller<Person> {

  /**
   * The provider that holds the list of persons.
   */
  private final ListDataProvider<Person> dataProvider = new ListDataProvider<Person>();
  private final PersistenceServiceAsync persistenceService;

  public PersonsController(final PersistenceServiceAsync persistenceService) {
    this.persistenceService = persistenceService;
    final List<Person> persons = dataProvider.getList();
    persistenceService.listAllPersons(new AsyncCallback<List<Person>>() {

      @Override
      public void onSuccess(final List<Person> result) {
        persons.addAll(result);
      }

      @Override
      public void onFailure(final Throwable caught) {
        throw new RuntimeException(caught);
      }
    });
  }

  @Override
  public void refresh() {
    dataProvider.refresh();
  }

  /* (non-Javadoc)
   * @see de.tixus.mb.opac.client.presenter.Controller#addDataDisplay(com.google.gwt.view.client.HasData)
   */
  @Override
  public void addDataDisplay(final HasData<Person> display) {
    dataProvider.addDataDisplay(display);
  }
}
