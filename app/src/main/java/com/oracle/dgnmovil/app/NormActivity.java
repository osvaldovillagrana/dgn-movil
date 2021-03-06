package com.oracle.dgnmovil.app;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.oracle.dgnmovil.util.DbUtil;


public class NormActivity extends ActionBarActivity {

    int favorite;
    long id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_norm);

        Typeface tf = Typeface.createFromAsset(getAssets(), "font/MavenPro-Regular.ttf");

        ActionBar actionBar = getActionBar();
        actionBar.setDisplayShowHomeEnabled(false);

        int titleId = getResources().getIdentifier("action_bar_title", "id", "android");
        TextView titleTextView = (TextView) findViewById(titleId);
        titleTextView.setTextColor(Color.WHITE);
        titleTextView.setTypeface(tf);

        Button report = (Button) findViewById(R.id.report_btn);
        report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ReportActivity.class);
                startActivity(intent);
            }
        });

        Button contact = (Button) findViewById(R.id.contact_btn);
        contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + "36336892"));
                startActivity(callIntent);
            }
        });

        TextView code = (TextView) findViewById(R.id.norm_code);
        TextView organism = (TextView) findViewById(R.id.norm_organism);
        TextView title = (TextView) findViewById(R.id.norm_title);
        TextView publicationBanner = (TextView) findViewById(R.id.norm_publication_banner);
        TextView publication = (TextView) findViewById(R.id.norm_publication);
        TextView releaseBanner = (TextView) findViewById(R.id.norm_relase_banner);
        TextView release = (TextView) findViewById(R.id.norm_release);
        TextView internationalBanner = (TextView) findViewById(R.id.norm_international_banner);
        TextView international = (TextView) findViewById(R.id.norm_international);
        TextView concordanceBanner = (TextView) findViewById(R.id.norm_concordance_banner);
        TextView concordance = (TextView) findViewById(R.id.norm_concordance);
        TextView productBanner = (TextView) findViewById(R.id.norm_product_banner);
        TextView product = (TextView) findViewById(R.id.norm_product);
        TextView raeBanner = (TextView) findViewById(R.id.norm_rae_banner);
        TextView rae = (TextView) findViewById(R.id.norm_rae);

        code.setTypeface(tf);
        organism.setTypeface(tf);
        title.setTypeface(tf);
        publicationBanner.setTypeface(tf);
        publication.setTypeface(tf);
        releaseBanner.setTypeface(tf);
        release.setTypeface(tf);
        internationalBanner.setTypeface(tf);
        international.setTypeface(tf);
        concordanceBanner.setTypeface(tf);
        concordance.setTypeface(tf);
        productBanner.setTypeface(tf);
        product.setTypeface(tf);
        raeBanner.setTypeface(tf);
        rae.setTypeface(tf);

        Intent intent = getIntent();
        String[] attributes = intent.getStringArrayExtra(SearchActivity.NORMA_ATTRIBUTES);
        favorite = intent.getIntExtra(SearchActivity.NORMA_FAVORITE, 0);
        id = intent.getLongExtra(SearchActivity.NORMA_ID, 0);

        code.setText(attributes[0] != null ? attributes[0] : "N/A");
        title.setText(attributes[1] != null ? attributes[1] : "N/A");
        release.setText(attributes[2] != null ? attributes[2] : "N/A");
        publication.setText(attributes[4] != null ? attributes[4] : "N/A");
        international.setText(attributes[5] != null ? attributes[5] : "N/A");
        concordance.setText(attributes[6] != null ? attributes[6] : "N/A");
        final String file = attributes[7];

        ImageView icon = (ImageView) findViewById(R.id.norm_icon);
        String name = attributes[3].toLowerCase().replaceAll(" ", "_").replaceAll(",", "").replace("-", "");
        int resID = getResources().getIdentifier(name, "drawable",  getPackageName());
        if(resID == 0)
            resID = R.drawable.ic_dgn_ico00;
        icon.setImageResource(resID);

        final DbUtil dbutil = new DbUtil(this);
        product.setText(dbutil.getProducto(id));
        rae.setText(dbutil.getRae(id));

        final Button favoriteButton = (Button) findViewById(R.id.norm_favorite);
        if(favorite == 0)
            favoriteButton.setBackgroundResource(R.drawable.ic_dgn_unlike);
        else
            favoriteButton.setBackgroundResource(R.drawable.ic_dgn_like);

        favoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                favorite = 1 - favorite;
                if(favorite == 0)
                    favoriteButton.setBackgroundResource(R.drawable.ic_dgn_unlike);
                else
                    favoriteButton.setBackgroundResource(R.drawable.ic_dgn_like);
                dbutil.setNormaPreference(id, favorite);
            }
        });

        Button fileButton = (Button) findViewById(R.id.norm_file);
        if(!file.equals("NO APLICA")) {
            fileButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(file));
                    startActivity(browserIntent);
                }
            });
        }
        else
            fileButton.setVisibility(View.INVISIBLE);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.norm, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_search_main) {
            startActivity(new Intent(this, SearchActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
