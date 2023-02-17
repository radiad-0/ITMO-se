package lab6.tools;

import java.net.SocketAddress;
import java.util.Objects;

public class RequestBox<T> {
    private T data;
    private SocketAddress address;

    public RequestBox(T data, SocketAddress address) {
        this.data = data;
        this.address = address;
    }

    public T getData() {
        return data;
    }

    public SocketAddress getAddress() {
        return address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RequestBox<?> that = (RequestBox<?>) o;
        return Objects.equals(data, that.data) && Objects.equals(address, that.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(data, address);
    }

    @Override
    public String toString() {
        return "RequestBox{" +
                "data=" + data +
                ", address=" + address +
                '}';
    }
}
