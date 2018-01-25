package android.leo.electricity.presenter.presenterImpl;

import android.leo.electricity.bean.Bill;
import android.leo.electricity.callback.DataCallback;
import android.leo.electricity.model.IBillListModel;
import android.leo.electricity.model.modelImpl.BillListImpl;
import android.leo.electricity.presenter.IBillListPresenter;
import android.leo.electricity.view.IBillView;
import android.util.Log;

import java.util.List;

/**
 * Created by Leo on 2017/8/7.
 */

public class BIllListPresenterImpl implements IBillListPresenter{
    private IBillListModel billListModel;
    private IBillView billView;

    public BIllListPresenterImpl(IBillView billView){
        this.billView = billView;
        billListModel = new BillListImpl();
    }

    @Override
    public void loadBillList(String path, String customerId, String token){
        billListModel.loadBillList(path, customerId, token, new DataCallback(){
            @Override
            public void onSuccess(Object object){
                List<Bill> bills = (List<Bill>) object;
                billView.showBillList(bills);
            }

            @Override
            public void onError(){
            }
        });

    }
}
