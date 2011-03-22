package de.tixus.mb.opac.client.event;

import com.google.gwt.event.shared.EventHandler;

public interface MediaItemUpdatedEventHandler extends EventHandler {

  void onMediaItemUpdated(MediaItemUpdatedEvent event);
}
