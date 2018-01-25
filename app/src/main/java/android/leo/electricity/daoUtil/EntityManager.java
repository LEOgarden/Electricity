package android.leo.electricity.daoUtil;

/**
 * Created by Leo on 2018/1/11.
 */

public class EntityManager {
    private static EntityManager entityManager;
    /**
     * 创建单例
     *
     * @return
     */
    public static EntityManager getInstance() {
        if (entityManager == null) {
            entityManager = new EntityManager();
        }
        return entityManager;
    }
}
