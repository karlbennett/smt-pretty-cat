package shiver.me.timbers.exceptions;

import shiver.me.timbers.transform.Container;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static shiver.me.timbers.asserts.Asserts.argumentIsNullMessage;
import static shiver.me.timbers.asserts.Asserts.assertIsNotNull;
import static shiver.me.timbers.checks.Checks.isNotNull;
import static shiver.me.timbers.checks.Checks.isNull;
import static shiver.me.timbers.transform.mapped.MappedContainer.isValidIndex;

/**
 * A collection of exception handlers. The population of this container is deferred till the first call to a get method.
 * This is so that the possibly slow reflective setup logic isn't run unless it is actually needed.
 */
public class IterableExceptionHandlers implements Container<Class, ExceptionHandler> {

    private final Iterable<ExceptionHandler> handlers;
    private final ExceptionHandler nullHandler;

    private List<ExceptionHandler> handlerList;
    private Map<Class, ExceptionHandler> handlerMap;

    public IterableExceptionHandlers(ExceptionHandler nullHandler) {

        this(Collections.<ExceptionHandler>emptySet(), nullHandler);
    }

    public IterableExceptionHandlers(Iterable<ExceptionHandler> handlers, ExceptionHandler nullHandler) {

        assertIsNotNull(argumentIsNullMessage("handlers"), handlers);
        assertIsNotNull(argumentIsNullMessage("nullHandler"), nullHandler);

        this.handlers = handlers;
        this.nullHandler = nullHandler;
    }

    @Override
    public ExceptionHandler get(int index) {

        populateCollections();

        return isValidIndex(handlerList, index) ? handlerList.get(index) : nullHandler;
    }

    @Override
    public ExceptionHandler get(Class key) {

        populateCollections();

        final ExceptionHandler handler = handlerMap.get(key);

        return isNotNull(handler) ? handler : nullHandler;
    }

    @Override
    public Iterator<ExceptionHandler> iterator() {

        populateCollections();

        return new LinkedList<ExceptionHandler>(handlerList).iterator();
    }

    @Override
    public Collection<ExceptionHandler> asCollection() {

        populateCollections();

        return new HashSet<ExceptionHandler>(handlerList);
    }

    private void populateCollections() {

        if (isNotPopulated()) {

            handlerList = new ArrayList<ExceptionHandler>();
            handlerMap = new HashMap<Class, ExceptionHandler>();

            for (ExceptionHandler handler : handlers) {

                handlerList.add(handler);
                handlerMap.put(handler.getExceptionType(), handler);
            }
        }
    }

    private boolean isNotPopulated() {

        return isNull(handlerList) || isNull(handlerMap);
    }
}
