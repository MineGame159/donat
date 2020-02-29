package minegame159.donat.events.input;

import me.zero.alpine.event.type.ICancellable;

public abstract class Cancellable implements ICancellable {
    private boolean cancelled;

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    @Override
    public void cancel() {
        cancelled = true;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }
}
