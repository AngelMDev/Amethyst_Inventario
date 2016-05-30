package co.com.amethyst.amethyst_inventario.Activities;


import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.View;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import co.com.amethyst.amethyst_inventario.Adapters.PagerAdapter;
import co.com.amethyst.amethyst_inventario.R;

public class MainActivity extends AppCompatActivity {
Toolbar toolBar;
 public ViewPager viewPager;
 FloatingActionsMenu fab;
 FloatingActionButton fab1;
 FloatingActionButton fab2;
    Animation fab_open,fab_close;
    Context context;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolBar = (Toolbar) this.findViewById(R.id.toolbar);
        this.setSupportActionBar(toolBar);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new PagerAdapter(getSupportFragmentManager(), 2,
                MainActivity.this));
        context=this.getApplicationContext();
        fab_open=AnimationUtils.loadAnimation(this,R.anim.fab_open);
        fab_close=AnimationUtils.loadAnimation(this,R.anim.fab_close);
        // Give the TabLayout the ViewPager
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        fab = (FloatingActionsMenu) findViewById(R.id.fab);
        fab1=(FloatingActionButton) findViewById(R.id.fab1);
        fab2=(FloatingActionButton) findViewById(R.id.fab2);
        fab1.setTitle("Nueva venta");
        fab2.setTitle("Agregar inventario");
        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context,NewSaleActivity.class);
                startActivity(intent);
                fab.collapse();
            }
        });
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context,AddInventory.class);
                startActivity(intent);
                fab.collapse();
            }
        });

viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        if(position==0){

            fab.startAnimation(fab_open);
            fab.setVisibility(View.VISIBLE);
            fab.setClickable(true);
        }
        else {
            fab.startAnimation(fab_close);
            fab.setVisibility(View.INVISIBLE);
            fab.setClickable(false);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
});

    }



}

