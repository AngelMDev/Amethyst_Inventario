package co.com.amethyst.amethyst_inventario.Adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import co.com.amethyst.amethyst_inventario.Fragments.IOFragment;
import co.com.amethyst.amethyst_inventario.Fragments.InventoryFragment;

/**
 * Created by Angel on 4/11/2016.
 */
public class PagerAdapter extends FragmentPagerAdapter {
    private final int mNumOfTabs;
    private Context context;
    private String tabTitles[] = new String[] { "Inventario", "Ganancias/Perdidas" };
    public PagerAdapter(FragmentManager fm, int NumOfTabs, Context context) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
        this.context=context;
    }
    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return InventoryFragment.newInstance(position+1);
            case 1:
                return IOFragment.newInstance(position+1);
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }
}
