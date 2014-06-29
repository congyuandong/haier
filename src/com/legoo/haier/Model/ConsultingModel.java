package com.legoo.haier.Model;

import com.legoo.haier.Model.Base.BaseModel;
import com.legoo.haier.Model.Base.ModelInterface;

/**
 * @class User Model
 * @author LeonNoW
 * @version 1.0
 * @date 2014-02-14
 */
public class ConsultingModel extends BaseModel implements ModelInterface
{
	private String id;
	private String message;
	private String time;

	@Override
	public void dispose()
	{
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}
}
