import static spark.Spark.get;

public class WebService {
    private final HotelScrapper scrapper;

    public WebService(HotelScrapper scrapper) {
        this.scrapper = scrapper;
    }

    public void start() {
        get("/hotels/:name", (req, res) -> {
            String hotel = req.params("name");
            res.type("application/json");
            return scrapper.getHotelName(hotel);
        });
        get("/hotels/:name/services", (req, res) -> {
            String hotel = req.params("name");
            res.type("application/json");
            return scrapper.getHotelServices(hotel);
        });
        get("/hotels/:name/comments", (req, res) -> {
            String hotel = req.params("name");
            res.type("application/json");
            return scrapper.getHotelComments(hotel);
        });
        get("/hotels/:name/ratings", (req, res) -> {
            String hotel = req.params("name");
            res.type("application/json");
            return scrapper.getHotelRatings(hotel);
        });
    }
}