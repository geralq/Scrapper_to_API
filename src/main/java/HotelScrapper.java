import java.io.IOException;

public interface HotelScrapper {
    String getHotelName(String hotel) throws IOException;

    String getHotelServices(String hotel) throws IOException;

    String getHotelComments(String hotel) throws IOException;

    String getHotelRatings(String hotel) throws IOException;
}