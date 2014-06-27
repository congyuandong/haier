package com.legoo.haier.Adapter.Layout;

import com.legoo.haier.R;
import com.legoo.haier.Adapter.Base.BaseRelativeLayout;
import com.legoo.haier.Adapter.Base.LayoutInterface;
import com.legoo.haier.Extension.Cache.UnifiedImageFormat;
import com.legoo.haier.Extension.UltraIdeal.UnifiedImageView;
import android.content.Context;

/**
 * @class Pager Item Layout
 * @author LeonNoW
 * @version 1.0
 * @date 2014-02-10
 */
public class PagerItemLayout extends BaseRelativeLayout implements LayoutInterface
{
	private UnifiedImageView _image;
	
	public void setImage(String image)
	{
		_image.setImageUrl(image, UnifiedImageFormat.TYPE_THUMB, UnifiedImageFormat.FORCE_STROAGE);
	}
	
	public PagerItemLayout(Context context)
	{
		super(context, R.layout.slide_item_pager);
		initView();
	}

	private void initView() 
	{
		_image = (UnifiedImageView) findViewById(R.id.imageSlideItemPager);
	}
	
	@Override
	public void dispose() 
	{
		_image = null;
	}
}
