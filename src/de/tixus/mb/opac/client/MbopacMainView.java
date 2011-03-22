package de.tixus.mb.opac.client;

import java.util.Date;

import com.google.gwt.cell.client.Cell;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.cellview.client.HasKeyboardPagingPolicy.KeyboardPagingPolicy;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy.KeyboardSelectionPolicy;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.SplitLayoutPanel;
import com.google.gwt.user.client.ui.TabLayoutPanel;
import com.google.gwt.view.client.ProvidesKey;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;

import de.tixus.mb.opac.client.presenter.LendingController;
import de.tixus.mb.opac.client.presenter.MediaItemController;
import de.tixus.mb.opac.client.presenter.PersonsController;
import de.tixus.mb.opac.client.view.AdminForm;
import de.tixus.mb.opac.client.view.LendingForm;
import de.tixus.mb.opac.client.view.MediaItemCell;
import de.tixus.mb.opac.client.view.MediaItemDetailForm;
import de.tixus.mb.opac.client.view.MediaItemSearchForm;
import de.tixus.mb.opac.client.view.PersonCell;
import de.tixus.mb.opac.client.view.ShowMorePagerPanel;
import de.tixus.mb.opac.shared.entities.MediaItem;
import de.tixus.mb.opac.shared.entities.Person;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class MbopacMainView implements EntryPoint {

  /**
   * The message displayed to the user when the server cannot be reached or returns an error.
   */
  private static final String SERVER_ERROR = "An error occurred while " + "attempting to contact the server. Please check your network "
                                             + "connection and try again.";

  /**
   * Create a remote service proxy to talk to the server-side persistence service.
   */
  private final PersistenceServiceAsync persistenceService = GWT.create(PersistenceService.class);

  /**
   * This is the entry point method.
   */
  public void onModuleLoad() {
    // Master detail view on media items
    final HandlerManager eventBus = new HandlerManager(null);

    final Images images = GWT.create(Images.class);
    final MediaItemController mediaItemController = new MediaItemController(eventBus, persistenceService);
    final MediaItemDetailForm mediaItemDetailForm = new MediaItemDetailForm(mediaItemController);
    final MediaItemCell mediaItemCell = new MediaItemCell(images);
    // Set a key provider that provides a unique key for each contact. If key is
    // used to identify contacts when fields (such as the name and address)
    // change.
    final CellList<MediaItem> catalogCellList = new CellList<MediaItem>(mediaItemCell, MEDIA_ITEM_KEY_PROVIDER);
    catalogCellList.setPageSize(10);
    catalogCellList.setKeyboardPagingPolicy(KeyboardPagingPolicy.INCREASE_RANGE);
    catalogCellList.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.BOUND_TO_SELECTION);

    // Add a selection model so we can select cells.
    final SingleSelectionModel<MediaItem> selectionModel = new SingleSelectionModel<MediaItem>(MEDIA_ITEM_KEY_PROVIDER);
    catalogCellList.setSelectionModel(selectionModel);
    selectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {

      public void onSelectionChange(final SelectionChangeEvent event) {
        mediaItemDetailForm.setItem(selectionModel.getSelectedObject());
      }
    });

    mediaItemController.addDataDisplay(catalogCellList);
    final ShowMorePagerPanel catalogPagerPanel = new ShowMorePagerPanel();
    // Set the cellList as the display of the pagers. This example has two
    // pagers. pagerPanel is a scrollable pager that extends the range when the
    // user scrolls to the bottom. rangeLabelPager is a pager that displays the
    // current range, but does not have any controls to change the range.
    catalogPagerPanel.setDisplay(catalogCellList);

    final MediaItemSearchForm mediaItemSearchForm = new MediaItemSearchForm(mediaItemController);

    // Full catalog entries and detail for media item. 
    final SplitLayoutPanel catalogPanel = new SplitLayoutPanel();
    catalogPanel.addNorth(mediaItemSearchForm, 200);
    catalogPanel.addWest(catalogPagerPanel, 800);
    catalogPanel.add(mediaItemDetailForm);

    // A person's lending items overview and new lending actions.
    final SplitLayoutPanel accountPanel = new SplitLayoutPanel();
    accountPanel.addNorth(new Label("Ausleiher Tino Sperlich"), 200);

    final SplitLayoutPanel accountDetailPanel = new SplitLayoutPanel();
    final ShowMorePagerPanel accoutLentItemsPanel = new ShowMorePagerPanel();

    final LendingController lendingController = new LendingController(eventBus, persistenceService);

    final CellList<MediaItem> lentItemsList = new CellList<MediaItem>(mediaItemCell, MEDIA_ITEM_KEY_PROVIDER);
    accoutLentItemsPanel.setDisplay(lentItemsList);
    lendingController.addDataDisplay(lentItemsList);
    accountDetailPanel.addNorth(accoutLentItemsPanel, 200);
    final LendingForm accoutNewLendingForm = new LendingForm(lendingController);
    accountDetailPanel.add(accoutNewLendingForm);

    // Add a selection model so we can select lent items and manipulate them.
    final SingleSelectionModel<MediaItem> lentSelectionModel = new SingleSelectionModel<MediaItem>(MEDIA_ITEM_KEY_PROVIDER);
    lentItemsList.setSelectionModel(lentSelectionModel);
    lentSelectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {

      public void onSelectionChange(final SelectionChangeEvent event) {
        accoutNewLendingForm.setMediaItem(lentSelectionModel.getSelectedObject());
      }
    });
    final Cell<Person> personCell = new PersonCell(images);
    final CellList<Person> personsCellList = new CellList<Person>(personCell, PERSON_KEY_PROVIDER);
    personsCellList.setPageSize(10);
    personsCellList.setKeyboardPagingPolicy(KeyboardPagingPolicy.INCREASE_RANGE);
    personsCellList.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.BOUND_TO_SELECTION);

    // Add a selection model so we can select persons and show their lent items.
    final SingleSelectionModel<Person> personSelectionModel = new SingleSelectionModel<Person>(PERSON_KEY_PROVIDER);
    personsCellList.setSelectionModel(personSelectionModel);
    personSelectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {

      public void onSelectionChange(final SelectionChangeEvent event) {
        lendingController.listAllLendings(personSelectionModel.getSelectedObject());
        accoutNewLendingForm.setMediaItem(null);
      }
    });

    final PersonsController personsController = new PersonsController(persistenceService);
    personsController.addDataDisplay(personsCellList);
    final ShowMorePagerPanel accountDetailPersonsPanel = new ShowMorePagerPanel();
    accountDetailPersonsPanel.setDisplay(personsCellList);
    accountPanel.addWest(accountDetailPersonsPanel, 700);
    accountPanel.add(accountDetailPanel);

    final DockLayoutPanel allUsersPanel = new DockLayoutPanel(Unit.EM);

    // Attach the LayoutPanel to the RootLayoutPanel. The latter will listen for
    // resize events on the window to ensure that its children are informed of
    // possible size changes.
    final TabLayoutPanel mainPanel = new TabLayoutPanel(1.5, Unit.EM);
    final SimplePanel titlePanel = new SimplePanel();
    titlePanel.add(new Label("Sie sind angemeldet als: Tino Sperlich seit " + new Date()));

    final DockLayoutPanel adminPanel = new DockLayoutPanel(Unit.EM);
    final AdminForm adminForm = new AdminForm(persistenceService);
    adminPanel.add(adminForm);

    mainPanel.add(titlePanel, "Information");
    mainPanel.add(catalogPanel, "Katalog");
    mainPanel.add(accountPanel, "Benutzerkonto");
    mainPanel.add(allUsersPanel, "Benutzer");
    mainPanel.add(adminPanel, "Admin");

    final RootLayoutPanel rp = RootLayoutPanel.get();
    rp.add(mainPanel);

  }

  public static interface Images extends ClientBundle {

    ImageResource away();

    ImageResource available();

    ImageResource reserved();

    ImageResource male();

    ImageResource female();

    ImageResource cd();

    ImageResource book();

    ImageResource largeprint();

    ImageResource dvd();

    ImageResource mp3();
  }

  /**
   * The key provider that allows us to identify MediaItem even if a field changes. We identify MediaItem by their
   * unique ID.
   */
  public static final ProvidesKey<MediaItem> MEDIA_ITEM_KEY_PROVIDER = new ProvidesKey<MediaItem>() {

    public Object getKey(final MediaItem item) {
      return item.getId();
    }
  };
  /**
   * The key provider that allows us to identify MediaItem even if a field changes. We identify MediaItem by their
   * unique ID.
   */
  public static final ProvidesKey<Person> PERSON_KEY_PROVIDER = new ProvidesKey<Person>() {

    public Object getKey(final Person item) {
      return item.getId();
    }
  };
}
