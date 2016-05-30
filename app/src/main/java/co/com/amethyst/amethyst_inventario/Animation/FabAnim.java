package co.com.amethyst.amethyst_inventario.Animation;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import co.com.amethyst.amethyst_inventario.Activities.MainActivity;

/**
 * Created by Angel on 5/19/2016.
 */
class FabAnim extends FloatingActionButton.Behavior {

    public FabAnim(Context context, AttributeSet attrs) {
        super();
    }
    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout,
                                       FloatingActionButton child, View directTargetChild,
                                       View target, int nestedScrollAxes) {
        return nestedScrollAxes == ViewCompat.SCROLL_AXIS_VERTICAL ||
                super.onStartNestedScroll(coordinatorLayout, child, directTargetChild, target,
                        nestedScrollAxes);
    }

    @Override
    public void onNestedScroll(CoordinatorLayout coordinatorLayout, FloatingActionButton child,
                               View target, int dxConsumed, int dyConsumed, int dxUnconsumed,
                               int dyUnconsumed) {
        Log.d("Animation","onNestedScroll called");
        MainActivity mainActivity=new MainActivity();
        if (mainActivity.viewPager.getCurrentItem() == 0) {
            if (dyConsumed > 0 && child.getVisibility() == View.VISIBLE ) {
                child.hide();
            } else if (dyConsumed < 0 && child.getVisibility() != View.VISIBLE) {
                child.show();
            }

        }
    }
}

