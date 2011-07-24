ebook viewer 디자인 문서.
2011-07-07 : 윤지한 작성.

0. 생각해볼점. ( 여기서 고려할 점들은 나중에 추가 구현한다. )

TextView에서 독립적으로 캔버스를 이용해서 비트맵을 그리면 블러효과 처럼 그려진다.
 -> getDrawable(), getBackground() 메소드로 Drawable객체 만들어 
	Drawable 객체를 Bitmap으로 변환해서 해결했어요.
	/*
	n b = new Button(context);  
	b.setText("Hello World");  
	b.setDrawingCacheEnabled(true);  
	// this is the important line :)  
	// Without it the button will have a dimension of 0,0  
	// and the bitmap will be null  
	b.layout(0, 0, 100, 100);  
	b.buildDrawingCache();  
	Bitmap bitmap = Bitmap.createBitmap(b.getDrawingCache());  
	b.setDrawingCacheEnabled(false);  
	*/

TextView가 항상 그려지는 메소듣가 호출된다는 점을 생각해보아라.
약한 참조 해쉬맵을 사용할 것을 고려해보자.

	- 첫 번째 페이지를 그린다.
	- 페이지를 넘긴다. -> 비트맵 프로바이더에서 비트맵을 가져온다. 
		-> 여분의 페이지를 읽어온다. -> 페이지 프로바이더에게 데이터를 넘겨준다.

1. CanvasTextView에 필요한 기능들.

	- 비트맵을 polling해서 가져오는 기능.
	- 해당 페이지 세팅 기능.

2. 구현 순서

	- CurlView 및 페이지를 그려주는 클래스를 ebookViewer에 추가해준다.
	- *0th milestone.

	- 일단 BitmapProvider를 만든다.
	- 비트맵 프로바이더와 CanvasTextViewer를 연결한다.
	- 비트맵 프로바이더의 인덱스에 상관없이 동일 한 페이지를 보여주는 TextViewer를 만든다.
	- 테스트 해본다.
	- *1th milestone.

invalidate() 가 호출되면  OnDraw가 그려진다.

---------------------------------------------
3. 세부 구현정보

	페이지 읽어오기 : 텍스트 정보를 받아온다.
	처음 1,2,3페이지 정보를 읽어서 그린다.(비트맵으로 그려준다.)
	슬라이딩 윈도우 처럼 페이지가 넘어갈 때 마다 -+2의 페이지 정보 비트맵을 저장해둡니다. 
	안쓰는 비트맵 이미지는 해제 해준다.(?) : gc에 의해서 가비지 컬렉터 당하게 해주는 방법을
	찾아보자. => weakhashmap

	비트맵 프로바이더에 사용될 구조체
	비트맵 벡터 or weakhashmap


