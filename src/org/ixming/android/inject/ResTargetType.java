package org.ixming.android.inject;

/**
 * 组员对象的种类。（请根据实际需要正确选择，否则将导致异常）
 * 
 * @author Yin Yong
 * @version 1.0
 */
public enum ResTargetType {
	
    Animation {
		@Override
		public java.lang.String getAsAndroidResType() {
			return "anim";
		}
	},
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
    Boolean {
		@Override
		public java.lang.String getAsAndroidResType() {
			return "value";
		}
	},
    Color {
		@Override
		public java.lang.String getAsAndroidResType() {
			return "color";
		}
	},
    ColorStateList {
		@Override
		public java.lang.String getAsAndroidResType() {
			return "color";
		}
	},
    Dimension {
		@Override
		public java.lang.String getAsAndroidResType() {
			return "dimen";
		}
	},
    DimensionPixelOffset {
		@Override
		public java.lang.String getAsAndroidResType() {
			return "dimen";
		}
	},
    DimensionPixelSize {
		@Override
		public java.lang.String getAsAndroidResType() {
			return "dimen";
		}
	},
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
    Integer {
		@Override
		public java.lang.String getAsAndroidResType() {
			return "value";
		}
	},
    IntArray {
		@Override
		public java.lang.String getAsAndroidResType() {
			return "array";
		}
	},
    Movie {
		@Override
		public java.lang.String getAsAndroidResType() {
			return "raw";
		}
	},
    String {
		@Override
		public java.lang.String getAsAndroidResType() {
			return "string";
		}
	},
    StringArray {
		@Override
		public java.lang.String getAsAndroidResType() {
			return "array";
		}
	},
    Text {
		@Override
		public java.lang.String getAsAndroidResType() {
			return "string";
		}
	},
    TextArray {
		@Override
		public java.lang.String getAsAndroidResType() {
			return "array";
		}
	},
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
