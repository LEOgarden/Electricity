package android.leo.electricity.presenter.presenterImpl;

import android.leo.electricity.bean.ElectricPreMonth;
import android.leo.electricity.callback.DataCallback;
import android.leo.electricity.model.IUseElectricModel;
import android.leo.electricity.model.modelImpl.UseElectricModelImpl;
import android.leo.electricity.presenter.IUseElectricPresenter;
import android.leo.electricity.view.IElectricUsedView;
import android.util.Log;

import java.util.List;

/**
 * Created by Leo on 2017/9/11.
 */

public class UseElectricPresenterImpl implements IUseElectricPresenter {
    private IUseElectricModel useElectricModel;
    private IElectricUsedView electricUsedView;

    public UseElectricPresenterImpl(IElectricUsedView electricUsedView) {
        this.electricUsedView = electricUsedView;
        useElectricModel = new UseElectricModelImpl();
    }

    @Override
    public void loadUseElectricInfo(String path, String customerId, int year, String token) {
        useElectricModel.useElectricInfo(path, customerId, year, token, new DataCallback() {
            @Override
            public void onSuccess(Object object) {
                List<ElectricPreMonth> electricPreMonths = (List<ElectricPreMonth>) object;
                electricUsedView.showElectricUsed(electricPreMonths);
            }

            @Override
            public void onError() {
            }
        });
    }
}
