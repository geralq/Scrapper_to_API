import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class BookingScrapper implements HotelScrapper {

    @Override
    public String getHotelName(String hotel) throws IOException {
        String url = "https://www.booking.com/hotel/es/" + hotel + ".es.html";
        Document NameDocument = Jsoup.connect(url).get();
        Map<String, String> nameResponse = new LinkedHashMap<>();

        nameResponse.put("NombreDelHotel", NameDocument.getElementsByClass("d2fee87262 pp-header__title").text());
        nameResponse.put("Dirección", NameDocument.getElementsByClass("\n" +
                "hp_address_subtitle\n" +
                "js-hp_address_subtitle\n" +
                "jq_tooltip\n").text());
        nameResponse.put("Coordenadas", NameDocument.select("a").attr("data-atlas-latlng"));
        return new ObjectMapper().writeValueAsString(nameResponse);
    }

    @Override
    public String getHotelServices(String hotel) throws IOException {
        String url = "https://www.booking.com/hotel/es/" + hotel + ".es.html";
        Document serviceDocument = Jsoup.connect(url).get();
        Map<String, List<String>> servicesResponse = new LinkedHashMap<>();

        Elements servicesElements = serviceDocument.getElementsByClass("hotel-facilities-group");
        serviceElementsMap(servicesResponse, servicesElements);
        return new ObjectMapper().writeValueAsString(servicesResponse);
    }

    private static void serviceElementsMap(Map<String, List<String>> servicesResponse, Elements servicesElements) {
        for (Element facility : servicesElements) {
            Elements facilityElements = facility.getElementsByClass("bui-list__item bui-spacer--medium hotel-facilities-group__list-item");
            List<String> caracteristics = new ArrayList<>();
            for (Element facilityElement : facilityElements) {
                caracteristics.add(facilityElement.getElementsByClass("bui-list__description").text());
            }
            servicesResponse.put(facility.getElementsByClass("bui-title__text hotel-facilities-group__title-text").text(),
                    caracteristics);
        }
    }

    @Override
    public String getHotelComments(String hotel) throws IOException {
        String url = "https://www.booking.com/reviews/es/hotel/" + hotel + ".es.html";
        Document commentsDocument = Jsoup.connect(url).get();
        List<Map<String, String>> commentsResponse = new ArrayList<>();

        Elements commentElements = commentsDocument.getElementsByClass("review_item clearfix ");
        for (Element commentElement : commentElements) {
            commentsResponse.add(readComment(commentElement));
        }
        return new Gson().toJson(commentsResponse);
    }

    private static Map<String, String> readComment(Element CommentElement) {
        Map<String, String> commentsMap = new LinkedHashMap<>();
        commentsMap.put("Fecha", CommentElement.getElementsByClass("review_item_date").text());
        commentsMap.put("Reseña", CommentElement.getElementsByClass("review_item_header_content_container").text());
        commentsMap.put("Puntuación", CommentElement.getElementsByClass("review-score-badge").text());
        commentsMap.put("ComentarioNegativo", CommentElement.getElementsByClass("review_neg ").text());
        commentsMap.put("ComentarioPositivo", CommentElement.getElementsByClass("review_pos ").text());
        return commentsMap;
    }

    @Override
    public String getHotelRatings(String hotel) throws IOException {
        String url = "https://www.booking.com/hotel/es/" + hotel + ".es.html";
        Document ratingDocument = Jsoup.connect(url).get();
        Map<String, String> ratingsResponse = new LinkedHashMap<>();

        Elements ratingsElements = ratingDocument.getElementsByClass("ccff2b4c43 cfc0860887");
        for (Element ratingsElement : ratingsElements) {
            ratingsResponse.put(ratingsElement.getElementsByClass("d6d4671780").text(),
                    ratingsElement.getElementsByClass("ee746850b6 b8eef6afe1").text());
        }
        return new ObjectMapper().writeValueAsString(ratingsResponse);
    }
}