package android.leo.electricity.presenter.presenterImpl;

import android.leo.electricity.bean.ServicePoint;
import android.leo.electricity.bean.UserHH;
import android.leo.electricity.callback.DataCallback;
import android.leo.electricity.model.IFastBindtModel;
import android.leo.electricity.model.IServerModel;
import android.leo.electricity.model.modelImpl.FastBindImpl;
import android.leo.electricity.model.modelImpl.ServerModelImpl;
import android.leo.electricity.presenter.IServerPresenter;
import android.leo.electricity.presenter.IUserHHPresenter;
import android.leo.electricity.view.IServerView;
import android.leo.electricity.view.IUserHHView;

import java.util.List;

/**
 * Created by Administrator on 2017/7/24.
 */

public class UserHHPresenterImpl implements IUserHHPresenter {

    private IFastBindtModel fastBindModel;
    private IUserHHView userHHView;

    public UserHHPresenterImpl(IUserHHView userHHView){
        this.userHHView = userHHView;
        fastBindModel = new FastBindImpl();
    }

    @Override
    public void bindUserHH(String url, String token){
        fastBindModel.loadUserHH(url, token, new DataCallback(){
            @Override
            public void onSuccess(Object object){
                UserHH userHH = (UserHH) object;
                userHHView.showUserHHView(userHH);
            }

            @Override
            public void onError(){
            }
        });
    }
}
