package com.legoo.haier.Model;

import com.legoo.haier.Model.Base.BaseModel;
import com.legoo.haier.Model.Base.ModelInterface;

/**
 * @class User Model
 * @author LeonNoW
 * @version 1.0
 * @date 2014-02-14
 */
public class RepairTVModel extends BaseModel implements ModelInterface
{

	private String code;
	private String number;
	@Override
	public void dispose()
	{
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}

}
