package actions;

import gui.SearchComponent;

import java.awt.event.ActionEvent;

import javax.swing.JOptionPane;

import org.geonames.ToponymSearchCriteria;
import org.geonames.ToponymSearchResult;
import org.geonames.WebService;

public class SearchAction extends ApplicationAction
{	
	private SearchComponent searchComponent;
	
	public SearchAction(SearchComponent searchComponent)
	{
		super(null, "search", 0, 0);
		
		this.searchComponent = searchComponent;
	}

	private static final long serialVersionUID = 1L;

	@Override
	public void actionPerformed(ActionEvent event)
	{
		String searchString = searchComponent.getSearchString().trim();
		
		if(searchString.length() > 0)
		{
			searchComponent.setStatus(SearchComponent.Status.SEARCHING);
			ToponymSearchCriteria searchCriteria = new ToponymSearchCriteria();
			searchCriteria.setQ(searchString);
			ToponymSearchResult searchResult = null;
			try
			{
				searchResult = WebService.search(searchCriteria);
			}
			catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			// check if there are any results
			if(searchResult.getTotalResultsCount() > 0)
			{
				searchComponent.setListData(searchResult.getToponyms());
			}
			else
			{
				// TODO i18n
				JOptionPane.showMessageDialog(null, "Your search for \"" + searchString + "\" returned no results.\nPlease try a different term.", "No results", JOptionPane.WARNING_MESSAGE);
			}

			searchComponent.setStatus(SearchComponent.Status.READY);
		}
	}

}
