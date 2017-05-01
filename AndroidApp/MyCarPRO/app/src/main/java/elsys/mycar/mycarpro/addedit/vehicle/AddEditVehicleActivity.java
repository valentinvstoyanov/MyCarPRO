package elsys.mycar.mycarpro.addedit.vehicle;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.google.common.base.Preconditions;

import butterknife.ButterKnife;
import elsys.mycar.mycarpro.R;
import elsys.mycar.mycarpro.data.VehicleRepositoryImpl;
import elsys.mycar.mycarpro.util.ActivityUtils;
import elsys.mycar.mycarpro.util.ProviderUtils;
import elsys.mycar.mycarpro.util.StringUtils;
import elsys.mycar.mycarpro.util.TokenUtils;

public class AddEditVehicleActivity extends AppCompatActivity {

    private ActionBar mActionBar;
    private String mToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        manageToken();
        setContentView(R.layout.activity_add_edit_vehicle);
        setUpToolbar();

        AddEditVehicleFragment addEditVehicleFragment = AddEditVehicleFragment.newInstance();
        AddEditVehiclePresenter addEditVehiclePresenter = new AddEditVehiclePresenter(null, ProviderUtils.getVehicleRepository(mToken), addEditVehicleFragment, true);
        addEditVehicleFragment.setPresenter(addEditVehiclePresenter);

        ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                addEditVehicleFragment,
                R.id.frame_layout_add_vehicle);
    }

    @Override
    protected void onResume() {
        super.onResume();
        manageToken();
    }

    private boolean manageToken() {
        String token = TokenUtils.getToken(this);
        mToken = token;
        if (StringUtils.checkNotNullOrEmpty(token)) {
            return true;
        }else {
            //goto login
            finish();
            return false;
        }
    }

    private void setUpToolbar() {
        Toolbar toolbar = ButterKnife.findById(this, R.id.toolbar);
        setSupportActionBar(toolbar);
        mActionBar = Preconditions.checkNotNull(getSupportActionBar());
        mActionBar.setDisplayHomeAsUpEnabled(true);
        mActionBar.setDisplayShowHomeEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
