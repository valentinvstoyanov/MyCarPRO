package elsys.mycar.mycarpro.loginscreen;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.common.base.Preconditions;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import elsys.mycar.mycarpro.R;
import elsys.mycar.mycarpro.homescreen.HomeActivity;
import elsys.mycar.mycarpro.homescreen.MainActivity;
import elsys.mycar.mycarpro.registerscreen.RegisterActivity;
import elsys.mycar.mycarpro.registerscreen.RegisterFragment;
import elsys.mycar.mycarpro.util.TextInputUtils;
import elsys.mycar.mycarpro.util.TokenUtils;

public class LoginFragment extends Fragment implements LoginContract.View {

    public static final String TAG = "LoginFragment";

    @BindView(R.id.til_login_username) TextInputLayout tilUsername;
    @BindView(R.id.til_login_password) TextInputLayout tilPassword;

    @BindString(R.string.signing_in) String signingIn;

    private LoginContract.Presenter mPresenter;
    private Unbinder mUnbinder;
    private ProgressDialog mProgressDialog;

    public static final int REGISTER_REQUEST_CODE = 13;

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        mUnbinder = ButterKnife.bind(this, view);
        mProgressDialog = new ProgressDialog(getActivity());
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage(signingIn);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }

    @OnClick(R.id.btn_login)
    public void onLoginButtonClick() {
        String username = TextInputUtils.getTextFromTil(tilUsername);
        String password = TextInputUtils.getTextFromTil(tilPassword);

        mPresenter.login(username, password);
    }

    @OnClick(R.id.tv_login_to_register)
    public void onNoAccountYetClick() {
        Intent intent = new Intent(getActivity(), RegisterActivity.class);
        startActivityForResult(intent, REGISTER_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REGISTER_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            String email = data.getStringExtra(RegisterFragment.REGISTER_RESULT_USERNAME);
            if (email != null) {
                TextInputUtils.setTextToTil(tilUsername, email);
            }
            Toast.makeText(getContext(), "Now enter your credentials to continue", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void setPresenter(LoginContract.Presenter presenter) {
        this.mPresenter = Preconditions.checkNotNull(presenter, "LoginContract.Presenter cannot be null");
    }

    @Override
    public void setUsername(String username) {
        TextInputUtils.setTextToTil(tilUsername, username);
    }

    @Override
    public void showUsernameError(String error) {
        tilUsername.setError(error);
    }

    @Override
    public void showPasswordError(String error) {
        tilPassword.setError(error);
    }

    @Override
    public void showProgress() {
        mProgressDialog.show();
    }

    @Override
    public void hideProgress() {
        mProgressDialog.dismiss();
    }

    @Override
    public void showLoginFailed() {
        Toast.makeText(getContext(), "Login failed, are you registered?", Toast.LENGTH_LONG).show();
    }

    @Override
    public void loggedIn(String token) {
        new TokenUtils(getActivity()).saveToken(token);
        Intent intent = new Intent(getActivity(), MainActivity.class);
        startActivity(intent);
        getActivity().finish();
    }
}
