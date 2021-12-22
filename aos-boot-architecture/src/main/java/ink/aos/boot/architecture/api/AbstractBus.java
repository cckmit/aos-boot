package ink.aos.boot.architecture.api;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractBus<E, H> {

    private List<H> handlers = new ArrayList<>();

    public void dispatch(E e) {
        e.getClass();
    }

    public H register(H handler) {
        handlers.add(handler);
        return null;
    }

}