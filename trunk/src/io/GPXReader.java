package io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Stack;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import data.coordinate.LatLon;
import data.gpx.GPXData;
import data.gpx.GPXTrack;
import data.gpx.WayPoint;
import exceptions.ReadException;

public class GPXReader
{
	private XMLStreamReader parser;
	private enum state { INIT, metadata, WAYPOINT, ROUTE, TRACK, TRACKPOINT, ext, author, link, TRACKSEGMENT }
	private Stack<state> states = new Stack<state>();
	private GPXData gpxData;
	private WayPoint currentWayPoint;
	private GPXTrack currentTrack;
	private state currentState = state.INIT;
	
	public GPXReader(File inputFile) throws ReadException
	{
		XMLInputFactory factory = XMLInputFactory.newInstance();

		try
		{
			parser = factory.createXMLStreamReader(new FileInputStream(inputFile));
		}
		catch (FileNotFoundException e)
		{
			throw new ReadException(e.getMessage());
		}
		catch (XMLStreamException e)
		{
			throw new ReadException(e.getMessage());
		}
	}

	public GPXData getGPXDataObject() throws ReadException
	{
		try
		{
			boolean reading = true;
			
			while(reading)
			{
				// if we read the last element this should be the last run
				if(!parser.hasNext())
				{
					reading = false;
				}
				
				switch (parser.getEventType())
				{
					case XMLStreamConstants.START_DOCUMENT:
						gpxData = new GPXData();
						break;
	
					case XMLStreamConstants.END_DOCUMENT:
						if(states.isEmpty())
						{
							parser.close();
						}
						else
						{
							// TODO: throw exception
						}
						break;
	
					case XMLStreamConstants.START_ELEMENT:
						handleStartElement();
						break;

					case XMLStreamConstants.END_ELEMENT:
						handleEndElement();
						break;
	
					default:
						break;
				}
				
				parser.next();
			}
		}
		catch (XMLStreamException e)
		{
			throw new ReadException(e.getMessage());
		}
		
		gpxData.calculateBounds();
		return gpxData;
	}

	private void handleStartElement() throws ReadException
	{
		try
		{
			switch(currentState)
			{
				case INIT:
					if(parser.getLocalName().equals("metadata"))
					{
						
					}
					else if(parser.getLocalName().equals("wpt"))
					{
						states.push(currentState);
						currentState = state.WAYPOINT;
						
						double latitude = new Double(parser.getAttributeValue(null, "lat"));
						double longitude = new Double(parser.getAttributeValue(null, "lon"));
						
						currentWayPoint = new WayPoint(new LatLon(latitude, longitude));
					}
					else if(parser.getLocalName().equals("rte"))
					{
						
					}
					else if(parser.getLocalName().equals("trk"))
					{
						states.push(currentState);
						currentState = state.TRACK;
						
						currentTrack = new GPXTrack();
					}
					else if(parser.getLocalName().equals("extensions"))
					{
						
					}
					break;
				case WAYPOINT:
					if(parser.getLocalName().equals("ele"))
					{
						double elevation = new Double(parser.getElementText());
						currentWayPoint.setElevation(elevation);
					}
					else if(parser.getLocalName().equals("name"))
					{
						String name = new String(parser.getElementText());
						currentWayPoint.setName(name);
					}
					else if(parser.getLocalName().equals("time"))
					{
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss"); // ignore timezone
						Date date = sdf.parse(parser.getElementText());
						currentWayPoint.setDateTime(date);
					}
					break;
				case ROUTE:
					break;
				case TRACK:
					if(parser.getLocalName().equals("name"))
					{
						String name = new String(parser.getElementText());
						currentTrack.setName(name);
					}
					else if(parser.getLocalName().equals("trkseg"))
					{
						states.push(currentState);
						currentState = state.TRACKSEGMENT;
					}
					break;
				case TRACKSEGMENT:
					if(parser.getLocalName().equals("trkpt"))
					{
						states.push(currentState);
						currentState = state.WAYPOINT;
						
						double latitude = new Double(parser.getAttributeValue(null, "lat"));
						double longitude = new Double(parser.getAttributeValue(null, "lon"));
						
						currentWayPoint = new WayPoint(new LatLon(latitude, longitude));
					}
					break;
				default:
					throw new RuntimeException("Unhandled state!");
			}
		}
		catch(Exception e)
		{
			if(e.getMessage() != null)
			{
				throw new ReadException(String.format("Error while parsing line number %s:\n%s", parser.getLocation().getLineNumber(), e.getMessage()));				
			}
			else
			{
				throw new ReadException(String.format("Error while parsing line number %s", parser.getLocation().getLineNumber()));
			}
		}
	}
	
	private void handleEndElement()
	{
		switch(currentState)
		{
			case INIT:
				break;
			case WAYPOINT:
				if(parser.getLocalName().equals("wpt") || parser.getLocalName().equals("trkpt")) // only go the next state if this is really the end element of the current state
				{
					currentState = states.pop();
					
					if(currentState == state.TRACKSEGMENT)
					{
						currentTrack.add(currentWayPoint);
					}
					else if(currentState == state.INIT)
					{
						gpxData.addWayPoint(currentWayPoint);					
					} 
					else
					{
						throw new RuntimeException("Unhandled state!");
					}
					currentWayPoint = null;
				}
				break;
			case ROUTE:
				break;
			case TRACK:
				if(parser.getLocalName().equals("trk")) // only go the next state if this is really the end element of the current state
				{
					currentState = states.pop();
					gpxData.getTracks().add(currentTrack);
					currentTrack = null;
				}
				break;
			case TRACKSEGMENT:
				if(parser.getLocalName().equals("trkseg")) // only go the next state if this is really the end element of the current state
				{
					currentState = states.pop();
				}
				break;
			default:
				throw new RuntimeException("Unhandled state!");
		}
	}
}
