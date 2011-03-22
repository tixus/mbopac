/**
 * 
 */
package de.tixus.mb.opac.client.view;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.client.ui.AbstractImagePrototype;

import de.tixus.mb.opac.client.MbopacMainView;
import de.tixus.mb.opac.client.MbopacMainView.Images;
import de.tixus.mb.opac.shared.entities.Gender;
import de.tixus.mb.opac.shared.entities.Person;

/**
 * @author TSP
 * 
 */
public class PersonCell extends AbstractCell<Person> {

  private final String femaleImageHtml;
  private final String maleImageHtml;

  public PersonCell(final Images images) {

    this.femaleImageHtml = AbstractImagePrototype.create(images.female()).getHTML();
    this.maleImageHtml = AbstractImagePrototype.create(images.male()).getHTML();
  }

  @Override
  public void render(final com.google.gwt.cell.client.Cell.Context context, final Person value, final SafeHtmlBuilder sb) {
    // Value can be null, so do a null check..
    if (value == null) {
      return;
    }

    sb.appendHtmlConstant("<table>");

    // Add the contact image.
    sb.appendHtmlConstant("<tr><td rowspan='3'>");
    sb.appendHtmlConstant(Gender.Female == value.getGender() ? femaleImageHtml : maleImageHtml);
    sb.appendHtmlConstant("</td>");

    // Add the name and address.
    sb.appendHtmlConstant("<td style='font-size:95%;'>");
    sb.appendEscaped(value.getFirstName());
    sb.appendHtmlConstant("</td></tr><tr><td>");
    sb.appendEscaped(value.getLastName());
    sb.appendHtmlConstant("</td></tr></table>");
  }

}
