package android.leo.electricity.presenter.presenterImpl;

import android.leo.electricity.bean.ServicePoint;
import android.leo.electricity.callback.DataCallback;
import android.leo.electricity.model.IServerModel;
import android.leo.electricity.model.modelImpl.ServerModelImpl;
import android.leo.electricity.presenter.IServerPresenter;
import android.leo.electricity.view.IServerView;

import java.util.List;

/**
 * Created by Administrator on 2017/7/24.
 */

public class ServerPresenterImpl implements IServerPresenter{

    private IServerModel serverModel;
    private IServerView serverView;

    public ServerPresenterImpl(IServerView serverView){
        this.serverView = serverView;
        serverModel = new ServerModelImpl();
    }

    @Override
    public void obtainServerPoint(String url, String token){
        serverModel.obtainServerPoint(url, token, new DataCallback(){
            @Override
            public void onSuccess(Object object){
                List<ServicePoint> servicePointList = (List<ServicePoint>) object;
                serverView.showServerPointView(servicePointList);
            }

            @Override
            public void onError(){
            }
        });
    }
}
