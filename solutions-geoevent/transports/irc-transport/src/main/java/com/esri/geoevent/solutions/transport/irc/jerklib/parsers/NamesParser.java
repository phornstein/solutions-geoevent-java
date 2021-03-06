package com.esri.geoevent.solutions.transport.irc.jerklib.parsers;

import com.esri.geoevent.solutions.transport.irc.jerklib.Channel;
import com.esri.geoevent.solutions.transport.irc.jerklib.EventToken;
import com.esri.geoevent.solutions.transport.irc.jerklib.Session;
import com.esri.geoevent.solutions.transport.irc.jerklib.events.IRCEvent;
import com.esri.geoevent.solutions.transport.irc.jerklib.events.impl.NickListEventImpl;

/**
 * @author mohadib
 *
 */
public class NamesParser implements CommandParser
{
	public IRCEvent createEvent(EventToken token, IRCEvent event)
	{

		if (token.command().matches("366"))
		{
			Session session = event.getSession();
			return new NickListEventImpl
			(
				token.data(), 
				session, 
				session.getChannel(token.arg(1)),
				session.getChannel(token.arg(1)).getNicks());
		}

		Channel chan = event.getSession().getChannel(token.arg(2));
		String[] names = token.arg(3).split("\\s+");

		for (String name : names)
		{
			if (name != null && name.length() > 0)
			{
				chan.addNick(name);
			}
		}
		return event;
	}
}
