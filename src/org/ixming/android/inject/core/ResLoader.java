package org.ixming.android.inject.core;


import org.ixming.android.inject.ResTargetType;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.XmlResourceParser;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Movie;
import android.graphics.drawable.Drawable;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

/**
 * 工具中加载Res的操作类。
 * 
 * @author Yin Yong
 */
class ResLoader {

	public ResLoader() {
	}
	
	private void checkContext(Context context, String which) {
		if (null == context) {
			throw new NullPointerException("context is null, detail = " + which);
		}
	}
	
	private void checkId(int id, String which) {
		if (id <= 0) {
			throw new UnsupportedOperationException("invalid id = " + id + ", detail = " + which);
		}
	}
	
	public Object loadThemedRes(ResTargetType type, Context themedContext, String name) {
		checkContext(themedContext, "loadThemedRes<themedContext>");
		if (type.isSupportTheme()) {
			throw new UnsupportedOperationException("target res type : " + type.getAsAndroidResType()
					+ " do not support theme operations!");
		}
		int id = themedContext.getResources().getIdentifier(type.name(),
				type.getAsAndroidResType(), themedContext.getPackageName());
		checkId(id, "cannot find a valid id by name = " + name);
		return loadRes(type, themedContext, id);
	}
	
    public Object loadRes(ResTargetType type, Context context, int id) {
    	checkContext(context, "loadRes<localContext>");
    	checkId(id, "");
    	
    	try {
    		switch (type) {
	            case Animation:
	                return getAnimation(context, id);
	            case Bitmap:
	                return getBitmap(context, id);
	            case Boolean:
	            	return getBoolean(context, id);
	            case Color:
	                return getColor(context, id);
	            case ColorStateList:
	                return getColorStateList(context, id);
	            case Dimension:
	                return getDimension(context, id);
	            case DimensionPixelOffset:
	                return getDimensionPixelOffset(context, id);
	            case DimensionPixelSize:
	                return getDimensionPixelSize(context, id);
	            case Drawable:
	                return getDrawable(context, id);
	            case Integer:
	                return getInteger(context, id);
	            case IntArray:
	                return getIntArray(context, id);
	            case Movie:
	                return getMovie(context, id);
	            case String:
	                return getString(context, id);
	            case StringArray:
	                return getStringArray(context, id);
	            case Text:
	                return getText(context, id);
	            case TextArray:
	                return getTextArray(context, id);
	            case Xml:
	                return getXml(context, id);
	            default:
	                break;
    		}
    		return null;
		} catch (Exception e) {
			throw new RuntimeException("Is ResTargetType[" + type + "] what you need ? detail: " + e.getMessage());
		}
    }

    private Animation getAnimation(Context context, int id) {
        return AnimationUtils.loadAnimation(context, id);
    }

    private Bitmap getBitmap(Context context, int id) {
        return BitmapFactory.decodeResource(context.getResources(), id);
    }
    
    private boolean getBoolean(Context context, int id) {
    	return context.getResources().getBoolean(id);
    }

    private int getColor(Context context, int id) {
        return context.getResources().getColor(id);
    }

    private ColorStateList getColorStateList(Context context, int id) {
        return context.getResources().getColorStateList(id);
    }

    private float getDimension(Context context, int id) {
        return context.getResources().getDimension(id);
    }

    private int getDimensionPixelOffset(Context context, int id) {
        return context.getResources().getDimensionPixelOffset(id);
    }

    private int getDimensionPixelSize(Context context, int id) {
        return context.getResources().getDimensionPixelSize(id);
    }

    private Drawable getDrawable(Context context, int id) {
        return context.getResources().getDrawable(id);
    }

    private int getInteger(Context context, int id) {
        return context.getResources().getInteger(id);
    }

    private int[] getIntArray(Context context, int id) {
        return context.getResources().getIntArray(id);
    }

    private Movie getMovie(Context context, int id) {
        return context.getResources().getMovie(id);
    }

    private String getString(Context context, int id) {
        return context.getResources().getString(id);
    }

    private String[] getStringArray(Context context, int id) {
        return context.getResources().getStringArray(id);
    }

    private CharSequence getText(Context context, int id) {
        return context.getResources().getText(id);
    }

    private CharSequence[] getTextArray(Context context, int id) {
        return context.getResources().getTextArray(id);
    }

    private XmlResourceParser getXml(Context context, int id) {
        return context.getResources().getXml(id);
    }
}
