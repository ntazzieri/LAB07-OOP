package it.unibo.inner;

import java.util.Iterator;

import it.unibo.inner.api.IterableWithPolicy;
import it.unibo.inner.api.Predicate;

/**
 *  Implementation of {@link IterableWithPolicy<T>}
 */
public class IterableWithPolicyImpl<T> implements IterableWithPolicy<T> {
    
    private final T[] array;
    private Predicate<T> filter;

    /**
     * It sets an always true predicate
     * @param array to iterate
     */
    public IterableWithPolicyImpl(final T[] array) {
        this(array, new Predicate<T>() {
            public boolean test(final T elem) {
                return true;
            }
        });
    }
    
    /**
     * @param array to iterate
     * @param predicate filter predicate
     */
    public IterableWithPolicyImpl(final T[] array, final Predicate<T> predicate) {
        this.array = array;
        this.filter = predicate;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Iterator<T> iterator() {
        return new IteratorWithPolicy();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setIterationPolicy(final Predicate<T> filter) {
        this.filter = filter;
    }

    /**
     * An iterator with the policy declared in {@link IterableWithPolicy<T>}
     */
    public class IteratorWithPolicy implements Iterator<T> {

        private int currentElement;

        public IteratorWithPolicy() {
            this.currentElement = 0;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean hasNext() {
            int tmpCurrentElem = this.currentElement;
            while(tmpCurrentElem < array.length && !filter.test(array[tmpCurrentElem])) {
                tmpCurrentElem++;
            }
            return tmpCurrentElem < array.length;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public T next() {
            while(hasNext() && !filter.test(array[currentElement])) {
                currentElement++;
            }
            final T retValue = array[currentElement];
            currentElement++;
            return retValue;
        }
    }
}
