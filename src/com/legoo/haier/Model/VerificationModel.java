package com.legoo.haier.Model;

import java.io.Serializable;

import com.legoo.haier.Model.Base.BaseModel;
import com.legoo.haier.Model.Base.ModelInterface;

/**
 * @class Contacts Model
 * @author Mlzx
 * @version 1.0
 * @date 2014-04-10
 */
public class VerificationModel extends BaseModel implements ModelInterface, Serializable
{	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6489870244878360919L;

	private String _verificationCode;
	public VerificationModel()
	{
		
	}
	
	@Override
	public void dispose()
	{
		_verificationCode=null;
	}

	public String getVerificationCode() {
		return _verificationCode;
	}

	public void setVerificationCode(String verificationCode) {
		_verificationCode = verificationCode;
	}
}
