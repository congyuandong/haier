package com.legoo.haier.AsyncTask.Callback;

import com.legoo.haier.AsyncTask.Base.JsonEvent;
import com.legoo.haier.Model.Base.ModelInterface;

/**
 * @class Model Event
 * @author LeonNoW
 * @version 1.0
 * @date 2013-9-10
 */
public class ModelEvent extends JsonEvent
{
	private static final long serialVersionUID = -7015308363609671365L;
	
	private ModelInterface _model;
	
	public ModelEvent(Object source)
    {
        super(source);
        source = null;
    }
	
	public void setModel(ModelInterface model)
	{
		_model = model;
	}
	
	public ModelInterface getModel()
	{
		return _model;
	}
	
	@Override
	public void dispose()
	{
		_model = null;
	}
}
