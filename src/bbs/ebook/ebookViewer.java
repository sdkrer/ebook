package bbs.ebook;

import android.app.Activity;
import android.os.Bundle;
import android.graphics.*;
import android.view.*;
import java.util.*;

public class ebookViewer extends Activity implements TextListener
{
	private Bitmap bitmap;
	private Canvas bitmapCanvas;
	private int width;
	private int height;
	private final int multiplier = 1;
	private CanvasTextView textView;

	private BitmapProvider bitmapProvider;

	private CurlView curlView;

	/* 텍스트 리스너의 구현 */
	public void DrawComplete(Canvas canvas)
	{
		// canvas.drawBitmap( bitmap, 0.0f, 0.0f , null );
		// Bitmap provider에게 정보를 제공해줍니다.
		bitmapProvider.pushBitmap( 0, bitmap );
	}

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

		textView = (CanvasTextView) findViewById( R.id.contents );

		Display display =
		   	((WindowManager)getSystemService(WINDOW_SERVICE)).getDefaultDisplay();

		width  = display.getWidth() * multiplier;
		height = display.getHeight() * multiplier;
		
		// 비트맵과 캔버스 정보를 초기화 합니다.
		bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888 );
		bitmapCanvas = new Canvas( bitmap );

		textView.setCanvas( bitmapCanvas );
		textView.setListener( this );

		int index = 0;
		if ( getLastNonConfigurationInstance() != null ) {
			index = (Integer) getLastNonConfigurationInstance();
		}

		// CurlView 관련된 정보를 초기화 해줍니다. 
		curlView = (CurlView ) findViewById(R.id.curl);
		bitmapProvider = new BitmapProvider();
		curlView.setBitmapProvider( bitmapProvider );
		curlView.setSizeChangedObserver( new SizeChangedObserver() );
		curlView.setCurrentIndex(index);
		curlView.setBackgroundColor(0xFF202830);
    }

	final int INIT_PAGES = 100;
	final int GET_PAGES = 10; 

	/*
	 * Bitmap Provider. - Page Provider
	 */
	private class BitmapProvider implements CurlView.BitmapProvider {

		Vector<Bitmap> bitmapVector;

		/* 
		 * 비트맵 제공자.
		 * 생성자. 비트맵 벡터를 초기화 한다.
		 */
		BitmapProvider()
		{
			bitmapVector = new Vector<Bitmap>( INIT_PAGES, GET_PAGES );
		}

		public void pushBitmap(int index, Bitmap bitmap)
		{
			if ( bitmapVector.size() == index )
				bitmapVector.add( bitmap );
			else if ( bitmapVector.size() > index )
				bitmapVector.set( index, bitmap );	
		}
		
		@Override
		public Bitmap getBitmap( int width, int height, int index ) {
			if ( bitmapVector.isEmpty() ) return null;
			return bitmapVector.get(0) ;
		}

		@Override
		public int getBitmapCount() {
			return 10;//bitmapVector.size();
		}
	}

	/*
	 * Curl view 설정 함수들...
	 */
	@Override
	public void onPause() {
		super.onPause();
		curlView.onPause();
	}

	@Override
	public void onResume() {
		super.onResume();
		curlView.onResume();
	}

	@Override
	public Object onRetainNonConfigurationInstance() {
		return curlView.getCurrentIndex();
	}

	/**
	 * CurlView size changed observer.
	 */
	private class SizeChangedObserver implements CurlView.SizeChangedObserver {
		@Override
		public void onSizeChanged(int w, int h) {
			if (w > h) {
				curlView.setViewMode(CurlView.SHOW_TWO_PAGES);
				//curlView.setMargins(.1f, .05f, .1f, .05f);
				curlView.setMargins(.0f, .0f, .0f, .0f);
			} else {
				curlView.setViewMode(CurlView.SHOW_ONE_PAGE);
				//curlView.setMargins(.1f, .1f, .1f, .1f);
				curlView.setMargins(.0f, .0f, .0f, .0f);
			}
		}
	}
}
