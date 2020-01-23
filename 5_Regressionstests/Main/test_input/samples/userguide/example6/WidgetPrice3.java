package samples.userguide.example6;

/**
 * Interface describing a web service to set and get Widget prices.
 **/
public interface WidgetPrice3 {
    public void setWidgetPrice(String widgetName, String price);
    public String getWidgetPrice(String widgetName);
}