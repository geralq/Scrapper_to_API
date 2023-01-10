public class Controller {
    public void control() {
        new WebService(new BookingScrapper()).start();
    }
}
