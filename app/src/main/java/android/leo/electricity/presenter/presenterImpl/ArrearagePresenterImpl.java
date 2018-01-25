package android.leo.electricity.presenter.presenterImpl;

import android.content.res.ObbInfo;
import android.leo.electricity.bean.ArrearageBean;
import android.leo.electricity.callback.DataCallback;
import android.leo.electricity.model.IArrearageModel;
import android.leo.electricity.model.modelImpl.ArrearageModelImpl;
import android.leo.electricity.presenter.IArrearagePresenter;
import android.leo.electricity.view.IArrearageView;

/**
 * Created by Leo on 2017/12/13.
 */

public class ArrearagePresenterImpl implements IArrearagePresenter {

    private IArrearageModel arrearageModel ;
    private IArrearageView arrearageView;

    public ArrearagePresenterImpl(IArrearageView arrearageView){
        arrearageModel = new ArrearageModelImpl();
        this.arrearageView = arrearageView;
    }
    @Override
    public void passArrearageInfo(String path, String customerId, String token) {
        arrearageModel.loadArrearageInfo(path, customerId, token, new DataCallback() {
            @Override
            public void onSuccess(Object object) {
                String response = (String) object;
                arrearageView.showArrearageInfo(response);
            }

            @Override
            public void onError() {
            }
        });
    }
}
