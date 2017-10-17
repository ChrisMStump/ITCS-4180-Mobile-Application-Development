package com.example.chris.scrollingnewsapplication;

import org.xml.sax.helpers.DefaultHandler;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by Chris on 6/8/2017.
 */

public class NewsUtil {
    static public class NewsSAXParser extends DefaultHandler {

        static ArrayList<News> parseNews(InputStream in) throws XmlPullParserException, IOException {
            XmlPullParser parser = XmlPullParserFactory.newInstance().newPullParser();
            parser.setInput(in, "UTF-8");
            News news = null;
            ArrayList<News> newsList = new ArrayList<News>();
            int event = parser.getEventType();
            boolean gotItem = false;
            while(event != XmlPullParser.END_DOCUMENT){

                switch (event){

                    case XmlPullParser.START_TAG:
                        if(parser.getName().equals("item")){
                            gotItem = true;
                            news = new News();

                        } else if(parser.getName().equals("title")){
                            if(gotItem) {
                                news.setTitle(parser.nextText().trim());
                            }
                        } else if(parser.getName().equals("description")){
                            if(gotItem)
                                news.setDescription(parser.nextText().trim());

                        } else if(parser.getName().equals("pubDate")) {
                            news.setPubDate(parser.nextText().trim());

                        }else if(parser.getName().equals("media:content")){
                            news.setImageURL(parser.getAttributeValue(null, "url").trim());

                        }
                        break;
                    case XmlPullParser.END_TAG:
                        if(parser.getName().equals("item")){
                            newsList.add(news);
                            news = null;
                        }
                    default:
                        break;
                }

                event = parser.next();
            }
            return newsList;
        }
    }
}
