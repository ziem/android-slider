# android-slider

Slider implementation based on [ViewPager](http://developer.android.com/reference/android/support/v4/view/ViewPager.html).
It requires 9+ API level.

## Usage

Add Slider view to your xml:

    <me.ziem.slider.Slider 
        xmlns:android="http://schemas.android.com/apk/res/android"
        xmlns:slider="http://schemas.android.com/apk/res-auto"
        android:id="@+id/pager"
        sndroid:layout_width="match_parent"
        android:layout_height="match_parent">

## Configuration

### XML

    slider:touchEnabled="true"
    slider:scrollDuration="2000"
    slider:scrollDelay="500"
    slider:stopScrollingAfterTouchEnabled="false"
    
### Java

    Slider slider = (Slider) findViewById(R.id.pager);
        
    slider.setTouchEnabled(true); // enables page dragging
    slider.setScrollDuration(2000); // defines page change scroll duration 
    slider.setScrollDelay(500); // defines page change delay
    slider.setStopScrollingAfterTouchEnabled(false); // disables scrolling after user interaction (e.g. drag)

## License

Copyright 2015 Ziem

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
