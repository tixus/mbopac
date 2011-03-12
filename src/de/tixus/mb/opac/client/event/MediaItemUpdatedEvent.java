package de.tixus.mb.opac.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class MediaItemUpdatedEvent extends GwtEvent<MediaItemUpdatedEventHandler> {

  public static Type<MediaItemUpdatedEventHandler> TYPE = new Type<MediaItemUpdatedEventHandler>();
  private final String id;

  public MediaItemUpdatedEvent(final String id) {
    this.id = id;
  }

  public String getId() {
    return id;
  }

  @Override
  public Type<MediaItemUpdatedEventHandler> getAssociatedType() {
    return TYPE;
  }

  @Override
  protected void dispatch(final MediaItemUpdatedEventHandler handler) {
    handler.onMediaItemUpdated(this);
  }
}
