package mcheli;

import java.util.*;

public class MCH_Queue<T>
{
    private int current;
    private List<T> list;
    
    public MCH_Queue(int filterLength, final T initVal) {
        if (filterLength <= 0) {
            filterLength = 1;
        }
        this.list = new ArrayList<T>();
        for (int i = 0; i < filterLength; ++i) {
            this.list.add(initVal);
        }
        this.current = 0;
    }
    
    public void clear(final T clearVal) {
        for (int i = 0; i < this.size(); ++i) {
            this.list.set(i, clearVal);
        }
    }
    
    public void put(final T t) {
        this.list.set(this.current, t);
        ++this.current;
        this.current %= this.size();
    }
    
    private int getIndex(int offset) {
        offset %= this.size();
        final int index = this.current + offset;
        if (index < 0) {
            return index + this.size();
        }
        return index % this.size();
    }
    
    public T oldest() {
        return this.list.get(this.getIndex(1));
    }
    
    public T get(final int i) {
        return this.list.get(i);
    }
    
    public int size() {
        return this.list.size();
    }
}
