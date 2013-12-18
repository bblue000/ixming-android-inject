package org.ixming.android.inject;

/**
 * 组员对象的种类。（请根据实际需要正确选择，否则将导致异常）
 * 
 * @author Yin Yong
 * @version 1.0
 */
public enum ResTargetType {
	
	/**
	 * 返回值类型为{@link android.graphics.Animation}
	 */
    Animation {
		@Override
		public java.lang.String getAsAndroidResType() {
			return "anim";
		}
	},
	/**
	 * 返回值类型为{@link android.graphics.Bitmap}
	 */
    Bitmap {
		@Override
		public java.lang.String getAsAndroidResType() {
			return "drawable";
		}
		
		@Override
		public boolean isSupportTheme() {
			return true;
		}
	},
	/**
	 * 返回值类型为{@link boolean}
	 */
    Boolean {
		@Override
		public java.lang.String getAsAndroidResType() {
			return "value";
		}
	},
	/**
	 * 返回值类型为{@link int}
	 */
    Color {
		@Override
		public java.lang.String getAsAndroidResType() {
			return "color";
		}
	},
	/**
	 * 返回值类型为{@link android.content.res.ColorStateList}
	 */
    ColorStateList {
		@Override
		public java.lang.String getAsAndroidResType() {
			return "color";
		}
	},
	/**
	 * 返回值类型为{@link float}
	 */
    Dimension {
		@Override
		public java.lang.String getAsAndroidResType() {
			return "dimen";
		}
	},
	/**
	 * 返回值类型为{@link int}
	 */
    DimensionPixelOffset {
		@Override
		public java.lang.String getAsAndroidResType() {
			return "dimen";
		}
	},
	/**
	 * 返回值类型为{@link int}
	 */
    DimensionPixelSize {
		@Override
		public java.lang.String getAsAndroidResType() {
			return "dimen";
		}
	},
	/**
	 * 返回值类型为{@link android.graphics.drawable.Drawable}
	 */
    Drawable {
		@Override
		public java.lang.String getAsAndroidResType() {
			return "drawable";
		}
		@Override
		public boolean isSupportTheme() {
			return true;
		}
	},
	/**
	 * 返回值类型为{@link int}
	 */
    Integer {
		@Override
		public java.lang.String getAsAndroidResType() {
			return "value";
		}
	},
	/**
	 * 返回值类型为{@link int[]}
	 */
    IntArray {
		@Override
		public java.lang.String getAsAndroidResType() {
			return "array";
		}
	},
	/**
	 * 返回值类型为{@link android.graphics.Movie}
	 */
    Movie {
		@Override
		public java.lang.String getAsAndroidResType() {
			return "raw";
		}
	},
	/**
	 * 返回值类型为{@link java.lang.String}
	 */
    String {
		@Override
		public java.lang.String getAsAndroidResType() {
			return "string";
		}
	},
	/**
	 * 返回值类型为{@link java.lang.String[]}
	 */
    StringArray {
		@Override
		public java.lang.String getAsAndroidResType() {
			return "array";
		}
	},
	/**
	 * 返回值类型为{@link java.lang.String}
	 */
    Text {
		@Override
		public java.lang.String getAsAndroidResType() {
			return "string";
		}
	},
	/**
	 * 返回值类型为{@link java.lang.String[]}
	 */
    TextArray {
		@Override
		public java.lang.String getAsAndroidResType() {
			return "array";
		}
	},
	/**
	 * 返回值类型为{@link android.content.res.XmlResourceParser}
	 */
    Xml {
		@Override
		public java.lang.String getAsAndroidResType() {
			return "xml";
		}
	};
    
	/**
     * 获取该种类型在Android中相应的资源Type
     */
    public abstract String getAsAndroidResType();
    
    /**
     * 该种类型是否支持Theme
     */
    public boolean isSupportTheme() {
    	return false;
    }
}
