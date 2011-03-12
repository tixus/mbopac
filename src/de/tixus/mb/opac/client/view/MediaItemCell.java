package de.tixus.mb.opac.client.view;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.client.ui.AbstractImagePrototype;

import de.tixus.mb.opac.client.MbopacMainView.Images;
import de.tixus.mb.opac.shared.entities.MediaItem;

public class MediaItemCell extends AbstractCell<MediaItem> {

  /**
   * The html of the image used for contacts.
   */
  private final String reserveredImageHtml;
  private final String awayImageHtml;
  private final String bookImageHtml;
  private final String cdImageHtml;
  private final String dvdImageHtml;
  private final String largePrintImageHtml;
  private final String availableImageHtml;

  public MediaItemCell(final Images images) {

    this.reserveredImageHtml = AbstractImagePrototype.create(images.reserved()).getHTML();
    this.awayImageHtml = AbstractImagePrototype.create(images.away()).getHTML();
    this.availableImageHtml = AbstractImagePrototype.create(images.available()).getHTML();
    this.bookImageHtml = AbstractImagePrototype.create(images.book()).getHTML();
    this.largePrintImageHtml = AbstractImagePrototype.create(images.largeprint()).getHTML();
    this.cdImageHtml = AbstractImagePrototype.create(images.cd()).getHTML();
    this.dvdImageHtml = AbstractImagePrototype.create(images.dvd()).getHTML();
  }

  @Override
  public void render(final Context context, final MediaItem value, final SafeHtmlBuilder sb) {
    // Value can be null, so do a null check..
    if (value == null) {
      return;
    }

    sb.appendHtmlConstant("<table>");

    sb.appendHtmlConstant("<tr><td rowspan='3'>");
    sb.appendHtmlConstant(value.isLent() ? awayImageHtml : availableImageHtml);
    sb.appendHtmlConstant("</td>");
    sb.appendHtmlConstant("<tr><td rowspan='3'>");
    sb.appendHtmlConstant(mediaKindImage(value));
    sb.appendHtmlConstant("</td>");
    sb.appendHtmlConstant("<td style='font-size:95%;'>");
    sb.appendEscaped(value.getMediaNumber());
    sb.appendHtmlConstant("</td></tr><tr><td>");
    sb.appendEscaped(value.getTitle());
    sb.appendHtmlConstant("</td></tr></table>");
  }

  private String mediaKindImage(final MediaItem value) {
    switch (value.getKind()) {
      case Book:
        return bookImageHtml;
      case BigFont:
        return largePrintImageHtml;
      case CompactDisc:
        return cdImageHtml;
      default:
        return "";
    }
  }

}
