package com.legoo.haier.Model;

import com.legoo.haier.Model.Base.BaseModel;
import com.legoo.haier.Model.Base.ModelInterface;

/**
 * @class Pager Model
 * @author LeonNoW
 * @version 1.0
 * @date 2014-02-10
 */
public class PagerModel extends BaseModel implements ModelInterface
{
	private String _title;
	private String _image;
	private String _url;
	
	public String getTitle()
	{
		return _title;
	}
	
	public void setTitle(String title)
	{
		_title = title;
	}
	
	public String getImage()
	{
		return _image;
	}
	
	public void setImage(String image)
	{
		_image = image;
	}
	
	public String getUrl()
	{
		return _url;
	}
	
	public void setUrl(String url)
	{
		_url = url;
	}
	
	public PagerModel()
	{
		
	}
	
	public PagerModel(String title, String image, String url)
	{
		_title = title;
		_image = image;
		_url = url;
	}
	
	@Override
	public void dispose()
	{
		_title = null;
		_image = null;
		_url = null;
	}
}
