package org.dimigo.gui.helloworld;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.List;

public class Controller {
    @FXML private Button btn_click;
    @FXML private Button btn_click1;
    @FXML private ListView<String> html_tags;
    @FXML WebView webView;
    @FXML private TextField search_input;


    /* HTML 웹 크롤링 */
    @FXML
    String htmltags_link[] = new String[300];
    public void handleAction(ActionEvent event) {
        btn_click1.getStyleClass().remove("active");
        btn_click.getStyleClass().add("active");
        html_tags.getItems().clear();
        int cnt=0;
        try {
            Document doc = Jsoup.connect("https://www.w3schools.com/tags/").get();
            System.out.println(doc.html());
            Elements elements = doc.select("#htmltags table tbody tr a");

            List<String> keywords = elements.eachText();

            for (Element temp : elements) {
                htmltags_link[cnt++] = temp.absUrl("href");
            }
            for(int i=0; i<keywords.size(); i++) {
                html_tags.getItems().add(keywords.get(i));
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    /* CSS 웹 크롤링 */
    @FXML
    public void handleAction1(ActionEvent event) {
        btn_click.getStyleClass().remove("active");
        btn_click1.getStyleClass().add("active");
        html_tags.getItems().clear();
        int cnt=0;
        try {
            Document doc = Jsoup.connect("https://www.w3schools.com/cssref/").get();
            System.out.println(doc.html());
            Elements elements = doc.select(".w3-table-all tbody tr a");

            List<String> keywords = elements.eachText();

            for (Element temp : elements) {
                htmltags_link[cnt++] = temp.absUrl("href");
            }
            for(int i=0; i<keywords.size(); i++) {
                html_tags.getItems().add(keywords.get(i));
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    /* 선택한 항목의 링크 웹뷰로 띄우기 */
    @FXML
    public void showWebView(Event e) {
        int index = html_tags.getSelectionModel().getSelectedIndex();
        System.out.println(htmltags_link[index]);

        WebEngine webEngine = webView.getEngine();
        webEngine.load(htmltags_link[index]);
    }

    /* 검색  */
    @FXML
    public void searchSubmit(ActionEvent event) {
        for(int i=0; i<=html_tags.getItems().size(); i++) {
            if(i==html_tags.getItems().size()) {
                WebEngine webEngine = webView.getEngine();
                webEngine.load("https://nodata-hyoyeon.firebaseapp.com/");
                break;
            }
            if(search_input.getText().equals(html_tags.getItems().get(i))) {
                WebEngine webEngine = webView.getEngine();
                webEngine.load(htmltags_link[i]);
                break;
            }
        }
    }
}