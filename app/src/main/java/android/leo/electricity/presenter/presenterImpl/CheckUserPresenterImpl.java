package android.leo.electricity.presenter.presenterImpl;

import android.leo.electricity.callback.DataCallback;
import android.leo.electricity.model.ICheckUserModel;
import android.leo.electricity.model.modelImpl.CheckUserModelImpl;
import android.leo.electricity.presenter.ICheckUserPresenter;
import android.leo.electricity.view.ICheckUserView;

/**
 * Created by Leo on 2017/9/7.
 */

public class CheckUserPresenterImpl implements ICheckUserPresenter {
    private ICheckUserModel checkUserModel;
    private ICheckUserView checkUserView;

    public CheckUserPresenterImpl(ICheckUserView checkUserView) {
        this.checkUserView = checkUserView;
        checkUserModel = new CheckUserModelImpl();
    }

    @Override
    public void checkUser(String path, String token) {
        checkUserModel.loadUser(path, token, new DataCallback() {
            @Override
            public void onSuccess(Object object) {
                checkUserView.showCheckUser(object);
            }

            @Override
            public void onError() {

            }
        });
    }
}
