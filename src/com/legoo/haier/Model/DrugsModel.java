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
public class DrugsModel extends BaseModel implements ModelInterface, Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6101194674262308496L;
	private String _serialNumber;
	private String _category;
	private String _name;
	private String _form;
	private String _specifications;
	private String _manufacturer;
	private String _price;
	
	
	public DrugsModel()
	{
		_serialNumber = null;
		_category = null;
		_name = null;
		_form = null;
		_specifications = null;
		_manufacturer = null;
		_price = null;		
	}
	
	@Override
	public void dispose()
	{
		_serialNumber = null;
		_category = null;
		_name = null;
		_form = null;
		_specifications = null;
		_manufacturer = null;
		_price = null;
	}

	public String getSerrialNumber() {
		return _serialNumber;
	}

	public void setSerrialNumber(String _serrialNumber) {
		this._serialNumber = _serrialNumber;
	}

	public String getCategory() {
		return _category;
	}

	public void setCategory(String _category) {
		this._category = _category;
	}

	public String getName() {
		return _name;
	}

	public void setName(String _name) {
		this._name = _name;
	}

	public String getForm() {
		return _form;
	}

	public void setForm(String _form) {
		this._form = _form;
	}

	public String getSpecifications() {
		return _specifications;
	}

	public void setSpecifications(String _specifications) {
		this._specifications = _specifications;
	}

	public String getManufacturer() {
		return _manufacturer;
	}

	public void setManufacturer(String _manufacturer) {
		this._manufacturer = _manufacturer;
	}

	public String getPrice() {
		return _price;
	}

	public void setPrice(String _price) {
		this._price = _price;
	}
}
