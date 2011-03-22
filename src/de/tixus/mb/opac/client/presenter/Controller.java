package de.tixus.mb.opac.client.presenter;

import com.google.gwt.view.client.HasData;

public interface Controller<T> {

  public void refresh();

  /**
   * Add a display to the database. The current range of interest of the display will be populated with data.
   * 
   * @param display a {@Link HasData}.
   */
  public void addDataDisplay(final HasData<T> display);

}