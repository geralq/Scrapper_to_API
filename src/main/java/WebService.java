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
            return scrapper.name(hotel);
        });
        get("/hotels/:name/services", (req, res) -> {
            String hotel = req.params("name");
            res.type("application/json");
            return scrapper.services(hotel);
        });
        get("/hotels/:name/comments", (req, res) -> {
            String hotel = req.params("name");
            res.type("application/json");
            return scrapper.comments(hotel);
        });
        get("/hotels/:name/ratings", (req, res) -> {
            String hotel = req.params("name");
            res.type("application/json");
            return scrapper.ratings(hotel);
        });
    }
}