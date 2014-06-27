package com.legoo.haier.Handler.Json;

import com.legoo.haier.Model.Base.ModelInterface;


/**
 * @class Model Json Handler
 * @author LeonNoW
 * @version 1.0
 * @date 2013-9-10
 */
public abstract class ModelJsonHandler extends JsonHandler
{      	
	private ModelInterface _model;
	
	public ModelInterface getModel()
	{
		return _model;
	}
	 
	protected void setModel(ModelInterface model)
	{
		_model = model;
	}
	
	@Override
	public boolean isValid() 
	{
		return _model != null;
	}
	
	public ModelJsonHandler() {}
} 