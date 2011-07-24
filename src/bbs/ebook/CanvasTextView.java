package bbs.ebook;

import android.widget.*;
import android.content.*;
import android.util.*;
import android.graphics.*;


public class CanvasTextView extends TextView
{
	public Canvas customCanvas = null;
	public TextListener listener = null;

	public CanvasTextView( Context context, AttributeSet attrs )
	{
		super(context, attrs);
	}

	public CanvasTextView( Context context, AttributeSet attrs, int defStyle )
	{
		super(context, attrs, defStyle);
	}
	
	public void setCanvas( Canvas userCanvas )
	{
		customCanvas = userCanvas;
	}

	public void setListener( TextListener ltr )
	{
		listener = ltr;
	}

	@Override
	public void onDraw( Canvas canvas )
	{
		/* 사용자가 설정한 캔버스가 있으면 그것을 사용한다. */
		if ( customCanvas == null )
			super.onDraw( canvas );
		else
		{
			super.onDraw( customCanvas );
			if ( listener != null ) listener.DrawComplete(canvas);
		}
	}
}
