package android.leo.electricity.daoUtil;

import android.content.Context;
import android.leo.electricity.bean.ElectricFee;
import android.leo.electricity.greendao.gen.ElectricFeeDao;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Leo on 2018/1/11.
 */

public class FeeManager extends BaseDao<ElectricFee> {

    public FeeManager(Context context) {
        super(context);
    }

    /***************************数据库查询*************************/

    /**
     * 通过ID查询对象
     * @param id
     * @return
     */
    private ElectricFee loadById(long id){

        return daoSession.getElectricFeeDao().load(id);
    }

    /**
     * 获取某个对象的主键ID
     * @param electricFee
     * @return
     */
    private long getID(ElectricFee electricFee){

        return daoSession.getElectricFeeDao().getKey(electricFee);
    }

    /**
     * 通过名字获取Customer对象
     * @return
     */
    private List<ElectricFee> getStudentByName(String key){
        QueryBuilder queryBuilder =  daoSession.getElectricFeeDao().queryBuilder();
        queryBuilder.where(ElectricFeeDao.Properties.Name.eq(key));
        int size = queryBuilder.list().size();
        if (size > 0){
            return queryBuilder.list();
        }else{
            return null;
        }
    }

    /**
     * 通过名字获取Customer对象
     * @return
     */
    private List<Long> getIdByName(String key){
        List<ElectricFee> electricFees = getStudentByName(key);
        List<Long> ids = new ArrayList<Long>();
        int size = electricFees.size();
        if (size > 0){
            for (int i = 0;i < size;i++){
                ids.add(electricFees.get(i).getId());
            }
            return ids;
        }else{
            return null;
        }
    }

    /***************************数据库删除*************************/

    /**
     * 根据ID进行数据库的删除操作
     * @param id
     */
    private void deleteById(long id){

        daoSession.getElectricFeeDao().deleteByKey(id);
    }


    /**
     * 根据ID同步删除数据库操作
     * @param ids
     */
    private void deleteByIds(List<Long> ids){

        daoSession.getElectricFeeDao().deleteByKeyInTx(ids);
    }

    /***********************************
     * 在次添加一些electricFee特有的数据库操作语句
     * ************************************/
}
