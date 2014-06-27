package com.legoo.haier.Model;

import com.legoo.haier.Model.Base.BaseModel;
import com.legoo.haier.Model.Base.ModelInterface;

/**
 * @class User Model
 * @author LeonNoW
 * @version 1.0
 * @date 2014-02-14
 */
public class UserModel extends BaseModel implements ModelInterface
{
	private String id;
	private String name;
	private String password;
	private String deviceid;
	@Override
	public void dispose()
	{
		setId(null);
		setName(null);
		setPassword(null);
		setDeviceid(null);
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getDeviceid() {
		return deviceid;
	}
	public void setDeviceid(String deviceid) {
		this.deviceid = deviceid;
	}
	public boolean isRegister()
	{
		return deviceid!=null&&deviceid.equals("");
	}
}
