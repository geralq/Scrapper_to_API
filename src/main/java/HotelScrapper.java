import java.io.IOException;

public interface HotelScrapper {
    String name(String hotel) throws IOException;

    String services(String hotel) throws IOException;

    String comments(String hotel) throws IOException;

    String ratings(String hotel) throws IOException;
}