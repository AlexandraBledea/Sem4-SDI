package common.utils;

import java.io.Serializable;
import java.util.Objects;

public class Pair<A, B> implements Serializable {
    private A first;
    private B second;

    public Pair(A first, B second) {
        super();
        this.first = first;
        this.second = second;
    }

    public A getFirst(){
        return first;
    }

    public B getSecond(){
        return second;
    }

    public void setFirst(A first){this.first = first;}

    public void setSecond(B second){this.second = second;}

    @Override
    public boolean equals(Object o) {
        return o instanceof Pair && this.first.equals(((Pair<?, ?>) o).first)&&
                this.second.equals(((Pair<?, ?>) o).second);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getFirst(), getSecond());
    }

    public String toString()
    {
        return "(" + first + "," + second + ")";
    }
}