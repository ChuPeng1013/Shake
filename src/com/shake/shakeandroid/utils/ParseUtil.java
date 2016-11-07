package com.shake.shakeandroid.utils;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;
import com.shake.shakeandroid.PunishmentInfo;

public class ParseUtil 
{
	public static List<PunishmentInfo> parseXMLWithPull(String xmlData)
	{
		List<PunishmentInfo> list = new ArrayList<PunishmentInfo>();
		try
		{
			XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
			XmlPullParser xmlPullParser = factory.newPullParser();
			xmlPullParser.setInput(new StringReader(xmlData));
			int eventType = xmlPullParser.getEventType();
			@SuppressWarnings("unused")
			String id = "";
			String item = "";
			while(eventType != XmlPullParser.END_DOCUMENT)
			{
				String nodeName = xmlPullParser.getName();
				switch(eventType)
				{
					case XmlPullParser.START_TAG:
						if("id".equals(nodeName))
						{
							id = xmlPullParser.nextText();
						}
						else if("item".equals(nodeName))
						{
							item = xmlPullParser.nextText();
						}
						break;
					case XmlPullParser.END_TAG:
						if("content".equals(nodeName))
						{
							PunishmentInfo info = new PunishmentInfo();
							info.setContent(item);
							list.add(info);
						}
						break;
				}
				eventType = xmlPullParser.next();
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return list;
	}
}
