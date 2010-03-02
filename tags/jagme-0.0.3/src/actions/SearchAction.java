package actions;

import gui.SearchComponent;

import i18n.I18NHelper;

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
				searchComponent.setStatus(SearchComponent.Status.READY);
			}
			catch (Exception e)
			{
				JOptionPane.showMessageDialog(null, I18NHelper.getInstance().getString("search.webserviceerror"), I18NHelper.getInstance().getString("generic.error"), JOptionPane.ERROR_MESSAGE);
				e.printStackTrace();
				return;
			}
			
			// check if there are any results
			if(searchResult.getTotalResultsCount() > 0)
			{
				searchComponent.setListData(searchResult.getToponyms());
			}
			else
			{
				JOptionPane.showMessageDialog(null, I18NHelper.getInstance().getString("search.noresult.msg"), I18NHelper.getInstance().getString("search.noresult.title"), JOptionPane.WARNING_MESSAGE);
			}

			searchComponent.setStatus(SearchComponent.Status.READY);
		}
	}

}
