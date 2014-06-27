package com.legoo.haier.Model;

import java.util.List;

import com.legoo.haier.Model.Base.BaseModel;
import com.legoo.haier.Model.Base.ModelInterface;

/**
 * @class Channel Model
 * @author LeonNoW
 * @version 1.0
 * @date 2014-02-10
 */
public class ExpertFilterDepartmentModel extends BaseModel implements ModelInterface
{
	private List<ChannelModel> models;
	
	public ExpertFilterDepartmentModel()
	{
		
	}
	
	@Override
	public void dispose()
	{
		models = null;
	}

	/**
	 * @return the models
	 */
	public List<ChannelModel> getModels() {
		return models;
	}

	/**
	 * @param models the models to set
	 */
	public void setModels(List<ChannelModel> models) {
		this.models = models;
	}
}
