package com.legoo.haier.Model.Base;

import java.util.List;

/**
 * @class Base Model
 * @author LeonNoW
 * @version 1.0
 * @date 2014-02-10
 */
public abstract class BaseModel
{
	protected <T> void dispose(List<T> list)
	{
		if (list != null)
		{
			for (T t : list)
			{
				if (t instanceof ModelInterface)
				{
					((ModelInterface) t).dispose();
				}
				t = null;
			}
			list.clear();
			list = null;
		}
	}
}
