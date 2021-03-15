package by.edik.car_api.dao;

import by.edik.car_api.dao.model.User;

import java.util.List;

public class UserDaoHiba extends AbstractDaoHiba<User> {

    private static volatile UserDaoHiba userDaoHibaInstance;

    @Override
    public User create(User user) {
        persist(user);
        return user;
    }

    @Override
    public User getById(Long id) {
        return find(User.class, id);
    }

    @Override
    public List<User> getAll() {
        return createQuery("SELECT user FROM User user").getResultList();
    }

    @Override
    public void update(User user) {
        merge(user);
    }

    @Override
    public void delete(Long id) {
        remove(getById(id));
    }

    public static UserDaoHiba getInstance() {
        UserDaoHiba localInstance = userDaoHibaInstance;

        if (localInstance == null) {

            synchronized (UserDaoHiba.class) {
                localInstance = userDaoHibaInstance;

                if (localInstance == null) {
                    userDaoHibaInstance = localInstance = new UserDaoHiba();
                }
            }
        }

        return localInstance;
    }
}
