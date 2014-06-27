package com.legoo.haier.Extension;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import com.legoo.haier.Application.Haier;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.util.Base64;

/**
 * @class Bitmap Utils
 * @author LeonNoW
 * @version 1.0
 * @date 2013-11-13
 */
public class BitmapUtils
{
	public static Bitmap getResizeBitmapFromFile(String path, int size, boolean autoRotate)
	{
		try 
		{
			File file = new File(path);
			if (file.exists() == true && file.isFile())
			{
				FileInputStream inputStream = new FileInputStream(path);
				Bitmap resizeBitmap = getResizeBitmapFromStream(inputStream.getFD(), inputStream, size);  
				inputStream.close();
				inputStream = null;
				return autoRotate == true ? 
						rotaingImageView(resizeBitmap, readPictureDegreeFromFile(path)): 
					resizeBitmap;
			}
			return null;
		} 
		catch (IOException e) 
		{
			System.gc();
			return null;
		}
	}
	
	private static Bitmap getResizeBitmapFromStream(FileDescriptor fileDescriptor, InputStream inputStream, int size)
	{
		BitmapFactory.Options options = new BitmapFactory.Options();  
		options.inPreferredConfig = Bitmap.Config.RGB_565;
		options.inJustDecodeBounds = true;  
		BitmapFactory.decodeFileDescriptor(fileDescriptor, null, options);  
		options.inSampleSize = BitmapUtils.computeSampleSize(options, size, 2 * size * size);  
		options.inJustDecodeBounds = false;  
		return BitmapFactory.decodeStream(inputStream, null, options);  
	}
	
	public static Bitmap getResizeBitmapFromByte(byte[] imageData, int maxSize, int resizeWidth, int resizeHeight)
	{
		try
		{
			BitmapFactory.Options options = new BitmapFactory.Options();  
			options.inPreferredConfig = Bitmap.Config.RGB_565;
			options.inJustDecodeBounds = true;  
			BitmapFactory.decodeByteArray(imageData, 0, imageData.length, options);
			if (maxSize > 0 && (options.outWidth > maxSize || options.outHeight > maxSize))
			{
				options.inSampleSize = BitmapUtils.computeSampleSize(options, maxSize, maxSize * maxSize);  
			}
			options.inJustDecodeBounds = false;  
			Bitmap bitmap = BitmapFactory.decodeByteArray(imageData, 0, imageData.length, options);
			if (resizeWidth > 0 && resizeHeight > 0)
			{
				bitmap = Bitmap.createScaledBitmap(bitmap, resizeWidth, resizeHeight, true);
			}
			return bitmap;
		} 
		catch (Exception e) 
		{
			Haier.getInstance().getToast().show(e.getMessage());
			System.gc();
		}
		return null;
	}
	
	public static int computeSampleSize(BitmapFactory.Options options,    
            int minSideLength, int maxNumOfPixels) 
	{    
        int initialSize = computeInitialSampleSize(options, minSideLength,    
                maxNumOfPixels);    
        
        int roundedSize;    
        if (initialSize <= 8) 
        {    
            roundedSize = 1;    
            while (roundedSize < initialSize) {    
                roundedSize <<= 1;    
            }    
        } 
        else 
        {    
            roundedSize = (initialSize + 7) / 8 * 8;    
        }    
        
        return roundedSize;    
    }    
        
    private static int computeInitialSampleSize(BitmapFactory.Options options,    
            int minSideLength, int maxNumOfPixels) 
    {    
        double w = options.outWidth;    
        double h = options.outHeight;    
        
        int lowerBound = (maxNumOfPixels == -1) ? 1 : (int) Math.ceil(Math    
                .sqrt(w * h / maxNumOfPixels));    
        int upperBound = (minSideLength == -1) ? 128 : (int) Math.min(    
                Math.floor(w / minSideLength), Math.floor(h / minSideLength));    
        
        if (upperBound < lowerBound) 
        {      
            return lowerBound;    
        }    
        
        if ((maxNumOfPixels == -1) && (minSideLength == -1)) 
        {    
            return 1;    
        } 
        else if (minSideLength == -1) 
        {    
            return lowerBound;    
        } 
        else 
        {    
            return upperBound;    
        }    
    }  
    
    private static Bitmap rotaingImageView(Bitmap bitmap, int angle) 
    {  
        Matrix matrix = new Matrix();;  
        matrix.postRotate(angle);  

        Bitmap rotateBitmap = Bitmap.createBitmap(bitmap, 0, 0,  
                bitmap.getWidth(), bitmap.getHeight(), matrix, true);  
        return rotateBitmap;  
    }

    private static int readPictureDegreeFromFile(String path) 
    {  
        int degree  = 0;  
        try 
        {  
            ExifInterface exifInterface = new ExifInterface(path);  
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);  
            switch (orientation) 
            {  
	            case ExifInterface.ORIENTATION_ROTATE_90: 
	            {
	                degree = 90;  
	                break;  
	            }
	            case ExifInterface.ORIENTATION_ROTATE_180:
	            {
                    degree = 180;  
                    break;  
	            }
	            case ExifInterface.ORIENTATION_ROTATE_270: 
	            {
                    degree = 270;  
                    break;  
	            }
            }  
        } 
        catch (IOException e) 
        {  
        	return 0;
        }  
        return degree;  
    }  
    
    public static String convertBitmapToBase64(Bitmap bitmap) 
	{  
	    String result = null;  
	    ByteArrayOutputStream outputStream = null;  
	    try 
	    {  
	        if (bitmap != null) 
	        {  
	        	outputStream = new ByteArrayOutputStream();  
	            bitmap.compress(Bitmap.CompressFormat.JPEG, 60, outputStream);  
	  
	            outputStream.flush();  
	            outputStream.close();  
	  
	            byte[] bitmapBytes = outputStream.toByteArray();  
	            result = Base64.encodeToString(bitmapBytes, Base64.DEFAULT);  
	        }  
	    } 
	    catch (IOException e) 
	    {  
	    	result = "";  
	    } 
	    finally 
	    {  
	        try 
	        {  
	            if (outputStream != null) 
	            {  
	            	outputStream.flush();  
	            	outputStream.close();  
	            }  
	        } 
	        catch (IOException e) 
	        {  
	        	result = "";  
	        }  
	    }  
	    return result;  
	}  
}
