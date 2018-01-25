package android.leo.electricity.presenter.presenterImpl;

import android.leo.electricity.bean.Department;
import android.leo.electricity.callback.DataCallback;
import android.leo.electricity.model.IDepartmentModel;
import android.leo.electricity.model.modelImpl.DepartmentModelImpl;
import android.leo.electricity.presenter.IDepartmentPresenter;
import android.leo.electricity.view.IDepartmentView;
import android.util.Log;

import java.util.List;

/**
 * Created by Administrator on 2017/7/24.
 */

public class DepartmentPresenterImpl implements IDepartmentPresenter{

    private IDepartmentView departmentView;
    private IDepartmentModel departmentModel;

    public DepartmentPresenterImpl(IDepartmentView departmentView){
        this.departmentView = departmentView;
        departmentModel = new DepartmentModelImpl();
    }

    @Override
    public void obtainDepartment(String url, String token){
        departmentModel.obtainDepartment(url, token, new DataCallback(){
            @Override
            public void onSuccess(Object object){
                List<Department> departmentList = (List<Department>) object;
                for(int i = 0; i < departmentList.size(); i++){
                    Log.d("modelCallback", departmentList.get(i).getDanwmc());
                }
                departmentView.showDepartmentView(departmentList);
            }

            @Override
            public void onError(){
            }
        });
    }
}
