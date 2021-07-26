package a4;

public class TransformedPicture implements PixelTransformation {
	
	private Picture source;
	private PixelTransformation xform;
	public TransformedPicture (Picture source, PixelTransformation xform) {
		this.source = source;
		this.xform = xform;
	}
	
	public Pixel transform(Pixel p) {
		for (int i = 0; i < source.getWidth();i++) {
			for ( int j = 0; j < source.getHeight();j++) {
				
			}
		}
	}

}
