package camp.store;

abstract public class AbstractStore<T> implements Store<T> {
    private int sequence = 1;

    @Override
    public int getNextSequence() {
        return sequence++;
    }
}
